package com.test.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.test.app.model.BookedTable;
import com.test.app.model.Booking;
import com.test.app.model.Client;

public class BookingDAO extends DAO {

    public BookingDAO() {
        super();
    }

    /**
     * Insert a new booking into the database, including its booked tables. All are
     * added in a single transaction.
     *
     */
    public boolean addBooking(Booking b) {
        String sqlAddBooking = "INSERT INTO tblbooking(creatorid, clientid, bookingdate, totalamount, note) VALUES(?,?,?,?,?)";
        String sqlAddBookedTable = "INSERT INTO tblbookedtable(bookingid, tableid, checkin, checkout, price, ischeckin)  VALUES(?,?,?,?,?,?)";
        String sqlCheckBookedTable = "SELECT * FROM tblbookedtable WHERE tableid = ? AND checkout > ? AND checkin < ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean result = true;
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sqlAddBooking,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, b.getUser().getId());
            ps.setInt(2, b.getClient().getId());
            ps.setString(3, sdf.format(b.getBookDate()));
            ps.setFloat(4, b.getTotalAmount());
            ps.setString(5, b.getNote());

            ps.executeUpdate();
            // get id of the new inserted booking
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                b.setId(generatedKeys.getInt(1));

                // insert booked tables
                for (BookedTable br : b.getBookedTables()) {
                    // check if the table is available at the period
                    ps = con.prepareStatement(sqlCheckBookedTable);
                    ps.setInt(1, br.getTable().getId());
                    ps.setString(2, sdf.format(br.getCheckin()));
                    ps.setString(3, sdf.format(br.getCheckout()));

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {// unavailable
                        result = false;
                        try {
                            con.rollback();
                            con.setAutoCommit(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return result;
                    }

                    // insert booked table
                    ps = con.prepareStatement(sqlAddBookedTable,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, b.getId());
                    ps.setInt(2, br.getTable().getId());
                    ps.setString(3, sdf.format(br.getCheckin()));
                    ps.setString(4, sdf.format(br.getCheckout()));
                    ps.setDouble(5, br.getPrice());
                    ps.setBoolean(6, br.isCheckedIn());

                    ps.executeUpdate();
                    // get id of the new inserted booking
                    generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        br.setId(generatedKeys.getInt(1));
                    }
                }
            }

            // con.commit();//set this line into comment in JUnit test mode
        } catch (Exception e) {
            result = false;
            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // con.setAutoCommit(true);//set this line into comment in JUnit test mode
            } catch (Exception ex) {
                result = false;
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * get list of booking involved the table whose @idtable is given
     * between @startDate and @endDate
     *
     */
    public ArrayList<Booking> getBookingOfTable(int idtable, Date startDate, Date endDate) {
        ArrayList<Booking> result = new ArrayList<>();
        String sql = "SELECT a.id as bookedtableid, GREATEST(a.checkin,?) as checkin, LEAST(a.checkout,?) as checkout, a.price, b.id as bookingid, b.totalamount as totalamount,  c.id as idclient, c.name, c.address, c.tel  FROM tblBookedTable a, tblBooking b, tblClient c WHERE a.tableid = ? AND a.ischeckin = 1  AND a.checkout > ? AND a.checkin < ? AND b.id = a.bookingid AND c.id = b.clientid";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sdf.format(startDate));
            ps.setString(2, sdf.format(endDate));
            ps.setInt(3, idtable);
            ps.setString(4, sdf.format(startDate));
            ps.setString(5, sdf.format(endDate));
            ResultSet rs = ps.executeQuery();

            // a == null ? b : (b == null ? a : (a.before(b) ? a : b));
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("bookingid"));
                b.setTotalAmount(rs.getFloat("totalamount"));
                // client
                Client c = new Client();
                c.setId(rs.getInt("clientid"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                b.setClient(c);
                // booked table
                BookedTable br = new BookedTable();
                br.setId(rs.getInt("bookedtableid"));
                br.setPrice(rs.getFloat("price"));
                br.setCheckin(rs.getDate("checkin"));
                br.setCheckout(rs.getDate("checkout"));
                b.getBookedTables().add(br);
                result.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
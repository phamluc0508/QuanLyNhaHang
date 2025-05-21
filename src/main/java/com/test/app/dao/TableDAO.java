package com.test.app.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.test.app.model.Table;
 
public class TableDAO extends DAO{
     
    public TableDAO() {
        super();
    }
 
    /**
     * search all tables in the tblTable whose name contains the @key
     * @return list of table whose name contains the @key
     */
    public ArrayList<Table> searchTable(String key){
        ArrayList<Table> result = new ArrayList<>();
        String sql = "SELECT * FROM tbltable WHERE name LIKE ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                Table tb = new Table();
                tb.setId(rs.getInt("id"));
                tb.setName(rs.getString("name"));
                tb.setMaxNumber(rs.getInt("maxnumber"));
                tb.setDes(rs.getString("des"));
                result.add(tb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }

    /**
     * create the @table
     */
    public boolean createTable(Table tb){
        if(findTableByName(tb.getName())){
            throw new RuntimeException("Tên bàn đã tồn tại");
        }

        String sql = "INSERT INTO tbltable (name, maxnumber, des) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tb.getName());
            ps.setInt(2, tb.getMaxNumber());
            ps.setString(3, tb.getDes());

            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    /**
     * update the @table
     */
    public boolean updateTable(Table tb){

        if (findTableIsUsedById(tb.getId())){
            throw new RuntimeException("Bàn đang được dùng");
        }

        if (findTableByIdNotInAndName(tb.getId(), tb.getName())){
            throw new RuntimeException("Tên bàn đã tồn tại");
        }

        String sql = "UPDATE tbltable SET name=?, maxNumber=?, des=? WHERE id=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tb.getName());
            ps.setInt(2, tb.getMaxNumber());
            ps.setString(3, tb.getDes());
            ps.setInt(4, tb.getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }       
        return true;
    }

    /**
     * delete the @table
     */
    public boolean deleteTable(Table tb){

        if (findTableIsUsedById(tb.getId())){
            throw new RuntimeException("Bàn đang được dùng");
        }

        String sql = "DELETE FROM tbltable WHERE id=?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tb.getId());
 
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }       
        return true;
    }

    private boolean findTableByName(String name){
        String sql = "SELECT * FROM tbltable WHERE name = ?";
        Table tb = new Table();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean findTableByIdNotInAndName(int id, String name){
        String sql = "SELECT 1 FROM tbltable WHERE id <> ? and name = ? LIMIT 1";
        Table tb = new Table();
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean findTableIsUsedById(int id){
        String sql = """
                SELECT 1 FROM tblbookedtable
                  WHERE tableid = ?
                    AND (
                          (checkin <= NOW() AND checkout >= NOW())
                          OR (checkin > NOW())
                        )
                  LIMIT 1
                """;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Table> searchFreeTable(Date checkin, int maxNumber){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkin);
        if(maxNumber <= 2){
            calendar.add(Calendar.HOUR_OF_DAY, 2);
        } else if (maxNumber <= 6){
            calendar.add(Calendar.HOUR_OF_DAY, 3);
        } else {
            calendar.add(Calendar.HOUR_OF_DAY, 4);
        }
        Date checkout = calendar.getTime();
        ArrayList<Table> result = new ArrayList<>();
        String sql = """
        SELECT * FROM tbltable WHERE maxnumber >= ?
        and id NOT IN (SELECT tableid FROM tblbookedtable WHERE checkout > ? AND checkin < ?)
        """;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, maxNumber);
            ps.setString(2, sdf.format(checkin));
            ps.setString(3, sdf.format(checkout));
            ResultSet rs = ps.executeQuery();
    
            while(rs.next()){
                Table tb = new Table();
                tb.setId(rs.getInt("id"));
                tb.setName(rs.getString("name"));
                tb.setMaxNumber(rs.getInt("maxnumber"));
                tb.setDes(rs.getString("des"));
                result.add(tb);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
}
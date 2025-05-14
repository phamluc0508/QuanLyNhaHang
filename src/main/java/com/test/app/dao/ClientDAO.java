package com.test.app.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.test.app.model.Client;
 
public class ClientDAO extends DAO{
     
    /**
     * search all clients in the tblClient whose name contains the @key
     * @return list of client whose name contains the @key
     */
    public ArrayList<Client> searchClient(String key){
        ArrayList<Client> result = new ArrayList<>();
        String sql = "SELECT * FROM tblclient WHERE name LIKE ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setAddress(rs.getString("address"));
                client.setTel(rs.getString("tel"));
                client.setEmail(rs.getString("email"));
                result.add(client);
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
     
    /**
     * add a new @client into the DB
     */
    public Client addClient(Client client){
        String sql = "INSERT INTO tblclient(name, address, tel, email) VALUES(?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql,
                              Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getTel());
            ps.setString(4, client.getEmail());
 
            ps.executeUpdate();
             
            //get id of the new inserted client
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
            }
            return client;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
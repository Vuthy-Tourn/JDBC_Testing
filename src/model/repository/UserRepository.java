package model.repository;

import model.dto.UpdateUserDto;
import model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static model.utils.DataConfig.getDatabaseConnection;

public class UserRepository implements Repository<User,Integer>{

    @Override
    public User save(User user) {
        String sql = """
               INSERT INTO users (uuid,user_name,email,password,created_date)
               VALUES (?,?,?,?,?)
               """;
        try(Connection con = getDatabaseConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUuid());
            ps.setString(2,user.getUserName());
            ps.setString(3,user.getEmail());
            ps.setString(4,user.getPassword());
            ps.setDate(5,user.getCreatedDate());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0 ? user : null;
        } catch (Exception e) {
            System.out.println("[!] Error while saving user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try(Connection con = getDatabaseConnection()){
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setUuid(result.getString("uuid"));
                user.setUserName(result.getString("user_name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setCreatedDate(result.getDate("created_date"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("[!] Error while finding all users: "+e.getMessage());
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String sql = """
            DELETE FROM users
            WHERE id = ?
            """;
        try(Connection con = getDatabaseConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? rowsAffected : 0;
        } catch (Exception e) {
            System.out.println("[!] Error while deleting user: "+e.getMessage());
        }
        return 0;
    }

    public User findByUserUuid(String uuid){
        String sql = """
                SELECT * FROM users
                WHERE uuid = ?
                """;
        try(Connection con = getDatabaseConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,uuid);
            ResultSet result = stmt.executeQuery();
            User user = new User();
            while (result.next()) {
                user.setId(result.getInt("id"));
                user.setUuid(result.getString("uuid"));
                user.setUserName(result.getString("user_name"));
                user.setEmail(result.getString("email"));
                user.setCreatedDate(result.getDate("created_date"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("[!] Error while finding all users: "+e.getMessage());
        }
        return null;
    }

    public User updateUserByUuid(String uuid, UpdateUserDto updateUserDto){
        User user
                = findByUserUuid(uuid);
        if(user!=null){
            String sql = """
                    UPDATE users
                    SET user_name = ?
                    WHERE uuid = ?
                    """;
            try(Connection con = getDatabaseConnection()){
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,updateUserDto.userName());
                ps.setString(2, uuid);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0){
                    return findByUserUuid(uuid);
                }
            } catch (Exception e) {
                System.out.println("[!] Error while updating user: "+e.getMessage());
            }
            return null;
        }
        throw new NoSuchElementException("Cannot find user");
    }
}

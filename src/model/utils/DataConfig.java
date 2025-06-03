package model.utils;

import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;

@Data
public class DataConfig {
    private static final String databaseUrl = "jdbc:postgresql://localhost:5432/morning_ecommerce_db";
    private static final String username = "postgres";
    private static final String password = "vuthy123";
    public static Connection getDatabaseConnection(){
       try{
           return DriverManager.getConnection(databaseUrl, username, password);
       }catch(Exception e){
           System.out.println("Database connection failed: "+ e.getMessage());
       }
       return null;
    }
}

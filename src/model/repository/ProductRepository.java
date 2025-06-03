package model.repository;

import model.ProductData;
import model.dto.UpdateProductDto;
import model.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static model.utils.DataConfig.getDatabaseConnection;

public class ProductRepository implements Repository<Product, Integer> {

    @Override
    public Product save(Product product) {
       String sql = """
               INSERT INTO products (uuid,p_name,expired_date)
               VALUES (?,?,?)
               """;
       try(Connection con = getDatabaseConnection()){
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setString(1, product.getUuid());
           ps.setString(2,product.getPName());
           ps.setDate(3,product.getExpiredDate());
           int rowsAffected = ps.executeUpdate();
           return rowsAffected > 0 ? product : null;
       } catch (Exception e) {
           System.out.println("[!] Error while saving product: " + e.getMessage());
       }
        return null;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        try(Connection con = getDatabaseConnection()){
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("id"));
                product.setUuid(result.getString("uuid"));
                product.setPName(result.getString("p_name"));
                product.setExpiredDate(result.getDate("expired_date"));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            System.out.println("[!] Error while finding all products: "+e.getMessage());
        }
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        String sql = """
            DELETE FROM products
            WHERE id = ?
            """;
        try(Connection con = getDatabaseConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? rowsAffected : 0;
        } catch (Exception e) {
            System.out.println("[!] Error while deleting product: "+e.getMessage());
        }
        return 0;
    }

    public Product findByProductUuid(String uuid){
        String sql = """
                SELECT * FROM products
                WHERE uuid = ?
                """;
        try(Connection con = getDatabaseConnection()){
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,uuid);
            ResultSet result = stmt.executeQuery();
            Product product = new Product();
            while (result.next()) {
                product.setId(result.getInt("id"));
                product.setUuid(result.getString("uuid"));
                product.setPName(result.getString("p_name"));
                product.setExpiredDate(result.getDate("expired_date"));
                return product;
            }
        } catch (Exception e) {
            System.out.println("[!] Error while finding all products: "+e.getMessage());
        }
        return null;
    }

    public Product updateProductByUuid(String uuid, UpdateProductDto updateProductDto){
        Product product
                 = findByProductUuid(uuid);
        if(product!=null){
            String sql = """
                    UPDATE products
                    SET p_name = ? , expired_date = ?
                    WHERE uuid = ?
                    """;
            try(Connection con = getDatabaseConnection()){
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, updateProductDto.pName());
                ps.setDate(2,updateProductDto.expiredDate());
                ps.setString(3, uuid);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0){
                    return findByProductUuid(uuid);
                }
            } catch (Exception e) {
                System.out.println("[!] Error while updating product: "+e.getMessage());
            }
            return null;
        }
        throw new NoSuchElementException("Cannot find Product");
    }
}

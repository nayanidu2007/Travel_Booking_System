package com.travelbooking.dao;

import com.travelbooking.model.Customer;
import com.travelbooking.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    // CREATE
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add customer failed: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Get customers failed: " + e.getMessage());
        }
        return list;
    }

    // READ ONE
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Get customer failed: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name=?, email=?, phone=?, address=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update customer failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete customer failed: " + e.getMessage());
            return false;
        }
    }
}
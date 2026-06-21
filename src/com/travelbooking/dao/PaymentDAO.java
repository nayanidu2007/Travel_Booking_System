package com.travelbooking.dao;

import com.travelbooking.model.Payment;
import com.travelbooking.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private Connection connection;

    public PaymentDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    // CREATE
    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO payment (booking_id, amount, payment_date, method, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            ps.setString(4, payment.getMethod());
            ps.setString(5, payment.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add payment failed: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payment";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getInt("id"));
                p.setBookingId(rs.getInt("booking_id"));
                p.setAmount(rs.getDouble("amount"));
                p.setPaymentDate(rs.getDate("payment_date"));
                p.setMethod(rs.getString("method"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Get payments failed: " + e.getMessage());
        }
        return list;
    }

    // READ ONE
    public Payment getPaymentByBookingId(int bookingId) {
        String sql = "SELECT * FROM payment WHERE booking_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getInt("id"));
                p.setBookingId(rs.getInt("booking_id"));
                p.setAmount(rs.getDouble("amount"));
                p.setPaymentDate(rs.getDate("payment_date"));
                p.setMethod(rs.getString("method"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Get payment failed: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payment SET booking_id=?, amount=?, payment_date=?, method=?, status=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            ps.setString(4, payment.getMethod());
            ps.setString(5, payment.getStatus());
            ps.setInt(6, payment.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update payment failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deletePayment(int id) {
        String sql = "DELETE FROM payment WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete payment failed: " + e.getMessage());
            return false;
        }
    }
}
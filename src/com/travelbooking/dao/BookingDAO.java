package com.travelbooking.dao;

import com.travelbooking.model.Booking;
import com.travelbooking.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private Connection connection;

    public BookingDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    // CREATE
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO booking (customer_id, destination_id, travel_date, return_date, num_persons, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, booking.getCustomerId());
            ps.setInt(2, booking.getDestinationId());
            ps.setDate(3, new java.sql.Date(booking.getTravelDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getReturnDate().getTime()));
            ps.setInt(5, booking.getNumPersons());
            ps.setDouble(6, booking.getTotalPrice());
            ps.setString(7, booking.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add booking failed: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setCustomerId(rs.getInt("customer_id"));
                b.setDestinationId(rs.getInt("destination_id"));
                b.setTravelDate(rs.getDate("travel_date"));
                b.setReturnDate(rs.getDate("return_date"));
                b.setNumPersons(rs.getInt("num_persons"));
                b.setTotalPrice(rs.getDouble("total_price"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Get bookings failed: " + e.getMessage());
        }
        return list;
    }

    // READ ONE
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM booking WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setCustomerId(rs.getInt("customer_id"));
                b.setDestinationId(rs.getInt("destination_id"));
                b.setTravelDate(rs.getDate("travel_date"));
                b.setReturnDate(rs.getDate("return_date"));
                b.setNumPersons(rs.getInt("num_persons"));
                b.setTotalPrice(rs.getDouble("total_price"));
                b.setStatus(rs.getString("status"));
                return b;
            }
        } catch (SQLException e) {
            System.out.println("Get booking failed: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE booking SET customer_id=?, destination_id=?, travel_date=?, return_date=?, num_persons=?, total_price=?, status=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, booking.getCustomerId());
            ps.setInt(2, booking.getDestinationId());
            ps.setDate(3, new java.sql.Date(booking.getTravelDate().getTime()));
            ps.setDate(4, new java.sql.Date(booking.getReturnDate().getTime()));
            ps.setInt(5, booking.getNumPersons());
            ps.setDouble(6, booking.getTotalPrice());
            ps.setString(7, booking.getStatus());
            ps.setInt(8, booking.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update booking failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteBooking(int id) {
        String sql = "DELETE FROM booking WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete booking failed: " + e.getMessage());
            return false;
        }
    }
}
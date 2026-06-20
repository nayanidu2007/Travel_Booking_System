package com.travelbooking.dao;

import com.travelbooking.model.Destination;
import com.travelbooking.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationDAO {

    private Connection connection;

    public DestinationDAO() {
        connection = DBConnection.getInstance().getConnection();
    }

    // CREATE
    public boolean addDestination(Destination destination) {
        String sql = "INSERT INTO destination (name, country, description, price_per_person) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, destination.getName());
            ps.setString(2, destination.getCountry());
            ps.setString(3, destination.getDescription());
            ps.setDouble(4, destination.getPricePerPerson());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Add destination failed: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Destination> getAllDestinations() {
        List<Destination> list = new ArrayList<>();
        String sql = "SELECT * FROM destination";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Destination d = new Destination();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setCountry(rs.getString("country"));
                d.setDescription(rs.getString("description"));
                d.setPricePerPerson(rs.getDouble("price_per_person"));
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Get destinations failed: " + e.getMessage());
        }
        return list;
    }

    // READ ONE
    public Destination getDestinationById(int id) {
        String sql = "SELECT * FROM destination WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Destination d = new Destination();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setCountry(rs.getString("country"));
                d.setDescription(rs.getString("description"));
                d.setPricePerPerson(rs.getDouble("price_per_person"));
                return d;
            }
        } catch (SQLException e) {
            System.out.println("Get destination failed: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean updateDestination(Destination destination) {
        String sql = "UPDATE destination SET name=?, country=?, description=?, price_per_person=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, destination.getName());
            ps.setString(2, destination.getCountry());
            ps.setString(3, destination.getDescription());
            ps.setDouble(4, destination.getPricePerPerson());
            ps.setInt(5, destination.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update destination failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteDestination(int id) {
        String sql = "DELETE FROM destination WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete destination failed: " + e.getMessage());
            return false;
        }
    }
}
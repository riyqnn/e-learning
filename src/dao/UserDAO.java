package dao;

import models.User;
import Jdbc.DbConnection;
import java.sql.*;

public class UserDAO {

	public boolean registerUser(User user) {
	    String query = "INSERT INTO users (username, email, password, user_type, contact_number, address, dob, profile_picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, user.getUsername());
	        stmt.setString(2, user.getEmail());
	        stmt.setString(3, user.getPassword()); 
	        stmt.setString(4, user.getUserType());
	        stmt.setString(5, user.getContactNumber());
	        stmt.setString(6, user.getAddress());
	        
	        if (user.getDateOfBirth() != null) {
	            try {
	                java.sql.Date sqlDate = java.sql.Date.valueOf(user.getDateOfBirth());
	                stmt.setDate(7, sqlDate);
	            } catch (IllegalArgumentException e) {
	                stmt.setNull(7, Types.DATE);
	            }
	        } else {
	            stmt.setNull(7, Types.DATE);
	        }
	        
	        stmt.setBytes(8, user.getProfilePicture());

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setUserType(rs.getString("user_type"));
                    user.setDob(rs.getDate("dob"));
                    user.setContactNumber(rs.getString("contact_number"));
                    user.setAddress(rs.getString("address"));
                    user.setProfilePicture(rs.getBytes("profile_picture"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean checkUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
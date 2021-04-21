package model;

import java.sql.*;

public class Payment {

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payments", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Payment Method</th>" + "<th>Item Name</th><th>Item Price</th>"
					+ "<th>Email</th>" + "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PID = Integer.toString(rs.getInt("PID"));
				String pMethod = rs.getString("pMethod");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String email = rs.getString("email");
				// Add a row into the html table
				output += "<tr><td>" + pMethod + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>";

				output += "<td>" + email + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' " + " type='button' value='Update' class='btn btn-danger'></td>"
						+ "<td><form method='post' action=''>" + "<input name='btnRemove' "
						+ " type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='PID' type='hidden' class='form-control' " + " value='" + PID + "'>"
						+ "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertPay(String method, String name, String price, String email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into payments(`PID`,`pMethod`,`itemName`,`itemPrice`,`email`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, method);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, email);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePay(String ID, String method, String name, String price, String email) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE items SET pMethod=?,itemName=?,itemPrice=?,email=? WHERE PID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, method);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, email);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePay(String PID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from items where PID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}

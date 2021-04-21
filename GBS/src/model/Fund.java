package model;

import java.sql.*;

public class Fund {
	
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
	
	public String readFunds() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project Name</th>" + "<th>Company Name</th><th>Fund Amount</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from fund";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String FID = Integer.toString(rs.getInt("FID"));
				String project = rs.getString("project");
				String cName = rs.getString("cName");
				String fundAmount = Double.toString(rs.getDouble("fundAmount"));
				
				// Add a row into the html table
				output += "<tr><td>" + project + "</td>";
				output += "<td>" + cName + "</td>";
				output += "<td>" + fundAmount + "</td>";

				
				// buttons
				output += "<td><input name='btnUpdate' " + " type='button' value='Update' class='btn btn-danger'></td>"
						+ "<td><form method='post' action=''>" + "<input name='btnRemove' "
						+ " type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='FID' type='hidden' class='form-control' " + " value='" + FID + "'>"
						+ "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Fundings.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertFund(String project, String name, String fund) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into fund(`FID`,`project`,`cName`,`fundAmount`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, project);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(fund));
			

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

	public String updateFund(String ID, String project, String name, String fund) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE items SET project=?,cName=?,fundAmount=? WHERE FID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, project);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(fund));
			preparedStmt.setInt(4, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Funding Data.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteFund(String FID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from fund where FID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(FID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Funding Data.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}

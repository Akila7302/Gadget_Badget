package model;

import java.sql.*;

public class ProjectReview {

	//A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projects_db", "root", "root");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return con;
		}
	
	
	//Insert review
		public String insertReview(String pid,String fid, String rev, String accept) {
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for inserting."; 
				 }
				
				// create a prepared statement
				String query = " insert into review(`review_id`,`proj_id`,`fundraiser_id`,`review`,`acceptance`)"
				 		+ " values (?, ?, ?, ?, ?)"; 
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setInt(2, Integer.parseInt(pid));
				preparedStmt.setInt(3, Integer.parseInt(fid));
				preparedStmt.setString(4, rev);
				preparedStmt.setString(5, accept);
				
				
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				output = "Inserted successfully";
			}
			catch (Exception e) {
				output = "Error while inserting the project.";
				System.err.println(e.getMessage());
			}
			return output;
		}

		
		//Read review details
		public String readReviews() {
			
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for reading."; 	
				}

			// Prepare the html table to be displayed
				output = "<table border='1'><tr>" +
							"<th>Project ID</th>" +
							"<th>Fundraiser</th>" +
							"<th>Review</th>" +
							"<th>Acceptance</th>" +
							"<th>Update</th>"+
							"<th>Remove</th>" +
							"</tr>";
				
				String query = "select * from review";
				
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				// iterate through the rows in the result set
				while (rs.next()) {
					String review_id = Integer.toString(rs.getInt("review_id"));
					String proj_id = Integer.toString(rs.getInt("proj_id"));
					String fundraiser_id = Integer.toString(rs.getInt("fundraiser_id"));
					String review = rs.getString("review");
					String acceptance = rs.getString("acceptance");
					
					// Add into the html table
					output += "<tr><td>" + proj_id + "</td>";
					output += "<td>" + fundraiser_id + "</td>";
					output += "<td>" + review + "</td>";
					output += "<td>" + acceptance + "</td>";
			
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' "
							+ "class='btn btn-secondary'></td>"  
							+ "<td><form method='post' action='projects.jsp'>" 
							+ "<input name='btnRemove' type='submit' value='Remove'  class='btn btn-danger'>" 
							+ "<input name='project_id' type='hidden' value='" + review_id
							+ "'>" + "</form></td></tr>";
				}
				
				con.close();
				
				// Complete the html table
				output += "</table>";
			}
			catch (Exception e) {
				output = "Error while reading the projects.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}


		//Update project
		public String updateReview(String ID, String proj_id, String fundraiser_id, String rev, String accept) {
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for updating."; 
				}
				
				// create a prepared statement
				String query = "UPDATE review SET proj_id=?,fundraiser_id=?,review=?,acceptance=? WHERE review_id=?";
								
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(proj_id));
				preparedStmt.setInt(2, Integer.parseInt(fundraiser_id));
				preparedStmt.setString(3, rev);
				preparedStmt.setString(4, accept);
				preparedStmt.setInt(5, Integer.parseInt(ID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
			}
			catch (Exception e) {
				output = "Error while updating the project.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}

		//Delete project
		public String deleteReview(String review_id) {
			String output = "";
			
			try {
				
				Connection con = connect();
				
				if (con == null) {
					return "Error while connecting to the database for deleting."; 
				}
				
				// create a prepared statement
				String query = "delete from review where review_id=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(review_id));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				output = "Deleted successfully";
			}
			catch (Exception e) {
				output = "Error while deleting the project.";
				System.err.println(e.getMessage());
			}
				return output;
			}

}

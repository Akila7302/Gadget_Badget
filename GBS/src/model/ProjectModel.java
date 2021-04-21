package model;

import java.sql.*;
public class ProjectModel {
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projects_db", "root", "root");
		}
		catch (Exception e)
			{e.printStackTrace();}
			return con;
	}

	public String insertProject(String ptitle, String pdesc, String iname, String dtime, String pcost)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for inserting."; }
			
			// create a prepared statement
			String query = " insert into projects(`project_id`,`project_title`,`p_description`,`inventor_name`,`delivery_time`,`project_cost`)"
					 		+ " values (?, ?, ?, ?, ?, ?)"; 
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ptitle);
			preparedStmt.setString(3, pdesc);
			preparedStmt.setString(4, iname);
			preparedStmt.setString(5, dtime);
			preparedStmt.setDouble(4, Double.parseDouble(pcost));
			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Inserted successfully";
		}
		catch (Exception e){
			output = "Error while inserting the project.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String readProjects()
	{
		
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>" +
					"<th>Project Title</th>" +
					"<th>Description</th>" +
					"<th>Inventor</th>" +
					"<th>delivery Time</th>" +
					"<th>Cost</th>" +
					"<th>Update</th><th>Remove</th>" +
					"</tr>";
			
			String query = "select * from projects";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String project_id = Integer.toString(rs.getInt("project_id"));
				String project_title = rs.getString("project_title");
				String p_description = rs.getString("p_description");
				String inventor_name = rs.getString("inventor_name");
				String delivery_time = rs.getString("delivery_time");
				String project_cost = Double.toString(rs.getDouble("project_cost"));
				
				// Add into the html table
				output += "<tr><td>" + project_title + "</td>";
				output += "<td>" + p_description + "</td>";
				output += "<td>" + inventor_name + "</td>";
				output += "<td>" + delivery_time + "</td>";
				output += "<td>" + project_cost + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btn btn-secondary'></td>"  
						+ "<td><form method='post' action='projects.jsp'>" 
						+ "<input name='btnRemove' type='submit' value='Remove'  class='btn btn-danger'>" 
						+ "<input name='project_id' type='hidden' value='" + project_id
						+ "'>" + "</form></td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e){
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}


	public String updateProject(String ID, String ptitle, String pdesc, String iname, String dtime, String pcost)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for updating."; }
			
			// create a prepared statement
			String query = "UPDATE projects SET project_title=?,p_description=?,inventor_name=?,delivery_time=?,project_cost=? WHERE project_id=?";
							
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, ptitle);
			preparedStmt.setString(2, pdesc);
			preparedStmt.setString(3, iname);
			preparedStmt.setString(4, dtime);
			preparedStmt.setDouble(5, Double.parseDouble(pcost));
			preparedStmt.setInt(6, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the project.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	public String deleteProject(String project_id)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from projects where project_id=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(project_id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}
			return output;
		}
	



}

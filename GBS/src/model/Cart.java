package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cart {

	 private Connection connect(){

	 Connection con = null;
	 try{ 
	 
		 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loging", "root", "123456");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 return con;
		 } 
	
	 public String insertItem(String proName, String owner, String description, String price ,String email) 
	 { 
	 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for inserting."; } 
		 // create a prepared statement
		 String query = " insert into cart (`productId`,`productName`,`owner`,`description`,`price`,`email`)"+ " values (?, ?, ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setString(2, proName); 
		 preparedStmt.setString(3, owner); 
		 preparedStmt.setString(4, description); 
		 preparedStmt.setDouble(5, Double.parseDouble(price)); 
		 preparedStmt.setString(6, email); 
		// execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while inserting the item."; 
		 System.err.println(e.getMessage()); 
		 } 
	 return output; 
	 } 
		
	public String readItems(){


		 String output = ""; 
		 
		 try{
			 Connection con = connect();
			 if (con == null) {
	
				 return "Error while connecting to the database for reading."; }
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Product ID</th><th>Product Name</th>" +
					 "<th>Owner</th>" +
					 "<th>Description</th>" +
					 "<th>price</th>" +
					 "<th>Email</th>" +
					 "<th>Update</th><th>Remove</th></tr>";
		
			 String query = "select * from cart";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 // iterate through the rows in the result set
			 while (rs.next()){
				 
				 String productId = Integer.toString(rs.getInt("productId"));
				 String productName = rs.getString("productName");
				 String owner = rs.getString("owner");
				 String description = rs.getString("description");
				 String price = Double.toString(rs.getDouble("price"));
				 String email = rs.getString("email");
				 
				 // Add into the html table
				 output += "<tr><td>" + productId + "</td>";
				 output += "<td>" + productName + "</td>";
				 output += "<td>" + owner + "</td>";
				 output += "<td>" + description + "</td>";
				 output += "<td>" + price + "</td>";
				 output += "<td>" + email + "</td>";
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						 + "<td><form method='post' action='items.jsp'>"+"<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						 + "<input name='productId' type='hidden' value='" + productId
						 + "'>" + "</form></td></tr>";
			 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
		 }
		 catch (Exception e){
			 output = "Error while reading the items.";
			 System.err.println(e.getMessage());
		 }
		 return output;
	 } 
	
	public String updateItem(String proId ,String proName, String owner, String description, String price, String email){
	 
		String output = "";
	 
		 try {
			 
			 Connection con = connect();
			 
			 if (con == null){
				 return "Error while connecting to the database for updating."; 
			 }
			 
			 // create a prepared statement
			 String query = "UPDATE cart SET productName=?,owner=?,description=?,price=?,email=? WHERE productId=?";
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 
			 preparedStmt.setString(1, proName);
			 preparedStmt.setString(2, owner);
			 preparedStmt.setString(3, description);
			 preparedStmt.setDouble(4, Double.parseDouble(price));
			 preparedStmt.setString(5, email);
			 preparedStmt.setInt(6,Integer.parseInt(proId));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Updated successfully";
		 }
		 catch (Exception e){
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		 }
		 return output;
	} 
	
	public String deleteItem(String productId){
	 
		 String output = "";
		 
		 try {
				 
			 Connection con = connect();
			 
			 if (con == null){
				 return "Error while connecting to the database for deleting."; 
			 }
			 
			 // create a prepared statement
			 String query = "delete from cart where productId=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(productId));
			 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Deleted successfully";
		 }
		 catch (Exception e){
			 output = "Error while deleting the item.";
			 System.err.println(e.getMessage());
		 }
		 return output;
		 }
	
	

}

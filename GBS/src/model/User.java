package model;

import java.sql.*;

public class User 
{	
	//A common method to connect to the DB
	private Connection connect() 
	{ 
		Connection con = null; 
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver"); 
	 
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loging", "root", "123456"); 
		} 
		catch (Exception e) 
		{e.printStackTrace();} 
		return con; 
	 }
	 //insert
	 public String insertUser(String fname, String lname, String pno, String email,String type, String pw) 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for inserting."; }
			 
			 // create a prepared statement
			 String query = " insert into user(`userId`,`firstName`,`lastName`,`phoneNo`,`email`,`type`,`password`)"
					 + " values (?, ?, ?, ?, ?,?,?)"; 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, fname); 
			 preparedStmt.setString(3, lname); 
			 preparedStmt.setString(4, pno); 
			 preparedStmt.setString(5, email);
			 preparedStmt.setString(6, type);
			 preparedStmt.setString(7, pw);
			 
			 // execute the statement3
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Inserted successfully"; 
			 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while inserting the user."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	 
	 public String readUser() 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; }
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>ID</th><th>First Name</th>"+
					 "<th>Last Name</th>"+
					 "<th>Phone Number</th>"+ 
					 "<th>Email</th>" +
					 "<th>Type</th>" +
					 "<th>Password</th>" +
					 "<th>Update</th><th>Remove</th></tr>"; 
	 
			 String query = "select * from user"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String userId = Integer.toString(rs.getInt("userId")); 
				 String firstName = rs.getString("firstName"); 
				 String lastName = rs.getString("lastName"); 
				 String phoneNo = rs.getString("phoneNo"); 
				 String email = rs.getString("email");
				 String type = rs.getString("type");
				 String password = rs.getString("password"); 
				 
				 // Add into the html table
				 output += "<tr><td>" + userId + "</td>";
				 output += "<td>" + firstName + "</td>"; 
				 output += "<td>" + lastName + "</td>"; 
				 output += "<td>" + phoneNo + "</td>"; 
				 output += "<td>" + email + "</td>"; 
				 output += "<td>" + type + "</td>"; 
				 output += "<td>" + password + "</td>";
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"  + "<td><form method='post' action='items.jsp'>" + "<input name='btnRemove' type='submit' value='Remove'  class='btn btn-danger'>" + "<input name='userId' type='hidden' value='" + userId  + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the ."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 } 
	 
	 //Update
	 public String updateUser(String ID, String fname, String lname, String pno, String email, String type, String pw)
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for updating."; } 
			 
			 // create a prepared statement
			 String query = "UPDATE user SET firstName=?,lastName=?,phoneNo=?,email=?,type=?,password=? WHERE userId=?";

			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setString(1, fname); 
			 preparedStmt.setString(2, lname); 
			 preparedStmt.setString(3, pno); 
			 preparedStmt.setString(4, email);
			 preparedStmt.setString(5, type);
			 preparedStmt.setString(6, pw); 
			 
			 preparedStmt.setInt(7, Integer.parseInt(ID)); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Updated successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while updating the user."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	
	 //delete
	 public String deleteUser(String userId) 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for deleting."; } 
			 
			 // create a prepared statement
			 String query = "delete from user where userId=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(userId));
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Deleted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while deleting the user."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	 
	 //entered users logging part 
	 public String getUser(String email,String password)
     {
         String output = "";
         try
         {
             Connection con = connect();
        
             //create a prepard statment
         String query = "select * from user where email = '" + email +"' AND password = '" + password + "'";
         
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query);
        
         if(rs.next()==true)
         {
        	 String type = rs.getString("type");
        	 System.out.println(type);
        	 output+="Loging as"+ " "+type;
            
         }
         else {
        	 output+="No user matching type";
         }
         con.close();
        
     }
     catch (Exception e)
     {
         output = "Error while reading Users.";
         System.err.println(e.getMessage());
     }
         return output;
     }

}

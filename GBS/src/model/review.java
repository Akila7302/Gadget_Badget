package model;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class review {

	
	private Connection connect() 
	 { 
		 Connection con = null; 
		 try
		 { 
		 Class.forName("com.mysql.jdbc.Driver"); 
		 
		 //Provide the correct details: DBServer/DBName, username, password 
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", ""); 
		 } 
		 catch (Exception e) 
		 {e.printStackTrace();} 
		 return con; 
	 } 
	
	
	
	
		
	
	//Insert items to table
	
	public String insertItem(String project_id,String review, String acceptance) 
	 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for inserting."; } 
		 
		 
		 // create a prepared statement
		 String query = " insert into review (`review_id`,`project_id`,`review`,`acceptance`)" + " values ( ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values 
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setInt(2, Integer.parseInt(project_id));
         preparedStmt.setString(3, review);
		 preparedStmt.setString(4, acceptance);
		 
		 
		// execute the statement3
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
	
	
	
	
	
	
	
	
	
	//retrieve items from the table
	
	public String readItems() 
	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for reading."; } 
		 
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Project _id</th>"+
		 "<th>review</th>"+
		 "<th>acceptance</th></tr>"; 
		 
		 String query = "select * from review"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 
		 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String project_id = Integer.toString(rs.getInt("project_id")); 
		 String review = rs.getString("review");
		 String acceptance = rs.getString("acceptance");
		 
		 
		 // Add into the html table 
		 output += "<tr><td>" + project_id+ "</td>"; 
		 output += "<td>" + review + "</td>";
		 output += "<td>" + acceptance + "</td>";
		 
		 
		 
		 } 
		 con.close(); 
		 
		 
		 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 }
	
	
	
	
	
	
	
	
	//update items in the table 
	
	public String updateItem(String review_id, String project_id , String review, String acceptance)
	 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 
		 
		 // create a prepared statement
		 String query = "UPDATE review SET project_id=? ,review=?,acceptance=? WHERE review_id=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(project_id));
		 preparedStmt.setString(2, review); 
		 preparedStmt.setString(3, acceptance); 
		 preparedStmt.setInt(4, Integer.parseInt(review_id));
		 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Updated successfully"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while updating the item."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 } 
	
	
	
	
	
	
	//delete items from table
	
	public String deleteItem(String ID) 
	 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for deleting."; } 
		 
		 
		 // create a prepared statement
		 String query = "delete from review where review_id=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(ID)); 
		 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Deleted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while deleting the item."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
}
	
	
}

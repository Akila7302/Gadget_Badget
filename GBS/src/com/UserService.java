package com;
import model.User; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 
@Path("/Users") 
	public class UserService 
	{ 
		User userObj = new User(); 
		@GET
		@Path("/") 
		@Produces(MediaType.TEXT_HTML) 
		public String readUser() 
		{ 
			return userObj.readUser() ;  
		} 
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertUser(@FormParam("firstName") String firstName,
			 @FormParam("lastName") String lastName,
			 @FormParam("phoneNo") String phoneNo,
			 @FormParam("email") String email,
			 @FormParam("type") String type,
			 @FormParam("password") String password)
			 
		{
			 String output = userObj.insertUser(firstName,lastName,phoneNo,email,type,password);
			return output;
		}
		
		//Update
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateUser(@FormParam("userId") String userId,
				 @FormParam("firstName") String firstName,
				 @FormParam("lastName") String lastName,
				 @FormParam("phoneNo") String phoneNo,
				 @FormParam("email") String email,
				 @FormParam("type") String type,
				 @FormParam("password") String password)
                 
		{
			//Convert the input string to a JSON object
//			 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
			
			 //Read the values from the JSON object
			 
//			 String userId = userObject.get("userId").getAsString();	
//			 String firstName = userObject.get("firstName").getAsString();
//			 String lastName = userObject.get("lastName").getAsString();
//			 String phoneNo = userObject.get("phoneNo").getAsString();
//			 String email = userObject.get("email").getAsString();
//			 String password = userObject.get("password").getAsString();
					
			 String output = userObj.updateUser(userId,firstName, lastName, phoneNo,email,type,password);
		
			 return output;
		}
		
		@DELETE
		@Path("/") 
		@Consumes(MediaType.APPLICATION_XML) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String deleteUser(String userData) 
		{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String userId = doc.select("userId").text(); 
		 String output = userObj.deleteUser(userId); 
		return output; 
		}

		
		/*@GET
	    @Path("/Email/{email}/Password/{password}")
		@Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.TEXT_HTML)
	    public String userLogin(String userData)
		{
			JsonObject userobject = new JsonParser().parse(userData).getAsJsonObject();
			
			String email = userobject.get("email").getAsString();
			String password = userobject.get("password").getAsString();
			
			String output = userObj.getUser(email,password);
			return output;
			
	    }*/
		
		@GET
	    @Path("/Email/{email}/Password/{password}")
		//@Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.TEXT_HTML)
	    public String userLogin(@PathParam("email") String email,
				@PathParam("password") String password) {
			
				return userObj.getUser(email,password);
			
	    }
	}




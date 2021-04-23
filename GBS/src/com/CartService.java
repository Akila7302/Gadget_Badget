package com;

import javax.ws.rs.core.MediaType;

import model.Cart;

//For REST Service
import javax.ws.rs.*;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/carts") 
public class CartService {
		Cart cartObj = new Cart();
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readItems(){
			
		 return cartObj.readItems();
		}	
		
		@POST
		@Path("/") 
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		@Produces(MediaType.TEXT_PLAIN) 
		public String insertItem(@FormParam("project_id") String project_id, 
		 @FormParam("project_name") String project_name, 
		 @FormParam("owner") String owner, 
		 @FormParam("description") String description, 
		@FormParam("price") String price,
		@FormParam("email") String email)
		{ 
			
			String output = cartObj.insertItem(project_id, project_name, owner, description, price, email); 
			return output; 
			
		}
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateItem(String itemData){
			
		//Convert the input string to a JSON object
		 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		//Read the values from the JSON object
		 String project_id = itemObject.get("project_id").getAsString();
		 String project_name = itemObject.get("project_name").getAsString();
		 String owner = itemObject.get("owner").getAsString();
		 String description = itemObject.get("description").getAsString();
		 String price = itemObject.get("price").getAsString();
		 String email = itemObject.get("email").getAsString();
		 
		 String output = cartObj.updateItem(project_id, project_name, owner, description, price ,email );
		
		 return output;
		}
		
		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteItem(String itemData)
		{
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		 String project_id = doc.select("project_id").text();
		 String output = cartObj.deleteItem(project_id);
		
		 return output;
		}

}

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

@Path("/Carts") 
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
		public String insertItem(@FormParam("productName") String productName, 
		 @FormParam("owner") String owner, 
		 @FormParam("description") String description, 
		@FormParam("price") String price,
		@FormParam("email") String email)
		{ 
			
			String output = cartObj.insertItem(productName, owner, description, price, email); 
			return output; 
			
		}
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateUser(@FormParam("productId") String productId,
				 @FormParam("productName") String productName,
				 @FormParam("owner") String owner,
				 @FormParam("description") String description,
				 @FormParam("price") String price,
				 @FormParam("email") String email)
				 
		{	
		//Convert the input string to a JSON object
		//JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		//Read the values from the JSON object
		// String project_id = itemObject.get("project_id").getAsString();
		// String project_name = itemObject.get("project_name").getAsString();
		// String owner = itemObject.get("owner").getAsString();
		// String description = itemObject.get("description").getAsString();
		// String price = itemObject.get("price").getAsString();
		// String email = itemObject.get("email").getAsString(); 
		 
		 String output = cartObj.updateItem(productId, productName, owner, description, price ,email );
		
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
		 String productId = doc.select("productId").text();
		 String output = cartObj.deleteItem(productId);
		
		 return output;
		}

}

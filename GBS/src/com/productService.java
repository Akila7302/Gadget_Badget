package com;
import model.product; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 




@Path("/products") 
public class productService 
{ 
	 product itemObj = new product(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	 { 
		 return itemObj.readItems();
	 } 
	
	

	 
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertItem(@FormParam("product_name") String product_name, 
	 @FormParam("owner") String owner, 
	 @FormParam("description") String description, 
	 @FormParam("price") String price, 
	 @FormParam("email") String email) 
	{ 
	 String output = itemObj.insertItem(product_name, owner,description, price, email); 
	return output; 
	}

	

	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateItem(String itemData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String product_id = itemObject.get("product_id").getAsString(); 
	 String product_name= itemObject.get("product_name").getAsString(); 
	 String owner = itemObject.get("owner").getAsString();
	 String description = itemObject.get("description").getAsString(); 
	 String price = itemObject.get("price").getAsString(); 
	 String email = itemObject.get("email").getAsString(); 
	 String output = itemObj.updateItem(product_id,product_name, owner,description, price, email); 
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
	 String product_id = doc.select(" product_id ").text(); 
	 String output = itemObj.deleteItem(product_id); 
	return output; 
	}

	
	
}


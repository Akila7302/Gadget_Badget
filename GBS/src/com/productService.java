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
	 product productObj = new product(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readproduct() 
	 { 
		 return productObj.readproduct();
	 } 
	
	

	 
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertproduct(@FormParam("product_name") String product_name, 
	 @FormParam("owner") String owner, 
	 @FormParam("description") String description, 
	 @FormParam("price") String price, 
	 @FormParam("email") String email) 
	{ 
	 String output = productObj.insertproduct(product_name, owner,description, price, email); 
	return output; 
	}

	

	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateproduct(String productData) 
	{ 
		
	//Convert the input string to a JSON object 
		
	 JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject(); 
	 
	//Read the values from the JSON object
	 
	 String product_id = productObject.get("product_id").getAsString(); 
	 String product_name= productObject.get("product_name").getAsString(); 
	 String owner = productObject.get("owner").getAsString();
	 String description = productObject.get("description").getAsString(); 
	 String price = productObject.get("price").getAsString(); 
	 String email = productObject.get("email").getAsString(); 
	 String output = productObj.updateproduct(product_id,product_name, owner,description, price, email); 
	return output; 
	}
	
	 
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteproduct(String productData) 
	{ 
		
	      //Convert the input string to an XML document
		
	 Document doc = Jsoup.parse(productData, "", Parser.xmlParser()); 
	 
	     //Read the value from the element <project_id>
	 
	 String product_id = doc.select(" product_id ").text(); 
	 String output = productObj.deleteproduct(product_id); 
	return output; 
	}

	
	
}


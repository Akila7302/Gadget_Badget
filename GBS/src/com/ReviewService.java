package com;
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*;

import model.ProjectReview;

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

import org.jsoup.Jsoup;

@Path("/ProjectReview")
public class ReviewService {
	ProjectReview reviewObj = new ProjectReview();
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readReviews() { 
		return reviewObj.readReviews() ;  
	} 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertReview(@FormParam("proj_id") String proj_id,
		 @FormParam("fundraiser_id") String fundraiser_id,
		 @FormParam("review") String review,
		 @FormParam("acceptance") String acceptance)
		 
	{
		 String output = reviewObj.insertReview(proj_id,fundraiser_id,review,acceptance);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatereview(String reviewData) {
		
		//Convert the input string to a JSON object
		 JsonObject reviewObject = new JsonParser().parse(reviewData).getAsJsonObject();
	
		 //Read the values from the JSON object
		 String review_id = reviewObject.get("review_id").getAsString();	
		 String proj_id = reviewObject.get("proj_id").getAsString();
		 String fundraiser_id = reviewObject.get("fundraiser_id").getAsString();
		 String review = reviewObject.get("review").getAsString();
		 String acceptance = reviewObject.get("acceptance").getAsString();
				
		 String output = reviewObj.updateReview(review_id,proj_id, fundraiser_id, review,acceptance);
	
		 return output;
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deletereview(String reviewData) { 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(reviewData, "", Parser.xmlParser()); 
		 
		//Read the value from the element review_id
		 String review_id = doc.select("review_id").text(); 
		 String output = reviewObj.deleteReview(review_id); 
		 
		return output; 
	}
}

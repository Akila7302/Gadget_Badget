package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Payment;

@Path("/Payments")
public class PaymentService {
	
	Payment payObj = new Payment();
	
	//Retrieve
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return payObj.readPayments();
	}
	
	
	//Insert
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPay(@FormParam("pMethod") String pMethod, @FormParam("itemName") String itemName,
			@FormParam("itemPrice") String itemPrice, @FormParam("email") String email) {

		String output = payObj.insertPay(pMethod, itemName, itemPrice, email);
		return output;
	}
	
	//Update
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePay(String paymentData) {

		// Convert the input string to a JSON object

		JsonObject payObject = new JsonParser().parse(paymentData).getAsJsonObject();

		// Read the values from the JSON object

		String PID = payObject.get("PID").getAsString();
		String pMethod = payObject.get("pMethod").getAsString();
		String itemName = payObject.get("itemName").getAsString();
		String itemPrice = payObject.get("itemPrice").getAsString();
		String email = payObject.get("email").getAsString();

		String output = payObj.updatePay(PID, pMethod, itemName, itemPrice, email);

		return output;
	}

	//Delete
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePay(String payData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(payData, "", Parser.xmlParser());
		// Read the value from the element <itemID>
		String PID = doc.select("PID").text();
		String output = payObj.deletePay(PID);
		return output;
	}

}

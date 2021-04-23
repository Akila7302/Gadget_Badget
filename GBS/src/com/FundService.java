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

import model.Fund;

@Path("/Funds")
public class FundService {

	Fund fundObj = new Fund();

	//Retrieve
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readFunds() {
		return fundObj.readFunds();
	}
	
	//Insert

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFund(@FormParam("project") String project, @FormParam("cName") String cName,
			@FormParam("fundAmount") String fundAmount) {

		String output = fundObj.insertFund(project, cName, fundAmount);
		return output;
	}

	//Upadte
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFund(String FundData) {

		// Convert the input string to a JSON object

		JsonObject fundObject = new JsonParser().parse(FundData).getAsJsonObject();

		// Read the values from the JSON object

		String FID = fundObject.get("FID").getAsString();
		String project = fundObject.get("project").getAsString();
		String cName = fundObject.get("cName").getAsString();
		String fundAmount = fundObject.get("fundAmount").getAsString();

		String output = fundObj.updateFund(FID, project, cName, fundAmount);

		return output;
	}
	
	//Delete

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteFund(String fundData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(fundData, "", Parser.xmlParser());
		// Read the value from the element <itemID>
		String FID = doc.select("FID").text();
		String output = fundObj.deleteFund(FID);
		return output;
	}

}

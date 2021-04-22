package com;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

import org.jsoup.Jsoup;

import model.ProjectModel;

@Path("/ProjectModel")
public class ProjectService {
	ProjectModel projectObj = new ProjectModel(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readProjects() 
	{ 
		return projectObj.readProjects() ;  
	} 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProject(
		 @FormParam("project_title") String project_title,
		 @FormParam("p_description") String p_description,
		 @FormParam("inventor_name") String inventor_name,
		 @FormParam("delivery_time") String delivery_time,
		 @FormParam("project_cost") String project_cost)
		 
	{
		 String output = projectObj.insertProject(project_title,p_description,inventor_name,delivery_time,project_cost);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateproject(String projectData) {
		
		//Convert the input string to a JSON object
		 JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject();
	
		 //Read the values from the JSON object
		 String project_id = projectObject.get("project_id").getAsString();	
		 String project_title = projectObject.get("project_title").getAsString();
		 String p_description = projectObject.get("p_description").getAsString();
		 String inventor_name = projectObject.get("inventor_name").getAsString();
		 String delivery_time = projectObject.get("delivery_time").getAsString();
		 String project_cost = projectObject.get("project_cost").getAsString();
				
		 String output = projectObj.updateProject(project_id,project_title, p_description, inventor_name,delivery_time,project_cost);
	
		 return output;
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteproject(String projectData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(projectData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <project_id>
	 String project_id = doc.select("project_id").text(); 
	 String output = projectObj.deleteProject(project_id); 
	return output; 
	}
}

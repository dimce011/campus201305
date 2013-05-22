package org.infobip.mpayments.help.repo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface RestAPI {

	
	/*@GET
	@Path("/getPage")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
						 @QueryParam("reseller") String reseller,  @QueryParam("language") String language);*/
	
	@GET
	@Path("/getPage/document/{id}/content")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@PathParam("id") String id);
	
	@GET
	@Path("/getPage/document/{id}/content")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
			@QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
}

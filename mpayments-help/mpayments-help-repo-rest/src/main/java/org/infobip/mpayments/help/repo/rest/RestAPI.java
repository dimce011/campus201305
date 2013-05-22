package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Local
@Path("/")
public interface RestAPI {

	
	/*@GET
	@Path("/getPage")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
						 @QueryParam("reseller") String reseller,  @QueryParam("language") String language);*/
	
	@GET
	@Path("/getPageById/document/{id}/content")
	@Produces(MediaType.TEXT_HTML)
	public String getPageById(@PathParam("id") String id);
	
	@GET
	@Path("/getPage")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
			@QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
	@GET
	@Path("/getParagraph/document/{app}/{topic}/content/{parID}")
	@Produces(MediaType.TEXT_HTML)
	public String getParagraph(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("parID") String parID, @QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
	@GET
	@Path("/getPage/document/{app}/{topic}/content/{reseller}/{language}")
	@Produces(MediaType.TEXT_HTML)
	public String getDoc(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("reseller") String reseller, @PathParam("language") String language);

}
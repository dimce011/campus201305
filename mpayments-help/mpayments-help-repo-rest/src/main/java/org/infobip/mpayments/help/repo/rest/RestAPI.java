package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/")
public interface RestAPI {

	
	/*@GET
	@Path("/getPage")
	@Produces(MediaType.TEXT_HTML)
	public String getPage(@QueryParam("app") String app, @QueryParam("topic") String topic,
						 @QueryParam("reseller") String reseller,  @QueryParam("language") String language);*/
	
	@GET
	@Path("/getPageById/{id}/content")
	@Produces(MediaType.TEXT_HTML)
	public Response getPageById(@PathParam("id") String id);
	
	@GET
	@Path("/getPage")
	@Produces(MediaType.TEXT_HTML)
	public Response getPage(@QueryParam("app") String app, @QueryParam("topic") String topic, @QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
	@GET
	@Path("/getParagraph/{app}/{topic}/content/{parID}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("parID") String parID, @QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
	@GET
	@Path("/getPage/{app}/{topic}/content/{reseller}/{language}")
	@Produces(MediaType.TEXT_HTML)
	public Response getDoc(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("reseller") String reseller, @PathParam("language") String language);

}

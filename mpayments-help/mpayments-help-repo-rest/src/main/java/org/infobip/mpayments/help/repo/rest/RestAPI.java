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
	
	@GET
	@Path("/documents/{id}/content")
	@Produces(MediaType.TEXT_HTML)
	public Response getDocuments(@PathParam("id") String id);
	

	@GET
	@Path("/getParagraph/{app}/{topic}/content/{parID}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("parID") String parID, @QueryParam("reseller") String reseller, @QueryParam("language") String language);
	
	
	@GET
	@Path("/help/{app}/{topic}/content")
	@Produces(MediaType.TEXT_HTML)
	public Response getDocument(@PathParam("app") String app, @PathParam("topic") String topic, @QueryParam("language") String language, @QueryParam("reseller") String reseller);
	
//  za staro drvo!
//	@GET
//	//@Path("/{apppath}/content")
//	@Path("/{rPath:.*}/content")
//	@Produces(MediaType.TEXT_HTML)
//	public Response getDoc(@PathParam("rPath") String rPath);

}

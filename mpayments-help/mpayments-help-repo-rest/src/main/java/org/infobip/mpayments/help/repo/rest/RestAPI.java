package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Local
@Path("/")
public interface RestAPI {
	
//	@GET
//	@Path("/documents/{id}/content")
//	@Produces(MediaType.TEXT_HTML)
//	public Response getDocuments(@PathParam("id") String id);
//	

	@GET
	@Path("/documents/{docPath:.*}/content/paragraph/{parID}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("docPath") String docPath, @PathParam("parID") String parID, @Context UriInfo ui);

	@GET
	@Path("/documents/{docPath:.*}/content/paragraph/{parID}/{fieldPars}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("docPath") String docPath, @PathParam("parID") String parID, @PathParam("fieldPars") String fieldPars, @Context UriInfo ui);
	
	//@Path("/mypath{param1 : (/param1)?}") {fuel : (/fuel/[^/]+)?}
	@GET
	//@Path("/{app}/{topic}/content{fieldPars : (/fieldPars)?}")
	@Path("/documents/{docPath:.*}/content/{fieldPars}")
	@Produces(MediaType.TEXT_HTML)
	public Response getDocument(@PathParam("docPath") String docPath, @PathParam("fieldPars") String fieldPars, @Context UriInfo ui);
	
	@GET
	//@Path("/{app}/{topic}/content{fieldPars : (/fieldPars)?}")
	@Path("/documents/{docPath:.*}/content")
	@Produces(MediaType.TEXT_HTML)
	public Response getDocument(@PathParam("docPath") String docPath, @Context UriInfo ui);
	
	@DELETE
	@Path("/documents/{docPath:.*}/{fieldPars}")
	@Produces(MediaType.TEXT_HTML)
	public Response delDocument(@PathParam("docPath") String docPath, @PathParam("fieldPars") String fieldPars);
	
	@GET
	@Path("/documents/{docPath:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJSON(@PathParam("docPath") String docPath, @QueryParam("language") String language, @QueryParam("reseller") String reseller, @Context UriInfo ui);
	
	
	@DELETE
	@Path("/documents/{docPath:.*}")
	@Produces(MediaType.TEXT_HTML)
	public Response delDocument(@PathParam("docPath") String docPath);
	
//	@GET
//	@Path("/documents/{docPath:.*}/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getJSON(@PathParam("docPath") String docPath);
	
	

	
//  za staro drvo!
//	@GET
//	//@Path("/{apppath}/content")
//	@Path("/{rPath:.*}/content")
//	@Produces(MediaType.TEXT_HTML)
//	public Response getDoc(@PathParam("rPath") String rPath);
	

}

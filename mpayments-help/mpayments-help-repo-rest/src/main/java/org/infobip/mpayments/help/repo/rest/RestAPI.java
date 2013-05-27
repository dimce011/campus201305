package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.ws.rs.DELETE;
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
	@Path("/documents/getParagraph/{app}/{topic}/content/{parID}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("parID") String parID);

	@GET
	@Path("/documents/getParagraph/{app}/{topic}/content/{parID}/{fieldPars}")
	@Produces(MediaType.TEXT_HTML)
	public Response getParagraph(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("parID") String parID, @PathParam("fieldPars") String fieldPars);
	
	//@Path("/mypath{param1 : (/param1)?}") {fuel : (/fuel/[^/]+)?}
	@GET
	//@Path("/{app}/{topic}/content{fieldPars : (/fieldPars)?}")
	@Path("help/{app}/{topic}/content/{fieldPars}")

	@Produces(MediaType.TEXT_HTML)
	public Response getDocument(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("fieldPars") String fieldPars);
	
	@GET
	//@Path("/{app}/{topic}/content{fieldPars : (/fieldPars)?}")
	@Path("help/{app}/{topic}/content")
	@Produces(MediaType.TEXT_HTML)
	public Response getDocument(@PathParam("app") String app, @PathParam("topic") String topic);
	
	@DELETE
	@Path("help/{app}/{topic}/{fieldPars}")
	@Produces(MediaType.TEXT_HTML)
	public Response delDocument(@PathParam("app") String app, @PathParam("topic") String topic, @PathParam("fieldPars") String fieldPars);
	
	@DELETE
	@Path("help/{app}/{topic}/")
	@Produces(MediaType.TEXT_HTML)
	public Response delDocument(@PathParam("app") String app, @PathParam("topic") String topic);
	
//  za staro drvo!
//	@GET
//	//@Path("/{apppath}/content")
//	@Path("/{rPath:.*}/content")
//	@Produces(MediaType.TEXT_HTML)
//	public Response getDoc(@PathParam("rPath") String rPath);

}

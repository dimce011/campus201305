package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
public interface RestHelpRepo {

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response test(@Context HttpServletRequest request,
			@QueryParam("action") String action);

	@GET
	@Path("old/sayHello")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello();

	@GET
	@Path("old/document")
	@Produces(MediaType.TEXT_HTML)
	public String getDocument(@QueryParam("language") String language,
			@QueryParam("test") String test);

	@GET
	@Path("old/testJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJSON();

	@GET
	@Path("old/getOneLevelJSON/{nodePath:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneLevelJSON(@PathParam("nodePath") String nodePath);

	@GET
	@Path("old/root/{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLinksJSON(@PathParam("path") String path,
			@QueryParam("language") String language,
			@QueryParam("reseller") String reseller, @Context UriInfo ui);

	@GET
	@Path("old/documents/{parent:.*}/children/{fieldPars}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildrenLinksJSON(@PathParam("parent") String parent,
			@PathParam("fieldPars") String fieldPars,
			@QueryParam("language") String language,
			@QueryParam("reseller") String reseller, @Context UriInfo ui);
	
	@GET
	@Path("old/documents/{parent:.*}/children/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChildrenLinksJSON2(@PathParam("parent") String parent,
			@QueryParam("language") String language,
			@QueryParam("reseller") String reseller, @Context UriInfo ui);


	@POST
	@Path("/getSaveStatus")
	@Produces(MediaType.TEXT_PLAIN)
   // @Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response getSaveStatus(@QueryParam("node_path") String node_path,
			@QueryParam("html_page") String html_page,
			@QueryParam("f_name") String f_name,
			@QueryParam("is_file") String is_file,
			@QueryParam("to_save") String to_save,
			@QueryParam("language") String language,
			@QueryParam("reseller") String reseller);

}

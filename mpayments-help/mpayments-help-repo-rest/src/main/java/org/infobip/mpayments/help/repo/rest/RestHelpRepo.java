package org.infobip.mpayments.help.repo.rest;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Exposed to Merchants - users of Centili premium API
 * 
 * @author sivkovic
 * 
 */

@Local
@Path("/")
public interface RestHelpRepo {

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response test(@Context HttpServletRequest request);

}
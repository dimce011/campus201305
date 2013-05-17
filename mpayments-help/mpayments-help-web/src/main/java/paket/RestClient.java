package paket;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sivkovic, nilic
 * @
 *
 */
public class RestClient<A> {
	
	static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	
	public RestClient() {
	
	}
	
	public A postRequest(String uri, Map<String, String> params, String contentType, Class<A> type) {
		logger.debug("post request for URL: {}", uri);
		ClientRequest client = null;
		ClientResponse<A> response = null;
		try {
			client = new ClientRequest(uri);
			client.header("Content-Type", contentType);
			
			//Setting form parameters
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					client.formParameter(entry.getKey(), entry.getValue());
				}
			}
			
			response = client.post(type);
			
			if (response.getStatus() != 200) {
				logger.warn("Failed : HTTP error code : " + response.getStatus() + ", responseStatus: " + response.getResponseStatus());
			}
			
			return response.getEntity();
			
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (client != null) {
				client.clear();
			}
			if (response != null) {
				response.releaseConnection();
			}
		}
		
		return (A)null;
	}

	public A getRequest(String uri, Map<String, String> params, Class<A> type) {
		System.out.println(uri);
		logger.debug("get request for URL: {}", uri);
		ClientRequest client = null;
		ClientResponse<A> response = null;
		try {
			client = new ClientRequest(uri);
			
			//Setting form parameters
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					client.queryParameter(entry.getKey(), entry.getValue());
				}
			}
			
			response = client.get(type);
			
			if (response.getStatus() != 200) {
				logger.warn("Failed : HTTP error code : " + response.getStatus() + ", responseStatus: " + response.getResponseStatus());
			}
		
			return response.getEntity();
			
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (client != null) {
				client.clear();
			}
			if (response != null) {
				response.releaseConnection();
			}
		}
		
		return (A)null;
	}

}

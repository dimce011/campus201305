package org.infobip.mplatform.help.it.business;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;

import org.infobip.mplatform.help.it.repo.vo.TestResponseVO;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestHelpRepoServiceIT  {

	static final Logger logger = LoggerFactory.getLogger(RestHelpRepoServiceIT.class);


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
    }

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    
    }

	@Test
	public void testOnce() {

		ClientRequest client = null;
		ClientResponse<TestResponseVO> response = null;
		
		String contentType = MediaType.APPLICATION_FORM_URLENCODED;
		String uri = "http://localhost:8080/helprepo/test";
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			client = new ClientRequest(uri);
			client.header("Content-Type", contentType);
			
			//Setting form parameters
			if (params != null) {
				for (Entry<String, String> entry : params.entrySet()) {
					client.formParameter(entry.getKey(), entry.getValue());
				}
			}
			
			response = client.get(TestResponseVO.class);
			
			TestResponseVO result = response.getEntity();
			
			if (response.getStatus() != 200) {
				logger.warn("Failed : HTTP error code : " + response.getStatus() + ", message: " + (result == null ? "null" : result.getErrorMessage()));
			} else {
				logger.info("Result: " + result);
			}
			
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
		
	}
	
}

import javax.ws.rs.core.MediaType;

import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.rest.service.MathPlayNLearnServiceResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class RestClient {

	public static void main(String[] args) {

		User user = new User();
		user.setUserID("sumit1");
		user.setEmail("s@b1.com");
		user.setPassword("sumit1234");
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		
		/*WebResource resource = client.resource("http://localhost:8080/MathPlayNLearn/rest/math-play-service/register-new-user");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);*/
	    
		/*WebResource resource = client.resource("http://localhost:8080/MathPlayNLearn/rest/math-play-service/sign-in");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);*/
		
		/*WebResource resource = client.resource("http://localhost:8080/MathPlayNLearn/rest/math-play-service/search-user?userIDString=amo");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	    System.out.println(response.getEntity(String.class));*/
		
		WebResource resource = client.resource("http://localhost:8080/MathPlayNLearn/rest/math-play-service/recover-password");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, "amit2");
		
		MathPlayNLearnServiceResponse serviceResponse = response.getEntity(MathPlayNLearnServiceResponse.class);
	    System.out.println(serviceResponse.getMessage());
	    
	}
}

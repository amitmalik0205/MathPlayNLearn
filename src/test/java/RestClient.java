import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.rest.service.MathPlayNLearnServiceResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class RestClient {

	public static void main(String[] args) throws JSONException {

		SecurityQuestion question = new SecurityQuestion();
		question.setQuestionId(1L);
		
		User user = new User();
		user.setUserID("amit1");
		user.setPassword("amit123");
		user.setAnswer("I S Malik");
		
		user.setSecurityQuestion(question);
		
/*		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userID", "amit1");
		jsonObject.put("password", "amit123");
		jsonObject.put("answer", "I S Malik");
		
		JSONObject jsonQuestion = new JSONObject();
		jsonQuestion.put("questionId", 1);
		
		jsonObject.put("securityQuestion", jsonQuestion);
		
		System.out.println(jsonObject);*/
		
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/get-security-questions");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		
		/*WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/register-new-user");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);*/
	    
		/*WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/sign-in");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);*/
		
		/*WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/search-user?userIDString=amo");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	    System.out.println(response.getEntity(String.class));*/
		
		/*WebResource resource = client.resource("http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/recover-password");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, "streetboy03");*/
		
		/*WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/create-group?userID=sumit3&groupName=sumit3g1");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));*/
		
	/*	MathPlayNLearnServiceResponse serviceResponse = response.getEntity(MathPlayNLearnServiceResponse.class);
	    System.out.println(serviceResponse.getMessage());*/
	    
	}
}

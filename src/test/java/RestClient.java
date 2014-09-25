


public class RestClient {

	/*public static void main(String[] args) throws JSONException, JAXBException, IOException {

		SecurityQuestion question = new SecurityQuestion();
		question.setQuestionId(1L);
		
		User user = new User();
		user.setUserID("amit1");
		user.setPassword("amit123");
		user.setAnswer("i sMalik");
		
		user.setSecurityQuestion(question);
		
		
		Customer customer = new Customer();
		customer.setId(42);
		customer.setName("Amit Malik");
		customer.setAge(28);
		
		Address address = new Address();
		address.setCity("Delhi");
		address.setCountry("India");
		
		customer.setAddress(address);
		
		JAXBContext ctx = JAXBContext.newInstance(Customer.class);
		StringWriter writer = new StringWriter();
		ctx.createMarshaller().marshal(customer, writer);
		String custString = writer.toString();
		customer = (Customer) ctx.createUnmarshaller().unmarshal(new StringReader(custString));
		
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("userID", "amit1");
		jsonUser.put("password", "amit123");
		jsonUser.put("answer", "I S Malik");
		
		JSONObject jsonQuestion = new JSONObject();
		jsonQuestion.put("questionId", 1);
		
		jsonUser.put("securityQuestion", jsonQuestion);
		
		System.out.println(jsonUser.toString());
		
		URL url = new URL("http://localhost:8081/MathPlayNLearn/rest/math-play-service/recover-password");
		HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestMethod("POST");
	     
	    OutputStream os = conn.getOutputStream();
		os.write(jsonUser.toString().getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		conn.disconnect();
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/get-security-questions");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/register-new-user");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);
	    
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/sign-in");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);
		
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/search-user?userIDString=amo");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
	    System.out.println(response.getEntity(String.class));
		
		WebResource resource = client.resource("http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/recover-password");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, user);
		
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/create-group?userID=sumit3&groupName=sumit3g1");
		ClientResponse response = resource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
		
		MathPlayNLearnServiceResponse serviceResponse = response.getEntity(MathPlayNLearnServiceResponse.class);
	    System.out.println(serviceResponse.getMessage());
	    
		WebResource resource = client.resource("http://localhost:8081/MathPlayNLearn/rest/math-play-service/xml-test");
		ClientResponse response = resource.type(MediaType.APPLICATION_XML).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
	}*/
}

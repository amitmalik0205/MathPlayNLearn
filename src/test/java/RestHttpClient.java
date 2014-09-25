import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.domain.User;



public class RestHttpClient {

	public static void main(String[] args) throws JAXBException, IOException {

		SecurityQuestion question = new SecurityQuestion();
		question.setQuestionId(1L);
		
		User user = new User();
		user.setUserID("amit1");
		user.setPassword("amit123");
		user.setAnswer("i sMalik");
		
		user.setSecurityQuestion(question);
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(user));
		
		
		URL url = new URL("http://localhost:8081/MathPlayNLearn/rest/math-play-service/recover-password");
		HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestMethod("POST");
	     
	    OutputStream os = conn.getOutputStream();
	    String content = mapper.writeValueAsString(user);
		os.write(content.getBytes());
		os.flush();
		
		conn.connect();
		
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
	}
}

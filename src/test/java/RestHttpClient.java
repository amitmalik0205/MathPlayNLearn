import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.domain.GameDetails;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.rest.dto.GameDetailsDTO;



public class RestHttpClient {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/save-game-score";
		
		User user = new User();
		user.setUserID("amit1");
		
		Game game = new Game();
		game.setGameName("Addition");
		game.setGameClass("Class 1");
		
		GameDetailsDTO  details = new GameDetailsDTO();
		details.setLevel("Easy");
		details.setUserScore(500);
		details.setUserID("amit2");
		details.setGameName("Addition");
		details.setGameClass("Class 2");
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(details);
		System.out.println(content);

		sendRequest(url, content, "POST");
	}
	
	public static void  sendRequest(String urlStr, String param, String method) throws IOException {
		
		URL url = new URL(urlStr);
		
		HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestMethod(method);
	     
	    if(method.equals("POST")) {
	    	OutputStream os = conn.getOutputStream();
			os.write(param.getBytes());
			os.flush();
	    }
		
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

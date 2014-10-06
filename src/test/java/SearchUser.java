import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class SearchUser {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://localhost:8081/MathPlayNLearn/rest/math-play-service/search-user?userIDString=amit&groupID=24";
		
		TestUtil.sendRequest(url, "", "GET");
		
	}

}

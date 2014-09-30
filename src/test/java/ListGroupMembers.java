import java.io.IOException;

import javax.xml.bind.JAXBException;


public class ListGroupMembers {


	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/get-group-members/10";

		TestUtil.sendRequest(url, null, "GET");
		
	}

}

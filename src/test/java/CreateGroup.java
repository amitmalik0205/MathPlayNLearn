import java.io.IOException;

import javax.xml.bind.JAXBException;


public class CreateGroup {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/create-group?userID=amit1&groupName=group2";

		TestUtil.sendRequest(url, null, "GET");
		
	}


}

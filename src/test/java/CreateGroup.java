import java.io.IOException;

import javax.xml.bind.JAXBException;


public class CreateGroup {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://localhost:8081/MathPlayNLearn/rest/math-play-service/create-group?userID=tny&groupName=tny2";

		TestUtil.sendRequest(url, null, "GET");
		
	}


}

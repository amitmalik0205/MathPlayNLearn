import java.io.IOException;

import javax.xml.bind.JAXBException;


public class UserTotalScore {


	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://localhost:8081/MathPlayNLearn/rest/math-play-service/get-total-user-score/24";

		TestUtil.sendRequest(url, null, "GET");
		
	}

}

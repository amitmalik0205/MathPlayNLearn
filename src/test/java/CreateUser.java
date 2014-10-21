import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.domain.User;


public class CreateUser {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://localhost:8081/MathPlayNLearn/rest/math-play-service/register-new-user";
		
		SecurityQuestion question = new SecurityQuestion();
		question.setQuestionId(1L);
		
		User user = new User();
		user.setUserID("amit0205");
		user.setAnswer("Answer");
		user.setPassword("1234");
		user.setCity("Delhi");
		user.setCountry("India");
		user.setName("Amit");
		user.setSecurityQuestion(question);
		
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(user);
		System.out.println(content);

		TestUtil.sendRequest(url, content, "POST");
		
	}


}

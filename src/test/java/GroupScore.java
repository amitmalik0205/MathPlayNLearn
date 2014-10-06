import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qait.mathplaynlearn.dto.GroupScoreDTO;


public class GroupScore {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://localhost:8081/MathPlayNLearn/rest/math-play-service/get-group-score";
		
		GroupScoreDTO dto = new GroupScoreDTO();
		dto.setGameName("Addition");
		dto.setGameClass("Class 2");
		dto.setGroupID(24L);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(dto);
		System.out.println(content);

		TestUtil.sendRequest(url, content, "POST");
		
	}




}

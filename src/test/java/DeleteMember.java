import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qait.mathplaynlearn.dto.GroupMemberDTO;


public class DeleteMember {

	public static void main(String[] args) throws JAXBException, IOException {
		
		String url = "http://172.16.9.35:8081/MathPlayNLearn/rest/math-play-service/delete-member-from-group";
		
		GroupMemberDTO memberDTO = new GroupMemberDTO();
		memberDTO.setGroupID(10);
		memberDTO.setUserKey(11);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(memberDTO);
		System.out.println(content);

		TestUtil.sendRequest(url, content, "POST");
		
	}


}

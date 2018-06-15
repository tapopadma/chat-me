package chat.me.dao.spec;

import java.util.List;

import chat.me.dto.UserIpTrnDto;

public interface UserIpDao {

	List<UserIpTrnDto> getAllDtos();
	
	void insert(String ip);
}

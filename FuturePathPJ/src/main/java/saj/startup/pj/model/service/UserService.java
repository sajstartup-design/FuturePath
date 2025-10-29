package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dto.UserDto;

@Service
public interface UserService {

	public void saveUser(UserDto inDto) throws Exception;
	
	public UserDto getUserOverview() throws Exception;
	
	public UserDto getAllUsers(UserDto inDto) throws Exception;
	
	public UserEntity getUserActive() throws Exception;
}

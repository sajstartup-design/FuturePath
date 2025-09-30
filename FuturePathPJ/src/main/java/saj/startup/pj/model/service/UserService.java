package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.UserDto;

@Service
public interface UserService {

	public void saveUser(UserDto inDto) throws Exception;
}

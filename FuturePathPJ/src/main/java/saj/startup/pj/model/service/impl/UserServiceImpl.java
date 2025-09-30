package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.logic.UserLogic;
import saj.startup.pj.model.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserLogic userLogic;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void saveUser(UserDto inDto) throws Exception {
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		
		UserEntity newUser = new UserEntity();
		
		newUser.setFirstName(inDto.getFirstName());
		newUser.setLastName(inDto.getLastName());
		newUser.setEmail(inDto.getEmail());
		newUser.setPhone(inDto.getPhone());
		newUser.setGender(inDto.getGender());
		newUser.setUsername(inDto.getUsername());
		newUser.setPassword(encoder.encode(inDto.getPassword()));
		newUser.setIsActive(true);
		newUser.setIsDeleted(false);
		newUser.setCreatedAt(timeNow);
		
		userLogic.saveUser(newUser);
	}

}

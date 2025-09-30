package saj.startup.pj.model.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UserEntity;

@Service
public interface UserLogic {

	public void saveUser(UserEntity entity);
	
	public Page<UserEntity> getAllUsers(Pageable pageable, String search);
}

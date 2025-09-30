package saj.startup.pj.model.logic;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UserEntity;

@Service
public interface UserLogic {

	public void saveUser(UserEntity entity);
}

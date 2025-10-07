package saj.startup.pj.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.UserDao;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.entity.UserOverviewData;
import saj.startup.pj.model.logic.UserLogic;

@Service
public class UserLogicImpl implements UserLogic{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void saveUser(UserEntity entity) {
		
		userDao.save(entity);
		
	}

	@Override
	public Page<UserEntity> getAllUsers(Pageable pageable, 
			String search) {
	
		return userDao.getAllUsers(pageable, search);
	}

	@Override
	public UserOverviewData getUserOverview() {
		
		return userDao.getUserOverview();
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		
		return userDao.getUserByUsername(username);
	}

}

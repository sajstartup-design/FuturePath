package saj.startup.pj.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import saj.startup.pj.model.dao.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>{

}

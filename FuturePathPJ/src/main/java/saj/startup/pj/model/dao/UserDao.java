package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>{

	public final String GET_ALL_USERS = "SELECT u "
			+ "FROM UserEntity u "
			+ "WHERE u.isDeleted = false "
			+ "AND ( "
		    + "   (:search IS NOT NULL AND :search <> '' AND ( " 
		    + "       LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(u.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(u.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(u.gender) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		    + "       LOWER(CAST(u.isActive AS CHARACTER)) LIKE LOWER(CONCAT('%', :search, '%'))" 
		    + "   )) " 
		    + "   OR (:search IS NULL OR :search = '') " 
		    + ")";
	
	@Query(GET_ALL_USERS)
	public Page<UserEntity> getAllUsers(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
}

package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.entity.UserOverviewData;

public interface UserDao extends JpaRepository<UserEntity, Integer>{
	
	public final String GET_USERS_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.UserOverviewData( " +
		    "CAST(COUNT(e) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.isActive = false THEN 1 ELSE 0 END) AS INTEGER) " +
		    ") " +
		    "FROM UserEntity e "
		    + "WHERE e.isDeleted = false ";
	
	@Query(GET_USERS_OVERVIEW)
	public UserOverviewData getUserOverview() throws DataAccessException;


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

package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.entity.UserOverviewData;
import saj.startup.pj.model.dto.UserDto;
import saj.startup.pj.model.logic.UserLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.UserObj;
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
		newUser.setRole(CommonConstant.ROLE_USER);
		
		userLogic.saveUser(newUser);
	}
	
	@Override
	public UserDto getUserOverview() throws Exception {
		
		UserDto outDto = new UserDto();
		
		UserOverviewData overview = userLogic.getUserOverview();
		
		outDto.setOverview(overview);
		
		return outDto;
	}

	@Override
	public UserDto getAllUsers(UserDto inDto) throws Exception {
		
		UserDto outDto = new UserDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.USER_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<UserEntity> allUsers = userLogic.getAllUsers(pageable, filter.getSearch());
		
		List<UserObj> users = new ArrayList<>();
		
		for (UserEntity user : allUsers) {
		    UserObj obj = new UserObj();

		    obj.setIdPk(user.getIdPk());
		    obj.setFirstName(user.getFirstName());
		    obj.setLastName(user.getLastName());
		    obj.setEmail(user.getEmail());
		    obj.setPhone(user.getPhone());
		    obj.setGender(user.getGender());
		    obj.setUsername(user.getUsername());
		    obj.setIsActive(user.getIsActive());
		    obj.setCreatedAt(user.getCreatedAt());
		    
		    users.add(obj);
		}
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(allUsers.getNumber());
		pagination.setTotalPages(allUsers.getTotalPages());
		pagination.setTotalElements(allUsers.getTotalElements());
		pagination.setHasNext(allUsers.hasNext());
		pagination.setHasPrevious(allUsers.hasPrevious());
		
		outDto.setUsers(users);
		outDto.setPagination(pagination);

		return outDto;
	}

	@Override
	public UserEntity getUserActive() throws Exception {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	        throw new Exception("No user is currently logged in");
	    }

	    Object principal = authentication.getPrincipal();

	    if (principal instanceof UserDetails userDetails) {
	        UserEntity user = userLogic.getUserByUsername(userDetails.getUsername());
	        if (user == null) {
	            throw new Exception("User not found");
	        }
	        return user;
	    } else {
	        throw new Exception("Unknown principal type");
	    }
	}

	@Override
	public UserDto getUser(UserDto inDto) throws Exception {
		
		UserDto outDto = new UserDto();
		
		UserEntity user = userLogic.getUser(inDto.getIdPk());
		
		UserObj obj = new UserObj();
		
		obj.setIdPk(user.getIdPk());
	    obj.setFirstName(user.getFirstName());
	    obj.setLastName(user.getLastName());
	    obj.setEmail(user.getEmail());
	    obj.setPhone(user.getPhone());
	    obj.setGender(user.getGender());
	    obj.setUsername(user.getUsername());
		
		outDto.setUser(obj);	
		
		return outDto;
	}

	@Override
	public void updateUser(UserDto inDto) throws Exception {
		
		UserEntity existing = userLogic.getUser(inDto.getIdPk());
		
		if(existing == null) {
			throw new Exception("User not found with ID: " + inDto.getIdPk());
		}
		
		existing.setFirstName(inDto.getFirstName());
		existing.setLastName(inDto.getLastName());
		existing.setEmail(inDto.getEmail());
		existing.setPhone(inDto.getPhone());
		existing.setGender(inDto.getGender());
		existing.setUsername(inDto.getUsername());
		existing.setPassword(inDto.getPassword());
		
		userLogic.saveUser(existing);		
		
	}

	@Override
	public void deleteUser(UserDto inDto) throws Exception {
		
		userLogic.deleteUser(inDto.getIdPk());
		
	}

	
	

}

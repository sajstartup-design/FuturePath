package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.UserOverviewData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.UserObj;

@Data
public class UserDto {
	
	private int idPk;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String username;
	
	private String password;
	
	private UserOverviewData overview;
	
	private List<UserObj> users;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
	
	private UserObj user;
}

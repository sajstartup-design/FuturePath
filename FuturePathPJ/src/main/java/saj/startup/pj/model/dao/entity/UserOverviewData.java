package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class UserOverviewData {
	
	private int totalUsers;
	
	private int totalActiveUsers;
	
	private int totalInactiveUsers;
}

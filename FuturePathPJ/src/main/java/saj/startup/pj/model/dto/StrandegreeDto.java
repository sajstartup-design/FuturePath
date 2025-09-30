package saj.startup.pj.model.dto;

import lombok.Data;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;

@Data
public class StrandegreeDto {

	private String name;
	
	private String code;
	
	private String category;
	
	private String details;
	
	private StrandegreeOverviewData overview;
}

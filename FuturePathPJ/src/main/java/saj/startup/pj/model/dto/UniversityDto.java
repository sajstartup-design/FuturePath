package saj.startup.pj.model.dto;

import java.util.HashMap;

import lombok.Data;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;

@Data
public class UniversityDto {
	
	private String universityName;
	
	private String category;
	
	private String contact;
	
	private String street;
	
	private String city;
	
	private String province;
	
	private String postalCode;

	private HashMap<Integer, Boolean> strandegreesAvailability;
	
	private UniversityOverviewData overview;
}

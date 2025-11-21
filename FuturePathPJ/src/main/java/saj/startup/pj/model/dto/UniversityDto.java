package saj.startup.pj.model.dto;

import java.util.HashMap;
import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.UniversityData;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.UniversityObj;

@Data
public class UniversityDto {
	
	private int idPk;
	
	private String universityName;
	
	private String category;
	
	private String contact;
	
	private String street;
	
	private String city;
	
	private String province;
	
	private String postalCode;
	
	private int ranking;

	private HashMap<Integer, Boolean> strandegreesAvailability;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
	
	private List<UniversityData> universities;
	
	private List<UniversityObj> allUniversities;
	
	private UniversityOverviewData overview;
	
	private String founded;
	
	private String students;
	
	private String motto;
	
	private UniversityObj university;
}

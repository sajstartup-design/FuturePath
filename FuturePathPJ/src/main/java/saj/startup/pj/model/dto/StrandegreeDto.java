package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeQuestionData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.StrandegreeObj;

@Data
public class StrandegreeDto {

	private String name;
	
	private String code;
	
	private String category;
	
	private String details;
	
	private StrandegreeOverviewData overview;
	
	private List<StrandegreeObj> strandegrees;
	
	private List<StrandegreeQuestionData> strandegreesQuestions;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
}

package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.QuestionObj;

@Data
public class QuestionDto {

	public String category;
	
	private int strandegreeIdPk;
	
	private String question;
	
	private List<String> answers;
	
	private QuestionOverviewData overview;
	
	private List<QuestionObj> questions;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
	
}

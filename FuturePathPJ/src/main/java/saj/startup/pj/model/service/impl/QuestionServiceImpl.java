package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.AnswerEntity;
import saj.startup.pj.model.dao.entity.QuestionData;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.QuestionObj;
import saj.startup.pj.model.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionLogic questionLogic;

	@Override
	public void saveQuestion(QuestionDto inDto) throws Exception {
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		
		QuestionEntity newQuestion = new QuestionEntity();
		
		newQuestion.setQuestion(inDto.getQuestion());
		newQuestion.setStrandegreeIdPk(inDto.getStrandegreeIdPk());
		newQuestion.setIsActive(true);
		newQuestion.setIsDeleted(false);
		newQuestion.setCreatedAt(timeNow);
		
		int questionIdPk = questionLogic.saveQuestion(newQuestion);
		
		List<AnswerEntity> answers = new ArrayList<>();
		
		for(String answer : inDto.getAnswers()) {
			
			AnswerEntity newAnswer = new AnswerEntity();
			
			newAnswer.setQuestionIdPk(questionIdPk);
			newAnswer.setAnswer(answer);
			newAnswer.setIsActive(true);
			newAnswer.setIsDeleted(false);
			newAnswer.setCreatedAt(timeNow);
			
			answers.add(newAnswer);
			
		}
		
		questionLogic.saveAnswers(answers);
	}

	@Override
	public QuestionDto getQuestionOverview() throws Exception {
		
		QuestionDto outDto = new QuestionDto();
		
		QuestionOverviewData overview = questionLogic.getQuestionOverview();
		
		outDto.setOverview(overview);
		
		return outDto;
	}

	@Override
	public QuestionDto getAllQuestions(QuestionDto inDto) throws Exception {
		
		QuestionDto outDto = new QuestionDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.QUESTION_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<QuestionData> allQuestions = questionLogic.getAllQuestions(pageable, filter.getSearch());
		
		List<QuestionObj> questions = new ArrayList<>();
		
		for (QuestionData question : allQuestions) {
			QuestionObj obj = new QuestionObj();
			
			obj.setIdPk(question.getQuestionIdPk());
			obj.setCategory(question.getCategory());
			obj.setQuestion(question.getQuestion());
			obj.setStrandegree(question.getStrandegree());
			obj.setIsActive(question.getIsActive());
		    obj.setCreatedAt(question.getCreatedAt());
		    
		    questions.add(obj);
		}
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(allQuestions.getNumber());
		pagination.setTotalPages(allQuestions.getTotalPages());
		pagination.setTotalElements(allQuestions.getTotalElements());
		pagination.setHasNext(allQuestions.hasNext());
		pagination.setHasPrevious(allQuestions.hasPrevious());
		
		outDto.setQuestions(questions);
		outDto.setPagination(pagination);

		return outDto;
	}

	@Override
	public QuestionDto getQuestionsForAssessment(QuestionDto inDto) throws Exception {
		
		QuestionDto outDto = new QuestionDto();
		
		String mode = inDto.getMode();
		
		List<QuestionData> allQuestions = new ArrayList<>();
		
		if(CommonConstant.DEGREE_DEFAULT_MODE.equals(mode)) {
			allQuestions = questionLogic.getQuestionsForAssessment();
		}
		
		List<QuestionObj> questions = new ArrayList<>();
		
		if(!allQuestions.isEmpty()) {
			
			for(QuestionData question : allQuestions) {
				
				QuestionObj obj = new QuestionObj();
				
				obj.setIdPk(question.getQuestionIdPk());
				obj.setCategory(question.getCategory());
				obj.setQuestion(question.getQuestion());
				obj.setStrandegree(question.getStrandegree());
				obj.setAnswers(question.getAnswers());
				
				questions.add(obj);
				
			}
		}
		
		outDto.setQuestions(questions);		
		return outDto;
	}
}

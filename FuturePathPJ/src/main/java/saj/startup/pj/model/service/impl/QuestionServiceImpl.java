package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.AssessmentConfigDao;
import saj.startup.pj.model.dao.entity.AnswerData;
import saj.startup.pj.model.dao.entity.AnswerEntity;
import saj.startup.pj.model.dao.entity.AssessmentConfigEntity;
import saj.startup.pj.model.dao.entity.QuestionData;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.logic.StrandegreeLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.QuestionObj;
import saj.startup.pj.model.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionLogic questionLogic;
	
	@Autowired
	private StrandegreeLogic strandegreeLogic;
	
	@Autowired
	private AssessmentConfigDao assessmentConfigDao;

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
		
		int i = 0;
		for(String answer : inDto.getAnswers()) {
						
			AnswerEntity newAnswer = new AnswerEntity();
			
			newAnswer.setQuestionIdPk(questionIdPk);
			newAnswer.setAnswer(answer);
			newAnswer.setIsActive(true);
			newAnswer.setIsDeleted(false);
			newAnswer.setCreatedAt(timeNow);
			
			if(i == Integer.valueOf(inDto.getCorrectIndex())) {
				newAnswer.setIsCorrect(true);
			}else {
				newAnswer.setIsCorrect(false);
			}
			
			answers.add(newAnswer);
			
			i++;
			
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
		
		int limit = 50;
		
		Optional<AssessmentConfigEntity> config = assessmentConfigDao.findById(1);
		
		if(config.isPresent()) {
			
			limit = config.get().getTotalQuestion();
			
		}
		
		String mode = inDto.getMode();
		
		List<QuestionData> allQuestions = new ArrayList<>();
		
		String category =
		        (inDto.getMode() != null && inDto.getMode().contains(CommonConstant.DEGREE))
		                ? CommonConstant.DEGREE
		                : CommonConstant.STRAND;		
		
		if(CommonConstant.DEGREE_DEFAULT_MODE.equals(mode)) {
			allQuestions = questionLogic.geQuestionsForAssessmentByRiasecCodes(inDto.getRiasecCodes(), category, limit);
		}
		else if(CommonConstant.DEGREE_CUSTOM_MODE.equals(mode)) {
			allQuestions = questionLogic.geQuestionsForAssessment(inDto.getDegrees(), category, limit);
		}else if(CommonConstant.STRAND_DEFAULT_MODE.equals(mode)) {
			allQuestions = questionLogic.geQuestionsForAssessment(inDto.getStrands(), category, limit);
		}else if(CommonConstant.STRAND_CUSTOM_MODE.equals(mode)) {
			allQuestions = questionLogic.geQuestionsForAssessment(inDto.getStrands(), category, limit);
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

	@Override
	public QuestionDto getQuestionByIdPk(QuestionDto inDto) throws Exception {
		
		QuestionDto outDto = new QuestionDto();
		
		QuestionEntity questionEntity = questionLogic.getQuestionByIdPk(inDto.getIdPk());
		
		StrandegreeEntity strandegree = strandegreeLogic.getStrandegree(questionEntity.getStrandegreeIdPk());
		
		System.out.println(questionEntity);
		
		QuestionObj questionObj = new QuestionObj();
		
		questionObj.setQuestion(questionEntity.getQuestion());
		questionObj.setCategory(strandegree.getCategory());
		questionObj.setStrandegree(strandegree.getCode());;
		
		List<AnswerData> answers = questionLogic.getAnswersByQuestionIdPk(inDto.getIdPk());
		
		questionObj.setAnswers(answers);
		
		outDto.setQuestionObj(questionObj);
		
		return outDto;
	}

	@Override
	public void updateQuestion(QuestionDto inDto) throws Exception {
		
		QuestionEntity existingQuestion = questionLogic.getQuestionByIdPk(inDto.getIdPk());
		
		existingQuestion.setQuestion(inDto.getQuestion());
		
		List<AnswerEntity> existingAnswers = questionLogic.getAnswersEntityByQuestionIdPk(inDto.getIdPk());
		
		List<AnswerEntity> answers = new ArrayList<>();
		
		int i = 0;
		for(AnswerEntity existingAnswer : existingAnswers) {
									
			existingAnswer.setQuestionIdPk(inDto.getIdPk());
			existingAnswer.setAnswer(inDto.getAnswers().get(i));
			
			if(i == Integer.valueOf(inDto.getCorrectIndex())) {
				existingAnswer.setIsCorrect(true);
			}else {
				existingAnswer.setIsCorrect(false);
			}
			
			answers.add(existingAnswer);
			
			i++;
		}
		
		questionLogic.saveQuestion(existingQuestion);
		questionLogic.saveAnswers(answers);		
	}

	@Override
	public void deleteQuestion(QuestionDto inDto) throws Exception {
		
		questionLogic.deleteQuestion(inDto.getIdPk());
	}
}

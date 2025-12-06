package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.dao.entity.RiasecRecommendationEntity;
import saj.startup.pj.model.dao.entity.RiasecResultEntity;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.projection.AssessmentStatisticsData;
import saj.startup.pj.model.dao.projection.UniversityRecommendationData;
import saj.startup.pj.model.dao.projection.UserAssessmentStatisticsData;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.logic.FeedbackLogic;
import saj.startup.pj.model.logic.HistoryLogic;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.logic.StrandegreeLogic;
import saj.startup.pj.model.logic.UniversityLogic;
import saj.startup.pj.model.object.RecommendationObj;
import saj.startup.pj.model.service.AssessmentService;
import saj.startup.pj.model.service.UserService;

@Service
public class AssessmentServiceImpl implements AssessmentService{
		
	@Autowired
	private QuestionLogic questionLogic;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryLogic historyLogic;
	
	@Autowired
	private UniversityLogic universityLogic;
	
	@Autowired
	private FeedbackLogic feedbackLogic;
	
	@Autowired
	private StrandegreeLogic strandegreeLogic;
	
	@Override
	public AssessmentDto saveAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();
	    
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	    UserEntity user = userService.getUserActive();

	    ObjectMapper mapper = new ObjectMapper();
	    HashMap<Integer, Integer> answeredMap = mapper.readValue(
	        inDto.getAnsweredJson(),
	        new TypeReference<HashMap<Integer, Integer>>() {}
	    );

	    int totalCorrect = 0;
	    int totalIncorrect = 0;

	    List<HistoryQuestionEntity> historyQuestions = new ArrayList<>();

	    AssessmentResultEntity resultEntity = new AssessmentResultEntity();
	    resultEntity.setUserIdPk(user.getIdPk());
	    resultEntity.setDateTaken(timestamp);
	    resultEntity.setCategory(
    	    inDto.getMode() != null && inDto.getMode().contains("DEGREE") ? "DEGREE" : "STRAND"
    	);
	    int resultIdPk = historyLogic.saveAssessmentResult(resultEntity);

	    for (Map.Entry<Integer, Integer> entry : answeredMap.entrySet()) {
	        Integer questionId = entry.getKey();
	        Integer answerId = entry.getValue();

	        AssessmentCheckerData checker = questionLogic.getQuestionAssessmentChecker(questionId, answerId);
	        boolean isCorrect = Boolean.TRUE.equals(checker.getIsCorrect());

	        if (isCorrect) {
	            totalCorrect++;
	        } else {
	            totalIncorrect++;
	        }

	        HistoryQuestionEntity history = new HistoryQuestionEntity();
	        history.setResultIdPk(resultIdPk);
	        history.setUserIdPk(user.getIdPk());
	        history.setQuestionIdPk(questionId);
	        history.setAnswerIdPk(answerId);
	        history.setIsCorrect(isCorrect);

	        historyQuestions.add(history);
	    }

	    historyLogic.saveHistoryQuestions(historyQuestions);

	    int totalQuestions = answeredMap.size();
	    double percentage = totalQuestions > 0 ? ((double) totalCorrect / totalQuestions) * 100 : 0.0;
	    percentage = Math.round(percentage * 10.0) / 10.0;

	    resultEntity.setCorrect(totalCorrect);
	    resultEntity.setIncorrect(totalIncorrect);
	    resultEntity.setScore(percentage);
	    resultEntity.setTotalQuestion(totalQuestions);

	    historyLogic.saveAssessmentResult(resultEntity);

	    outDto.setResultIdPk(resultIdPk);

	    return outDto;
	}



	@Override
	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();

	    UserEntity user = userService.getUserActive();

	    Map<String, RecommendationObj> correctCountMap = new HashMap<>();
	    Map<String, Integer> totalQuestionPerCode = new HashMap<>();

	    AssessmentResultEntity result = historyLogic.getAssessmentResult(inDto.getResultIdPk());
	    List<HistoryQuestionData> questions = historyLogic.getHistoryQuestionsByResultIdPk(inDto.getResultIdPk());
	    outDto.setQuestions(questions);

	    for (HistoryQuestionData q : questions) {
	        String code = q.getCode();
	        String name = q.getName();
	        boolean isCorrect = Boolean.TRUE.equals(q.getIsCorrect());

	        correctCountMap.putIfAbsent(code, new RecommendationObj(code, name, 0, 0.0));
	        totalQuestionPerCode.put(code, totalQuestionPerCode.getOrDefault(code, 0) + 1);

	        if (isCorrect) {
	            correctCountMap.get(code).setCorrectCount(
	                correctCountMap.get(code).getCorrectCount() + 1
	            );
	        }
	    }

	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
	        String code = e.getKey();
	        RecommendationObj rec = e.getValue();
	        int totalPerCode = totalQuestionPerCode.getOrDefault(code, 0);
	        double percentagePerCode = totalPerCode > 0
	            ? ((double) rec.getCorrectCount() / totalPerCode) * 100
	            : 0.0;
	        rec.setPercentage(percentagePerCode);
	    }

	    List<RecommendationObj> top3 = correctCountMap.values().stream()
	        .filter(rec -> rec.getPercentage() > 0)
	        .sorted((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()))
	        .limit(3)
	        .collect(Collectors.toList());

	    List<String> top3Codes = top3.stream()
	        .map(RecommendationObj::getCode)
	        .collect(Collectors.toList());

	    List<UniversityRecommendationData> universities = universityLogic.getUniversityRecommendation(top3Codes);
	    
	    outDto.setUniversities(universities);
	    
	    FeedbackEntity feedback = feedbackLogic.getFeedbackByResultId(inDto.getResultIdPk());
	    
	    outDto.setFeedback(feedback);
	    
	    if(user.getIdPk() == result.getUserIdPk()) {
	    	outDto.setOwner(true);
	    }
	    
	    outDto.setResultIdPk(inDto.getResultIdPk());
	    outDto.setTotalCorrect(result.getCorrect());
	    outDto.setTotalIncorrect(result.getIncorrect());
	    outDto.setTotalQuestion(result.getTotalQuestion());
	    outDto.setPercentage(result.getScore());
	    outDto.setRecommendationMap(correctCountMap);
	    outDto.setTop3Recommendations(top3);

	    return outDto;
	}



	@Override
	public AssessmentDto getAssessmentRIASECResult(AssessmentDto inDto) throws Exception {

	    Map<String, Integer> questionCount = Map.of(
	        "R", 6,
	        "I", 8,
	        "A", 7,
	        "S", 6,
	        "E", 7,
	        "C", 8
	    );
	    
	    RiasecResultEntity riasec = historyLogic.getRiasecResultById(inDto.getRiasecIdPk());
	    
	    UserEntity user = userService.getUserActive();
	    
	    Map<String, Integer> totals = Map.of(
	        "R", riasec.getRealistic(),
	        "I", riasec.getInvestigative(),
	        "A", riasec.getArtistic(),
	        "S", riasec.getSocial(),
	        "E", riasec.getEnterprising(),
	        "C", riasec.getConventional()
	    );
	    
	    List<String> top3Letters = totals.entrySet().stream()
	    	    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // highest first
	    	    .limit(3)
	    	    .map(Map.Entry::getKey)
	    	    .collect(Collectors.toList());


	    String topCombo = String.join("-", top3Letters);
	    
	    List<Integer> codes = getTopMatches(topCombo);
	    
	    int countAllStrandegree = strandegreeLogic.countAllStrandegree();
	    int countRecommendedStrandegree = strandegreeLogic.countRecommendedStrandegree(codes);
	    
	    RiasecRecommendationEntity recommendation = historyLogic.getRiasecRecommendationByRiasecIdPk(riasec.getIdPk());
	    
	    List<String> degrees = new ArrayList<>();
	    
	    if(recommendation == null) {
	    	
	    	List<Integer> idPks = new ArrayList<>();
	    	
	    	List<StrandegreeEntity> strandegrees = strandegreeLogic.getRandomRecommendedStrandegree(codes);
	    	
	    	for(StrandegreeEntity strandegree : strandegrees) {
	    		degrees.add("(" + strandegree.getCode() + ") " + strandegree.getName());
	    		idPks.add(strandegree.getIdPk());
	    	}
	    	
	    	RiasecRecommendationEntity riasecRecommendation = new RiasecRecommendationEntity();
	    	
	    	riasecRecommendation.setRiasecIdPk(riasec.getIdPk());
	    	riasecRecommendation.setStrandegreeIdPks(idPks);
	    	
	    	historyLogic.saveRiasecRecommendation(riasecRecommendation);
	    }else {
	    	
	    	
	    	
	    	List<StrandegreeEntity> strandegrees = strandegreeLogic.getStrandegreeByIdPks(recommendation.getStrandegreeIdPks());
	    	
	    	for(StrandegreeEntity strandegree : strandegrees) { 
	    		degrees.add("(" + strandegree.getCode() + ") " + strandegree.getName());
	    	}
	    	
	    	
	    }

	    StringBuilder message = new StringBuilder("Based on your interests and strengths:<br><br>");
	    for (String letter : top3Letters) {
	        message.append("• ")
	               .append(CommonConstant.TRAIT_DESCRIPTIONS.getOrDefault(letter, ""))
	               .append(".<br>");
	    }
	    String summary = CommonConstant.RIASEC_SUMMARY_MAP.getOrDefault(
	        top3Letters.get(0),
	        "You have a mix of interests that can lead to many exciting paths."
	    );
	    message.append("<br>").append(summary);

	
	    Set<String> exampleFieldsSet = new LinkedHashSet<>(); 

	    for (String letter : top3Letters) {
	
	        Set<Integer> keys = CommonConstant.RIASEC_CODE_MAP.entrySet().stream()
	                .filter(e -> e.getValue().contains(letter))
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toSet());


	        keys.stream()
	            .map(CommonConstant.RIASEC_DETAIL_MAP::get)
	            .filter(Objects::nonNull)
	            .forEach(exampleFieldsSet::add); 
	    }

	    String exampleFields = String.join(", ", exampleFieldsSet);

	    DecimalFormat df = new DecimalFormat("0.00");

	    AssessmentDto dto = new AssessmentDto();
	    dto.setCombination(topCombo);
	    dto.setMessage(message.toString());
	    System.out.println("TOP COMBO: " + topCombo);
	    dto.setExampleFields(exampleFields);
	    dto.setRiasecCodes(getTopMatches(topCombo));
	    System.out.println(getTopMatches(topCombo));
	    dto.setRealisticPercentageStr(df.format((double) riasec.getRealistic() / (questionCount.get("R") * 4) * 100));
	    dto.setInvestigativePercentageStr(df.format((double) riasec.getInvestigative() / (questionCount.get("I") * 4) * 100));
	    dto.setArtisticPercentageStr(df.format((double) riasec.getArtistic() / (questionCount.get("A") * 4) * 100));
	    dto.setSocialPercentageStr(df.format((double) riasec.getSocial() / (questionCount.get("S") * 4) * 100));
	    dto.setEnterprisingPercentageStr(df.format((double) riasec.getEnterprising() / (questionCount.get("E") * 4) * 100));
	    dto.setConventionalPercentageStr(df.format((double) riasec.getConventional() / (questionCount.get("C") * 4) * 100));
	    dto.setStrandegreePercentageStr(df.format((double) countRecommendedStrandegree / countAllStrandegree * 100));
	    dto.setDegrees(degrees);
	    dto.setCountAllStrandegree(countAllStrandegree);
	    dto.setCountRecommendStrandegree(countRecommendedStrandegree);
	    dto.setRiasecIdPk(riasec.getIdPk());
	    
	    FeedbackEntity feedback = feedbackLogic.getFeedbackByResultId(riasec.getIdPk());
	    
	    dto.setFeedback(feedback);
	    
	    if(user.getIdPk() == riasec.getUserIdPk()) {
	    	dto.setOwner(true);
	    }
	    
	    return dto;
	}
	
	@Override
	public AssessmentDto saveAssessmentRiasecResult(AssessmentDto inDto) throws Exception {
		
		AssessmentDto outDto = new AssessmentDto();
		
		UserEntity user = userService.getUserActive();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		RiasecResultEntity riasec = new RiasecResultEntity();
	    
		riasec.setUserIdPk(user.getIdPk());
	    riasec.setRealistic(inDto.getRealistic());
	    riasec.setInvestigative(inDto.getInvestigative());
	    riasec.setArtistic(inDto.getArtistic());
	    riasec.setSocial(inDto.getSocial());
	    riasec.setEnterprising(inDto.getEnterprising());
	    riasec.setConventional(inDto.getConventional()); 
	    riasec.setDateTaken(timestamp);
	    
	    historyLogic.saveRiasecResult(riasec);
	    
	    outDto.setRiasecIdPk(riasec.getIdPk());
	    
	    return outDto;  
	}
	
	public static List<Integer> getTopMatches(String inputCode) {
	    List<Map.Entry<Integer, String>> allEntries = new ArrayList<>(CommonConstant.RIASEC_CODE_MAP.entrySet());

	    // Sort by descending score
	    allEntries.sort((a, b) -> Integer.compare(
	            calculateScore(inputCode, b.getValue()),
	            calculateScore(inputCode, a.getValue())
	    ));

	    // Return only the keys of the top 5 matches
	    return allEntries.stream()
	            .limit(5)
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toList());
	}



	private static int calculateScore(String inputCode, String testCode) {
	    String[] inp = inputCode.split("-");
	    String[] tst = testCode.split("-");

	    int score = 0;

	    if (inputCode.equals(testCode)) score += 100;

	    // Rule 2: Contains all letters bonus
	    for (String c : inp) {
	        if (testCode.contains(c)) score += 10;
	    }

	    // Rule 3: Position match bonus
	    for (int i = 0; i < 3; i++) {
	        if (inp[i].equals(tst[i])) score += 5;
	    }

	    return score;
	}



	@Override
	public AssessmentDto getAssessmentStatistics() throws Exception {
		
		AssessmentDto outDto = new AssessmentDto();
	
		AssessmentStatisticsData data = historyLogic.getAssessmentStatistics();
		
		outDto.setAssessmentStatistics(data);
		
		return outDto;
	}



	@Override
	public AssessmentDto getAssessmentStatisticsByUser() throws Exception {

	    AssessmentDto outDto = new AssessmentDto();
	    UserEntity user = userService.getUserActive();

	    // 🔹 Get user statistics safely
	    UserAssessmentStatisticsData data = historyLogic.getAssessmentStatisticsByUser(user.getIdPk());
	    if (data == null) {
	        // no assessment yet → return empty dto
	        return outDto;
	    }

	    AssessmentResultEntity result = historyLogic.getAssessmentResult(data.getLastResultIdPk());
	    List<HistoryQuestionData> questions = historyLogic.getHistoryQuestionsByResultIdPk(data.getLastResultIdPk());

	    // 🔹 Initialize empty collections if null
	    if (questions == null) {
	        questions = new ArrayList<>();
	    }

	    Map<String, RecommendationObj> correctCountMap = new HashMap<>();
	    Map<String, Integer> totalQuestionPerCode = new HashMap<>();

	    outDto.setQuestions(questions);

	    for (HistoryQuestionData q : questions) {
	        if (q == null) continue;
	        String code = q.getCode();
	        String name = q.getName();
	        boolean isCorrect = Boolean.TRUE.equals(q.getIsCorrect());

	        correctCountMap.putIfAbsent(code, new RecommendationObj(code, name, 0, 0.0));
	        totalQuestionPerCode.put(code, totalQuestionPerCode.getOrDefault(code, 0) + 1);

	        if (isCorrect) {
	            RecommendationObj rec = correctCountMap.get(code);
	            rec.setCorrectCount(rec.getCorrectCount() + 1);
	        }
	    }

	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
	        String code = e.getKey();
	        RecommendationObj rec = e.getValue();
	        int totalPerCode = totalQuestionPerCode.getOrDefault(code, 0);
	        double percentagePerCode = totalPerCode > 0
	                ? ((double) rec.getCorrectCount() / totalPerCode) * 100
	                : 0.0;
	        rec.setPercentage(percentagePerCode);
	    }

	    List<RecommendationObj> top3 = correctCountMap.values().stream()
	            .filter(rec -> rec.getPercentage() > 0)
	            .sorted((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()))
	            .limit(3)
	            .collect(Collectors.toList());

	    List<String> top3Codes = top3.stream()
	            .map(RecommendationObj::getCode)
	            .collect(Collectors.toList());

	    // 🔹 Handle empty codes safely
	    List<UniversityRecommendationData> universities = top3Codes.isEmpty()
	            ? new ArrayList<>()
	            : universityLogic.getUniversityRecommendation(top3Codes);

	    outDto.setUniversities(universities);

	    // 🔹 Null-check before setting result data
	    if (result != null) {
	        outDto.setTotalCorrect(result.getCorrect());
	        outDto.setTotalIncorrect(result.getIncorrect());
	        outDto.setTotalQuestion(result.getTotalQuestion());
	        outDto.setPercentage(result.getScore());
	    }

	    outDto.setResultIdPk(data.getLastResultIdPk());
	    outDto.setRecommendationMap(correctCountMap);
	    outDto.setTop3Recommendations(top3);
	    outDto.setUserAssessmentStatistics(data);

	    return outDto;
	}





}






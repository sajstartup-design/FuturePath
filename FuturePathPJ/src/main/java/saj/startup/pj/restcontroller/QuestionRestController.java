package saj.startup.pj.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionRestController {

	@Autowired
    private QuestionService questionService;

    @GetMapping("/admin/questions/retrieve")
    public QuestionDto getAllQuestions(@RequestParam(defaultValue = "0") int page,
    		@RequestParam(required = false) String search) {
        try {
        	QuestionDto inDto = new QuestionDto();

            PaginationObj pagination = new PaginationObj();
            pagination.setPage(page);

            FilterAndSearchObj filter = new FilterAndSearchObj();
            filter.setSearch(search);

            inDto.setPagination(pagination);
            inDto.setFilter(filter);

            return questionService.getAllQuestions(inDto);
        } catch (Exception e) {
            e.printStackTrace();

            // Return empty UserDto on error
            return new QuestionDto();
        }
    }
}

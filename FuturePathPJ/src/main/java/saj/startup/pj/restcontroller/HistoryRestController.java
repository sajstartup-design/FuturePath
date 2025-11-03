package saj.startup.pj.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saj.startup.pj.model.dto.HistoryDto;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.service.HistoryService;

@RestController
@RequestMapping("/api")
public class HistoryRestController {

	@Autowired
	private HistoryService historyService;
	
	@GetMapping("/history/retrieve")
    public HistoryDto getAllHistory(@RequestParam(defaultValue = "0") int page,
    		@RequestParam(required = false) String search) {
        try {
            HistoryDto inDto = new HistoryDto();

            PaginationObj pagination = new PaginationObj();
            pagination.setPage(page);

            FilterAndSearchObj filter = new FilterAndSearchObj();
            filter.setSearch(search);

            inDto.setPagination(pagination);
            inDto.setFilter(filter);

            return historyService.getAllAssessmentByUser(inDto);
        } catch (Exception e) {
            e.printStackTrace();

            return new HistoryDto();
        }
    }
}

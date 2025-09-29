package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/admin/users")
	public String showUsers() {
		
		
		return "user/user-view";
	}
}

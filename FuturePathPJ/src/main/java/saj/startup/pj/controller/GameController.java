package saj.startup.pj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	
	@GetMapping("/game/one")
	public String showGameOne() {
		
		return "game-one";
	}
	
	@GetMapping("/game/two")
	public String showGameTwo() {
		
		return "game-two";
	}
}

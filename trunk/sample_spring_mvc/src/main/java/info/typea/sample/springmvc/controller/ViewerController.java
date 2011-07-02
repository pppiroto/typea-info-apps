package info.typea.sample.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewerController {

	
	@RequestMapping(value="/viewer.html", method=RequestMethod.GET)
	public String viewer() {
	
		return "viewer";
	}
}

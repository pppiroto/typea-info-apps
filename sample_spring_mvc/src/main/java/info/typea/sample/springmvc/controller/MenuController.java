package info.typea.sample.springmvc.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import info.typea.sample.springmvc.dao.MenuDao;
import info.typea.sample.springmvc.entity.Menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	private MenuDao menuDao;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String home(Model model) {
		logger.info("menu controller started!");
		
		/*
		 * @see http://stackoverflow.com/questions/6300812/get-the-servlet-request-object-in-a-pojo-class
		 */
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes(); 
		HttpServletRequest request = sra.getRequest();
		
		List<Menu> menulist =  menuDao.findAll();
		
		String host = "http://" + request.getServerName() + ":" + request.getServerPort();
		for (Menu m : menulist) {
			m.setUrl(host + m.getUrl());
		}
		
		model.addAttribute("menulist", menulist);

		return "menu";
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
}


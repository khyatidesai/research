package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {

	@RequestMapping("/")
	public String homepage() {
		return "homepage";
	}

	//@RequestMapping(value = "/usermanagement", method = RequestMethod.GET)
	//public String getIndexPage() {
		//return "UserManagement";
	//}

}
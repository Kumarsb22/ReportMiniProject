package com.secondminiproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/index")
	public String homePage() {
		System.out.println("index function called");
		
		return "index";
	}

}

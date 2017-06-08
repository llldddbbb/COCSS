package com.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class CocssApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocssApplication.class, args);
	}

	@RequestMapping("/")
	public String showIndex(){
		return "redirect:/course/list";
	}
}

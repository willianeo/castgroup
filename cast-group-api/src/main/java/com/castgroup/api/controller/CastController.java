package com.castgroup.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cast")
public class CastController {
	
	@GetMapping("/")
	public String home() {
		return "Home Page";
	}
}

package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.LoginRequest;
import com.app.service.intf.HomeServiceIntf;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {

	// dependency : homeService
	@Autowired
	private HomeServiceIntf homeService;

	@PostMapping("/userLogin")
	public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(homeService.authenticateUser(request.getEmail(), request.getPassword()));
	}

	@GetMapping("/generateToken/{userEmail}")
	public ResponseEntity<?> generateToken(@PathVariable String userEmail) {
		return ResponseEntity.ok(homeService.generateToken(userEmail));
	}
	
	@PostMapping("/resetPassword/{userEmail}/{userNewPassword}")
	public ResponseEntity<?> resetPassword(@PathVariable String userEmail, @PathVariable String userNewPassword) {
		
		int num = homeService.resetPassword(userEmail,userNewPassword);
		
		if(num == 1) {
			return ResponseEntity.badRequest().body(null);
		}
		
		return ResponseEntity.ok(null);
	}
	
}

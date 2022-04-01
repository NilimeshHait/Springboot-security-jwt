package com.nilimesh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nilimesh.security.config.JwtTokenUtil;
import com.nilimesh.security.model.JwtRequest;
import com.nilimesh.security.model.JwtResponce;
import com.nilimesh.security.services.MyUserDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class ApplictionController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	
	@GetMapping("/getMessage/{message}")
	public String greetings(@PathVariable(value="message") String mess) {
		return "Hi "+mess+", Welcome to Web security tutorial"; 
	}
	
	@RequestMapping(value ="/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails=myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token= jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponce(token));
	}

	public void authenticate(String username,String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			throw new Exception("User Disabled "+e);
		}catch(BadCredentialsException bc) {
			throw new Exception("Invalid Credential "+bc);
		}
	}
}

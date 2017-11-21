package dz.ibdaa.gestionCommande.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.security.model.User;
import dz.ibdaa.gestionCommande.security.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(path = "/api/createUser", method = RequestMethod.POST)
	public @ResponseBody User createUser(@RequestBody User user) {
		System.out.println("auth :::::::::::::::::");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User createsUser = this.userService.save(user);
		return createsUser;
	}

}

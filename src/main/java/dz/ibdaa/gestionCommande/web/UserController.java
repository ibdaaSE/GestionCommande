package dz.ibdaa.gestionCommande.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.security.JwtTokenUtil;
import dz.ibdaa.gestionCommande.security.JwtUser;
import dz.ibdaa.gestionCommande.security.JwtUserFactory;
import dz.ibdaa.gestionCommande.security.model.ChangePassword;
import dz.ibdaa.gestionCommande.security.model.User;
import dz.ibdaa.gestionCommande.security.repository.UserRepository;
import dz.ibdaa.gestionCommande.security.service.JwtAuthenticationResponse;

@RestController
public class UserController {

	@Autowired
	private UserRepository userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(path = "/api/createUser", method = RequestMethod.POST)
	public @ResponseBody User createUser(@RequestBody User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User createsUser = this.userService.save(user);
		return createsUser;
	}

	@RequestMapping(path = "/api/changePassword", method = RequestMethod.PATCH)
	public ResponseEntity<?> changeUserPassword(@RequestBody ChangePassword changePassword,
			HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		System.out.println("token :" + token);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		System.out.println("username :" + username);
		JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
		System.out.println("old : " + changePassword.getOldPassword());
		System.out.println("jwtUser : " + jwtUser.getPassword());

		if (this.passwordEncoder.matches(changePassword.getOldPassword(), jwtUser.getPassword())) {
			System.out.println("ok");
			User user = this.userService.findOne(jwtUser.getId());
			user.setPassword(this.passwordEncoder.encode(changePassword.getNewPassword()));
			this.userService.save(user);
			return ResponseEntity.ok().body(user);
		} else {
			System.out.println("BAD");
			return ResponseEntity.badRequest().body(null);
		}
	}

}

package net.codejava.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@GetMapping("/users")
	public String showUserList(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@GetMapping("/users/new")
	public String showCreateNewUserForm(Model model) {
		List<Role> listRoles = roleRepo.findAll();
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("user", new User());
		
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user) {
		userRepo.save(user);
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepo.findById(id).get();
		model.addAttribute("user", user);
		
		List<Role> listRoles = roleRepo.findAll();
		model.addAttribute("listRoles", listRoles);
		
		return "user_form";
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		userRepo.deleteById(id);
		
		return "redirect:/users";
	}
}

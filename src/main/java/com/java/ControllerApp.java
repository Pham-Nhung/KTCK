package com.java;
	
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

	
	
@Controller
public class ControllerApp {
	@Autowired
	private UserRepository repo;
	@Autowired
	private UserService repo3;
	@Autowired
	private NoteService note2;
	@GetMapping("")
	public String home() {
		return "homepage";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@GetMapping("/takenote")
	public String showNotes(Model model) {
		 model.addAttribute("note", new Notes()); 
		 return "editUser";
	}
	
	@PostMapping("/take_notes") 
	public String takeNoteSuccess(Notes note,Model model) {
		note2.save(note);
		return "notesuccess"; 
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		return "register_success";
	}
	
	@GetMapping("/list_users")
	public String viewUsersList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	

	
	@RequestMapping("/takenotes")
	public String viewNotes(Model model,
			@Param("keyword")String keyword) {
	List <Notes> notes = note2.listAll(keyword);  
    model.addAttribute("takenote1", notes);  
    return "takenote"; 
	}
	

	
	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name ="id") Long id) {
		repo3.delete(id);
		return "redirect:/";
	}
	
}

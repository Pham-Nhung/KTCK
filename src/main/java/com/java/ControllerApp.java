package com.java;
	
import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

	
	
@Controller
public class ControllerApp {
	@Autowired
	private UserRepository repo;
	@Autowired
	private NoteService note2;
	@Autowired
	private NoteRepository note;
	@GetMapping("")
	public String home() {
		return "homepage";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
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
	
	@GetMapping("/takenote")
	public String showNotes(Model model) {
		 model.addAttribute("note", new Notes()); 
		 return "editnote";
	}

	@PostMapping("/take_notes") 
	public String takeNoteSuccess(Notes notes,Model model, Principal principle) {
		User user  = repo.findByEmail(principle.getName());
		notes.setUser(user);
		note.save(notes);
		return "notesuccess"; 
	}
	
	@RequestMapping("/takenotes")
	public String viewNotes(Model model,
			@Param("keyword")String keyword) {
	List <Notes> notes = note2.listAll(keyword);  
    model.addAttribute("takenote1", notes);  
    return "takenote"; 
	}
	
	@RequestMapping("/takenotes/filter")
	public String viewNotesByUser(Model model,
			 @RequestParam("user_id") Long userId) {
		User user = repo.findById(userId).get(); 
		List<Notes> notes = note.findByUser(user);
		model.addAttribute("takenote1", notes);
		return "takenote";
	}
	
	@RequestMapping("/edit/{stt}")
	public String showEdit(@PathVariable("stt") Long stt,Model model) {
		model.addAttribute("note", note.findById(stt).orElse(null));
		return "edit";
	}
	
	@RequestMapping("/notes/update")
	public String update(@Param("keyword1") String keyword,Model model,@RequestParam Long stt, @RequestParam String title, @RequestParam String content, @RequestParam String ngaytao) {
		Notes notes = note.findById(stt).orElse(null);
		notes.setTieude(title);
		notes.setNoidung(content);
		notes.setNgaytao(ngaytao);
		note.save(notes);
		List<Notes> notess = note2.listAll(keyword);
		model.addAttribute("takenote1", notess);
		return "takenote";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteUser(Model model,@PathVariable(name ="id") Long id) {
		repo.deleteById(id);
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
	
	@RequestMapping("/deleted/{stt}")
	public String deleteNotes(@PathVariable(name = "stt") Long stt, Model model) {
		note.deleteById(stt);
		List<Notes> listnote = note.findAll();
		model.addAttribute("takenote1", listnote);
		return "takenote";
	}
	
	
	
}

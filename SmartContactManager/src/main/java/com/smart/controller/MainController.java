package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserDao;
import com.smart.entity.User;
import com.smart.helper.Message;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserDao dao;

	@RequestMapping("/")
	public String home(Model model)
	{
		model.addAttribute("title","Home-Smart contact manager");		
		return "home";

	}

	@RequestMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title","about-Smart contact manager");		
		return "about";

	}
	@RequestMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title","Register-Smart contact manager");
		model.addAttribute("user", new User());
		return "signup";

	}

	@RequestMapping(value ="/do_register",method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="agreement",defaultValue = "false") boolean agreement,Model model,HttpSession httpSession )
	{  

		try {
			if(!agreement)
			{

				System.out.println("you have not agreed terms and condition");
				throw new Exception("you have not agreed terms and condition");
			}

			if(result.hasErrors())
			{
				System.out.println("Error"+result.toString());

				model.addAttribute("user", user);
				return "signup";
			}

			user.setActive(true);
			user.setRole("ROLE_USER");
			user.setPassowrd( encoder.encode(user.getPassowrd()));
			System.out.println("aggrement"+agreement);
			User save = this.dao.save(user);

			System.out.println("user"+user);
			model.addAttribute("user", save);
			httpSession.setAttribute("messages", new Message("Successfuly Registered !! ","alert-success"));

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
			model.addAttribute("user",new User());
			httpSession.setAttribute("messages", new Message("Something Went Wrong !! "+e.getMessage(),"alert-danger"));
			return "signup";
		}
		return "signup";
	}





	@RequestMapping("/signin")
	public String customLogin(Model model) {

		model.addAttribute("title","Login Page");		

		return "login";

	}






}

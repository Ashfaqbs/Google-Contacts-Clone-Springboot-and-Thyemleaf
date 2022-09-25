package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.remote.JMXPrincipal;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactDao;
import com.smart.dao.OrderDao;
import com.smart.dao.UserDao;
import com.smart.entity.Contact;
import com.smart.entity.MyOrder;
import com.smart.entity.User;
import com.smart.helper.Message;


import com.razorpay.*;


@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired
	private UserDao dao;

	@Autowired
	private ContactDao dao2;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private OrderDao dao3;


	@ModelAttribute
	public void addCommonData(Model model, Principal principal)
	{
		String name = principal.getName();
		System.out.println(name );
		User userByUsername = this.dao.getUserByUsername(name);
		model.addAttribute("user", userByUsername);

	}

	@RequestMapping("/index")
	public String dashBoard(Model model , Principal principal)
	{
		model.addAttribute("title", "User DashBoard");

		return "normal/user_dashboard";
	}

	@RequestMapping("/add-contact")
	public String addContacts(Model model )
	{

		model.addAttribute("contact", new Contact());

		model.addAttribute("title", "Add contact");
		return "normal/add_contacts_form";
	}

	@RequestMapping(value = "/process-contact",method = RequestMethod.POST)
	public String processDate(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file, Principal principal,HttpSession httpSession)
	{

		try{String name = principal.getName();
		User user = this.dao.getUserByUsername(name);
		if(file.isEmpty())
		{
			System.out.println("File is Empty");
			contact.setImageUrl("contact.jpg");
		}else {

			contact.setImageUrl(file.getOriginalFilename());
			File file2 = new ClassPathResource("static/image").getFile();
			Path path = Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}

		user.getContacts().add(contact);
		contact.setUser(user);


		this.dao.save(user);
		System.out.println("data is" + contact);
		httpSession.setAttribute("message", new Message("Your Contact is Successfully added !! Add more..","success"));
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error"+e.getMessage());
			httpSession.setAttribute("message", new Message("Something went wrong !! try again..","danger"));

			// TODO: handle exception
		}
		return "normal/add_contacts_form";
	}


	@RequestMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") int page,Model model,Principal principal)
	{
		String name = principal.getName();
		User user = this.dao.getUserByUsername(name);
		Pageable p1 = PageRequest.of(page, 5);
		Page<Contact> contacts = this.dao2.findContactsByUser(user.getId(),p1);
		model.addAttribute("contacts", contacts);
		model.addAttribute("title", "Show Contacts");
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpages", contacts.getTotalPages());
		return "normal/show_contacts";
	}


	@RequestMapping("/contact/{id}")
	public String showcontact(Model model,@PathVariable("id") int id,Principal principal )
	{
		Contact contact = this.dao2.findById(id).get();

		String name = principal.getName();
		User userByUsername = this.dao.getUserByUsername(name);

		if(userByUsername.getId()==contact.getUser().getId())
			model.addAttribute("contact", contact);


		System.out.println("contact details are"+ contact.getName());
		model.addAttribute("title", "Contact Details");
		return "normal/contact_detail";
	}

	//	@RequestMapping("/deletecontact/{id}")
	//	public String deleteContact(@PathVariable("id") int id)
	//	{
	//
	//		this.dao2.deleteById(id);
	//
	//		return "normal/show_contacts";
	//
	//	}

	@RequestMapping("/delete/{id}")
	public String deletecontact(@PathVariable("id") int id,Principal principal ,HttpSession session)
	{
		Contact contact = this.dao2.findById(id).get();

		User user = this.dao.getUserByUsername(principal.getName());
		user.getContacts().remove(contact);
		this.dao.save(user);


		session.setAttribute("message", new Message("Contact deleted Successfully", "success"));



		return "redirect:/user/show-contacts/0";	 
	}

	@PostMapping("/update-contact/{id}")
	public String updateContact(@PathVariable("id") int id,Model model)
	{

		Contact contact = this.dao2.findById(id).get();
		model.addAttribute("contact", contact);
		model.addAttribute("title", "Update Contact");
		return "normal/update_form";

	}




	@RequestMapping(value = "/process-update",method = RequestMethod.POST)
	public String precess_Update(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile file,Model model , HttpSession session,Principal principal)
	{

		Contact contact2 = this.dao2.findById(contact.getId()).get();
		try {

			if(!file.isEmpty()) {

				File deletefile = new ClassPathResource("static/image").getFile();
				File fil= new File(deletefile, contact2.getImageUrl());
				fil.delete();

				File file2 = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(file2.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				contact.setImageUrl(file.getOriginalFilename());


			}else {

				contact.setImageUrl(contact2.getImageUrl());
			}

			User user = this.dao.getUserByUsername(principal.getName());
			contact.setUser(user);
			this.dao2.save(contact);
			session.setAttribute("message", new Message("Your contact is updated", "success"));
		} catch (Exception e) {

			session.setAttribute("message", new Message("Your contact is  not updated", "danger"));

		}

		System.out.println(contact.getName());
		System.out.println(contact.getId());

		return "redirect:/user/show-contacts/"+contact.getId();
	}


	@RequestMapping("/profile")
	public String profile(Model model)
	{
		model.addAttribute("title", "Profile Page");

		return"normal/profile";
	}
	

	@RequestMapping("/settings")
	public String setting()
	{

		return "normal/setting";
	}

//	@RequestMapping(value = "/process-setting",method = RequestMethod.POST)
//	public String updateProfile(@ModelAttribute User user,HttpSession httpSession,Principal principal)
//	{
//		String name = principal.getName();
//		User user1 = this.dao.getUserByUsername(name);
//		user1=user;
//
//		this.dao.save(user1);
//		httpSession.setAttribute("message", new Message("Your Profile is updated", "success"));
//		System.out.println(user.getName());
//		System.out.println(user.getPassowrd());
//
//		return "redirect:/user/settings";
//	}

	
	
	
	
	@RequestMapping(value = "/change-password" , method = RequestMethod.POST)
	public String changePassword(@RequestParam("old") String oldPass, @RequestParam("new") String newPass,Principal principal,HttpSession session)
	{
		
		System.out.println(oldPass);
		System.out.println(newPass);
		User user = this.dao.getUserByUsername(principal.getName());
		
		boolean matches = this.bCryptPasswordEncoder.matches(oldPass, user.getPassowrd());
		
		if(matches)
		{
			user.setPassowrd(this.bCryptPasswordEncoder.encode(newPass));
			this.dao.save(user);
			session.setAttribute("message", new Message("your Password is Updated","success"));
			}
		else {
			session.setAttribute("message", new Message("Wrong password","danger"));
			return "redirect:/user/settings";


		}
		
		return "redirect:/user/index";
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/create_order",method =  RequestMethod.POST)
	@ResponseBody
	public String createOrder(@RequestBody Map<String ,Object> data,Principal principal) throws RazorpayException
	{
		
	int amt = Integer.parseInt(data.get("amount").toString());  
	
	var client= new RazorpayClient(null, null);
	
	JSONObject ob= new  JSONObject();
	ob.put("amount", amt*100);
	ob.put("currency", "INR");
	ob.put("receipt", "txn_1231");
		Order create = client.Orders.create(ob);
		
		MyOrder myOrder = new MyOrder();
		myOrder.setAmount(create.get("amount")+"");
		myOrder.setOrderId(create.get("id"));
		myOrder.setPaymentID(null);
		myOrder.setStatus("created");
		User userByUsername = this.dao.getUserByUsername(principal.getName());
		myOrder.setUser(userByUsername);
		myOrder.setReceipt(create.get("receipt"));		
		
		this.dao3.save(myOrder);
		
	return create.toString();
	}
	
	
	@RequestMapping(value = "/update_order" , method = RequestMethod.POST)
	public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data)
	{
		MyOrder myorder = this.dao3.findByOrderId(data.get("order_id").toString());
		myorder.setPaymentID(data.get("payment_id").toString());
		myorder.setStatus(data.get("status").toString());
		this.dao3.save(myorder);
		return ResponseEntity.ok(Map.of("msg","updated"));
		
	}
	
}

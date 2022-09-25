package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.ContactDao;
import com.smart.dao.UserDao;
import com.smart.entity.Contact;
import com.smart.entity.User;

@RestController
public class SearchController {
	@Autowired
	private UserDao dao;
	@Autowired
	private ContactDao dao2;


	@GetMapping("/search/{query}")
	public ResponseEntity<?> search( @PathVariable("query") String query,Principal principal)
	{
		System.out.println(query);

		String name = principal.getName();
		User user= this.dao.getUserByUsername(name);
		List<Contact> contacts = this.dao2.findByNameContainingAndUser(query, user);

		return ResponseEntity.ok(contacts);
	}



}

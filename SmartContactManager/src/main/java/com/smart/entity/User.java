package com.smart.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User_table")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

@NotBlank(message = "User Name Cannot be empty!!")
@Size(min = 3,max=12, message = "User name must be between 3-12 characters!!")
	private String name;

	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;

	private String passowrd;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user",orphanRemoval = true)
	private List<Contact> contacts;
	

	private String about;
	
	

	private boolean active;

	private String imageUrl;

	private String role;

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public User(int id, String name, String email, String passowrd, List<Contact> contacts, String about,
			boolean active, String imageUrl, String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.passowrd = passowrd;
		this.contacts = contacts;
		this.about = about;
		this.active = active;
		this.imageUrl = imageUrl;
		this.role = role;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", passowrd=" + passowrd + ", contacts="
				+ contacts + ", about=" + about + ", active=" + active + ", imageUrl=" + imageUrl + ", role=" + role
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) { 
		this.active = active;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



}

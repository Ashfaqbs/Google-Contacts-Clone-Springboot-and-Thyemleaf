package com.smart.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Contact_Table")
public class Contact {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String nickname;


	private String work;

	private String email;
	@Column(length = 300)
	private String description;

	private String phNo;


	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;


	private String imageUrl;


	public Contact(int id, String name, String nickname, String work, String email, String description, String phNo,
			String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.work = work;
		this.email = email;
		this.description = description;
		this.phNo = phNo;
		this.imageUrl = imageUrl;
	}


	public Contact() {
		super();
		// TODO Auto-generated constructor stub
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


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPhNo() {
		return phNo;
	}


	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	//	@Override
	//	public String toString() {
	//		return "Contact [id=" + id + ", name=" + name + ", nickname=" + nickname + ", work=" + work + ", email=" + email
	//				+ ", description=" + description + ", phNo=" + phNo + ", user=" + user + ", imageUrl=" + imageUrl + "]";
	//	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public boolean equals(Object obj) {
		return this.id==((Contact)obj).getId();

	}



}

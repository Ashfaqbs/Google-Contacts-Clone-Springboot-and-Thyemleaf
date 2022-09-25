package com.smart.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.helper.Message;
import com.smart.service.Emailservice;

@Controller
public class ForgotController {

	@Autowired
	private Emailservice emailservice;

	@RequestMapping("/forgot")
	public String forgotPass()
	{



		return "forgot_email";
	}
	@RequestMapping("/send-otp")
	public String otpsend(@RequestParam("email") String email,HttpSession session)
	{


		Random random= new Random(1000);
		int otp = random.nextInt(99999);
		System.err.println(email +" "+otp);
		String subject="OTP from SCM";
		String message ="<h1> OTP "+otp +"</h1>";

		boolean flag = this.emailservice.sendEmail(subject, message, email);

		if(flag)
		{
			session.setAttribute("otp", otp);
			return "verify_otp";

		}else {
			session.setAttribute("message", new Message("check your email ID","danger"));
			return "forgot_email";

		}
	}




}

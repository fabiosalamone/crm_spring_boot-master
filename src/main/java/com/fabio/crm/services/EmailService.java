package com.fabio.crm.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {


	@Autowired
	SendGrid sendGrid;
	public boolean sendEmail(String email, String object, String text)  {
		
		try {
			Mail mail = prepareMail(email, object, text);
			
			Request request = new Request();
			
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());

			Response response = sendGrid.api(request);
			
			if(response!=null) {
				System.out.println("response code from sendgrid"+response.getHeaders());
		}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
		}
		
		return true;
		
	}
	
	public Mail prepareMail(String email, String object, String text) {
		
		Email from = new Email("fabios159@gmail.com");
        String subject = object;
        Email to = new Email(email);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);
		
		return mail;
	}
}
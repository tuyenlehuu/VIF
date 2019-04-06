package vif.online.chungkhoan.helper;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

/**
 * @author TUYENLH
 *
 */
@Repository
public class VifMailHelper {
	
	@Autowired
    public JavaMailSender emailSender;
	
	public void sendMailWithSimpleText(String recipient, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(content);
 
        // Send Message!
        this.emailSender.send(message);
	}
	
	public void sendMailWithAttachfile(String recipient, String subject, String content, String[] listPathFile) throws MessagingException{
		MimeMessage message = emailSender.createMimeMessage();
		 
        boolean multipart = true;
 
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
 
        helper.setTo(recipient);
        helper.setSubject(subject);
         
        helper.setText(content);
        
        for (String fileItem : listPathFile) {
        	FileSystemResource newFile = new FileSystemResource(new File(fileItem));
        	helper.addAttachment(newFile.getFilename(), newFile);
		}
 
        emailSender.send(message);
	}
	
	public void sendMailWithHTMLContent(String recipient, String subject, String content) throws MessagingException{
		MimeMessage message = emailSender.createMimeMessage();
		 
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        message.setContent(content, "text/html");
        helper.setTo(recipient);
        helper.setSubject(subject);
         
        emailSender.send(message);
	}
	
	
	public void sendHTMLMailWithAttachfile(String recipient, String subject, String content, String[] listPathFile) throws MessagingException{
		MimeMessage message = emailSender.createMimeMessage();
		 
        boolean multipart = true;
 
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        message.setContent(content, "text/html");
        helper.setTo(recipient);
        helper.setSubject(subject);
        
        for (String fileItem : listPathFile) {
        	FileSystemResource newFile = new FileSystemResource(new File(fileItem));
        	helper.addAttachment(newFile.getFilename(), newFile);
		}
 
        emailSender.send(message);
	}
}

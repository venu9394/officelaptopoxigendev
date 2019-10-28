
package com.hhcl.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;



public class Mailer {
	
	Properties error_prop=new Properties();
	static final Logger LOGGER = Logger.getLogger(MailServices.class);
	
public  void send(StringBuffer toMail,String subject,String msg,StringBuffer CC_mail,String Name,String EMPIMAGE){

final String user="noreply@heterohealthcare.com";//change accordingly
final String pass="Hhcl@123";
//1st step) Get the session object	
Properties props = new Properties();
/*props.put("mail.smtp.host", "mail.javatpoint.com");//change accordingly
props.put("mail.smtp.auth", "true");*/

    /*props.put("mail.smtp.host","czismtp.logix.in");  
	props.put("mail.smtp.port","587"); 
	props.put("mail.smtp.starttls.enable", "true");
	//props.put("mail.transport.protocol", "true");
	props.put("mail.smtp.auth", "true");*/
	
	   props.put("mail.smtp.host","smtp.office365.com");  
		props.put("mail.smtp.port","587"); 
		props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.transport.protocol", "true");
		props.put("mail.smtp.auth", "true");
		

Session session = Session.getDefaultInstance(props,
new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(user,pass);
   }
});
//2nd step)compose message
try {
//StringBuffer toMail=new StringBuffer();
//toMail.append(to);
 MimeMessage message = new MimeMessage(session);
 message.setFrom(new InternetAddress(user));
 
 LOGGER.debug(toMail.toString() + "**********Mail ID*********" +CC_mail.toString());
 //message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMail.toString()));
 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail.toString(), false));
 
 try {
 message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC_mail.toString()));
 }catch(Exception emm) {
	emm.printStackTrace(); 
	
	LOGGER.debug("CC MAIL Error Sending mail at Mailer:" + emm);
	
 }
 
 String rtstr = "SUCCESS";
 
 FileReader reader = null;
 try
 {
 	
 	
	    
	    try
	    {
	      /*String filepath = configpath.concat("payslippolar\\email.properties");*/
	      String filepath = "C:\\ATT_SCHEDULAR\\BDALERTS\\config_att.properties";
	      reader = new FileReader(filepath);
	    }
	    catch (FileNotFoundException e)
	    {
	      rtstr = "FAILD";
	      
	      e.printStackTrace();
	    }
	    Properties p = new Properties();
	    try
	    {
	      p.load(reader);
	    }
	    catch (IOException e)
	    {
	      rtstr = "FAILD";
	      e.printStackTrace();
	    }
  final String Subject = p.getProperty("SUBJECT").toString();
	    
   //message.setFrom(new InternetAddress(user));
   
   
  // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
  // message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail.toString()));
   
   message.setSubject(Subject);
   
   
   try {

       MimeMultipart multipart = new MimeMultipart("related");

       // first part (the html)
       BodyPart messageBodyPart = new MimeBodyPart();
       String htmlText = "Dear  Mr./Ms.    "+Name+", <br/> "
       		+ "<br/> <div style='color:blue'> <em>\"Hetero Healthcare & Azista\" family is wishing you a happy birthday and a successful year ahead..<br/> We wish you all the success in your career and job going forward.</em> </div> "
       		+ ""
       		+ "<br/><br/> <div width='380' height='320'><img width='420' height='420' src=\"cid:image\" ></div>"
       		+ "<div><br></div> <div>Regards, <br> Hetero Healthcare Ltd , <br> Azista Industries Pvt. Ltd.</div>";
       
       messageBodyPart.setContent(htmlText, "text/html");
       // add it
       multipart.addBodyPart(messageBodyPart);

       // second part (the image)
       messageBodyPart = new MimeBodyPart();
       MimeBodyPart messageBodyPart1 = new MimeBodyPart();

       
       try {
       DataSource fds= new FileDataSource("C:/ATT_SCHEDULAR/BDALERTS/images/bday0.jpg");
       //DataSource fds = new FileDataSource("C:/Users/Komal.k/Desktop/happy_bday.png");
       try {
        fds = new FileDataSource("C:/ATT_SCHEDULAR/BDALERTS/images/bday"+EMPIMAGE+".jpg");
       }catch(Exception err) {
    	   
    	   System.out.println("Iam at Mailer Class err----->" +err);
    	   fds= new FileDataSource("C:/ATT_SCHEDULAR/BDALERTS/images/bday0.jpg");;
       }
       
       //C:\ATT_SCHEDULAR\BDALERTS\images

       messageBodyPart.setDataHandler(new DataHandler(fds));
       
       }catch(Exception ERRD) {
    	   System.out.println(ERRD);
       }
       
       
       messageBodyPart.setHeader("Content-ID", "<image>");

       // add image to the multipart
       
    
       
       
       multipart.addBodyPart(messageBodyPart);
       
     /*  BodyPart messageBodyPart4 = new MimeBodyPart();
       String htmlText1 = "Regards <br> Hetero Healthcare Ltd. & Azista Industries Pvt.Ltd";
       messageBodyPart4.setText(htmlText1);
       multipart.addBodyPart(messageBodyPart4);
*/
       // put everything together
       message.setContent(multipart);
       // Send message
       Transport.send(message);

       System.out.println("Sent message successfully....");

       LOGGER.debug("Mail Send Successfully....!");
       
    } catch (MessagingException e) {
    	LOGGER.debug("Mail Send Error....!" +e.toString());
       throw new RuntimeException(e);
       
    }
   
 System.out.println("Done");

 } catch (MessagingException e) {
	 
	 LOGGER.debug("MessagingException Error Sending mail at Mailer:" + e);
	throw new RuntimeException(e);
	
	 
	
 }
 
}
 catch(Exception e) {
System.err.println(e);

LOGGER.debug("Mail Send Error2....!" +e.toString());

 }
}
}


	


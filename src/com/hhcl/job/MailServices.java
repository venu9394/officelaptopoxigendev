package com.hhcl.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

class MailServices extends Thread{
String ThreadCnt;
String ThreadCnt1;
String ID;
String NAME;
Map map1=new HashMap();
Mailer SubMail=new Mailer();


Properties error_prop=new Properties();
static final Logger LOGGER = Logger.getLogger(MailServices.class);

MailServices(Map Obj,String k,int j) {
	
	this.ThreadCnt=k;
	this.ThreadCnt1=Obj.get("EMPID_"+k).toString();
	this.ID=Obj.get("EMPID_"+k).toString();
	this.NAME=Obj.get("EMPNAME_"+k).toString();
	this.map1=Obj;
	System.out.println(ThreadCnt1 + "***********************" +j);
	 
	   
}
@SuppressWarnings("unchecked")

public static void main(String[] args) {
		
	/*MailServices test[] = new MailServices[5];
		Map map1=new HashMap();
		int cont=0;
		*/
		/*for(int i=0;i<5;i++) {
			int k=i;
			map1.put("D", ""+k+"");
			test[i]=new MailServices(map1,""+k+"");
			test[i].start();
			System.out.println(k +" ::Thread Loop::" +i);
			
		}*/
		
	}

	@Override
	public  synchronized void  run() {
		// TODO Auto-generated method stub
		
		
		
		System.out.println(ThreadCnt +" :: Run Loop 1::" + ThreadCnt1);
		
		System.out.println(NAME +" :: Run Loop 2::" + ID);
		
		String  to=null, subject="Wishing you a very Happy Birthday..!", msg=null;
		
		StringBuffer toMail=new  StringBuffer(); 
		StringBuffer CC_Mail=new  StringBuffer();
		Mailer SubMail=new Mailer();
		
		
		//toMail.append("komal.kumari@heterohealthcare.com");
		
		 toMail.append(map1.get("EMPMAIL_"+ID).toString().toLowerCase());
		
		 String CC_Mailmgr=map1.get("EMPMGR_"+ID).toString().toLowerCase();
		 
		 if(!CC_Mailmgr.equalsIgnoreCase("0")) {
		  CC_Mail.append(map1.get("EMPMGR_"+ID).toString().toLowerCase());
		 }
		 
		 String EMPIMAGE=map1.get("EMPIMAGE_"+ID).toString();
		 
		 String HODMail="";
		 
		 try {
		 
		  HODMail=(map1.get("EMPHOD_"+ID).toString().toLowerCase()).split("~")[2];
		  if(HODMail.equalsIgnoreCase("0")) {
			  HODMail="0";
		  }
		 }catch(Exception Err) {
			 Err.printStackTrace();
		 }
		 System.out.println("HODMail----->" +HODMail);
		// HODMail="venubabu.g@heterohealthcare.com";
		 
		 if(!HODMail.equalsIgnoreCase("0")) {
		 CC_Mail.append(",");
		 CC_Mail.append(HODMail);
		 }
		//toMail.append("komal.kumari@heterohealthcare.com");
		
		//toMail.append("venubabu.g@heterohealthcare.com");
		
		//LPHYD00037686667
		
		//CC_Mail.append(map1.get("EMPMGR_"+ID).toString().toLowerCase());
		//CC_Mail.append(",");
		/*if(!map1.get("EMPMGR_"+ID).toString().equalsIgnoreCase("NA")) {
		  // CC_Mail.append("komal.kumari@heterohealthcare.com");
		  // CC_Mail.append(map1.get("EMPMGR_"+ID).toString().toLowerCase());
		}*/
		
	
		
		StringBuffer htmlboddy=new  StringBuffer();
	
		
		
		/*htmlboddy.append(" <table class='leave-table'>   ");
		htmlboddy.append(" <tr>   ");
		htmlboddy.append(" <th colspan='10' class='title-txt' style='font-style:italic'> Swipe Information</th>   ");
		htmlboddy.append(" </tr>   ");
		htmlboddy.append(" <tr>   ");
		htmlboddy.append(" <th  colspan='3' >ID#Name</th>   ");
		htmlboddy.append(" <th  colspan='3' >DATE</th>   ");
		htmlboddy.append(" <th  colspan='2' >IN</th>   ");
		htmlboddy.append(" <th  colspan='2' >OUT</th>   ");
		htmlboddy.append("  ");
		htmlboddy.append(" </tr>   ");
		htmlboddy.append(" <tr>   ");
		htmlboddy.append(" <td colspan='3'> "+ ID+"#"+map1.get("EMPNAME_"+ID).toString().toUpperCase()+" </td>   ");
		htmlboddy.append(" <td colspan='3'> "+ map1.get("EMPDATE_"+ID).toString()+" </td>   ");
		htmlboddy.append(" <td  colspan='2'>"+map1.get("EMPIN_"+ID).toString()+"</td>   ");
		htmlboddy.append(" <td  colspan='2' >"+map1.get("EMPOUT_"+ID).toString()+"</td>   ");
		*/
		
		
		
		
		
		LOGGER.info(ID+ "Before Sending e-mail : "+NAME+"  and ID :"+ID+"  AND email-:"+toMail);
		
		try {
		  String Name=map1.get("EMPNAME_"+ID).toString() ;
		  SubMail.send(toMail, subject, htmlboddy.toString(),CC_Mail ,Name,EMPIMAGE);
		}catch(Exception err) {
			
			LOGGER.debug("Error Sending mail :" + err);
			err.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}

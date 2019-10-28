package com.hhcl.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class job1_sub {
	  
	Properties error_prop=new Properties();
	static final Logger LOGGER = Logger.getLogger(job1_sub.class);
	
public synchronized Map calljob (int minLimit, int MaxLimit, Connection con,String FETCHQUERY,String INSERTQURY,int CallNum,Connection conSql,String Masql_FETCHQUERY) throws SQLException{
	
	 //System.out.println("15");
		
		Map  map=new HashMap()  ;
		int[] excutBatch;
		ArrayList empiid=new ArrayList();
		Map parrams=new HashMap();
		int finalCount=0;
		System.out.println(FETCHQUERY);
		
		Set ManagerData=new HashSet();
		Map ManagerData_map=new HashMap();
		
		
			
			//System.out.println("12");
			boolean flag=false;
			int MiniLimit_m=minLimit+1000;
			int MaxLimit_m=MaxLimit+1000;
			String DATEON=null;
			String DAYTYPE=null;
			
			map.put("minLimit", ""+MiniLimit_m+"");
			map.put("MaxLimit", ""+MaxLimit_m+"");
			//String Query=Job1.fetchQuery;
			/*PreparedStatement pstmt = con.prepareStatement( Query.toString());*/
			/*select IFNULL(A.HOLIDAYDATE,'0000-00-00') HOLIDAYDATE
			from HCLHRM_PROD.TBL_HOLIDAYS A left join
			HCLHRM_PROD.TBL_EMPLOYEE_PRIMARY B ON A.BUSINESSUNITID=B.companyid
			where A.employeeid in(20314)*/
			
			//System.out.println("2");
			PreparedStatement pstmt=null;
		    pstmt = con.prepareStatement(FETCHQUERY);
		   
		//   Masql_FETCHQUERY= "select  A.AttendanceDate , B.EmployeeCode, B.EmployeeId , B.EmployeeName ,convert(varchar,CAST(A.InTime as datetime),8) as intime,convert(varchar,CAST(A.OutTime as datetime),8) as outtime , A.LateBy , A.EarlyBy , A.ShiftId,A.Duration ,convert(varchar(5),DateDiff(s, A.InTime, A.outTime)/3600)+':'+convert(varchar(5),DateDiff(s, A.InTime, A.outTime)%3600/60)+':'+convert(varchar(5),(DateDiff(s, A.InTime, A.outTime)%60)) workinghours from dbo.AttendanceLogs  A left join dbo.Employees  B ON B.employeeid=A.employeeid where A.AttendanceDate in(?) and B.EmployeeCode in(?)";
		  
		   StringBuffer Buffatt=new StringBuffer();
		   StringBuffer Holidays=new StringBuffer();
	      
	       
	         
	        /* Buffatt.append(" select  A.AttendanceDate ,B.EmployeeCode, B.EmployeeId , B.EmployeeName ,A.InTime as intime,A.OutTime as outtime , A.LateBy , A.EarlyBy , A.ShiftId,A.Duration As workinghours from dbo.AttendanceLogs  A ");
	         Buffatt.append(" left join dbo.Employees  B ON B.employeeid=A.employeeid where A.AttendanceDate in(?) and B.EmployeeCode in(?) ");
	        
	         */
		  // PreparedStatement MsSqlpstmt = null;
		   Statement MsSqlpstmt = conSql.createStatement();
		 
		   ResultSet rs=null;
		  // pstmt.setInt(1, minLimit); for limit Set
		   System.out.println(pstmt);
		  //   PreparedStatement stmt=con.prepareStatement(INSERTQURY);
		     rs = pstmt.executeQuery();
		     StringBuffer EmployeeId=new StringBuffer();
		     StringBuffer EmployeeIdMysql=new StringBuffer();
		 
		   int BirthImage=0; 
		   while (rs.next()) {
		    
			   flag=true;
			   DATEON=rs.getString("DOB");
			   List myList_Main = new ArrayList();
			   List myList_Sub = new ArrayList();
			   
			//   EmpCode, NAME, DOB, Emp_Mail, Reportee_Id, Reportee_Name, Reportee_Email, HOD_details
			   
			   
			   empiid.add(rs.getString("EmpCode"));
			   
			   map.put("EMPID_"+rs.getString("EmpCode") , rs.getString("EmpCode"));
			   map.put("EMPNAME_"+rs.getString("EmpCode") , rs.getString("NAME"));
			   map.put("EMPMAIL_"+rs.getString("EmpCode") , rs.getString("Emp_Mail"));
			   map.put("EMPMGR_"+rs.getString("EmpCode") , rs.getString("Reportee_Email"));
			   map.put("EMPHOD_"+rs.getString("EmpCode") , rs.getString("HOD_details"));
			   
			   map.put("EMPDOB_"+rs.getString("EmpCode") , rs.getString("DOB"));
			   
			   EmployeeId.append(rs.getString("EmpCode"));
			   EmployeeId.append(",");
			   
			   finalCount ++;
			   
			   map.put("EMPIMAGE_"+rs.getString("EmpCode") , BirthImage);
			   
			   if(BirthImage==5) {
				   BirthImage=0; 
			   }else {
				   BirthImage=BirthImage+1;
			   }
			  
			
		   }
	    EmployeeId.append("0101010101");
	    EmployeeIdMysql.append("0101010101");
	    
	  
	    

		try {
		System.out.println(DATEON +" ~~~ " +EmployeeId);
		System.out.println(flag +" ::: flag");
		   
		  // flag=false;
		   
	Iterator ltr=empiid.iterator();
	if(flag){
		
		MailServices test[] = new MailServices[empiid.size()+2];
		int i=0;
		
		
		try{
			
		while(ltr.hasNext()){
			
			
			
			Thread.sleep(10000);
			String Empid=ltr.next().toString();
			
		try {
			test[i]=new MailServices(map,Empid,i);
			test[i].start();
			/*
			test[i].sleep(1000);
			test[i].join();*/
			
			i=i+1;
			
		}catch(Exception Err) {
			Err.printStackTrace();
		}
			
		}
		try {
			
		
		for(int j=0 ;j<i ;j++) {
		  
			test[j].join();
			Thread.sleep(1000);
			
		}
		}catch(Exception err) {
			err.printStackTrace();
		}
		
	

		}catch(Exception err){
			flag=false;
			System.out.println("Exception At AddBatch"+err);
		}
		
		map.put("exuFlag", "false");
		// map.put("exuFlag", "true"); for limit use
	     map.put("DATEON", DATEON);
	       
		
	}else{
		   
	    map.put("minLimit", "0");
		map.put("MaxLimit", "0");
		map.put("exuFlag", "false");
	   
   }
		}catch(Exception Err){
			System.out.println("Exception at sub class" +Err);
		}
	     System.out.println("Final Mail processed Count :::" +finalCount);
	     LOGGER.info("******************************* Final Count At End************" +finalCount);
		 return map;
		}
  }


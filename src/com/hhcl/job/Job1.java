package com.hhcl.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bonecp.util.DBUtil;

public class Job1 implements Job { 
	

        private int minLimit=0;
		private int MaxLimit=1000; 
		private String DATEON="0000-00-00";
		private String DATEON1="0000-00-00";
		private String Flag=null;
		private String Flag2 ="false";
		private boolean whilflag=true;
		
		 Connection con=DBUtil.getConnection(); 
		 Connection conSql=con;
		//Connection conSql=Util_mssql.getConnection(); 
		public static String FETCHQUERY=null;
		public static String INSERTQURY=null;
		public static String UPDATEQUEY=null;
		public static String Masql_FETCHQUERY=null;
		
		
		

	public Job1(){
		  this.minLimit=minLimit;
		  this.MaxLimit=MaxLimit;
		  this.Flag=Flag;
		  this.whilflag=whilflag;
		  this.DATEON=DATEON;
		  this.DATEON1=DATEON1;
		  
	      }
	
	
	public void execute (JobExecutionContext context)  throws JobExecutionException {
		 
		
		System.out.println( "Bone Cp Connection Status" +con);
		
		 if(con!=null){
		job1_sub obj=new  job1_sub();
		 
		
		try {
			
			  Properties prop = Util.loadPropertiesFile();
		      FETCHQUERY=prop.getProperty("FETCHQUERY");
			  INSERTQURY=prop.getProperty("INSERTQURY");
			  UPDATEQUEY=prop.getProperty("UPDATEQUEY");
			  Masql_FETCHQUERY=prop.getProperty("Masql_FETCHQUERY");
			  
			  
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}
		 
		try {
		 
            Map hm=obj.calljob(minLimit, MaxLimit,con,FETCHQUERY,INSERTQURY,1,conSql,Masql_FETCHQUERY);
             
            try{
            	
              DATEON1=hm.get("DATEON").toString();
              
            }catch(Exception er){
            	
            }
          minLimit=Integer.parseInt(hm.get("minLimit").toString());
          MaxLimit=Integer.parseInt(hm.get("MaxLimit").toString());
          Flag=hm.get("exuFlag").toString();
          
          System.out.println(Flag + "exuFlag at Job1.java" + Flag);
    //      System.out.println(hm.toString());
          hm=null;
       
    /* if(minLimit>0 && minLimit>0 && Flag.equalsIgnoreCase("true") ){
    	 whilflag=true;
  	  
    	 //System.out.println("HIII");
    	 
    	 while(whilflag){
    		 
    		 try{
    		 Thread.sleep(1000*2);
    		 }catch(Exception th){
    			 System.out.println(th);
    		 }
    		 
    		 
    	  hm=obj.calljob(minLimit, MaxLimit,con,FETCHQUERY,INSERTQURY,2,conSql,Masql_FETCHQUERY);
    	  
    	  minLimit=Integer.parseInt(hm.get("minLimit").toString());
          MaxLimit=Integer.parseInt(hm.get("MaxLimit").toString());
          Flag=hm.get("exuFlag").toString();
          
          if(Flag.equalsIgnoreCase("true")){
        	  whilflag=true;
        	  
        	  try {
        	  if(con==null) {
        	    con=DBUtil.getConnection();
        	    conSql=con;
        	  }
        	  }catch(Exception errr) {
        		  errr.printStackTrace();
        	  }
        	  
        	  
    	 }else{
    		 whilflag=false;
    	 }   
     // t = new Thread();
  	 // t.start();
  	    //  run();
  	  
      }
    	 if(hm.get("DATEON")!=null){
    	 try{
    	     
    	       PreparedStatement stmt=con.prepareStatement(UPDATEQUEY);
    	       stmt.setString(1, hm.get("DATEON").toString());
    	       stmt.addBatch();
    	       int []updateData=stmt.executeBatch();
    	       System.out.println(hm.get("DATEON").toString()+"::updateData ::" +updateData.length);
    	      
    	       
    	       }catch(Exception err){
    	    	   err.printStackTrace();
    	       }
    	 }
    	 
    	 
     }*/
     
     System.out.println("hm.get('DATEON') ::" +DATEON1);
     
    /* if(DATEON1!=null && DATEON1!="0000-00-00"){
    	 try{
    	       PreparedStatement stmt=con.prepareStatement(UPDATEQUEY);
    	       stmt.setString(1, DATEON1);
    	       stmt.addBatch();
    	       int []updateData=stmt.executeBatch();
    	       //System.out.println(hm.get("DATEON").toString()+"::updateData ::" +updateData.length);
    	       }catch(Exception err){
    	    	   err.printStackTrace();
    	       }
    	 }*/
     } catch(Exception ERR){
		System.out.println("Error at Main Class " +ERR);
	}finally
		{
			try {
				if(con!=null)
				con.close();
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conSql!=null)
				conSql.close();
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}else{
		System.out.println("Connection issue ::" +con);
	}
	
}
}
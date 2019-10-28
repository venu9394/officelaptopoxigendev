package com.hhcl.init;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class InitializeServlet extends HttpServlet {


 public void init() throws ServletException {
    
    try {
        System.out.println("Execute Job ***VENU ****************");
        
        MyApp objPlugin = new MyApp();
        objPlugin.calljob();
        
        
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    
  }
 
}
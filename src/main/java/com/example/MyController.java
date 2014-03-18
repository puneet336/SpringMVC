
package com.example;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@Scope("session")
public class MyController implements BeanFactoryAware {
	static int i=0,ii=0;
	int iii=0;
	Connection con=null;
	Statement stmt = null;
	ArrayList<String> list;
	org.springframework.web.servlet.i18n.CookieLocaleResolver cookieLocaleResolver;
    @RequestMapping(value="/template", method={RequestMethod.GET})
    public String homePage(HttpServletRequest request,HttpServletResponse response) {
    	 
    	list=new ArrayList<>();
    	if(response==null)
    	{
    		System.exit(1);
    	}
    	HttpServletResponse httpResponse = response ;
   /*
    	httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    	httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
    	httpResponse.setDateHeader("Expires", 0);
    	Locale bLocale = new Locale("de");
    	response.setLocale(bLocale);
    	*/
    	if(request.getParameter("language")!=null)
    	{
    	if(request.getParameter("language").equals("en"))
    	cookieLocaleResolver.setDefaultLocale(new Locale("en"));
    	else if(request.getParameter("language").equals("de"))
    		cookieLocaleResolver.setDefaultLocale(new Locale("de"));
    	}
    	request.setAttribute("resultType", "GET"+i+" getter"+(++i)+"  instance"+(++iii));
    	request.setAttribute("result","ndefault data");
	return "contact";
    }
    
    @RequestMapping(value="/template", method={RequestMethod.POST})
    public String resultPage(HttpServletRequest request) {
    	
    	try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:/home/cdac/Downloads/sqlite-autoconf-3080300/doctor.db");
			//con = DriverManager.getConnection("jdbc:sqlite://home//cdac//Downloads//sqlite-autoconf-3080300//doctor.db");
			stmt=con.createStatement();
			list=new ArrayList<>();
			ResultSet resultSet=stmt.executeQuery("select * from DOCTORS");
			while(resultSet.next())
			{
				int id=resultSet.getInt("ID");
				String name=resultSet.getString("NAME");
				list.add(Integer.toString(id)+" "+name);
				
			}
			resultSet.close();	
			stmt.close();
			con.close();
			request.getSession().setAttribute("resultType", "POST:");
			request.getSession().setAttribute("result", list);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		 
			request.setAttribute("errorCause",errors );
			return "error";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		 
			request.setAttribute("errorCause",errors);
			return "error";
		}
    	
    	
    	
    	//request.getSession().setAttribute("result","post");
	return "contact";
    }
    
    @RequestMapping(value="/fetchInsertPage", method={RequestMethod.GET})
    public String getInsertPage(HttpServletRequest request) {
    	
    	return "insert";
    }
    @RequestMapping(value="/insertData", method={RequestMethod.POST})
    public String addDoctor(HttpServletRequest request) {
		
    String sql = "INSERT INTO DOCTORS (ID,NAME) " +
                "VALUES ("+request.getParameter("ID")+",'"+request.getParameter("NAME")+"');";
 try {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:/home/cdac/Downloads/sqlite-autoconf-3080300/doctor.db");
		stmt=con.createStatement();
	   stmt.executeUpdate(sql);
	 
	   stmt.close();
		con.close();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
	 
		request.setAttribute("errorCause",errors);
		return "error";
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	 
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
	 
		request.setAttribute("errorCause",errors);
		return "error";
	}
    
    
    	request.setAttribute("result", "doctor added success!"+request.getParameter("ID")+request.getParameter("NAME"));
   
    	return "contact";
	}

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		// TODO Auto-generated method stub
		 cookieLocaleResolver=(org.springframework.web.servlet.i18n.CookieLocaleResolver)	arg0.getBean("localeResolver");
		cookieLocaleResolver.setDefaultLocale(new Locale("en"));
		i++;
	}
    
    
}


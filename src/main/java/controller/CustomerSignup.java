package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dto.Customer;

@WebServlet("/CustomerSignup")
public class CustomerSignup extends HttpServlet {

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	CustomerDao dao=new CustomerDao();
//	String name=req.getParameter("name");
	long mobile=Long.parseLong(req.getParameter("mobile"));
	String email=req.getParameter("email");
//	String password=req.getParameter("password");
//	String gender=req.getParameter("gender");
//	String dob=req.getParameter("dob");
//	resp.getWriter().print("<h1>"+name+"</h1>"
//			+"<h1>"+mobile+"</h1>"
//			+"<h1>"+email+"</h1>"
//			+"<h1>"+password+"</h1>"
//			+"<h1>"+gender+"</h1>"
//			+"<h1>"+dob+"</h1>");
	Date date=Date.valueOf(req.getParameter("dob"));
	int age=Period.between(date.toLocalDate(), LocalDate.now()).getYears();
	if(age<18)
	{
		resp.getWriter().print("<h1>Not 18+!! Too early to create an Bank Account<h1>");
		 req.getRequestDispatcher("Signup.html").include(req,resp);
		 
	}
	else {
		if(dao.check(mobile).isEmpty()&&dao.check(mobile).isEmpty())
		{
		Customer customer=new Customer();
		customer.setName(req.getParameter("name"));
		customer.setGender(req.getParameter("gender"));
		customer.setPassword(req.getParameter("password"));
		customer.setDob(date);
		customer.setEmail(email);
		customer.setMobile(mobile);
		dao.save(customer);
		Customer customer2=dao.check(email).get(0);
		
		resp.getWriter().print("<h1> Account created successfully<h1>");
		if(customer2.getGender().equals("male"))
			resp.getWriter().print("<h1> hello sir<h1>");
		else
			resp.getWriter().print("<h1> hello mam<h1>");
		resp.getWriter().print("<h1>Your customer Id is:"+ customer2.getCust_id()+"<h1>");
		req.getRequestDispatcher("Home.html").include(req,resp);
		
		
		
	   }
		else {
			resp.getWriter().print("<h1> Email or Mobile Number already exists ...<h1>");
			req.getRequestDispatcher("Signup.html").include(req,resp);
		     }
			
		}
	
}
	
}
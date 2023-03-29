package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BankDao;
import dto.BankAccount;


@WebServlet("/withdraw")
public class Withdraw extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		double amt=Double.parseDouble(req.getParameter("amt"));
		Long acno=(Long) req.getSession().getAttribute("acno");
		BankDao bankDao= new BankDao();
		BankAccount account=new BankAccount();
				
		
				if(amt>account.getAmount()) 
				{
					resp.getWriter().print("<h1>Insufficient Balance</h1>");
					req.getRequestDispatcher("AccountHome.jsp").include(req, resp);
					
				}else {if(amt>account.getAclimit()) 
				{
					resp.getWriter().print("<h1>Out of the Limit Enter Amount Within</h1>" +account.getAclimit());
					req.getRequestDispatcher("AccountHome.jsp").include(req, resp);
					
				}else {
					account.setAmount(account.getAmount() -amt);
					bankDao.update(account);
					resp.getWriter().print("<h1>Amount Withdrawed Succesfully</h1>" +account.getAclimit());
					req.getRequestDispatcher("AccountHome.jsp").include(req, resp);
					
				}
					
				}
		account.setAmount(account.getAmount()-amt);
	}

}

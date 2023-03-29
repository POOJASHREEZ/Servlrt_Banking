package controller;


	import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import dao.BankDao;
	import dto.BankAccount;
import dto.BankTransaction;


	@WebServlet("/deposit")
	public class Deposit extends HttpServlet{
		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			double amt=Double.parseDouble(req.getParameter("amt"));
			long acno=(long) req.getSession().getAttribute("acno");
			BankDao bankDao= new BankDao();
			BankAccount account=new BankAccount();
			account.setAmount(account.getAmount() +amt);
			
			BankTransaction bankTransaction =new BankTransaction();
			bankTransaction.setDeposit(amt);
			bankTransaction.setWithdraw(0);
			bankTransaction.setBalance(account.getAmount());
			bankTransaction.setDateTime(LocalDateTime.now());
			 
			List<BankTransaction> list=account.getTransactions();
			list.add(bankTransaction);
			
			account.setTransactions(list);
			
			
			
			
			bankDao.update(account);
			resp.getWriter().print("<h1>Amount Added Succesfully</h1>" +account.getAclimit());
			req.getRequestDispatcher("AccountHome.jsp").include(req, resp);
}
}
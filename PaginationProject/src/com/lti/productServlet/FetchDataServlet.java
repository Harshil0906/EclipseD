package com.lti.productServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lti.dao.ProductDao;
import com.lti.exception.DataAccessException;
import com.lti.model.Product;


@WebServlet("/FetchDataServlet")
public class FetchDataServlet extends HttpServlet {
	
  // public int from=0;
   //public int to=5;
   private int pageSize=5;
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   HttpSession session = request.getSession();
	   Integer cursor = (Integer) session.getAttribute("cursor");
	   if(cursor==null) 
		   cursor = 1;
	 
	/*  else if(cursor==15){
		  cursor =1;
	  }*/
	   
	  String action = request.getParameter("action");
	  if(action != null) {
		  if(action.equals("next"))
			  cursor += pageSize;
		  else if(action.equals("prev"))
			  cursor -= pageSize;
	  }
	   
	   session.setAttribute("cursor", cursor);
	   PrintWriter out=response.getWriter();
	  
	   //to get range from user
	   /* out.print("<h1>Enter the range of rows to be fetched<h1>");
	   out.print("<form>");
	   out.print("<h2>From rows<h2><input name='from' >");
	   out.print("<h2>To row<h2><input name='to'>");
	   out.print("<button type='submit' name='submit'>SUBMIT</button>");
	   
	   String s1=(String)request.getParameter("from");
	   from=Integer.parseInt(s1);
	   String s2=(String)request.getParameter("to");
	   to=Integer.parseInt(s2);*/
	   
	   
	   ProductDao p = new ProductDao();
	 
	try {
		  List<Product> products = p.fetch(cursor,cursor + pageSize -1);
		out.print("<table border = 1 cellpadding=5 cellspacing =0>");
		out.print("<tr><th>Product ID</th><th>Product name</th><th>Price</th><th>Quantity</th></tr>");
		for(Product obj  : products ){
			out.print("<tr>");
			out.print("<td>"+obj.getId()+"</td>");
			out.print( "<td>"+obj.getName()+"</td>");
			out.print("<td>"+obj.getPrice()+"</td>");
			out.print("<td>"+obj.getQuantity()+"</td>");
			out.print("</tr>");
			}
		out.print("</table>");
		out.print("<button><a href ='FetchDataServlet?action=prev'>Prev</a></button>");
		out.print("<button><a href ='FetchDataServlet?action=next'>Next</a></button>");
		
		
		
		
	} 
	catch (DataAccessException e) {
		
		e.printStackTrace();
	}
	   
	   
	   
   }


}

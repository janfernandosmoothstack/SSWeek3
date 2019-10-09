package com.ss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ss.dto.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/login", "/login/", "/login/*"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
    	System.out.println("Servlet Initialized");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		Gson gson = new Gson();
		
		List<User> users = new ArrayList<User>();
		
		users.add(new User("janFern", "jan123"));
		users.add(new User("sayExcess", "shrestha"));
		users.add(new User("santosh", "football12"));
		users.add(new User("afia", "oneRing"));
		
		if(path.contains("/login")) {
			try {
				response.setContentType("text");
				
				User readUser = gson.fromJson(request.getReader(), User.class);
				
				PrintWriter out = response.getWriter();
				
				List<User> search = users.stream()
										.filter(user -> (user.getUserName().equals(readUser.getUserName())) &&
														(user.getPassword().equals(readUser.getPassword())))
										.collect(Collectors.toList());
				
				if (search.isEmpty()) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				} else {
					out.print("Access Granted");
				}
				
				out.flush();
				
			} catch (Exception e){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}	
		}
	}
}

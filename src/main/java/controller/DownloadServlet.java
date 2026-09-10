package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import model.User;
import utls.CookieUtil;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		String url = "";
		switch (action) {
			case "checkUser":
				url = checkUser(request, response);
				break;
			case "registerUser":
				url = registerUser(request, response);
				break;
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		HttpSession session = request.getSession(true);
		session.setAttribute("productId", productId);
		User user = (User) session.getAttribute("user");
		
		String url = "";
		if (user == null) {
			Cookie[] cookies = request.getCookies();
			String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
			
			if (emailAddress == null || emailAddress.equals("")) {
				url = "/register.jsp";
			} else {
				ServletContext sc = getServletContext();
				String path = sc.getRealPath("/WEB-INF/EmailList.txt");
//				user = UserIO.getUser(emailAddress, path);
				session.setAttribute("user", user);
				url = "/" + productId + "_download.jsp";
			}
		} else {
			url = "/" + productId + "_download.jsp";
		}
		
		return url;
	}
	
	private String registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		HttpSession session = request.getSession(false);
		session.setAttribute("user", user);
		
		Cookie c = new Cookie("emailCookie", email);
		c.setMaxAge(60 * 60 * 24 * 365 * 2);
		c.setPath("/");
		response.addCookie(c);
		
		String productId = (String) session.getAttribute("productId");
		String url = "/" + productId + "_download.jsp";
		return url;
	}

}

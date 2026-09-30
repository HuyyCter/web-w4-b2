package controller;

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
       
    public DownloadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null || action.trim().isEmpty()) {
			action = "viewAlbum";
		}
		
		String url = "/";
		switch (action) {
			case "viewAlbum":
				response.sendRedirect(request.getContextPath() + "/");
				return;
			case "checkUser":
				url = checkUser(request, response);
				break;
			case "registerUser":
				url = registerUser(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/");
				return;
		}
		
		if (url != null && !url.isEmpty()) {
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("productId");
		HttpSession session = request.getSession(true);
		
		if (productId != null && !productId.trim().isEmpty()) {
			session.setAttribute("productId", productId);
		} else {
			productId = (String) session.getAttribute("productId");
		}
		
		if (productId == null || productId.trim().isEmpty()) {
			productId = "8601";
			session.setAttribute("productId", productId);
		}
		
		User user = (User) session.getAttribute("user");
		String url = "";
		if (user == null) {
			Cookie[] cookies = request.getCookies();
			String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
			
			if (emailAddress == null || emailAddress.trim().isEmpty()) {
				url = "/register.jsp";
			} else {
				user = new User("", "", emailAddress);
				session.setAttribute("user", user);
				session.setAttribute("verifiedAccess", true);
				url = "/html/" + productId + "_download.jsp";
			}
		} else {
			session.setAttribute("verifiedAccess", true);
			url = "/html/" + productId + "_download.jsp";
		}
		
		return url;
	}
	
	private String registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		User user = new User(firstName, lastName, email);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		session.setAttribute("verifiedAccess", true);
		
		Cookie c = new Cookie("emailCookie", email != null ? email : "");
		c.setMaxAge(60 * 60 * 24 * 365 * 2);
		c.setPath("/");
		response.addCookie(c);
		
		String productId = (String) session.getAttribute("productId");
		if (productId == null || productId.trim().isEmpty()) {
			productId = "8601";
		}
		String url = "/html/" + productId + "_download.jsp";
		return url;
	}

}

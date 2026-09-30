package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import utls.CookieUtil;

import java.io.IOException;

/**
 * Filter to restrict direct access to download pages in /html/*
 */
@WebFilter(urlPatterns = {"/html/*"})
public class DownloadAccessFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

    public DownloadAccessFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		
		boolean verified = false;
		if (session != null) {
			Boolean v = (Boolean) session.getAttribute("verifiedAccess");
			if (v != null && v) {
				verified = true;
			}
		}
		
		// If not verified in session, check if user has a valid email cookie
		if (!verified) {
			Cookie[] cookies = httpRequest.getCookies();
			String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
			if (emailAddress != null && !emailAddress.trim().isEmpty()) {
				if (session == null) {
					session = httpRequest.getSession(true);
				}
				session.setAttribute("verifiedAccess", true);
				User user = (User) session.getAttribute("user");
				if (user == null) {
					user = new User("", "", emailAddress);
					session.setAttribute("user", user);
				}
				verified = true;
			}
		}
		
		if (!verified) {
			// User has not registered or verified access => redirect to homepage
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

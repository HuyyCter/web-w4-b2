package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class DownloadAccessFilter
 */
@WebFilter("*.jsp")
public class DownloadAccessFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public DownloadAccessFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		httpRequest.getRequestDispatcher("/register.jsp").forward(httpRequest, httpResponse);
		HttpSession session = httpRequest.getSession(false);
		
		if (session == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			return;
		}
		
		Boolean verified = (session != null) ? (Boolean) session.getAttribute("verifiedAccess") : null;
		
		if (verified == null) {
			// Không có attribute "verifiedAccess" => Chưa từng đi qua Servlet
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

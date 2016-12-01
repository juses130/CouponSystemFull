package com.filters;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

@WebFilter(urlPatterns = { "/rest" }, servletNames = { "Jersey REST Service" })
public class LoginFilter implements Filter {
	
    public LoginFilter() {}
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Filtered Login..");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getContextPath() + "/login.html";
		HttpSession session = req.getSession(false);
			
		// Path for login request that needs to be passed
		String reqURI = req.getRequestURI();
		
		if (reqURI.contains("Login")) {
			// pass the request along the filter chain (if the user trying to login in some of the 3 options)
			chain.doFilter(request, response);
		} // if - contains("Login")
		
		// check if we have facade on the session. if we don't, redirect. if we have, save it in String attribute.
		if(req.getSession().getAttribute("FACADE") == null) {
			System.out.println("No session or facade!" + " Redirect to " + url);
			sendJSONredirect(res, url);
			return;
		} // if - facade == null
		req.getSession().setMaxInactiveInterval(1800);
		if(session != null) {
			
			System.out.println(session.getAttribute("FACADE").getClass().getSimpleName() + " found in session!");
			chain.doFilter(request, response);
		} // session != null
	} // doFilter
			
	public void init(FilterConfig fConfig) throws ServletException {}
	
	private void sendJSONredirect(HttpServletResponse res, String urlRedirect) throws IOException {
		
		// send status 400 (error in the client side)
		res.setStatus(400);
		res.setContentType(MediaType.APPLICATION_JSON);
		// Write to body
		ServletOutputStream  out = res.getOutputStream();
			out.println("{"
					+ "\"url\":\"" + urlRedirect +"\","
					+ " \"errorCode\": 900"
					+ "}");
			return;
	} // sendJSONredirect

} // Class
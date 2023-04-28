package filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class BasicRequestFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		System.out.println(((HttpServletRequest)req).getRequestURI() + " [" + ((HttpServletRequest)req).getMethod() + "]");

		//전달 파라미터 한글 인코딩 처리 UTF-8
		req.setCharacterEncoding("UTF-8");
		
		chain.doFilter(req, resp);
		
	}

}















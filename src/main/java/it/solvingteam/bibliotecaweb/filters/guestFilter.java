package it.solvingteam.bibliotecaweb.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="/guestFilter", value="/accessoEffettuato/ricerca/*,/accessoEffettuato/elencazione/*")
public class guestFilter implements Filter {

    public guestFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Sono nel filter di guest");
		HttpServletRequest servletRequest=(HttpServletRequest) request;
		HttpSession session=servletRequest.getSession();
		if (session.getAttribute("utenteIdentificato")==null||!session.getAttribute("hasGuestRole").equals("true")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

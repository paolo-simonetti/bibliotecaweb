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

@WebFilter(filterName="/adminFilter", value="/accessoEffettuato/inserimento/inserimentoUtente,/accessoEffettuato/aggiornamento/aggiornamentoUtente,/accessoEffettuato/eliminazione/eliminazioneUtente,/accessoEffettuato/ricerca/ricercaUtente")
public class adminFilter implements Filter {

    public adminFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Sono nel filter di admin");
		HttpServletRequest servletRequest=(HttpServletRequest) request;
		HttpSession session=servletRequest.getSession();
		if (session.getAttribute("utenteIdentificato")==null||!session.getAttribute("hasAdminRole").equals("true")) {
			session.invalidate();
			request.setAttribute("permessiMancantiMessage","Non hai i permessi per effettuare questa operazione!");
			request.getServletContext().getRequestDispatcher("jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
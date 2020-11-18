package it.solvingteam.bibliotecaweb.servlet.registrazioneEAccesso;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.Ruolo;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

@WebServlet("/registrazioneEAccesso/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Set<Utente> listaIscritti=null;
		try {
			listaIscritti= MyServiceFactory.getUtenteServiceInstance().elenca();			
		} catch(Exception e) {
			System.err.println("Problemi nel recupero degli utenti iscritti");
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
			return;
		}
		boolean utenteIsRegistrato=false;
		Utente utenteIdentificato=null;
		for (Utente u:listaIscritti) {
			if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
				utenteIsRegistrato=true;
				utenteIdentificato=u;
				break;
			}
		}
		
		if (utenteIsRegistrato) {
			HttpSession session=request.getSession(true);
			session.setAttribute("utenteIdentificato",utenteIdentificato);
			//Salvo in sessione i ruoli che ha l'utente
			boolean hasAdminRole=false;
			boolean hasClassicRole=false;
			boolean hasGuestRole=false;
			for (Ruolo r:utenteIdentificato.getRuoli()) {
				if (r.equals(new Ruolo(NomeRuolo.ADMIN_ROLE))) {
					hasAdminRole=true;
				}
				
				if (r.equals(new Ruolo(NomeRuolo.CLASSIC_ROLE))) {
					hasClassicRole=true;
				}
				
				if (r.equals(new Ruolo(NomeRuolo.GUEST_ROLE))) {
					hasGuestRole=true;
				}
			}
			session.setAttribute("hasAdminRole",hasAdminRole);
			session.setAttribute("hasClassicRole",hasClassicRole);
			session.setAttribute("hasGuestRole",hasGuestRole);
			
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
		} else {
			request.setAttribute("credenzialiErrateMessage","Le credenziali inserite sono errate o non sei registrato");
			request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request,response);
		}
	}

}

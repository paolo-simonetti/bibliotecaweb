package it.solvingteam.bibliotecaweb.servlet.registrazioneEAccesso;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.Ruolo;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.StandAloneUtils;


@WebServlet("/registrazioneEAccesso/ExecuteRegistrazioneServlet")
public class ExecuteRegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteRegistrazioneServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUtente=request.getParameter("nome");
		String cognomeUtente=request.getParameter("cognome");
		String usernameUtente=request.getParameter("username");
		String passwordUtente=request.getParameter("password");
		LocalDate dataRegistrazione=LocalDate.now();
		Utente utenteDaRegistrare=new Utente();
		utenteDaRegistrare.setNomeUtente(nomeUtente);
		utenteDaRegistrare.setCognomeUtente(cognomeUtente);
		utenteDaRegistrare.setDateCreated(dataRegistrazione);
		utenteDaRegistrare.setUsername(usernameUtente);
		utenteDaRegistrare.setPassword(passwordUtente);
		//Inserisco i ruoli ammessi sul db, se non già presenti
		Set<Ruolo> ruoliPresenti=null;
		try {
			MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(new Ruolo(NomeRuolo.ADMIN_ROLE));
			MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(new Ruolo(NomeRuolo.CLASSIC_ROLE));
			MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(new Ruolo(NomeRuolo.GUEST_ROLE));
			ruoliPresenti=MyServiceFactory.getRuoloServiceInstance().elenca();
		} catch(Exception e) {
			System.err.println("Problemi nell'inserimento dei ruoli ammessi");
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/jsp/generali/registrazione.jsp").forward(request,response);
			return;
		}
		// Recupero gli utenti iscritti, per poter verificare se l'utente si sta registrando con credenziali già usate
		Set<Utente> listaIscritti=null;
		try {
			listaIscritti= MyServiceFactory.getUtenteServiceInstance().elenca();			
		} catch(Exception e) {
			System.err.println("Problemi nel recupero degli utenti iscritti");
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/jsp/generali/registrazione.jsp").forward(request,response);
			return;
		}
		if (listaIscritti.size()==0) { // Il primo iscritto voglio che abbia tutte le qualifiche
			
			utenteDaRegistrare.setRuoli(ruoliPresenti);
			try {
				boolean esito=MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaRegistrare);
				request.setAttribute("registrazioneRiuscitaMessage","Registrazione: "+StandAloneUtils.testaOperazione(esito));
			} catch (Exception e) {
				System.err.println("Non sono riuscito ad aggiungere l'utente "+ nomeUtente+ " " + cognomeUtente+ " alla lista degli iscritti");		
				e.printStackTrace();
			} finally {
				request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request, response);				
			}
		} else { // se ci sono già utenti iscritti, verifico che utenteDaRegistrare non sia già tra loro
			boolean utenteGiaRegistrato=false;
			for (Utente u:listaIscritti) {
				if (usernameUtente.equals(u.getUsername())) {
					utenteGiaRegistrato=true;
					break;
				}
			}
			if (utenteGiaRegistrato) {
				request.setAttribute("registrazioneGiaEffettuataMessage", "Sei già registrato! Non ricordi le tue credenziali?");
				request.getServletContext().getRequestDispatcher("jsp/generali/welcome.jsp").forward(request,response);
				return;
			} else {
				utenteDaRegistrare.getRuoli().add(new Ruolo(NomeRuolo.GUEST_ROLE));
				try {
					MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaRegistrare);
				} catch (Exception e) {
					System.err.println("Non sono riuscito ad aggiungere l'utente "+ nomeUtente+ " " + cognomeUtente+ " alla lista degli iscritti");
					e.printStackTrace();
				}
				request.setAttribute("registrazioneRiuscitaMessage","Registrazione effettuata con successo!");
				request.getServletContext().getRequestDispatcher("/jsp/generali/welcome.jsp").forward(request, response);
			}
			
		}
	}

}

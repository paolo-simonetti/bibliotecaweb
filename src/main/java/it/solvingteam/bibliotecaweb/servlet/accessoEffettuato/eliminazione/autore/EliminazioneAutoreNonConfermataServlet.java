package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.autore;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/autore/EliminazioneAutoreNonConfermataServlet")
public class EliminazioneAutoreNonConfermataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminazioneAutoreNonConfermataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arrayRisultatoRicercaAutore=request.getParameterValues("risultatoRicercaAutore");
		String stringaRisultatoRicercaAutore=null;
		try {
			stringaRisultatoRicercaAutore=WebUtilsFactory.getWebUtilsAutoreInstance()
					.trasformaDaGetAPostFormatoIdRisultatiRicerca(arrayRisultatoRicercaAutore);	
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Eliminazione fallita: errore nel recupero dei risultati della ricerca del libro");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");	
		paginaDiProvenienza=WebUtilsFactory.getWebUtilsAutoreInstance()
				.ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza,request);
		Set<Autore> risultatoRicercaAutore=new TreeSet<>();
		try {
			risultatoRicercaAutore=WebUtilsFactory.getWebUtilsAutoreInstance().
					ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaAutore);			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero degli autori risultanti dalla ricerca precedente");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}
		//Questo attributo mi serve per il campo hidden
		request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
		//Questo attributo mi serve per il forEach sugli Autori risultanti
		request.setAttribute("elencoAutori",risultatoRicercaAutore);
		//Questo attributo mi serve per il passaggio in doGet
		request.setAttribute("risultatoRicercaAutorePerGet",stringaRisultatoRicercaAutore);
		request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

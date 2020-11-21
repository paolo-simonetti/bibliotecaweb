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
import it.solvingteam.bibliotecaweb.utils.WebUtils;

@WebServlet("/accessoEffettuato/eliminazione/autore/EliminazioneNonConfermataServlet")
public class EliminazioneNonConfermataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminazioneNonConfermataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arrayRisultatoRicercaAutore=request.getParameterValues("risultatoRicercaAutore");
		String stringaRisultatoRicercaAutore="[";
		for (String idStringa:arrayRisultatoRicercaAutore) {
			stringaRisultatoRicercaAutore+=idStringa+", ";
		}
		stringaRisultatoRicercaAutore+="]";
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");	
		paginaDiProvenienza=WebUtils.ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza);
		Set<Autore> risultatoRicercaAutore=new TreeSet<>();
		try {
			risultatoRicercaAutore=WebUtils.ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaAutore);			
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

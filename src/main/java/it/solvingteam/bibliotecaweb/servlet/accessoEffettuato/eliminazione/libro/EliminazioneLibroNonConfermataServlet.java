package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.eliminazione.libro;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/eliminazione/libro/EliminazioneLibroNonConfermataServlet")
public class EliminazioneLibroNonConfermataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminazioneLibroNonConfermataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arrayRisultatoRicercaLibro=request.getParameterValues("risultatoRicercaLibroPerGet");
		String stringaRisultatoRicercaLibro=null;
		try {
			stringaRisultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance()
					.trasformaDaGetAPostFormatoIdRisultatiRicerca(arrayRisultatoRicercaLibro);	
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage","Eliminazione fallita: errore nel recupero dei risultati della ricerca del libro");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");	
		paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance().ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza,request);
		Set<Libro> risultatoRicercaLibro=new TreeSet<>();
		String risultatoRicercaLibroPerGet=WebUtilsFactory.getWebUtilsLibroInstance()
				.trasformaDaPostAGetFormatoIdRisultatiRicercaLibro(arrayRisultatoRicercaLibro);
		try {
			risultatoRicercaLibro=WebUtilsFactory.getWebUtilsLibroInstance().ricostruisciTreeSetDaStringaRisultati(stringaRisultatoRicercaLibro);			
			
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore nel recupero dei libri risultanti dalla ricerca precedente");
			request.getServletContext().getRequestDispatcher("/jsp/generali/menu.jsp").forward(request,response);
			return;
		}

		//Questo attributo mi serve per il campo hidden
		request.setAttribute("risultatoRicercaLibro",risultatoRicercaLibro);
		//Questo attributo mi serve per il forEach sui libri risultanti
		request.setAttribute("elencoLibri",risultatoRicercaLibro);
		//Questo attributo mi serve per il passaggio in doGet
		request.setAttribute("risultatoRicercaLibroPerGet",risultatoRicercaLibroPerGet);
		request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

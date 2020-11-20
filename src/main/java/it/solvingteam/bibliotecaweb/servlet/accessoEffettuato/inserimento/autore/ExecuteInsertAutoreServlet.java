package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.inserimento.autore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.model.Genere;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtils;

@WebServlet("/accessoEffettuato/inserimento/autore/ExecuteInsertAutoreServlet")
public class ExecuteInsertAutoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteInsertAutoreServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeAutoreInputParam=request.getParameter("nomeAutore");
		String cognomeAutoreInputParam=request.getParameter("cognomeAutore");
		String dataNascitaStringInputParam=request.getParameter("dataNascita");
		String titoloInputParam=request.getParameter("titolo");
		String genereStringInputParam=request.getParameter("genere");
		String tramaInputParam=request.getParameter("trama");
		String ISBNInputParam=request.getParameter("ISBN");
		//Valido gli input
		if (WebUtils.almenoUnInputVuoto(nomeAutoreInputParam,cognomeAutoreInputParam,dataNascitaStringInputParam,ISBNInputParam)) {
			request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("errorMessage","Inserimento fallito: inserire tutti i campi indicati come obbligatori");
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
			return;
		} 
		LocalDate dataNascita=null;
		try {
			dataNascita=WebUtils.stringToLocalDate(dataNascitaStringInputParam);
			WebUtils.validaISBN(ISBNInputParam);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("errorMessage","Inserimento fallito: dati inseriti non validi");
			request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
			return;
		}
		
		// Mi occupo delle operazioni di business
		boolean riuscitoInserimentoAutore=false;
		boolean riuscitoInserimentoLibro=false;
		Autore nuovoAutore=null;
		try {
			nuovoAutore=new Autore(nomeAutoreInputParam,cognomeAutoreInputParam,dataNascita);
			Libro nuovoLibro=new Libro(titoloInputParam,Genere.conversioneGenere.get(genereStringInputParam),tramaInputParam,ISBNInputParam);
			nuovoAutore.getLibriScritti().add(nuovoLibro);
			nuovoLibro.setAutoreDelLibro(nuovoAutore);
			
			riuscitoInserimentoAutore=MyServiceFactory.getAutoreServiceInstance().inserisciNuovo(nuovoAutore);
			riuscitoInserimentoLibro=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(nuovoLibro);
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
			request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
			request.setAttribute("errorMessage","Inserimento fallito: errore nella registrazione dell'autore o del libro");
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
			return;
		}
		
		if (riuscitoInserimentoAutore&&riuscitoInserimentoLibro) {
			request.setAttribute("successMessage","Operazione effettuata con successo");
			try {
				String risultatoStringaRicercaAutore= request.getParameter("risultatoRicercaAutore");
				TreeSet<Autore> risultatoRicercaAutore=WebUtils.ricostruisciTreeSetDaStringaRisultati(risultatoStringaRicercaAutore);
				risultatoRicercaAutore.add(nuovoAutore);
				request.setAttribute("risultatoRicercaAutore",risultatoRicercaAutore);
				request.setAttribute("idAutoriRisultanti",risultatoRicercaAutore.stream().map(autore->autore.getIdAutore()).collect(Collectors.toSet()));
			} catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("risultatoRicercaAutore",request.getParameter("risultatoRicercaAutore"));
				request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
				request.setAttribute("alertMessage","Attenzione: l'inserimento è andato a buon fine, ma si è verificato un errore nell'elencazione dei risultati");
				request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoAutore.jsp").forward(request,response);
				return;
			}
			request.getServletContext().getRequestDispatcher("/jsp/inserimento/risultatiInserimentoAutore.jsp").forward(request,response);
		}
		
		
		
	}

}

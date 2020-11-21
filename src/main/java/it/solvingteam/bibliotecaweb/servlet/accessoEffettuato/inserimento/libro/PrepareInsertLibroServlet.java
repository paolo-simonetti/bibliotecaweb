package it.solvingteam.bibliotecaweb.servlet.accessoEffettuato.inserimento.libro;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.solvingteam.bibliotecaweb.model.Genere;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.WebUtilsFactory;

@WebServlet("/accessoEffettuato/inserimento/libro/PrepareInsertLibroServlet")
public class PrepareInsertLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareInsertLibroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("risultatoRicercaLibro",request.getParameter("risultatoRicercaLibro"));
		String paginaDiProvenienza=request.getParameter("paginaDiProvenienza");
		paginaDiProvenienza=WebUtilsFactory.getWebUtilsLibroInstance().ricostruisciPathRelativoDellaPaginaDiProvenienza(paginaDiProvenienza,request);
		try {
			// Questo attributo mi serve per il forEach nella pagina jsp di inserimento del libro
			request.setAttribute("listaAutoriPresenti", MyServiceFactory.getAutoreServiceInstance().elenca());	
			// Questo attributo mi serve come campo hidden nella pagina jsp di inserimento del libro
			request.setAttribute("idAutoriPresenti",MyServiceFactory.getAutoreServiceInstance().elenca()
					.stream().map(autore->autore.getIdAutore()).collect(Collectors.toSet()));
		} catch(Exception e) {
			request.setAttribute("errorMessage","Errore nel recupero degli autori presenti");
			request.getServletContext().getRequestDispatcher(paginaDiProvenienza).forward(request,response);
		}
		request.setAttribute("paginaDiProvenienza", request.getParameter("paginaDiProvenienza"));
		request.setAttribute("listaGeneri",Genere.conversioneGenere.keySet());
		request.getServletContext().getRequestDispatcher("/jsp/inserimento/inserimentoLibro.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

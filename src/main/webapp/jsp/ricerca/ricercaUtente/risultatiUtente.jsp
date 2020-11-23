<%@page import="it.solvingteam.bibliotecaweb.model.Utente"%>
<%@page import="it.solvingteam.bibliotecaweb.model.Ruolo"%>
<%@page import="it.solvingteam.bibliotecaweb.model.NomeRuolo"%>
<%@page import="it.solvingteam.bibliotecaweb.model.StatoUtente"%>

<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Risultati della ricerca dell'utente</title>
	
	<!-- style per le pagine diverse dalla index -->
    
</head>
<body>
	
	<main role="main" class="container">
	
		<div class="alert alert-success alert-dismissible fade show ${successMessage==null?'d-none': ''}" role="alert">
		  ${successMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-warning alert-dismissible fade show ${alertMessage==null?'d-none': ''}" role="alert">
		  ${alertMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
		  ${errorMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Lista dei risultati</h5> 
		    </div>
		    	<div class='table-responsive'>
		            <table class='table table-striped ' >
		                <thead>
		                    <tr>
		                        <th>Id Utente</th>
		                        <th>Nome</th>
		                        <th>Username</th>
		                        <th>Data di creazione</th>
								<th>Stato Utente</th>
								<th>Ruolo</th>
		                        <th>Azioni</th>
		                    </tr>
		                </thead>
		                <tbody>
		                  	<c:forEach items="${requestScope.elencoUtenti}" var="item">
		                	  <tr >
		                        <td><c:out value="${item.idUtente}"></c:out></td>
		                        <td><c:out value="${item.nomeUtente}"></c:out></td>
		                        <td><c:out value="${item.cognomeUtente}"></c:out></td>
		                        <td><c:out value="${item.dateCreated}"></c:out></td>
		                        <td><c:out value="${item.statoUtente}"></c:out></td>
		                        <td><c:forEach items="${item.ruoli}" var="ruolo"><c:out value="${ruolo}"/> </c:forEach></td>
		                     
	               	<td>
								  <a class="btn  btn-sm btn-outline-secondary" 
								  	href="${pageContext.request.contextPath}/accessoEffettuato/visualizzazione/utente/VisualizzazioneUtenteServlet?${requestScope.risultatoRicercaUtentePerGet}idUtenteDaVisualizzare=${item.idUtente}&paginaDiProvenienza=risultatiUtente">
									Visualizza utente
								  </a>
								  <c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}">
								    <a class="btn  btn-sm btn-outline-primary ml-2 mr-2" 
								      href="${pageContext.request.contextPath}/accessoEffettuato/aggiornamento/utente/PrepareUpdateUtenteServlet?${requestScope.risultatoRicercaUtentePerGet}idUtenteDaAggiornare=${item.idUtente}&paginaDiProvenienza=risultatiUtente">
								      Aggiorna utente
								    </a>
								    <a class="btn btn-outline-danger btn-sm" 
								      href="${pageContext.request.contextPath}/accessoEffettuato/eliminazione/utente/PrepareDeleteUtenteServlet?${requestScope.risultatoRicercaUtentePerGet}idUtenteDaEliminare=${item.idUtente}&paginaDiProvenienza=risultatiUtente">
								      Elimina utente
								    </a>
								  </c:if>
							  	</td>
		                      </tr>
		                	</c:forEach>		                   
		                </tbody>
		            </table>
		        </div>
		   
			<!-- end card-body -->			   
		    </div>
			
	
	
	
	
	
	
	<!-- end container -->	 
	</main>
</html>
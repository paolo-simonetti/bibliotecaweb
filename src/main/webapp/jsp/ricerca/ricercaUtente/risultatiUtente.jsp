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
	<title>Risultati della ricerca</title>
	
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
		<div class="alert alert-danger alert-dismissible fade show ${dangerMessage==null?'d-none': ''}" role="alert">
		  ${dangerMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Lista dei risultati</h5> 
		    </div>
		    <div class='card-body'>
		    	<c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}"><a class="btn btn-primary " href="PrepareInsertArticoloServlet">Inserisci nuovo</a></c:if>
		    
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
								  <a class="btn  btn-sm btn-outline-secondary" href="GetArticoloDaListaServlet?idArticoloDaVisualizzare=${item.idUtente}">Visualizza utente</a>
									<c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}"><a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="PrepareUpdateArticoloDaListaServlet?idArticoloDaAggiornare=${item.idUtente}">Aggiorna</a></c:if>
									<c:if test="${sessionScope.hasAdminRole eq 'true'}"><a class="btn btn-outline-danger btn-sm" href="PrepareDeleteArticoloDaListaServlet?idArticoloDaEliminare=${item.idUtente}">Elimina</a></c:if>
								</td>
		                      </tr>
		                	</c:forEach>		                   
		                </tbody>
		            </table>
		        </div>
		   
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	
	
	
	
	<!-- end container -->	
	</main>
</html>
<%@page import="it.solvingteam.bibliotecaweb.model.Autore"%>
<%@page import="it.solvingteam.bibliotecaweb.model.Libro"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Dettaglio Utente</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
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
		        <h5>Maggiori informazioni sull'utente richiesto</h5> 
		    </div>
		    <div class='card-body'>
					
		    
		        <div class='table-responsive'>
		            <table class='table table-striped ' >
		                <thead>
		                    <tr>
		                        <th>Campo</th>
		                        <th>Valore</th>
		                    </tr>
		                </thead>
		                <tbody>
		                  <tr>
		                    <td><c:out value="Id:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.idUtente}"/></td>
		                  </tr>
		                      
		                  <tr>
		                    <td><c:out value="Nome:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.nomeUtente}"/></td>
		                  </tr>
		                      
		                  <tr>
		                    <td><c:out value="Cognome:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.cognomeUtente}"/></td>
		                  </tr>
		                  
		                  <tr>
		                    <td><c:out value="Username:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.username}"/></td>
		                  </tr>
		                  
		                  <tr >
		                    <td><c:out value="Data di creazione:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.dateCreated}"/></td>
		                  </tr>

		                  <tr >
		                    <td><c:out value="Stato utente:"/></td>
		                    <td><c:out value="${requestScope.utenteResult.statoUtente}" /></td>
		                  </tr>

		                  <tr >
		                    <td><c:out value="Ruoli assegnati:"/></td>
		                    <c:forEach items="${requestScope.utenteResult.ruoli}" var="ruolo">
		                      <td><c:out value="${ruolo.nomeRuolo}"/></td>
		                    </c:forEach>
		                  </tr>

		              </tbody>
		            </table>
		        </div>
		   
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	
	
	
	
	<!-- end container -->	
	</main>
</html>
<%@page import="it.solvingteam.bibliotecaweb.model.Autore"%>
<%@page import="it.solvingteam.bibliotecaweb.model.Libro"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../generali/header.jsp" />
	<title>Dettaglio Libro</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../generali/navbar.jsp" />
	
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
		        <h5>Maggiori informazioni sul libro richiesto</h5> 
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
		                    <td><c:out value="${requestScope.libroResult.idLibro}"/></td>
		                  </tr>
		                      
		                  <tr>
		                    <td><c:out value="Titolo:"/></td>
		                    <td><c:out value="${requestScope.libroResult.titolo}"/></td>
		                  </tr>
		                      
		                  <tr>
		                    <td><c:out value="Genere:"/></td>
		                    <td><c:out value="${requestScope.libroResult.genere}"/></td>
		                  </tr>
		                  
		                  <tr>
		                    <td><c:out value="Trama:"/></td>
		                    <td><c:out value="${requestScope.libroResult.trama}"/></td>
		                  </tr>
		                  
		                  <tr >
		                    <td><c:out value="ISBN:"/></td>
		                    <td><c:out value="${requestScope.libroResult.ISBN}"/></td>
		                  </tr>

		                  <tr >
		                    <td><c:out value="Nome e cognome dell'autore:"/></td>
		                    <td><c:out value="${requestScope.libroResult.autoreDelLibro.nomeAutore} ${requestScope.libroResult.autoreDelLibro.cognomeAutore}"/></td>
		                  </tr>

		                  <tr >
		                    <td><c:out value="Data di nascita dell'autore:"/></td>
		                    <td><c:out value="${requestScope.libroResult.autoreDelLibro.dataNascita}"/></td>
		                  </tr>

		              </tbody>
		            </table>
		        </div>
		   
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	
	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../generali/footer.jsp" />
</html>
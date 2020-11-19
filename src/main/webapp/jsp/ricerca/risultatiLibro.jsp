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
	<title>Risultati della ricerca</title>
	
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
		                        <th>Id Libro</th>
		                        <th>Titolo</th>
		                        <th>Genere</th>
		                        <th>Trama</th>
		                        <th>ISBN</th>
		                        <th>Azioni</th>
		                    </tr>
		                </thead>
		                <tbody>
		                  	<c:forEach items="${requestScope.elencoLibri}" var="item">
		                	  <tr >
		                        <td><c:out value="${item.idLibro}"></c:out></td>
		                        <td><c:out value="${item.titolo}"></c:out></td>
		                        <td><c:out value="${item.genere}"></c:out></td>
		                        <td><c:out value="${item.trama}"></c:out></td>
		                        <td><c:out value="${item.ISBN}"></c:out></td>
		                     
		                         <td>
									<a class="btn  btn-sm btn-outline-secondary" href="GetArticoloDaListaServlet?idArticoloDaVisualizzare=${item.idLibro}">Visualizza Libro</a>
									<c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}"><a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="PrepareUpdateArticoloDaListaServlet?idArticoloDaAggiornare=${item.idLibro}">Aggiorna</a></c:if>
									<c:if test="${sessionScope.hasAdminRole eq 'true'}"><a class="btn btn-outline-danger btn-sm" href="PrepareDeleteArticoloDaListaServlet?idArticoloDaEliminare=${item.idLibro}">Elimina</a></c:if>
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
	<jsp:include page="../generali/footer.jsp" />
</html>
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
	<title>Risultati inserimento libro</title>
	
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
		    	<c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}">
				<form method="post" action="${pageContext.request.contextPath}/accessoEffettuato/inserimento/libro/PrepareInsertLibroServlet?paginaDiProvenienza=risultatiInserimentoLibro" novalidate="novalidate">
		    	  <div class="form-group col-md-6">
					<label></label>
					<input type="hidden" name="risultatoRicercaLibro" id="risultatoRicercaLibro" value="${requestScope.risultatoRicercaLibro}" class="form-control">
				  </div>
				<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Inserisci nuovo</button>
				</form>
		    	</c:if>

		    
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
									<a class="btn  btn-sm btn-outline-secondary" 
									  href="${pageContext.request.contextPath}/accessoEffettuato/visualizzazione/libro/VisualizzazioneLibroServlet?${requestScope.risultatoRicercaLibroPerGet}idLibroDaVisualizzare=${item.idLibro}&paginaDiProvenienza=risultatiInserimentoLibro">Visualizza libro
									</a>
									<c:if test="${sessionScope.hasAdminRole eq 'true' or sessionScope.hasClassicRole eq 'true'}">
									  <a class="btn  btn-sm btn-outline-primary ml-2 mr-2" 
									    href="${pageContext.request.contextPath}/accessoEffettuato/aggiornamento/libro/PrepareUpdateLibroServlet?${requestScope.risultatoRicercaLibroPerGet}idLibroDaAggiornare=${item.idLibro}&paginaDiProvenienza=risultatiInserimentoLibro">Aggiorna libro
									  </a>
									  
									  <a class="btn btn-outline-danger btn-sm" 
									    href="${pageContext.request.contextPath}/accessoEffettuato/eliminazione/libro/PrepareDeleteLibroServlet?${requestScope.risultatoRicercaLibroPerGet}idLibroDaEliminare=${item.idLibro}&paginaDiProvenienza=risultatiInserimentoLibro">Elimina libro
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
		</div>	
	
	
	
	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../generali/footer.jsp" />
</html>
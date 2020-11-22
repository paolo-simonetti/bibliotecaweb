<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="../generali/header.jsp" />
	<title>Aggiornamento libro</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="../generali/navbar.jsp" />
	
	<main role="main" class="container">
	
		
		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': 
		''}" role="alert">
		  ${errorMessage}
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
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Aggiorna i dati del libro </h5> 
		    </div>
		    <div class='card-body'>
		    <h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori. </h6>
		    
		    		<form method="post" 
		    		  action="${pageContext.request.contextPath}/accessoEffettuato/aggiornamento/libro/ExecuteUpdateLibroServlet?" 
		    		  novalidate="novalidate">
								 	 
				 	    <div class="form-group col-md-6">
						  <label></label>
						  <input type="hidden" name="paginaDiProvenienza" id="paginaDiProvenienza" 
						    value="${requestScope.paginaDiProvenienza}" class="form-control">
						  <input type="hidden" name="risultatoRicercaLibro" id="risultatoRicercaLibro" 
						    value="${requestScope.risultatoRicercaLibro}" class="form-control">
						  <input type="hidden" name="idAutoriPresenti" id="idAutoriPresenti" 
						    value="${requestScope.idAutoriPresenti}" class="form-control">
						  <input type="hidden" name="listaGeneri" id="listaGeneri" 
						    value="${requestScope.listaGeneri}" class="form-control">
						  <input type="hidden" name="idLibroDaAggiornare" id="idLibroDaAggiornare" 
						    value="${requestScope.idLibroDaAggiornare}" class="form-control">


				  		</div>

						
						<div class="form-row">
							<div class="form-group">
  					      	  <label for="selectAutore">Autore: <span class="text-danger">*</span></label>
  					      	  <select class="form-control" id="selectAutore" name="selectAutore">
     					        <c:forEach items="${requestScope.listaAutoriPresenti}" var="autore"> 
    					    	  <option value="${autore.idAutore}"
    					    	    <c:if test="${requestScope.libroDaAggiornare.autoreDelLibro.idAutore eq autore.idAutore}" > selected </c:if>
    					    	  >
    					    	    ${autore.nomeAutore} ${autore.cognomeAutore} nato il ${autore.dataNascita}
    					    	  </option>
    					    	</c:forEach>
  					      	  </select>
					    	</div> 							
							
						</div>
						
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Titolo del libro</label>
								<input type="text" class="form-control" name="titolo" id="titolo" value="${libroDaAggiornare.titolo}">
							</div>
							<div class="form-group">
  					      	  <label for="selectGenere">Genere:</label>
  					      	  <select class="form-control" id="selectGenere" name="genere">
     					        <c:forEach items="${requestScope.listaGeneri}" var="genereDisponibile">
    					    	  <option value="${genereDisponibile.stringaGenere}" 
    					    	    <c:if test="${requestScope.libroDaAggiornare.genere eq genereDisponibile}" > selected </c:if>
    					    	  >
    					    	    ${genereDisponibile.stringaGenere}
    					    	  </option>
    					    	</c:forEach>
  					      	  </select>
					    	</div> 							
							
						</div>
						
						<div class="form-row">	
						  <div class="form-group col-md-3">
						    <label>Trama</label>
							<input type="text" class="form-control" name="trama" id="trama" 
							  placeholder="Di cosa parla il libro?" value="${requestScope.libroDaAggiornare.trama}"
							>
						  </div>		
						</div>	
					
						<div class="form-row">
						  <div class="form-group col-md-3">
						    <label>ISBN<span class="text-danger">*</span></label> 
							<input type="text" class="form-control" name="ISBN" id="ISBN" value="${requestScope.libroDaAggiornare.ISBN}">
						  </div>		
						</div>
												
							
						<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
					

					</form>

		    
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="../generali/footer.jsp" />
	
</body>
</html>
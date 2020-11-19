<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="../generali/header.jsp" />
	<title>Ricerca avanzata di libro</title>
	
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
		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Inserisci campi di ricerca</h5> 
		    </div>
		    <div class='card-body'>

					<form method="post" action="${pageContext.request.contextPath}/accessoEffettuato/ricerca/libri/ExecuteRicercaLibriServlet" novalidate="novalidate">
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Titolo</label>
								<input type="text" name="titolo" id="titolo" class="form-control">
							</div>
							
							<div class="form-group">
  					      	  <label for="selectGenere">Genere:</label>
  					      	  <select class="form-control" id="selectGenere" name="genere">
     					        <c:forEach items="${requestScope.listaGeneri}" var="genereDisponibile">
    					    	  <option value="${genereDisponibile}">
    					    	    ${genereDisponibile}
    					    	  </option>
    					    	</c:forEach>
  					      	  </select>
					    	</div> 							

						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Trama</label>
								<input type="text" class="form-control" name="trama" id="trama" placeholder="Scrivi brevemente cosa ricordi">
							</div>		
						</div>	
					
						<div class="form-row">
							<div class="form-group col-md-3">
								<label>ISBN</label> 
								<input type="text" class="form-control" name="ISBN" id="ISBN">
							</div>		
						</div>
	
						<div class="form-row">
							<div class="form-group col-md-3">
								<label>Nome dell'autore</label> 
								<input type="text" class="form-control" name="nomeAutore" id="nomeAutore">
							</div>		
							<div class="form-group col-md-3">
								<label>Cognome dell'autore</label> 
								<input type="text" class="form-control" name="cognomeAutore" id="cognomeAutore" >
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
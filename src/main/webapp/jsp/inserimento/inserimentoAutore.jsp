<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="../generali/header.jsp" />
	<title>Inserimento nuovo autore</title>
	
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
		        <h5>Inserisci il nuovo autore e il suo libro </h5> 
		    </div>
		    <div class='card-body'>
		    <h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		    		<form method="post" action="${pageContext.request.contextPath}/accessoEffettuato/inserimento/autore/ExecuteInsertAutoreServlet" novalidate="novalidate">
				 	    <div class="form-group col-md-6">
						  <label></label>
						  <input type="hidden" name="risultatoRicercaAutore" id="risultatoRicercaAutore" value="${requestScope.risultatoRicercaAutore}" class="form-control">
				  		</div>
						
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Nome dell'autore<span class="text-danger">*</span></label>
								<input type="text" name="nomeAutore" id="nomeAutore" class="form-control" required>
							</div>
							
							<div class="form-group col-md-6">
								<label>Cognome dell'autore<span class="text-danger">*</span></label>
								<input type="text" name="cognomeAutore" id="cognomeAutore" class="form-control" required>
							</div>
						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Data di nascita<span class="text-danger">*</span></label>
								<input type="date" class="form-control" name="dataNascita" id="dataNascita" required>
							</div>		

						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Titolo del suo libro</label>
								<input type="text" class="form-control" name="titolo" id="titolo">
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
							<input type="text" class="form-control" name="trama" id="trama" placeholder="Di cosa parla il libro?">
						  </div>		
						</div>	
					
						<div class="form-row">
						  <div class="form-group col-md-3">
						    <label>ISBN<span class="text-danger">*</span></label> 
							<input type="text" class="form-control" name="ISBN" id="ISBN">
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
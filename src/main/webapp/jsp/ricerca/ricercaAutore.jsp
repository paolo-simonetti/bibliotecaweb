<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="../generali/header.jsp" />
	<title>Ricerca avanzata di autore</title>
	
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

					<form method="post" action="${pageContext.request.contextPath}/accessoEffettuato/ricerca/autori/ExecuteRicercaAutoriServlet" novalidate="novalidate">
					
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Nome dell'autore</label>
								<input type="text" name="nomeAutore" id="nomeAutore" class="form-control">
							</div>
							
							<div class="form-group col-md-6">
								<label>Cognome dell'autore</label>
								<input type="text" name="cognomeAutore" id="cognomeAutore" class="form-control">
							</div>
						</div>
						
						<div class="form-row">	
							<div class="form-group col-md-3">
								<label>Data di nascita</label>
								<input type="date" class="form-control" name="dataNascita" id="dataNascita">
							</div>		
							<div class="form-group col-md-3">
								<label>Libro</label>
								<input type="text" class="form-control" name="libro" id="libro" placeholder="Inserisci il titolo di un suo libro">
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
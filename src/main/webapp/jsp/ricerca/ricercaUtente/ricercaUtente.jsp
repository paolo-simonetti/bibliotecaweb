<!doctype html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="it">
<head>
	<jsp:include page="${pageContext.request.contextPath}/jsp/generali/header.jsp" />
	<title>Ricerca avanzata di utente</title>
	
	<!-- style per le pagine diverse dalla index -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    
</head>
<body>
	<jsp:include page="${pageContext.request.contextPath}/jsp/generali/navbar.jsp" />
	
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
								<label>Nome dell'utente</label>
								<input type="text" name="nomeUtente" id="nomeUtente" class="form-control">
							</div>
							<div class="form-group col-md-3">
								<label>Cognome dell'utente</label>
								<input type="text" class="form-control" name="cognomeUtente" id="cognomeUtente">
							</div>		
							
						</div>
						
						<div class="form-row">
							<div class="form-group col-md-6">
								<label>Username</label>
								<input type="text" name="username" id="username" class="form-control">
							</div>	
							<div class="form-group col-md-3">
								<label>Data di creazione dell'account</label> 
								<input type="date" class="form-control" name="dateCreated" id="dateCreated">
							</div>		
							<div class="form-group col-md-6">
								<label>Ruolo nel sito</label>
								<input type="text" name="ruoli" id="ruoli" class="form-control">
							</div>
						</div>
	
							
						<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
					

					</form>

		    
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
	
	
	<!-- end container -->	
	</main>
	<jsp:include page="${pageContext.request.contextPath}/jsp/generali/footer.jsp" />
	
</body>
</html>
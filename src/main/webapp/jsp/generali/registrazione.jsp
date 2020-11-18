<!doctype html>
<html lang="it">
  <head>
    
    <jsp:include page="header.jsp" />
    
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    <style type="text/css">
    	body {
		  padding-top: 3.5rem;
		}	
    </style>

<title>Registrazione</title>
</head>
<body>

	<main role="main" class="container">
	
		<div class="alert alert-warning alert-dismissible fade show ${registrazioneGiaEffettuataMessage==null?'d-none': ''}" role="alert">
		  ${registrazioneGiaEffettuataMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>

		
		<div class='card'>
		    <div class='card-header'>
		        <h5>Registrati</h5> 
		    </div>
		    <div class='card-body'>
				<form method="post" action="${pageContext.request.contextPath}/registrazioneEAccesso/ExecuteRegistrazioneServlet" novalidate="novalidate">
					
					<div class="form-row">
						<div class="form-group col-md-6">
							<label>Nome </label>
							<input type="text" name="nome" id="nome" class="form-control" placeholder="Inserisci il tuo nome" required>
						</div>

						<div class="form-group col-md-6">
							<label>Cognome </label>
							<input type="text" name="cognome" id="cognome" class="form-control" placeholder="Inserisci il tuo cognome" required>
						</div>

	
						<div class="form-group col-md-6">
							<label>Username </label>
							<input type="text" name="username" id="username" class="form-control" placeholder="Inserire lo username" required>
						</div>
							
						<div class="form-group col-md-6">
							<label>Password</label>
							<input type="password" name="password" id="password" class="form-control" placeholder="Inserire la password" required>
						</div>
					</div>
											
					<button type="submit" name="signup" value="signup" id="signup" class="btn btn-primary">Registrati</button>
						
				</form>
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
		
	</main>

	<jsp:include page="./footer.jsp" />
</body>
</html>
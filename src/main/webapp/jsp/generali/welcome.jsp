<!doctype html>
<html lang="it">
  <head>
    
    <jsp:include page="header.jsp" />
    
    <!-- Custom styles for this template -->
    <link href="./assets/css/global.css" rel="stylesheet">
    <style type="text/css">
    	body {
		  padding-top: 3.5rem;
		}	
    </style>
    
<title>Login</title>
</head>
<body>

	<main role="main" class="container">
	
		<div class="alert alert-info alert-dismissible fade show ${logoutMessage==null?'d-none': ''}" role="alert">
		  ${logoutMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-warning alert-dismissible fade show ${credenzialiErrateMessage==null?'d-none': ''}" role="alert">
		  ${credenzialiErrateMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-warning alert-dismissible fade show ${registrazioneGiaEffettuataMessage==null?'d-none': ''}" role="alert">
		  ${registrazioneGiaEffettuataMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-success alert-dismissible fade show ${registrazioneRiuscitaMessage==null?'d-none': ''}" role="alert">
		  ${registrazioneRiuscitaMessage}
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
		<div class="alert alert-danger alert-dismissible fade show ${permessiMancantiMessage==null?'d-none': ''}" role="alert">
		  ${permessiMancantiMessage}
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
		        <h5>Effettua il login</h5> 
		    </div>
		    <div class='card-body'>
				<form method="post" action="${pageContext.request.contextPath}/registrazioneEAccesso/LoginServlet" novalidate="novalidate">
					
					<div class="form-row">
						<div class="form-group col-md-6">
							<label>Username </label>
							<input type="text" name="username" id="username" class="form-control" placeholder="Inserire lo username" required>
						</div>
						
						<div class="form-group col-md-6">
							<label>Password</label>
							<input type="password" name="password" id="password" class="form-control" placeholder="Inserire la password" required>
						</div>
					</div>
											
					<button type="submit" name="login" value="login" id="login" class="btn btn-primary">Accedi</button>
						
				</form>
			<p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/registrazioneEAccesso/PrepareRegistrazioneServlet" role="button">Non sei ancora registrato? &raquo;</a></p>				
		    
			<!-- end card-body -->			   
		    </div>
		</div>	
		
	</main>
	<jsp:include page="footer.jsp" />
</body>
</html>
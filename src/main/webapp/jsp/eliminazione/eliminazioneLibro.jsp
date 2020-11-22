<!doctype html>
<html lang="it">
  <head>
    
    <jsp:include page="../generali/header.jsp" />
    
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/assets/css/global.css" rel="stylesheet">
    <style type="text/css">
    	body {
		  padding-top: 3.5rem;
		}	
    </style>
    
    <title>Conferma eliminazione libro</title>
  </head>
  <body>
  
	<jsp:include page="../generali/navbar.jsp"></jsp:include>
  
  
	<main role="main">

	  <!-- Main jumbotron for a primary marketing message or call to action -->
	  <div class="jumbotron" >
	    <div class="container">
	      <h1 class="display-3">Conferma eliminazione libro </h1>
	      <p>Pazzo scatenato, vuoi davvero eliminare questo libro?</p>
	      <p><a class="btn btn-primary btn-lg" 
	        href="${pageContext.request.contextPath}/accessoEffettuato/eliminazione/libro/ExecuteDeleteLibroServlet?${requestScope.risultatoRicercaLibro}idLibroDaEliminare=${requestScope.idLibroDaEliminare}&paginaDiProvenienza=${requestScope.paginaDiProvenienza}" role="button">
	            Sei un folle &raquo;
	      </a></p>
	      <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/accessoEffettuato/eliminazione/libro/EliminazioneLibroNonConfermataServlet?${requestScope.risultatoRicercaLibro}paginaDiProvenienza=${requestScope.paginaDiProvenienza}" role="button">
	          Bravo, torna alla lista degli articoli &raquo;
	      </a></p>
	    </div>
	  </div>


	</main>
	
	<jsp:include page="../generali/footer.jsp" />
  </body>
</html>
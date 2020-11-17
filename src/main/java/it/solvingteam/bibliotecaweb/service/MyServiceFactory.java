package it.solvingteam.bibliotecaweb.service;

import it.solvingteam.bibliotecaweb.dao.MyDAOFactory;
import it.solvingteam.bibliotecaweb.service.autore.AutoreService;
import it.solvingteam.bibliotecaweb.service.autore.AutoreServiceImpl;
import it.solvingteam.bibliotecaweb.service.libro.LibroService;
import it.solvingteam.bibliotecaweb.service.libro.LibroServiceImpl;
import it.solvingteam.bibliotecaweb.service.ruolo.RuoloService;
import it.solvingteam.bibliotecaweb.service.ruolo.RuoloServiceImpl;
import it.solvingteam.bibliotecaweb.service.utente.UtenteService;
import it.solvingteam.bibliotecaweb.service.utente.UtenteServiceImpl;

public class MyServiceFactory {

	private static AutoreService AUTORE_SERVICE_INSTANCE;	
	private static LibroService LIBRO_SERVICE_INSTANCE;	
	private static RuoloService RUOLO_SERVICE_INSTANCE;	
	private static UtenteService UTENTE_SERVICE_INSTANCE;
	
	public static AutoreService getAutoreServiceInstance() {
		if (AUTORE_SERVICE_INSTANCE == null)
			AUTORE_SERVICE_INSTANCE = new AutoreServiceImpl();

		AUTORE_SERVICE_INSTANCE.setAutoreDAO(MyDAOFactory.getAutoreDAOInstance());
		return AUTORE_SERVICE_INSTANCE;
	}
	
	public static LibroService getLibroServiceInstance() {
		if (LIBRO_SERVICE_INSTANCE == null)
			LIBRO_SERVICE_INSTANCE = new LibroServiceImpl();

		LIBRO_SERVICE_INSTANCE.setLibroDAO(MyDAOFactory.getLibroDAOInstance());
		return LIBRO_SERVICE_INSTANCE;
	}	

	public static RuoloService getRuoloServiceInstance() {
		if (RUOLO_SERVICE_INSTANCE == null)
			RUOLO_SERVICE_INSTANCE = new RuoloServiceImpl();

		RUOLO_SERVICE_INSTANCE.setRuoloDAO(MyDAOFactory.getRuoloDAOInstance());
		return RUOLO_SERVICE_INSTANCE;
	}

	public static UtenteService getUtenteServiceInstance() {
		if (UTENTE_SERVICE_INSTANCE == null)
			UTENTE_SERVICE_INSTANCE = new UtenteServiceImpl();

		UTENTE_SERVICE_INSTANCE.setUtenteDAO(MyDAOFactory.getUtenteDAOInstance());
		return UTENTE_SERVICE_INSTANCE;
	}

}

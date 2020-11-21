package it.solvingteam.bibliotecaweb.utils;

public class WebUtilsFactory {
	
	private static WebUtilsAutore WEB_UTILS_AUTORE_INSTANCE=null;
	private static WebUtilsLibro WEB_UTILS_LIBRO_INSTANCE=null;
	private static WebUtilsUtente WEB_UTILS_UTENTE_INSTANCE=null;
	
	public static WebUtilsAutore getWebUtilsAutoreInstance() {
		if (WEB_UTILS_AUTORE_INSTANCE == null)
			WEB_UTILS_AUTORE_INSTANCE = new WebUtilsAutore();
		return WEB_UTILS_AUTORE_INSTANCE;
	}

	public static WebUtilsLibro getWebUtilsLibroInstance() {
		if (WEB_UTILS_LIBRO_INSTANCE == null)
			WEB_UTILS_LIBRO_INSTANCE = new WebUtilsLibro();
		return WEB_UTILS_LIBRO_INSTANCE;
	}

	public static WebUtilsUtente getWebUtilsUtenteInstance() {
		if (WEB_UTILS_UTENTE_INSTANCE == null)
			WEB_UTILS_UTENTE_INSTANCE = new WebUtilsUtente();
		return WEB_UTILS_UTENTE_INSTANCE;
	}

	
	
}

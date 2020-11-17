package it.solvingteam.bibliotecaweb.service.utente;

import it.solvingteam.bibliotecaweb.dao.utente.UtenteDAO;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface UtenteService extends IBaseService<Utente> {
	public void setUtenteDAO(UtenteDAO utenteDAO);
}

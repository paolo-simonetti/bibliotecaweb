package it.solvingteam.bibliotecaweb.service.utente;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.utente.UtenteDAO;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface UtenteService extends IBaseService<Utente> {
	public void setUtenteDAO(UtenteDAO utenteDAO);
	public Set<Utente> trovaTuttiTramiteAttributiERuolo (TreeMap<String,TreeSet<String>> input) throws Exception;
	public Utente caricaSingoloElementoConRuolo(Long idUtente) throws Exception;
}

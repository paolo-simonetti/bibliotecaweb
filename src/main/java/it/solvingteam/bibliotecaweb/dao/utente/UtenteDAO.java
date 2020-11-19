package it.solvingteam.bibliotecaweb.dao.utente;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.IBaseDAO;
import it.solvingteam.bibliotecaweb.model.Utente;

public interface UtenteDAO extends IBaseDAO<Utente> {
	public Set<Utente> findAllByExampleAndRuolo(TreeMap<String,TreeSet<String>> input) throws Exception;
}

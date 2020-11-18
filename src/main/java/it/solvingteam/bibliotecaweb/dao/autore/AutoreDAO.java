package it.solvingteam.bibliotecaweb.dao.autore;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.IBaseDAO;
import it.solvingteam.bibliotecaweb.model.Autore;

public interface AutoreDAO extends IBaseDAO<Autore> {
	public Set<Autore> findAllByExampleAndLibro(TreeMap<String,TreeSet<String>> input) throws Exception;
}

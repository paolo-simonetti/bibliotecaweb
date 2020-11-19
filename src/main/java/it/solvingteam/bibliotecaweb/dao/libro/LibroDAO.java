package it.solvingteam.bibliotecaweb.dao.libro;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.IBaseDAO;
import it.solvingteam.bibliotecaweb.model.Libro;

public interface LibroDAO extends IBaseDAO<Libro> {
	public Set<Libro> findAllByExampleAndAutore(TreeMap<String,TreeSet<String>> input) throws Exception;
}

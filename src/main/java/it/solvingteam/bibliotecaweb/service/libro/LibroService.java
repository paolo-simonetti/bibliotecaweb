package it.solvingteam.bibliotecaweb.service.libro;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.libro.LibroDAO;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface LibroService extends IBaseService<Libro> {
	public void setLibroDAO(LibroDAO libroDAO);
	
	public Set<Libro> trovaTuttiTramiteAttributiEAutore(TreeMap<String,TreeSet<String>> input) throws Exception;
	public Libro caricaSingoloElementoConAutore(Long idLibro) throws Exception;
	
}

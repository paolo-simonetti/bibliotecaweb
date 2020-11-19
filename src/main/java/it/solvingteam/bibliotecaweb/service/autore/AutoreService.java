package it.solvingteam.bibliotecaweb.service.autore;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.dao.autore.AutoreDAO;
import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface AutoreService extends IBaseService<Autore> {
	
	public Set<Autore> trovaTuttiTramiteAttributiELibro(TreeMap<String,TreeSet<String>> input) throws Exception;
	public Autore caricaSingoloElementoConLibri(Long idInput) throws Exception;
	
	
	public void setAutoreDAO(AutoreDAO autoreDAO);
}

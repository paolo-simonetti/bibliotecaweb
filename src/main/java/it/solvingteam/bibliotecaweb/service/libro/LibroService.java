package it.solvingteam.bibliotecaweb.service.libro;

import it.solvingteam.bibliotecaweb.dao.libro.LibroDAO;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface LibroService extends IBaseService<Libro> {
	public void setLibroDAO(LibroDAO libroDAO);
}

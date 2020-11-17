package it.solvingteam.bibliotecaweb.service.autore;

import it.solvingteam.bibliotecaweb.dao.autore.AutoreDAO;
import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface AutoreService extends IBaseService<Autore> {
	public void setAutoreDAO(AutoreDAO autoreDAO);
}

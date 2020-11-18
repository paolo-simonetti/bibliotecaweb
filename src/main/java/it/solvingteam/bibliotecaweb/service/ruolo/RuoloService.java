package it.solvingteam.bibliotecaweb.service.ruolo;

import java.util.Set;

import it.solvingteam.bibliotecaweb.dao.ruolo.RuoloDAO;
import it.solvingteam.bibliotecaweb.model.Ruolo;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.IBaseService;

public interface RuoloService extends IBaseService<Ruolo> {
	
	public void setRuoloDAO(RuoloDAO ruoloDAO);
}

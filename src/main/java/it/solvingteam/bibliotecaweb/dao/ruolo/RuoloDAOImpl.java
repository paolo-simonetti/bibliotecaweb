package it.solvingteam.bibliotecaweb.dao.ruolo;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.model.Ruolo;

public class RuoloDAOImpl implements RuoloDAO {

	@Override
	public Set<Ruolo> list() throws Exception {
		try {
			return entityManager.createQuery("from Ruolo",Ruolo.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione dei ruoli");
		}		
	}

	@Override
	public Ruolo get(Long idRuolo) throws Exception {
		try {
			return entityManager.find(Ruolo.class, idRuolo);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero del ruolo con idRuolo="+idRuolo);
		}
	}

	@Override
	public boolean update(Ruolo ruoloInstance) throws Exception {
		try {
			ruoloInstance = entityManager.merge(ruoloInstance);			
	 		return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento del ruolo "+ruoloInstance);
		}
	}

	@Override
	public boolean insert(Ruolo ruoloInstance) throws Exception {
		try {
			entityManager.persist(ruoloInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento del ruolo "+ruoloInstance);
		}		
	}

	@Override
	public boolean delete(Ruolo ruoloInstance) throws Exception {
		try {
			entityManager.remove(entityManager.merge(ruoloInstance));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione del ruolo "+ruoloInstance);
		}
	}

	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
		
	}

}

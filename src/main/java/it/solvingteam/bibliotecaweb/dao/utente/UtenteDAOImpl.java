package it.solvingteam.bibliotecaweb.dao.utente;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.model.Utente;

public class UtenteDAOImpl implements UtenteDAO {

	@Override
	public Set<Utente> list() throws Exception {
		try {
			return entityManager.createQuery("from Utente",Utente.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione degli utenti");
		}		
	}

	@Override
	public Utente get(Long idUtente) throws Exception {
		try {
			return entityManager.find(Utente.class, idUtente);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero dell'utente con id="+idUtente);
		}	
	}

	@Override
	public boolean update(Utente utenteInstance) throws Exception {
		try {
			utenteInstance = entityManager.merge(utenteInstance);			
	 		return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento dell'utente "+utenteInstance);
		}
	}

	@Override
	public boolean insert(Utente utenteInstance) throws Exception {
		try {
			entityManager.persist(utenteInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento dell'utente "+utenteInstance);
		}		
	}

	@Override
	public boolean delete(Utente utenteInstance) throws Exception {
		try {
			entityManager.remove(entityManager.merge(utenteInstance));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione dell'utente "+utenteInstance);
		}
	}

	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
	}

}

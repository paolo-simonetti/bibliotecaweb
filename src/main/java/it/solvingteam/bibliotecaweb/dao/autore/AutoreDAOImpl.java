package it.solvingteam.bibliotecaweb.dao.autore;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.model.Autore;

public class AutoreDAOImpl implements AutoreDAO {

	@Override
	public Set<Autore> list() throws Exception {
		try {
			return entityManager.createQuery("from Autore",Autore.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione degli autori");
		}		
	}

	@Override
	public Autore get(Long idAutore) throws Exception {
		try {
			return entityManager.find(Autore.class, idAutore);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero dell'autore con id="+idAutore);
		}
	}

	@Override
	public boolean update(Autore autoreInstance) throws Exception {
		try {
			autoreInstance = entityManager.merge(autoreInstance);			
	 		return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento dell'autore "+autoreInstance);
		}
	}

	@Override
	public boolean insert(Autore autoreInstance) throws Exception {
		try {
			entityManager.persist(autoreInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento dell'autore "+autoreInstance);
		}		
	}

	@Override
	public boolean delete(Autore autoreInstance) throws Exception {
		try {
			entityManager.remove(entityManager.merge(autoreInstance));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione dell'autore "+autoreInstance);
		}
	}
	
	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
		
	}

}

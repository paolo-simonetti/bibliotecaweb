package it.solvingteam.bibliotecaweb.dao.libro;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.model.Libro;

public class LibroDAOImpl implements LibroDAO {

	@Override
	public Set<Libro> list() throws Exception {
		try {
			return entityManager.createQuery("from Libro",Libro.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione dei libri");
		}		
	}

	@Override
	public Libro get(Long idLibro) throws Exception {
		try {
			return entityManager.find(Libro.class, idLibro);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero del libro con id="+idLibro);
		}
	}

	@Override
	public boolean update(Libro libroInstance) throws Exception {
		try {
			libroInstance = entityManager.merge(libroInstance);			
	 		return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento del libro "+libroInstance);
		}
	}

	@Override
	public boolean insert(Libro libroInstance) throws Exception {
		try {
			entityManager.persist(libroInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento del libro "+libroInstance);
		}		
	}

	@Override
	public boolean delete(Libro libroInstance) throws Exception {
		try {
			entityManager.remove(entityManager.merge(libroInstance));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione dell'autore "+libroInstance);
		}
	}

	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
		
	}

}

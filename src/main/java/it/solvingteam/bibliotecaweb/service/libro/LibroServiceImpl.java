package it.solvingteam.bibliotecaweb.service.libro;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.dao.EntityManagerUtil;
import it.solvingteam.bibliotecaweb.dao.libro.LibroDAO;
import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;

public class LibroServiceImpl implements LibroService {

	@Override
	public Set<Libro> elenca() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			libroDAO.setEntityManager(entityManager);
			return libroDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Libro caricaSingoloElemento(Long idLibro) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (idLibro==null||idLibro<=0) {
			throw new Exception("Errore nell'id del libro in input");
		}

		try {
			libroDAO.setEntityManager(entityManager);

			return libroDAO.get(idLibro);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean aggiorna(Libro libroInstance) throws Exception {		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();		
		try {
			entityManager.getTransaction().begin();
			libroDAO.setEntityManager(entityManager);
			if (libroInstance==null) {
				throw new Exception("Errore nel libro in input");
			} else if(libroInstance.getAutoreDelLibro()==null) {
				throw new Exception("Aggiornamento fallito: il libro in input non ha autore");
			} else if(libroInstance.getIdLibro()==null) {
				throw new Exception("Aggiornamento fallito: il libro in input non ha id");
			} 

			
			// Controllo che il libro sia presente
			Set<Long> idLibriPresenti= libroDAO.list().stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			if (!idLibriPresenti.contains(libroInstance.getIdLibro())) {
				throw new Exception("Il libro in input non è presente sul db");
			}
			// Blocco i tentativi di aggiornamento che replichino dati di libri già presenti
			Set<Libro> libriPresenti=libroDAO.list();
			if (libriPresenti.size()!=0) {
				for (Libro l:libriPresenti) {
					if (l.equals(libroInstance)&&l.getIdLibro()!=libroInstance.getIdLibro()) {
						throw new Exception("Aggiornamento fallito: è già presente nel db");
					}
				}
			}

			boolean esito=libroDAO.update(libroInstance);
			entityManager.getTransaction().commit();
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean inserisciNuovo(Libro libroInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
				
		try {
			entityManager.getTransaction().begin();
			libroDAO.setEntityManager(entityManager);

			if (libroInstance==null) {
				throw new Exception("Errore nel libro in input");
			} else if(libroInstance.getAutoreDelLibro()==null) {
				throw new Exception("Inserimento fallito: il libro in input non ha autore");
			}

			// Impedisco l'inserimento di doppioni
			Set<Libro> libriPresenti=libroDAO.list();
			if (libriPresenti.size()!=0) {
				for (Libro l:libriPresenti) {
					if (l.equals(libroInstance)) {
						throw new Exception("Inserimento fallito: è già presente nel db");
					}
				}
			}
			
			boolean esito=libroDAO.insert(libroInstance);
			entityManager.getTransaction().commit();
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean rimuovi(Libro libroInstance) throws Exception {
		if (libroInstance==null||libroInstance.getIdLibro()==null) {
			throw new Exception("Rimozione fallita: problema nel libro in input");
		} 
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();		
		
		try {
			entityManager.getTransaction().begin();
			libroDAO.setEntityManager(entityManager);
			// Controllo che il libro sia presente sul db
			Set<Long> idLibriPresenti= libroDAO.list().stream().map(libro->libro.getIdLibro()).collect(Collectors.toSet());
			if (!idLibriPresenti.contains(libroInstance.getIdLibro())) {
				throw new Exception("Il libro in input non è presente sul db");
			}
			// Controllo che libroInstance non sia l'ultimo dei libri che la biblioteca ha di quell'autore
			Autore autoreDelLibro=libroInstance.getAutoreDelLibro();
			Autore autoreDelLibroConLibriScritti=MyServiceFactory.getAutoreServiceInstance()
					.caricaSingoloElementoConLibri(autoreDelLibro.getIdAutore());
			if(autoreDelLibroConLibriScritti.getLibriScritti().size()==1) {
				throw new Exception("Rimozione fallita: il libro in input è l'ultimo che la biblioteca ha del suo autore");
			}

			boolean esito=libroDAO.delete(libroInstance);
			entityManager.getTransaction().commit();
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	private LibroDAO libroDAO;
	
	@Override
	public void setLibroDAO(LibroDAO libroDAO) {
		this.libroDAO=libroDAO;
	}

	@Override
	public Set<Libro> trovaTuttiTramiteAttributiEAutore(TreeMap<String, TreeSet<String>> input) throws Exception {
		if (input==null) {
			throw new Exception("Errore nell'input di ricerca");
		}
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			libroDAO.setEntityManager(entityManager);
			return libroDAO.findAllByExampleAndAutore(input);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}

	}

	@Override
	public Libro caricaSingoloElementoConAutore(Long idLibro) throws Exception {

		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			libroDAO.setEntityManager(entityManager);
			return libroDAO.getWithAutore(idLibro);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}

	}

}

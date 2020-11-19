package it.solvingteam.bibliotecaweb.service.autore;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.dao.EntityManagerUtil;
import it.solvingteam.bibliotecaweb.dao.autore.AutoreDAO;
import it.solvingteam.bibliotecaweb.model.Autore;

public class AutoreServiceImpl implements AutoreService {

	@Override
	public Set<Autore> elenca() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			autoreDAO.setEntityManager(entityManager);
			return autoreDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Autore caricaSingoloElemento(Long idAutore) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (idAutore==null||idAutore<=0) {
			throw new Exception("Errore nell'id dell'autore in input");
		}

		try {
			autoreDAO.setEntityManager(entityManager);

			return autoreDAO.get(idAutore);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean aggiorna(Autore autoreInstance) throws Exception {		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		
		try {
			entityManager.getTransaction().begin();			
			autoreDAO.setEntityManager(entityManager);

			// Non voglio che sia caricato un autore senza libri
			if (autoreInstance.getLibriScritti().size()==0) {
				throw new Exception("Aggiornamento fallito: l'autore in input non ha scritto libri");
			} else if(autoreInstance.getIdAutore()==null) {
				throw new Exception("Aggiornamento fallito: l'autore in input non ha un id, quindi non so cosa aggiornare sul db");
			}	


			// Non voglio autori doppioni, né autori multipli per uno stesso libro (cfr. il metodo equals in Autore e in Libro)
			Set<Autore> autoriPresenti=autoreDAO.list();
			if (autoriPresenti.size()==0) {
				throw new Exception("Aggiornamento fallito: non è presente alcun autore sul db");
			} else {
				//Impedisco di modificare un autore in modo da renderlo uguale a un altro già presente
				for (Autore a:autoriPresenti) {
					if (a.equals(autoreInstance)&&(a.getIdAutore()!=autoreInstance.getIdAutore())) {
						throw new Exception("Aggiornamento fallito: si sta tentando di replicare i dati di un altro autore");
					}
				}
			}
			
			boolean esito= autoreDAO.update(autoreInstance);
			entityManager.getTransaction().commit();
			return esito;
			
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento dell'autore");
		}

	}

	@Override
	public boolean inserisciNuovo(Autore autoreInstance) throws Exception {		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			// Non voglio che sia caricato un autore senza libri
			if (autoreInstance.getLibriScritti().size()==0) {
				throw new Exception("Inserimento fallito: l'autore in input non ha scritto libri");
			}

			autoreDAO.setEntityManager(entityManager);
			Set<Autore> autoriPresenti=autoreDAO.list();
			if (autoriPresenti.size()!=0) {
				// Non voglio autori doppioni, né autori multipli per uno stesso libro (cfr. il metodo equals in Autore e in Libro)
				for (Autore a:autoriPresenti) {
					if (a.equals(autoreInstance)) {
						throw new Exception("Inserimento fallito: l'autore in input è già presente nel db");
					}
				}
			}

			
			boolean esito=autoreDAO.insert(autoreInstance);
			entityManager.getTransaction().commit();		
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean rimuovi(Autore autoreInstance) throws Exception {
		if (autoreInstance==null||autoreInstance.getIdAutore()==null) {
			throw new Exception("Rimozione fallita: problema nell'autore in input");
		} 
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			autoreDAO.setEntityManager(entityManager);

			// Controllo che l'autore sia presente sul db
			Set<Long> idAutoriPresenti= autoreDAO.list().stream().map(autore->autore.getIdAutore()).collect(Collectors.toSet());
			if (!idAutoriPresenti.contains(autoreInstance.getIdAutore())) {
				throw new Exception("L'autore in input non è presente sul db");
			}
			
			// Se l'autore ha più di un libro, impedisco la rimozione (voglio rimuovere tutti i libri uno a uno). Permetto la rimozione solo
			// se è rimasto un solo libro
			
			if(autoreInstance.getLibriScritti().size()>1) {
				throw new Exception("Rimozione fallita: nella biblioteca è presente più di un suo libro");
			}
			
			boolean esito=autoreDAO.delete(autoreInstance);
			entityManager.getTransaction().commit();
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	private AutoreDAO autoreDAO;
	
	@Override
	public void setAutoreDAO(AutoreDAO autoreDAO) {
		this.autoreDAO=autoreDAO;
	}

	@Override
	public Set<Autore> trovaTuttiTramiteAttributiELibro(TreeMap<String, TreeSet<String>> input) throws Exception {
		if (input==null) {
			throw new Exception("Errore nell'input di ricerca");
		}
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			autoreDAO.setEntityManager(entityManager);
			return autoreDAO.findAllByExampleAndLibro(input);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Autore caricaSingoloElementoConLibri(Long idAutore) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (idAutore==null||idAutore<=0) {
			throw new Exception("Errore nell'id dell'autore in input");
		}

		try {
			autoreDAO.setEntityManager(entityManager);

			return autoreDAO.getWithLibri(idAutore);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}

	}

}

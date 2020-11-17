package it.solvingteam.bibliotecaweb.service.utente;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.dao.EntityManagerUtil;
import it.solvingteam.bibliotecaweb.dao.utente.UtenteDAO;
import it.solvingteam.bibliotecaweb.model.Utente;

public class UtenteServiceImpl implements UtenteService {

	@Override
	public Set<Utente> elenca() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			utenteDAO.setEntityManager(entityManager);
			return utenteDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Utente caricaSingoloElemento(Long idUtente) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (idUtente==null||idUtente<=0) {
			throw new Exception("Errore nell'id dell'utente in input");
		}

		try {
			utenteDAO.setEntityManager(entityManager);

			return utenteDAO.get(idUtente);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean aggiorna(Utente utenteInstance) throws Exception {
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			utenteDAO.setEntityManager(entityManager);

			// Non voglio che sia caricato un utente senza username o password
			if (utenteInstance.getUsername()==null||utenteInstance.getPassword()==null||utenteInstance.getRuoli().size()==0
					||utenteInstance.getUsername().isEmpty()||utenteInstance.getStatoUtente()==null||utenteInstance.getPassword().isEmpty()) {
				throw new Exception("Aggiornamento fallito: problema nell'utente in input");
			} else if(utenteInstance.getIdUtente()==null) {
				throw new Exception("Aggiornamento fallito: l'utente in input non ha un id, quindi non so cosa aggiornare sul db");
			}	

			Set<Utente> utentiPresenti=utenteDAO.list();
			if (utentiPresenti.size()==0) {
				throw new Exception("Aggiornamento fallito: non è presente alcun utente sul db");
			} else {
				//Impedisco di modificare un utente in modo da renderlo uguale a un altro già presente
				for (Utente u:utentiPresenti) {
					if (u.equals(utenteInstance)&&(u.getIdUtente()!=utenteInstance.getIdUtente())) {
						throw new Exception("Aggiornamento fallito: si sta tentando di replicare i dati di "+u);
					}
				}
			}
		
			boolean esito=utenteDAO.update(utenteInstance);
			entityManager.getTransaction().commit();
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean inserisciNuovo(Utente utenteInstance) throws Exception {
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			utenteDAO.setEntityManager(entityManager);

			// Non voglio che sia caricato un utente senza username, password, stato o ruolo
			if (utenteInstance.getUsername()==null||utenteInstance.getPassword()==null||utenteInstance.getRuoli().size()==0
					||utenteInstance.getUsername().isEmpty()||utenteInstance.getStatoUtente()==null||utenteInstance.getPassword().isEmpty()) {
				throw new Exception("Inserimento fallito: problema nell'utente in input");
			}

			Set<Utente> utentiPresenti=utenteDAO.list();
			if (utentiPresenti.size()!=0) {
				// Non voglio utenti doppioni
				for (Utente u:utentiPresenti) {
					if (u.equals(utenteInstance)) {
						throw new Exception("Inserimento fallito: " +utenteInstance+ " è già presente nel db come "+u);
					}
				}
			}

			boolean esito=utenteDAO.update(utenteInstance);
			entityManager.getTransaction().commit();		
			return esito;

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean rimuovi(Utente utenteInstance) throws Exception {
		if (utenteInstance==null||utenteInstance.getIdUtente()==null) {
			throw new Exception("Rimozione fallita: problema nell'utente in input");
		} 

		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();
			utenteDAO.setEntityManager(entityManager);
			// Controllo che l'utente sia presente sul db
			Set<Long> idUtentiPresenti= utenteDAO.list().stream().map(utente->utente.getIdUtente()).collect(Collectors.toSet());
			if (!idUtentiPresenti.contains(utenteInstance.getIdUtente())) {
				throw new Exception("L'utente in input non è presente sul db");
			}

			entityManager.getTransaction().commit();

			return utenteDAO.delete(utenteInstance);

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

	private UtenteDAO utenteDAO;
	
	@Override
	public void setUtenteDAO(UtenteDAO utenteDAO) {
		this.utenteDAO=utenteDAO;
	}

}

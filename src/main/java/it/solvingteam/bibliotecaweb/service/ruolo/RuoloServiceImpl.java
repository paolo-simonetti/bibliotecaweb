package it.solvingteam.bibliotecaweb.service.ruolo;

import java.util.Set;

import javax.persistence.EntityManager;

import it.solvingteam.bibliotecaweb.dao.EntityManagerUtil;
import it.solvingteam.bibliotecaweb.dao.ruolo.RuoloDAO;
import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.Ruolo;

public class RuoloServiceImpl implements RuoloService {

	@Override
	public Set<Ruolo> elenca() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {
		
			ruoloDAO.setEntityManager(entityManager);
			return ruoloDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Ruolo caricaSingoloElemento(Long idRuolo) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (idRuolo==null||idRuolo<=0) {
			throw new Exception("Errore nell'id del ruolo in input");
		}

		try {
			ruoloDAO.setEntityManager(entityManager);

			return ruoloDAO.get(idRuolo);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean aggiorna(Ruolo ruoloInstance) throws Exception {
		throw new Exception("Aggiornamento del ruolo negato: funzionalità non implementata");
	}

	@Override
	public boolean inserisciNuovo(Ruolo ruoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		if (ruoloInstance==null) {
			throw new Exception("Errore nel ruolo in input");
		}

		try {
			
			entityManager.getTransaction().begin();
			ruoloDAO.setEntityManager(entityManager);
			//Permetto di inserire un ruolo nel db solo se è tra quelli che ho previsto nella classe Ruolo 
			if (!NomeRuolo.conversioneRuolo.values().contains(ruoloInstance.getNomeRuolo())) {
				entityManager.getTransaction().rollback();
				throw new Exception("Inserimento fallito: ruolo non attualmente disponibile");
			} else {
				//Controllo che il ruolo non sia già presente
				Set<Ruolo> ruoliPresenti=ruoloDAO.list();
				for (Ruolo r:ruoliPresenti) {
					if (r.equals(ruoloInstance)) {
						System.err.println("Inserimento negato: ruolo già presente");
					}
				}
				
				boolean esito=ruoloDAO.insert(ruoloInstance);
				entityManager.getTransaction().commit();
				return esito;
			}

		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public boolean rimuovi(Ruolo ruoloInstance) throws Exception {
		throw new Exception("Rimozione del ruolo negato: funzionalità non implementata");
	}

	
	
	private RuoloDAO ruoloDAO;
	
	@Override
	public void setRuoloDAO(RuoloDAO ruoloDAO) {
		this.ruoloDAO=ruoloDAO;	
	}


}

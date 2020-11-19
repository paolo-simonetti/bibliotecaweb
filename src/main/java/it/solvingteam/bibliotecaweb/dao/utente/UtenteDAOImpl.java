package it.solvingteam.bibliotecaweb.dao.utente;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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

	@Override
	public Set<Utente> findAllByExampleAndRuolo(TreeMap<String, TreeSet<String>> input) throws Exception {
		Set<Utente> utentiResult=new TreeSet<>();
		String query=null;
		// Se il ruolo dell'utente non è stato messo in input, non c'è bisogno di fare una join 
		if (input.get("descrizioneRuolo")==null) {
			query="select u from Utente u where ";
		} else {
			query="select u from Utente u join fetch u.ruoli r where ";
		}
		Set<String> campiDiRicerca= input.keySet();
		for (String campo:campiDiRicerca) {
			//il pezzo di query relativo alla data di creazione e agli enum vanno scritti diversamente
			if (!campo.equals("dateCreated") &&!campo.equals("descrizioneRuolo") && !campo.equals("statoUtente") && input.get(campo)!=null) {  
				String pezzoQueryCampo=new String("");
				for (String s:input.get(campo)) {
					pezzoQueryCampo+="u."+campo+" LIKE '%"+s+"%' AND ";
				}
				query+=pezzoQueryCampo;
			}		
		}
		
		/*A questo punto, se nei campi di input non ci sono solo la data e/o lo stato dell'utente e/o il nome del ruolo, ho una 
	    (lunghissima) query che termina con un " AND ", derivante dal ciclo più interno eseguito precedentemente. Se, invece, c'è solo la data 
	    di creazione come input, ho solo la prima delle query originali scritte in alto. Infine, se (eventualmente, oltre la data) ci sono solo 
	    nomeRuolo e/o statoUtente, ho solo la seconda delle query originali scritte in alto.
		Quindi, se negli input di ricerca c'è anche la data, costruisco il pezzo di query ad esso relativo e lo concateno al precedente;
		altrimenti, devo togliere quell'AND:  */
		if (input.get("dateCreated")==null && input.get("descrizioneRuolo")==null && input.get("statoUtente")==null) {
			query=query.substring(0,query.length()-"AND ".length());
		} 
		if (input.get("descrizioneRuolo")!=null) {
			String pezzoQueryNomeRuolo="r.descrizioneRuolo = "+"'"+input.get("descrizioneRuolo").stream().reduce("",(s,t)->s+t)+"'"; //input è una mappa a valori TreeSet
			query+=pezzoQueryNomeRuolo;
			if(input.get("statoUtente")!=null) {
				query+=" AND ";
			}
		}
		if (input.get("statoUtente")!=null) {
			String pezzoQueryStatoUtente="u.statoUtente = "+"'"+input.get("statoUtente").stream().reduce("",(s,t)->s+t)+"'";
			query+=pezzoQueryStatoUtente;
			if(input.get("dateCreated")!=null) {
				query+=" AND ";
			}

		}
		if (input.get("dateCreated")!=null) {
			String pezzoQueryDateCreated="u.dateCreated = "+"'"+input.get("dateCreated").stream().reduce("",(s,t)->s+t)+"'"; //input è una mappa a valori TreeSet
			query+=pezzoQueryDateCreated;
		}
		System.out.println(query);
		// Ora la query è pronta.
		utentiResult= entityManager.createQuery(query,Utente.class).getResultList().stream().collect(Collectors.toSet());
		return utentiResult;

	}

}

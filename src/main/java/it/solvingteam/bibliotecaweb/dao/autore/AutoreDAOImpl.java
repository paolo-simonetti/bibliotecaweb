package it.solvingteam.bibliotecaweb.dao.autore;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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

	@Override
	public Set<Autore> findAllByExampleAndLibro(TreeMap<String, TreeSet<String>> input) throws Exception {
		Set<Autore> autoriResult=new TreeSet<>();
		String query=null;
		// Se il titolo del libro non è stato messo in input, non c'è bisogno di fare una join 
		if (input.get("titolo")==null) {
			query="select a from Autore a where ";
		} else {
			query="select a from Autore a join fetch a.libriScritti l where ";
		}
		Set<String> campiDiRicerca= input.keySet();
		for (String campo:campiDiRicerca) {
			if (!campo.equals("dataNascita") && input.get(campo)!=null) { //il pezzo di query relativo alla data di nascita va scritto diversamente 
				String pezzoQueryCampo=new String("");
				for (String s:input.get(campo)) {
					/* titolo è un attributo della classe libro, quindi voglio un "l.titolo". Gli altri attributi (nome,cognome,dataNascita)
					 *  sono della classe Autore, quindi voglio un a.nome,a.cognome,a.dataNascita */
					String alias=(campo.equals("titolo")? "l.":"a.");  
					pezzoQueryCampo+=alias+campo+" LIKE '%"+s+"%' AND ";
				}
				query+=pezzoQueryCampo;
			}		
		}
		/*A questo punto, se nei campi di input non c'è solo la data, ho una (lunghissima) query che termina con un " AND ", derivante
		dal ciclo più interno eseguito precedentemente. Se invece c'è solo la data come input, ho solo la query originale scritta in alto.
		Quindi, se negli input di ricerca c'è anche la data, costruisco il pezzo di query ad esso relativo e lo concateno al precedente;
		altrimenti, devo togliere quell'AND:  */
		if (input.get("dataNascita")!=null) {
			String pezzoQueryData="a.dataNascita = "+"'"+input.get("dataNascita").stream().reduce("",(s,t)->s+t)+"'"; //input è una mappa a valori TreeSet
			query+=pezzoQueryData;
		} else {
			query=query.substring(0,query.length()-"AND ".length());
		}

		// Ora la query è pronta.
		autoriResult= entityManager.createQuery(query,Autore.class).getResultList().stream().collect(Collectors.toSet());
		return autoriResult;
	}

}

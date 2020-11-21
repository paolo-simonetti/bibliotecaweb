package it.solvingteam.bibliotecaweb.dao.libro;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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

	@Override
	public Set<Libro> findAllByExampleAndAutore(TreeMap<String, TreeSet<String>> input) throws Exception {
		Set<Libro> libriResult=new TreeSet<>();
		String query=null;
		// Se il nome e il cognome dell'autore non sono stati messi in input, non c'è bisogno di fare una join 
		if (input.get("nomeAutore")==null&&input.get("cognomeAutore")==null) {
			query="select l from Libro l where ";
		} else {
			query="select l from Libro l join fetch l.autoreDelLibro a where ";
		}
		Set<String> campiDiRicerca= input.keySet();
		for (String campo:campiDiRicerca) {
			if (!campo.equals("ISBN")&&!campo.equals("genere") && input.get(campo)!=null) { //i pezzi di query relativi all'ISBN e al genere vanno scritto diversamente 
				String pezzoQueryCampo=new String("");
				for (String s:input.get(campo)) {
					/* titolo e trama sono attributi della classe libro, quindi voglio un "l.titolo" e un "l.trama". Gli altri attributi, 
					 * nomeAutore e cognomeAutore, sono della classe Autore, quindi voglio un a.nomeAutore,a.cognomeAutore */
					String alias=(campo.equals("titolo")||(campo.equals("trama"))? "l.":"a.");  
					pezzoQueryCampo+=alias+campo+" LIKE '%"+s+"%' AND ";
				}
				query+=pezzoQueryCampo;
			}		
		}
		/*A questo punto, se nei campi di input non ci sono solo genere e ISBN, ho una (lunghissima) query che termina con un " AND ", derivante
		dal ciclo più interno eseguito precedentemente. Se invece ci sono solo genere e/o ISBN come input, ho solo la query originale scritta 
		in alto. Quindi, se negli input di ricerca ci sono anche genere e/o ISBN, costruisco il pezzo di query ad essi relativo e lo concateno 
		al precedente; altrimenti, devo togliere quell'AND:  */
		if (input.get("ISBN")==null&&input.get("genere")==null) {
			query=query.substring(0,query.length()-"AND ".length());
		} 
		if (input.get("ISBN")!=null) {
			String pezzoQueryISBN="l.ISBN = "+"'"+input.get("ISBN").stream().reduce("",(s,t)->s+t)+"'"; //input è una mappa a valori TreeSet
			query+=pezzoQueryISBN;
			if(input.get("ISBN")!=null) {
				query+=" AND ";
			}
		}
		if (input.get("genere")!=null) {
			String pezzoQueryGenere="l.genere like "+"'"+input.get("genere").stream().reduce("",(s,t)->s+t)+"'";
			query+=pezzoQueryGenere;
		}

		// Ora la query è pronta.
		libriResult= entityManager.createQuery(query,Libro.class).getResultList().stream().collect(Collectors.toSet());
		return libriResult;
	}

	@Override
	public Libro getWithAutore(Long idLibro) throws Exception {
		Libro libroResult=null;
		String query="select l from Libro l join fetch l.autoreDelLibro a where l.idLibro="+idLibro;
		try {
			libroResult=entityManager.createQuery(query,Libro.class).getResultList().stream().findFirst().orElse(null);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Errore nel recupero del libro richiesto");
			throw e;
		}
		return libroResult;
	}

}

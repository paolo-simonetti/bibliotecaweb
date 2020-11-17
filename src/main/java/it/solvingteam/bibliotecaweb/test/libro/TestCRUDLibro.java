package it.solvingteam.bibliotecaweb.test.libro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.model.Autore;
import it.solvingteam.bibliotecaweb.model.Genere;
import it.solvingteam.bibliotecaweb.model.Libro;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.StandAloneUtils;


public class TestCRUDLibro {

	public static void main(String[] args) {
		List<Boolean> esitiOperazione=new ArrayList<>();
		StandAloneUtils standAloneUtils=new StandAloneUtils();
		//Inserisco un articolo da main
		Libro libroDaMain=null;
		Autore autoreDaMain=new Autore("Aldo", "Giannuli", LocalDate.of(1952,6,8));
		try {
			libroDaMain=new Libro("Mafia mondiale",Genere.REPORTAGE, "È arrivato ormai il momento di parlare di \"geopolitica della Mafia\", e questo libro lo fa in maniera chiara e accessibile a tutti. ", "9788833312750");			
		} catch(Exception e) {
			System.err.println("Problemi con l'ISBN");
		}
		Set<Libro> libriScrittiDaAutore=new TreeSet<>();
		libriScrittiDaAutore.add(libroDaMain);
		autoreDaMain.setLibriScritti(libriScrittiDaAutore);
		libroDaMain.setAutoreDelLibro(autoreDaMain);
	
		try {
			boolean esitoAutore1=MyServiceFactory.getAutoreServiceInstance().inserisciNuovo(autoreDaMain);
			boolean esito1=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(libroDaMain);
			System.out.println("INSERIMENTO --- ");
			esitiOperazione.add(esito1&&esitoAutore1);
			standAloneUtils.testaOperazione(esito1&&esitoAutore1);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Aggiorno il libro da main
		libroDaMain.setGenere(Genere.SOCIETA);
		System.out.println("AGGIORNAMENTO --- ");
		try {
			boolean esito2=MyServiceFactory.getLibroServiceInstance().aggiorna(libroDaMain);
			esitiOperazione.add(esito2);
			standAloneUtils.testaOperazione(esito2);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Recupero il libro da db
		Libro libroDaDb=new Libro();
		System.out.println("CARICAMENTO --- ");
		try {
			libroDaDb=MyServiceFactory.getLibroServiceInstance().caricaSingoloElemento(libroDaMain.getIdLibro());
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		boolean esito3=(libroDaDb!=null);
		esitiOperazione.add(esito3);
		standAloneUtils.testaOperazione(esito3);
		
		//Inserisco un altro libro da db e mi faccio tornare tutti i presenti
		Autore autoreDaMain2=new Autore("Sheldon", "Kopp", LocalDate.of(1929,3,29));
		Libro libroDaMain2=null;
		try {
			libroDaMain2 = new Libro("Scritto sotto la forca", Genere.REPORTAGE, "Il racconto dell'ultimo anno di vita di Julius Fucik", "9788877996138");
		} catch (Exception e1) {
			System.err.println("Problemi con l'ISBN");
		}
		Set<Libro> libriScrittiDaAutore2=new TreeSet<>();
		libriScrittiDaAutore2.add(libroDaMain2);
		autoreDaMain2.setLibriScritti(libriScrittiDaAutore2);
		Set<Libro> libriPresenti=null;;
		System.out.println("ELENCAZIONE --- ");
		try {
			boolean inserimentoSecondoAutoreRiuscito=MyServiceFactory.getAutoreServiceInstance().inserisciNuovo(autoreDaMain2);
			boolean inserimentoSecondoLibroRiuscito=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(libroDaMain2);
			if (inserimentoSecondoLibroRiuscito) {
				libriPresenti=new TreeSet<>();
				libriPresenti=MyServiceFactory.getLibroServiceInstance().elenca(); 
				libriPresenti.stream().forEach(libro->System.out.println(libro+"\n"));
			}
			boolean esito4=(libriPresenti!=null&&inserimentoSecondoAutoreRiuscito);
			esitiOperazione.add(esito4);
			standAloneUtils.testaOperazione(esito4);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Rimuovo un libro da main
		System.out.println("RIMOZIONE --- \n");
		System.out.println("Provo a rimuovere "+ libroDaMain+"\n");
		try {
			boolean esito5=MyServiceFactory.getLibroServiceInstance().rimuovi(libroDaMain);
			esitiOperazione.add(esito5);
			standAloneUtils.testaOperazione(esito5);
			System.out.println("Libri rimanenti sul db: \n");
			MyServiceFactory.getLibroServiceInstance().elenca().stream().forEach(libro->System.out.println(libro));
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		boolean testCRUDFallito=esitiOperazione.stream().filter(esito->esito==false).findFirst().orElse(true);
		System.out.println("TOTALE CRUD: ");
		standAloneUtils.testaOperazione(testCRUDFallito);
	}

}

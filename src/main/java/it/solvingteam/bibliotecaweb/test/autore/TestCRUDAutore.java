package it.solvingteam.bibliotecaweb.test.autore;

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

public class TestCRUDAutore {

	public static void main(String[] args) {
		List<Boolean> esitiOperazione=new ArrayList<>();
		StandAloneUtils standAloneUtils=new StandAloneUtils();
		//Inserisco un autore da main
		Autore autoreDaMain=new Autore("Aldo", "Giannuli", LocalDate.of(1952,6,8));
		Libro libroDaMain=null;
		Libro libroDaMain3=null;
		try {
			libroDaMain=new Libro("Mafia mondiale",Genere.REPORTAGE, "Oh jesus", "9788833312750");	
			libroDaMain3=new Libro("Bombe a inchiostro", Genere.REPORTAGE, "aiuto","9788817020596");
		} catch(Exception e) {
			System.err.println("Problemi con l'ISBN");
		}
		Set<Libro> libriScrittiDaAutore=new TreeSet<>();
		libriScrittiDaAutore.add(libroDaMain);
		libriScrittiDaAutore.add(libroDaMain3);
		autoreDaMain.setLibriScritti(libriScrittiDaAutore);
		libroDaMain.setAutoreDelLibro(autoreDaMain);
		libroDaMain3.setAutoreDelLibro(autoreDaMain);
		try {
			boolean esito1=MyServiceFactory.getAutoreServiceInstance().inserisciNuovo(autoreDaMain);
			boolean esitoLibro1=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(libroDaMain);
			System.out.println("INSERIMENTO --- ");
			esitiOperazione.add(esito1&&esitoLibro1);
			standAloneUtils.testaOperazione(esito1&&esitoLibro1);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Aggiorno l'autore da main
		autoreDaMain.setDataNascita(LocalDate.of(1998,8,26));
		System.out.println("AGGIORNAMENTO --- ");
		try {
			boolean esito2=MyServiceFactory.getAutoreServiceInstance().aggiorna(autoreDaMain);
			esitiOperazione.add(esito2);
			standAloneUtils.testaOperazione(esito2);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Recupero l'autore da db
		Autore autoreDaDb=new Autore();
		System.out.println("CARICAMENTO --- ");
		try {
			autoreDaDb=MyServiceFactory.getAutoreServiceInstance().caricaSingoloElemento(autoreDaMain.getIdAutore());
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		boolean esito3=(autoreDaDb!=null);
		esitiOperazione.add(esito3);
		standAloneUtils.testaOperazione(esito3);
		
		//Inserisco un altro autore da db e mi faccio tornare tutti i presenti
		Autore autoreDaMain2=new Autore("Sheldon", "Kopp", LocalDate.of(1929,3,29));
		Libro libroDaMain2=null;
		try {
			libroDaMain2=new Libro("Se incontri il Buddha per la strada, uccidilo", Genere.AUTOAIUTO,"AAAAAAAAAA","9788834000793");
			libroDaMain2.setAutoreDelLibro(autoreDaMain2);
		} catch(Exception e) {
			System.err.println("Problemi con l'ISBN");
		}
		Set<Libro> libriScrittiDaAutore2=new TreeSet<>();
		libriScrittiDaAutore2.add(libroDaMain2);
		autoreDaMain2.setLibriScritti(libriScrittiDaAutore2);
		libroDaMain2.setAutoreDelLibro(autoreDaMain2);
		Set<Autore> autoriPresenti=null;;
		System.out.println("ELENCAZIONE --- ");
		try {
			boolean inserimentoSecondoAutoreRiuscito=MyServiceFactory.getAutoreServiceInstance().inserisciNuovo(autoreDaMain2);
			boolean inserimentoSecondoLibroRiuscito=MyServiceFactory.getLibroServiceInstance().inserisciNuovo(libroDaMain2);
			if (inserimentoSecondoAutoreRiuscito&&inserimentoSecondoLibroRiuscito) {
				autoriPresenti=new TreeSet<>();
				autoriPresenti=MyServiceFactory.getAutoreServiceInstance().elenca(); 
				autoriPresenti.stream().forEach(autore->System.out.println(autore+"\n"));
			}
			boolean esito4=((autoriPresenti!=null)&&(inserimentoSecondoLibroRiuscito));
			esitiOperazione.add(esito4);
			standAloneUtils.testaOperazione(esito4);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Rimuovo un autore da main
		System.out.println("RIMOZIONE --- \n");
		System.out.println("Provo a rimuovere "+ autoreDaMain+"\n");
		try {
			boolean esito5=MyServiceFactory.getAutoreServiceInstance().rimuovi(autoreDaMain);
			esitiOperazione.add(esito5);
			standAloneUtils.testaOperazione(esito5);
			System.out.println("Autori rimanenti sul db: \n");
			MyServiceFactory.getAutoreServiceInstance().elenca().stream().forEach(autore->System.out.println(autore));
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		boolean testCRUDFallito=esitiOperazione.stream().filter(esito->esito==false).findFirst().orElse(true);
		System.out.println("TOTALE CRUD: ");
		standAloneUtils.testaOperazione(testCRUDFallito);
	}

}

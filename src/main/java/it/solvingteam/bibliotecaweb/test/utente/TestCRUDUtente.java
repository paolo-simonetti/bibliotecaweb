package it.solvingteam.bibliotecaweb.test.utente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.Ruolo;
import it.solvingteam.bibliotecaweb.model.Utente;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.StandAloneUtils;

public class TestCRUDUtente {

	public static void main(String[] args) {
		List<Boolean> esitiOperazione=new ArrayList<>();
		StandAloneUtils standAloneUtils=new StandAloneUtils();
		//Inserisco un utente da main
		Utente utenteDaMain=new Utente("bravoRagazzo", "amoLeFeste", "Raffaele", "Sollecito", LocalDate.now());
		Set<Ruolo> ruoli=new TreeSet<>();
		Ruolo ruoloDaMain=new Ruolo(NomeRuolo.CLASSIC_ROLE);
		ruoli.add(ruoloDaMain);
		try {
			boolean esitoRuolo=MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(ruoloDaMain);	
			utenteDaMain.setRuoli(ruoli);
			boolean esito1=MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaMain);
			System.out.println("INSERIMENTO --- ");
			esitiOperazione.add(esito1);
			standAloneUtils.testaOperazione(esito1&&esitoRuolo);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Aggiorno l'utente da main
		utenteDaMain.setPassword("vivaGliErasmusParty");
		System.out.println("AGGIORNAMENTO --- ");
		try {
			boolean esito2=MyServiceFactory.getUtenteServiceInstance().aggiorna(utenteDaMain);
			esitiOperazione.add(esito2);
			standAloneUtils.testaOperazione(esito2);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Recupero l'utente da db
		Utente utenteDaDb=new Utente();
		System.out.println("CARICAMENTO --- ");
		try {
			utenteDaDb=MyServiceFactory.getUtenteServiceInstance().caricaSingoloElemento(utenteDaMain.getIdUtente());
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		boolean esito3=(utenteDaDb==null);
		esitiOperazione.add(esito3);
		standAloneUtils.testaOperazione(esito3);
		
		//Inserisco un altro utente da db e mi faccio tornare tutti i presenti
		Utente utenteDaMain2=new Utente("ilBombarolo", "mmmLeCentralineElettriche", "Giangiacomo", "Feltrinelli", LocalDate.now());
		utenteDaMain2.setRuoli(ruoli);
		Set<Utente> utentiPresenti=null;;
		System.out.println("ELENCAZIONE --- ");
		try {
			boolean inserimentoSecondoUtenteRiuscito=MyServiceFactory.getUtenteServiceInstance().inserisciNuovo(utenteDaMain2);
			if (inserimentoSecondoUtenteRiuscito) {
				utentiPresenti=new TreeSet<>();
				utentiPresenti=MyServiceFactory.getUtenteServiceInstance().elenca(); 
				utentiPresenti.stream().forEach(utente->System.out.println(utente+"\n"));
			}
			boolean esito4=(utentiPresenti==null);
			esitiOperazione.add(esito4);
			standAloneUtils.testaOperazione(esito4);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Rimuovo un autore da main
		System.out.println("RIMOZIONE --- \n");
		System.out.println("Provo a rimuovere "+ utenteDaMain+"\n");
		try {
			boolean esito5=MyServiceFactory.getUtenteServiceInstance().rimuovi(utenteDaMain);
			esitiOperazione.add(esito5);
			standAloneUtils.testaOperazione(esito5);
			System.out.println("Utenti rimanenti sul db: \n");
			MyServiceFactory.getUtenteServiceInstance().elenca().stream().forEach(utente->System.out.println(utente));
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		boolean testCRUDFallito=esitiOperazione.stream().filter(esito->esito==false).findFirst().orElse(true);
		System.out.println("TOTALE CRUD: ");
		standAloneUtils.testaOperazione(!testCRUDFallito);
	}

}

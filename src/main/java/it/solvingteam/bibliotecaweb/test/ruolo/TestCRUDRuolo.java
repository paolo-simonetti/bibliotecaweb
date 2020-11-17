package it.solvingteam.bibliotecaweb.test.ruolo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.solvingteam.bibliotecaweb.model.NomeRuolo;
import it.solvingteam.bibliotecaweb.model.Ruolo;
import it.solvingteam.bibliotecaweb.service.MyServiceFactory;
import it.solvingteam.bibliotecaweb.utils.StandAloneUtils;

public class TestCRUDRuolo {

	public static void main(String[] args) {
		List<Boolean> esitiOperazione=new ArrayList<>();
		StandAloneUtils standAloneUtils=new StandAloneUtils();
		//Inserisco un ruolo da main
		Ruolo ruoloDaMain=new Ruolo(NomeRuolo.ADMIN_ROLE);
		try {
			boolean esito1=MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(ruoloDaMain);
			System.out.println("INSERIMENTO --- ");
			esitiOperazione.add(esito1);
			standAloneUtils.testaOperazione(esito1);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Aggiorno il ruolo da main
		Ruolo ruoloDaMain2=new Ruolo(NomeRuolo.CLASSIC_ROLE);
		ruoloDaMain2.setIdRuolo(1L);
		System.out.println("AGGIORNAMENTO --- ");
		try {
			boolean esito2=MyServiceFactory.getRuoloServiceInstance().aggiorna(ruoloDaMain2);
			esitiOperazione.add(esito2);
			standAloneUtils.testaOperazione(esito2);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Recupero il ruolo da db
		Ruolo ruoloDaDb=null;
		System.out.println("CARICAMENTO --- ");
		try {
			ruoloDaDb=MyServiceFactory.getRuoloServiceInstance().caricaSingoloElemento(7L);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		boolean esito3=(ruoloDaDb!=null);
		esitiOperazione.add(esito3);
		standAloneUtils.testaOperazione(esito3);
		
		//Inserisco un altro ruolo da db e mi faccio tornare tutti i presenti
		Ruolo ruoloDaMain3=new Ruolo(NomeRuolo.GUEST_ROLE);
		Set<Ruolo> ruoliPresenti=null;;
		System.out.println("ELENCAZIONE --- ");
		try {
			boolean inserimentoTerzoRuoloRiuscito=MyServiceFactory.getRuoloServiceInstance().inserisciNuovo(ruoloDaMain3);
			if (inserimentoTerzoRuoloRiuscito) {
				ruoliPresenti=new TreeSet<>();
				ruoliPresenti=MyServiceFactory.getRuoloServiceInstance().elenca(); 
				ruoliPresenti.stream().forEach(ruolo->System.out.println(ruolo+"\n"));
			}
			boolean esito4=(ruoliPresenti!=null);
			esitiOperazione.add(esito4);
			standAloneUtils.testaOperazione(esito4);
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		//Rimuovo un ruolo da main
		System.out.println("RIMOZIONE --- \n");
		System.out.println("Provo a rimuovere "+ ruoloDaMain+"\n");
		try {
			boolean esito5=MyServiceFactory.getRuoloServiceInstance().rimuovi(ruoloDaMain);
			esitiOperazione.add(esito5);
			standAloneUtils.testaOperazione(esito5);
			System.out.println("Ruoli rimanenti sul db: \n");
			MyServiceFactory.getRuoloServiceInstance().elenca().stream().forEach(ruolo->System.out.println(ruolo));
		} catch(Exception e) {
			standAloneUtils.testaOperazione(false);
			esitiOperazione.add(false);
		}
		
		boolean testCRUDFallito=esitiOperazione.stream().filter(esito->esito==false).findFirst().orElse(true);
		System.out.println("TOTALE CRUD: ");
		standAloneUtils.testaOperazione(testCRUDFallito);
	}

}

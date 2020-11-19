package it.solvingteam.bibliotecaweb.model;

import java.util.Map;
import java.util.TreeMap;

public enum Genere {
	
	NON_SPECIFICATO(""),
	STORICO("storico"),
	NARRATIVA("narrativa"),
	ROSA("rosa"),
	YOUNG_ADULT("youngadult"),
	GIALLO("giallo"),
	THRILLER("thriller"),
	NOIR("noir"),
	PER_RAGAZZI("perRagazzi"),
	PER_BAMBINI("perBambini"),
	RACCONTI("racconti"),
	AVVENTURA("avventura"),
	FANTASCIENZA("fantascienza"), 
	FANTASY("fantasy"),
	EROTICO("erotico"),
	REALISMO_MAGICO("realismoMagico"),
	CLASSICI("classici"),
	BIOGRAFIA("biografia"),
	AUTOAIUTO("autoaiuto"),
	FILOSOFIA("filosofia"), 
	LETTERATURA("letteratura"),
	LINGUE_STRANIERE("linguestraniere"),
	LINGUISTICA("linguistica"),
	VIAGGIO("viaggio"),
	STORIA("storia"),
	GUIDA("guida"),
	FOLKLORE("folklore"),
	RICETTARIO("ricettario"),
	EPISTOLE("epistole"),
	SOCIETA("societa"),
	ATTUALITA("attualita"),
	RELIGIONE("religione"),
	SOCIOLOGIA("sociologia"),
	CARTOMANZIA("cartomanzia"),
	MARKETING("marketing"),
	BENESSERE("benessere"),
	ARCHITETTURA("architettura"),
	PSICOLOGIA("psicologia"),
	POLITICA("politica"),
	ARTE("arte"),
	REPORTAGE("reportage"),
	EDUCAZIONE("educazione"),
	PUERICULTURA("puericultura"),
	ECOLOGIA("ecologia"),
	ESSAY("essay"),
	BOTANICA("botanica"),
	TECNOLOGIA("tecnologia"),
	PROGRAMMAZIONE("programmazione"),
	TRAINING("training"),
	ETOLOGIA("etologia"),
	SPORT("sport"),
	ANTROPOLOGIA("antropologia"),
	MEDICINA("medicina"),
	MUSICA("musica"),
	CINEMA("cinema"),
	CULTURA("cultura"),
	DIETA("dieta"),
	ESOTERISMO("esoterismo"),
	MITOLOGIA("mitologia");
	
	private String stringaGenere;
	
	public String toString() {
		return stringaGenere;
	}
	
	Genere(String stringaGenere) {
		this.stringaGenere=stringaGenere;
	}
	
	public static Map<String,Genere> conversioneGenere=new TreeMap<>();
	static {
		
		conversioneGenere.put("",NON_SPECIFICATO);
		conversioneGenere.put("classici", CLASSICI);
		conversioneGenere.put("storico",STORICO);
		conversioneGenere.put("narrativa",NARRATIVA);
		conversioneGenere.put("rosa",ROSA);
		conversioneGenere.put("youngadult",YOUNG_ADULT);
		conversioneGenere.put("giallo",GIALLO);
		conversioneGenere.put("thriller",THRILLER);
		conversioneGenere.put("noir",NOIR);	
		conversioneGenere.put("perRagazzi",PER_RAGAZZI);	
		conversioneGenere.put("perBambini",PER_BAMBINI);
		conversioneGenere.put("avventura",AVVENTURA);
		conversioneGenere.put("fantascienza",FANTASCIENZA);
		conversioneGenere.put("fantasy",FANTASY);
		conversioneGenere.put("erotico",EROTICO);
		conversioneGenere.put("realismoMagico",REALISMO_MAGICO);
		conversioneGenere.put("biografia",BIOGRAFIA);
		conversioneGenere.put("autoaiuto",AUTOAIUTO);
		conversioneGenere.put("filosofia",FILOSOFIA);
		conversioneGenere.put("letteratura",LETTERATURA);
		conversioneGenere.put("linguestraniere",LINGUE_STRANIERE);
		conversioneGenere.put("linguistica",LINGUISTICA);
		conversioneGenere.put("viaggio",VIAGGIO);
		conversioneGenere.put("storia",STORIA);
		conversioneGenere.put("guida",GUIDA);
		conversioneGenere.put("folklore",FOLKLORE);
		conversioneGenere.put("ricettario",RICETTARIO);
		conversioneGenere.put("epistole",EPISTOLE);
		conversioneGenere.put("societa",SOCIETA);
		conversioneGenere.put("attualita",ATTUALITA);
		conversioneGenere.put("religione",RELIGIONE);
		conversioneGenere.put("sociologia",SOCIOLOGIA);
		conversioneGenere.put("marketing",MARKETING);
		conversioneGenere.put("benessere",BENESSERE);
		conversioneGenere.put("architettura",ARCHITETTURA);
		conversioneGenere.put("psicologia",PSICOLOGIA);
		conversioneGenere.put("politica",POLITICA);
		conversioneGenere.put("arte",ARTE);
		conversioneGenere.put("reportage",REPORTAGE);
		conversioneGenere.put("educazione",EDUCAZIONE);
		conversioneGenere.put("puericultura",PUERICULTURA);
		conversioneGenere.put("ecologia",ECOLOGIA);
		conversioneGenere.put("essay",ESSAY);
		conversioneGenere.put("botanica",BOTANICA);
		conversioneGenere.put("tecnologia",TECNOLOGIA);
		conversioneGenere.put("programmazione",PROGRAMMAZIONE);
		conversioneGenere.put("training",TRAINING);
		conversioneGenere.put("etologia",ETOLOGIA);
		conversioneGenere.put("sport",SPORT);
		conversioneGenere.put("antropologia",ANTROPOLOGIA);
		conversioneGenere.put("medicina",MEDICINA);
		conversioneGenere.put("musica",MUSICA);
		conversioneGenere.put("cinema",CINEMA);
		conversioneGenere.put("cultura",CULTURA);
		conversioneGenere.put("dieta",DIETA);
		conversioneGenere.put("esoterismo",ESOTERISMO);
		conversioneGenere.put("mitologia",MITOLOGIA);

	}
}

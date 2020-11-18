package it.solvingteam.bibliotecaweb.model;

import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ruolo")
public class Ruolo implements Comparable<Ruolo> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ruolo")
	private Long idRuolo;

	@Enumerated(EnumType.STRING)
	private final NomeRuolo nomeRuolo;
	
	@Column(name="descrizione_ruolo")
	private final String descrizioneRuolo;
	
	public Ruolo() {
		this.nomeRuolo=NomeRuolo.GUEST_ROLE;
		this.descrizioneRuolo=nomeRuolo.toString();
	}
		
	public Ruolo(NomeRuolo nomeRuolo) {
		this.nomeRuolo=nomeRuolo;
		this.descrizioneRuolo=nomeRuolo.getNomeRuolo();
	}
	
	public Long getIdRuolo() {
		return idRuolo;
	}

	public NomeRuolo getNomeRuolo() {
		return nomeRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}
	
	public boolean equals(Ruolo ruoloInstance) {
		return (nomeRuolo.toString().equals(ruoloInstance.getNomeRuolo().toString()));
	}

	public String toString() {
		return nomeRuolo.toString();
	} 
	public static Map<String,NomeRuolo> conversioneRuolo=new TreeMap<>();
	static {
		
		conversioneRuolo.put("admin", NomeRuolo.ADMIN_ROLE);
		conversioneRuolo.put("classic", NomeRuolo.CLASSIC_ROLE);
		conversioneRuolo.put("guest", NomeRuolo.GUEST_ROLE);
	}
	@Override
	public int compareTo(Ruolo ruolo) {
		return descrizioneRuolo.compareTo(ruolo.getDescrizioneRuolo());
	}
	
}

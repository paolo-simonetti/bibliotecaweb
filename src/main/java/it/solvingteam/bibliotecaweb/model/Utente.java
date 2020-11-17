package it.solvingteam.bibliotecaweb.model;


import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="utente")
public class Utente implements Comparable<Utente>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_utente")
	private Long idUtente;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "nome_utente")
	private String nomeUtente;
	@Column(name = "cognome_utente")
	private String cognomeUtente;
	@Column(name = "date_created")
	private LocalDate dateCreated;

	// se non uso questa annotation viene gestito come un intero
	@Enumerated(EnumType.STRING)
	private StatoUtente statoUtente = StatoUtente.ABILITATO;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id_utente"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "id_ruolo"))
	private Set<Ruolo> ruoli = new TreeSet<>();

	public Utente() {
	}

	public Utente(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Utente(String username, String password, String nomeUtente, String cognomeUtente, LocalDate dateCreated) {
		this.username = username;
		this.password = password;
		this.nomeUtente = nomeUtente;
		this.cognomeUtente = cognomeUtente;
		this.dateCreated = dateCreated;
	}
	
	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getCognomeUtente() {
		return cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStatoUtente() {
		return statoUtente;
	}

	public void setStatoUtente(StatoUtente statoUtente) {
		this.statoUtente = statoUtente;
	}
	
	@Override
	public String toString() {
		return "Utente [idUtente=" + idUtente + ", username=" + username + ", password=" + password + ", nomeUtente=" + nomeUtente
				+ ", cognomeUtente=" + cognomeUtente + ", dateCreated=" + dateCreated + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Utente) {
			Utente u=(Utente) o;
			return username.equals(u.getUsername());			
		} else {
			return this.equals(o);
		}
	}
	
	@Override
	public int compareTo(Utente utente) {
		return cognomeUtente.compareTo(utente.getCognomeUtente());
	}

	
	
}

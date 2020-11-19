package it.solvingteam.bibliotecaweb.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="autore")
public class Autore implements Comparable<Autore> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_autore")
	private Long idAutore;
	
	@Column(name="nome_autore")
	private String nomeAutore;
	
	@Column(name="cognome_autore")
	private String cognomeAutore;
	
	@Column(name="data_di_nascita")
	private LocalDate dataNascita;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="autoreDelLibro")
	private Set<Libro> libriScritti=new TreeSet<>();

	public Long getIdAutore() {
		return idAutore;
	}

	public void setIdAutore(Long idAutore) {
		this.idAutore = idAutore;
	}

	public String getNomeAutore() {
		return nomeAutore;
	}

	public void setNomeAutore(String nomeAutore) {
		this.nomeAutore = nomeAutore;
	}

	public String getCognomeAutore() {
		return cognomeAutore;
	}

	public void setCognomeAutore(String cognomeAutore) {
		this.cognomeAutore = cognomeAutore;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Set<Libro> getLibriScritti() {
		return libriScritti;
	}

	public void setLibriScritti(Set<Libro> libriScritti) {
		this.libriScritti = libriScritti;
	}

	public Autore() {}
	
	public Autore(String nomeAutore, String cognomeAutore, LocalDate dataNascita) {
		this.nomeAutore = nomeAutore;
		this.cognomeAutore = cognomeAutore;
		this.dataNascita = dataNascita;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Autore) {
			Autore autore=(Autore) o;
			/*Controllo se nella lista di libri scritti dai due autori ce n'è almeno uno in comune
			 * Non uso equals per la lista di libri, altrimenti lo fa sulla base dell'indirizzo di memoria.
			 * Non faccio l'uguaglianza su tutta la lista di libri, non è necessaria */
			if (autore.getLibriScritti().size()==0) {
				return (nomeAutore.equals(autore.getNomeAutore())&&cognomeAutore.equals(autore.getCognomeAutore())&&
						dataNascita.isEqual(autore.getDataNascita()));
			} else {
				try {
					boolean libroInComuneIsPresente=false;
					for(Libro libro:libriScritti) {
						for (Libro libroAutore:autore.getLibriScritti()) {
							libroInComuneIsPresente=(libro.equals(libroAutore));					
						}
						if(libroInComuneIsPresente) {
							return true;
						}
					}
					return false;
				} catch(Exception e) {
					System.err.println("Errore nel confronto con "+ autore+ ": uno dei libri non ha ISBN");
					return false;
				}
			}

		} else {
			return o.equals(this);
		}
		
	}


	@Override
	public String toString() {
		return "Autore [idAutore=" + idAutore + ", nomeAutore=" + nomeAutore + ", cognomeAutore=" + cognomeAutore
				+ ", dataNascita=" + dataNascita + "]";
	}

	@Override
	public int compareTo(Autore autore) {
		return cognomeAutore.compareTo(autore.getCognomeAutore());
	}
	
	
	
}

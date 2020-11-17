package it.solvingteam.bibliotecaweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="libro")
public class Libro implements Comparable<Libro>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_libro")
	private Long idLibro;
	
	@Column(name = "titolo")
	private String titolo;
	
	@Enumerated(EnumType.STRING)
	private Genere genere;
	
	@Column(name = "trama")
	private String trama;
	
	@Column(name = "isbn")
	private String ISBN;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "autore_fk")
	private Autore autoreDelLibro;

	public Long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere genere) {
		this.genere = genere;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		this.trama = trama;
	}

	public Autore getAutoreDelLibro() {
		return autoreDelLibro;
	}

	public void setAutoreDelLibro(Autore autoreDelLibro) {
		this.autoreDelLibro = autoreDelLibro;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public Libro() {}
	
	public Libro(String titolo, Genere genere, String trama, String ISBN) throws Exception {
		this.titolo = titolo;
		this.genere = genere;
		this.trama = trama;
		if(ISBN==null||ISBN.isEmpty()) {
			throw new Exception("Instanziazione di libro fallita: ISBN non valido");
		} else {
			this.ISBN=ISBN;			
		}
	}

	@Override
	public String toString() {
		return "Libro [idLibro=" + idLibro + ", titolo=" + titolo + ", genere=" + genere + ", trama=" + trama
				+ ", autoreDelLibro=" + autoreDelLibro + "]";
	}

	@Override
	public int compareTo(Libro libro) {
		return titolo.compareTo(libro.getTitolo());
	}
	
	public boolean equals(Libro libro) throws Exception {
		if (ISBN==null || ISBN.isEmpty() ||libro.getISBN()==null || libro.getISBN().isEmpty()) {
			throw new Exception("Confronto fallito con "+libro+ ": uno dei due libri non ha l'ISBN");
		}
		return (ISBN.equals(libro.getISBN()));
	}
	
}

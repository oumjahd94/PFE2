package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The Class FondsDataRapport.	
 */
@Entity
@Table(name="fonds_data_rapport")
public class FondsDataRapport implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data rapport. */
	@Id
	@Column(name="id_fonds_rapport")
	private Long idFondsDataRapport;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche rapport. */
	@Column(name="fiche_rapport")
	private String ficheRapport;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration; 
	

	private Date dateRapport ; 
	
	private int anneeRapport ; 
	
	
	/**
	 * Gets the id fonds data rapport.
	 *
	 * @return the id fonds data rapport
	 */
	public Long getIdFondsDataRapport() {
		return idFondsDataRapport;
	}
	
	/**
	 * Sets the id fonds data rapport.
	 *
	 * @param idFondsDataRapport the new id fonds data rapport
	 */
	public void setIdFondsDataRapport(Long idFondsDataRapport) {
		this.idFondsDataRapport = idFondsDataRapport;
	}
	
	/**
	 * Gets the type credilog.
	 *
	 * @return the type credilog
	 */
	public TypeCredilog getTypeCredilog() {
		return typeCredilog;
	}
	
	/**
	 * Sets the type credilog.
	 *
	 * @param typeCredilog the new type credilog
	 */
	public void setTypeCredilog(TypeCredilog typeCredilog) {
		this.typeCredilog = typeCredilog;
	}
	
	/**
	 * Gets the fiche rapport.
	 *
	 * @return the fiche rapport
	 */
	public String getFicheRapport() {
		return ficheRapport;
	}
	
	/**
	 * Sets the fiche rapport.
	 *
	 * @param ficheRapport the new fiche rapport
	 */
	public void setFicheRapport(String ficheRapport) {
		this.ficheRapport = ficheRapport;
	}
	
	/**
	 * Gets the date publication.
	 *
	 * @return the date publication
	 */
	public Date getDatePublication() {
		return datePublication;
	}
	
	/**
	 * Sets the date publication.
	 *
	 * @param datePublication the new date publication
	 */
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	
	/**
	 * Gets the date expiration.
	 *@Table
	 * @return the date expiration
	 */
	public Date getDateExpiration() {
		return dateExpiration;
	}
	
	/**
	 * Sets the date expiration.
	 *
	 * @param dateExpiration the new date expiration
	 */
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	
	/**
	 * Instantiates a new fonds data rapport.
	 */
	public FondsDataRapport() {
		super();
	}

	public Date getDateRapport() {
		return dateRapport;
	}

	public void setDateRapport(Date dateRapport) {
		this.dateRapport = dateRapport;
	}

	public int getAnneeRapport() {
		return anneeRapport;
	}

	public void setAnneeRapport(int anneeRapport) {
		this.anneeRapport = anneeRapport;
	}
	
}

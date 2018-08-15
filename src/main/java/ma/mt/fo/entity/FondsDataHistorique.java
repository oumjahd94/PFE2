package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The Class FondsDataHistorique.
 */
@Entity
@Table(name="fonds_data_historique")
public class FondsDataHistorique implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data historique. */
	@Id
	@Column(name="id_historique_fonds")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idFondsDataHistorique;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche historique. */
	@Column(name="fiche_historique")
	private String ficheHistorique;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	
	/**
	 * Gets the id fonds data historique.
	 *
	 * @return the id fonds data historique
	 */
	public Long getIdFondsDataHistorique() {
		return idFondsDataHistorique;
	}
	
	/**
	 * Sets the id fonds data historique.
	 *
	 * @param idFondsDataHistorique the new id fonds data historique
	 */
	public void setIdFondsDataHistorique(Long idFondsDataHistorique) {
		this.idFondsDataHistorique = idFondsDataHistorique;
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
	 * Gets the fiche historique.
	 *
	 * @return the fiche historique
	 */
	public String getFicheHistorique() {
		return ficheHistorique;
	}
	
	/**
	 * Sets the fiche historique.
	 *
	 * @param ficheHistorique the new fiche historique
	 */
	public void setFicheHistorique(String ficheHistorique) {
		this.ficheHistorique = ficheHistorique;
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
	 *
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
	 * Instantiates a new fonds data historique.
	 */
	public FondsDataHistorique() {
		super();
	}
}

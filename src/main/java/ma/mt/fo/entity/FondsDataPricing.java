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
 * The Class FondsDataPricing.
 */
@Entity
@Table(name="fonds_data_pricing")
public class FondsDataPricing implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data pricing. */
	@Id
	@Column(name="id_fonds_data_pricing")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idFondsDataPricing;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche pricing. */
	@Column(name="fiche_pricing")
	private String fichePricing;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	
	/**
	 * Gets the id fonds data pricing.
	 *
	 * @return the id fonds data pricing
	 */
	public Long getIdFondsDataPricing() {
		return idFondsDataPricing;
	}
	
	/**
	 * Sets the id fonds data pricing.
	 *
	 * @param idFondsDataPricing the new id fonds data pricing
	 */
	public void setIdFondsDataPricing(Long idFondsDataPricing) {
		this.idFondsDataPricing = idFondsDataPricing;
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
	 * Gets the fiche pricing.
	 *
	 * @return the fiche pricing
	 */
	public String getFichePricing() {
		return fichePricing;
	}
	
	/**
	 * Sets the fiche pricing.
	 *
	 * @param fichePricing the new fiche pricing
	 */
	public void setFichePricing(String fichePricing) {
		this.fichePricing = fichePricing;
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
	 * Instantiates a new fonds data pricing.
	 */
	public FondsDataPricing() {
		super();
	}

}

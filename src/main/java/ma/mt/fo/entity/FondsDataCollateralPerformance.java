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
 * The Class FondsDataCollateralPerformance.
 */
@Entity
@Table(name="fonds_data_collateral_perf")
public class FondsDataCollateralPerformance implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data collateral perf. */
	@Id
	@Column(name="id_fonds_data_collateralperf")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idFondsDataCollateralPerf;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche collateral. */
	@Column(name="fiche_collateral")
	private String ficheCollateral;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	
	/**
	 * Gets the id fonds data collateral perf.
	 *
	 * @return the id fonds data collateral perf
	 */ 
	
	
	public FondsDataCollateralPerformance() {
		super();
	}
	
	
	public Long getIdFondsDataCollateralPerf() {
		return idFondsDataCollateralPerf;
	}
	
	/**
	 * Sets the id fonds data collateral perf.
	 *
	 * @param idFondsDataCollateralPerf the new id fonds data collateral perf
	 */
	public void setIdFondsDataCollateralPerf(Long idFondsDataCollateralPerf) {
		this.idFondsDataCollateralPerf = idFondsDataCollateralPerf;
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
	 * Gets the fiche collateral.
	 *
	 * @return the fiche collateral
	 */
	public String getFicheCollateral() {
		return ficheCollateral;
	}
	
	/**
	 * Sets the fiche collateral.
	 *
	 * @param ficheCollateral the new fiche collateral
	 */
	public void setFicheCollateral(String ficheCollateral) {
		this.ficheCollateral = ficheCollateral;
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
	

}

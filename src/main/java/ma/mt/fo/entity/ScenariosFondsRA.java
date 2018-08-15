package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The Class ScenariosFondsRa.
 */
@Entity
@Table(name="scenarios_fonds_ra")
public class ScenariosFondsRA implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id scenario fonds ra. */
	@Id
	@Column(name="id_scenario_ra")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idScenarioFondsRa;
	
	/** The value scenario fonds ra. */
	@Column(name="value_scenario_ra")
	private Double valueScenarioFondsRa;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The date creation. */
	@Column(name="datecreation_scenario_ra")
	private Date dateCreation;
	
	/** The date expiration. */
	@Column(name="dateexpiration_scenario_ra")
	private Date dateExpiration;
	
	/** The default tx. */
	@Column(name="default_tx")
	private boolean defaultTx;
	
	/** The echeancier previsionnel. */
	@JsonIgnore
	@OneToMany(mappedBy="scenarioFondsRa")
	private List<EcheancierPrevisionnel> echeancierPrevisionnel;
	/** The echeancier previsionnel. */
	@JsonIgnore
	@OneToMany(mappedBy="scenariosFondsRa")
	private List<FondsDataCashflow> fondsDataCashflow;
	private boolean flagDeleted = false;
	
	public boolean isFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(boolean flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the id scenario fonds ra.
	 *
	 * @return the id scenario fonds ra
	 */
	public Long getIdScenarioFondsRa() {
		return idScenarioFondsRa;
	}
	
	/**
	 * Sets the id scenario fonds ra.
	 *
	 * @param idScenarioFondsRa the new id scenario fonds ra
	 */
	public void setIdScenarioFondsRa(Long idScenarioFondsRa) {
		this.idScenarioFondsRa = idScenarioFondsRa;
	}
	
	/**
	 * Gets the value scenario fonds ra.
	 *
	 * @return the value scenario fonds ra
	 */
	public Double getValueScenarioFondsRa() {
		return valueScenarioFondsRa;
	}
	
	/**
	 * Sets the value scenario fonds ra.
	 *
	 * @param valueScenarioFondsRa the new value scenario fonds ra
	 */
	public void setValueScenarioFondsRa(Double valueScenarioFondsRa) {
		this.valueScenarioFondsRa = valueScenarioFondsRa;
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
	 * @param typeCredilogdefaultTx the new type credilog
	 */
	public void setTypeCredilog(TypeCredilog typeCredilog) {
		this.typeCredilog = typeCredilog;
	}
	
	/**
	 * Gets the date creation.
	 *
	 * @return the date creation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}
	
	/**
	 * Sets the date creation.
	 *
	 * @param dateCreation the new date creation
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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
	 * Checks if is default tx.
	 *
	 * @return true, if is default tx
	 */
	public boolean isDefaultTx() {
		return defaultTx;
	}
	
	/**
	 * Sets the default tx.
	 *
	 * @param defaultTx the new default tx
	 */
	public void setDefaultTx(boolean defaultTx) {
		this.defaultTx = defaultTx;
	}
	
	/**
	 * Instantiates a new scenarios fonds ra.
	 */
	public ScenariosFondsRA() {
		super();
	}

	/**
	 * Gets the echeancier previsionnel.
	 *
	 * @return the echeancier previsionnel
	 */
	public List<EcheancierPrevisionnel> getEcheancierPrevisionnel() {
		return echeancierPrevisionnel;
	}

	/**
	 * Sets the echeancier previsionnel.
	 *
	 * @param echeancierPrevisionnel the new echeancier previsionnel
	 */
	public void setEcheancierPrevisionnel(List<EcheancierPrevisionnel> echeancierPrevisionnel) {
		this.echeancierPrevisionnel = echeancierPrevisionnel;
	}

	/**
	 * Gets the fonds data cashflow.
	 *
	 * @return the fonds data cashflow
	 */
	public List<FondsDataCashflow> getFondsDataCashflow() {
		return fondsDataCashflow;
	}

	/**
	 * Sets the fonds data cashflow.
	 *
	 * @param fondsDataCashflow the new fonds data cashflow
	 */
	public void setFondsDataCashflow(List<FondsDataCashflow> fondsDataCashflow) {
		this.fondsDataCashflow = fondsDataCashflow;
	}
}

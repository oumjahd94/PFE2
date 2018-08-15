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
 * The Class ScenariosFondsDDT.
 */
@Entity
@Table(name = "scenarios_fonds_ddt")
public class ScenariosFondsDDT implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id scenario ddt. */
	@Id
	@Column(name = "id_scenario_ddt")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idScenarioDdt;
	
	/** The value scenario. */
	@Column(name = "valeur_scenario_ddt")
	private Double valueScenario;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	/** The date creation ddt. */
	@Column(name = "date_creation_ddt")
	private Date dateCreationDdt;
	
	/** The Date expiration ddt. */
	@Column(name = "date_expiration_ddt")
	private Date DateExpirationDdt;
	
	/** The default tx. */
	@Column(name = "default_tx")
	private boolean defaultTx;
	
	/** The echeancier previsionnels. */
	@JsonIgnore
	@OneToMany(mappedBy="scenarioFondsDdt")
	private List<EcheancierPrevisionnel> echeancierPrevisionnels;
	/** The echeancier previsionnels. */
	@JsonIgnore
	@OneToMany(mappedBy="scenarioFondsDdt")
	private List<FondsDataCashflow> fondsDataCashflow;  
	
	private boolean flagDeleted;
	
	public List<FondsDataCashflow> getFondsDataCashflow() {
		return fondsDataCashflow;
	}

	public void setFondsDataCashflow(List<FondsDataCashflow> fondsDataCashflow) {
		this.fondsDataCashflow = fondsDataCashflow;
	}

	public boolean isFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(boolean flagDeleted) {
		this.flagDeleted = flagDeleted;
	}

	/**
	 * Gets the id scenario ddt.
	 *
	 * @return the id scenario ddt
	 */
	public Long getIdScenarioDdt() {
		return idScenarioDdt;
	}
	
	/**
	 * Sets the id scenario ddt.
	 *
	 * @param idScenarioDdt the new id scenario ddt
	 */
	public void setIdScenarioDdt(Long idScenarioDdt) {
		this.idScenarioDdt = idScenarioDdt;
	}
	
	/**
	 * Gets the value scenario.
	 *
	 * @return the value scenario
	 */
	public Double getValueScenario() {
		return valueScenario;
	}
	
	/**
	 * Sets the value scenario.
	 *
	 * @param valueScenario the new value scenario
	 */
	public void setValueScenario(Double valueScenario) {
		this.valueScenario = valueScenario;
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
	 * Gets the date creation ddt.
	 *
	 * @return the date creation ddt// TODO: Auto-generated Javadoc
	 */
	public Date getDateCreationDdt() {
		return dateCreationDdt;
	}
	
	/**
	 * Sets the date creation ddt.
	 *
	 * @param dateCreationDdt the new date creation ddt
	 */
	public void setDateCreationDdt(Date dateCreationDdt) {
		this.dateCreationDdt = dateCreationDdt;
	}
	
	/**
	 * Gets the date expiration ddt.
	 *
	 * @return the date expiration ddt
	 */
	public Date getDateExpirationDdt() {
		return DateExpirationDdt;
	}
	
	/**
	 * Sets the date expiration ddt.
	 *
	 * @param dateExpirationDdt the new date expiration ddt
	 */
	public void setDateExpirationDdt(Date dateExpirationDdt) {
		DateExpirationDdt = dateExpirationDdt;
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
	 * Instantiates a new scenarios fonds DDT.
	 */
	public ScenariosFondsDDT() {
		super();
	}

	/**
	 * Gets the echeancier previsionnels.
	 *
	 * @return the echeancier previsionnels
	 */
	public List<EcheancierPrevisionnel> getEcheancierPrevisionnels() {
		return echeancierPrevisionnels;
	}

	/**
	 * Sets the echeancier previsionnels.
	 *
	 * @param echeancierPrevisionnels the new echeancier previsionnels
	 */
	public void setEcheancierPrevisionnels(List<EcheancierPrevisionnel> echeancierPrevisionnels) {
		this.echeancierPrevisionnels = echeancierPrevisionnels;
	}
	
}

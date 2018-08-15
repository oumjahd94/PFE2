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
 * The Class FondsDataCashflow.
 */
@Entity
@Table(name="fonds_data_cashflow")
public class FondsDataCashflow implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data cashflow. */
	@Id
	@Column(name="id_fonds_data_cashflow")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idFondsDataCashflow;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche cashflow fonds. */
	@Column(name="fiche_cashflow_fonds")
	private String ficheCashflowFonds;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	
	/** The scenario fonds ddt. */
	@ManyToOne
	private ScenariosFondsDDT scenarioFondsDdt;
	
	/** The scenarios fonds ra. */
	@ManyToOne
	private ScenariosFondsRA scenariosFondsRa;
	
	/**
	 * Gets the id fonds data cashflow.
	 *
	 * @return the id fonds data cashflow
	 */
	public Long getIdFondsDataCashflow() {
		return idFondsDataCashflow;
	}
	
	/**
	 * Sets the id fonds data cashflow.
	 *
	 * @param idFondsDataCashflow the new id fonds data cashflow
	 */
	public void setIdFondsDataCashflow(Long idFondsDataCashflow) {
		this.idFondsDataCashflow = idFondsDataCashflow;
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
	 * Gets the fiche cashflow fonds.
	 *
	 * @return the fiche cashflow fonds
	 */
	public String getFicheCashflowFonds() {
		return ficheCashflowFonds;
	}
	
	/**
	 * Sets the fiche cashflow fonds.
	 *
	 * @param ficheCashflowFonds the new fiche cashflow fonds
	 */
	public void setFicheCashflowFonds(String ficheCashflowFonds) {
		this.ficheCashflowFonds = ficheCashflowFonds;
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
	 * Gets the scenario fonds ddt.
	 *
	 * @return the scenario fonds ddt
	 */
	public ScenariosFondsDDT getScenarioFondsDdt() {
		return scenarioFondsDdt;
	}
	
	/**
	 * Sets the scenario fonds ddt.
	 *
	 * @param scenarioFondsDdt the new scenario fonds ddt
	 */
	public void setScenarioFondsDdt(ScenariosFondsDDT scenarioFondsDdt) {
		this.scenarioFondsDdt = scenarioFondsDdt;
	}
	
	/**
	 * Gets the scenarios fonds ra.
	 *
	 * @return the scenarios fonds ra
	 */
	public ScenariosFondsRA getScenariosFondsRa() {
		return scenariosFondsRa;
	}
	
	/**
	 * Sets the scenarios fonds ra.
	 *
	 * @param scenariosFondsRa the new scenarios fonds ra
	 */
	public void setScenariosFondsRa(ScenariosFondsRA scenariosFondsRa) {
		this.scenariosFondsRa = scenariosFondsRa;
	}
	
	/**
	 * Instantiates a new fonds data cashflow.
	 */
	public FondsDataCashflow() {
		super();
	}
	
}

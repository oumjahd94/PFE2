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
 * The Class EcheancierPrevisionnel.
 */
@Entity
@Table(name="echeancier_previsionnel")
public class EcheancierPrevisionnel implements Serializable{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id echeancier previsionnel. */
	@Id
	@Column(name="id_echeancier_previsionnel")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idEcheancierPrevisionnel;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The part P 1. */
	@Column(name="part_p1")
	private String partP1;
	
	/** The part P 2. */	
	@Column(name="part_p2")
	private String partP2; 
	
	/** The part S. */
	@Column(name="part_s")
	private String partS;
	
	/** The scenario fonds ddt. */
	@ManyToOne
	private ScenariosFondsDDT scenarioFondsDdt;
	
	/** The scenario fonds ra. */
	@ManyToOne
	private ScenariosFondsRA scenarioFondsRa;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	/**
	 * Gets the id echeancier previsionnel.
	 *
	 * @return the id echeancier previsionnel
	 */
	public Long getIdEcheancierPrevisionnel() {
		return idEcheancierPrevisionnel;
	}
	
	/**
	 * Sets the id echeancier previsionnel.
	 *
	 * @param idEcheancierPrevisionnel the new id echeancier previsionnel
	 */
	public void setIdEcheancierPrevisionnel(Long idEcheancierPrevisionnel) {
		this.idEcheancierPrevisionnel = idEcheancierPrevisionnel;
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
	 * Gets the part P 1.
	 *
	 * @return the part P 1
	 */
	public String getPartP1() {
		return partP1;
	}
	
	/**
	 * Sets the part P 1.
	 *
	 * @param partP1 the new part P 1
	 */
	public void setPartP1(String partP1) {
		this.partP1 = partP1;
	}
	
	/**
	 * Gets the part P 2.
	 *
	 * @return the part P 2
	 */
	public String getPartP2() {
		return partP2;
	}
	
	/**
	 * Sets the part P 2.
	 *
	 * @param partP2 the new part P 2
	 */
	public void setPartP2(String partP2) {
		this.partP2 = partP2;
	}
	
	/**
	 * Gets the part S.
	 *
	 * @return the part S
	 */
	public String getPartS() {
		return partS;
	}
	
	/**
	 * Sets the part S.
	 *
	 * @param partS the new part S
	 */
	public void setPartS(String partS) {
		this.partS = partS;
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
	 * Gets the scenario fonds ra.
	 *
	 * @return the scenario fonds ra
	 */
	public ScenariosFondsRA getScenarioFondsRa() {
		return scenarioFondsRa;
	}
	
	/**
	 * Sets the scenario fonds ra.
	 *
	 * @param scenarioFondsRa the new scenario fonds ra
	 */
	public void setScenarioFondsRa(ScenariosFondsRA scenarioFondsRa) {
		this.scenarioFondsRa = scenarioFondsRa;
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
	 * Instantiates a new echeancier previsionnel.
	 */
	public EcheancierPrevisionnel() {
		super();
	}
	
}

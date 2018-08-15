package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The Class TypeCredilog.
 */
@Entity
@Table(name = "type_credilog")
public class TypeCredilog implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id type credilog. */
	@Id
	@Column(name = "id_type_credilog")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTypeCredilog;

	/** The entitled type credilog. */
	@Column(name = "int_type_credilog")
	private String entitledTypeCredilog;

	/** The fiche presentation. */
	@Column(name = "fiche_presentation")
	private String fichePresentation;

	/** The fonds data snapshot. */
	@OneToMany(mappedBy = "typeCredilog")
	private List<FondsDataSnapshot> fondsDataSnapshot;
   
	
	/**  **/ 
//	@OneToMany(mappedBy = "typeCredilog")
//	private List<Roles> role_u; 
//	
//	public List<Roles> getRole_u() {
//		return role_u;
//	}
//
//	public void setRole_u(List<Roles> role_u) {
//		this.role_u = role_u;
//	}

	/** The courbe taux acturials. */
	@OneToMany(mappedBy = "typeCredilog")
	private List<CourbeTauxAc> courbeTauxActurials;

	/** The scenarios fonds ddt. */
	@OneToMany(mappedBy = "typeCredilog")
	private List<ScenariosFondsDDT> scenariosFondsDdt;
	/** The scenarios fonds ddt. */
	@OneToMany(mappedBy = "typeCredilog")
	private List<ScenariosFondsRA> scenariosFondsRa;
	
	/** The echeancier previsionnels. */
	@OneToMany(mappedBy = "typeCredilog")
	private List<EcheancierPrevisionnel> echeancierPrevisionnels;
	
	/** The fonds data historique. */
	@OneToMany(mappedBy="typeCredilog")
	private List<FondsDataHistorique> fondsDataHistorique;
	
	/** The fonds data collateral performance. */
	@OneToMany(mappedBy="typeCredilog")
	private List<FondsDataCollateralPerformance> fondsDataCollateralPerformance;
	
	/** The fonds data pricing. */
	@OneToMany(mappedBy="typeCredilog")
	private List<FondsDataPricing> fondsDataPricing;
	
	/** The fonds data rapport. */
	@OneToMany(mappedBy="typeCredilog")
	private List<FondsDataRapport> fondsDataRapport;
	/**
	 * Gets the id type credilog.
	 *
	 * @return the id type credilog
	 */
	public Long getIdTypeCredilog() {
		return idTypeCredilog;
	}

	/**
	 * Sets the id type credilog.
	 *
	 * @param idTypeCredilog
	 *            the new id type credilog
	 */
	public void setIdTypeCredilog(Long idTypeCredilog) {
		this.idTypeCredilog = idTypeCredilog;
	}

	/**
	 * Gets the entitled type credilog.
	 *
	 * @return the entitled type credilog
	 */
	public String getEntitledTypeCredilog() {
		return entitledTypeCredilog;
	}	

	/**
	 * Sets the entitled type credilog.
	 *
	 * @param entitledTypeCredilog
	 *            the new entitled type credilog
	 */
	public void setEntitledTypeCredilog(String entitledTypeCredilog) {
		this.entitledTypeCredilog = entitledTypeCredilog;
	}

	/**
	 * Gets the fiche presentation.
	 *
	 * @return the fiche presentation
	 */
	public String getFichePresentation() {
		return fichePresentation;
	}

	/**
	 * Sets the fiche presentation.
	 *
	 * @param fichePresentation
	 *            the new fiche presentation
	 */
	public void setFichePresentation(String fichePresentation) {
		this.fichePresentation = fichePresentation;
	}

	/**
	 * Instantiates a new type credilog.
	 */
	public TypeCredilog() {
		super();
	}

	/**
	 * Gets the fonds data snapshot.
	 *
	 * @return the fonds data snapshot
	 */
	@JsonIgnore
	public List<FondsDataSnapshot> getFondsDataSnapshot() {
		return fondsDataSnapshot;
	}

	/**
	 * Sets the fonds data snapshot.
	 *
	 * @param fondsDataSnapshot
	 *            the new fonds data snapshot
	 */
	public void setFondsDataSnapshot(List<FondsDataSnapshot> fondsDataSnapshot) {
		this.fondsDataSnapshot = fondsDataSnapshot;
	}

	/**
	 * Gets the courbe taux acturials.
	 *
	 * @return the courbe taux acturials
	 */
	@JsonIgnore
	public List<CourbeTauxAc> getCourbeTauxActurials() {
		return courbeTauxActurials;
	}

	/**
	 * Sets the courbe taux acturials.
	 *
	 * @param courbeTauxActurials
	 *            the new courbe taux acturials
	 */
	public void setCourbeTauxActurials(List<CourbeTauxAc> courbeTauxActurials) {
		this.courbeTauxActurials = courbeTauxActurials;
	}

	/**
	 * Gets the scenarios fonds ddt.
	 *
	 * @return the scenarios fonds ddt
	 */
	@JsonIgnore
	public List<ScenariosFondsDDT> getScenariosFondsDdt() {
		return scenariosFondsDdt;
	}

	/**
	 * Sets the scenarios fonds ddt.
	 *
	 * @param scenariosFondsDdt
	 *            the new scenarios fonds ddt
	 */
	public void setScenariosFondsDdt(List<ScenariosFondsDDT> scenariosFondsDdt) {
		this.scenariosFondsDdt = scenariosFondsDdt;
	}

	/**
	 * Gets the scenarios fonds ra.
	 *
	 * @return the scenarios fonds ra
	 */
	@JsonIgnore
	public List<ScenariosFondsRA> getScenariosFondsRa() {
		return scenariosFondsRa;
	}

	/**
	 * Sets the scenarios fonds ra.
	 *
	 * @param scenariosFondsRa
	 *            the new scenarios fonds ra
	 */
	public void setScenariosFondsRa(List<ScenariosFondsRA> scenariosFondsRa) {
		this.scenariosFondsRa = scenariosFondsRa;
	}

	/**
	 * Gets the echeancier previsionnels.
	 *
	 * @return the echeancier previsionnels
	 */
	@JsonIgnore
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

	/**
	 * Gets the fonds data historique.
	 *
	 * @return the fonds data historique
	 */
	@JsonIgnore
	public List<FondsDataHistorique> getFondsDataHistorique() {
		return fondsDataHistorique;
	}

	/**
	 * Sets the fonds data historique.
	 *
	 * @param fondsDataHistorique the new fonds data historique
	 */
	public void setFondsDataHistorique(List<FondsDataHistorique> fondsDataHistorique) {
		this.fondsDataHistorique = fondsDataHistorique;
	}

	/**
	 * Gets the fonds data collateral performance.
	 *
	 * @return the fonds data collateral performance
	 */
	@JsonIgnore
	public List<FondsDataCollateralPerformance> getFondsDataCollateralPerformance() {
		return fondsDataCollateralPerformance;
	}

	/**
	 * Sets the fonds data collateral performance.
	 *
	 * @param fondsDataCollateralPerformance the new fonds data collateral performance
	 */
	public void setFondsDataCollateralPerformance(List<FondsDataCollateralPerformance> fondsDataCollateralPerformance) {
		this.fondsDataCollateralPerformance = fondsDataCollateralPerformance;
	}

	/**
	 * Gets the fonds data pricing.
	 *
	 * @return the fonds data pricing
	 */
	@JsonIgnore
	public List<FondsDataPricing> getFondsDataPricing() {
		return fondsDataPricing;
	}

	/**
	 * Sets the fonds data pricing.
	 *
	 * @param fondsDataPricing the new fonds data pricing
	 */
	public void setFondsDataPricing(List<FondsDataPricing> fondsDataPricing) {
		this.fondsDataPricing = fondsDataPricing;
	}

	/**
	 * Gets the fonds data rapport.
	 *
	 * @return the fonds data rapport
	 */
	@JsonIgnore
	public List<FondsDataRapport> getFondsDataRapport() {
		return fondsDataRapport;
	}

	/**
	 * Sets the fonds data rapport.
	 *
	 * @param fondsDataRapport the new fonds data rapport
	 */
	public void setFondsDataRapport(List<FondsDataRapport> fondsDataRapport) {
		this.fondsDataRapport = fondsDataRapport;
	}
	
}

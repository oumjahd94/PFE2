package ma.mt.fo.util;

import java.util.List;
import java.util.Vector;

import ma.mt.fo.entity.ScenariosFondsDDT;
import ma.mt.fo.entity.ScenariosFondsRA;

/**
 * The Class HelpPricing.
 */
public class HelpPricing { 
	
	private int action;
	private String dateIni;
	private String dateFin;
	private Long idScenarioRA;
	private Long idScenarioDDT;
	private Long defaultScenariosFondsRa;
	private Long defaultScenariosFondsDDT;     
	
	
//  Déclaration d'autres champs  
	
	private Long idTypeCredilog; 
	private List<ScenariosFondsRA> listScenarioRA;
	private List<ScenariosFondsDDT> listScenarioDDT;   
	
	
	private String idClass;
	/** The taux actuarial 13 S. */
	private Double tauxActuarial13S;

	/** The taux actuarial 26 S. */
	private Double tauxActuarial26S;

	/** The taux actuarial 52 S. */
	private Double tauxActuarial52S;

	/** The taux actuarial 2 A. */
	private Double tauxActuarial2A;

	/** The taux actuarial 5 A. */
	private Double tauxActuarial5A;

	/** The taux actuarial 10 A. */
	private Double tauxActuarial10A;

	/** The taux actuarial 15 A. */
	private Double tauxActuarial15A;

	/** The taux actuarial 20 A. */
	private Double tauxActuarial20A;

	/** The taux actuarial 30 A. */
	private Double tauxActuarial30A;

	/** The prime esperee A. */
	private Double primeEspereeA;

	/** The prime esperee B. */
	private Double primeEspereeB;

	/** The prime esperee S. */
	private Double primeEspereeS;

	/** The date T ransaction. */
	private String dateTRansaction;

	/** The fichier part 1 pricing. */
	private String fichierPart1Pricing;

	/** The fichier part 2 pricing. */
	private String fichierPart2Pricing;

	/** The fichier S pricing. */
	private String fichierSPricing;

	/** The my vector date. */
	private Vector myVectorDate;

	/** The date depart P 1. */
	private String dateDepartP1 = "";

	/** The date depart P 2. */
	private String dateDepartP2 = "";

	/** The date depart S. */
	private String dateDepartS = "";

	/** The date fin P 1. */
	private String dateFinP1 = "";

	/** The date fin P 2. */
	private String dateFinP2 = "";

	/** The date fin S. */
	private String dateFinS = "";

	/** The my vector P 1. */
	Vector myVectorP1 = null;

	/** The my vector P 2. */
	Vector myVectorP2 = null;

	/** The my vector S. */  
	
	Vector myVectorS = null; 
	
	Vector<Vector<String>> vecCashFlow = new Vector<Vector<String>>(); 
	
	/** The message. */
	String message = "";

	/** The date transaction. */
	String dateTransaction = "valeur par défaut";

	/** The fichier excel. */
	private String fichierExcel;

	/** The vec pricer. */
	private Vector<Vector<String>> vecPricer = new Vector<Vector<String>>();

	
	// Getters et Setters 
	
	public Long getDefaultScenariosFondsRa() {
		return defaultScenariosFondsRa;
	}

	public void setDefaultScenariosFondsRa(Long defaultScenariosFondsRa) {
		this.defaultScenariosFondsRa = defaultScenariosFondsRa;
	}

	public Long getDefaultScenariosFondsDDT() {
		return defaultScenariosFondsDDT;
	}

	public void setDefaultScenariosFondsDDT(Long defaultScenariosFondsDDT) {
		this.defaultScenariosFondsDDT = defaultScenariosFondsDDT;
	}

	public Long getIdScenarioRA() {
		return idScenarioRA;
	}

	public void setIdScenarioRA(Long idScenarioRA) {
		this.idScenarioRA = idScenarioRA;
	}

	public Long getIdScenarioDDT() {
		return idScenarioDDT;
	}

	public void setIdScenarioDDT(Long idScenarioDDT) {
		this.idScenarioDDT = idScenarioDDT;
	}

	public String getDateFin() {
		return dateFin;
	}

	
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getDateIni() {
		return dateIni;
	}

	public void setDateIni(String dateIni) {
		this.dateIni = dateIni;
	}


	public List<ScenariosFondsRA> getListScenarioRA() {
		return listScenarioRA;
	}

	public void setListScenarioRA(List<ScenariosFondsRA> listScenarioRA) {
		this.listScenarioRA = listScenarioRA;
	}

	public List<ScenariosFondsDDT> getListScenarioDDT() {
		return listScenarioDDT;
	}

	public void setListScenarioDDT(List<ScenariosFondsDDT> listScenarioDDT) {
		this.listScenarioDDT = listScenarioDDT;
	}

	public Long getIdTypeCredilog() {
		return idTypeCredilog;
	}

	public void setIdTypeCredilog(Long idTypeCredilog) {
		this.idTypeCredilog = idTypeCredilog;
	}

	public String getIdClass() {
		return idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	

	public Vector<Vector<String>> getVecCashFlow() {
		return vecCashFlow;
	}

	public void setVecCashFlow(Vector<Vector<String>> vecCashFlow) {
		this.vecCashFlow = vecCashFlow;
	}


	/**
	 * Gets the my vector date.
	 *
	 * @return the my vector date
	 */
	public Vector getMyVectorDate() {
		return myVectorDate;
	}

	/**
	 * Sets the my vector date.
	 *
	 * @param myVectorDate
	 *            the new my vector date
	 */
	public void setMyVectorDate(Vector myVectorDate) {
		this.myVectorDate = myVectorDate;
	}
	/**
	 * Gets the taux actuarial 13 S.
	 *
	 * @return the taux actuarial 13 S
	 */
	public Double getTauxActuarial13S() {
		return tauxActuarial13S;
	}

	/**
	 * Sets the taux actuarial 13 S.
	 *
	 * @param tauxActuarial13S
	 *            the new taux actuarial 13 S
	 */
	public void setTauxActuarial13S(Double tauxActuarial13S) {
		this.tauxActuarial13S = tauxActuarial13S;
	}

	/**
	 * Gets the taux actuarial 26 S.
	 *
	 * @return the taux actuarial 26 S
	 */
	public Double getTauxActuarial26S() {
		return tauxActuarial26S;
	}

	/**
	 * Sets the taux actuarial 26 S.
	 *
	 * @param tauxActuarial26S
	 *            the new taux actuarial 26 S
	 */
	public void setTauxActuarial26S(Double tauxActuarial26S) {
		this.tauxActuarial26S = tauxActuarial26S;
	}

	/**
	 * Gets the taux actuarial 52 S.
	 *
	 * @return the taux actuarial 52 S
	 */
	public Double getTauxActuarial52S() {
		return tauxActuarial52S;
	}

	/**
	 * Sets the taux actuarial 52 S.
	 *
	 * @param tauxActuarial52S
	 *            the new taux actuarial 52 S
	 */
	public void setTauxActuarial52S(Double tauxActuarial52S) {
		this.tauxActuarial52S = tauxActuarial52S;
	}

	/**
	 * Gets the taux actuarial 2 A.
	 *
	 * @return the taux actuarial 2 A
	 */
	public Double getTauxActuarial2A() {
		return tauxActuarial2A;
	}

	/**
	 * Sets the taux actuarial 2 A.
	 *
	 * @param tauxActuarial2A
	 *            the new taux actuarial 2 A
	 */
	public void setTauxActuarial2A(Double tauxActuarial2A) {
		this.tauxActuarial2A = tauxActuarial2A;
	}

	/**
	 * Gets the taux actuarial 5 A.
	 *
	 * @return the taux actuarial 5 A
	 */
	public Double getTauxActuarial5A() {
		return tauxActuarial5A;
	}

	/**
	 * Sets the taux actuarial 5 A.
	 *
	 * @param tauxActuarial5A
	 *            the new taux actuarial 5 A
	 */
	public void setTauxActuarial5A(Double tauxActuarial5A) {
		this.tauxActuarial5A = tauxActuarial5A;
	}

	/**
	 * Gets the taux actuarial 10 A.
	 *
	 * @return the taux actuarial 10 A
	 */
	public Double getTauxActuarial10A() {
		return tauxActuarial10A;
	}

	/**
	 * Sets the taux actuarial 10 A.
	 *
	 * @param tauxActuarial10A
	 *            the new taux actuarial 10 A
	 */
	public void setTauxActuarial10A(Double tauxActuarial10A) {
		this.tauxActuarial10A = tauxActuarial10A;
	}

	/**
	 * Gets the taux actuarial 15 A.
	 *
	 * @return the taux actuarial 15 A
	 */
	public Double getTauxActuarial15A() {
		return tauxActuarial15A;
	}

	/**
	 * Sets the taux actuarial 15 A.
	 *
	 * @param tauxActuarial15A
	 *            the new taux actuarial 15 A
	 */
	public void setTauxActuarial15A(Double tauxActuarial15A) {
		this.tauxActuarial15A = tauxActuarial15A;
	}

	/**
	 * Gets the taux actuarial 20 A.
	 *
	 * @return the taux actuarial 20 A
	 */
	public Double getTauxActuarial20A() {
		return tauxActuarial20A;
	}

	/**
	 * Sets the taux actuarial 20 A.
	 *
	 * @param tauxActuarial20A
	 *            the new taux actuarial 20 A
	 */
	public void setTauxActuarial20A(Double tauxActuarial20A) {
		this.tauxActuarial20A = tauxActuarial20A;
	}

	/**
	 * Gets the taux actuarial 30 A.
	 *
	 * @return the taux actuarial 30 A
	 */
	public Double getTauxActuarial30A() {
		return tauxActuarial30A;
	}

	/**
	 * Sets the taux actuarial 30 A.
	 *
	 * @param tauxActuarial30A
	 *            the new taux actuarial 30 A
	 */
	public void setTauxActuarial30A(Double tauxActuarial30A) {
		this.tauxActuarial30A = tauxActuarial30A;
	}

	/**
	 * Gets the prime esperee A.
	 *
	 * @return the prime esperee A
	 */
	public Double getPrimeEspereeA() {
		return primeEspereeA;
	}

	/**
	 * Sets the prime esperee A.
	 *
	 * @param primeEspereeA
	 *            the new prime esperee A
	 */
	public void setPrimeEspereeA(Double primeEspereeA) {
		this.primeEspereeA = primeEspereeA;
	}

	/**
	 * Gets the prime esperee B.
	 *
	 * @return the prime esperee B
	 */
	public Double getPrimeEspereeB() {
		return primeEspereeB;
	}

	/**
	 * Sets the prime esperee B.
	 *
	 * @param primeEspereeB
	 *            the new prime esperee B
	 */
	public void setPrimeEspereeB(Double primeEspereeB) {
		this.primeEspereeB = primeEspereeB;
	}

	/**
	 * Gets the prime esperee S.
	 *
	 * @return the prime esperee S
	 */
	public Double getPrimeEspereeS() {
		return primeEspereeS;
	}

	/**
	 * Sets the prime esperee S.
	 *
	 * @param primeEspereeS
	 *            the new prime esperee S
	 */
	public void setPrimeEspereeS(Double primeEspereeS) {
		this.primeEspereeS = primeEspereeS;
	}

	/**
	 * Gets the date T ransaction.
	 *
	 * @return the date T ransaction
	 */
	public String getDateTRansaction() {
		return dateTRansaction;
	}

	/**
	 * Sets the date T ransaction.
	 *
	 * @param dateTRansaction
	 *            the new date T ransaction
	 */
	public void setDateTRansaction(String dateTRansaction) {
		this.dateTRansaction = dateTRansaction;
	}

	/**
	 * Gets the fichier part 1 pricing.
	 *
	 * @return the fichier part 1 pricing
	 */
	public String getFichierPart1Pricing() {
		return fichierPart1Pricing;
	}

	/**
	 * Sets the fichier part 1 pricing.
	 *
	 * @param fichierPart1Pricing
	 *            the new fichier part 1 pricing
	 */
	public void setFichierPart1Pricing(String fichierPart1Pricing) {
		this.fichierPart1Pricing = fichierPart1Pricing;
	}

	/**
	 * Gets the fichier part 2 pricing.
	 *
	 * @return the fichier part 2 pricing
	 */
	public String getFichierPart2Pricing() {
		return fichierPart2Pricing;
	}

	/**
	 * Sets the fichier part 2 pricing.
	 *
	 * @param fichierPart2Pricing
	 *            the new fichier part 2 pricing
	 */
	public void setFichierPart2Pricing(String fichierPart2Pricing) {
		this.fichierPart2Pricing = fichierPart2Pricing;
	}

	/**
	 * Gets the fichier S pricing.
	 *
	 * @return the fichier S pricing
	 */
	public String getFichierSPricing() {
		return fichierSPricing;
	}

	/**
	 * Sets the fichier S pricing.
	 *
	 * @param fichierSPricing
	 *            the new fichier S pricing
	 */
	public void setFichierSPricing(String fichierSPricing) {
		this.fichierSPricing = fichierSPricing;
	}

	/**
	 * Gets the date depart P 1.
	 *
	 * @return the date depart P 1
	 */
	public String getDateDepartP1() {
		return dateDepartP1;
	}

	/**
	 * Sets the date depart P 1.
	 *
	 * @param dateDepartP1
	 *            the new date depart P 1
	 */
	public void setDateDepartP1(String dateDepartP1) {
		this.dateDepartP1 = dateDepartP1;
	}

	/**
	 * Gets the date depart P 2.
	 *
	 * @return the date depart P 2
	 */
	public String getDateDepartP2() {
		return dateDepartP2;
	}

	/**
	 * Sets the date depart P 2.
	 *
	 * @param dateDepartP2
	 *            the new date depart P 2
	 */
	public void setDateDepartP2(String dateDepartP2) {
		this.dateDepartP2 = dateDepartP2;
	}

	/**
	 * Gets the date depart S.
	 *
	 * @return the date depart S
	 */
	public String getDateDepartS() {
		return dateDepartS;
	}

	/**
	 * Sets the date depart S.
	 *
	 * @param dateDepartS
	 *            the new date depart S
	 */
	public void setDateDepartS(String dateDepartS) {
		this.dateDepartS = dateDepartS;
	}

	/**
	 * Gets the date fin P 1.
	 *
	 * @return the date fin P 1
	 */
	public String getDateFinP1() {
		return dateFinP1;
	}

	/**
	 * Sets the date fin P 1.
	 *
	 * @param dateFinP1
	 *            the new date fin P 1
	 */
	public void setDateFinP1(String dateFinP1) {
		this.dateFinP1 = dateFinP1;
	}

	/**
	 * Gets the date fin P 2.
	 *
	 * @return the date fin P 2
	 */
	public String getDateFinP2() {
		return dateFinP2;
	}

	/**
	 * Sets the date fin P 2.
	 *
	 * @param dateFinP2
	 *            the new date fin P 2
	 */
	public void setDateFinP2(String dateFinP2) {
		this.dateFinP2 = dateFinP2;
	}

	/**
	 * Gets the date fin S.
	 *
	 * @return the date fin S
	 */
	public String getDateFinS() {
		return dateFinS;
	}

	/**
	 * Sets the date fin S.
	 *
	 * @param dateFinS
	 *            the new date fin S
	 */
	public void setDateFinS(String dateFinS) {
		this.dateFinS = dateFinS;
	}

	/**
	 * Gets the my vector P 1.
	 *
	 * @return the my vector P 1
	 */
	public Vector getMyVectorP1() {
		return myVectorP1;
	}

	/**
	 * Sets the my vector P 1.
	 *
	 * @param myVectorP1
	 *            the new my vector P 1
	 */
	public void setMyVectorP1(Vector myVectorP1) {
		this.myVectorP1 = myVectorP1;
	}

	/**
	 * Gets the my vector P 2.
	 *
	 * @return the my vector P 2
	 */
	public Vector getMyVectorP2() {
		return myVectorP2;
	}

	/**
	 * Sets the my vector P 2.
	 *
	 * @param myVectorP2
	 *            the new my vector P 2
	 */
	public void setMyVectorP2(Vector myVectorP2) {
		this.myVectorP2 = myVectorP2;
	}

	/**
	 * Gets the my vector S.
	 *
	 * @return the my vector S
	 */
	public Vector getMyVectorS() {
		return myVectorS;
	}

	/**
	 * Sets the my vector S.
	 *
	 * @param myVectorS
	 *            the new my vector S
	 */
	public void setMyVectorS(Vector myVectorS) {
		this.myVectorS = myVectorS;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the date transaction.
	 *
	 * @return the date transaction
	 */
	public String getDateTransaction() {
		return dateTransaction;
	}

	/**
	 * Sets the date transaction.
	 *
	 * @param dateTransaction
	 *            the new date transaction
	 */
	public void setDateTransaction(String dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	/**
	 * Gets the fichier excel.
	 *
	 * @return the fichier excel
	 */
	public String getFichierExcel() {
		return fichierExcel;
	}

	/**
	 * Sets the fichier excel.
	 *
	 * @param fichierExcel
	 *            the new fichier excel
	 */
	public void setFichierExcel(String fichierExcel) {
		this.fichierExcel = fichierExcel;
	}

	/**
	 * Gets the vec pricer.
	 *
	 * @return the vec pricer
	 */
	public Vector<Vector<String>> getVecPricer() {
		return vecPricer;
	}

	/**
	 * Sets the vec pricer.
	 *
	 * @param vecPricer
	 *            the new vec pricer
	 */
	public void setVecPricer(Vector<Vector<String>> vecPricer) {
		this.vecPricer = vecPricer;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}

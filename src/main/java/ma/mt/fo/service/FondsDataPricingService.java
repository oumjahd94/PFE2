package ma.mt.fo.service;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import jnr.ffi.types.id_t;
*/
import ma.mt.fo.dao.EcheancierPrevisionnelJpaRepository;
import ma.mt.fo.dao.FondsDataCashflowJpaRepository;
import ma.mt.fo.dao.FondsDataPricingJpaRepository;
import ma.mt.fo.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.fo.dao.ScenariosFondsRAJpaRepository;
import ma.mt.fo.entity.EcheancierPrevisionnel;
import ma.mt.fo.entity.FondsDataCashflow;
import ma.mt.fo.entity.FondsDataPricing;
import ma.mt.fo.entity.ScenariosFondsDDT;
import ma.mt.fo.entity.ScenariosFondsRA;
import ma.mt.fo.service.interfaces.IFondsDataPricingService;
import ma.mt.fo.util.CollateralTraitement;
import ma.mt.fo.util.Constants;
import ma.mt.fo.util.HelpPricing;
import ma.mt.fo.util.LnetDate;
import ma.mt.fo.util.PricingProcess;
import ma.mt.fo.util.StringTokenizer;
import ma.mt.fo.util.xmlParser;

/**
 * The Class FondsDataPricingService.
 */
@Service
public class FondsDataPricingService implements IFondsDataPricingService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataPricingService.class);

	/** The fonds data pricing jpa repository. */
	@Autowired
	private FondsDataPricingJpaRepository fondsDataPricingJpaRepository;

	/** The scenarios fonds RA jpa repository. */
	@Autowired
	private ScenariosFondsRAJpaRepository scenariosFondsRAJpaRepository;

	/** The scenarios fonds DDT jpa repository. */
	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/** The echeancier previsionnel jpa repository. */
	@Autowired
	private EcheancierPrevisionnelJpaRepository echeancierPrevisionnelJpaRepository;

	/** The fonds data cashflow jpa repository. */
	@Autowired
	private FondsDataCashflowJpaRepository fondsDataCashflowJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataPricingService#listFondsDataPricing()
	 */
	@Override
	public List<FondsDataPricing> listFondsDataPricing() {
		try {
			return fondsDataPricingJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataPricingJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc) Envoyer un message
	 * 
	 * 
	 * @see
	 * ma.mt.fo.service.interfaces.IFondsDataPricingService#doPricing(java.lang.
	 * Long)
	 */

	@Override 
	public HelpPricing doPricing( 
		
			Long idTypeCredilog, 
			int action, 
			double scenarioRA,    
			double scenarioDDT,  
			String dtTrans,   
			double val1, double val2, double val3, double val4, double val5,
			double val6, double val7, double val8, double val9,
			double valA, double valB, double valC) {   
		
		
		HelpPricing helpPricing = new HelpPricing();
        		
        try { 
			 
        	System.out.println("LA DATE DE TRANSACTION =================> "+dtTrans);
			
			String idClass = "4";
			// la liste des matrices de pricer et cashflow 
			
			Vector<Vector<String>> vecPricer = new Vector<Vector<String>>();
			Vector<Vector<String>> vecCashFlow = new Vector<Vector<String>>(); 
			
			// Les dates de départ 
			String dateDepartP1 = "";
			String dateDepartP2 = "";
			String dateDepartS = ""; 
			 
			// Les dates fin  
			String dateFinP1 = "";
			String dateFinP2 = "";
			String dateFinS = "";
		
            // Définir des fichiers xml pour les parts p1, p2 et s
			String xmlPricingFileP1Path = "fpct_sakane/P1.xml";
			String xmlPricingFileP2Path = "fpct_sakane/P2.xml";
			String xmlPricingFileSPath = "fpct_sakane/S.xml";   

			// selectionner les scenarios de base RA
			ScenariosFondsRA scenariosFondsRA = 
					scenariosFondsRAJpaRepository.selectionDefaultsScenarioRA(idTypeCredilog);    
			
			// selectionner les scenarios de base DDT
			
			ScenariosFondsDDT scenariosFondsDDT = 
					scenariosFondsDDTJpaRepository.selectionDefaultsScenarioDDT(idTypeCredilog);    
			  			
	
			Long defaultScenariosFondsRa = null;
			Long idScenarioDDT = null;
			Long idScenarioRA = null;     
			
			
			// selectionner id of default scenarioDDT
			Long defaultScenarioFondsDDT=null; 
			
			// selectionner id of default scenarioRa and scenarioDdt
			if(scenariosFondsRA != null) {
				defaultScenariosFondsRa = scenariosFondsRA.getIdScenarioFondsRa();
				
				// mettre les scenarios de base// ou bien mettre les params si sont pas nulles
				idScenarioRA = defaultScenariosFondsRa;
			} 
			
			
			if(scenariosFondsDDT != null) {
				defaultScenarioFondsDDT = scenariosFondsDDT.getIdScenarioDdt();
				idScenarioDDT = defaultScenarioFondsDDT;
			} 
			
			// create messageScenario string variable
			String messageScenario = "";
			
			if (idScenarioRA.equals(defaultScenariosFondsRa) && idScenarioDDT.equals(defaultScenarioFondsDDT)) {
				messageScenario = "Scénario de la dernière date de paiement";
			} 
			
			// Récupérer la liste des scenario RA du Fonds
			List<ScenariosFondsRA> listScenarioRA = scenariosFondsRAJpaRepository.selectListRA(idTypeCredilog);  
					
			// Récupérer la liste des scenario DDT du Fonds
			List<ScenariosFondsDDT> listScenarioDDT = scenariosFondsDDTJpaRepository.selectListDDT(idTypeCredilog);
					
			// Selection de l'echeanciers courant du Fonds en cours 							
			EcheancierPrevisionnel currentEcheanciersPrevisionnels 
			= echeancierPrevisionnelJpaRepository
					.selectCurrentEcheanciers(idTypeCredilog, idScenarioRA, idScenarioDDT); 
						
			String dateTransaction = "";
			Vector myVectorP1 = null;
			Vector myVectorP2 = null;
			Vector myVectorS = null;
			Vector myVectorDate = null;
			String message = ""; 
						
			// TODO: onChange action parameter !!!
			
			if (action == 1) { 
								
				// set idScenarioRA
				idScenarioRA = defaultScenariosFondsRa;
				// set idScenarioDDT
				idScenarioDDT = defaultScenarioFondsDDT;
				// set dateTransaction 
				
				dateTransaction = "01/06/2018";     

				String fichierP1 = currentEcheanciersPrevisionnels.getPartP1(), 
					   fichierP2 = currentEcheanciersPrevisionnels.getPartP2(), 
					   fichierS  =  currentEcheanciersPrevisionnels.getPartS();  
				
				// check echeanciers filesvalueScenarioFondsRa
				if (       (fichierP1 != null)
						&& (fichierP2 != null)
						&& (fichierS  != null)) {  
					
					// oumjahd : créer des chemins vers  les fichiers xls de P1, P2 et P3
					String fichierExcelP1 = Constants.priceFondsDirectory + fichierP1;
					String fichierExcelP2 = Constants.priceFondsDirectory + fichierP2;
					String fichierExcelS  = Constants.priceFondsDirectory  + fichierS; 
					
					 // oumjahd : récupérer les fichiers xml de P1, P2 et P3
					String fichierXmlP1 = Constants.xmlPricingDirectory + xmlPricingFileP1Path;
					String fichierXmlP2 = Constants.xmlPricingDirectory + xmlPricingFileP2Path;
					String fichierXmlS  = Constants.xmlPricingDirectory + xmlPricingFileSPath;
                    
					
					// calculer le prix globale et le % du prix globale pour les 3 parts P1, P2 et S 
					myVectorP1 =							
							PricingProcess.calculPricingCreditlog3(
							        fichierExcelP1, 
							        fichierExcelP2, 
							        fichierXmlP1,
							        dtTrans,
							        val1, val2, val3, val4, val5, val6, val7, val8, val9,
							        valA); 
				
										
					myVectorP2 = 
							PricingProcess.calculPricingCreditlog3(
							fichierExcelP2,
							fichierExcelP2,
							fichierXmlP2,
							dtTrans, 
							val1, val2, val3, val4, val5, val6, val7, val8, val9,
					        valB); 
										
					
				  myVectorS = 
						  PricingProcess.calculPricingCreditlog3(
						    fichierExcelS, 
						    fichierExcelP2, 
						    fichierXmlS, 
						    dtTrans, 
						    val1, val2, val3, val4, val5, val6, val7, val8, val9,
					        valC);

					// sélectionner le fichier de pricing 
					FondsDataPricing selectFichier = fondsDataPricingJpaRepository.selectPricing(idTypeCredilog);
					String fichierPricing = selectFichier.getFichePricing();
					String cheminFichier = Constants.priceFondsDirectory;
                     
					
					// sélectionner le chemin de fichier de cashflow 
					String cheminCashflow = Constants.cashflowDirectory;
                    
					
					// lire le fichier de pricing 
					Map<Integer, Vector<Vector<String>>> dataPricing = 
							CollateralTraitement
							.readExcelFileSnapshot(cheminFichier.concat(fichierPricing));
					
					//Remplir la feuille N°1 
					vecPricer = dataPricing.get(0);

					// Recupération des dates pour la fenêtre d'amortissement
					String dateTr = "01/04/2012";  
										
					dateDepartP1 = PricingProcess.getFirstDate(fichierExcelP1, dateTr);  
					dateFinP1 = PricingProcess.getLastDateS(fichierExcelP1, dateTr);  
                    						
					dateDepartP2 = PricingProcess.getFirstDate(fichierExcelP2, dateTr);
					dateFinP2 = PricingProcess.getLastDateS(fichierExcelP2, dateTr);

					dateDepartS = dateDepartP2;
					dateFinS = PricingProcess.getLastDateS(fichierExcelS, dateTr);
							
					// Selection du CashFlow
					FondsDataCashflow fondsDataCashflow = 
							fondsDataCashflowJpaRepository
							.selectFondsDataCashFlow(idTypeCredilog, idScenarioRA, idScenarioDDT);
			
					String fichierCashflow = fondsDataCashflow.getFicheCashflowFonds(); 
										
					cheminCashflow = cheminCashflow.concat(fichierCashflow);
				
					
					// si le fichier existe  
					if (!"".equals(fichierCashflow)) { 
						
						Map<Integer, Vector<Vector<String>>> dataCashflow 
						= CollateralTraitement
								.readExcelFileCashflow(cheminCashflow);
						vecCashFlow = dataCashflow.get(0); 
						
					} else {
						message = "Les échéanciers des scénarios sélectionnés ne sont pas fourni";
					}

					// Map<Integer, String> headers =
					// CollateralTraitement.getHeaders(cheminFichier.concat(nomFichier));

				} else {
					message = "Les écheanciers ne sont pas disponibles pour le moment, veuillez réessayer ultérieurement.";
				}
			} 
			
			   else {    
				   
					System.out.println("++++L'action ====> FALSE   ++++++");

			/*	String fichierXmlP1 = Constants.tmpDirectory + xmlPricingFileP1Path;
				String fichierXmlP2 = Constants.tmpDirectory + xmlPricingFileP2Path;
				String fichierXmlS = Constants.tmpDirectory + xmlPricingFileSPath; 
				
				
				// Ceci est fait pour récupérer les valerus des fichiers xml après leurs parsing 
				xmlParser ConfigFile = new xmlParser();   
				
				Hashtable ParametresGenerauxA = ConfigFile.GetParameters(fichierXmlP1, "PRICING");   
				
				
				if (ParametresGenerauxA == null)
					System.out.println("Probleme Parsing Xml");     
				
				String PE = (String) ParametresGenerauxA.get("primeEsperee");  */ 
				   
				   
				Double primeEspereeA = 0.0D;
				// new Double(Double.parseDouble(PE));

		/*		Hashtable ParametresGenerauxB = ConfigFile.GetParameters(fichierXmlP2, "PRICING");
				
				if (ParametresGenerauxB == null)
					System.out.println("Probleme Parsing Xml");
				PE = (String) ParametresGenerauxB.get("primeEsperee"); */
				
				
				Double primeEspereeB = 0.0D;
				// new Double(Double.parseDouble(PE));

/*				Hashtable ParametresGenerauxS = ConfigFile.GetParameters(fichierXmlS, "PRICING");
				if (ParametresGenerauxS == null)
					System.out.println("Probleme Parsing Xml");
				PE = (String) ParametresGenerauxS.get("primeEsperee"); */
				
				
				Double primeEspereeS = 0.0D;  

				// new Double(Double.parseDouble(PE));
				helpPricing.setPrimeEspereeA(primeEspereeA);
				helpPricing.setPrimeEspereeB(primeEspereeB);
				helpPricing.setPrimeEspereeS(primeEspereeS);
			}     
			

		
			String fichierExcel = "";    
			
			if ((       currentEcheanciersPrevisionnels != null) 
					&& (currentEcheanciersPrevisionnels.getPartP1() != null)
					&& (currentEcheanciersPrevisionnels.getPartP2() != null)) {
				
				
				LnetDate myDate = new LnetDate();
				String dateDuJour = myDate.RenvoieDate("/"); 
				
				if (dateTransaction.compareTo("") == 0)
					dtTrans = dateDuJour; 
							
				//System.out.println("\n=*=*=*=*=*= date fin: "+ dateFinP1+"=*=*=*=*=*=");
			   System.out.println("La date de transaction est :" + dtTrans);  
				
				/*	String tmpDirectory = System.getProperty("java.io.tmpdir") + "/fonds/"; 
				
				fichierExcel = tmpDirectory	+ currentEcheanciersPrevisionnels.getPartP2(); 
				
				System.out.println("avant ça, tout marche bien !!!");
				myVectorDate = PricingProcess.simulationDateTitrisation(fichierExcel);
			     
				System.out.println("après je ne pense pas okayyy");  */
			}  
			
			/*else {
				message = "Les écheanciers ne sont pas disponibles pour le moment, veuillez réessayer ultérieurement.";
			} 
			
			String dateIni = "";
			String dateFin = ""; 
			
			if (myVectorDate != null && !myVectorDate.isEmpty()) { 
				
				String dateInitial = (String) myVectorDate.get(0);
				String dateFinale = (String) myVectorDate.get(myVectorDate.size() - 1);
				StringTokenizer stIni = new StringTokenizer(dateInitial, "/");
				String jour = stIni.nextToken();
				String mois = stIni.nextToken();
				String annee = stIni.nextToken();
				dateIni = annee + "," + mois + " - 1," + jour;
				StringTokenizer stFinale = new StringTokenizer(dateFinale, "/");
				String jourFin = stFinale.nextToken();
				String moisFin = stFinale.nextToken();
				String anneeFin = stFinale.nextToken();
				dateFin = anneeFin + "," + moisFin + " - 1," + jourFin; 
				
			} */
			 
			
			// le remplissag de helpPricing 
			helpPricing.setAction(action);
			helpPricing.setDateTRansaction(dtTrans);
			helpPricing.setFichierPart1Pricing(currentEcheanciersPrevisionnels.getPartP1());
			helpPricing.setFichierPart2Pricing(currentEcheanciersPrevisionnels.getPartP2());
			helpPricing.setFichierSPricing(currentEcheanciersPrevisionnels.getPartS());
		//	helpPricing.setMyVectorDate(myVectorDate);
			helpPricing.setMyVectorP1(myVectorP1);
			helpPricing.setMyVectorP2(myVectorP2);
			helpPricing.setMyVectorS(myVectorS);
			helpPricing.setMessage(message);
			//helpPricing.setFichierExcel(fichierExcel);
			helpPricing.setVecPricer(vecPricer);
			helpPricing.setVecCashFlow(vecCashFlow);
			helpPricing.setIdClass(idClass);
			helpPricing.setIdTypeCredilog(idTypeCredilog);
			helpPricing.setListScenarioRA(listScenarioRA);
			helpPricing.setListScenarioDDT(listScenarioDDT);
		//	helpPricing.setDateIni(dateIni);
		//	helpPricing.setDateFin(dateFin);
			helpPricing.setMessage(message);
			helpPricing.setIdScenarioRA(idScenarioRA);
			helpPricing.setIdScenarioDDT(idScenarioDDT);
			helpPricing.setDefaultScenariosFondsDDT(defaultScenarioFondsDDT);
			helpPricing.setDefaultScenariosFondsRa(defaultScenariosFondsRa);
			helpPricing.setDateDepartP1(dateDepartP1);
			helpPricing.setDateDepartP2(dateDepartP2);
			helpPricing.setDateDepartS(dateDepartS);
			helpPricing.setDateFinP1(dateFinP1);
			helpPricing.setDateFinP2(dateFinP2);
			helpPricing.setDateFinS(dateFinS);  
			
			return helpPricing;   
			
		} catch (Exception e) {	
			return helpPricing;
		}
	}
	
   
}

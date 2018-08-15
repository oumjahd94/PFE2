package ma.mt.fo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class PricingProcess {

    public static final double nombreJourBaseMonetaire = 36000;

    private static String[] datesDeBorne;

    public static double[] maturitesBornes;

    public static double[] zeroCouponBornes;

    public static double[] rangBornes;

    public static final int[] indexDateBornes = { 3, 7, 19, 39, 59, 79 };

    public static final int[] indexZeroCouponBorne = { 1, 2, 3, 4, 7, 12, 17, 22, 32 };

    public static final int[] anneeDateBornes = { 1, 2, 5, 10, 15, 20 };

    public static final Hashtable<String, Integer> hashDatesBorne = new Hashtable<String, Integer>();

    public static final HashMap<Integer, Integer> hashCorrespondance = new HashMap<Integer, Integer>();

    public PricingProcess() {

    }

    public static void dateBornes() {
	for (int i = 0; i < datesDeBorne.length; i++) {
	    hashDatesBorne.put(datesDeBorne[i], Integer.valueOf(anneeDateBornes[i]));
	}

	hashCorrespondance.put(Integer.valueOf(1), Integer.valueOf(1));
	hashCorrespondance.put(Integer.valueOf(2), Integer.valueOf(2));
	hashCorrespondance.put(Integer.valueOf(5), Integer.valueOf(3));
	hashCorrespondance.put(Integer.valueOf(10), Integer.valueOf(4));
	hashCorrespondance.put(Integer.valueOf(15), Integer.valueOf(5));
	hashCorrespondance.put(Integer.valueOf(20), Integer.valueOf(6));

    }

    public static double arrondi(double x, int n) throws IOException {  
    	
	    return Math.round(x * Math.pow(10.0D, n)) / Math.pow(10.0D, n);  
	    
    }
    
    public static double arrondiInf(double x) throws IOException{
	DecimalFormat df = new DecimalFormat("#.##");
	df.setRoundingMode(RoundingMode.DOWN);
	String formatedNumber = df.format(x);
	formatedNumber = formatedNumber.replace(',', '.');
	
	return Double.parseDouble(formatedNumber);
    }
    
    public static double arrondiSup(double x) throws IOException{
	DecimalFormat df = new DecimalFormat("#.####");
	df.setRoundingMode(RoundingMode.UP);
	String formatedNumber = df.format(x);
	formatedNumber = formatedNumber.replace(',', '.');
	
	return Double.parseDouble(formatedNumber);
    }

    public static void getDatesBorne(Vector vecDates, Vector vecMaturites, Vector vecZeroCoupon, Vector vecRang) {
	datesDeBorne = new String[indexDateBornes.length];
	zeroCouponBornes = new double[indexZeroCouponBorne.length];
	for (int compte = 0; compte < indexDateBornes.length; compte++) {
	    datesDeBorne[compte] = (String) vecDates.elementAt(indexDateBornes[compte]);
	}
	maturitesBornes = new double[indexZeroCouponBorne.length];
	for (int i = 0; i < indexZeroCouponBorne.length; i++) {
	    maturitesBornes[i] = ((Double) vecMaturites.elementAt(indexZeroCouponBorne[i])).doubleValue();
	}

	for (int i = 0; i < indexZeroCouponBorne.length; i++) {
	    zeroCouponBornes[i] = ((Double) vecZeroCoupon.elementAt(indexZeroCouponBorne[i])).doubleValue();
	}

	rangBornes = new double[indexDateBornes.length];
	for (int i = 0; i < indexDateBornes.length; i++) {
	    rangBornes[i] = ((Double) vecRang.elementAt(indexDateBornes[i])).doubleValue();
	}
	dateBornes();
    }

    public static void setMaturitesBornes(Vector vecMaturites) {

    }

    public static boolean contains(String chaine) {
	for (int i = 0; i < datesDeBorne.length; i++) {
	    if (datesDeBorne[i].equals(chaine)) {
		return true;
	    }
	}
	return false;
    }

    public static String dateToString(Date maDate) throws IOException {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	return dateFormat.format(maDate);
    }

	public static GregorianCalendar stringToGregorianCalendar(String maDate) throws IOException {
		int index1 = maDate.indexOf("/");
		int index2 = maDate.lastIndexOf("/");
		int jour = Integer.parseInt(maDate.substring(0, index1));
		int mois = Integer.parseInt(maDate.substring(index1 + 1, index2)) - 1;
		int annee = Integer.parseInt(maDate.substring(index2 + 1, maDate.length()));
		GregorianCalendar laDate = new GregorianCalendar(annee, mois, jour);
		return laDate;
	}

    public static int nombreJourAnnee(GregorianCalendar maDate) throws IOException {
	int NJA = 0;
	int annee = maDate.get(1);
	if (annee % 4 == 0)
	    NJA = 366;
	else
	    NJA = 365;
	return NJA;
    }

    public static int nombreJourAnneeTa(GregorianCalendar maDate) throws IOException {
	return 365;
    }

    public static double sigma(Vector myVector, int debut, int fin) throws IOException {
	double somme = 0.0D;
	for (int i = debut; i <= fin; i++) {
	    Double myDouble = (Double) myVector.elementAt(i);
	    somme += myDouble.doubleValue();
	}

	return somme;
    }

    
    public static Vector extraireFichierExcel(
    		String excelFileName, 
    		String maDate, 
    		int colonneExtraite) 
    		throws IOException {
	
    	Vector vecReturn = new Vector();
	
    	try { 
    		
	    FileInputStream fileInputStream = new FileInputStream(new File(excelFileName));
	    POIFSFileSystem fileSystem = new POIFSFileSystem(fileInputStream);
	    HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
	    HSSFSheet sheet = workbook.getSheetAt(0);
        
	    // il se rappeler que j'ai ajouté ici qlq chose (<= est transformée en <)
	    for (int k = 6; k <= sheet.getLastRowNum(); k++) { 
	    	
		HSSFRow row = sheet.getRow(k);
		if (row == null)
		    continue;   
		
		HSSFCell cellDate = row.getCell((short) 0);
		HSSFCell cellCRDDebut = row.getCell((short) 2);
		HSSFCell cellAmortissement = row.getCell((short) 3);  
		
		System.out.println("JE VAIS AFFICHER ÇA ET VOIR QUEL RESULTAT cellDate ====> "+cellDate); 
		System.out.println("JE VAIS AFFICHER ÇA ET VOIR QUEL RESULTAT cellCRDDebut ====> "+cellCRDDebut);
		System.out.println("JE VAIS AFFICHER ÇA ET VOIR QUEL RESULTAT cellAmortissement====> "+cellAmortissement);

		
		if ((cellDate == null) || (cellCRDDebut == null) || (cellAmortissement == null))
		    continue; 
		
		GregorianCalendar laDate = stringToGregorianCalendar(maDate);  
		
		
		
		//là ou il y a le prob okiiiii
		String dateExcel = dateToString(cellDate.getDateCellValue());
		GregorianCalendar laDateExcel = stringToGregorianCalendar(dateExcel);
		double difference = laDateExcel.getTime().getTime() - laDate.getTime().getTime();
        
		// long diffe = laDateExcel.getTime().getTime() -
		// laDate.getTime().getTime();

		// long diffJ = diffe / 86400000;
		// double diffD = diffe / 86400000.D;
		// double math = Math.ceil(diffe / 86400000.D);
		if (difference <= 0.0D)
		    continue;

		int dfj = (int) (difference / 86400000.0D);
		double differenceJour = Math.round(difference / 86400000.0D);

		double valeurRang = differenceJour * Math.pow(nombreJourAnnee(laDateExcel), -1.0D);
		// valeurRang = arrondi(valeurRang, 2);
		double CRDDebut = cellCRDDebut.getNumericCellValue();
		double amortissement = cellAmortissement.getNumericCellValue(); 
		
		System.out.println("*******************************\n sur le fichier excel valeurRang ==> "+ valeurRang);    
		System.out.println("sur le fichier excel CRDDebut ==>"+ CRDDebut); 
		System.out.println("sur le fichier excel amortissement ==> "+ amortissement+"\n*********************************"); 
		
		
		if (colonneExtraite == 0)
		    vecReturn.addElement(new Double(differenceJour));
		if (colonneExtraite == 1) {
		    System.out.println("date Excel ==> " + laDateExcel.getTime());
		    System.out.println("madate ==> " + laDate.getTime());
		    System.out.println("différence de jour ==> " + differenceJour);
		    System.out.println("diff avant ==> " + (difference / 86400000.0D));
		    System.out.println("Nombre jour annee ==> " + nombreJourAnnee(laDateExcel));
		    System.out.println("valeur Rang ==> " + valeurRang);

		    vecReturn.addElement(new Double(valeurRang));
		}
		if (colonneExtraite == 2)
		    vecReturn.addElement(new Double(CRDDebut));
		if (colonneExtraite == 3)
		    vecReturn.addElement(new Double(amortissement));
		if (colonneExtraite == 4) {
		    vecReturn.addElement(dateExcel);
		}

	    }

	    fileInputStream.close();
	    vecReturn.removeElement(vecReturn.lastElement()); 
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    } 
    
    
    

    public static Vector extraireFichierExcelCRD4(String excelFileName, String maDate, int colonneExtraite) throws IOException {
	
    	Vector vecReturn = new Vector(); 
    	
	try {
	    FileInputStream fileInputStream = new FileInputStream(new File(excelFileName));
	    POIFSFileSystem fileSystem = new POIFSFileSystem(fileInputStream);
	    HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
	    HSSFSheet sheet = workbook.getSheetAt(0);

	    for (int count=-1, k = 6; k <= sheet.getLastRowNum(); k++, count++) {
		HSSFRow row = sheet.getRow(k);
		if (row == null)
		    continue;  
		
		HSSFCell cellDate = row.getCell((short) 0);
		HSSFCell cellCRDDebut = row.getCell((short) 2);
		HSSFCell cellAmortissement = row.getCell((short) 3);
		if ((cellDate == null) || (cellCRDDebut == null) || (cellAmortissement == null))
		    continue;
		GregorianCalendar laDate = stringToGregorianCalendar(maDate);
		String dateExcel = dateToString(cellDate.getDateCellValue());
		GregorianCalendar laDateExcel = stringToGregorianCalendar(dateExcel);
		double difference = laDateExcel.getTime().getTime() - laDate.getTime().getTime();

		// long diffe = laDateExcel.getTime().getTime() -
		// laDate.getTime().getTime();

		// long diffJ = diffe / 86400000;
		// double diffD = diffe / 86400000.D;
		// double math = Math.ceil(diffe / 86400000.D);    
		
		if (difference <= 0.0D)
		    continue;

		int dfj = (int) (difference / 86400000.0D);
		double differenceJour = Math.round(difference / 86400000.0D);
		double valeurRang = 0.0;
//		if(k == 6){
//		    valeurRang = differenceJour * Math.pow(nombreJourAnnee(laDateExcel), -1.0D);
//		}else{
//		    valeurRang = ((Double)vecReturn.elementAt(count)).doubleValue() + 0.25;
//		}
		
		valeurRang = differenceJour * Math.pow(365, -1.0D);
		
		// valeurRang = arrondi(valeurRang, 2);
		double CRDDebut = cellCRDDebut.getNumericCellValue();
		double amortissement = cellAmortissement.getNumericCellValue();
		if (colonneExtraite == 0)
		    vecReturn.addElement(new Double(differenceJour));
		if (colonneExtraite == 1) {
		    System.out.println("date Excel ==> " + laDateExcel.getTime());
		    System.out.println("madate ==> " + laDate.getTime());
		    System.out.println("différence de jour ==> " + differenceJour);
		    System.out.println("diff avant ==> " + (difference / 86400000.0D));
		    System.out.println("Nombre jour anneeeeeeeeeeeee ==> " + 365);
		    System.out.println("valeur Rang ==> " + valeurRang);

		    vecReturn.addElement(new Double(valeurRang));
		}
		if (colonneExtraite == 2)
		    vecReturn.addElement(new Double(CRDDebut));
		if (colonneExtraite == 3)
		    vecReturn.addElement(new Double(amortissement));
		if (colonneExtraite == 4) {
		    vecReturn.addElement(dateExcel);
		}

	    }

	    fileInputStream.close();
	    vecReturn.removeElement(vecReturn.lastElement());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    
    public static Vector extraireFichierExcelDateEcheance(String excelFileName, String maDate, int colonneExtraite) throws IOException {
	Vector vecDateEcheance = new Vector();
	try {
	    FileInputStream fileInputStream = new FileInputStream(new File(excelFileName));
	    POIFSFileSystem fileSystem = new POIFSFileSystem(fileInputStream);
	    HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
	    HSSFSheet sheet = workbook.getSheetAt(0);

	    for (int k = 6; k < 150; k++) {
		HSSFRow row = sheet.getRow(k);
		if (row == null)
		    continue;
		HSSFCell cellDate = row.getCell((short) 0);
		HSSFCell cellCRDDebut = row.getCell((short) 2);
		HSSFCell cellAmortissement = row.getCell((short) 3);
		if ((cellDate == null) || (cellCRDDebut == null) || (cellAmortissement == null))
		    continue;
		GregorianCalendar laDate = stringToGregorianCalendar(maDate);
		String dateExcel = dateToString(cellDate.getDateCellValue());
		GregorianCalendar laDateExcel = stringToGregorianCalendar(dateExcel);
		double difference = laDateExcel.getTime().getTime() - laDate.getTime().getTime();

		if (difference <= 0.0D)
		    continue;
		double differenceJour = difference / 86400000.0D;
		vecDateEcheance.addElement(new Double(differenceJour));
		// double valeurRang = differenceJour *
		// Math.pow(nombreJourAnnee(laDateExcel), -1.0D);
		// valeurRang = arrondi(valeurRang, 2);
		// double CRDDebut = cellCRDDebut.getNumericCellValue();
		// double amortissement =
		// cellAmortissement.getNumericCellValue();
		// if (colonneExtraite == 0)
		// vecReturn.addElement(new Double(differenceJour));
		// if (colonneExtraite == 1)
		// vecReturn.addElement(new Double(valeurRang));
		// if (colonneExtraite == 2)
		// vecReturn.addElement(new Double(CRDDebut));
		// if (colonneExtraite == 3)
		// vecReturn.addElement(new Double(amortissement));
		// if (colonneExtraite == 4) {
		// vecReturn.addElement(dateExcel);
		// }

	    }

	    fileInputStream.close();
	    vecDateEcheance.removeElement(vecDateEcheance.lastElement());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecDateEcheance;
    }

    public static Vector extraireFichierExcelDPE(String excelFileName, String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    FileInputStream fileInputStream = new FileInputStream(new File(excelFileName));
	    POIFSFileSystem fileSystem = new POIFSFileSystem(fileInputStream);
	    HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
	    HSSFSheet sheet = workbook.getSheetAt(0);

	    for (int k = 6; k < 150; k++) {
		HSSFRow row = sheet.getRow(k);
		if (row == null)
		    continue;
		HSSFCell cellDate = row.getCell((short) 0);

		if (cellDate == null)
		    continue;
		GregorianCalendar laDate = stringToGregorianCalendar(maDate);
		String dateExcel = dateToString(cellDate.getDateCellValue());
		GregorianCalendar laDateExcel = stringToGregorianCalendar(dateExcel);

		vecReturn.addElement(dateExcel);
	    }

	    fileInputStream.close();
	    vecReturn.removeElement(vecReturn.lastElement());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    
    
    
    public static Vector calculNombreJours(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    vecReturn.addElement(new Double(91.0D));
	    vecReturn.addElement(new Double(182.0D));
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    laDate.add(5, 182);
	    double annee26S = nombreJourAnnee(laDate);
	    vecReturn.addElement(new Double(annee26S));
	    laDate.add(5, -182);
	    for (int i = 3; i <= 21; i++) {
		laDate.add(1, 1);
		Double AV = (Double) vecReturn.elementAt(i - 1);
		double CJ = AV.doubleValue() + nombreJourAnnee(laDate);
		vecReturn.addElement(new Double(CJ));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    
    
    
    public static Vector calculNombreJoursCredilog3(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D;
	    double M26S = 182.0D;

	    vecReturn.addElement(new Double(91.0D));
	    vecReturn.addElement(new Double(182.0D));
	    double nombreJourAnnee = nombreJourAnnee(laDate);
	    vecReturn.addElement(new Double(nombreJourAnnee(laDate)));

	    laDate.add(5, (int) nombreJourAnnee);
	    // laDate.add(Calendar.YEAR, 1);
	    for (int i = 3; i <= 31; i++) {
		Double AV = (Double) vecReturn.elementAt(i - 1);
		double CJ = AV.doubleValue() + nombreJourAnnee(laDate);
		vecReturn.addElement(new Double(CJ));
		laDate.add(Calendar.YEAR, 1);
	    }

	    // for (int i = 2; i <= 31; i++)
	    // vecReturn.addElement(new Double(i - 1));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    
    
    public static Vector calculNombreJoursCredilog4(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D;
	    double M26S = 182.0D;

	    vecReturn.addElement(new Double(91.0D));
	    vecReturn.addElement(new Double(182.0D));
	    double nombreJourAnnee = nombreJourAnnee(laDate);
	    vecReturn.addElement(new Double(nombreJourAnnee(laDate)));

	    laDate.add(5, (int) nombreJourAnnee);
	    laDate.add(Calendar.YEAR, 1);
	    for (int i = 3; i <= 31; i++) {
		
		Double AV = (Double) vecReturn.elementAt(i - 1);
		double CJ = AV.doubleValue() + nombreJourAnnee(laDate);
		vecReturn.addElement(new Double(CJ));
		laDate.add(Calendar.YEAR, 1);
	    }

	    // for (int i = 2; i <= 31; i++)
	    // vecReturn.addElement(new Double(i - 1));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static Vector calculNombreJoursImmolv(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D;
	    double M26S = 182.0D;
	    double M52S = 364.0D;

	    vecReturn.addElement(new Double(91.0D));
	    vecReturn.addElement(new Double(182.0D));
	    vecReturn.addElement(new Double(364.0D));
	    double nombreJourAnnee = nombreJourAnnee(laDate);
	    vecReturn.addElement(new Double(nombreJourAnnee(laDate)));

	    laDate.add(5, (int) nombreJourAnnee);
	    laDate.add(Calendar.YEAR, 1);
	    for (int i = 4; i <= 31; i++) {
		
		Double AV = (Double) vecReturn.elementAt(i - 1);
		double CJ = AV.doubleValue() + nombreJourAnnee(laDate);
		vecReturn.addElement(new Double(CJ));
		laDate.add(Calendar.YEAR, 1);
	    }

	    // for (int i = 2; i <= 31; i++)
	    // vecReturn.addElement(new Double(i - 1));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculNombreJoursCredilog3Old(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);
	    double M26S = 182.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);

	    vecReturn.addElement(new Double(M13S));
	    vecReturn.addElement(new Double(M26S));
	    for (int i = 2; i <= 31; i++)
		vecReturn.addElement(new Double(i - 1));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculNombreJoursSakane(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    vecReturn.addElement(new Double(91.0D));
	    vecReturn.addElement(new Double(182.0D));
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);

	    laDate.add(5, 182);
	    // vecReturn.addElement(new Double(364.0D));
	    // laDate.add(Calendar.DAY_OF_YEAR, 182);

	    double annee1A = nombreJourAnnee(laDate);

	    vecReturn.addElement(new Double(annee1A));

	    laDate.add(5, -182);
	    laDate.add(Calendar.YEAR, 1);
	    System.out.println("La date 2 ==> " + laDate.getTime());
	    for (int i = 3; i <= 31; i++) {

		Double AV = (Double) vecReturn.elementAt(i - 1);
		double CJ = AV.doubleValue() + nombreJourAnnee(laDate);
		vecReturn.addElement(new Double(CJ));
		laDate.add(1, 1);
		System.out.println("La date " + i + "==> " + laDate.getTime());
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    
    
    
    public static Vector calculTauxActuarial(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A) throws IOException {
	Vector vecReturn = new Vector();
	try {    
		
	    Vector vecCumulJour = calculNombreJours(maDate);   
	     
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA1A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA2A, 3);
	    vecReturn.addElement(new Double(arrondiTA)); 
	    
	    
	    Double NJ3 = (Double) vecCumulJour.elementAt(3);
	    Double NJ4 = (Double) vecCumulJour.elementAt(4);
	    Double NJ5 = (Double) vecCumulJour.elementAt(5);
	    Double NJ6 = (Double) vecCumulJour.elementAt(6);
	    Double NJ7 = (Double) vecCumulJour.elementAt(7);
	    Double NJ8 = (Double) vecCumulJour.elementAt(8);
	    Double NJ9 = (Double) vecCumulJour.elementAt(9);
	    Double NJ10 = (Double) vecCumulJour.elementAt(10);
	    Double NJ11 = (Double) vecCumulJour.elementAt(11);
	    Double NJ12 = (Double) vecCumulJour.elementAt(12);
	    Double NJ13 = (Double) vecCumulJour.elementAt(13);
	    Double NJ14 = (Double) vecCumulJour.elementAt(14);
	    Double NJ15 = (Double) vecCumulJour.elementAt(15);
	    Double NJ16 = (Double) vecCumulJour.elementAt(16);
	    Double NJ17 = (Double) vecCumulJour.elementAt(17);
	    Double NJ18 = (Double) vecCumulJour.elementAt(18);
	    Double NJ19 = (Double) vecCumulJour.elementAt(19);
	    Double NJ20 = (Double) vecCumulJour.elementAt(20);
	    Double NJ21 = (Double) vecCumulJour.elementAt(21);
	    double TA = (TA5A - TA2A) * (NJ4.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA5A - TA2A) * (NJ5.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));
	    arrondiTA = arrondi(TA5A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    TA = (TA10A - TA5A) * (NJ7.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA10A - TA5A) * (NJ8.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA10A - TA5A) * (NJ9.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA10A - TA5A) * (NJ10.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));
	    arrondiTA = arrondi(TA10A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    TA = (TA15A - TA10A) * (NJ12.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA15A - TA10A) * (NJ13.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA15A - TA10A) * (NJ14.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA15A - TA10A) * (NJ15.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));
	    arrondiTA = arrondi(TA15A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    TA = (TA20A - TA15A) * (NJ17.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA20A - TA15A) * (NJ18.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA20A - TA15A) * (NJ19.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));
	    TA = (TA20A - TA15A) * (NJ20.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));
	    arrondiTA = arrondi(TA20A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculTauxActuarialCredilog3(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A)
	    throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecCumulJour = calculNombreJoursCredilog3(maDate);
	    System.out.println("vecCumulJour ==> " + vecCumulJour);
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA1A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA2A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    Double NJ3 = (Double) vecCumulJour.elementAt(3);
	    Double NJ4 = (Double) vecCumulJour.elementAt(4);
	    Double NJ5 = (Double) vecCumulJour.elementAt(5);
	    Double NJ6 = (Double) vecCumulJour.elementAt(6);
	    Double NJ7 = (Double) vecCumulJour.elementAt(7);
	    Double NJ8 = (Double) vecCumulJour.elementAt(8);
	    Double NJ9 = (Double) vecCumulJour.elementAt(9);
	    Double NJ10 = (Double) vecCumulJour.elementAt(10);
	    Double NJ11 = (Double) vecCumulJour.elementAt(11);
	    Double NJ12 = (Double) vecCumulJour.elementAt(12);
	    Double NJ13 = (Double) vecCumulJour.elementAt(13);
	    Double NJ14 = (Double) vecCumulJour.elementAt(14);
	    Double NJ15 = (Double) vecCumulJour.elementAt(15);
	    Double NJ16 = (Double) vecCumulJour.elementAt(16);
	    Double NJ17 = (Double) vecCumulJour.elementAt(17);
	    Double NJ18 = (Double) vecCumulJour.elementAt(18);
	    Double NJ19 = (Double) vecCumulJour.elementAt(19);
	    Double NJ20 = (Double) vecCumulJour.elementAt(20);
	    Double NJ21 = (Double) vecCumulJour.elementAt(21);
	    Double NJ22 = (Double) vecCumulJour.elementAt(22);
	    Double NJ23 = (Double) vecCumulJour.elementAt(23);
	    Double NJ24 = (Double) vecCumulJour.elementAt(24);
	    Double NJ25 = (Double) vecCumulJour.elementAt(25);
	    Double NJ26 = (Double) vecCumulJour.elementAt(26);
	    Double NJ27 = (Double) vecCumulJour.elementAt(27);
	    Double NJ28 = (Double) vecCumulJour.elementAt(28);
	    Double NJ29 = (Double) vecCumulJour.elementAt(29);
	    Double NJ30 = (Double) vecCumulJour.elementAt(30);
	    Double NJ31 = (Double) vecCumulJour.elementAt(31);

	    double TA = (TA5A - TA2A) * (NJ4.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA5A - TA2A) * (NJ5.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA5A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA10A - TA5A) * (NJ7.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ8.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ9.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ10.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA10A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA15A - TA10A) * (NJ12.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ13.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ14.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ15.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA15A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA20A - TA15A) * (NJ17.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ18.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ19.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ20.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA20A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA30A - TA20A) * (NJ22.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ23.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ24.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ25.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ26.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ27.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ28.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ29.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ30.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA30A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static Vector calculTauxActuarialCredilog4(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A)
	    throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecCumulJour = calculNombreJoursCredilog4(maDate);
	    System.out.println("vecCumulJour ==> " + vecCumulJour);
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA1A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA2A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    Double NJ3 = (Double) vecCumulJour.elementAt(3);//731
	    Double NJ4 = (Double) vecCumulJour.elementAt(4);//1096
	    Double NJ5 = (Double) vecCumulJour.elementAt(5);//1461
	    Double NJ6 = (Double) vecCumulJour.elementAt(6);//1826
	    Double NJ7 = (Double) vecCumulJour.elementAt(7);
	    Double NJ8 = (Double) vecCumulJour.elementAt(8);
	    Double NJ9 = (Double) vecCumulJour.elementAt(9);
	    Double NJ10 = (Double) vecCumulJour.elementAt(10);
	    Double NJ11 = (Double) vecCumulJour.elementAt(11);
	    Double NJ12 = (Double) vecCumulJour.elementAt(12);
	    Double NJ13 = (Double) vecCumulJour.elementAt(13);
	    Double NJ14 = (Double) vecCumulJour.elementAt(14);
	    Double NJ15 = (Double) vecCumulJour.elementAt(15);
	    Double NJ16 = (Double) vecCumulJour.elementAt(16);
	    Double NJ17 = (Double) vecCumulJour.elementAt(17);
	    Double NJ18 = (Double) vecCumulJour.elementAt(18);
	    Double NJ19 = (Double) vecCumulJour.elementAt(19);
	    Double NJ20 = (Double) vecCumulJour.elementAt(20);
	    Double NJ21 = (Double) vecCumulJour.elementAt(21);
	    Double NJ22 = (Double) vecCumulJour.elementAt(22);
	    Double NJ23 = (Double) vecCumulJour.elementAt(23);
	    Double NJ24 = (Double) vecCumulJour.elementAt(24);
	    Double NJ25 = (Double) vecCumulJour.elementAt(25);
	    Double NJ26 = (Double) vecCumulJour.elementAt(26);
	    Double NJ27 = (Double) vecCumulJour.elementAt(27);
	    Double NJ28 = (Double) vecCumulJour.elementAt(28);
	    Double NJ29 = (Double) vecCumulJour.elementAt(29);
	    Double NJ30 = (Double) vecCumulJour.elementAt(30);
	    Double NJ31 = (Double) vecCumulJour.elementAt(31);

	    double TA = (TA5A - TA2A) * (NJ4.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA5A - TA2A) * (NJ5.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA5A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    	
	    double TAB = TA10A - TA5A;
		    //arrondi(TA10A - TA5A, 3);
	    
	    TA = TAB * (NJ7.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ8.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ8.doubleValue() - NJ6.doubleValue()), 3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ9.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ9.doubleValue() - NJ6.doubleValue()),3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ10.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ10.doubleValue() - NJ6.doubleValue()), 3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA10A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA15A - TA10A) * (NJ12.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ13.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ14.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ15.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA15A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA20A - TA15A) * (NJ17.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ18.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ19.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ20.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA20A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA30A - TA20A) * (NJ22.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ23.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ24.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ25.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ26.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ27.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ28.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ29.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ30.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA30A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    // Fonction a verifier
    public static Vector calculTauxActuarialImmolv(Vector vecTauxBDT, String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A) throws IOException{
	Vector vecReturn = new Vector();
	try {
	    Vector vecCumulJour = calculNombreJoursImmolv(maDate);
	    
	    for(int index=0; index < vecTauxBDT.size(); index++){
		double TA = 0.0;
		
		double itemJour = (Double)vecCumulJour.get(index);
		
		double tauxBdt = (Double)vecTauxBDT.get(index);
		
		if(itemJour < 365){
		 TA = Math.pow((1 + (((tauxBdt /100)*itemJour)/360.0)),(365.0/itemJour)) - 1;
		 TA *= 100.0;
		}else{
		    TA = tauxBdt;
		}
		vecReturn.add(index, TA);
	    }
	    
	    double TA4 = (Double)vecReturn.get(4);
	    double TA2 = (Double)vecReturn.get(2);
	    double itemJour4 = (Double)vecCumulJour.get(4);
	    double itemJour2 = (Double)vecCumulJour.get(2);    
	    
	    double TA3 = TA2 + ((TA4 - TA2) / (itemJour4 - itemJour2));
	    vecReturn.set(3, TA3);
	   
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    // Fonction � verifier 
    
    public static Vector calculTauxActuarialBDTImmolv(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A)
	    throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecCumulJour = calculNombreJoursImmolv(maDate);
	    System.out.println("vecCumulJour ==> " + vecCumulJour);
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    
	    arrondiTA = arrondi(TA1A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    vecReturn.addElement(new Double(0));
	    arrondiTA = arrondi(TA2A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    Double NJ3 = (Double) vecCumulJour.elementAt(3);//731
	    Double NJ4 = (Double) vecCumulJour.elementAt(4);//1096
	    Double NJ5 = (Double) vecCumulJour.elementAt(5);//1461
	    Double NJ6 = (Double) vecCumulJour.elementAt(6);//1826
	    Double NJ7 = (Double) vecCumulJour.elementAt(7);
	    Double NJ8 = (Double) vecCumulJour.elementAt(8);
	    Double NJ9 = (Double) vecCumulJour.elementAt(9);
	    Double NJ10 = (Double) vecCumulJour.elementAt(10);
	    Double NJ11 = (Double) vecCumulJour.elementAt(11);
	    Double NJ12 = (Double) vecCumulJour.elementAt(12);
	    Double NJ13 = (Double) vecCumulJour.elementAt(13);
	    Double NJ14 = (Double) vecCumulJour.elementAt(14);
	    Double NJ15 = (Double) vecCumulJour.elementAt(15);
	    Double NJ16 = (Double) vecCumulJour.elementAt(16);
	    Double NJ17 = (Double) vecCumulJour.elementAt(17);
	    Double NJ18 = (Double) vecCumulJour.elementAt(18);
	    Double NJ19 = (Double) vecCumulJour.elementAt(19);
	    Double NJ20 = (Double) vecCumulJour.elementAt(20);
	    Double NJ21 = (Double) vecCumulJour.elementAt(21);
	    Double NJ22 = (Double) vecCumulJour.elementAt(22);
	    Double NJ23 = (Double) vecCumulJour.elementAt(23);
	    Double NJ24 = (Double) vecCumulJour.elementAt(24);
	    Double NJ25 = (Double) vecCumulJour.elementAt(25);
	    Double NJ26 = (Double) vecCumulJour.elementAt(26);
	    Double NJ27 = (Double) vecCumulJour.elementAt(27);
	    Double NJ28 = (Double) vecCumulJour.elementAt(28);
	    Double NJ29 = (Double) vecCumulJour.elementAt(29);
	    Double NJ30 = (Double) vecCumulJour.elementAt(30);
	    Double NJ31 = (Double) vecCumulJour.elementAt(31);

	    double TA = (TA5A - TA2A) * (NJ4.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA5A - TA2A) * (NJ5.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA5A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    	
	    double TAB = TA10A - TA5A;
		    //arrondi(TA10A - TA5A, 3);
	    
	    TA = TAB * (NJ7.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ8.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ8.doubleValue() - NJ6.doubleValue()), 3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ9.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ9.doubleValue() - NJ6.doubleValue()),3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = TAB * (NJ10.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
		    //arrondi(TAB * arrondi((NJ10.doubleValue() - NJ6.doubleValue()), 3) / arrondi((NJ11.doubleValue() - NJ6.doubleValue()), 3), 3) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA10A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA15A - TA10A) * (NJ12.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ13.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ14.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ15.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA15A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    vecReturn.addElement(new Double(arrondiTA));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculTauxActuarialSakane(String maDate, double TA13S, double TA26S, double TA52S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A,
	    double TA30A) throws IOException {  
    	
    	
	Vector vecReturn = new Vector();
	try {
	    Vector vecCumulJour = calculNombreJoursSakane(maDate);
	    double arrondiTA = 0.0D;
	    // arrondiTA = arrondi(TA13S, 3);
	    //
	    // vecReturn.addElement(new Double(arrondiTA));
	    // arrondiTA = arrondi(TA26S, 3);
	    // vecReturn.addElement(new Double(arrondiTA));
	    // arrondiTA = arrondi(TA1A, 3);
	    // vecReturn.addElement(new Double(arrondiTA));

	    Double NJ0 = (Double) vecCumulJour.elementAt(0);// 91 - 13S
	    Double NJ1 = (Double) vecCumulJour.elementAt(1);// 182 - 26S
	    Double NJ2 = (Double) vecCumulJour.elementAt(2);// 365 - 1A
	    Double NJ3 = (Double) vecCumulJour.elementAt(3);// 730 - 2A
	    Double NJ4 = (Double) vecCumulJour.elementAt(4);// 1095
	    Double NJ5 = (Double) vecCumulJour.elementAt(5);// 1461
	    Double NJ6 = (Double) vecCumulJour.elementAt(6);// 1826 - 5A
	    Double NJ7 = (Double) vecCumulJour.elementAt(7);// 2191
	    Double NJ8 = (Double) vecCumulJour.elementAt(8);// 2556
	    Double NJ9 = (Double) vecCumulJour.elementAt(9);// 2922
	    Double NJ10 = (Double) vecCumulJour.elementAt(10);// 3287
	    Double NJ11 = (Double) vecCumulJour.elementAt(11);// 3652 - 10A
	    Double NJ12 = (Double) vecCumulJour.elementAt(12);
	    Double NJ13 = (Double) vecCumulJour.elementAt(13);
	    Double NJ14 = (Double) vecCumulJour.elementAt(14);
	    Double NJ15 = (Double) vecCumulJour.elementAt(15);
	    Double NJ16 = (Double) vecCumulJour.elementAt(16);// 5478 - 15A
	    Double NJ17 = (Double) vecCumulJour.elementAt(17);
	    Double NJ18 = (Double) vecCumulJour.elementAt(18);
	    Double NJ19 = (Double) vecCumulJour.elementAt(19);
	    Double NJ20 = (Double) vecCumulJour.elementAt(20);
	    Double NJ21 = (Double) vecCumulJour.elementAt(21);// 7305 - 20A
	    Double NJ22 = (Double) vecCumulJour.elementAt(22);
	    Double NJ23 = (Double) vecCumulJour.elementAt(23);
	    Double NJ24 = (Double) vecCumulJour.elementAt(24);
	    Double NJ25 = (Double) vecCumulJour.elementAt(25);
	    Double NJ26 = (Double) vecCumulJour.elementAt(26);// 25A
	    Double NJ27 = (Double) vecCumulJour.elementAt(27);
	    Double NJ28 = (Double) vecCumulJour.elementAt(28);
	    Double NJ29 = (Double) vecCumulJour.elementAt(29);
	    Double NJ30 = (Double) vecCumulJour.elementAt(30);
	    Double NJ31 = (Double) vecCumulJour.elementAt(31);// 30A
	    // Double NJ32 = (Double) vecCumulJour.elementAt(32);

	    GregorianCalendar date = stringToGregorianCalendar(maDate);
	    double tauxActu13S = arrondi(TA13S, 3);
	    // (Math.pow(1 + (TA13S * NJ0.doubleValue() *
	    // Math.pow(nombreJourBaseMonetaire, -1)), (nombreJourAnneeTa(date)
	    // * Math.pow(NJ0.doubleValue(), -1)))) - 1;
	    vecReturn.addElement(new Double(tauxActu13S));

	    double tauxActu26S = arrondi(TA26S, 3);
	    // (Math.pow(1 + (TA26S * NJ1.doubleValue() *
	    // Math.pow(nombreJourBaseMonetaire, -1)), nombreJourAnneeTa(date) *
	    // Math.pow(NJ1.doubleValue(), -1))) - 1;
	    vecReturn.addElement(new Double(tauxActu26S));

	    // double tauxActu52S = (Math.pow(1 + (TA52S * NJ2.doubleValue() *
	    // Math.pow(nombreJourBaseMonetaire, -1)), nombreJourAnneeTa(date) *
	    // Math.pow(NJ2.doubleValue(), -1))) - 1;
	    // vecReturn.addElement(new Double(tauxActu52S));

	    double tauxActu1A = arrondi(TA52S, 3);
	    // tauxActu52S + ((TA2A / 100) - tauxActu52S) *
	    // (nombreJourAnneeTa(date) - NJ2.doubleValue()) *
	    // Math.pow(NJ4.doubleValue() - NJ2.doubleValue(), -1);
	    vecReturn.addElement(new Double(tauxActu1A));

	    arrondiTA = arrondi(TA2A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    double TA = (TA5A - TA2A) * (NJ4.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA5A - TA2A) * (NJ5.doubleValue() - NJ3.doubleValue()) / (NJ6.doubleValue() - NJ3.doubleValue()) + TA2A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA5A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA10A - TA5A) * (NJ7.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ8.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ9.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA10A - TA5A) * (NJ10.doubleValue() - NJ6.doubleValue()) / (NJ11.doubleValue() - NJ6.doubleValue()) + TA5A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA10A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA15A - TA10A) * (NJ12.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ13.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ14.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA15A - TA10A) * (NJ15.doubleValue() - NJ11.doubleValue()) / (NJ16.doubleValue() - NJ11.doubleValue()) + TA10A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA15A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    TA = (TA20A - TA15A) * (NJ17.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ18.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ19.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA20A - TA15A) * (NJ20.doubleValue() - NJ16.doubleValue()) / (NJ21.doubleValue() - NJ16.doubleValue()) + TA15A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA20A, 3);
	    vecReturn.addElement(new Double(arrondiTA));

	    // TA = (TA30A - TA20A) * (NJ21.doubleValue() - NJ21.doubleValue())
	    // / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    // vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ22.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ23.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ24.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ25.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ26.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ27.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ28.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ29.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    TA = (TA30A - TA20A) * (NJ30.doubleValue() - NJ21.doubleValue()) / (NJ31.doubleValue() - NJ21.doubleValue()) + TA20A;
	    vecReturn.addElement(new Double(TA));

	    arrondiTA = arrondi(TA30A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculZeroCoupon(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A) throws IOException {
	Vector vecReturn = new Vector();
	Vector vecCoefficientActualisation = new Vector();
	try {
	    Vector vecTauxActuarial = calculTauxActuarial(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A);
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    Double myDouble = (Double) vecTauxActuarial.elementAt(2);
	    double coefficientActualisation = Math.pow(1.0D + myDouble.doubleValue() / 100.0D, -1.0D);
	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
	    for (int i = 3; i <= 21; i++) {
		Double TA = (Double) vecTauxActuarial.elementAt(i);
		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - TA.doubleValue() / 100.0D * sigma(vecCoefficientActualisation, 2, i - 1), -1.0D), Math.pow(i - 1, -1.0D)) - 1.0D) * 100.0D;
		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 1), -1.0D);
		vecReturn.addElement(new Double(ZC));
		vecCoefficientActualisation.addElement(new Double(CA));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculZeroCouponCredilog3(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A)
	    throws IOException {
	Vector vecReturn = new Vector();
	Vector vecCoefficientActualisation = new Vector();
	try {
	    Vector vecTauxActuarial = calculTauxActuarialCredilog3(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    System.out.println("VecTauxActuarial ==> " + vecTauxActuarial.toString());
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    Double myDouble = (Double) vecTauxActuarial.elementAt(2);
	    double coefficientActualisation = Math.pow(1.0D + myDouble.doubleValue() / 100.0D, -1.0D);
	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
	    for (int i = 3; i <= 31; i++) {
		Double TA = (Double) vecTauxActuarial.elementAt(i);
		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - (TA.doubleValue() / 100.0D) * sigma(vecCoefficientActualisation, 2, i - 1), -1.0D), Math.pow(i - 1, -1.0D)) - 1.0D) * 100.0D;
		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 1), -1.0D);
		vecReturn.addElement(new Double(ZC));
		vecCoefficientActualisation.addElement(new Double(CA));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static Vector calculZeroCouponCredilog4(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A)
	    throws IOException {
	Vector vecReturn = new Vector();
	Vector vecCoefficientActualisation = new Vector();
	try {
	    Vector vecTauxActuarial = calculTauxActuarialCredilog4(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    System.out.println("VecTauxActuarial ==> " + vecTauxActuarial.toString());
	    double arrondiTA = 0.0D;
	    arrondiTA = arrondi(TA13S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA1A, 3);
	    vecReturn.addElement(new Double(arrondiTA));
	    //vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    Double myDouble = (Double) vecTauxActuarial.elementAt(2);
	    double coefficientActualisation = Math.pow(1.0D + myDouble.doubleValue() / 100.0D, -1.0D);
	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
	    for (int i = 3; i <= 31; i++) {
		Double TA = (Double) vecTauxActuarial.elementAt(i);
		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - (TA.doubleValue() / 100.0D) * sigma(vecCoefficientActualisation, 2, i - 1), -1.0D), Math.pow(i - 1, -1.0D)) - 1.0D) * 100.0D;
		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 1), -1.0D);
		vecReturn.addElement(new Double(ZC));
		vecCoefficientActualisation.addElement(new Double(CA));
	    }

	    System.out.println("vec Coeffeicient >> "+vecCoefficientActualisation.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static Vector calculZeroCouponImmolv(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A)
    	    throws IOException {
    	Vector vecReturn = new Vector();
    	Vector vecCoefficientActualisation = new Vector();
    	try {
    	    Vector vecTauxBdt = calculTauxActuarialBDTImmolv(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
    	    Vector vecTauxActuarial = calculTauxActuarialImmolv(vecTauxBdt, maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
    	    System.out.println("VecTauxActuarial ==> " + vecTauxActuarial.toString());
    	    double arrondiTA = 0.0D;
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(0), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(1), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(2), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(3), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    //vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    Double myDouble = (Double) vecReturn.elementAt(3);
    	    //myDouble = arrondiSup(myDouble);
    	    double coefficientActualisation = Math.pow(1.0D + myDouble.doubleValue() / 100.0D, -1.0D);
    	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
    	    
    	    //Double myDouble3 = (Double) vecReturn.elementAt(3);
    	    //myDouble3 = arrondiSup(myDouble);
    	    //arrondiInf(myDouble);
    	    //double coefficientActualisation3 = Math.pow(1.0D + myDouble3.doubleValue() / 100.0D, -1.0D);
    	    //vecCoefficientActualisation.addElement(new Double(coefficientActualisation3));
    	    for (int i = 4; i <= 31; i++) {
    		Double TA = (Double) vecTauxActuarial.elementAt(i);
    		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - (TA.doubleValue() / 100.0D) * sigma(vecCoefficientActualisation, 3, i - 1), -1.0D), Math.pow(i - 2, -1.0D)) - 1.0D) * 100.0D;
    		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 2), -1.0D);
    		vecReturn.addElement(new Double(ZC));
    		vecCoefficientActualisation.addElement(new Double(CA));
    	    }

    	    System.out.println("vec Coeffeicient >> "+vecCoefficientActualisation.toString());
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return vecReturn;
        }
    
    
    public static Vector calculZeroCouponConsovert(String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A)
    	    throws IOException {
    	Vector vecReturn = new Vector();
    	Vector vecCoefficientActualisation = new Vector();
    	try {
    	    Vector vecTauxBdt = calculTauxActuarialBDTImmolv(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
    	    Vector vecTauxActuarial = calculTauxActuarialImmolv(vecTauxBdt, maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
    	    System.out.println("VecTauxActuarial ==> " + vecTauxActuarial.toString());
    	    double arrondiTA = 0.0D;
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(0), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(1), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(2), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    arrondiTA = arrondi((Double)vecTauxActuarial.get(3), 3);
    	    vecReturn.addElement(new Double(arrondiTA));
    	    //vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    vecCoefficientActualisation.addElement(new Double(0.0D));
    	    Double myDouble = (Double) vecReturn.elementAt(3);
    	    //myDouble = arrondiSup(myDouble);
    	    double coefficientActualisation = Math.pow(1.0D + myDouble.doubleValue() / 100.0D, -1.0D);
    	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
    	    
    	    //Double myDouble3 = (Double) vecReturn.elementAt(3);
    	    //myDouble3 = arrondiSup(myDouble);
    	    //arrondiInf(myDouble);
    	    //double coefficientActualisation3 = Math.pow(1.0D + myDouble3.doubleValue() / 100.0D, -1.0D);
    	    //vecCoefficientActualisation.addElement(new Double(coefficientActualisation3));
    	    for (int i = 4; i <= 31; i++) {
    		Double TA = (Double) vecTauxActuarial.elementAt(i);
    		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - (TA.doubleValue() / 100.0D) * sigma(vecCoefficientActualisation, 3, i - 1), -1.0D), Math.pow(i - 2, -1.0D)) - 1.0D) * 100.0D;
    		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 2), -1.0D);
    		vecReturn.addElement(new Double(ZC));
    		vecCoefficientActualisation.addElement(new Double(CA));
    	    }

    	    System.out.println("vec Coeffeicient >> "+vecCoefficientActualisation.toString());
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return vecReturn;
        }

    public static Vector calculZeroCouponSakane(String maDate, double TA13S, double TA26S, double TA52S, double TA1A, double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A)
	    throws IOException {
	Vector vecReturn = new Vector();
	Vector vecCoefficientActualisation = new Vector();
	try {
	    Vector vecTauxActuarial = calculTauxActuarialSakane(maDate, TA13S, TA26S, TA52S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    double arrondiTA = 0.0D;
	    Double tauxActuarial13S = (Double) vecTauxActuarial.elementAt(0);
	    // double arronditauxActuarial13S = tauxActuarial13S.doubleValue() *
	    // 100.0D;
	    vecReturn.addElement(new Double(tauxActuarial13S));

	    Double tauxActuarial26S = (Double) vecTauxActuarial.elementAt(1);
	    // double arronditauxActuarial26S = tauxActuarial26S.doubleValue() *
	    // 100.0D;
	    vecReturn.addElement(new Double(tauxActuarial26S));

	    // Double tauxActuarial52S = (Double) vecTauxActuarial.elementAt(2);
	    // double arronditauxActuarial52S = tauxActuarial52S.doubleValue() *
	    // 100.0D;
	    // vecReturn.addElement(new Double(arronditauxActuarial52S));

	    Double tauxActuarial1A = (Double) vecTauxActuarial.elementAt(2);
	    // double arronditauxActuarial1A = tauxActuarial1A.doubleValue() *
	    // 100.0D;
	    vecReturn.addElement(new Double(tauxActuarial1A));

	    // vecReturn.addElement(new Double(arrondiTA));
	    arrondiTA = arrondi(TA26S, 3);
	    // vecReturn.addElement(new Double(arrondiTA));
	    // vecReturn.addElement((Double) vecTauxActuarial.elementAt(2));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    vecCoefficientActualisation.addElement(new Double(0.0D));
	    // vecCoefficientActualisation.addElement(new Double(0.0D));
	    Double myDouble = (Double) vecTauxActuarial.elementAt(2);
	    double coefficientActualisation = Math.pow(1.0D + (myDouble.doubleValue() / 100.0D), -1.0D);
	    vecCoefficientActualisation.addElement(new Double(coefficientActualisation));
	    for (int i = 3; i <= 31; i++) {
		Double TA = (Double) vecTauxActuarial.elementAt(i);
		double ZC = (Math.pow((1.0D + TA.doubleValue() / 100.0D) * Math.pow(1.0D - TA.doubleValue() / 100.0D * sigma(vecCoefficientActualisation, 2, i - 1), -1.0D), Math.pow(i - 1, -1.0D)) - 1.0D) * 100.0D;
		double CA = Math.pow(Math.pow(1.0D + ZC / 100.0D, i - 1), -1.0D);
		vecReturn.addElement(new Double(ZC));
		vecCoefficientActualisation.addElement(new Double(CA));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculMaturite(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);
	    double M26S = 182.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);
	    vecReturn.addElement(new Double(M13S));
	    vecReturn.addElement(new Double(M26S));
	    for (int i = 2; i <= 21; i++) {
		vecReturn.addElement(new Double(i - 1));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculMaturiteCredilog3(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);
	    double M26S = 182.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);

	    vecReturn.addElement(new Double(M13S));
	    vecReturn.addElement(new Double(M26S));
	    for (int i = 2; i <= 31; i++) {
		vecReturn.addElement(new Double(i - 1));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static Vector calculMaturiteCredilog4(String maDate) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double M13S = 91.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);
	    double M26S = 182.0D * Math.pow(nombreJourAnnee(laDate), -1.0D);

	    vecReturn.addElement(new Double(M13S));
	    vecReturn.addElement(new Double(M26S));
	    for (int i = 2; i <= 31; i++) {
		vecReturn.addElement(new Double(i - 1));
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculTauxZeroCoupon(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A,
	    double TA10A, double TA15A, double TA20A) throws IOException {
	Vector vecReturn = new Vector();
	Vector vecTZC = new Vector();
	try {
	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String PE = (String) ParametresGeneraux.get("primeEsperee");

	    double primeEsperee = Double.parseDouble(PE);
	    Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
	    Vector vecMaturite = calculMaturite(maDate);
	    Vector vecZeroCoupon = calculZeroCoupon(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A);
	    Vector vecCumulJour = calculNombreJours(maDate);

	    double TZC0 = 0.0D;
	    Double Rang0 = (Double) vecRang.elementAt(0);
	    Double MT0 = (Double) vecMaturite.elementAt(0);
	    Double MT1 = (Double) vecMaturite.elementAt(1);
	    Double MT2 = (Double) vecMaturite.elementAt(2);
	    Double ZC0 = (Double) vecZeroCoupon.elementAt(0);
	    Double ZC1 = (Double) vecZeroCoupon.elementAt(1);
	    Double ZC2 = (Double) vecZeroCoupon.elementAt(2);
	    if (Rang0.doubleValue() < MT1.doubleValue()) {
		TZC0 = (ZC1.doubleValue() - ZC0.doubleValue()) / (MT1.doubleValue() - MT0.doubleValue()) * (Rang0.doubleValue() - MT0.doubleValue()) + ZC0.doubleValue();
		TZC0 += primeEsperee;
	    } else {
		TZC0 = (ZC2.doubleValue() - ZC1.doubleValue()) / (MT2.doubleValue() - MT1.doubleValue()) * (Rang0.doubleValue() - MT1.doubleValue()) + ZC1.doubleValue();
		TZC0 += primeEsperee;
	    }
	    vecTZC.addElement(new Double(TZC0));
	    int j = 1;
	    for (int i = 3; i < vecRang.size(); i++) {
		if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2 >= vecZeroCoupon.size()))
		    continue;
		double TZC = 0.0D;
		Double Rang = (Double) vecRang.elementAt(i);
		Double MTJ0 = (Double) vecMaturite.elementAt(j);
		Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
		Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
		Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j);
		Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j + 1);
		Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 2);
		if (Rang.doubleValue() > j) {
		    TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) / (MTJ2.doubleValue() - MTJ1.doubleValue()) * (Rang.doubleValue() - MTJ1.doubleValue()) + ZCJ1.doubleValue();
		    TZC += primeEsperee;
		} else {
		    TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) / (MTJ1.doubleValue() - MTJ0.doubleValue()) * (Rang.doubleValue() - MTJ0.doubleValue()) + ZCJ0.doubleValue();
		    TZC += primeEsperee;
		}
		vecTZC.addElement(new Double(TZC));
		j++;
	    }

	    vecTZC.addElement(new Double(0.0D));
	    vecReturn.addElement((Double) vecTZC.elementAt(0));
	    Double IRang0 = (Double) vecRang.elementAt(0);
	    Double IRang1 = (Double) vecRang.elementAt(1);
	    Double IRang2 = (Double) vecRang.elementAt(2);
	    Double IRang3 = (Double) vecRang.elementAt(3);
	    Double ITZC0 = (Double) vecTZC.elementAt(0);
	    Double ITZC1 = (Double) vecTZC.elementAt(1);
	    double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang1.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
	    vecReturn.addElement(new Double(TZC1));
	    double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang2.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
	    vecReturn.addElement(new Double(TZC2));
	    vecReturn.addElement((Double) vecTZC.elementAt(1));
	    int k = 1;
	    for (int i = 3; i < vecRang.size(); i++) {
		if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 * k + 3) && (k + 1 < vecTZC.size()) && (4 * k + 3 < vecRang.size())) {
		    Double IRang = (Double) vecRang.elementAt(i);
		    Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
		    Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
		    Double ITZCK0 = (Double) vecTZC.elementAt(k);
		    Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
		    double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue()) / (IRangK1.doubleValue() - IRangK0.doubleValue()) * (IRang.doubleValue() - IRangK0.doubleValue())
			    + ITZCK0.doubleValue();
		    vecReturn.addElement(new Double(TZCI));
		}
		if (i == 4 * k + 3) {
		    k++;
		}
	    }
	    vecReturn.addElement(new Double(0.0D));
	    vecReturn.addElement(new Double(0.0D));
	    vecReturn.addElement(new Double(0.0D));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculTauxZeroCouponCredilog4(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A,
	    double TA5A, double TA10A, double TA15A, double TA20A, double TA30A) throws IOException {
	Vector vecReturn = new Vector();
	Vector vecTZC = new Vector();
	try {
	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String PE = (String) ParametresGeneraux.get("primeEsperee");
	    double primeEsperee = Double.parseDouble(PE);
	    Vector vecRang = extraireFichierExcelCRD4(excelFileNameRang, maDate, 1);
	    Vector vecMaturite = calculMaturiteCredilog3(maDate);
	    Vector vecZeroCoupon = calculZeroCouponCredilog4(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    System.out.println("vecRang ==> " + vecRang.toString());
	    System.out.println("vecMaturite ==> " + vecMaturite.toString());
	    System.out.println("vecZeroCoupon ==> " + vecZeroCoupon.toString());
	    double TZC0 = 0.0D;
	    Double Rang0 = (Double) vecRang.elementAt(0);
	    Double MT0 = (Double) vecMaturite.elementAt(0);
	    Double MT1 = (Double) vecMaturite.elementAt(1);
	    Double MT2 = (Double) vecMaturite.elementAt(2);
	    Double ZC0 = (Double) vecZeroCoupon.elementAt(0);
	    Double ZC1 = (Double) vecZeroCoupon.elementAt(1);
	    Double ZC2 = (Double) vecZeroCoupon.elementAt(2);
	    if (Rang0.doubleValue() < MT1.doubleValue()) {
		TZC0 = (ZC1.doubleValue() - ZC0.doubleValue()) / (MT1.doubleValue() - MT0.doubleValue()) * (Rang0.doubleValue() - MT0.doubleValue()) + ZC0.doubleValue();
		//TZC0 += primeEsperee;
	    } else {
		TZC0 = (ZC2.doubleValue() - ZC1.doubleValue()) / (MT2.doubleValue() - MT1.doubleValue()) * (Rang0.doubleValue() - MT1.doubleValue()) + ZC1.doubleValue();
		//TZC0 += primeEsperee;
	    }
	    vecTZC.addElement(new Double(TZC0));
	    int j = 1;
	    for (int i = 3; i < vecRang.size(); i++) {
		if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2 >= vecZeroCoupon.size())) {
		    continue;
		}
		double TZC = 0.0D;
		Double Rang = (Double) vecRang.elementAt(i);
		Double MTJ0 = (Double) vecMaturite.elementAt(j);
		Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
		Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
		Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j);
		Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j + 1);
		Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 2);
		if (Rang.doubleValue() > j) {
		    TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) / (MTJ2.doubleValue() - MTJ1.doubleValue()) * (Rang.doubleValue() - MTJ1.doubleValue()) + ZCJ1.doubleValue();
		   // TZC += primeEsperee;
		} else {
		    TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) / (MTJ1.doubleValue() - MTJ0.doubleValue()) * (Rang.doubleValue() - MTJ0.doubleValue()) + ZCJ0.doubleValue();
		    //TZC += primeEsperee;
		}
		vecTZC.addElement(new Double(TZC));
		j++;
	    }

	    vecTZC.addElement(new Double(0.0D));
	    
	    double TZCIni = ((Double) vecTZC.elementAt(0)).doubleValue();
	    TZCIni += primeEsperee;
	    vecReturn.addElement(new Double(TZCIni));
	    
	    Double IRang0 = (Double) vecRang.elementAt(0);
	    Double IRang1 = (Double) vecRang.elementAt(1);
	    Double IRang2 = (Double) vecRang.elementAt(2);
	    Double IRang3 = (Double) vecRang.elementAt(3);
	    Double MTC0 = (Double) vecMaturite.elementAt(0);
	    Double MTC1 = (Double) vecMaturite.elementAt(1);
	    Double MTC2 = (Double) vecMaturite.elementAt(2);
	    Double MTC3 = (Double) vecMaturite.elementAt(3);
	    Double ITZC0 = (Double) vecTZC.elementAt(0);
	    Double ITZC1 = (Double) vecTZC.elementAt(1);
	    double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang1.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
	    //double TZCR1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC1.doubleValue() - MTC0.doubleValue()) * (IRang1.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
	    TZC1 += primeEsperee;
	    vecReturn.addElement(new Double(TZC1));
	    double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang2.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
	    TZC2 += primeEsperee;
	    //double TZCR2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC2.doubleValue() - MTC1.doubleValue()) * (IRang2.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
	    vecReturn.addElement(new Double(TZC2));
	    double TZC3 = ((Double) vecTZC.elementAt(1)).doubleValue();
	    TZC3 += primeEsperee;
	    vecReturn.addElement(new Double(TZC3));
	    int k = 1;
	    int compteBorne = 3;
	    int count = 0;
	    for (int i = 3; i < vecRang.size(); i++) {
		if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 * k + 3) && (k + 1 < vecTZC.size()) && (4 * k + 3 < vecRang.size())) {
		    Double IRang = (Double) vecRang.elementAt(i);
		    Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
		    Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
		    Double MK0 = (Double) vecMaturite.elementAt(compteBorne - 1);
		    Double MK1 = (Double) vecMaturite.elementAt(compteBorne);
		    Double ITZCK0 = (Double) vecTZC.elementAt(k);
		    Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
		    Double itemZC0 = (Double) vecZeroCoupon.elementAt(compteBorne - 1);
		    Double itemZC1 = (Double) vecZeroCoupon.elementAt(compteBorne);
		    double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue()) / (IRangK1.doubleValue() - IRangK0.doubleValue()) * (IRang.doubleValue() - IRangK0.doubleValue())
			    + ITZCK0.doubleValue();
		    double TZCJ = (itemZC1.doubleValue() - itemZC0.doubleValue()) / (MK1.doubleValue() - MK0.doubleValue()) * (IRang.doubleValue() - MK0.doubleValue())
			    + itemZC0.doubleValue() + primeEsperee; 
		    vecReturn.addElement(new Double(TZCJ));
		}
		if (i == 4 * k + 3) {
		    k++;
		}
		if(i== 7 || i==11 || i== 15 || i==19 || i==23 || i == 27){
		    compteBorne++;
		}
		
	    }
	    vecReturn.addElement(new Double(0.0D));
	    vecReturn.addElement(new Double(0.0D));
	    vecReturn.addElement(new Double(0.0D));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
    public static double interpolationTaux(Vector vecMaturite, Vector vecZeroCoupon, double rang){
	double result = 0.0;
	if(vecMaturite.size() == vecZeroCoupon.size()){
	    return result;
	}else{
	    Vector maturites = new Vector();
	    Vector tauxZeroCoupon = new Vector();
	    int j = 0;
	    int index=0;
	    int m= 0;
	    for(int i=0; i < vecZeroCoupon.size(); i++){
		if((Double)vecZeroCoupon.get(i) != 0.0) j++;
	    }
	    if(j == 0){
		return result;
	    }else{
		m= j;
		j=0;
		for(;index < vecZeroCoupon.size(); index++){
		    if((Double)vecZeroCoupon.get(index) != 0.0){
			maturites.add(vecMaturite.get(index));
			tauxZeroCoupon.add(vecZeroCoupon.get(index));
			j++;
		    }
		}
	    }
	    
	    for(j = 1; j < m ; j++){
		if(rang <= (Double)maturites.get(j)){
		    break;
		}
	    }
	    
	    if(j == m+1){
		j = m;
	    }
	    result = (Double)tauxZeroCoupon.get(j-1) + ((Double)tauxZeroCoupon.get(j+1) - (Double)tauxZeroCoupon.get(j-1))*(rang - (Double)maturites.get(j-1)) / ((Double)maturites.get(j) - (Double)maturites.get(j-1));
	}
	
	return result;
    }
    
	public static double interpolationTauxEnergy(Vector vecMaturite, Vector vecZeroCoupon, double rang) {
		double result = 0.0;
		if (vecMaturite.size() != vecZeroCoupon.size()) {
			return result;
		} else {
			Vector maturites = new Vector();
			Vector tauxZeroCoupon = new Vector();
			int j = 0;
			int index = 0;
			int m = 0;
			for (int i = 0; i < vecZeroCoupon.size(); i++) {
				if ((Double) vecZeroCoupon.get(i) != 0.0)
					j++;
			}
			if (j == 0) {
				return result;
			} else {
				m = j;
				j = 0;
				for (; index < vecZeroCoupon.size(); index++) {
					if ((Double) vecZeroCoupon.get(index) != 0.0) {
						maturites.add(vecMaturite.get(index));
						tauxZeroCoupon.add(vecZeroCoupon.get(index));
						j++;
					}
				}
			}

			for (j = 1; j <= m; j++) {
				if (rang <= (Double) maturites.get(j)) {
					break;
				}
			}

			if (j == m + 1) {
				j = m;
			}
			result = (Double) tauxZeroCoupon.get(j - 1)
					+ ((Double) tauxZeroCoupon.get(j) - (Double) tauxZeroCoupon.get(j - 1))
							* (rang - (Double) maturites.get(j - 1))
							/ ((Double) maturites.get(j) - (Double) maturites.get(j - 1));
		}

		return result;
	}

	 public static Vector calculTauxZeroCouponImmolv(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A,
			    double TA5A, double TA10A, double TA15A) throws IOException {
			Vector vecReturn = new Vector();
			Vector vecTZC = new Vector();
			try {
			    xmlParser ConfigFile = new xmlParser();
			    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
			    if (ParametresGeneraux == null)
				System.out.println("Probleme Parsing Xml");
			    String PE = (String) ParametresGeneraux.get("primeDeRisque");
			    double primeDeRisque = Double.parseDouble(PE);
			    Vector vecRang = extraireFichierExcelCRD4(excelFileNameRang, maDate, 1);
			    Vector vecMaturite = calculMaturiteCredilog3(maDate);
			    Vector vecZeroCoupon = calculZeroCouponImmolv(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
			    System.out.println("vecRang ==> " + vecRang.toString());
			    System.out.println("vecMaturite ==> " + vecMaturite.toString());
			    System.out.println("vecZeroCoupon ==> " + vecZeroCoupon.toString());
			    double TZC0 = 0.0D;
			    double TZCI = 0.0D;
			    
			    TZC0 = (Double)vecZeroCoupon.get(0) + ((Double)vecZeroCoupon.get(1) - (Double)vecZeroCoupon.get(0))*((Double)vecRang.get(0) - (Double)vecMaturite.get(0)) / ((Double)vecMaturite.get(1) - (Double)vecMaturite.get(0));
			    vecTZC.add(TZC0);
			    TZC0 += primeDeRisque;
			    vecReturn.add(TZC0);
			    for(int i=1; i < vecRang.size(); i++){
				double rang = (Double)vecRang.get(i);
				TZCI = interpolationTaux(vecMaturite, vecZeroCoupon, rang);
				vecTZC.add(TZCI);
				TZCI += primeDeRisque;
				vecReturn.add(TZCI);
			    }
			    
			    
//			    Double Rang0 = (Double) vecRang.elementAt(0);
//			    Double MT0 = (Double) vecMaturite.elementAt(0);
//			    Double MT1 = (Double) vecMaturite.elementAt(1);
//			    Double MT2 = (Double) vecMaturite.elementAt(2);
//			    Double ZC0 = (Double) vecZeroCoupon.elementAt(0);
//			    Double ZC1 = (Double) vecZeroCoupon.elementAt(1);
//			    Double ZC2 = (Double) vecZeroCoupon.elementAt(2);
//			    if (Rang0.doubleValue() < MT1.doubleValue()) {
//				TZC0 = (ZC1.doubleValue() - ZC0.doubleValue()) / (MT1.doubleValue() - MT0.doubleValue()) * (Rang0.doubleValue() - MT0.doubleValue()) + ZC0.doubleValue();
//				//TZC0 += primeEsperee;
//			    } else {
//				TZC0 = (ZC2.doubleValue() - ZC1.doubleValue()) / (MT2.doubleValue() - MT1.doubleValue()) * (Rang0.doubleValue() - MT1.doubleValue()) + ZC1.doubleValue();
//				//TZC0 += primeEsperee;
//			    }
//			    vecTZC.addElement(new Double(TZC0));
//			    int j = 1;
//			    for (int i = 3; i < vecRang.size(); i++) {
//				if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2 >= vecZeroCoupon.size())) {
//				    continue;
//				}
//				double TZC = 0.0D;
//				TZC = 0.0D;
//				Double Rang = (Double) vecRang.elementAt(j);
//				Double MTJ0 = (Double) vecMaturite.elementAt(j);
//				Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
//				Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
//				Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j);
//				Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j + 1);
//				Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 2);
//				if (Rang.doubleValue() > j) {
//				    TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) / (MTJ2.doubleValue() - MTJ1.doubleValue()) * (Rang.doubleValue() - MTJ1.doubleValue()) + ZCJ1.doubleValue();
//				   // TZC += primeEsperee;
//				} else {
//				    TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) / (MTJ1.doubleValue() - MTJ0.doubleValue()) * (Rang.doubleValue() - MTJ0.doubleValue()) + ZCJ0.doubleValue();
//				    //TZC += primeEsperee;
//				}
//				vecTZC.addElement(new Double(TZC));
//				j++;
//			    }

//			    vecTZC.addElement(new Double(0.0D));
//			    
//			    double TZCIni = ((Double) vecTZC.elementAt(0)).doubleValue();
//			    TZCIni += primeDeRisque;
//			    vecReturn.addElement(new Double(TZCIni));
//			    
//			    Double IRang0 = (Double) vecRang.elementAt(0);
//			    Double IRang1 = (Double) vecRang.elementAt(1);
//			    Double IRang2 = (Double) vecRang.elementAt(2);
//			    Double IRang3 = (Double) vecRang.elementAt(3);
//			    Double MTC0 = (Double) vecMaturite.elementAt(0);
//			    Double MTC1 = (Double) vecMaturite.elementAt(1);
//			    Double MTC2 = (Double) vecMaturite.elementAt(2);
//			    Double MTC3 = (Double) vecMaturite.elementAt(3);
//			    Double ITZC0 = (Double) vecTZC.elementAt(0);
//			    Double ITZC1 = (Double) vecTZC.elementAt(1);
//			    double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang1.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
//			    //double TZCR1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC1.doubleValue() - MTC0.doubleValue()) * (IRang1.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
//			    TZC1 += primeDeRisque;
//			    vecReturn.addElement(new Double(TZC1));
//			    double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang2.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
//			    TZC2 += primeDeRisque;
//			    //double TZCR2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC2.doubleValue() - MTC1.doubleValue()) * (IRang2.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
//			    vecReturn.addElement(new Double(TZC2));
//			    double TZC3 = ((Double) vecTZC.elementAt(1)).doubleValue();
//			    TZC3 += primeDeRisque;
//			    vecReturn.addElement(new Double(TZC3));
//			    int k = 1;
//			    int compteBorne = 3;
//			    int count = 0;
//			    for (int i = 3; i < vecRang.size(); i++) {
//				if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 * k + 3) && (k + 1 < vecTZC.size()) && (4 * k + 3 < vecRang.size())) {
//				    Double IRang = (Double) vecRang.elementAt(i);
//				    Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
//				    Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
//				    Double MK0 = (Double) vecMaturite.elementAt(compteBorne - 1);
//				    Double MK1 = (Double) vecMaturite.elementAt(compteBorne);
//				    Double ITZCK0 = (Double) vecTZC.elementAt(k);
//				    Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
//				    Double itemZC0 = (Double) vecZeroCoupon.elementAt(compteBorne - 1);
//				    Double itemZC1 = (Double) vecZeroCoupon.elementAt(compteBorne);
//				    double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue()) / (IRangK1.doubleValue() - IRangK0.doubleValue()) * (IRang.doubleValue() - IRangK0.doubleValue())
//					    + ITZCK0.doubleValue();
//				    double TZCJ = (itemZC1.doubleValue() - itemZC0.doubleValue()) / (MK1.doubleValue() - MK0.doubleValue()) * (IRang.doubleValue() - MK0.doubleValue())
//					    + itemZC0.doubleValue() + primeDeRisque; 
//				    vecReturn.addElement(new Double(TZCJ));
//				}
//				if (i == 4 * k + 3) {
//				    k++;
//				}
//				if(i== 7 || i==11 || i== 15 || i==19 || i==23 || i == 27){
//				    compteBorne++;
//				}
//				
//			    }
//			    vecReturn.addElement(new Double(0.0D));
//			    vecReturn.addElement(new Double(0.0D));
//			    vecReturn.addElement(new Double(0.0D));
			} catch (Exception e) {
			    e.printStackTrace();
			}
			return vecReturn;
		    }
	 
	 public static Vector calculTauxZeroCouponConsovert(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A,
			    double TA5A, double TA10A, double TA15A) throws IOException {
			Vector vecReturn = new Vector();
			Vector vecTZC = new Vector();
			try {
			    xmlParser ConfigFile = new xmlParser();
			    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
			    if (ParametresGeneraux == null)
				System.out.println("Probleme Parsing Xml");
			    String PE = (String) ParametresGeneraux.get("primeDeRisque");
			    double primeDeRisque = Double.parseDouble(PE);
			    Vector vecRang = extraireFichierExcelCRD4(excelFileNameRang, maDate, 1);
			    Vector vecMaturite = calculMaturiteCredilog3(maDate);
			    Vector vecZeroCoupon = calculZeroCouponImmolv(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A);
			    System.out.println("vecRang ==> " + vecRang.toString());
			    System.out.println("vecMaturite ==> " + vecMaturite.toString());
			    System.out.println("vecZeroCoupon ==> " + vecZeroCoupon.toString());
			    double TZC0 = 0.0D;
			    double TZCI = 0.0D;
			    
			    TZC0 = (Double)vecZeroCoupon.get(0) + ((Double)vecZeroCoupon.get(1) - (Double)vecZeroCoupon.get(0))*((Double)vecRang.get(0) - (Double)vecMaturite.get(0)) / ((Double)vecMaturite.get(1) - (Double)vecMaturite.get(0));
			    vecTZC.add(TZC0);
			    TZC0 += primeDeRisque;
			    vecReturn.add(TZC0);
			    for(int i=1; i < vecRang.size(); i++){
				double rang = (Double)vecRang.get(i);
				TZCI = interpolationTaux(vecMaturite, vecZeroCoupon, rang);
				vecTZC.add(TZCI);
				TZCI += primeDeRisque;
				vecReturn.add(TZCI);
			    }
			    
			    
//			    Double Rang0 = (Double) vecRang.elementAt(0);
//			    Double MT0 = (Double) vecMaturite.elementAt(0);
//			    Double MT1 = (Double) vecMaturite.elementAt(1);
//			    Double MT2 = (Double) vecMaturite.elementAt(2);
//			    Double ZC0 = (Double) vecZeroCoupon.elementAt(0);
//			    Double ZC1 = (Double) vecZeroCoupon.elementAt(1);
//			    Double ZC2 = (Double) vecZeroCoupon.elementAt(2);
//			    if (Rang0.doubleValue() < MT1.doubleValue()) {
//				TZC0 = (ZC1.doubleValue() - ZC0.doubleValue()) / (MT1.doubleValue() - MT0.doubleValue()) * (Rang0.doubleValue() - MT0.doubleValue()) + ZC0.doubleValue();
//				//TZC0 += primeEsperee;
//			    } else {
//				TZC0 = (ZC2.doubleValue() - ZC1.doubleValue()) / (MT2.doubleValue() - MT1.doubleValue()) * (Rang0.doubleValue() - MT1.doubleValue()) + ZC1.doubleValue();
//				//TZC0 += primeEsperee;
//			    }
//			    vecTZC.addElement(new Double(TZC0));
//			    int j = 1;
//			    for (int i = 3; i < vecRang.size(); i++) {
//				if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2 >= vecZeroCoupon.size())) {
//				    continue;
//				}
//				double TZC = 0.0D;
//				TZC = 0.0D;
//				Double Rang = (Double) vecRang.elementAt(j);
//				Double MTJ0 = (Double) vecMaturite.elementAt(j);
//				Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
//				Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
//				Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j);
//				Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j + 1);
//				Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 2);
//				if (Rang.doubleValue() > j) {
//				    TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) / (MTJ2.doubleValue() - MTJ1.doubleValue()) * (Rang.doubleValue() - MTJ1.doubleValue()) + ZCJ1.doubleValue();
//				   // TZC += primeEsperee;
//				} else {
//				    TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) / (MTJ1.doubleValue() - MTJ0.doubleValue()) * (Rang.doubleValue() - MTJ0.doubleValue()) + ZCJ0.doubleValue();
//				    //TZC += primeEsperee;
//				}
//				vecTZC.addElement(new Double(TZC));
//				j++;
//			    }

//			    vecTZC.addElement(new Double(0.0D));
//			    
//			    double TZCIni = ((Double) vecTZC.elementAt(0)).doubleValue();
//			    TZCIni += primeDeRisque;
//			    vecReturn.addElement(new Double(TZCIni));
//			    
//			    Double IRang0 = (Double) vecRang.elementAt(0);
//			    Double IRang1 = (Double) vecRang.elementAt(1);
//			    Double IRang2 = (Double) vecRang.elementAt(2);
//			    Double IRang3 = (Double) vecRang.elementAt(3);
//			    Double MTC0 = (Double) vecMaturite.elementAt(0);
//			    Double MTC1 = (Double) vecMaturite.elementAt(1);
//			    Double MTC2 = (Double) vecMaturite.elementAt(2);
//			    Double MTC3 = (Double) vecMaturite.elementAt(3);
//			    Double ITZC0 = (Double) vecTZC.elementAt(0);
//			    Double ITZC1 = (Double) vecTZC.elementAt(1);
//			    double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang1.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
//			    //double TZCR1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC1.doubleValue() - MTC0.doubleValue()) * (IRang1.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
//			    TZC1 += primeDeRisque;
//			    vecReturn.addElement(new Double(TZC1));
//			    double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue()) * (IRang2.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
//			    TZC2 += primeDeRisque;
//			    //double TZCR2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (MTC2.doubleValue() - MTC1.doubleValue()) * (IRang2.doubleValue() - MTC0.doubleValue()) + ITZC0.doubleValue();
//			    vecReturn.addElement(new Double(TZC2));
//			    double TZC3 = ((Double) vecTZC.elementAt(1)).doubleValue();
//			    TZC3 += primeDeRisque;
//			    vecReturn.addElement(new Double(TZC3));
//			    int k = 1;
//			    int compteBorne = 3;
//			    int count = 0;
//			    for (int i = 3; i < vecRang.size(); i++) {
//				if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 * k + 3) && (k + 1 < vecTZC.size()) && (4 * k + 3 < vecRang.size())) {
//				    Double IRang = (Double) vecRang.elementAt(i);
//				    Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
//				    Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
//				    Double MK0 = (Double) vecMaturite.elementAt(compteBorne - 1);
//				    Double MK1 = (Double) vecMaturite.elementAt(compteBorne);
//				    Double ITZCK0 = (Double) vecTZC.elementAt(k);
//				    Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
//				    Double itemZC0 = (Double) vecZeroCoupon.elementAt(compteBorne - 1);
//				    Double itemZC1 = (Double) vecZeroCoupon.elementAt(compteBorne);
//				    double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue()) / (IRangK1.doubleValue() - IRangK0.doubleValue()) * (IRang.doubleValue() - IRangK0.doubleValue())
//					    + ITZCK0.doubleValue();
//				    double TZCJ = (itemZC1.doubleValue() - itemZC0.doubleValue()) / (MK1.doubleValue() - MK0.doubleValue()) * (IRang.doubleValue() - MK0.doubleValue())
//					    + itemZC0.doubleValue() + primeDeRisque; 
//				    vecReturn.addElement(new Double(TZCJ));
//				}
//				if (i == 4 * k + 3) {
//				    k++;
//				}
//				if(i== 7 || i==11 || i== 15 || i==19 || i==23 || i == 27){
//				    compteBorne++;
//				}
//				
//			    }
//			    vecReturn.addElement(new Double(0.0D));
//			    vecReturn.addElement(new Double(0.0D));
//			    vecReturn.addElement(new Double(0.0D));
			} catch (Exception e) {
			    e.printStackTrace();
			}
			return vecReturn;
		    }
    
	public static Vector calculTauxZeroCouponCredilog3(
			String excelFileName, String excelFileNameRang,
			String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A,
			double TA5A,
			double TA10A, double TA15A, double TA20A, double TA30A
			
			) throws IOException { 
		
		
		Vector vecReturn = new Vector();
		Vector vecTZC = new Vector();  
		
		try {   
			
			
			xmlParser ConfigFile = new xmlParser();
			Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
			if (ParametresGeneraux == null)
				System.out.println("Probleme Parsing Xml");
			String PE = (String) ParametresGeneraux.get("primeEsperee");
			double primeEsperee = Double.parseDouble(PE); 
			
			
			
			Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
			Vector vecMaturite = calculMaturiteCredilog3(maDate);
			Vector vecZeroCoupon = calculZeroCouponCredilog3(maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A,
					TA20A, TA30A);  
			
			System.out.println("vecRang ==> " + vecRang.toString());
			System.out.println("vecMaturite ==> " + vecMaturite.toString());
			System.out.println("vecZeroCoupon ==> " + vecZeroCoupon.toString());
			
			double TZC0 = 0.0D; 
				
			Double Rang0 = (Double) vecRang.elementAt(0);
			Double MT0 = (Double) vecMaturite.elementAt(0);
			Double MT1 = (Double) vecMaturite.elementAt(1);
			Double MT2 = (Double) vecMaturite.elementAt(2);
			Double ZC0 = (Double) vecZeroCoupon.elementAt(0);
			Double ZC1 = (Double) vecZeroCoupon.elementAt(1);
			Double ZC2 = (Double) vecZeroCoupon.elementAt(2);  
			
			if (Rang0.doubleValue() < MT1.doubleValue()) {
				TZC0 = (ZC1.doubleValue() - ZC0.doubleValue()) / (MT1.doubleValue() - MT0.doubleValue())
						* (Rang0.doubleValue() - MT0.doubleValue()) + ZC0.doubleValue();
				TZC0 += primeEsperee;
			} else {
				TZC0 = (ZC2.doubleValue() - ZC1.doubleValue()) / (MT2.doubleValue() - MT1.doubleValue())
						* (Rang0.doubleValue() - MT1.doubleValue()) + ZC1.doubleValue();
				TZC0 += primeEsperee;
			} 
			
			vecTZC.addElement(new Double(TZC0));
			int j = 1;
			for (int i = 3; i < vecRang.size(); i++) {
				if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2 >= vecZeroCoupon.size())) {
					continue;
				}
				double TZC = 0.0D;
				Double Rang = (Double) vecRang.elementAt(i);
				Double MTJ0 = (Double) vecMaturite.elementAt(j);
				Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
				Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
				Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j);
				Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j + 1);
				Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 2);
				if (Rang.doubleValue() > j) {
					TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) / (MTJ2.doubleValue() - MTJ1.doubleValue())
							* (Rang.doubleValue() - MTJ1.doubleValue()) + ZCJ1.doubleValue();
					TZC += primeEsperee;
				} else {
					TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) / (MTJ1.doubleValue() - MTJ0.doubleValue())
							* (Rang.doubleValue() - MTJ0.doubleValue()) + ZCJ0.doubleValue();
					TZC += primeEsperee;
				}
				vecTZC.addElement(new Double(TZC));
				j++;
			}

			vecTZC.addElement(new Double(0.0D));
			vecReturn.addElement((Double) vecTZC.elementAt(0));
			Double IRang0 = (Double) vecRang.elementAt(0);
			Double IRang1 = (Double) vecRang.elementAt(1);
			Double IRang2 = (Double) vecRang.elementAt(2);
			Double IRang3 = (Double) vecRang.elementAt(3);
			Double ITZC0 = (Double) vecTZC.elementAt(0);
			Double ITZC1 = (Double) vecTZC.elementAt(1);
			double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue())
					* (IRang1.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
			vecReturn.addElement(new Double(TZC1));
			double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) / (IRang3.doubleValue() - IRang0.doubleValue())
					* (IRang2.doubleValue() - IRang0.doubleValue()) + ITZC0.doubleValue();
			vecReturn.addElement(new Double(TZC2));
			vecReturn.addElement((Double) vecTZC.elementAt(1));
			int k = 1;
			for (int i = 3; i < vecRang.size(); i++) {
				if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 * k + 3) && (k + 1 < vecTZC.size())
						&& (4 * k + 3 < vecRang.size())) {
					Double IRang = (Double) vecRang.elementAt(i);
					Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
					Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
					Double ITZCK0 = (Double) vecTZC.elementAt(k);
					Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
					double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue())
							/ (IRangK1.doubleValue() - IRangK0.doubleValue())
							* (IRang.doubleValue() - IRangK0.doubleValue()) + ITZCK0.doubleValue();
					vecReturn.addElement(new Double(TZCI));
				}
				if (i == 4 * k + 3) {
					k++;
				}
			}
			vecReturn.addElement(new Double(0.0D));
			vecReturn.addElement(new Double(0.0D));
			vecReturn.addElement(new Double(0.0D));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vecReturn;
	} 

    public static Vector calculTauxZeroCouponSakane(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA52S, double TA1A,
	    double TA2A, double TA5A, double TA10A, double TA15A, double TA20A, double TA30A) throws IOException {
	Vector vecReturn = new Vector();
	Vector vecTZC = new Vector();
	try {
	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String PE = (String) ParametresGeneraux.get("primeEsperee");
	    String dateTitrisation = (String) ParametresGeneraux.get("dateTitrisation");
	    double primeEsperee = Double.parseDouble(PE);
	    Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
	    Vector vecMaturite = calculMaturiteCredilog3(maDate);
	    Vector vecCumulJour = calculNombreJoursSakane(maDate);
	    Vector vecDateEcheance = extraireFichierExcel(excelFileNameRang, maDate, 4);
	    Vector vecZeroCoupon = calculZeroCouponSakane(maDate, TA13S, TA26S, TA52S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);

	    double TZC0 = 0.0D;

	    // Calcul des 3 premiers TZC
	    for (int rang = 0; rang < 3; rang++) {

		Double ZC0 = new Double(0.0);
		Double ZC1 = new Double(0.0);
		Double ZC2 = new Double(0.0);
		String dateEcheance = (String) vecDateEcheance.elementAt(rang);
		GregorianCalendar laDateEcheance = stringToGregorianCalendar(dateEcheance);
		GregorianCalendar laDateTitrisation = stringToGregorianCalendar(maDate);
		double difference = laDateEcheance.getTime().getTime() - laDateTitrisation.getTime().getTime();
		double differenceJour = difference / 86400000.0D;

		double NJ0 = ((Double) vecCumulJour.elementAt(rang)).doubleValue();
		double NJ1 = ((Double) vecCumulJour.elementAt(rang + 1)).doubleValue();
		if (rang == 0) {
		    double NJ = ((Double) vecCumulJour.elementAt(rang)).doubleValue();
		    NJ0 = ((Double) vecCumulJour.elementAt(rang)).doubleValue();
		    NJ1 = ((Double) vecCumulJour.elementAt(rang + 1)).doubleValue();
		    ZC0 = (Double) vecZeroCoupon.elementAt(rang);
		    ZC1 = (Double) vecZeroCoupon.elementAt(rang + 1);
		    TZC0 = ZC0.doubleValue() + (ZC1.doubleValue() - ZC0.doubleValue()) * (differenceJour - NJ) * Math.pow((NJ1 - NJ), -1);
		} else {
		    NJ0 = ((Double) vecCumulJour.elementAt(rang - 1)).doubleValue();
		    NJ1 = ((Double) vecCumulJour.elementAt(rang)).doubleValue();
		    ZC0 = (Double) vecZeroCoupon.elementAt(rang - 1);
		    ZC1 = (Double) vecZeroCoupon.elementAt(rang);
		    ZC2 = (Double) vecZeroCoupon.elementAt(rang + 1);
		    TZC0 = ZC0.doubleValue() + (ZC1.doubleValue() - ZC0.doubleValue()) * (differenceJour - NJ0) * Math.pow((NJ1 - NJ0), -1);
		}

		vecReturn.addElement(new Double(TZC0));
	    }

	    // On calcule les TZC des bornes (1A, 2A, 5A, ...)
	    Vector vecTauxZeroCouponDesBornes = new Vector();
	    Hashtable hashTauxZeroCouponDesBornes = new Hashtable();

	    getDatesBorne(vecDateEcheance, vecCumulJour, vecZeroCoupon, vecRang);
	    // int indexDesBornes[] = new int[10];
	    int compte = 0, compteur = 0, compteurZeroCoupon = 0, compteurMaturite = 0;
	    for (int i = 3; i < vecRang.size(); i++) {
		String dateEcheanceEnCours = (String) vecDateEcheance.elementAt(i);

		double TZC = 0.0D;

		if (contains(dateEcheanceEnCours)) {
		    GregorianCalendar laDateEcheanceEnCours = stringToGregorianCalendar(dateEcheanceEnCours);
		    GregorianCalendar dateTransaction = stringToGregorianCalendar(maDate);
		    double difference2 = laDateEcheanceEnCours.getTime().getTime() - dateTransaction.getTime().getTime();
		    double differenceJour2 = difference2 / 86400000.0D;
		    // Double ZCJ0 = (Double) vecZeroCoupon.elementAt(i - 1);
		    // Double ZCJ1 = (Double) vecZeroCoupon.elementAt(i);
		    // Double ZCJ2 = (Double) vecZeroCoupon.elementAt(i + 1);
		    // double NJi = ((Double)
		    // vecCumulJour.elementAt(i)).doubleValue();
		    // double NJk = ((Double) vecCumulJour.elementAt(i -
		    // 1)).doubleValue();
		    // double NJ2 = ((Double) vecCumulJour.elementAt(i +
		    // 1)).doubleValue();
		    // Il s'agit d'une date borne
		    int value = ((Integer) hashDatesBorne.get(dateEcheanceEnCours)).intValue();

		    if (differenceJour2 * Math.pow(nombreJourAnneeTa(dateTransaction), -1) < value) {
			Double ZCJBorne1 = new Double(zeroCouponBornes[compteurZeroCoupon]);
			Double ZCJBorne2 = new Double(zeroCouponBornes[++compteurZeroCoupon]);
			double NJBorne1 = maturitesBornes[compteurMaturite];
			double NJBorne2 = maturitesBornes[++compteurMaturite];
			TZC = ZCJBorne1.doubleValue() + (ZCJBorne2.doubleValue() - ZCJBorne1.doubleValue()) * (differenceJour2 - NJBorne1) * Math.pow(NJBorne2 - NJBorne1, -1);
			if (value == 1) {
			    compteurZeroCoupon++;
			    compteurMaturite++;
			}
		    } else {
			// String nextDate2 = datesDeBorne[compteur +1];
			Double ZCJBorne2 = new Double(zeroCouponBornes[compteurZeroCoupon]);
			Double ZCJBorne3 = new Double(zeroCouponBornes[compteurZeroCoupon + 1]);
			double NJBorne2 = maturitesBornes[compteurMaturite];
			double NJBorne3 = maturitesBornes[compteurMaturite + 1];
			TZC = ZCJBorne2.doubleValue() + (ZCJBorne3.doubleValue() - ZCJBorne2.doubleValue()) * (differenceJour2 - NJBorne2) * Math.pow(NJBorne3 - NJBorne2, -1);
		    }
		    hashTauxZeroCouponDesBornes.put(new Integer(i), new Double(TZC));
		    vecTauxZeroCouponDesBornes.addElement(new Double(TZC));
		}

	    }
	    // On calcul les TZC du reste.
	    int compteurBorne = 0;
	    for (int i = 3; i < vecRang.size(); i++) {
		String dateEcheanceEnCours = (String) vecDateEcheance.elementAt(i);
		double TZC = 0.0D;
		Double TZCBorne = (Double) hashTauxZeroCouponDesBornes.get(new Integer(i));

		if (TZCBorne != null) {
		    vecReturn.addElement(TZCBorne);
		    if (compteurBorne < rangBornes.length)
			compteurBorne++;
		    // if (compteurBorne == rangBornes.length)
		    // compteurBorne--;
		} else {
		    // TZC = ZCJ0.doubleValue() + (ZCJ1.doubleValue() -
		    // ZCJ0.doubleValue()) * (differenceJour2 - NJk) *
		    // Math.pow(NJi - NJk, -1);

		    // récupere le taux zero coupon de la premiere borne
		    double tzcBorne1 = 0.0D;
		    double tzcBorne2 = 0.0D;

		    // int nextIndexBorne = indexDesBornes[i] + 1;
		    double rangActuel = ((Double) vecRang.elementAt(i)).doubleValue();
		    // double rang2 = ((Double) vecRang.elementAt(i +
		    // 1)).doubleValue();

		    if (compteurBorne == rangBornes.length) {
			compteurBorne--;
			GregorianCalendar laDateEcheanceEnCours = stringToGregorianCalendar(dateEcheanceEnCours);
			GregorianCalendar dateTransaction = stringToGregorianCalendar(maDate);
			double difference2 = laDateEcheanceEnCours.getTime().getTime() - dateTransaction.getTime().getTime();
			double differenceJour2 = difference2 / 86400000.0D;
			double maturiteBorne = maturitesBornes[compteurBorne + 1];
			double maturiteNextBorne = maturitesBornes[compteurBorne + 2];
			tzcBorne1 = ((Double) vecTauxZeroCouponDesBornes.elementAt(compteurBorne - 1)).doubleValue();
			tzcBorne2 = ((Double) vecTauxZeroCouponDesBornes.elementAt(compteurBorne)).doubleValue();

			TZC = tzcBorne1 + (tzcBorne2 - tzcBorne1) * (differenceJour2 - maturiteBorne) * Math.pow(maturiteNextBorne - maturiteBorne, -1);
		    } else {
			tzcBorne1 = ((Double) vecTauxZeroCouponDesBornes.elementAt(compteurBorne - 1)).doubleValue();
			tzcBorne2 = ((Double) vecTauxZeroCouponDesBornes.elementAt(compteurBorne)).doubleValue();
			double rangBorne = rangBornes[compteurBorne - 1];
			double rangNextBorne = rangBornes[compteurBorne];

			TZC = tzcBorne1 + (tzcBorne2 - tzcBorne1) * (rangActuel - rangBorne) * Math.pow(rangNextBorne - rangBorne, -1);
		    }
		    vecReturn.addElement(new Double(TZC));
		}
	    }

	    // int j = 4;
	    //
	    //
	    // for (int i = 3; i < vecRang.size(); i++) {
	    // if ((i != 4 * j - 1) || (j + 2 >= vecMaturite.size()) || (j + 2
	    // >= vecZeroCoupon.size())) {
	    // continue;
	    // }
	    // double TZC = 0.0D;
	    // Double Rang = (Double) vecRang.elementAt(i);
	    // Double MTJ0 = (Double) vecMaturite.elementAt(j);
	    // Double MTJ1 = (Double) vecMaturite.elementAt(j + 1);
	    // Double MTJ2 = (Double) vecMaturite.elementAt(j + 2);
	    // Double ZCJ0 = (Double) vecZeroCoupon.elementAt(j-1);
	    // Double ZCJ1 = (Double) vecZeroCoupon.elementAt(j);
	    // Double ZCJ2 = (Double) vecZeroCoupon.elementAt(j + 1);
	    // double NJi = ((Double)vecCumulJour.elementAt(j)).doubleValue();
	    // double NJk = ((Double)vecCumulJour.elementAt(j-1)).doubleValue();
	    // double NJ2 = ((Double)vecCumulJour.elementAt(j+1)).doubleValue();
	    //
	    // String dateEcheanceEnCours =
	    // (String)vecDateEcheance.elementAt(j);
	    // GregorianCalendar laDateEcheanceEnCours =
	    // stringToGregorianCalendar(dateEcheanceEnCours);
	    // GregorianCalendar dateTransation =
	    // stringToGregorianCalendar(maDate);
	    // double difference2 = laDateEcheanceEnCours.getTime().getTime() -
	    // dateTransation.getTime().getTime();
	    // double differenceJour2 = difference2 / 86400000.0D;
	    //
	    // //Comparer s'il s'agit d'une borne
	    // if(contains(dateEcheanceEnCours)){
	    // //Il s'agit d'une date borne
	    // TZC = ((Double)hashTauxZeroCouponDesBornes.get(new
	    // Integer(j))).doubleValue();
	    // }else{
	    // TZC = ZCJ0.doubleValue() + (ZCJ1.doubleValue() -
	    // ZCJ0.doubleValue()) * (differenceJour2 - NJk)*Math.pow(NJi - NJk,
	    // -1);
	    // //récupere le taux zero coupon de la premiere borne
	    // double tzcBorne1 =
	    // ((Double)vecTauxZeroCouponDesBornes.elementAt(j)).doubleValue();
	    // double tzcBorne2 =
	    // ((Double)vecTauxZeroCouponDesBornes.elementAt(j+1)).doubleValue();
	    //
	    // int nextIndexBorne = indexDesBornes[j] + 1;
	    // double rang1 = ((Double) vecRang.elementAt(j)).doubleValue();
	    // double rang2 = ((Double)
	    // vecRang.elementAt(j+1)).doubleVl24alue();
	    // double rangNextBorne = ((Double)
	    // vecRang.elementAt(nextIndexBorne)).doubleValue();
	    //
	    // TZC = tzcBorne1 + (tzcBorne2 - tzcBorne1) * (rang2 - rang1) *
	    // Math.pow(rangNextBorne - rang1, -1);
	    // }
	    // // if (Rang.doubleValue() > j) {
	    // // TZC = (ZCJ2.doubleValue() - ZCJ1.doubleValue()) /
	    // (MTJ2.doubleValue() - MTJ1.doubleValue()) * (Rang.doubleValue() -
	    // MTJ1.doubleValue()) + ZCJ1.doubleValue();
	    // // TZC += primeEsperee;
	    // // } else {
	    // // TZC = (ZCJ1.doubleValue() - ZCJ0.doubleValue()) /
	    // (MTJ1.doubleValue() - MTJ0.doubleValue()) * (Rang.doubleValue() -
	    // MTJ0.doubleValue()) + ZCJ0.doubleValue();
	    // // TZC += primeEsperee;
	    // // }
	    // vecTZC.addElement(new Double(TZC));
	    // j++;
	    // }

	    // vecTZC.addElement(new Double(0.0D));
	    // vecReturn.addElement((Double) vecTZC.elementAt(0));
	    // Double IRang0 = (Double) vecRang.elementAt(0);
	    // Double IRang1 = (Double) vecRang.elementAt(1);
	    // Double IRang2 = (Double) vecRang.elementAt(2);
	    // Double IRang3 = (Double) vecRang.elementAt(3);
	    // Double ITZC0 = (Double) vecTZC.elementAt(0);
	    // Double ITZC1 = (Double) vecTZC.elementAt(1);
	    // double TZC1 = (ITZC1.doubleValue() - ITZC0.doubleValue()) /
	    // (IRang3.doubleValue() - IRang0.doubleValue()) *
	    // (IRang1.doubleValue() - IRang0.doubleValue()) +
	    // ITZC0.doubleValue();
	    // vecReturn.addElement(new Double(TZC1));
	    // double TZC2 = (ITZC1.doubleValue() - ITZC0.doubleValue()) /
	    // (IRang3.doubleValue() - IRang0.doubleValue()) *
	    // (IRang2.doubleValue() - IRang0.doubleValue()) +
	    // ITZC0.doubleValue();
	    // vecReturn.addElement(new Double(TZC2));
	    // vecReturn.addElement((Double) vecTZC.elementAt(1));
	    // int k = 1;
	    // for (int i = 3; i < vecRang.size(); i++) {
	    // if ((i > 4 * k - 1) && (i <= 4 * k + 3) && (vecRang.size() >= 4 *
	    // k + 3) && (k + 1 < vecTZC.size()) && (4 * k + 3 <
	    // vecRang.size())) {
	    // Double IRang = (Double) vecRang.elementAt(i);
	    // Double IRangK0 = (Double) vecRang.elementAt(4 * k - 1);
	    // Double IRangK1 = (Double) vecRang.elementAt(4 * k + 3);
	    // Double ITZCK0 = (Double) vecTZC.elementAt(k);
	    // Double ITZCK1 = (Double) vecTZC.elementAt(k + 1);
	    // double TZCI = (ITZCK1.doubleValue() - ITZCK0.doubleValue()) /
	    // (IRangK1.doubleValue() - IRangK0.doubleValue()) *
	    // (IRang.doubleValue() - IRangK0.doubleValue())
	    // + ITZCK0.doubleValue();
	    // vecReturn.addElement(new Double(TZCI));
	    // }
	    // if (i == 4 * k + 3) {
	    // k++;
	    // }
	    // }
	    // vecReturn.addElement(new Double(0.0D));
	    // vecReturn.addElement(new Double(0.0D));
	    // vecReturn.addElement(new Double(0.0D));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculPricing(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A,
	    double TA10A, double TA15A, double TA20A) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecDate = extraireFichierExcel(excelFileNameRang, maDate, 4);
	    Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
	    Vector vecCRDDebut = extraireFichierExcel(excelFileName, maDate, 2);
	    Vector vecAmortissement = extraireFichierExcel(excelFileName, maDate, 3);
	    Vector vecTauxZeroCoupon = calculTauxZeroCoupon(excelFileName, excelFileNameRang, xmlFileName, maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A);
	    for (int cpt = 0; cpt < 80; cpt++) {
		vecCRDDebut.addElement(new Double(0.0D));
		vecAmortissement.addElement(new Double(0.0D));
	    }

	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String TF = (String) ParametresGeneraux.get("tauxFiscal");
	    String PE = (String) ParametresGeneraux.get("primeEsperee");
	    double tauxFiscal = Double.parseDouble(TF);
	    double primeEsperee = Double.parseDouble(PE);
	    double FA = 0.0D;
	    System.out.println("vecTauxZeroCoupon.size() " + vecTauxZeroCoupon.size());
	    System.out.println("vecRang.size() " + vecRang.size());
	    for (int i = 0; i < vecRang.size(); i++) {
		if (i >= vecTauxZeroCoupon.size())
		    continue;
		Double Rang = (Double) vecRang.elementAt(i);
		Double CRDDebut = (Double) vecCRDDebut.elementAt(i);
		Double Amortissement = (Double) vecAmortissement.elementAt(i);
		Double TauxZeroCoupon = (Double) vecTauxZeroCoupon.elementAt(i);
		double coupon = CRDDebut.doubleValue() * tauxFiscal * Math.pow(400.0D, -1.0D);
		double RA = Math.pow(Math.pow(1.0D + TauxZeroCoupon.doubleValue() * Math.pow(100.0D, -1.0D), Rang.doubleValue()), -1.0D);
		FA += RA * (coupon + Amortissement.doubleValue());
	    }

	    Double CRDDebut0 = (Double) vecCRDDebut.elementAt(0);
	    String dateEcheancePrecedente = (String) vecDate.elementAt(0);
	    GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheancePrecedente);
	    EcheancePrecedente.add(2, -3);
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double difference = laDate.getTime().getTime() - EcheancePrecedente.getTime().getTime();
	    double differenceJour = difference / 86400000.0D;
	    double prixCouponCouru = CRDDebut0.doubleValue() * tauxFiscal * differenceJour * Math.pow(100 * nombreJourAnnee(laDate), -1.0D);
	    double prixPiedCoupon = FA - prixCouponCouru;
	    double pourcentagePrixCouponCouru = prixCouponCouru * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixPiedCoupon = prixPiedCoupon * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixGlobal = FA * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    prixPiedCoupon = arrondi(prixPiedCoupon, 2);
	    pourcentagePrixPiedCoupon = arrondi(pourcentagePrixPiedCoupon, 2);
	    prixCouponCouru = arrondi(prixCouponCouru, 2);
	    pourcentagePrixCouponCouru = arrondi(pourcentagePrixCouponCouru, 2);
	    double prixGlobal = arrondi(FA, 2);
	    pourcentagePrixGlobal = arrondi(pourcentagePrixGlobal, 2);
	    // Double CRDNominal = (Double) vecCRDDebut.elementAt(0);
	    // double crdNominal = arrondi(CRDNominal.doubleValue(), 3);
	    // Double CRDSuivant = (Double) vecCRDDebut.elementAt(1);
	    // double crdSuivant = arrondi(CRDSuivant.doubleValue(), 3);
	    vecReturn.addElement(new Double(prixPiedCoupon));
	    vecReturn.addElement(new Double(pourcentagePrixPiedCoupon));
	    vecReturn.addElement(new Double(prixCouponCouru));
	    vecReturn.addElement(new Double(pourcentagePrixCouponCouru));
	    vecReturn.addElement(new Double(prixGlobal));
	    vecReturn.addElement(new Double(pourcentagePrixGlobal));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculPricingCreditlog4(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA1A, double TA2A, double TA5A,
	    double TA10A, double TA15A, double TA20A, double TA30A, double discountMargin) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecDate = extraireFichierExcel(excelFileNameRang, maDate, 4);
	    Vector listDate = extraireFichierExcelDPE(excelFileNameRang, maDate);
	    Vector vecRang = extraireFichierExcelCRD4(excelFileNameRang, maDate, 1);
	    Vector vecCRDDebut = extraireFichierExcel(excelFileName, maDate, 2);
	    Vector vecAmortissement = extraireFichierExcel(excelFileName, maDate, 3);
	    Vector vecTauxZeroCoupon = calculTauxZeroCouponCredilog4(excelFileName, excelFileNameRang, xmlFileName, maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    for (int cpt = 0; cpt < 80; cpt++) {
		vecCRDDebut.addElement(new Double(0.0D));
		vecAmortissement.addElement(new Double(0.0D));
	    }
	    //System.out.println("vecTauxZeroCoupon ==> " + vecTauxZeroCoupon.toString());
	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String TF = (String) ParametresGeneraux.get("tauxFiscal");
	    String PE = (String) ParametresGeneraux.get("primeEsperee");
	    String dataEcheancePre = (String) ParametresGeneraux.get("dateTitrisation");
	    double tauxFacial = Double.parseDouble(TF);
	    // double tauxEmission = tauxFacial + primeEsperee;

	    double tauxFiscal = Double.parseDouble(TF) + Double.parseDouble(PE);
	    // double primeEsperee = Double.parseDouble(PE);
	    double FA = 0.0D;
	    String dateEcheancePrec = (String) vecDate.elementAt(0);
	    GregorianCalendar echeancePrecedente = stringToGregorianCalendar(dateEcheancePrec);
	    GregorianCalendar bDate = stringToGregorianCalendar(maDate);
	    String dateE = (String) vecDate.elementAt(0);
	    Vector<Double> coupons = new Vector<Double>();
	    Vector<Double> RAs = new Vector<Double>();
	    Vector<Double> FAs = new Vector<Double>();
	    for (int i = 0; i < vecRang.size(); i++) {
		if (i >= vecTauxZeroCoupon.size()) {
		    continue;
		}
		Double Rang = (Double) vecRang.elementAt(i);
		Double CRDDebut = (Double) vecCRDDebut.elementAt(i);
		Double Amortissement = (Double) vecAmortissement.elementAt(i);
		Double TauxZeroCoupon = (Double) vecTauxZeroCoupon.elementAt(i);

		double coupon = 0.0D;
		double TA = 0.0;
		if ((listDate.elementAt(0).equals(dateE)) && (i == 0)) {
		    GregorianCalendar echanceInitial = stringToGregorianCalendar(dataEcheancePre);
		    GregorianCalendar premierEchance = stringToGregorianCalendar((String) listDate.elementAt(0));
		    double difference = premierEchance.getTime().getTime() - echanceInitial.getTime().getTime();
		    double differenceJour = difference / 86400000.0D;
		    int dj = (int) differenceJour;
		    System.out.println("====== dj ======== "+dj);
		    coupon = CRDDebut.doubleValue() * tauxFiscal * dj * Math.pow(36500.0D, -1.0D);
		    coupon = arrondi(coupon, 2);
		    System.out.println("Coupon ==========="+coupon);
		} else {
		    coupon = CRDDebut.doubleValue() * tauxFiscal * Math.pow(400.0D, -1.0D);
		    System.out.println("Coupon avant arrondi inf ========== "+coupon );
		    coupon = arrondiInf(coupon);
		    System.out.println("Coupon après arrondi inf ========== "+coupon);
		    coupon = arrondi(coupon, 2);
		    System.out.println("Coupon ==========="+coupon);
		}
		// System.out.println("coupon ==> "+coupon);
		coupons.add(coupon);
		double RA = Math.pow(Math.pow(1.0D + (TauxZeroCoupon.doubleValue() + discountMargin) * Math.pow(100.0D, -1.0D), Rang.doubleValue()), -1.0D);
		// System.out.println("RA ==> "+RA);
		RAs.add(RA);
		FAs.add(RA * (coupon + Amortissement.doubleValue()));
		// System.out.println("FA ==> "+RA * (coupon +
		// Amortissement.doubleValue()));
		
		double totalFlux = arrondi((coupon + Amortissement.doubleValue()), 2);
		System.out.println("Total Flux >>>>>>>> "+totalFlux);
		System.out.println("Flux actualisé >>>>>>> "+RA * totalFlux);	
		FA += RA * totalFlux;
			
		// if(TA > 0) FA +=TA;
	    }

	    System.out.println("Vec RA => " + RAs.toString());
	    System.out.println("Vec TZC ==> " + vecTauxZeroCoupon.toString());
	    System.out.println("Vec Rang ==> " + vecRang.toString());
	    Double CRDDebut0 = (Double) vecCRDDebut.elementAt(0);
	    String dateEcheancePrecedente = (String) vecDate.elementAt(0);
	    GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheancePrecedente);
	    // GregorianCalendar aDate = stringToGregorianCalendar(maDate);

	    if (listDate.elementAt(0).equals(dateEcheancePrecedente))
		EcheancePrecedente = stringToGregorianCalendar(dataEcheancePre);
	    else {
		EcheancePrecedente.add(2, -3);
	    }
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double difference = laDate.getTime().getTime() - EcheancePrecedente.getTime().getTime();
	    double differenceJour = difference / 86400000.0D;
	    int diffJ = (int)differenceJour;
	    double rang = ((Double) vecRang.elementAt(0)).doubleValue();
	    double prixCouponCouru = CRDDebut0.doubleValue() * tauxFiscal * differenceJour * Math.pow(100 * nombreJourAnneeTa(laDate), -1.0D);
	    
	    System.out.println("----------------prix Globale ==> " + FA);
	    double prixPiedCoupon = FA - prixCouponCouru;
	    System.out.println("Prix coupon couru >>>> "+prixCouponCouru);
	    System.out.println("Pied de coupn >>>> "+prixPiedCoupon);
	    
	    double pourcentagePrixCouponCouru = prixCouponCouru * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixPiedCoupon = prixPiedCoupon * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixGlobal = FA * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    //prixPiedCoupon = prixPiedCoupon;
	    //pourcentagePrixPiedCoupon = arrondi(pourcentagePrixPiedCoupon, 2);
	    prixCouponCouru = arrondi(prixCouponCouru, 2);
	    System.out.println("Pied de coupn >>>> "+pourcentagePrixPiedCoupon);
	    pourcentagePrixCouponCouru = arrondi(pourcentagePrixCouponCouru, 2);
	    double prixGlobal = FA;
	    pourcentagePrixGlobal = arrondi(pourcentagePrixGlobal, 2);
	    Double CRDNominal = (Double) vecCRDDebut.elementAt(0);
	    double crdNominal = arrondi(CRDNominal.doubleValue(), 3);
	    Double CRDSuivant = (Double) vecCRDDebut.elementAt(1);
	    double crdSuivant = arrondi(CRDSuivant.doubleValue(), 3);
	    vecReturn.addElement(new Double(prixPiedCoupon));
	    vecReturn.addElement(new Double(pourcentagePrixPiedCoupon));
	    vecReturn.addElement(new Double(prixCouponCouru));
	    vecReturn.addElement(new Double(pourcentagePrixCouponCouru));
	    vecReturn.addElement(new Double(prixGlobal));
	    vecReturn.addElement(new Double(pourcentagePrixGlobal));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector calculPricingSakane(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double TA13S, double TA26S, double TA52S, double TA1A, double TA2A,
	    double TA5A, double TA10A, double TA15A, double TA20A, double TA30A, double primeEsperee) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecDate = extraireFichierExcel(excelFileNameRang, maDate, 4);
	    Vector listDate = extraireFichierExcelDPE(excelFileNameRang, maDate);
	    Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
	    Vector vecCRDDebut = extraireFichierExcel(excelFileName, maDate, 2);
	    Vector vecAmortissement = extraireFichierExcel(excelFileName, maDate, 3);
	    Vector vecTauxZeroCoupon = calculTauxZeroCouponSakane(excelFileName, excelFileNameRang, xmlFileName, maDate, TA13S, TA26S, TA52S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A);
	    for (int cpt = 0; cpt < 80; cpt++) {
		vecCRDDebut.addElement(new Double(0.0D));
		vecAmortissement.addElement(new Double(0.0D));
	    }

	    xmlParser ConfigFile = new xmlParser();
	    Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING");
	    if (ParametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String TF = (String) ParametresGeneraux.get("tauxFiscal");
	    String dataEcheancePre = (String) ParametresGeneraux.get("dateTitrisation");
	    double tauxFacial = Double.parseDouble(TF);

	    double tauxEmission = tauxFacial + primeEsperee;
	    double FA = 0.0D;
	    GregorianCalendar bDate = stringToGregorianCalendar(maDate);
	    String dateE = (String) vecDate.elementAt(0);
	    for (int i = 0; i < vecRang.size(); i++) {
		if (i >= vecTauxZeroCoupon.size()) {
		    continue;
		}
		Double Rang = (Double) vecRang.elementAt(i);
		Double CRDDebut = (Double) vecCRDDebut.elementAt(i);
		Double Amortissement = (Double) vecAmortissement.elementAt(i);
		Double TauxZeroCoupon = (Double) vecTauxZeroCoupon.elementAt(i);

		double coupon = 0.0D;
		double TA = 0.0;
		coupon = CRDDebut.doubleValue() * tauxEmission * Math.pow(400D, -1);

		double a = 1.0D + (TauxZeroCoupon.doubleValue() / 100.0D) + (primeEsperee / 100.0D);
		TA = (Amortissement.doubleValue() + coupon) / Math.pow(a, Rang.doubleValue());
		if (TA > 0)
		    FA += TA;
	    }

	    Double CRDDebut0 = (Double) vecCRDDebut.elementAt(0);
	    String dateEcheancePrecedente = (String) vecDate.elementAt(0);
	    GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheancePrecedente);
	    // GregorianCalendar aDate = stringToGregorianCalendar(maDate);
	    if (listDate.elementAt(0).equals(dateEcheancePrecedente))
		EcheancePrecedente = stringToGregorianCalendar(dataEcheancePre);
	    else {
		EcheancePrecedente.add(2, -3);
	    }
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double difference = laDate.getTime().getTime() - EcheancePrecedente.getTime().getTime();
	    double differenceJour = difference / 86400000.0D;
	    double rang = ((Double) vecRang.elementAt(0)).doubleValue();
	    double prixCouponCouru = CRDDebut0.doubleValue() * (tauxEmission * Math.pow(100.0D, -1)) * differenceJour * Math.pow(nombreJourAnnee(bDate), -1.0D);

	    double prixPiedCoupon = FA - prixCouponCouru;
	    double pourcentagePrixCouponCouru = prixCouponCouru * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixPiedCoupon = prixPiedCoupon * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    double pourcentagePrixGlobal = FA * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
	    prixPiedCoupon = arrondi(prixPiedCoupon, 2);
	    pourcentagePrixPiedCoupon = arrondi(pourcentagePrixPiedCoupon, 2);
	    prixCouponCouru = arrondi(prixCouponCouru, 2);
	    pourcentagePrixCouponCouru = arrondi(pourcentagePrixCouponCouru, 2);
	    double prixGlobal = arrondi(FA, 2);
	    pourcentagePrixGlobal = arrondi(pourcentagePrixGlobal, 2);
	    
	    
	    vecReturn.addElement(new Double(prixPiedCoupon));
	    vecReturn.addElement(new Double(pourcentagePrixPiedCoupon));
	    vecReturn.addElement(new Double(prixCouponCouru));
	    vecReturn.addElement(new Double(pourcentagePrixCouponCouru));
	    vecReturn.addElement(new Double(prixGlobal));
	    vecReturn.addElement(new Double(pourcentagePrixGlobal)); 
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }
    
	public static Vector calculPricingCreditlog3( 
			
			String excelFileName,
			String excelFileNameRang,
			String xmlFileName,
			String maDate, 
			
			double TA13S, double TA26S, double TA1A, double TA2A, 
			double TA5A, double TA10A,
			double TA15A, double TA20A, double TA30A, double discountMargin)
	
					throws IOException {
		Vector vecReturn = new Vector(); 
		
		try { 

           // récupérer les colonnes des fichiers écheanciers prévionnels  
			Vector vecDate = extraireFichierExcel(excelFileNameRang, maDate, 4);

			Vector listDate = extraireFichierExcelDPE(excelFileNameRang, maDate);
			Vector vecRang = extraireFichierExcel(excelFileNameRang, maDate, 1);
			
			Vector vecCRDDebut = extraireFichierExcel(excelFileName, maDate, 2);
			Vector vecAmortissement = extraireFichierExcel(excelFileName, maDate, 3); 
			
			// 
			Vector vecTauxZeroCoupon = calculTauxZeroCouponCredilog3(excelFileName, excelFileNameRang, xmlFileName,
					maDate, TA13S, TA26S, TA1A, TA2A, TA5A, TA10A, TA15A, TA20A, TA30A); 
			
			// oumjahd : compléter le fichier avec des valeurs nulles 
			for (int cpt = 0; cpt < 80; cpt++) { 
				
				vecCRDDebut.addElement(new Double(0.0D));
				vecAmortissement.addElement(new Double(0.0D)); 
				
			}  
			
			
			// System.out.println("vecTauxZeroCoupon ==> " +
			// vecTauxZeroCoupon.toString()); 
			
			
			// oumjahd : ÇA C 1 HASHTABLE 
			xmlParser ConfigFile = new xmlParser(); 
			
			// oumjahd : Lecture de fihcier XML et récupération des valeurs configurés par l'admin  
			Hashtable ParametresGeneraux = ConfigFile.GetParameters(xmlFileName, "PRICING"); 
			
			if (ParametresGeneraux == null)
				System.out.println("Probleme Parsing Xml"); 

			String TF = (String) ParametresGeneraux.get("tauxFiscal");
			String PE = (String) ParametresGeneraux.get("primeEsperee");
			String dataEcheancePre = (String) ParametresGeneraux.get("dateTitrisation"); 
			
			
			double tauxFacial = Double.parseDouble(TF);
			// double tauxEmission = tauxFacial + primeEsperee;

			double tauxFiscal = Double.parseDouble(TF); 
			// double primeEsperee = Double.parseDouble(PE); 
			
			
			
			double FA = 0.0D; 
			
			String dateEcheancePrec = (String) vecDate.elementAt(0);   
			
			GregorianCalendar echeancePrecedente = stringToGregorianCalendar(dateEcheancePrec); 
			
			GregorianCalendar bDate = stringToGregorianCalendar(maDate);   
			
			
			String dateE = (String) vecDate.elementAt(0);   
			
			Vector<Double> coupons = new Vector<Double>();
			Vector<Double> RAs = new Vector<Double>();
			Vector<Double> FAs = new Vector<Double>();   
			
			
			for (int i = 0; i < vecRang.size(); i++) { 
				
				if (i >= vecTauxZeroCoupon.size()) {
					continue;
				} 
				
				Double Rang = (Double) vecRang.elementAt(i);
				Double CRDDebut = (Double) vecCRDDebut.elementAt(i);
				Double Amortissement = (Double) vecAmortissement.elementAt(i);
				Double TauxZeroCoupon = (Double) vecTauxZeroCoupon.elementAt(i);

				double coupon = 0.0D;
				double TA = 0.0;
				// coupon = CRDDebut.doubleValue() * tauxEmission *
				// Math.pow(400D, -1);

				// double a = 1.0D + (TauxZeroCoupon.doubleValue() / 100.0D) +
				// (primeEsperee / 100.0D);
				//
				// TA = (Amortissement.doubleValue() + coupon) / Math.pow(a,
				// Rang.doubleValue());

				if ((listDate.elementAt(0).equals(dateE)) && (i == 0)) {
					GregorianCalendar echanceInitial = stringToGregorianCalendar(dataEcheancePre);
					GregorianCalendar premierEchance = stringToGregorianCalendar((String) listDate.elementAt(0));
					double difference = premierEchance.getTime().getTime() - echanceInitial.getTime().getTime();
					double differenceJour = difference / 86400000.0D;
					coupon = CRDDebut.doubleValue() * tauxFiscal * differenceJour * Math.pow(36000.0D, -1.0D);
				} else {
					coupon = CRDDebut.doubleValue() * tauxFiscal * Math.pow(400.0D, -1.0D);
				}
				// System.out.println("coupon ==> "+coupon);
				coupons.add(coupon);
				double RA = Math
						.pow(Math.pow(1.0D + (TauxZeroCoupon.doubleValue() + discountMargin) * Math.pow(100.0D, -1.0D),
								Rang.doubleValue()), -1.0D);
				// System.out.println("RA ==> "+RA);
				RAs.add(RA);
				FAs.add(RA * (coupon + Amortissement.doubleValue()));
				// System.out.println("FA ==> "+RA * (coupon +
				// Amortissement.doubleValue()));
				FA += RA * (coupon + Amortissement.doubleValue());

				// if(TA > 0) FA +=TA;
			}

			System.out.println("Vec RA => " + RAs.toString());
			System.out.println("Vec TZC ==> " + vecTauxZeroCoupon.toString());
			System.out.println("Vec Rang ==> " + vecRang.toString());
			Double CRDDebut0 = (Double) vecCRDDebut.elementAt(0);
			String dateEcheancePrecedente = (String) vecDate.elementAt(0);
			GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheancePrecedente);
			// GregorianCalendar aDate = stringToGregorianCalendar(maDate);

			if (listDate.elementAt(0).equals(dateEcheancePrecedente))
				EcheancePrecedente = stringToGregorianCalendar(dataEcheancePre);
			else {
				EcheancePrecedente.add(2, -3);
			}
			GregorianCalendar laDate = stringToGregorianCalendar(maDate);
			double difference = laDate.getTime().getTime() - EcheancePrecedente.getTime().getTime();
			double differenceJour = difference / 86400000.0D;

			double rang = ((Double) vecRang.elementAt(0)).doubleValue();
			double prixCouponCouru = CRDDebut0.doubleValue() * tauxFiscal * differenceJour
					* Math.pow(100 * nombreJourAnnee(laDate), -1.0D);
			System.out.println("----------------prix Globale ==> " + FA);
			double prixPiedCoupon = FA - prixCouponCouru;
			double pourcentagePrixCouponCouru = prixCouponCouru * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
			double pourcentagePrixPiedCoupon = prixPiedCoupon * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
			double pourcentagePrixGlobal = FA * Math.pow(CRDDebut0.doubleValue(), -1.0D) * 100.0D;
			prixPiedCoupon = arrondi(prixPiedCoupon, 2);
			pourcentagePrixPiedCoupon = arrondi(pourcentagePrixPiedCoupon, 2);
			prixCouponCouru = arrondi(prixCouponCouru, 2);
			pourcentagePrixCouponCouru = arrondi(pourcentagePrixCouponCouru, 2);
			double prixGlobal = FA;
			pourcentagePrixGlobal = arrondi(pourcentagePrixGlobal, 2);
			Double CRDNominal = (Double) vecCRDDebut.elementAt(0);
			double crdNominal = arrondi(CRDNominal.doubleValue(), 3);
			Double CRDSuivant = (Double) vecCRDDebut.elementAt(1);
			double crdSuivant = arrondi(CRDSuivant.doubleValue(), 3); 
			
			
			
			vecReturn.addElement(new Double(prixPiedCoupon));
			vecReturn.addElement(new Double(pourcentagePrixPiedCoupon));
			vecReturn.addElement(new Double(prixCouponCouru));
			vecReturn.addElement(new Double(pourcentagePrixCouponCouru));
			vecReturn.addElement(new Double(prixGlobal));
			vecReturn.addElement(new Double(pourcentagePrixGlobal)); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vecReturn;
	}
    
    public static double calculPricingFpctTitrit(String xmlFileName, String dateChoisie, double TA13S, double TA26S, double TA52S, double TA1A) {
	xmlParser configFile = new xmlParser();
	Hashtable parametresGeneraux;
	double prixGlobal = 0.0D;
	double[] maturite = { 91, 182, 364, 365 };
	try {
	    parametresGeneraux = configFile.GetParameters(xmlFileName, "PRICING");
	    if (parametresGeneraux == null)
		System.out.println("Probleme Parsing Xml");
	    String tauxFacial = (String) parametresGeneraux.get("tauxFacial");
	    String primeDeRisque = (String) parametresGeneraux.get("primeDeRisque");
	    String dateEmission = (String) parametresGeneraux.get("dateEmission");
	    String dateEcheance = (String) parametresGeneraux.get("dateEcheance");
	    String montantARembourser = (String) parametresGeneraux.get("montantARembourser");
	    GregorianCalendar maDateChoisie = stringToGregorianCalendar(dateChoisie);
	    GregorianCalendar dateDemission = stringToGregorianCalendar(dateEmission);
	    GregorianCalendar dateDecheance = stringToGregorianCalendar(dateEcheance);

	    Double valeurMontantARembourser = Double.parseDouble(montantARembourser);
	    Double valeurTauxFacial = Double.parseDouble(tauxFacial);
	    Double valeurPrimeDeRisque = Double.parseDouble(primeDeRisque);
	    Double valeurTauxRendement = 0.0;

	    double difference = dateDecheance.getTime().getTime() - dateDemission.getTime().getTime();
	    double differenceJour = difference / 86400000.0D;

	    Double interets = valeurTauxFacial * valeurMontantARembourser * differenceJour * Math.pow(36000.0D, -1);

	    Double echeance = interets + valeurMontantARembourser;

	    // Calcul de la maturite residelentielle
	    double difference2 = maDateChoisie.getTime().getTime() - dateDemission.getTime().getTime();
	    double differenceJour2 = difference2 / 86400000.0D;

	    Double maturiteResidentielle = differenceJour - differenceJour2;
	    double maturite1 = 0;
	    double maturite2 = 0;
	    double tauxAct2 = 0;
	    double tauxAct1 = 0;
	    double txRendement = 0;

	    if (maturiteResidentielle > maturite[3] && maturiteResidentielle < maturite[2]) {
		maturite1 = maturite[2];
		maturite2 = maturite[3];
		tauxAct1 = TA52S;
		tauxAct2 = TA1A;
	    } else if (maturiteResidentielle > maturite[1] && maturiteResidentielle < maturite[2]) {
		maturite1 = maturite[1];
		maturite2 = maturite[2];
		tauxAct1 = TA26S;
		tauxAct2 = TA52S;
	    } else if (maturiteResidentielle > maturite[0] && maturiteResidentielle < maturite[1]) {
		maturite1 = maturite[0];
		maturite2 = maturite[1];
		tauxAct1 = TA13S;
		tauxAct2 = TA26S;
	    } else if (maturiteResidentielle <= maturite[0]) {
		tauxAct1 = TA13S;
		tauxAct2 = TA13S;
		maturite1 = maturite[0];
		maturite2 = maturite[1];
	    } else if(maturiteResidentielle == maturite[1]){
		tauxAct1 = TA26S;
		tauxAct2 = TA26S;
		maturite1 = maturite[2];
		maturite2 = maturite[1];
	    } else if(maturiteResidentielle == maturite[2]){
		tauxAct1 = TA52S;
		tauxAct2 = TA52S;
		maturite1 = maturite[2];
		maturite2 = maturite[1];		
	    }

	    txRendement = (((tauxAct1/100.0) - (tauxAct2/100.0)) * (maturiteResidentielle - maturite1) * Math.pow((maturite1 - maturite2), -1)) + (tauxAct1/100.0);
	    valeurTauxRendement = txRendement + (valeurPrimeDeRisque/100.0);

	    double alpha = 1 + (valeurTauxRendement * maturiteResidentielle * Math.pow(360.0D, -1));

	    prixGlobal = echeance * Math.pow(alpha, -1);

	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return prixGlobal;
    }
    
	public static double calculPricingFtEnergy(String xmlFileName, String dateChoisie, double TA13S, double TA26S,
			double TA52S, double TA1A) {
		xmlParser configFile = new xmlParser();
		Hashtable parametresGeneraux;
		double prixGlobal = 0.0D;
		double[] maturite = { 91, 182, 364, 365 };
		try {
			parametresGeneraux = configFile.GetParameters(xmlFileName, "PRICING");
			if (parametresGeneraux == null)
				System.out.println("Probleme Parsing Xml");
			String tauxFacial = (String) parametresGeneraux.get("tauxFacial");
			String primeDeRisque = (String) parametresGeneraux.get("primeDeRisque");
			String dateEmission = (String) parametresGeneraux.get("dateEmission");
			String dateEcheance = (String) parametresGeneraux.get("dateEcheance");
			String montantARembourser = (String) parametresGeneraux.get("montantARembourser");
			GregorianCalendar maDateChoisie = stringToGregorianCalendar(dateChoisie);
			GregorianCalendar dateDemission = stringToGregorianCalendar(dateEmission);
			GregorianCalendar dateDecheance = stringToGregorianCalendar(dateEcheance);

			Double valeurMontantARembourser = Double.parseDouble(montantARembourser);
			Double valeurTauxFacial = Double.parseDouble(tauxFacial);
			Double valeurPrimeDeRisque = Double.parseDouble(primeDeRisque);
			Double valeurTauxRendement = 0.0, valeurTauxActualisation = 0.0;

			double difference = dateDecheance.getTime().getTime() - dateDemission.getTime().getTime();
			double differenceJour = difference / 86400000.0D;

			Double interets = valeurTauxFacial * valeurMontantARembourser * differenceJour * Math.pow(36000.0D, -1);

			Double interetsArr = arrondiInf(interets);
			
			Double echeance = interetsArr + valeurMontantARembourser;

			// Calcul de la maturite residelentielle
			double difference2 = maDateChoisie.getTime().getTime() - dateDemission.getTime().getTime();

			double differenceJour2 = difference2 / 86400000.0D;

			Double[] arr = {25.0, 59.0, 62.0, 68.0, 154.0, 237.0, 402.0, 647.0, 685.0, 1374.0, 1743.0, 3262.0, 4301.0, 5033.0, 7172.0, 10808.0};
			
			Double[] tmp = {0.0244, 0.02443, 0.0245, 0.02458, 0.02467, 0.02518, 0.02622, 0.02685, 0.0268, 0.02983, 0.0310, 0.03452, 0.0372, 0.03907, 0.0431, 0.04975 };
			Vector<Double> vectorMaturite = new Vector<Double>(Arrays.asList(arr));
			Vector<Double> vectorTmp = new Vector<Double>(Arrays.asList(tmp));
			
			Double maturiteResiduelle = differenceJour - differenceJour2;
			//Double TZCI = interpolationTauxEnergy(vectorMaturite, vectorTmp, maturiteResiduelle);
			double maturite1 = 0;
			double maturite2 = 0;
			double tauxAct2 = 0;
			double tauxAct1 = 0;
			double txRendement = 0, txActualisation = 0;

//			if (maturiteResiduelle > maturite[3] && maturiteResiduelle < maturite[2]) {
//				maturite1 = maturite[2];
//				maturite2 = maturite[3];
//				tauxAct1 = TA52S;
//				tauxAct2 = TA1A;
//			} else if (maturiteResiduelle > maturite[1] && maturiteResiduelle < maturite[2]) {
//				maturite1 = maturite[1];
//				maturite2 = maturite[2];
//				tauxAct1 = TA26S;
//				tauxAct2 = TA52S;
//			} else if (maturiteResiduelle > maturite[0] && maturiteResiduelle < maturite[1]) {
//				maturite1 = maturite[0];
//				maturite2 = maturite[1];
//				tauxAct1 = TA13S;
//				tauxAct2 = TA26S;
//			} else if (maturiteResiduelle <= maturite[0]) {
//				tauxAct1 = TA13S;
//				tauxAct2 = TA13S;
//				maturite1 = maturite[0];
//				maturite2 = maturite[1];
//			} else if (maturiteResiduelle == maturite[1]) {
//				tauxAct1 = TA26S;
//				tauxAct2 = TA26S;
//				maturite1 = maturite[2];
//				maturite2 = maturite[1];
//			} else if (maturiteResiduelle == maturite[2]) {
//				tauxAct1 = TA52S;
//				tauxAct2 = TA52S;
//				maturite1 = maturite[2];
//				maturite2 = maturite[1];
//			}

//			txRendement = (((tauxAct1 / 100.0) - (tauxAct2 / 100.0)) * (maturiteResiduelle - maturite1)
//					* Math.pow((maturite1 - maturite2), -1)) + (tauxAct1 / 100.0);
			
			txRendement = interpolationTauxEnergy(vectorMaturite, vectorTmp, maturiteResiduelle);
			txActualisation = Math.pow((1 + txRendement * maturiteResiduelle / 360.0D), (365.0D/maturiteResiduelle)) - 1;
			
			valeurTauxRendement = txRendement + (valeurPrimeDeRisque / 100.0);
			valeurTauxActualisation = txActualisation + (valeurPrimeDeRisque / 100.00);

			double alpha = 1 + (valeurTauxActualisation * maturiteResiduelle * Math.pow(360.0D, -1));

			prixGlobal = echeance * Math.pow(alpha, -1);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prixGlobal;
	}

    public static Vector calculPricingFccBiatCredimmo1(String excelFileName, String excelFileNameRang, String xmlFileName, String maDate, double taux) throws IOException {
	Vector vecReturn = new Vector();
	try {
	    Vector vecDate = extraireFichierExcel(excelFileNameRang, maDate, 4);
	    Vector vecCRDDebut = extraireFichierExcel(excelFileName, maDate, 2);
	    Double CRDNominal = (Double) vecCRDDebut.elementAt(0);
	    double crdNominal = arrondi(CRDNominal.doubleValue(), 3);
	    Double CRDSuivant = (Double) vecCRDDebut.elementAt(1);
	    double crdSuivant = arrondi(CRDSuivant.doubleValue(), 3);
	    Double CRDDebut0 = (Double) vecCRDDebut.elementAt(0);
	    String dateEcheancePrecedente = (String) vecDate.elementAt(0);
	    GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheancePrecedente);
	    EcheancePrecedente.add(2, -3);
	    GregorianCalendar laDate = stringToGregorianCalendar(maDate);
	    double difference = laDate.getTime().getTime() - EcheancePrecedente.getTime().getTime();
	    double differenceJour = difference / 86400000.0D;
	    double prixCouponCouru = CRDDebut0.doubleValue() * taux * differenceJour * Math.pow(36500.0D, -1.0D);
	    prixCouponCouru = arrondi(prixCouponCouru, 3);
	    double prixPleinCoupon = crdNominal + prixCouponCouru;
	    double pourcentagePleinCoupon = prixPleinCoupon / crdNominal * 100.0D;
	    pourcentagePleinCoupon = arrondi(pourcentagePleinCoupon, 3);
	    vecReturn.addElement(new Double(crdNominal));
	    vecReturn.addElement(new Double(crdSuivant));
	    vecReturn.addElement(new Double(prixCouponCouru));
	    vecReturn.addElement(new Double(prixPleinCoupon));
	    vecReturn.addElement(new Double(pourcentagePleinCoupon));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static Vector simulationDateTitrisation(String fichierEcheancier) throws IOException {
	
    	Vector vecReturn = new Vector();
	try { 
		
	    LnetDate myDate = new LnetDate();
	    String dateDuJour = myDate.RenvoieDate("/");
	    Vector vecDate = extraireFichierExcel(fichierEcheancier, dateDuJour, 4);
	    String dateEcheanceSuivante = (String) vecDate.elementAt(0);
	    GregorianCalendar EcheanceSuivante = stringToGregorianCalendar(dateEcheanceSuivante);
	    GregorianCalendar EcheancePrecedente = stringToGregorianCalendar(dateEcheanceSuivante);
	    EcheancePrecedente.add(GregorianCalendar.MONTH, -3);
	    int sizeEcheance = 90;
	    if(dateEcheanceSuivante.equals("06/04/2015")){
		EcheancePrecedente.add(GregorianCalendar.DAY_OF_YEAR, -15);
		sizeEcheance = 105;
	    } 
	    
	    int month = GregorianCalendar.MONTH;
	    int moisEcheancePrecedente = EcheancePrecedente.get(2) + 1;
	    
	    vecReturn.addElement(EcheancePrecedente.get(GregorianCalendar.DAY_OF_MONTH) + "/" + moisEcheancePrecedente + "/" + EcheancePrecedente.get(GregorianCalendar.YEAR));
	    
	    for (int i = 0; i <= sizeEcheance; i++) {
		if (!EcheanceSuivante.after(EcheancePrecedente))
		    continue;
		EcheancePrecedente.add(5, 1);
		if (EcheanceSuivante.equals(EcheancePrecedente))
		    continue;
		int jourTitrisation = EcheancePrecedente.get(5);
		int moisTitrisation = EcheancePrecedente.get(2) + 1;
		int anneeTitrisation = EcheancePrecedente.get(1);
		String dateTitrisation = jourTitrisation + "/" + moisTitrisation + "/" + anneeTitrisation;
		vecReturn.addElement(dateTitrisation);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return vecReturn;
    }

    public static String getFirstDate(String fileName, String date) {
	Vector vecDate;
	String dateReturn = "";
	try {
		
	    vecDate = extraireFichierExcel(fileName, date, 4);
	    Vector vecAmortissement = extraireFichierExcel(fileName, date, 3);
	    dateReturn = vecDate.get(0).toString();
	    String firstAmortissement = vecAmortissement.get(0).toString();
	    if ("0.0".equals(firstAmortissement)) {
		for (int i = 0; i < vecAmortissement.size(); i++) {
		    if (!vecAmortissement.get(i).toString().equals("0.0")) {
			dateReturn = vecDate.get(i).toString();
			break;
		    }
		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return dateReturn;
    }

    public static String getLastDate(String fileName, String date) {
	Vector vecDate;
	String dateReturn = "";
	try {
	    vecDate = extraireFichierExcel(fileName, date, 4);
	    dateReturn = vecDate.lastElement().toString();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return dateReturn;
    }

    /**
     * 
     * @param fileName
     *            : Nom du fichier
     * @param date
     *            : date de trasaction utilisée pour les calculs
     * @return Date de la dernière valeur du CRD différente de 0
     */ 
    
    // j'avais fait des modif là, voir l'ancien méthode 
    public static String getLastDateS(String fileName, String date) {
		Vector vecDate;
		String dateReturn = "";
		try {
			vecDate = extraireFichierExcel(fileName, date, 4);
			Vector vecCRD = extraireFichierExcel(fileName, date, 2);
			for (int index = 0; index < vecCRD.size(); index++) {
				double d = Double.parseDouble(vecCRD.get(index).toString());
				if (d >= 0.0D && d < 1.0D) {
					dateReturn = vecDate.get(index).toString();
					//break;
				}
			}
			if ("".equals(dateReturn)) {
				dateReturn = vecDate.get(vecCRD.size() - 1).toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return dateReturn;
	}
    
/*    
    //je vais commenter cette version en attendant 
    public static String getLastDateS(String fileName, String date) {
	Vector vecDate;
	String dateReturn = "";
	try {
	    vecDate = extraireFichierExcel(fileName, date, 4);
	    Vector vecCRD = extraireFichierExcel(fileName, date, 2);
	    for (int index = 0; index < vecCRD.size(); index++) {
		double d = Double.parseDouble(vecCRD.get(index).toString());
		if (d >= 0.0D && d < 1.0D) {
		    dateReturn = vecDate.get(index - 1).toString();
		    break;
		}
	    }
	    if ("".equals(dateReturn)) {
		dateReturn = vecDate.get(vecCRD.size() - 1).toString();
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return dateReturn;
    }*/

    public static String getDateDebutS(String fileName, String date) {
	Vector vecDate;
	String dateReturn = "";
	try {
	    vecDate = extraireFichierExcel(fileName, date, 4);
	    Vector vecAmortissement = extraireFichierExcel(fileName, date, 3);
	    for (int index = 0; index < vecAmortissement.size(); index++) {
		if (vecAmortissement.get(index).toString().equals("0.0")) {
		    dateReturn = vecDate.get(index).toString();
		    break;
		}
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return dateReturn;
    }

    
}
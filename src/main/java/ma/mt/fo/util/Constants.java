package ma.mt.fo.util;

import java.io.File;
import java.lang.String;

/**
 * The Class Constants.
 */
public class Constants {   
	
	public static String tmpDirectory = System.getProperty("java.io.tmpdir");
	public static String snapshotDirectory = tmpDirectory + "/fonds/snapshot/";
	public static String presentationDirectory = tmpDirectory + "/fonds/presentation/";
	public static String collateralDirectory = tmpDirectory + "/fonds/collateralPerf/";
	public static String historiqueDirectory = tmpDirectory + "/fonds/historique/";
	public static String priceFondsDirectory = tmpDirectory + "/fonds/pricing/";
	public static String rapportsDirectory = tmpDirectory + "/fonds/rapports/";
	public static String cashflowDirectory = tmpDirectory + "/fonds/cashflow/";
	public static String xmlPricingDirectory = tmpDirectory + File.separator + "xml/";   
	
}

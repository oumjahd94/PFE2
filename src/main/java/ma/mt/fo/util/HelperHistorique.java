package ma.mt.fo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * The Class HelperHistorique.
 */
public class HelperHistorique {

	/** The data historique fonds. */
	private Map<Integer, Vector<Vector<String>>> dataHistoriqueFonds 
	= new HashMap<Integer, Vector<Vector<String>>>();
	
	/** The date arrete. */
	private String dateArrete;
	/** The headers. */
	private List<String> headers = new ArrayList<>();
	
	/** The id class. */
	private String idClass;

	/** The erreur credilog. */
	private boolean erreurCredilog;

	/**
	 * Gets the data historique fonds.
	 *
	 * @return the data historique fonds
	 */
	public Map<Integer, Vector<Vector<String>>> getDataHistoriqueFonds() {
		return dataHistoriqueFonds;
	}

	/**
	 * Sets the data historique fonds.
	 *
	 * @param dataHistoriqueFonds
	 *            the data historique fonds
	 */
	public void setDataHistoriqueFonds(Map<Integer, Vector<Vector<String>>> dataHistoriqueFonds) {
		this.dataHistoriqueFonds = dataHistoriqueFonds;
	}

	/** The vec obligation A. */
	private List<Vector<String>> vecObligationA = new ArrayList<>();

	/** The vec obligation B. */
	private List<Vector<String>> vecObligationB = new ArrayList<>();

	/** The vec obligation S. */
	private List<Vector<String>> vecObligationS = new ArrayList<>();
	public List<Vector<String>> getVecObligationA() {
		return vecObligationA;
	}

	public void setVecObligationA(List<Vector<String>> vecObligationA) {
		this.vecObligationA = vecObligationA;
	}

	public List<Vector<String>> getVecObligationB() {
		return vecObligationB;
	}

	public void setVecObligationB(List<Vector<String>> vecObligationB) {
		this.vecObligationB = vecObligationB;
	}

	public List<Vector<String>> getVecObligationS() {
		return vecObligationS;
	}

	public void setVecObligationS(List<Vector<String>> vecObligationS) {
		this.vecObligationS = vecObligationS;
	}
	/**
	 * Checks if is erreur credilog.
	 *
	 * @return true, if is erreur credilog
	 */
	public boolean isErreurCredilog() {
		return erreurCredilog;
	}

	/**
	 * Sets the erreur credilog.
	 *
	 * @param erreurCredilog
	 *            the new erreur credilog
	 */
	public void setErreurCredilog(boolean erreurCredilog) {
		this.erreurCredilog = erreurCredilog;
	}

	/**
	 * Gets the date arrete.
	 *
	 * @return the date arrete
	 */
	public String getDateArrete() {
		return dateArrete;
	}

	/**
	 * Sets the date arrete.
	 *
	 * @param dateArrete the new date arrete
	 */
	public void setDateArrete(String dateArrete) {
		this.dateArrete = dateArrete;
	}

	/**
	 * Gets the id class.
	 *
	 * @return the id class
	 */
	public String getIdClass() {
		return idClass;
	}

	/**
	 * Sets the id class.
	 *
	 * @param idClass the new id class
	 */
	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	
}

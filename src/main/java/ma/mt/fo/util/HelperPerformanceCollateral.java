package ma.mt.fo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * The Class HelperPerformanceCollateral.
 */
public class HelperPerformanceCollateral {   
	
	private Long idTypeCredilog;

	/** The id class. */
	private String idClass;

	/** The date arrete. */
	private String dateArrete;

	/** The map commentaires. */
	public static Map<String, String> mapCommentaires = new HashMap<String, String>();

	/** The data performance actif. */
	private List<Vector<String>> dataPerformanceActif = new Vector<Vector<String>>();

	/** The vec rendement. */
	private Vector<Vector<String>> vecRendement = new Vector<Vector<String>>();

	/** The vec passif. */
	private Vector<Vector<String>> vecPassif = new Vector<Vector<String>>();

	/** The vec compte. */
	private Vector<Vector<String>> vecCompte = new Vector<Vector<String>>();

	/** The headers. */
	private List<String> headers = new ArrayList<>();

	/** The erreur credilog. */
	private Boolean erreurCredilog;

	/**
	 * Gets the vec rendement.
	 *
	 * @return the vec rendement
	 */
	public Vector<Vector<String>> getVecRendement() {
		return vecRendement;
	}

	/**
	 * Sets the vec rendement.
	 *
	 * @param vecRendement
	 *            the new vec rendement
	 */
	public void setVecRendement(Vector<Vector<String>> vecRendement) {
		this.vecRendement = vecRendement;
	}

	public List<Vector<String>> getDataPerformanceActif() {
		return dataPerformanceActif;
	}

	public void setDataPerformanceActif(List<Vector<String>> dataPerformanceActif) {
		this.dataPerformanceActif = dataPerformanceActif;
	}

	/**
	 * Gets the vec passif.
	 *
	 * @return the vec passif
	 */
	public Vector<Vector<String>> getVecPassif() {
		return vecPassif;
	}

	/**
	 * Sets the vec passif.
	 *
	 * @param vecPassif
	 *            the new vec passif
	 */
	public void setVecPassif(Vector<Vector<String>> vecPassif) {
		this.vecPassif = vecPassif;
	}

	/**
	 * Gets the vec compte.
	 *
	 * @return the vec compte
	 */
	public Vector<Vector<String>> getVecCompte() {
		return vecCompte;
	}

	/**
	 * Sets the vec compte.
	 *
	 * @param vecCompte
	 *            the new vec compte
	 */
	public void setVecCompte(Vector<Vector<String>> vecCompte) {
		this.vecCompte = vecCompte;
	}

	/**
	 * Gets the erreur credilog.
	 *
	 * @return the erreur credilog
	 */
	public Boolean getErreurCredilog() {
		return erreurCredilog;
	}

	/**
	 * Sets the erreur credilog.
	 *
	 * @param erreurCredilog
	 *            the new erreur credilog
	 */
	public void setErreurCredilog(Boolean erreurCredilog) {
		this.erreurCredilog = erreurCredilog;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}


	/**
	 * Gets the map commentaires.
	 *
	 * @return the map commentaires
	 */
	public static Map<String, String> getMapCommentaires() {
		return mapCommentaires;
	}

	/**
	 * Sets the map commentaires.
	 *
	 * @param mapCommentaires
	 *            the map commentaires
	 */
	public static void setMapCommentaires(Map<String, String> mapCommentaires) {
		HelperPerformanceCollateral.mapCommentaires = mapCommentaires;
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
	 * @param idClass
	 *            the new id class
	 */
	public void setIdClass(String idClass) {
		this.idClass = idClass;
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
	 * @param dateArrete
	 *            the new date arrete
	 */
	public void setDateArrete(String dateArrete) {
		this.dateArrete = dateArrete;
	}

	public Long getIdTypeCredilog() {
		return idTypeCredilog;
	}

	public void setIdTypeCredilog(Long idTypeCredilog) {
		this.idTypeCredilog = idTypeCredilog;
	}
}

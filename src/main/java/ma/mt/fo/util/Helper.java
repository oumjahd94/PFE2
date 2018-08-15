package ma.mt.fo.util;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * The Class Helper.
 */
public class Helper {

	/** The data snapshot. */

	/** The vec rendement. */
	private List<String[]> vecRendement;

	/** The vec passif. */
	private List<Vector<String>> vecPassif;

	/** The vec compte. */
	private List<String> vecCompteHeaders;
	/** The vec compte. */
	private List<String> vecCompteData; 

	/** The headers. */
	private Map<Integer, String> headers; 
	

	/** The vec presentation. */
	private List<Vector<String>> vecPresentation;
	private Map<String, String> mapCommentaires;
	private String typeCredilog; 
	
	
	public List<String[]> getVecRendement() {
		return vecRendement;
	} 
	
	public void setVecRendement(List<String[]> vecRendement) {
		this.vecRendement = vecRendement;
	}
	
	public List<Vector<String>> getVecPassif() {
		return vecPassif;
	}   
	
	public void setVecPassif(List<Vector<String>> vecPassif) {
		this.vecPassif = vecPassif;
	}   
	
	public Map<Integer, String> getHeaders() {
		return headers;
	}   
	
	public void setHeaders(Map<Integer, String> headers) {
		this.headers = headers;
	}   
	
	public List<Vector<String>> getVecPresentation() {
		return vecPresentation;
	}  
	
	public void setVecPresentation(List<Vector<String>> vecPresentation) {
		this.vecPresentation = vecPresentation;
	}   
	
	public Map<String, String> getMapCommentaires() {
		return mapCommentaires;
	} 
	
	public void setMapCommentaires(Map<String, String> mapCommentaires) {
		this.mapCommentaires = mapCommentaires;
	} 
	 
	public String getTypeCredilog() {
		return typeCredilog;
	}   
	
	public void setTypeCredilog(String typeCredilog) {
		this.typeCredilog = typeCredilog;
	}   
	
	public List<String> getVecCompteHeaders() {
		return vecCompteHeaders;
	}   
	
	public void setVecCompteHeaders(List<String> vecCompteHeaders) {
		this.vecCompteHeaders = vecCompteHeaders;
	}   
	
	public List<String> getVecCompteData() {
		return vecCompteData;
	}  
	
	public void setVecCompteData(List<String> vecCompteData) {
		this.vecCompteData = vecCompteData;
	}
}

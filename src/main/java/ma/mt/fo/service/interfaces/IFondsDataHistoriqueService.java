package ma.mt.fo.service.interfaces;

import java.util.List;

import ma.mt.fo.entity.FondsDataHistorique;
import ma.mt.fo.util.HelperHistorique;

/**
 * The Interface IFondsDataHistoriqueService.
 */
public interface IFondsDataHistoriqueService {
	/**
	 * List fonds data historique.
	 *
	 * @return the list
	 */
	List<FondsDataHistorique> listFondsDataHistorique();

	/**
	 * Select sapshot by idTypeCredilog
	 */
	HelperHistorique trouverHistoriqueFondsByIdTypeCredilog(Long idTypeCredilog);
}

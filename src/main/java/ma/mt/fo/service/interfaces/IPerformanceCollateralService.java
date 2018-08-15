package ma.mt.fo.service.interfaces;

import ma.mt.fo.entity.FondsDataCollateralPerformance;
import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.util.HelperPerformanceCollateral;

/**
 * The Interface IPerformanceCollateralService.
 */
public interface IPerformanceCollateralService {

	/**
	 * Trouver fonds data collateral.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the fonds data collateral performance
	 */
	FondsDataCollateralPerformance trouverFondsDataCollateral(TypeCredilog typeCredilog);

	/**
	 * Select FondsDataCollateralPerformance by idTypeCredilog
	 */
	HelperPerformanceCollateral trouverCollateralPerfByIdTypeCredilog(Long idTypeCredilog);
}

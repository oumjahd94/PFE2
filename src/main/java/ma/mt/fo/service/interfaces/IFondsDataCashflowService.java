package ma.mt.fo.service.interfaces;

import java.util.List;

import ma.mt.fo.entity.FondsDataCashflow;

/**
 * The Interface IFondsDataCashflowService.
 */
public interface IFondsDataCashflowService {
	/**
	 * List fonds data cashflows.
	 *
	 * @return the list
	 */
	List<FondsDataCashflow> listFondsDataCashflows();
}

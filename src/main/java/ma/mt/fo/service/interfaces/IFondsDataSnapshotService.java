package ma.mt.fo.service.interfaces;

import java.util.List;

import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.util.Helper;

/**
 * The Interface IFondsDataSnapshotService.
 */
public interface IFondsDataSnapshotService {
	/**
	 * List fonds data snapshot.
	 *
	 * @return the list
	 */
	List<FondsDataSnapshot> listFondsDataSnapshot();

	/**
	 * Select sapshot by idTypeCredilog
	 */
	Helper trouverSnapshotByIdTypeCredilog(Long idTypeCredilog);
}

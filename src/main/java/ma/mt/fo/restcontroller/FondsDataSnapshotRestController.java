package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.service.interfaces.IFondsDataSnapshotService;
import ma.mt.fo.util.Helper;

/**
 * The Class FondsDataSnapshotRestController.
 */
@RestController
@RequestMapping(value = "/api/fondsDataSnapshotsFo")
public class FondsDataSnapshotRestController {

	/** The fonds data snapshot service. */
	@Autowired
	private IFondsDataSnapshotService fondsDataSnapshotService;

	/**
	 * List fonds data snapshot.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataSnapshot> listFondsDataSnapshot() {
		return fondsDataSnapshotService.listFondsDataSnapshot();
	}    

	/**
	 * Show snapshot.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the helper	
	 */
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	public Helper showSnapshot(@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		return fondsDataSnapshotService.trouverSnapshotByIdTypeCredilog(idTypeCredilog);
	}  
}

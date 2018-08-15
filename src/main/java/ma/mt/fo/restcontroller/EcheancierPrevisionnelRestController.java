package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.EcheancierPrevisionnel;
import ma.mt.fo.service.interfaces.IEcheancierPrevisionnelService;

/**
 * The Class EcheancierPrevisionnelRestController.
 */
@RequestMapping(value = "/api/EcheanciersPrevisionnels/")
@RestController
public class EcheancierPrevisionnelRestController {

	/** The echeancier previsionnel service. */
	@Autowired
	private IEcheancierPrevisionnelService echeancierPrevisionnelService;

	/**
	 * List echeancier previsionnel.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<EcheancierPrevisionnel> listEcheancierPrevisionnel() {
		return echeancierPrevisionnelService.listEcheancierPrevisionnel();
	}
}

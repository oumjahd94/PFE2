package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.service.interfaces.ITypeCredilogService;

/**
 * The Class TypeCredilogRestController.
 */
@RestController
@RequestMapping(value = "/api/typecredilogs")
public class TypeCredilogRestController {
	/** The type credilog service. */
	@Autowired
	private ITypeCredilogService typeCredilogService;

	/**
	 * List type credilog.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TypeCredilog> listTypeCredilog() {
		return typeCredilogService.listTypeCredilog();
	}
}

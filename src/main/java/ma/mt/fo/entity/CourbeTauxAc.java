package ma.mt.fo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The Class CourbeTauxAc.
 */
@Entity
@Table(name="courbe_taux_actuarial")
public class CourbeTauxAc implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id courbe taux ac. */
	@Id
	@Column(name="id_courbe_taux_acturial")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idCourbeTauxAc;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The taux actuarial 13 S. */
	@Column(name="taux_actuarial_13s")
	private Double tauxActuarial13S;
	
	/** The taux actuarial 1 A. */
	@Column(name="taux_actuarial_1a")
	private Double tauxActuarial1A;
	
	/** The taux actuarial 2 A. */
	@Column(name="taux_actuarial_2a")
	private Double tauxActuarial2A;
	
	/** The taux actuarial 5 A. */
	@Column(name="taux_actuarial_5a")
	private Double tauxActuarial5A;
	
	/** The taux actuarial 15 A. */
	@Column(name="taux_actuarial_15a")
	private Double tauxActuarial15A;
	
	/** The taux actuarial 10 A. */
	@Column(name="taux_actuarial_10a")
	private Double tauxActuarial10A;
	
	/**
	 * Gets the id courbe taux ac.
	 *
	 * @return the id courbe taux ac
	 */
	public Long getIdCourbeTauxAc() {
		return idCourbeTauxAc;
	}
	
	/**
	 * Sets the id courbe taux ac.
	 *
	 * @param idCourbeTauxAc the new id courbe taux ac
	 */
	public void setIdCourbeTauxAc(Long idCourbeTauxAc) {
		this.idCourbeTauxAc = idCourbeTauxAc;
	}
	
	/**
	 * Gets the type credilog.
	 *
	 * @return the type credilog
	 */
	public TypeCredilog getTypeCredilog() {
		return typeCredilog;
	}
	
	/**
	 * Sets the type credilog.
	 *
	 * @param typeCredilog the new type credilog
	 */
	public void setTypeCredilog(TypeCredilog typeCredilog) {
		this.typeCredilog = typeCredilog;
	}
	
	/**
	 * Gets the taux actuarial 13 S.
	 *
	 * @return the taux actuarial 13 S
	 */
	public Double getTauxActuarial13S() {
		return tauxActuarial13S;
	}
	
	/**
	 * Sets the taux actuarial 13 S.
	 *
	 * @param tauxActuarial13S the new taux actuarial 13 S
	 */
	public void setTauxActuarial13S(Double tauxActuarial13S) {
		this.tauxActuarial13S = tauxActuarial13S;
	}
	
	/**
	 * Gets the taux actuarial 1 A.
	 *
	 * @return the taux actuarial 1 A
	 */
	public Double getTauxActuarial1A() {
		return tauxActuarial1A;
	}
	
	/**
	 * Sets the taux actuarial 1 A.
	 *
	 * @param tauxActuarial1A the new taux actuarial 1 A
	 */
	public void setTauxActuarial1A(Double tauxActuarial1A) {
		this.tauxActuarial1A = tauxActuarial1A;
	}
	
	/**
	 * Gets the taux actuarial 2 A.
	 *
	 * @return the taux actuarial 2 A
	 */
	public Double getTauxActuarial2A() {
		return tauxActuarial2A;
	}
	
	/**
	 * Sets the taux actuarial 2 A.
	 *
	 * @param tauxActuarial2A the new taux actuarial 2 A
	 */
	public void setTauxActuarial2A(Double tauxActuarial2A) {
		this.tauxActuarial2A = tauxActuarial2A;
	}
	
	/**
	 * Gets the taux actuarial 5 A.
	 *
	 * @return the taux actuarial 5 A
	 */
	public Double getTauxActuarial5A() {
		return tauxActuarial5A;
	}
	
	/**
	 * Sets the taux actuarial 5 A.
	 *
	 * @param tauxActuarial5A the new taux actuarial 5 A
	 */
	public void setTauxActuarial5A(Double tauxActuarial5A) {
		this.tauxActuarial5A = tauxActuarial5A;
	}
	
	/**
	 * Gets the taux actuarial 15 A.
	 *
	 * @return the taux actuarial 15 A
	 */
	public Double getTauxActuarial15A() {
		return tauxActuarial15A;
	}
	
	/**
	 * Sets the taux actuarial 15 A.
	 *
	 * @param tauxActuarial15A the new taux actuarial 15 A
	 */
	public void setTauxActuarial15A(Double tauxActuarial15A) {
		this.tauxActuarial15A = tauxActuarial15A;
	}
	
	/**
	 * Gets the taux actuarial 10 A.
	 *
	 * @return the taux actuarial 10 A
	 */
	public Double getTauxActuarial10A() {
		return tauxActuarial10A;
	}
	
	/**
	 * Sets the taux actuarial 10 A.
	 *
	 * @param tauxActuarial10A the new taux actuarial 10 A
	 */
	public void setTauxActuarial10A(Double tauxActuarial10A) {
		this.tauxActuarial10A = tauxActuarial10A;
	}
	
	/**
	 * Instantiates a new courbe taux ac.
	 */
	public CourbeTauxAc() {
		super();
	}
}

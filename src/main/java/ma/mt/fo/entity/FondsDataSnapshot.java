package ma.mt.fo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class FondsDataSnapshot.
 */
@Entity
@Table(name="fonds_data_snapshot")
public class FondsDataSnapshot implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id fonds data snapshot. */
	@Id
	@Column(name="id_snapshot_fonds")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idFondsDataSnapshot;
	
	/** The type credilog. */
	@ManyToOne
	private TypeCredilog typeCredilog;
	
	/** The fiche snapshot. */
	@Column(name="fiche_snapshot")
	private String ficheSnapshot;
	
	/** The date publication. */
	@Column(name="date_publication")
	private Date datePublication;
	
	/** The date expiration. */
	@Column(name="date_expiration")
	private Date dateExpiration;
	
	/** The date arrete. */
	@Column(name="date_arrete")
	private Date dateArrete;
	
	/**
	 * Gets the id fonds data snapshot.
	 *
	 * @return the id fonds data snapshot
	 */
	public Long getIdFondsDataSnapshot() {
		return idFondsDataSnapshot;
	}
	
	/**
	 * Sets the id fonds data snapshot.
	 *
	 * @param idFondsDataSnapshot the new id fonds data snapshot
	 */
	public void setIdFondsDataSnapshot(Long idFondsDataSnapshot) {
		this.idFondsDataSnapshot = idFondsDataSnapshot;
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
	 * Gets the fiche snapshot.
	 *
	 * @return the fiche snapshot
	 */
	public String getFicheSnapshot() {
		return ficheSnapshot;
	}
	
	/**
	 * Sets the fiche snapshot.
	 *
	 * @param ficheSnapshot the new fiche snapshot
	 */
	public void setFicheSnapshot(String ficheSnapshot) {
		this.ficheSnapshot = ficheSnapshot;
	}
	
	/**
	 * Gets the date publication.
	 *
	 * @return the date publication
	 */
	public Date getDatePublication() {
		return datePublication;
	}
	
	/**
	 * Sets the date publication.
	 *
	 * @param datePublication the new date publication
	 */
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	
	/**
	 * Gets the date expiration.
	 *
	 * @return the date expiration
	 */
	public Date getDateExpiration() {
		return dateExpiration;
	}
	
	/**
	 * Sets the date expiration.
	 *
	 * @param dateExpiration the new date expiration
	 */
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	
	/**
	 * Gets the date arrete.
	 *
	 * @return the date arrete
	 */
	public Date getDateArrete() {
		return dateArrete;
	}
	
	/**
	 * Sets the date arrete.
	 *
	 * @param dateArrete the new date arrete
	 */
	public void setDateArrete(Date dateArrete) {
		this.dateArrete = dateArrete;
	}
	
	/**
	 * Instantiates a new fonds data snapshot.
	 */
	public FondsDataSnapshot() {
		super();
	}
}

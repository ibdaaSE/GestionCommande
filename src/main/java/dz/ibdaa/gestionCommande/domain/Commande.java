package dz.ibdaa.gestionCommande.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the commande database table.
 * 
 */
@Entity
@NamedQuery(name="Commande.findAll", query="SELECT c FROM Commande c")
public class Commande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date dateCommande;

	@Temporal(TemporalType.DATE)
	private Date delaiLivraison;

	private String modePayement;

	private double montantHT;

	private double montantTTC;

	private String numero;

	private String refCommandeClient;

	private double totalAchatHT;

	private double totalAchatTTC;

	//uni-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="client")
	private Client client;

	public Commande() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCommande() {
		return this.dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Date getDelaiLivraison() {
		return this.delaiLivraison;
	}

	public void setDelaiLivraison(Date delaiLivraison) {
		this.delaiLivraison = delaiLivraison;
	}

	public String getModePayement() {
		return this.modePayement;
	}

	public void setModePayement(String modePayement) {
		this.modePayement = modePayement;
	}

	public double getMontantHT() {
		return this.montantHT;
	}

	public void setMontantHT(double montantHT) {
		this.montantHT = montantHT;
	}

	public double getMontantTTC() {
		return this.montantTTC;
	}

	public void setMontantTTC(double montantTTC) {
		this.montantTTC = montantTTC;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRefCommandeClient() {
		return this.refCommandeClient;
	}

	public void setRefCommandeClient(String refCommandeClient) {
		this.refCommandeClient = refCommandeClient;
	}

	public double getTotalAchatHT() {
		return this.totalAchatHT;
	}

	public void setTotalAchatHT(double totalAchatHT) {
		this.totalAchatHT = totalAchatHT;
	}

	public double getTotalAchatTTC() {
		return this.totalAchatTTC;
	}

	public void setTotalAchatTTC(double totalAchatTTC) {
		this.totalAchatTTC = totalAchatTTC;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
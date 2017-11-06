package dz.ibdaa.gestionCommande.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the produits database table.
 * 
 */
@Entity
@Table(name="produits")
@NamedQuery(name="Produit.findAll", query="SELECT p FROM Produit p")
public class Produit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date delai;

	private double montantHT;

	private double montantTTC;

	@Lob
	private String produits;

	//uni-directional many-to-one association to Commande
	@ManyToOne
	@JoinColumn(name="commande")
	private Commande commande;

	//uni-directional many-to-one association to Fournisseur
	@ManyToOne
	@JoinColumn(name="fournisseur")
	private Fournisseur fournisseur;

	public Produit() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDelai() {
		return this.delai;
	}

	public void setDelai(Date delai) {
		this.delai = delai;
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

	public String getProduits() {
		return this.produits;
	}

	public void setProduits(String produits) {
		this.produits = produits;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Fournisseur getFournisseur() {
		return this.fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

}
package dz.ibdaa.gestionCommande.domain;

public class ProduitCommande {
	
	public Commande commande;
	public Produit[] produits;
	
	public Commande getCommande() {
		return commande;
	}
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	public Produit[] getProduits() {
		return produits;
	}
	public void setProduits(Produit[] produits) {
		this.produits = produits;
	}

}

package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() throws NullPointerException{
		if (etalOccupe==false)
			throw new NullPointerException("Etal non occupé");
		
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder(
				"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu "+produitVendu+" "+produit+ " parmi les "+
			quantiteDebutMarche+" "+ produit +" qu'il voulait vendre.\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + ".\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur)throws IllegalArgumentException, IllegalStateException {
		// Vérifier si l'acheteur est null
	    if (acheteur == null) {
	    	NullPointerException e = new NullPointerException("L'acheteur ne peut pas être null.");
	        // la pile d'erreurs sur la sortie d'erreur
	        e.printStackTrace();
	        // Retourner une chaîne vide
	        return "";
	    }
		
	    // Vérifier si la quantité est positive
	    if (quantiteAcheter < 1)
	        throw new IllegalArgumentException("La quantité doit être positive.");
	    
	    // Vérifier si l'étal est occupé
	    if(!etalOccupe)
	    	throw new IllegalStateException("L'étal doit être occupé.");
	        
		StringBuilder chaine = new StringBuilder();
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
				+ " " + produit + " à " + vendeur.getNom());
		if (quantite == 0) {
			chaine.append(", malheureusement il n'y en a plus !\n");
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > quantite) {
			chaine.append(", comme il n'y en a plus que " + quantite + ", "
					+ acheteur.getNom() + " vide l'étal de "
					+ vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		}
		if (quantite != 0) {
			quantite -= quantiteAcheter;
			chaine.append(". " + acheteur.getNom()
					+ ", est ravi de tout trouver sur l'étal de "
					+ vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}

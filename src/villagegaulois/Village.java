package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;



public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private int nbEtal;
	private Etal[] marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		this.nbEtal = nbEtal;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Etal[nbEtal];
		
		for( int i=0; i<nbEtal; i++) {
			marche[i] = new Etal();
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			
			for(int i=0; i<nbEtal; i++) {
				etals[i] = new Etal();
			}
			
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe())
					continue;
				else
					return i;
					
			}
			return -1;
			
		}
		
		int tailleEtalVendreProduit(String produit) {
			int lenEtalsVendreProduit=0;
			 
			 for( int i=0; i<etals.length; i++) {
				 if( etals[i].contientProduit(produit))
					 lenEtalsVendreProduit++;
			 }
			 
			 return lenEtalsVendreProduit;
		}
		
		 Etal[] trouverEtals(String produit) {
			 Etal[] etalsVendreProduit = new Etal[tailleEtalVendreProduit(produit)];
			 
			 for( int i=0; i<etals.length; i++) {
				 if( etals[i].contientProduit(produit))
					 etalsVendreProduit[i]=  etals[i];
			 }
			 
			 return etalsVendreProduit;
		 }
		 
		 
		 
		 Etal trouverVendeur(Gaulois gaulois) {
			 
			 for(int i=0; i<etals.length; i++) {
				 if( etals[i].getVendeur()==gaulois)
					 return etals[i];
			 }
			 
			 return null;
		 }
		 
		 
		 String afficherMarche() {
			 String affichage="";
			 String affichageEtals_i="";
			 int nbEtalVide = 0;
			 
			 for(int i=0; i<etals.length; i++) {
				 affichageEtals_i = etals[i].afficherEtal();
				 if (affichageEtals_i=="L'étal est libre")
				 {
					 nbEtalVide++;
					 continue;
				 }
				 affichage+= affichageEtals_i+"\n";
			 }
			 
			 if( nbEtalVide!=0)
				 affichage+= "Il reste " +nbEtalVide+" étals non utilisés dans le marché.\n";
			 
			 return affichage;
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	}
	
	
	
	
	
	
	
	
	
}
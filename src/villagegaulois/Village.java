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
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		this.nbEtal = nbEtal;
		villageois = new Gaulois[nbVillageoisMaximum];
		//création du marche
		marche = new Marche(nbEtal);
		
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

	public String afficherVillageois() throws VillageSansChefException{
		if(chef==null)
			throw new VillageSansChefException("Le village n'a pas de chef.");
			
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
	
	 public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		 StringBuilder messRetour = new StringBuilder();
		 messRetour.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit
				 +" "+produit+".\n");
		 
		 int indiceEtalLibre = marche.trouverEtalLibre(); //pour éviter le numéro d'étal 0.
		 messRetour.append("Le vendeur "+vendeur.getNom()+" vend des "+produit
				 +" à l'étal n°"+(indiceEtalLibre+1)+".\n");
		 
		 this.marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
		 return messRetour.toString();
	 }
	 
	 
	 public String rechercherVendeursProduit(String produit) {
		 StringBuilder resultat = new StringBuilder();
		 Etal[] etalsVenteProduit = marche.trouverEtals(produit);
		    
		 //traitement du cas où il n'y a pas de vendeur qui propose ce produit ou il y'a un seul vendeur qui propose ce produit.
		 if(etalsVenteProduit==null)
			 return (resultat.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.\n")).toString();
		 if(etalsVenteProduit.length==1)
			 return (resultat.append("Seul le vendeur "+etalsVenteProduit[0].getVendeur().getNom()
		    	+" propose des "+produit+" au marché.\n")).toString();
		    
		    
		 // Rechercher les étals proposant le produit spécifié
		 resultat.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		 // Parcourir les étals et extraire les vendeurs
		 for (Etal etal : etalsVenteProduit) {
			 Gaulois vendeur = etal.getVendeur();
			 resultat.append("- " + vendeur.getNom() + "\n");
		 }
		    
		 return resultat.toString();
	}

	 
	 public Etal rechercherEtal(Gaulois vendeur) {
		 return marche.trouverVendeur(vendeur);
	 }

	 
	 public String partirVendeur(Gaulois vendeur) {
		 StringBuilder messageRetour = new StringBuilder();
		 Etal etalVendeur = marche.trouverVendeur(vendeur);

		 if (etalVendeur != null) {
		     messageRetour.append(etalVendeur.libererEtal());
		     return messageRetour.toString();
		 } else {
		     messageRetour.append(vendeur.getNom() + " n'a pas d'étal dans le marché.\n");
		     return messageRetour.toString();
		 }
	}

	 
	 public String afficherMarche() {
		 StringBuilder affichage = new StringBuilder();
		 affichage.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");

		 // Obtenez les informations sur les étals du marché
		 String infoEtals = marche.afficherMarche();
		 affichage.append(infoEtals);

		 return affichage.toString();
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
		
		private int tailleEtalVenteProduit(String produit) {
			int lenEtalsVendreProduit=0;
			
			 for( int i=0; i<etals.length; i++) {
				 if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit))
					 lenEtalsVendreProduit++;
			 }
			 return lenEtalsVendreProduit;
		}
		
		 Etal[] trouverEtals(String produit) {
			 int taille = tailleEtalVenteProduit(produit);
			 if (taille==0)
					 return null;
			 
			 Etal[] etalsVenteProduit = new Etal[taille];
			 int ind = 0;
			 
			 for( int i=0; i<etals.length; i++) {
				 if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit))
					 etalsVenteProduit[ind++]=  etals[i];
			 }
			 
			 return etalsVenteProduit;
		 }
		 
		 
		 
		 Etal trouverVendeur(Gaulois gaulois) {
			 
			 for(int i=0; i<etals.length; i++) {
				 if( etals[i].getVendeur().equals(gaulois))
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
				 if (affichageEtals_i.equals("L'étal est libre"))
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
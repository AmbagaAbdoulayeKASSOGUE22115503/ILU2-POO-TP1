package histoire;
import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		 Etal etal = new Etal();
		 
		 try {
			 etal.libererEtal();
			 System.out.println("Fin du test");
		 }catch (NullPointerException e) {
			System.err.println("Message d'erreur: "+e.getMessage());
			
		}
		 
		Gaulois acheteur = new Gaulois("acheteur",4);
//		try {
//			// Tenter d'acheter un produit avec une quantité négative
//	        String resultat = etal.acheterProduit(5, acheteur);
//	        System.out.println("Résultat de l'achat : " + resultat);
//	        } catch (IllegalArgumentException e) {
//	        	System.err.println("Erreur lors de l'achat : " + e.getMessage());
//	        } catch (IllegalStateException e) {
//	        	System.err.println("Erreur lors de l'achat : " + e.getMessage());
//			}
		
		try {
			// Tenter d'acheter un produit avec une quantité négative
	        String resultat = etal.acheterProduit(-5, acheteur);
	        System.out.println("Résultat de l'achat : " + resultat);
	        } catch (Exception e) {
	        	System.err.println("Erreur lors de l'achat : " + e.getMessage());
	        }
		
		
		
	}

}

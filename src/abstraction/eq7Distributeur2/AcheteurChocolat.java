package abstraction.eq7Distributeur2;

import java.util.Map;

import abstraction.eq8Romu.chocolatBourse.IAcheteurChocolatBourse;
import abstraction.eq8Romu.chocolatBourse.SuperviseurChocolatBourse;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Variable;

public class AcheteurChocolat extends AbsAcheteurChocolat implements IAcheteurChocolatBourse, IActeur {
	//Raphaël Caby
	
	public AcheteurChocolat(Distributeur2 ac) {
		super(ac);
	}
	
	public double getDemande(Chocolat chocolat, double cours) {
		return getDemandesChoco().get(chocolat).getValeur();
	}

	public Integer getCryptogramme(SuperviseurChocolatBourse superviseur) {
		return ac.getCryptogramme(superviseur);
	}

	public void notifierCommande(Chocolat chocolat, double quantiteObtenue, boolean payee) {
		int i = getJournaux().size();
		String s = "";
		if (payee) {s = "Commande payée";}
		else {s = "Commande non payée";}
		getJournaux().get(i - 1).ajouter(s);;
		
	}
	
	public void receptionner(ChocolatDeMarque chocolat, double quantite) {
		ac.getStock().ajouterStockChocolat(chocolat, quantite);
	}

	public void initialiser() {
	}
	
	public void next() {
		// L'opération sera effectuée pour CHAQUE type de chocolat que nous vendons
		for (Chocolat choco : Chocolat.values()) {
			// D'abord on consulte les stocks
			double stockChoco = ac.getStock().getStockChocolat(choco);
			// Ensuite on demande au vendeur quelle quantité lui est demandée
			double demandeVendeur = 5.;   //Le temps de progresser dans le fichier vendeur
			// On compare la demande du vendeur et les stocks
			double achatsAFaire = max(demandeVendeur - stockChoco, 0.);
			if (achatsAFaire == 0.) {
				// Si achatsAFaire < 0 alors on n'achète rien
			}
			else {
				// Sinon on positionne la demande sur achatsAFaire
			}
			getDemandesChoco().get(choco).setValeur(this, achatsAFaire);
		}
	}

	public double max(double d1, double d2) {
		if (d1 < d2) {
			return d2;
		}
		return d1;
	}

}

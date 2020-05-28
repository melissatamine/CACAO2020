package abstraction.eq2Producteur2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;
import abstraction.eq4Transformateur2.PateInterne;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Pate;
import abstraction.fourni.Filiere;

public class Producteur2 extends eq2Investisseur implements IActeur {

	private Journal journal_de_production;
	
	public Producteur2() {
		super();
		this.journal_de_production = new Journal("Journal de Production",this);
	}

	/**
	 * Cette methode est appellee a chaque nouveau tour
	 */
	public void next() {
		this.RefreshAge();
		this.RefreshStocks();
		this.PayerEmployes();
		this.setPropal(99999999);
		this.decideAchatArbres();
	}
	/**
	 * Cette méthode avance l'age de chaque paquet d'arbre de 1 et enleve les arbres qui ont atteint les 45 ans
	 */
	public void RefreshAge() {
		List<Integer> deathlist = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.getPaquetsArbres().get(i).setAge(this.getPaquetsArbres().get(i).getAge() + 1);
			if (this.getPaquetsArbres().get(i).getAge() >= 45*24) {
				deathlist.add(i);
			}
		}
		for (int i = 0; i < deathlist.size(); i++) {
			this.getPaquetsArbres().remove(this.getPaquetsArbres().get(i));
		}
	}
	
	/**
	 * 
	 * @author lucas P
	 * cette fonction calcule la production d'un cycle et la rajoute au stock
	 */
	public void RefreshStocks() {
		for (int i = 0; i < this.getPaquetsArbres().size(); i++) {
			this.addQtFeve(this.getPaquetsArbres().get(i).getType(),this.getPaquetsArbres().get(i).production());
			this.journal_de_production.ajouter("Production de " + this.getPaquetsArbres().get(i).production() + " fèves de type: " + this.getPaquetsArbres().get(i).getType() );
		}
	}
	//cette fonction va essayer de calculer la valeur de notre stock a partir des prix de la criée precedente (pour le moment), il pourra etre amelioré.(lucas p)
	public double EstimationVenteStock() {
		return this.getPrixTC().getValeur()*this.getQuantiteFeve(Feve.FEVE_HAUTE)+this.getPrixTT().getValeur()*this.getQuantiteFeve(Feve.FEVE_MOYENNE)+this.getPrixTF().getValeur()*this.getQuantiteFeve(Feve.FEVE_BASSE);
	}
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		for (Feve feve: this.getStockFeve().keySet()) {
			res.add(this.getStockFeve().get(feve));
		}
		for (Pate pate: this.getStockPate().keySet()) {
			res.add(this.getStockPate().get(pate));
		}
		return res;
	}
	
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		res.add(new Variable("cout_arbre",this,this.getprixArbre()));
		res.add(this.getCoutStockFeve());
		return res;
	}
		
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		res.addAll(super.getJournaux());
		res.add(this.journal_de_production);
		return res;
	}
	


}
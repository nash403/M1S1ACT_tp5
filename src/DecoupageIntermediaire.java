import java.util.Arrays;

public class DecoupageIntermediaire {
	public boolean[][] verificationTable;
	public Pizza pizza;
	public Score score;

	public DecoupageIntermediaire(Pizza p) {
		this.pizza = p;
		this.score = new Score(pizza.pizza.length * pizza.pizza[0].length);
		this.verificationTable = new boolean[p.pizza.length][p.pizza[0].length];
		for (int i = 0; i < p.pizza.length; i++)
			Arrays.fill(this.verificationTable[i], false);
	}

	public boolean add(PartPizza pp) {
		// vérification de la taille de la part
		if ((pp.bas_droite.x - pp.haut_gauche.x) * (pp.haut_gauche.y - pp.bas_droite.y) > pizza.surface_max_part) {
			return false;
		}
		int cpt_ham = 0; // compteur de jambon par part
		for (int i = pp.haut_gauche.x; i <= pp.bas_droite.x; i++) {
			for (int j = pp.haut_gauche.y; j <= pp.bas_droite.y; j++) {
				if (verificationTable[i][j]) { // ici il y a overlap
					return false;
				}
				// test de présence de jambon
				cpt_ham = pizza.pizza[i][j].equals(Garniture.JAMBON) ? cpt_ham + 1 : cpt_ham;
			}
		}

		if (cpt_ham < pizza.jambon_mini_part)
			return false;
		for (int i = pp.haut_gauche.x; i <= pp.bas_droite.x; i++)
			for (int j = pp.haut_gauche.y; j <= pp.bas_droite.y; j++)
				verificationTable[i][j] = true;
		score.valide = true;
		score.parts.add(pp);
		score.couverture += pp.taille;
		score.surface_chute -= pp.taille;
		return true;
	}
}

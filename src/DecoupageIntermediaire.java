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
		//System.out.println("tentative ajout de "+pp);
		int cpt_ham = 0; // compteur de jambon par part
		for (int i = pp.haut_gauche.x; i <= pp.bas_droite.x; i++) {
			for (int j = pp.haut_gauche.y; j <= pp.bas_droite.y; j++) {
				//System.out.println("test ("+i+","+j+") "+verificationTable[i][j]);
				if (verificationTable[i][j]) { // ici il y a overlap
					// il faut revenir à l'état de départ avant l'appel de la
					// méthode
					//for (int i_prime = 0; i_prime <= i; i_prime++)
						//for (int j_prime = 0; j_prime < j; j_prime++)
							//verificationTable[i_prime][j_prime] = false;
					return false;
				}
				//verificationTable[i][j] = true;
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
		//System.out.println("part ajoutée "+score);
		return true;
	}
}

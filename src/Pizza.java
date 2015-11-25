import java.util.Arrays;

public class Pizza {
	public Garniture[][] pizza;
	public int surface_max_part;
	public int jambon_mini_part;

	public Pizza(Garniture[][] piz, int c, int n) {
		pizza = piz;
		surface_max_part = c;
		jambon_mini_part = n;
	}

	public Score resultat(CertificatPizza c) {
		int h = pizza.length;
		int l = pizza[0].length;
		Score sc = new Score(h * l);
		boolean verifTab[][] = new boolean[h][l];
		// intialisation
		for (int i = 0; i < h; i++)
			Arrays.fill(verifTab[i], false);
		// verification des parts
		for (PartPizza p : c.parts) {
			// vérification de la taille de la part
			if ((p.bas_droite.x - p.haut_gauche.x)
					* (p.haut_gauche.y - p.bas_droite.y) > surface_max_part) {
				return sc;
			}
			int cpt_ham = 0;

			for (int i = p.haut_gauche.x; i <= p.bas_droite.x; i++) {
				for (int j = p.haut_gauche.y; j <= p.bas_droite.y; j++) {
					if (verifTab[i][j])
						return sc;
					verifTab[i][j] = true;
					cpt_ham = pizza[i][j].equals(Garniture.JAMBON) ? cpt_ham + 1
							: cpt_ham;
					sc.couverture++;
					sc.surface_chute--;
				}
			}

			if (cpt_ham < jambon_mini_part)
				return sc;

		}
		sc.valide = true;
		return sc;
	}
}

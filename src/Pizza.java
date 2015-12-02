import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pizza {
	public Garniture[][] pizza;
	public int surface_max_part;
	public int jambon_mini_part;
	public List<Point> jambons = new ArrayList<Point>();

	public Pizza(Garniture[][] piz, int c, int n) {
		pizza = piz;
		surface_max_part = c;
		jambon_mini_part = n;
		for (int i = 0; i < pizza.length; i++) {
			for (int j = 0; j < pizza[0].length; j++) {
				if (pizza[i][j].equals(Garniture.JAMBON))
					jambons.add(new Point(i, j));
			}
		}
	}

	public int nbJambons() {
		return jambons.size();
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
			if ((p.bas_droite.x - p.haut_gauche.x) * (p.haut_gauche.y - p.bas_droite.y) > surface_max_part) {
				return sc;
			}
			int cpt_ham = 0; // compteur de jambon par part

			for (int i = p.haut_gauche.x; i <= p.bas_droite.x; i++) {
				for (int j = p.haut_gauche.y; j <= p.bas_droite.y; j++) {
					if (verifTab[i][j]) // vérification des overlaps
						return sc;
					verifTab[i][j] = true;
					// test de présence de jambon
					cpt_ham = pizza[i][j].equals(Garniture.JAMBON) ? cpt_ham + 1 : cpt_ham;
					sc.couverture++;
					sc.surface_chute--;
				}
			}

			if (cpt_ham < jambon_mini_part)
				return sc;
		}
		sc.valide = true;
		sc.parts = c.parts;
		return sc;
	}

	public List<PartPizza> all() {
		List<PartPizza> parts = new ArrayList<PartPizza>();
		// System.out.println("La pizza contient " + nbJambons() + "
		// jambons");
		for (int t = jambon_mini_part; t <= surface_max_part; t++) {
			for (int i = 0; i < pizza.length; i++) {
				for (int j = 0; j < pizza[0].length; j++) {
					for (int l = 1; l <= t; l++) {
						// System.out.println(l+"/"+t);
						if ((t % 2) == 1) {
							if ((l != 1) && (l != t)) {
								// System.out.println("ici");
								continue;
							}
						}
						// System.out.println("là");
						int x = i + (l - 1);
						int y = j + (t / (x - i + 1)) - 1;
						// System.out.println(t + ", (" + i + "," + j + "), " +
						// l + " -> (" + x + "," + y + ")");
						if (x < pizza.length && y < pizza[0].length) {
							int cpt = 0;
							for (Point p : jambons) {
								if ((p.x >= i && p.x <= x) && (p.y >= j && p.y <= y))
									cpt++;
								if (cpt >= jambon_mini_part) {
									PartPizza pp = new PartPizza(new Point(i, j), new Point(x, y));
									// System.out.println("on ajoute " + pp + "
									// avec " + cpt + " jambons");
									parts.add(pp);
									break;
								}
							}
						}
					}
				}
			}
		}
		return parts;
	}
}

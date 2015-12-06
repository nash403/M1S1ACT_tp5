import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TestPizza {

	public static void main(String[] args) throws Exception {
		if (args.length < 1)
			System.out.println("java TestPizza  file");
		else {
			Scanner donnee = new Scanner(new FileReader(args[0]));
			System.out.println("Fichier lu:");
			int l = donnee.nextInt();
			System.out.print(l + " ");
			int h = donnee.nextInt();
			System.out.print(h + " ");
			int n = donnee.nextInt();
			System.out.print(n + " ");
			int c = donnee.nextInt();
			System.out.println(c + " ");
			Garniture lapizza[][] = new Garniture[l][h];

			for (int i = 0; i < l; i++) {
				String line = donnee.next();
				for (int j = 0; j < h; j++) {
					char g = line.charAt(j);
					// System.out.print(g);
					switch (g) {
					case 'T':
						lapizza[i][j] = Garniture.TOMATE;
						break;
					case 'H':
						lapizza[i][j] = Garniture.JAMBON;
						break;

					default:
						throw new Exception("bad entry");
					}
				}
				// System.out.println();
			}
			donnee.close();
			Pizza pizza = new Pizza(lapizza, c, n);
			System.out.println(pizza.all().size() + " parts générées");
			Score test = bestScoreNbEssaiAlea(pizza, 1000);
			System.out.println("Resultat final : " + test.couverture);
			extractResultat(test.parts);
		}

	}

	static private Score scorePizzaAlea(Pizza pizza) {
		List<PartPizza> listAlea = pizza.all();
		List<PartPizza> resultat = new ArrayList<PartPizza>();
		Collections.shuffle(listAlea);
		DecoupageIntermediaire di = new DecoupageIntermediaire(pizza);
		for (PartPizza p : listAlea) {
			if (di.add(p))
				resultat.add(p);

			if (di.score.couverture >= pizza.size)
				break;
		}
		listAlea = null;
		return di.score;
	}

	static private Score bestScoreNbEssaiAlea(Pizza pizza, int essai) {
		Score result = null;
		int bestScore = 0;
		for (int i = 1; i <= essai; i++) {
			Score score = scorePizzaAlea(pizza);
			if (score.couverture > bestScore) {
				System.out.println(
						"\nNouveau meilleur score : " + score.couverture + " (score précédent : " + bestScore + ")");
				bestScore = score.couverture;
				result = score;
			}
			System.out.print("\r(Essai " + i + ") Meilleur score pour l'instant : " + bestScore + " !!");
		}
		return result;
	}

	static private void extractResultat(List<PartPizza> listPart) {
		File f = new File("resulat.txt");
		try {
			FileWriter fw = new FileWriter(f);

			fw.write(String.valueOf(listPart.size()));
			fw.write("\n");
			for (PartPizza p : listPart) {
				fw.write(p.haut_gauche.x + " " + p.haut_gauche.y + " " + p.bas_droite.x + " " + p.bas_droite.y + "\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Il y a eu une erreur lors de l'�criture sur le fichier :" + e.getMessage());
		}
	}

}

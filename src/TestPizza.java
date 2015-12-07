import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
			}
			donnee.close();
			Pizza pizza = new Pizza(lapizza, c, n);
			System.out.println(pizza.all().size() + " parts générées");
			// Score test = bestScoreNbEssaiAlea(pizza, 800); // 6526
//			 Score test = scorePetitAuPlusGrand(pizza,new Comparator<PartPizza>() {
//				@Override
//				public int compare(PartPizza pp1, PartPizza pp2) { // taille croiss d'abord, puis haut x croiss, puis haut y croiss
//					if (pp1.taille < pp2.taille)
//						return -1;
//					if (pp1.taille > pp2.taille)
//						return 1;
//					if (pp1.haut_gauche.x < pp2.haut_gauche.x)
//						return -1;
//					if (pp1.haut_gauche.x > pp2.haut_gauche.x)
//						return 1;
//					if (pp1.haut_gauche.y < pp2.haut_gauche.y)
//						return -1;
//					if (pp1.haut_gauche.y > pp2.haut_gauche.y)
//						return 1;
//					return 0;
//				}
//			}); // 3640
//			 Score test = scoreComparatorSort(pizza,new Comparator<PartPizza>() {
//					@Override
//					public int compare(PartPizza pp1, PartPizza pp2) { // haut x décr d'abord, puis taille décr, puis haut y décr
//						if (pp1.haut_gauche.x < pp2.haut_gauche.x)
//							return 1;
//						if (pp1.haut_gauche.x > pp2.haut_gauche.x)
//							return -1;
//						if (pp1.taille < pp2.taille)
//							return 1;
//						if (pp1.taille > pp2.taille)
//							return -1;
//						if (pp1.haut_gauche.y < pp2.haut_gauche.y)
//							return 1;
//						if (pp1.haut_gauche.y > pp2.haut_gauche.y)
//							return -1;
//						return 0;
//					}
//				}); //8663
//			 Score test = scoreComparatorSort(pizza,new Comparator<PartPizza>() {
//					@Override
//					public int compare(PartPizza pp1, PartPizza pp2) { // taille décr d'abord, puis haut x décr, puis haut y décr
//						if (pp1.taille < pp2.taille)
//							return 1;
//						if (pp1.taille > pp2.taille)
//							return -1;
//						if (pp1.haut_gauche.x < pp2.haut_gauche.x)
//							return 1;
//						if (pp1.haut_gauche.x > pp2.haut_gauche.x)
//							return -1;
//						if (pp1.haut_gauche.y < pp2.haut_gauche.y)
//							return 1;
//						if (pp1.haut_gauche.y > pp2.haut_gauche.y)
//							return -1;
//						return 0;
//					}
//				}); //8817
//			Score test = scoreComparatorSort(pizza, new Comparator<PartPizza>() {
//				@Override
//				public int compare(PartPizza pp1, PartPizza pp2) { // haut y croiss d'abord, puis taille décr, puis haut x croiss
//					if (pp1.haut_gauche.y < pp2.haut_gauche.y)
//						return -1;
//					if (pp1.haut_gauche.y > pp2.haut_gauche.y)
//						return 1;
//					if (pp1.taille < pp2.taille)
//						return 1;
//					if (pp1.taille > pp2.taille)
//						return -1;
//					if (pp1.haut_gauche.x < pp2.haut_gauche.x)
//						return -1;
//					if (pp1.haut_gauche.x > pp2.haut_gauche.x)
//						return 1;
//					return 0;
//				}
//			}); // 8646
			Score test = scoreComparatorSort(pizza, new Comparator<PartPizza>() {
				@Override
				public int compare(PartPizza pp1, PartPizza pp2) { // haut y décr d'abord, puis taille décr, puis haut x décr
					if (pp1.haut_gauche.y < pp2.haut_gauche.y)
						return 1;
					if (pp1.haut_gauche.y > pp2.haut_gauche.y)
						return -1;
					if (pp1.taille < pp2.taille)
						return 1;
					if (pp1.taille > pp2.taille)
						return -1;
					if (pp1.haut_gauche.x < pp2.haut_gauche.x)
						return 1;
					if (pp1.haut_gauche.x > pp2.haut_gauche.x)
						return -1;
					return 0;
				}
			});
			System.out.println("\nResultat final : " + test.couverture);
			extractResultat(test);
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
		return pizza.resultat(new CertificatPizza(di.score.parts));
	}

	static private Score scoreComparatorSort(Pizza pizza, Comparator<PartPizza> cp) {
		List<PartPizza> listTriee = pizza.all();
		List<PartPizza> resultat = new ArrayList<PartPizza>();
		Collections.sort(listTriee, cp);
		DecoupageIntermediaire di = new DecoupageIntermediaire(pizza);
		for (PartPizza p : listTriee) {
			if (di.add(p))
				resultat.add(p);

			if (di.score.couverture >= pizza.size)
				break;
		}
		listTriee = null;
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

	static private void extractResultat(Score sc) {
		File f = new File("resultat_" + sc.couverture + ".txt");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(sc.toString());
			fw.close();
		} catch (IOException e) {
			System.out.println("Il y a eu une erreur lors de l'�criture sur le fichier :" + e.getMessage());
		}
	}

}

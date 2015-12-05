import java.io.FileReader;
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
					char g  = line.charAt(j);
					//System.out.print(g);
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
				//System.out.println();
			}
			donnee.close();
			Pizza pizza = new Pizza(lapizza, c, n);
			System.out.println(pizza.all().size());
//			for (PartPizza p : pizza.all()) {
//				System.out.println("Part de taille " + ((p.bas_droite.x - p.haut_gauche.x + 1)* ( p.bas_droite.y - p.haut_gauche.y + 1)) + " : (" + p.haut_gauche.x + ", " + p.haut_gauche.y + ") (" + p.bas_droite.x + ", " + p.bas_droite.y + ")" );;
//			}
		}

	}

}

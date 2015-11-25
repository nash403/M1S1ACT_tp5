public class Score {
	public int couverture = 0;
	public int surface_chute;
	public boolean valide = false;

	public Score(int taille_pizza) {
		surface_chute = taille_pizza;
	}

	public String toString() {
		return valide ? "Score: " + couverture + ", surface de chute : "
				+ surface_chute : "Non valide";
	}
}

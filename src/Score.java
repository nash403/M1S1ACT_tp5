import java.util.ArrayList;
import java.util.List;

public class Score {
	public int couverture = 0;
	public int surface_chute;
	public boolean valide = false;
	public List<PartPizza> parts = new ArrayList<PartPizza>();

	public Score(int taille_pizza) {
		surface_chute = taille_pizza;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(parts.size()+"\n");
		for (PartPizza pp:parts){
			sb.append(pp.haut_gauche.x+" "+pp.haut_gauche.y+" "+pp.bas_droite.x+" "+pp.bas_droite.y+"\n");
		}
		return sb.toString();
	}
}

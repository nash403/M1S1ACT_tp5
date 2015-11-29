import java.awt.Point;


public class PartPizza {
	public Point haut_gauche;
	public Point bas_droite;
	public PartPizza(Point hg, Point bd) {
		haut_gauche = hg;
		bas_droite = bd;
	}
	
	public String toString(){
		return "("+haut_gauche.x+","+haut_gauche.y+") à ("+bas_droite.x+","+bas_droite.y+")";
	}
}

import java.awt.Point;

public class PartPizza {
	public Point haut_gauche;
	public Point bas_droite;
	public int taille;

	public PartPizza(Point hg, Point bd) {
		haut_gauche = hg;
		bas_droite = bd;
		taille = (bd.x - hg.x + 1) * (bd.y - hg.y + 1);
	}

	public boolean overlaps(PartPizza pp) {
		// 1|2
		// _._
		// 3|4
		int hg_rapportA_hg = 0;
		int hg_rapportA_bd = 0;
		int bd_rapportA_hg = 0;
		int bd_rapportA_bd = 0;
		
		if (this.haut_gauche.x == pp.haut_gauche.x && this.haut_gauche.y == pp.haut_gauche.y) return true;
		if (this.bas_droite.x == pp.bas_droite.x && this.bas_droite.y == pp.bas_droite.y) return true;

		if (this.haut_gauche.x > pp.haut_gauche.x && this.haut_gauche.y > pp.haut_gauche.y)
			hg_rapportA_hg = 1;
		else if (this.haut_gauche.x < pp.haut_gauche.x && this.haut_gauche.y < pp.haut_gauche.y)
			hg_rapportA_hg = 4;
		else if (this.haut_gauche.x < pp.haut_gauche.x && this.haut_gauche.y > pp.haut_gauche.y)
			hg_rapportA_hg = 2;
		else if (this.haut_gauche.x > pp.haut_gauche.x && this.haut_gauche.y < pp.haut_gauche.y)
			hg_rapportA_hg = 3;
		 
		if (this.bas_droite.x > pp.haut_gauche.x && this.bas_droite.y > pp.haut_gauche.y)
			hg_rapportA_bd = 1;
		else if (this.bas_droite.x < pp.haut_gauche.x && this.bas_droite.y < pp.haut_gauche.y)
			hg_rapportA_bd = 4;
		else if (this.bas_droite.x < pp.haut_gauche.x && this.bas_droite.y > pp.haut_gauche.y)
			hg_rapportA_bd = 2;
		else if (this.bas_droite.x > pp.haut_gauche.x && this.bas_droite.y < pp.haut_gauche.y)
			hg_rapportA_bd = 3;
		 
		if (hg_rapportA_hg == 4 && hg_rapportA_bd == 1)
			return true;

		if (this.haut_gauche.x > pp.bas_droite.x && this.haut_gauche.y > pp.bas_droite.y)
			bd_rapportA_hg = 1;
		else if (this.haut_gauche.x < pp.bas_droite.x && this.haut_gauche.y > pp.bas_droite.y)
			bd_rapportA_hg = 2;
		else if (this.haut_gauche.x > pp.bas_droite.x && this.haut_gauche.y < pp.bas_droite.y)
			bd_rapportA_hg = 3;
		else if (this.haut_gauche.x < pp.bas_droite.x && this.haut_gauche.y < pp.bas_droite.y)
			bd_rapportA_hg = 4;

		if (this.bas_droite.x > pp.bas_droite.x && this.bas_droite.y > pp.bas_droite.y)
			bd_rapportA_bd = 1;
		else if (this.bas_droite.x < pp.bas_droite.x && this.bas_droite.y > pp.bas_droite.y)
			bd_rapportA_bd = 2;
		else if (this.bas_droite.x > pp.bas_droite.x && this.bas_droite.y < pp.bas_droite.y)
			bd_rapportA_bd = 3;
		else if (this.bas_droite.x < pp.bas_droite.x && this.bas_droite.y < pp.bas_droite.y)
			bd_rapportA_bd = 4;

		if (bd_rapportA_hg == 4 && bd_rapportA_bd == 1)
			return true;

		if ((hg_rapportA_hg == 1 || hg_rapportA_hg == 2 || hg_rapportA_hg == 3) && hg_rapportA_bd == 1
				&& bd_rapportA_hg == 4 && (bd_rapportA_bd == 2 || bd_rapportA_bd == 3 || bd_rapportA_bd == 4))
			return true;

		return false;
	}

	public String toString() {
		return "Part de taille "+taille+", (" + haut_gauche.x + "," + haut_gauche.y + ") à (" + bas_droite.x + "," + bas_droite.y + ")";
	}
}

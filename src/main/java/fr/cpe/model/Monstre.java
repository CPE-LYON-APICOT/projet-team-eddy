package fr.cpe.model;

public class Monstre  extends Entite {
    public Monstre(int hpMax, int degats, double x, double y, double speed) {
        super(hpMax, degats, x, y, speed);
    }

    public void traquer(Joueur joueur, long tempsActuel) {
        double dx = joueur.getX() - this.getX();
        double dy = joueur.getY() - this.getY();
        
        double norme = Math.sqrt(dx * dx + dy * dy);
        
        if (norme > 0) {
            dx /= norme;
            dy /= norme;
        }
        
        this.setX(this.getX() + dx * this.speed);
        this.setY(this.getY() + dy * this.speed);
    }
}

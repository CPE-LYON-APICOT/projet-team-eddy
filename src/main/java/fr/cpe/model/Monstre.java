package fr.cpe.model;

public class Monstre  extends Entite {
    public Monstre(int hpMax, int degats, double x, double y, double speed) {
        super(hpMax, degats, x, y, speed);
    }

    public void traquer(Joueur joueur, long tempsActuel) {
        double dx = joueur.x - this.x;
        double dy = joueur.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            double moveX = (dx / distance) * this.speed;
            double moveY = (dy / distance) * this.speed;
            this.seDeplacer(moveX, moveY);
        }
        if (distance < 30 && (tempsActuel - this.dernierCoupPorte) > 1000000000) { // Attaque si proche et cooldown écoulé
            this.dernierCoupPorte = tempsActuel;
            joueur.recevoirDegats(this.degats);
        }
    }
}

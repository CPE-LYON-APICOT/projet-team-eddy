package fr.cpe.model;

public class Boss extends Monstre {

    private long lastPhaseChange = 0;
    private boolean isDashing = false;
    private double angleProjectiles = 0;

    public Boss(double x, double y) {
        super(1000, 50, x, y, 3.0);
    }

    @Override
    public int getHpMax() {
        return 1000;
    }

    @Override
    public void traquer(Joueur joueur, long tempsActuel) {
        long dureePhase = isDashing ? 500_000_000L : 2_500_000_000L;
        
        if (tempsActuel - lastPhaseChange > dureePhase) {
            isDashing = !isDashing;
            lastPhaseChange = tempsActuel;
            this.speed = isDashing ? 14.0 : 3.0;
        }

        angleProjectiles += 0.05;

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

    public double getProjectileX(int index, int total) {
        double angle = angleProjectiles + (index * 2 * Math.PI / total);
        return this.getX() + 100 + Math.cos(angle) * 150 - 15;
    }

    public double getProjectileY(int index, int total) {
        double angle = angleProjectiles + (index * 2 * Math.PI / total);
        return this.getY() + 100 + Math.sin(angle) * 150 - 15;
    }
}
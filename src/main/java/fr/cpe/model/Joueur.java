package fr.cpe.model;

import java.util.List;

public class Joueur extends Entite {
    private int poussiereEtoile;

    public Joueur(int hpMax, int degats, double x, double y, double speed) {
        super(hpMax, degats, x, y, speed);
        this.poussiereEtoile = 0; // Le joueur commence sans poussière d'étoile
    }

    public int getPoussiereEtoile() {
        return this.poussiereEtoile;
    }

    public void ajouterPoussiereEtoile(int quantite) {
        this.poussiereEtoile += quantite;
    }

    public void consommerPoussiereEtoile(int quantite) {
        if (quantite <= this.poussiereEtoile) {
            this.poussiereEtoile -= quantite;
        } else {
            System.out.println("Pas assez de poussière d'étoile !");
        }
    }

    public void attaquer(List<Monstre> monstre, long tempsActuel) {
        if (monstre.isEmpty()) return;

        if (tempsActuel - this.dernierCoupPorte > 1000000000) { 
                for (Monstre m : monstre) {
                    if (Math.sqrt(Math.pow(m.getX() - this.x, 2) + Math.pow(m.getY() - this.y, 2)) < 100) {
                        m.recevoirDegats(this.degats);
                    }
                }
                this.dernierCoupPorte = tempsActuel;
        }
    }
}


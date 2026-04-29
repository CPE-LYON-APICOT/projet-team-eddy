package fr.cpe.model.decorator;

import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;

public abstract class MonstreDecorator extends Monstre {
    protected Monstre monstreDecore;

    public MonstreDecorator(Monstre monstreDecore) {
        super(monstreDecore.getHp(), monstreDecore.getDegats(), monstreDecore.getX(), monstreDecore.getY(), 1.0);
        this.monstreDecore = monstreDecore;
    }

    @Override
    public int getHp() {
        return monstreDecore.getHp();
    }

    @Override
    public double getX() {
        return monstreDecore.getX();
    }

    @Override
    public double getY() {
        return monstreDecore.getY();
    }

    @Override
    public int getDegats() {
        return monstreDecore.getDegats();
    }

    @Override
    public void recevoirDegats(int degats) {
        monstreDecore.recevoirDegats(degats);
    }

    @Override
    public void seDeplacer(double dx, double dy) {
        monstreDecore.seDeplacer(dx, dy);
    }

    @Override
    public void mourir() {
        monstreDecore.mourir();
    }

    @Override
    public void traquer(Joueur joueur, long tempsActuel) {
        monstreDecore.traquer(joueur, tempsActuel);
    }
}
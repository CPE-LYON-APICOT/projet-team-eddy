package fr.cpe.model;

abstract class Entite {
    protected int hp;
    protected int hpMax;
    protected int degats;
    protected double dernierCoupPorte;
    protected double x;
    protected double y;
    protected double speed;

    public Entite(int hpMax, int degats, double x, double y, double speed) {
        this.hpMax = hpMax;
        this.degats = degats;
        this.dernierCoupPorte = 0;
        this.hp = hpMax; // L'entité commence avec des points de vie complets
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    

    public int getHp() {
        return this.hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDegats() {
        return this.degats;
    }

    public void recevoirDegats(int degats) {
        this.hp -= degats;
        if (this.hp < 0) {
            this.hp = 0;
            mourir();
        }
    }

    public void seDeplacer(double dx, double dy) {
        this.x += dx * this.speed;
        this.y += dy * this.speed;
    }

    public void mourir(){
        System.out.println("L'entité est morte !");
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    
}

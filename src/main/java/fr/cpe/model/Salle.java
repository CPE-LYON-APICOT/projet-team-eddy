package fr.cpe.model;

public class Salle {
    private int x;
    private int y;
    private int type;
    private boolean porteNord;
    private boolean porteSud;
    private boolean porteEst;
    private boolean porteOuest;

    public Salle(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getType() { return type; }

    public boolean hasPorteNord() { return porteNord; }
    public boolean hasPorteSud() { return porteSud; }
    public boolean hasPorteEst() { return porteEst; }
    public boolean hasPorteOuest() { return porteOuest; }

    public void setPorteNord(boolean porteNord) { this.porteNord = porteNord; }
    public void setPorteSud(boolean porteSud) { this.porteSud = porteSud; }
    public void setPorteEst(boolean porteEst) { this.porteEst = porteEst; }
    public void setPorteOuest(boolean porteOuest) { this.porteOuest = porteOuest; }
}
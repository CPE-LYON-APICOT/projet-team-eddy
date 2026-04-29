package fr.cpe.model;

public class Projectile {
    private double x, y;
    private double directionX, directionY;
    private final double speed = 7.0;
    private boolean alive = true;

    public Projectile(double x, double y, double targetX, double targetY) {
        this.x = x;
        this.y = y;
        
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        this.directionX = (dx / distance) * speed;
        this.directionY = (dy / distance) * speed;
    }

    public void update() {
        x += directionX;
        y += directionY;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }
}
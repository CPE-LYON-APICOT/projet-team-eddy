package fr.cpe.model;

// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║                                                                            ║
// ║   ✏️  FICHIER MODIFIABLE — Exemple de classe modèle                        ║
// ║                                                                            ║
// ║   Montre comment structurer un objet simple du jeu.                        ║
// ║   Remplacez-le par vos propres modèles.                                    ║
// ║                                                                            ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

import javafx.scene.paint.Color;

/**
 * Une balle : position, direction et couleur.
 *
 * <p>Exemple de classe modèle simple. Les champs publics {@code x, y, dx, dy}
 * représentent la position et le vecteur de direction. La couleur est encapsulée
 * avec un getter/setter.</p>
 */
public class Ball {

    public double x;
    public double y;
    public double dx;
    public double dy;
    private Color color;

    public Ball(double x, double y, double dx, double dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}

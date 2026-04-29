package fr.cpe.service.factory;

import com.google.inject.Singleton;

import fr.cpe.model.Monstre;

@Singleton
public class MonstreFactory {

    public Monstre creerMonstre(String type, double x, double y) {
        if ("BASE".equalsIgnoreCase(type)) {
            return new Monstre(50, 10, x, y, 2.0);
        }
        if ("BOSS".equalsIgnoreCase(type)) {
            return new Monstre(300, 40, x, y, 1.5);
        }
        if ("VELOCE".equalsIgnoreCase(type)) {
            return new Monstre(30, 5, x, y, 3.0);
        }
        throw new IllegalArgumentException("Type de monstre inconnu");
    }
}
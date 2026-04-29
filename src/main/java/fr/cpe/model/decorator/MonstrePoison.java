package fr.cpe.model.decorator;

import fr.cpe.model.Joueur;
import fr.cpe.model.Monstre;

public class MonstrePoison extends MonstreDecorator {
    
    private long dernierDegatPoison = 0;

    public MonstrePoison(Monstre monstreDecore) {
        super(monstreDecore);
    }

    @Override
    public void traquer(Joueur joueur, long tempsActuel) {
        if (tempsActuel - dernierDegatPoison > 1000000000) {
            this.recevoirDegats(2);
            dernierDegatPoison = tempsActuel;
        }
        super.traquer(joueur, tempsActuel);
    }
}
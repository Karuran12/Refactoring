package fr.efrei.transfret;

// le cœur de la chaîne : le tarif du mode de transport (étape 5)
public class PrixDeBase implements ComposantPrix {

    @Override
    public double calculer(DemandePrix d) {
        return d.mode().getTarif().calculer(d.distance(), d.poids(), d.volume());
    }
}
package fr.efrei.transfret;

public class Assurance extends DecorateurPrix {

    private static final double TAUX = 0.02;    // 2 % de la valeur déclarée
    private static final double MINIMUM = 15;

    public Assurance(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.assurance();
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix + Math.max(demande.valeur() * TAUX, MINIMUM);
    }
}
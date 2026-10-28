package fr.efrei.transfret;

public class SupplementUrgence extends DecorateurPrix {

    private static final double COEF = 1.5; // +50 %

    public SupplementUrgence(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.urgent();
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix * COEF;
    }
}
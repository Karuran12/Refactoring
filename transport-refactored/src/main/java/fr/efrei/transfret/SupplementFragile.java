package fr.efrei.transfret;

public class SupplementFragile extends DecorateurPrix {

    private static final double COEF = 1.15; // +15 %

    public SupplementFragile(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.contient(TypeMarchandise.FRAGILE);
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix * COEF;
    }
}
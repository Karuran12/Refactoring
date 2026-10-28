package fr.efrei.transfret;

public class SupplementDangereux extends DecorateurPrix {

    private static final double COEF = 1.30; // +30 %

    public SupplementDangereux(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.contient(TypeMarchandise.DANGEREUX);
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix * COEF;
    }
}
package fr.efrei.transfret;

public class SupplementRefrigere extends DecorateurPrix {

    private static final double COEF = 1.20; // +20 %

    public SupplementRefrigere(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.contient(TypeMarchandise.REFRIGERE);
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix * COEF;
    }
}
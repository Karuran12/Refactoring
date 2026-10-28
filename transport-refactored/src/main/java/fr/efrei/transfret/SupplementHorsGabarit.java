package fr.efrei.transfret;

public class SupplementHorsGabarit extends DecorateurPrix {

    private static final double COEF = 1.10;
    private static final double DIMENSION_MAX = 200;  

    public SupplementHorsGabarit(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return demande.colis().stream().anyMatch(c ->
                c.getLongueur() > DIMENSION_MAX || c.getLargeur() > DIMENSION_MAX || c.getHauteur() > DIMENSION_MAX);
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return prix * COEF;
    }
}

package fr.efrei.transfret;

// délègue à la politique de remise du client (étape 5)
public class RemiseClient extends DecorateurPrix {

    public RemiseClient(ComposantPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected boolean sApplique(DemandePrix demande) {
        return true;
    }

    @Override
    protected double appliquer(double prix, DemandePrix demande) {
        return demande.typeClient().getRemise().appliquer(prix);
    }
}
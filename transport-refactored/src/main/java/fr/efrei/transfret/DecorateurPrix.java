package fr.efrei.transfret;

// enveloppe un autre composant et modifie son prix s'il est concerné
public abstract class DecorateurPrix implements ComposantPrix {

    private final ComposantPrix enveloppe;

    protected DecorateurPrix(ComposantPrix enveloppe) {
        this.enveloppe = enveloppe;
    }

    @Override
    public final double calculer(DemandePrix demande) {
        double prix = enveloppe.calculer(demande);
        return sApplique(demande) ? appliquer(prix, demande) : prix;
    }

    protected abstract boolean sApplique(DemandePrix demande);

    protected abstract double appliquer(double prix, DemandePrix demande);
}
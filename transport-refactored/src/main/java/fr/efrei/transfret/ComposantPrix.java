package fr.efrei.transfret;

// interface commune au tarif de base et à tous les suppléments
public interface ComposantPrix {

    double calculer(DemandePrix demande);
}
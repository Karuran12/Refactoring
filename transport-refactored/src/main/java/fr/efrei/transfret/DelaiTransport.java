package fr.efrei.transfret;

// délai de livraison en jours, propre à chaque mode
public interface DelaiTransport {

    int calculer(int distance, boolean urgent);
}
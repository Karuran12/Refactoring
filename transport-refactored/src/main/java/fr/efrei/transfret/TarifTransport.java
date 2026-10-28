package fr.efrei.transfret;

// prix de base d'un transport, avant suppléments et remise
public interface TarifTransport {

    double calculer(int distance, double poids, double volume);
}
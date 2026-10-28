package fr.efrei.transfret;

import java.util.List;

/**
 * Ce que TOUS les véhicules savent faire et rien de plus.
 * Rouler, naviguer, voler ou réfrigérer sont des détails propres à chaque véhicule.
 */
public interface IVehicule {

    String getImmatriculation();

    boolean estDisponible();

    void setDisponible(boolean disponible);

    void charger(Colis colis);

    void decharger(Colis colis);

    boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode);

    void partir(int distanceKm);
}
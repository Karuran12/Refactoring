package fr.efrei.transfret;

public class Peniche extends Vehicule {

    public Peniche(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.FLUVIAL);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("La péniche " + getImmatriculation() + " navigue sur " + distanceKm + " km de fleuve");
    }
}
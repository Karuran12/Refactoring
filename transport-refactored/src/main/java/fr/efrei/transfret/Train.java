package fr.efrei.transfret;

public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.RAIL);
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("Le train " + getImmatriculation() + " circule sur " + distanceKm + " km de voies ferrées");
    }
}
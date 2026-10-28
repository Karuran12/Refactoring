package fr.efrei.transfret;

public class Avion extends Vehicule {

    private static final int ALTITUDE_CROISIERE = 35000; // pieds

    public Avion(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.AIR);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("L'avion " + getImmatriculation() + " roule sur la piste");
        System.out.println("L'avion " + getImmatriculation() + " vole à " + ALTITUDE_CROISIERE + " pieds");
    }
}
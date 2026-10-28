package fr.efrei.transfret;

import java.util.List;

public class Camion extends Vehicule {

    private final boolean adr; // certification matières dangereuses

    public Camion(String immatriculation, double capaciteMax, boolean adr) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
        this.adr = adr;
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return !contient(colis, TypeMarchandise.REFRIGERE)
                && (adr || !contient(colis, TypeMarchandise.DANGEREUX));
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("Le camion " + getImmatriculation() + " roule sur " + distanceKm + " km");
    }
}
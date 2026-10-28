package fr.efrei.transfret;

import java.util.List;

// n'hérite plus de Camion : il ne peut pas le remplacer partout (il ne prend que du réfrigéré)
public class CamionFrigorifique extends Vehicule {

    private static final double TEMPERATURE = 4.0; // °C

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("Le camion " + getImmatriculation() + " roule sur " + distanceKm + " km");
        if (transporte(TypeMarchandise.REFRIGERE)) {
            System.out.println("Réfrigération activée à " + TEMPERATURE + "°C sur " + getImmatriculation());
        }
    }
}
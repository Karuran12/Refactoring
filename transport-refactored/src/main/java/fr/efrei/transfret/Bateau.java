package fr.efrei.transfret;

import java.util.List;

public class Bateau extends Vehicule {

    private static final double KM_PAR_MILLE = 1.852;
    private static final double TEMPERATURE = 4.0; // °C

    private final boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax, ModeTransport.MER);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return conteneursRefrigeres || !contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        int milles = (int) (distanceKm / KM_PAR_MILLE);
        System.out.println("Le navire " + getImmatriculation() + " navigue sur " + milles + " milles nautiques");
        if (conteneursRefrigeres && transporte(TypeMarchandise.REFRIGERE)) {
            System.out.println("Conteneurs frigorifiques réglés à " + TEMPERATURE + "°C");
        }
    }
}
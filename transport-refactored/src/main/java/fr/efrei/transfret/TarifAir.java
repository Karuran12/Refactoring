package fr.efrei.transfret;

public class TarifAir implements TarifTransport {

    private static final double PRIX_KM = 0.6;
    private static final double PRIX_KG = 4.5;
    private static final double FACTEUR_VOLUMETRIQUE = 167; // kg par m3

    @Override
    public double calculer(int distance, double poids, double volume) {
        // on facture le plus grand des deux : poids réel ou poids volumétrique
        double poidsTaxable = Math.max(poids, volume * FACTEUR_VOLUMETRIQUE);
        return distance * PRIX_KM + poidsTaxable * PRIX_KG;
    }
}
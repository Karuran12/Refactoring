package fr.efrei.transfret;

public class TarifFluvial implements TarifTransport {

    private static final double PRIX_KM = 0.40;
    private static final double PRIX_KG = 0.02;
    private static final double MINIMUM = 300;

    @Override
    public double calculer(int distance, double poids, double volume) {
        double prix = distance * PRIX_KM + poids * PRIX_KG;
        return Math.max(prix, MINIMUM);
    }
}
package fr.efrei.transfret;

public class TarifMer implements TarifTransport {

    private static final double PRIX_KM = 0.5;
    private static final double PRIX_KG = 0.03;
    private static final double MINIMUM = 500;

    @Override
    public double calculer(int distance, double poids, double volume) {
        double prix = distance * PRIX_KM + poids * PRIX_KG;
        return Math.max(prix, MINIMUM);
    }
}
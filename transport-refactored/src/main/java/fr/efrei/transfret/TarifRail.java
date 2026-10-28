package fr.efrei.transfret;

public class TarifRail implements TarifTransport {

    private static final double PRIX_KM = 0.8;
    private static final double PRIX_KG = 0.07;
    private static final double MINIMUM = 150;

    @Override
    public double calculer(int distance, double poids, double volume) {
        double prix = distance * PRIX_KM + poids * PRIX_KG;
        return Math.max(prix, MINIMUM);
    }
}
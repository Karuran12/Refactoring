package fr.efrei.transfret;

public class DelaiFluvial implements DelaiTransport {

    private static final double KM_PAR_JOUR = 360.0;
    private static final int JOURS_MARGE = 1;

    @Override
    public int calculer(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_MARGE;
    }
}
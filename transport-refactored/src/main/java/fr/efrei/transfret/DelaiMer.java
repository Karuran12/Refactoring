package fr.efrei.transfret;

public class DelaiMer implements DelaiTransport {
    private static final double KM_PAR_JOUR = 720.0;
    private static final int JOURS_MARGE = 2;

    @Override
    public int calculer(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_MARGE;
    }
}
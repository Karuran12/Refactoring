package fr.efrei.transfret;

public class DelaiRail implements DelaiTransport {
    private static final double KM_PAR_JOUR = 1440.0;
    private static final int JOURS_MARGE = 1;

    @Override
    public int calculer(int distance, boolean urgent) {
        int jours = (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_MARGE;
        return urgent ? jours - 1 : jours;
    }
}
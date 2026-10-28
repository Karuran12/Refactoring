package fr.efrei.transfret;

public class DelaiAir implements DelaiTransport {

    private static final int SEUIL_LONGUE_DISTANCE = 3000; 
    private static final int DELAI_COURT = 1;
    private static final int DELAI_LONG = 2;

    @Override
    public int calculer(int distance, boolean urgent) {
        if (urgent || distance <= SEUIL_LONGUE_DISTANCE) {
            return DELAI_COURT;
        }
        return DELAI_LONG;
    }
}
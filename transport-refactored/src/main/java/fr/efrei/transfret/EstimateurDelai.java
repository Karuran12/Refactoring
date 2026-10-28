package fr.efrei.transfret;

public class EstimateurDelai {

    private static final int DELAI_MINIMUM = 1;
    // chaque mode connaît son délai, on garde juste un minimum d'un jour
    public int calculerDelai(int distance, ModeTransport mode, boolean urgent) {
        return Math.max(DELAI_MINIMUM, mode.getDelai().calculer(distance, urgent));
    }
}
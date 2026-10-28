package fr.efrei.transfret;

public class EstimateurDelai {

    private static final double VITESSE_ROUTE = 70.0;
    private static final double HEURE_CONDUITE_ROUTE = 9.0;
    private static final double HEURE_CONDUITE_ROUTE_URGENT = 18.0;
    private static final double KM_PAR_JOUR_RAIL = 1440.0;
    private static final int JOURS_MARGE_RAIL = 1;
    private static final double KM_PAR_JOUR_MER = 720.0;
    private static final int JOURS_MARGE_MER = 2;
    private static final int SEUIL_LONGUE_DISTANCE_AIR = 3000;
    private static final int DELAI_AIR_LONGUE_DISTANCE = 2;
    private static final int DELAI_AIR_COURTE_DISTANCE = 1;
    private static final int DELAI_MINIMUM = 1;

    public int calculerDelai(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        switch (mode) {
            case ROUTE:
                double heures = distance / VITESSE_ROUTE;
                if (urgent) {
                    jours = (int) Math.ceil(heures / HEURE_CONDUITE_ROUTE_URGENT);
                } else {
                    jours = (int) Math.ceil(heures / HEURE_CONDUITE_ROUTE);
                }
                break;
            case RAIL:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_RAIL) + JOURS_MARGE_RAIL;
                if (urgent) jours = jours - 1;
                break;
            case MER:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_MER) + JOURS_MARGE_MER;
                break;
            case AIR:
                jours = DELAI_AIR_COURTE_DISTANCE;
                if (distance > SEUIL_LONGUE_DISTANCE_AIR) jours = DELAI_AIR_LONGUE_DISTANCE;
                if (urgent) jours = DELAI_AIR_COURTE_DISTANCE;
                break;
        }
        if (jours < DELAI_MINIMUM) jours = DELAI_MINIMUM;
        return jours;
    }
}
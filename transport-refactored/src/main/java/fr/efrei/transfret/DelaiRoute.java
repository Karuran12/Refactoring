package fr.efrei.transfret;

public class DelaiRoute implements DelaiTransport {
    private static final double VITESSE = 70.0;     
    private static final double HEURES_PAR_JOUR = 9.0;
    private static final double HEURES_PAR_JOUR_URGENT = 18.0;

    @Override
    public int calculer(int distance, boolean urgent) {
        double heures = distance / VITESSE;
        return (int) Math.ceil(heures / (urgent ? HEURES_PAR_JOUR_URGENT : HEURES_PAR_JOUR));
    }
}
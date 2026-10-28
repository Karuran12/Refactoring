package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {
    private static final double PRIX_KM_ROUTE = 1.2;
    private static final double PRIX_KG_ROUTE = 0.10;
    private static final double PRIX_KM_RAIL = 0.8;
    private static final double PRIX_KG_RAIL = 0.07;
    private static final double MINIMUM_RAIL = 150;
    private static final double PRIX_KM_MER = 0.5;
    private static final double PRIX_KG_MER = 0.03;
    private static final double MINIMUM_MER = 500;
    private static final double PRIX_KM_AIR = 0.6;
    private static final double PRIX_KG_AIR = 4.5;
    private static final double FACTEUR_VOLUME_AIR = 167;

    private static final double COEFFICIENT_FRAGILE = 1.15;
    private static final double COEFFICIENT_DANGEREUX = 1.30;
    private static final double COEFFICIENT_REFRIGERE = 1.20;
    private static final double COEFFICIENT_URGENT = 1.5;

    private static final double REMISE_ENTREPRISE_5000 = 0.85;
    private static final double REMISE_ENTREPRISE = 0.90;
    private static final double REMISE_PREMIUM = 0.80;
    private static final double SEUIL_GROS_MONTANT = 5000;

    private static final double TAUX_ASSURANCE = 0.02;
    private static final double MONTANT_ASSURANCE_MIN = 15;


    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        double valeur = 0;
        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            poids = poids + c.getPoids();
            volume = volume + c.getVolume();
            valeur = valeur + c.getValeurDeclaree();
            if (c.getType() == TypeMarchandise.FRAGILE) fragile = true;
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }

        // tarif de base selon le mode
        double prix;
        if (mode == ModeTransport.ROUTE) {
            prix = distance * PRIX_KM_ROUTE + poids * PRIX_KG_ROUTE;
        } else if (mode == ModeTransport.RAIL) {
            prix = distance * PRIX_KM_RAIL + poids * PRIX_KG_RAIL;
            if (prix < MINIMUM_RAIL) prix = MINIMUM_RAIL;
        } else if (mode == ModeTransport.MER) {
            prix = distance * PRIX_KM_MER + poids * PRIX_KG_MER;
            if (prix < MINIMUM_MER) prix = MINIMUM_MER;
        } else if (mode == ModeTransport.AIR) {
            double poidsTaxable = poids;
            if (volume * FACTEUR_VOLUME_AIR > poids) {
                poidsTaxable = volume * FACTEUR_VOLUME_AIR;
            }
            prix = distance * PRIX_KM_AIR + poidsTaxable * PRIX_KG_AIR;
        } else {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
        }

        // suppléments
        if (fragile) prix = prix * COEFFICIENT_FRAGILE;
        if (dangereux) prix = prix * COEFFICIENT_DANGEREUX;
        if (refrigere) prix = prix * COEFFICIENT_REFRIGERE;
        if (urgent) prix = prix * COEFFICIENT_URGENT;

        // remise client
        if (typeClient == TypeClient.ENTREPRISE) {
            if (prix > SEUIL_GROS_MONTANT) {
                prix = prix * REMISE_ENTREPRISE_5000;
            } else {
                prix = prix * REMISE_ENTREPRISE;
            }
        } else if (typeClient == TypeClient.PREMIUM) {
            prix = prix * REMISE_PREMIUM;
        }

        // assurance
        if (assurance) {
            double montantAssurance = valeur * TAUX_ASSURANCE;
            if (montantAssurance < MONTANT_ASSURANCE_MIN) montantAssurance = MONTANT_ASSURANCE_MIN;
            prix = prix + montantAssurance;
        }
        return Utils.arrondir(prix);
    }
}

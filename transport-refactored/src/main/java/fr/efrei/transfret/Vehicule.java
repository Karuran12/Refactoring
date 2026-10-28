package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicule implements IVehicule {

    private final String immatriculation;
    private final double capaciteMax;
    private final ModeTransport mode;
    private double chargeActuelle;
    private boolean disponible = true;
    private final List<Colis> chargement = new ArrayList<>();

    protected Vehicule(String immatriculation, double capaciteMax, ModeTransport mode) {
        this.immatriculation = immatriculation;
        this.capaciteMax = capaciteMax;
        this.mode = mode;
    }

    @Override
    public String getImmatriculation() {
        return immatriculation;
    }

    public double getCapaciteMax() {
        return capaciteMax;
    }

    public double getChargeActuelle() {
        return chargeActuelle;
    }

    @Override
    public boolean estDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport modeDemande) {
        return disponible
                && modeDemande == mode
                && capaciteMax - chargeActuelle >= poids(colis)
                && accepte(colis);
    }

    protected boolean accepte(List<Colis> colis) {
        return true;
    }

    @Override
    public void charger(Colis colis) {
        if (chargeActuelle + colis.getPoids() > capaciteMax) {
            System.out.println("ERREUR : capacité dépassée pour " + immatriculation);
            return;
        }
        chargement.add(colis);
        chargeActuelle = chargeActuelle + colis.getPoids();
    }

    @Override
    public void decharger(Colis colis) {
        if (chargement.remove(colis)) {
            chargeActuelle = chargeActuelle - colis.getPoids();
        }
    }

    protected void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }

    protected boolean transporte(TypeMarchandise type) {
        return contient(chargement, type);
    }

    protected static boolean contient(List<Colis> colis, TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.getType() == type);
    }

    private static double poids(List<Colis> colis) {
        return colis.stream().mapToDouble(Colis::getPoids).sum();
    }
}
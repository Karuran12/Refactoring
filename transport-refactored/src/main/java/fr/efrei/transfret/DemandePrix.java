package fr.efrei.transfret;

import java.util.List;

// tout ce qu'il faut savoir pour calculer un prix
public record DemandePrix(List<Colis> colis, int distance, ModeTransport mode,
                          boolean urgent, boolean assurance, TypeClient typeClient) {

    public double poids() {
        return colis.stream().mapToDouble(Colis::getPoids).sum();
    }

    public double volume() {
        return colis.stream().mapToDouble(Colis::getVolume).sum();
    }

    public double valeur() {
        return colis.stream().mapToDouble(Colis::getValeurDeclaree).sum();
    }

    public boolean contient(TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.getType() == type);
    }
}
package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    private final ComposantPrix chaine = assembler();

    // assemble la chaîne de décorateurs une fois pour toutes
    // L'ordre compte: suppléments, urgence puis remise et puis assurance
    private static ComposantPrix assembler() {
        ComposantPrix prix = new PrixDeBase();
        prix = new SupplementFragile(prix);
        prix = new SupplementDangereux(prix);
        prix = new SupplementRefrigere(prix);
        prix = new SupplementUrgence(prix);
        prix = new RemiseClient(prix);
        prix = new Assurance(prix);
        return prix;
    }

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        DemandePrix demande = new DemandePrix(colis, distance, mode, urgent, assurance, typeClient);
        return Utils.arrondir(chaine.calculer(demande));
    }
}
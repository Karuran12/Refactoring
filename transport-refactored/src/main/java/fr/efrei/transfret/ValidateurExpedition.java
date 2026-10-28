package fr.efrei.transfret;

import java.util.List;

public class ValidateurExpedition {

    // affiche le message d'erreur et renvoie false si la demande est invalide
    public boolean estValide(Client client, List<Colis> colis, ModeTransport mode, boolean urgent) {
        if (client == null) {
            System.out.println("ERREUR : client obligatoire");
            return false;
        }
        if (colis == null || colis.isEmpty()) {
            System.out.println("ERREUR : au moins un colis est nécessaire");
            return false;
        }
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                System.out.println("ERREUR : poids invalide pour " + c.getDescription());
                return false;
            }
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }
        if (dangereux && !mode.accepteDangereux()) {
            System.out.println("ERREUR : les matières dangereuses sont interdites en transport " + mode);
            return false;
        }
        if (refrigere && !mode.accepteRefrigere()) {
            System.out.println("ERREUR : le transport réfrigéré est impossible en transport " + mode);
            return false;
        }
        if (urgent && !mode.accepteUrgence()) {
            System.out.println("ERREUR : pas d'option urgente en transport " + mode);
            return false;
        }
        return true;
    }
}
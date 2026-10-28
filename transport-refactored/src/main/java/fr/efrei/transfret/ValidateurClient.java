package fr.efrei.transfret;

public class ValidateurClient {

    // affiche le message d'erreur et renvoie false si les données sont invalides
    public boolean estValide(String nom, String email, TypeClient type, String siret) {
        if (nom == null || nom.trim().length() == 0) {
            System.out.println("ERREUR : nom obligatoire");
            return false;
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("ERREUR : email invalide (" + email + ")");
            return false;
        }
        if (type == null) {
            System.out.println("ERREUR : type de client inconnu");
            return false;
        }
        if (type == TypeClient.ENTREPRISE && (siret == null || siret.length() != 14)) {
            System.out.println("ERREUR : SIRET obligatoire (14 chiffres) pour une entreprise");
            return false;
        }
        return true;
    }
}
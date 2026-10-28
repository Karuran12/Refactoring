package fr.efrei.transfret;

public class ServiceClient {
    private Notificateur notificateur = new Notificateur();
    private ValidateurClient validateur = new ValidateurClient();

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        
        if (!validateur.estValide(nom, email, type, siret)) {
            return null;
        }

        Client c = new Client(nom, email, telephone, adresse, type, siret);
        Database.getInstance().sauvegarderClient(c);
        notificateur.envoyerEmail(c, "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}

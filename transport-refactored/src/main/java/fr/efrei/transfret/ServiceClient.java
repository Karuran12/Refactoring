package fr.efrei.transfret;

public class ServiceClient {

    private final ClientRepository clients;
    private final ValidateurClient validateur;
    private final Notificateur notificateur;

    public ServiceClient(ClientRepository clients, ValidateurClient validateur, Notificateur notificateur) {
        this.clients = clients;
        this.validateur = validateur;
        this.notificateur = notificateur;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        if (!validateur.estValide(nom, email, type, siret)) {
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clients.sauvegarder(c);
        notificateur.envoyerEmail(c, "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}
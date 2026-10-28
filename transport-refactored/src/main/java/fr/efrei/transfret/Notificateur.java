package fr.efrei.transfret;

public class Notificateur {

    private final CanalNotification email;
    private final CanalNotification sms;

    public Notificateur(CanalNotification email, CanalNotification sms) {
        this.email = email;
        this.sms = sms;
    }

    // email pour tous les clients, SMS en plus pour les clients premium
    public void notifier(Client client, String sujet, String corps, String texteSms) {
        envoyerEmail(client, sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            sms.envoyer(client.getTelephone(), sujet, texteSms);
        }
    }

    public void envoyerEmail(Client client, String sujet, String corps) {
        email.envoyer(client.getEmail(), sujet, corps);
    }
}
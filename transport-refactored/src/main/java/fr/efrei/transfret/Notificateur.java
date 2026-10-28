package fr.efrei.transfret;

public class Notificateur {

    // email pour tous les clients, SMS en plus pour les clients premium
    public void notifier(Client client, String sujet, String corps, String sms) {
        envoyerEmail(client, sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            new SmsService().envoyerSms(client.getTelephone(), sms);
        }
    }

    public void envoyerEmail(Client client, String sujet, String corps) {
        EmailService.envoyer(client.getEmail(), sujet, corps);
    }
}
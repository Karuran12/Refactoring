package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsService implements CanalNotification {

    private static final int LONGUEUR_MAX = 160;

    // garde une trace des SMS envoyés (facturés par l'opérateur)
    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(String numero, String sujet, String message) {
        if (message.length() > LONGUEUR_MAX) {
            message = message.substring(0, LONGUEUR_MAX - 3) + "...";
        }
        System.out.println("[SMS] -> " + numero + " : " + message);
        journal.add(new String[]{numero, message});
    }

    public List<String[]> getJournal() {
        return journal;
    }
}
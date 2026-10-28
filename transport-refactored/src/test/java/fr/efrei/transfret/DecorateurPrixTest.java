package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateurPrixTest {

    private static final double CENTIME = 0.01;

    // un faux tarif de base qui vaut toujours 100 €
    private static final ComposantPrix CENT_EUROS = demande -> 100;

    private static DemandePrix demande(TypeMarchandise type, boolean urgent, boolean assurance, TypeClient client) {
        Colis colis = new Colis("test", 10, 10, 10, 10, type, 1000);
        return new DemandePrix(List.of(colis), 100, ModeTransport.ROUTE, urgent, assurance, client);
    }

    private static DemandePrix standard() {
        return demande(TypeMarchandise.STANDARD, false, false, TypeClient.PARTICULIER);
    }

    @Test
    void fragile_plus_15_pourcent_seulement_si_colis_fragile() {
        assertEquals(115.0, new SupplementFragile(CENT_EUROS).calculer(demande(TypeMarchandise.FRAGILE, false, false, TypeClient.PARTICULIER)), CENTIME);
        assertEquals(100.0, new SupplementFragile(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void dangereux_plus_30_pourcent() {
        assertEquals(130.0, new SupplementDangereux(CENT_EUROS).calculer(demande(TypeMarchandise.DANGEREUX, false, false, TypeClient.PARTICULIER)), CENTIME);
        assertEquals(100.0, new SupplementDangereux(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void refrigere_plus_20_pourcent() {
        assertEquals(120.0, new SupplementRefrigere(CENT_EUROS).calculer(demande(TypeMarchandise.REFRIGERE, false, false, TypeClient.PARTICULIER)), CENTIME);
        assertEquals(100.0, new SupplementRefrigere(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void urgence_plus_50_pourcent() {
        assertEquals(150.0, new SupplementUrgence(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, true, false, TypeClient.PARTICULIER)), CENTIME);
        assertEquals(100.0, new SupplementUrgence(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void remise_delegue_a_la_politique_du_client() {
        assertEquals(80.0, new RemiseClient(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, false, TypeClient.PREMIUM)), CENTIME);
        assertEquals(100.0, new RemiseClient(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void assurance_2_pourcent_de_la_valeur() {
        // valeur déclarée 1000 € → 2 % = 20 €
        assertEquals(120.0, new Assurance(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, true, TypeClient.PARTICULIER)), CENTIME);
        assertEquals(100.0, new Assurance(CENT_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void assurance_minimum_15_euros() {
        Colis petit = new Colis("test", 10, 10, 10, 10, TypeMarchandise.STANDARD, 100); // 2 % = 2 € < 15 €
        DemandePrix d = new DemandePrix(List.of(petit), 100, ModeTransport.ROUTE, false, true, TypeClient.PARTICULIER);
        assertEquals(115.0, new Assurance(CENT_EUROS).calculer(d), CENTIME);
    }

    @Test
    void l_ordre_compte_remise_apres_supplements_et_assurance_apres_remise() {
        // 100 × 1,15 (fragile) × 1,5 (urgent) = 172,50 → remise premium -20 % = 138 → + 20 € d'assurance = 158
        ComposantPrix chaine = new Assurance(new RemiseClient(new SupplementUrgence(new SupplementFragile(CENT_EUROS))));
        assertEquals(158.0, chaine.calculer(demande(TypeMarchandise.FRAGILE, true, true, TypeClient.PREMIUM)), CENTIME);
    }
}
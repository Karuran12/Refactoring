package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionTest {

    private static final double CENTIME = 0.01;

    // ---------- mode FLUVIAL ----------

    @Test
    void tarif_fluvial_au_km_et_au_kg() {
        assertEquals(600.0, new TarifFluvial().calculer(1000, 10000, 0), CENTIME);
    }

    @Test
    void tarif_fluvial_minimum_300() {
        assertEquals(300.0, new TarifFluvial().calculer(100, 100, 0), CENTIME);
    }

    @Test
    void delai_fluvial_distance_sur_360_arrondi_plus_un_jour() {
        assertEquals(3, new DelaiFluvial().calculer(365, false));
    }

    @Test
    void fluvial_interdit_le_refrigere_et_l_urgence() {
        assertFalse(ModeTransport.FLUVIAL.accepteRefrigere());
        assertFalse(ModeTransport.FLUVIAL.accepteUrgence());
    }

    @Test
    void la_peniche_ne_prend_que_le_fluvial() {
        Peniche peniche = new Peniche("PEN-0001", 2500000);
        List<Colis> colis = List.of(new Colis("test", 1000, 100, 100, 100, TypeMarchandise.STANDARD, 100));
        assertTrue(peniche.peutPrendreEnCharge(colis, ModeTransport.FLUVIAL));
        assertFalse(peniche.peutPrendreEnCharge(colis, ModeTransport.ROUTE));
    }

    @Test
    void expedition_fluvial_paris_le_havre_de_bout_en_bout() {
        Notificateur notificateur = new Notificateur(new EmailService(), new SmsService());
        ServiceClient clients = new ServiceClient(new ClientRepositoryMemoire(), new ValidateurClient(), notificateur);
        ServiceExpedition service = new ServiceExpedition(new ExpeditionRepositoryMemoire(), new CalculateurPrix(),
                new AffectationVehicule(new Flotte()), new EstimateurDelai(), new ValidateurExpedition(), notificateur);

        Client client = clients.creerClient("Jean", "jean@mail.fr", "06", "Paris", TypeClient.PARTICULIER, null);
        List<Colis> colis = List.of(new Colis("Sable", 10000, 100, 100, 100, TypeMarchandise.STANDARD, 500));
        Expedition e = service.creerExpedition(client, colis, "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false);

        assertEquals("PEN-0001", e.getVehicule().getImmatriculation());
        assertEquals(346.0, e.getPrixHT(), CENTIME);
        assertNull(service.creerExpedition(client, colis, "Paris", "Le Havre", ModeTransport.FLUVIAL, true, false));
    }

    // ---------- supplément hors gabarit ----------

    private static final ComposantPrix CENT_EUROS = demande -> 100;

    private static DemandePrix demandeAvecLongueur(double longueurCm) {
        Colis colis = new Colis("test", 10, longueurCm, 50, 50, TypeMarchandise.STANDARD, 100);
        return new DemandePrix(List.of(colis), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
    }

    @Test
    void hors_gabarit_plus_10_pourcent_au_dela_de_200_cm() {
        assertEquals(110.0, new SupplementHorsGabarit(CENT_EUROS).calculer(demandeAvecLongueur(201)), CENTIME);
    }

    @Test
    void pas_de_supplement_jusqu_a_200_cm() {
        assertEquals(100.0, new SupplementHorsGabarit(CENT_EUROS).calculer(demandeAvecLongueur(200)), CENTIME);
    }
}
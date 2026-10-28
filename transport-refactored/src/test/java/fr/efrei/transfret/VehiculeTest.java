package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehiculeTest {

    private static List<Colis> colis(TypeMarchandise type, double poids) {
        return List.of(new Colis("test", poids, 10, 10, 10, type, 100));
    }

    @Test
    void chaque_vehicule_ne_prend_que_son_mode() {
        Camion camion = new Camion("C", 1000, false);
        assertTrue(camion.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD, 10), ModeTransport.ROUTE));
        assertFalse(camion.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD, 10), ModeTransport.RAIL));
    }

    @Test
    void camion_dangereux_seulement_avec_adr() {
        assertFalse(new Camion("C", 1000, false).peutPrendreEnCharge(colis(TypeMarchandise.DANGEREUX, 10), ModeTransport.ROUTE));
        assertTrue(new Camion("C", 1000, true).peutPrendreEnCharge(colis(TypeMarchandise.DANGEREUX, 10), ModeTransport.ROUTE));
    }

    @Test
    void camion_frigorifique_seulement_le_refrigere() {
        CamionFrigorifique frigo = new CamionFrigorifique("F", 1000);
        assertTrue(frigo.peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE, 10), ModeTransport.ROUTE));
        assertFalse(frigo.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD, 10), ModeTransport.ROUTE));
    }

    @Test
    void bateau_refrigere_seulement_avec_conteneurs() {
        assertFalse(new Bateau("B", 100000, false).peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE, 10), ModeTransport.MER));
        assertTrue(new Bateau("B", 100000, true).peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE, 10), ModeTransport.MER));
    }

    @Test
    void refuse_si_indisponible_ou_trop_lourd() {
        Train train = new Train("T", 100);
        assertFalse(train.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD, 500), ModeTransport.RAIL));
        train.setDisponible(false);
        assertFalse(train.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD, 10), ModeTransport.RAIL));
    }
}
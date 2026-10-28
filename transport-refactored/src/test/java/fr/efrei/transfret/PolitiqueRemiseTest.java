package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolitiqueRemiseTest {

    private static final double CENTIME = 0.01;

    @Test
    void particulier_sans_remise() {
        assertEquals(100.0, new SansRemise().appliquer(100), CENTIME);
    }

    @Test
    void entreprise_moins_10_pourcent_jusqu_a_5000() {
        assertEquals(900.0, new RemiseEntreprise().appliquer(1000), CENTIME);
        assertEquals(4500.0, new RemiseEntreprise().appliquer(5000), CENTIME);
    }

    @Test
    void entreprise_moins_15_pourcent_au_dela_de_5000() {
        assertEquals(8500.0, new RemiseEntreprise().appliquer(10000), CENTIME);
    }

    @Test
    void premium_moins_20_pourcent() {
        assertEquals(80.0, new RemisePremium().appliquer(100), CENTIME);
    }

    @Test
    void chaque_type_de_client_porte_sa_remise() {
        assertEquals(100.0, TypeClient.PARTICULIER.getRemise().appliquer(100), CENTIME);
        assertEquals(90.0, TypeClient.ENTREPRISE.getRemise().appliquer(100), CENTIME);
        assertEquals(80.0, TypeClient.PREMIUM.getRemise().appliquer(100), CENTIME);
    }
}
package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifTransportTest {

    private static final double CENTIME = 0.01;

    @Test
    void route_prix_au_km_et_au_kg() {
        // 100 km × 1,20 + 50 kg × 0,10
        assertEquals(125.0, new TarifRoute().calculer(100, 50, 0), CENTIME);
    }

    @Test
    void rail_prix_au_km_et_au_kg() {
        // 1000 km × 0,80 + 1000 kg × 0,07
        assertEquals(870.0, new TarifRail().calculer(1000, 1000, 0), CENTIME);
    }

    @Test
    void rail_minimum_150() {
        assertEquals(150.0, new TarifRail().calculer(100, 100, 0), CENTIME);
    }

    @Test
    void mer_prix_au_km_et_au_kg() {
        // 2000 km × 0,50 + 10000 kg × 0,03
        assertEquals(1300.0, new TarifMer().calculer(2000, 10000, 0), CENTIME);
    }

    @Test
    void mer_minimum_500() {
        assertEquals(500.0, new TarifMer().calculer(100, 100, 0), CENTIME);
    }

    @Test
    void air_au_poids_volumetrique() {
        // 1 m3 = 167 kg taxables > 10 kg réels : 100 × 0,60 + 167 × 4,50
        assertEquals(811.5, new TarifAir().calculer(100, 10, 1), CENTIME);
    }

    @Test
    void air_au_poids_reel() {
        // 500 kg réels > 167 kg volumétriques : 100 × 0,60 + 500 × 4,50
        assertEquals(2310.0, new TarifAir().calculer(100, 500, 1), CENTIME);
    }
}
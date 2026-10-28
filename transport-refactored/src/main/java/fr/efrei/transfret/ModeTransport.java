package fr.efrei.transfret;

public enum ModeTransport {
    ROUTE(new TarifRoute()),
    RAIL(new TarifRail()),
    MER(new TarifMer()),
    AIR(new TarifAir());

    private final TarifTransport tarif;

    ModeTransport(TarifTransport tarif) {
        this.tarif = tarif;
    }

    public TarifTransport getTarif() {
        return tarif;
    }
}
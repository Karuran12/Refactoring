package fr.efrei.transfret;

public enum ModeTransport {
    ROUTE(new TarifRoute(),new DelaiRoute(),true,true,true),
    RAIL(new TarifRail(),new DelaiRail(),true,false,true),
    MER(new TarifMer(),new DelaiMer(),true,true,false),
    AIR(new TarifAir(),new DelaiAir(),false,false,true),
    FLUVIAL(new TarifFluvial(),new DelaiFluvial(),true,false,false);

    private final TarifTransport tarif;
    private final DelaiTransport delai;
    private final boolean accepteDangereux;
    private final boolean accepteRefrigere;
    private final boolean accepteUrgence;

    ModeTransport(TarifTransport tarif, DelaiTransport delai, boolean accepteDangereux, boolean accepteRefrigere, boolean accepteUrgence) {
        this.tarif = tarif;
        this.delai = delai;
        this.accepteDangereux = accepteDangereux;
        this.accepteRefrigere = accepteRefrigere;
        this.accepteUrgence = accepteUrgence;
    }

    public TarifTransport getTarif() {
        return tarif;
    }

    public DelaiTransport getDelai() {
        return delai;
    }

    public boolean accepteDangereux() {
        return accepteDangereux;
    }

    public boolean accepteRefrigere() {
        return accepteRefrigere;
    }

    public boolean accepteUrgence() {
        return accepteUrgence;
    }
}
package fr.efrei.transfret;

public enum TypeClient {
    PARTICULIER(new SansRemise()),
    ENTREPRISE(new RemiseEntreprise()),
    PREMIUM(new RemisePremium());

    private final PolitiqueRemise remise;

    TypeClient(PolitiqueRemise remise) {
        this.remise = remise;
    }

    public PolitiqueRemise getRemise() {
        return remise;
    }
}
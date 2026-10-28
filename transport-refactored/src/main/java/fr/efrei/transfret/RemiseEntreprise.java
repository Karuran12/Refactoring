package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_GROS_MONTANT = 5000;
    private static final double COEF_GROS_MONTANT = 0.85;  // -15 %
    private static final double COEF_STANDARD = 0.90;      // -10 %

    @Override
    public double appliquer(double prix) {
        return prix > SEUIL_GROS_MONTANT ? prix * COEF_GROS_MONTANT : prix * COEF_STANDARD;
    }
}
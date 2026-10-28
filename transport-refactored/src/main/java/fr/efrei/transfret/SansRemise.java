package fr.efrei.transfret;

public class SansRemise implements PolitiqueRemise {

    @Override
    public double appliquer(double prix) {
        return prix;
    }
}
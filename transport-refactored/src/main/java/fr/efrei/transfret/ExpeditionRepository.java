package fr.efrei.transfret;

public interface ExpeditionRepository {

    void sauvegarder(Expedition expedition);

    Expedition trouver(String id);
}
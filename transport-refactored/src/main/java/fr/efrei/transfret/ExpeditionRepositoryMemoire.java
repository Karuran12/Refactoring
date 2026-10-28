package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;

public class ExpeditionRepositoryMemoire implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new HashMap<>();

    @Override
    public void sauvegarder(Expedition expedition) {
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public Expedition trouver(String id) {
        return expeditions.get(id);
    }
}
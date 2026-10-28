package fr.efrei.transfret;

public interface ClientRepository {

    void sauvegarder(Client client);

    Client trouver(String email);
}
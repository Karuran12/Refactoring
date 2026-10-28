# Étape 1 - Diagnostic du code historique

Avant de modifier le code, on l'a lu classe par classe (SOLID) pour comprendre ce qu'il fait et repérer ce qui pose problème.
Les numéros de ligne renvoient au code de `transport-legacy` (identique à notre copie de l'étape 0 dans le fichier `transport-refactored`).

---

## 1) Rôle et dépendances des classes

| Classe | Ce qu'elle fait | Dépend de |
|---|---|---|
| `Main` | Programme de démonstration: crée des clients, des expéditions et affiche les factures | `Flotte`, `ServiceClient`, `ServiceExpedition`, `Colis` |
| `ServiceClient` | Vérifie les infos d'un client, l'enregistre et lui envoie un email de bienvenue | `Client`, `Database`, `EmailService` |
| `ServiceExpedition` | Crée une expédition (vérifications, véhicule, prix, délai), gère son cycle de vie (départ, livraison, annulation) et prévient le client | `Database`, `CalculateurPrix`, `AffectationVehicule`, `Utils`, `EmailService`, `SmsService`, `Vehicule` |
| `CalculateurPrix` | Calcule le prix HT: tarif selon le mode, suppléments, urgence, remise client, assurance | `Colis` |
| `AffectationVehicule` | Cherche le premier véhicule libre et compatible avec les colis et le mode | `Flotte`, `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` |
| `Expedition` | Contient les données d'une expédition et génère aussi sa facture | `Client`, `Colis`, `Vehicule`, `Utils` |
| `Client` | Données d'un client (le type est une `String`) | – |
| `Colis` | Données d'un colis (le type est un `int` de 0 à 3), calcule son volume | – |
| `Database` | Fausse base de données: stocke tout dans des `Map` statiques et affiche du SQL | `Client`, `Expedition` |
| `EmailService` | Envoie un email (méthode statique) et garde un historique | – |
| `SmsService` | Envoie un SMS et garde un historique | – |
| `Flotte` | Liste statique de tous les véhicules | Tous les véhicules |
| `Utils` | Boîte à outils: arrondi, dates, distances entre villes | – |
| `IVehicule` | Interface commune à tous les véhicules | `Colis` |
| `Vehicule` | Classe mère des véhicules: capacité, chargement, disponibilité | `Colis` |
| `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` | Les véhicules concrets | `Vehicule` |

**En résumé:** deux classes font presque tout le travail (`ServiceExpedition` et `CalculateurPrix`) et beaucoup de classes passent par des éléments statiques (`Database`, `EmailService`, `Flotte`).

---

## 2) Violations des principes SOLID

### S - Responsabilité unique (Une classe ne devrait avoir qu'une seule raison de changer)

| Défaut | Emplacement |
|---|---|
| Une expédition fabrique elle-même sa facture. Si on change la mise en page de la facture, on doit modifier l'entité `Expedition` | `Expedition.genererFacture()` -> l.52-82 |
| `ServiceExpedition` fait tout: il vérifie les données, choisit le véhicule, calcule le prix TTC, calcule le délai, gère les statuts et envoie les emails/SMS | `creerExpedition` -> l.17-51 (vérifications), `calculerDelai` -> l.101-127, `notifier` -> l.204-209 |
| `ServiceClient` vérifie les données, enregistre le client et envoie l'email | `ServiceClient.creerClient` -> l.5-26 |
| Une seule méthode calcule le tarif, les suppléments, l'urgence, la remise et l'assurance | `CalculateurPrix.calculerPrixHT` -> l.7-67 |

### O - Ouvert/fermé (On doit pouvoir ajouter un comportement sans modifier le code existant)

| Défaut | Emplacement |
|---|---|
| Une chaîne de `if/else` sur le mode. Ajouter un mode = modifier la classe | `CalculateurPrix` -> l.25-41 |
| Une chaîne de `if/else` sur le type de client pour la remise | `CalculateurPrix` -> l.50-58 |
| Un `switch` sur le mode pour le délai | `ServiceExpedition.calculerDelai` -> l.103-124 |
| Un `if/else` sur le mode pour faire partir le véhicule | `ServiceExpedition.demarrerExpedition` -> l.142-149 |
| Des `if` sur le mode et des `instanceof` pour choisir un véhicule | `AffectationVehicule.trouverVehicule` -> l.20-52 |
| Un `switch` sur un entier pour le libellé du colis | `Colis.getLibelleType()` -> l.33-46 |
| Une chaîne de `if` pour chaque trajet. Ajouter une ville = modifier la méthode | `Utils.getDistance` -> l.30-51 |

### L - Substitution de Liskov (Une sous-classe doit pouvoir remplacer sa classe mère sans rien casser)

| Défaut | Emplacement |
|---|---|
| `CamionFrigorifique` refuse les colis non réfrigérés, alors qu'un `Camion` les accepte, on ne peut donc pas l'utiliser partout où un `Camion` est attendu (La preuve: `AffectationVehicule` doit l'éviter exprès (sinon ça plante)) | `CamionFrigorifique.charger` -> l.9-16, `AffectationVehicule` -> l.26-28 |
| `Train.faireLePlein()` lance une exception alors que `Vehicule.faireLePlein()` fonctionne, l'erreur est cachée par un `catch` vide | `Train` -> l.29-32, `ServiceExpedition` -> l.138-141 |

### I - Ségrégation des interfaces (Une classe ne devrait pas être obligée d'implémenter des méthodes dont elle n'a pas besoin)

| Défaut | Emplacement |
|---|---|
| `IVehicule` oblige tous les véhicules à savoir `rouler`, `naviguer`, `voler` et `activerRefrigeration` | `IVehicule` -> l.22-28 |
| Résultat: des méthodes qui ne font que lancer une `UnsupportedOperationException` (un camion qui "ne vole pas", un bateau qui "ne roule pas") | `Camion` -> l.16-26, `Train` -> l.13-23, `Bateau` -> l.12-22, `Avion` -> l.14-24 |

### D - Inversion des dépendances (Une classe devrait recevoir ses dépendances plutôt que les créer elle-même)

| Défaut | Emplacement |
|---|---|
| `ServiceExpedition` crée lui-même ses outils avec `new` et le singleton `Database.getInstance()`, impossible de les remplacer pour un test | `ServiceExpedition` -> l.12-14 |
| Un `new SmsService()` en plein milieu d'une méthode | `ServiceExpedition` -> l.207 |
| L'email est envoyé par une méthode statique | `EmailService.envoyer`, appelé dans `ServiceExpedition` -> l.205 et `ServiceClient` -> l.24 |
| La flotte est lue directement dans un champ statique | `AffectationVehicule` -> l.17 (`Flotte.vehicules`) |
| Beaucoup de données statiques modifiables (La preuve: `LegacyAdapter` doit les vider à la main avant chaque test) | `Database.expeditions`, `Database.clients`, `EmailService.journal`, `SmsService.journal`, `ServiceExpedition.compteur`, `Flotte.vehicules` |

---

## 3) Autres défauts repérés

| Défaut | Emplacement |
|---|---|
| **Nombres magiques**: `1.2`, `0.10`, `150`, `500`, `167`, `1.15`, `5000`, `0.85` On ne sait pas ce qu'ils représentent | `CalculateurPrix` -> l.26-64 |
| **Nombres magiques**: `70`, `9`, `18`, `1440`, `720`, `1.852`, `35000`, TVA `1.2` | `ServiceExpedition` -> l.76, 97, 105-121, 145, 186 |
| Le mode, le statut et le type de client sont des `String`, et le type de colis un `int`, une faute de frappe (`"ROTUE"`) passe inaperçue | `Expedition`, `Client`, `Colis` |
| `getType() == 2` ou `== 3` répété partout: il faut se souvenir que 3 veut dire "réfrigéré" | `AffectationVehicule` -> l.14-15, `CalculateurPrix` -> l.18-20, `ServiceExpedition` -> l.32-33 et 151 |
| L'arrondi `Math.round(x * 100.0) / 100.0` est copié 4 fois, alors que `Utils.arrondir()` existe et n'est jamais utilisé | `CalculateurPrix` -> l.66, `ServiceExpedition` -> l.76, 97, 186 |
| Les erreurs sont juste affichées avec `println`, puis la méthode renvoie `null` ou `-1` | `ServiceClient`, `ServiceExpedition` |
| Des erreurs sont avalées sans rien dire | `ServiceExpedition` -> l.140-141, `EmailService` -> l.23-26 |
| Une clé d'API est écrite en dur dans le code | `SmsService` -> l.11 |
| Les requêtes SQL sont construites par concaténation (risque d'injection SQL) | `Database` -> l.34-48 |

---

## 4) Ajouter le mode `FLUVIAL`: ce qu'il faudrait modifier aujourd'hui

| # | Fichier | Ce qu'il faut faire |
|---|---|---|
| 1 | `CalculateurPrix` -> l.25-41 | Ajouter un `else if` pour le tarif fluvial (0,40 €/km + 0,02 €/kg, minimum 300 €) |
| 2 | `ServiceExpedition.calculerDelai` -> l.103-124 | Ajouter un `case "FLUVIAL"` pour le délai |
| 3 | `ServiceExpedition.creerExpedition` -> l.43 | Interdire l'option urgente en fluvial |
| 4 | `ServiceExpedition.creerExpedition` -> l.39 | Vérifier que le réfrigéré reste interdit |
| 5 | `ServiceExpedition.demarrerExpedition` -> l.142-149 | Ajouter un cas pour faire partir la péniche |
| 6 | `AffectationVehicule.trouverVehicule` -> l.20-52 | Ajouter une branche `FLUVIAL` avec un `instanceof Peniche` |
| 7 | **Nouveau** `Peniche.java` | Créer la classe, avec des `UnsupportedOperationException` pour `rouler`, `voler` |
| 8 | `Flotte.initialiser` | Ajouter la péniche `PEN-0001` de 2 500 t |
| 9 | `Utils.getDistance` | Ajouter le trajet Paris ↔ Le Havre (365 km) |

**Bilan: 6 fichiers existants à modifier, plus une nouvelle classe.** Si on oublie un des `if`, le code compile quand même: l'erreur n'apparaît qu'à l'exécution.

---

## 5) Ajouter le supplément "hors gabarit": ce qu'il faudrait modifier aujourd'hui

| # | Fichier | Ce qu'il faut faire |
|---|---|---|
| 1 | `CalculateurPrix` -> l.14-21 | Dans la boucle, repérer un colis qui dépasse 200 cm sur une dimension |
| 2 | `CalculateurPrix` -> l.43-47 | Ajouter `prix = prix * 1.10`, **avant** la remise et l'assurance |
| 3 | `Colis` (conseillé) | Ajouter une méthode `estHorsGabarit()` pour garder la règle près du colis |

**Bilan: 1 à 2 fichiers existants à modifier, dont toujours `CalculateurPrix`, qui fait déjà trop de choses.**

---

## Conclusion

Le code fonctionne, mais il est difficile à faire évoluer: chaque nouveauté oblige à retrouver et modifier plusieurs `if` dispersés.
On compare ces deux listes à l'étape 8, une fois le refactoring terminé.
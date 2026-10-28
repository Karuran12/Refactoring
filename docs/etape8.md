# Étape 8 – Preuve par l'extension

## Combien de fichiers existants a-t-on modifiés ?

| Évolution | Avant le refactoring (étape 1) | Après le refactoring |
|---|---|---|
| Mode `FLUVIAL` | 6 fichiers existants à modifier (4 `if`/`switch` sur le mode à retrouver) + 1 nouvelle classe | **3 fichiers existants, 1 ligne chacun** (`ModeTransport`, `Flotte`, `Utils`) + 3 nouvelles classes (`TarifFluvial`, `DelaiFluvial`, `Peniche`) |
| Hors gabarit | 1 à 3 fichiers, dont toujours `CalculateurPrix` (boucle + `if`) | **1 ligne** dans l'assemblage de `CalculateurPrix` + 1 nouvelle classe (`SupplementHorsGabarit`) |

Les 3 lignes de `FLUVIAL` sont de la **configuration**: déclarer le mode, ajouter la péniche
à la flotte, ajouter le trajet. Aucune logique existante n'est modifiée et on ne peut plus
oublier un `if` caché: le compilateur oblige à fournir un tarif et un délai au nouveau mode.

Pour y arriver, on a d'abord fini l'étape 5 (bonus): le délai et les règles
(dangereux, réfrigéré, urgence) sont passés dans l'enum `ModeTransport`.

## Les 68 tests passent-ils toujours ?

- Après `FLUVIAL`: **oui, 68 / 68**. Ajouter un mode ne change rien aux modes existants.
- Après "hors gabarit": **non, 62 / 68**. Les 6 tests qui échouent utilisent des colis
  de plus de 200 cm (fûts de 240 cm, bobines et conteneur de 1 200 cm) et vérifient
  les anciens prix, sans supplément.

Ce n'est pas un bug: la règle métier a changé, le contrat décrit encore l'ancienne.
Les tests de contrat jouent leur rôle: ils signalent tout changement de comportement visible.
Il faudrait mettre à jour `transport-contrat` avec le métier (nous n'avons pas le droit d'y toucher).

## Tests unitaires des nouvelles classes

`ExtensionTest`: tarif fluvial (prix et minimum), délai fluvial, interdictions (réfrigéré, urgence),
péniche, expédition Paris → Le Havre de bout en bout, supplément hors gabarit (201 cm / 200 cm).
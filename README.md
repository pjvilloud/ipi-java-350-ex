# TP sur la qualité logicielle

Exercices de Java, module 350 pour l'IPI. Il est nécessaire de forker ce repository pour pouvoir faire tout le TP !! Après chaque question, pusher vos modifications sur votre repository.

## Pré-requis

- Installer IntelliJ Ultimate en utilisant votre adresse IPI sur Jetbrains Student https://www.jetbrains.com/student/
ou un autre IDE si vous avez l'habitude (Eclipse)
- Si vous n'êtes pas familié avec Git, je vous recommande ce site : https://learngitbranching.js.org/ (faire au moins la première leçon du niveau 1)
Via Microsoft Imagine, activer votre crédit étudiant Azure https://imagine.microsoft.com/fr-fr (ou via le mail reçu en début d'année, onthehub https://onthehub.com/)
- S'inscrire également au programme AWS Educate toujours avec votre adresse IPI (https://aws.amazon.com/fr/education/awseducate/).

## Introduction

Questions à se poser : 
- Que représente pour vous la qualité logicielle ?
- Quels éléments faut-il pour avoir un logiciel de qualité ?
- Dans un projet, qui est responsable de la qualité ?
- Quand doit-on se préoccuper de la qualité dans la vie d'un projet ?

Faire une recherche sur la qualité logicielle sur le web...
Répondre de nouveau aux questions. Qu'est-ce qui a changé ?

## Intégration continue

   - Rajouter la configuration nécessaire pour Travis dans le projet.
   - Vous connecter à Travis https://travis-ci.org avec votre compte Github.
   - Configurer le projet et vérifier que le premier build se passe correctement. Après chaque exercice, vérifier que le build passe toujours...
 
## Evaluation de la qualité

   - Connectez-vous à SonarQube https://about.sonarcloud.io/ avec votre compte Github
   - Ajouter votre projet dans Sonar
   - Modifier votre configuration Travis pour lancer une analyse après chaque build
   - Vérifier que tout est ok
   - Analyser le premier rapport de Sonar

## Tests unitaires

### Tests unitaires classiques

Créer la classe permettant de tester la méthode `getNombreAnneeAnciennete` et mettre en place les tests unitaires nécessaires pour tester le plus exhaustivement possible cette méthode. Bien penser à tous les cas possibles, notamment les cas aux limites. Ne pas hésiter à corriger le code de la méthode initiale si besoin.

### Tests paramétrés

Créer une méthode de test paramétré permettant de tester le plus exhaustivement possible la méthode `getPrimeAnnuelle` et corriger les éventuels problème de cette méthode.

### Tests mockés

Créer la classe de test et les méthodes permettant de tester la méthode `embaucheEmploye` de `EmployeService` sans la dépendance à la BDD.

## Tests d'intégration

### Tests de com.ipiecoles.java.java350.repository

Créer la classe de test et les méthodes permettant de tester la méthode `findLastMatricule` de `EmployeRepository`.

### Tests de service intégrés

Tester de façon intégrée un cas nominal de la méthode `embaucheEmploye`.

## Tests d'acceptation

TODO

## Maintenabilité

- S'assurer de la lisibilité du code et du respect des conventions
- Ajouter des `logger` aux endroits stratégiques du code en utilisant le bon niveau de log.
- Vérifier et le cas échéant compléter la documentation du code, générer la JavaDoc.
- Ajouter à votre Github une documentation statique avec Jekyll
- Ajouter des badges contenant les métriques principales de votre projet en haut de ce README

# Evaluation

==== BASE ====
TODO 
==== BASE ====

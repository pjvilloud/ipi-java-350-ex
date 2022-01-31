Specification Heading
=====================
Created by Achraf on 31/01/2022

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
Scenario Heading
----------------
## N°1 : Calcule de l'indicateur de performance d'un commercial avec un CA inférieur ou égale à 0
* Soit un employé appelé "Mohamed" "Boudissa" de matricule "C0001" un chiffre d'affaire de "-1000" euro et avec un objectif de "10000" euro
* Je calcul l'indice de performance de "Mohamed" "Boudissa" de matricule "C0001"
*J'obtiens le message d'erreur : "Le chiffre d'affaire traité ne peut être négatif ou null !"

## N°2 : Calcule de l'indicateur de performance d'un employé qui n'est pas un commercial
* Soit un employé appelé "Mohamed" "Boudissa" de matricule "T0001" un chiffre d'affaire de "1000" euro et avec un objectif de "10000" euro
* Je calcul l'indice de performance de "Mohamed" "Boudissa" de matricule "T0001"
*J'obtiens le message d'erreur : "Le matricule ne peut être null et doit commencer par un C !"

## N°3 : Calcule de l'indicateur de performance d'un commercial avec un objectif de CA inférieur ou égale à 0
* Soit un employé appelé "Mohamed" "Boudissa" de matricule "C0001" un chiffre d'affaire de "1000" euro et avec un objectif de "-10000" euro
* Je calcul l'indice de performance de "Mohamed" "Boudissa" de matricule "C0001"
*J'obtiens le message d'erreur : "L'objectif de chiffre d'affaire ne peut être négatif ou null !"

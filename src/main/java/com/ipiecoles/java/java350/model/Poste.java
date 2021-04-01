package com.ipiecoles.java.java350.model;

public enum Poste {
    TECHNICIEN(0),
    MANAGER(1),
    COMMERCIAL(2);

    int value;

    Poste(int _poste) {
        value = _poste;
    }
}

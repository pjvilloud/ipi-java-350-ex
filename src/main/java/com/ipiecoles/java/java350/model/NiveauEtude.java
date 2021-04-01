package com.ipiecoles.java.java350.model;

public enum NiveauEtude {
    CAP(0),
    BAC(1),
    BTS_IUT(2),
    LICENCE(3),
    MASTER(4),
    INGENIEUR(5),
    DOCTORAT(6);

    int value;

    NiveauEtude(int _lvl) {
        value = _lvl;
    }
}

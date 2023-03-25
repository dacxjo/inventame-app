package com.ubpis.inventame.model;

/**
 * Esta clase esta basada en la P4 de Diseño de Software. Almacena distintos status.
 */

public enum StatusType {

    CONTRASENYA_NO_SEGURA("Contrasenya no prou segura"),
    CONTRASENYA_SEGURA("Contrasenya segura"),
    CORREU_INCORRECTE("Correu en format incorrecte"),
    CORREU_CORRECTE("Correu en format correcte"),

    CORREU_INEXISTENT("Correu inexistent"),

    CONTRASENYA_INCORRECTA("Contrasenya incorrecta"),
    LOGIN_CORRECTE("Login correcte"),
    PERSONA_DUPLICADA("Persona Duplicada"),
    REGISTRE_VALID("Registre valid"),
    FORMAT_INCORRECTE("Format incorrecte"),
    FORMAT_INCORRECTE_CORREU_PWD("Format incorrecte en l'email o contrasenya poc segura ha de contenir 8 caracters, amb: Mayus, Minus i Numeros");

    private final String text;

    StatusType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

package com.ubpis.inventame.model;

/**
 * Esta clase esta basada en la P4 de Diseño de Software. Almacena distintos status.
 */

public enum StatusType {

    PASSWORD_INSECURE("Contraseña no es segura"),
    PASSWORD_SECURE("Contraseña segura"),
    EMAIL_INCORRECT("Correo en formato incorrecto"),
    EMAIL_CORRECT("Correo en formato correcto"),

    EMAIL_NONEXISTENT("Correo inexistente"),

    PASSWORD_INCORRECT("Contraseña incorrecta"),
    LOGIN_CORRECT("Login correcto"),
    USER_DUPLICATED("Persona Duplicada"),
    REGISTER_VALID("Registro valido"),
    FORMAT_INCORRECTE("Formato incorrecto"),
    FORMAT_INCORRECT_EMAIL_PWD("Formato incorrecto en email o contraseña poco segura. Ha de contener 8 caracteres, con: Mayus, Minus y Numeros");

    private final String text;

    StatusType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

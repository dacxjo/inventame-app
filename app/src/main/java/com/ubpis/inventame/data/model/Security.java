package com.ubpis.inventame.data.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase esta basada en la P4 de Diseño de Software. Aisla y valida correo y contraseña.
 */

public class Security {

    public static boolean isMail(String correu) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correu);
        return matcher.find();
    }

    public static boolean isPasswordSegur(String password) {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static StatusType validatePassword(String b) {
        if (!isPasswordSegur(b)) {
            return StatusType.PASSWORD_INSECURE;
        } else
            return StatusType.PASSWORD_SECURE;
    }

    public static StatusType validateUsername(String b) {
        if (!isMail(b))
            return StatusType.EMAIL_INCORRECT;
        else
            return StatusType.EMAIL_CORRECT;
    }
}

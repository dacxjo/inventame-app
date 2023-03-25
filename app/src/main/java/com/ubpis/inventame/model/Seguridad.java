package com.ubpis.inventame.model;

import com.ubpis.inventame.model.StatusType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase esta basada en la P4 de Diseño de Software. Aisla y valida correo y contraseña.
 */

public class Seguridad {

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
            return StatusType.CONTRASENYA_NO_SEGURA;
        } else
            return StatusType.CONTRASENYA_SEGURA;
    }

    public static StatusType validateUsername(String b) {
        if (!isMail(b))
            return StatusType.CORREU_INCORRECTE;
        else
            return StatusType.CORREU_CORRECTE;
    }
}

package com.ubpis.inventame.model;

public class ModelFacade {

    private static ModelFacade uniqueInstance;

    private Inventame inventame;

    private ModelFacade(Inventame inventame) {
        this.inventame = inventame;
    }

    public static ModelFacade getInstance(Inventame inventame){
        if (uniqueInstance == null) {
            uniqueInstance = new ModelFacade(inventame);
        }
        return uniqueInstance;
    }

    public StatusType validateUserRegister(String username, String password) {
        if (Seguridad.isMail((username)) && Seguridad.isPasswordSegur(password)) {
            Usuario usuario = findUserByEmail(username);
            if (usuario != null) {
                return StatusType.PERSONA_DUPLICADA; // Persona duplicada
            } else return StatusType.REGISTRE_VALID; // Registre valid
        } else return StatusType.FORMAT_INCORRECTE_CORREU_PWD; // Format incorrecte
    }

    public int loginUser(String username, String password) {
        Usuario usuario = findUserByEmail(username);
        if (usuario == null) {
            return 1; // "Correu inexistent";
        }
        if (usuario.getPwd().equals(password)) {
            return 0; // "Login correcte";
        } else {
            return 2; //"Contrassenya incorrecta";
        }
    }

    // Este metodo se debe modificar con la base de datos
    // TODO
    public String recoverPwd(String username) {
        Usuario usuario = findUserByEmail(username);
        if (usuario == null) {
            return StatusType.CORREU_INEXISTENT.toString();
        }
        return usuario.getPwd();
    }

    public StatusType loginUserStatus(String correu, String password) {
        Usuario usuario = findUserByEmail(correu);
        if (usuario == null) {
            return StatusType.CORREU_INEXISTENT;
        }
        if (usuario.getPwd().equals(password)) {
            return StatusType.LOGIN_CORRECTE;
        } else {
            return StatusType.CONTRASENYA_INCORRECTA;
        }
    }

    public Usuario findUserByEmail(String email) {
        ListaEmpleados empleados = inventame.getEmpleados();
        return(empleados.find(email));
    }
}

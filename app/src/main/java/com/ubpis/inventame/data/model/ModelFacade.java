package com.ubpis.inventame.data.model;

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
        if (Security.isMail((username)) && Security.isPasswordSegur(password)) {
            User user = findUserByEmail(username);
            if (user != null) {
                return StatusType.USER_DUPLICATED; // Persona duplicada
            } else return StatusType.REGISTER_VALID; // Registre valid
        } else return StatusType.FORMAT_INCORRECT_EMAIL_PWD; // Format incorrecte
    }

    public int loginUser(String username, String password) {
        User user = findUserByEmail(username);
        if (user == null) {
            return 1; // "Correu inexistent";
        }
        if (user.getPwd().equals(password)) {
            return 0; // "Login correcte";
        } else {
            return 2; //"Contrassenya incorrecta";
        }
    }

    // Este metodo se debe modificar con la base de datos
    // TODO
    public String recoverPwd(String username) {
        User user = findUserByEmail(username);
        if (user == null) {
            return StatusType.EMAIL_NONEXISTENT.toString();
        }
        return user.getPwd();
    }

    public StatusType loginUserStatus(String correu, String password) {
        User user = findUserByEmail(correu);
        if (user == null) {
            return StatusType.EMAIL_NONEXISTENT;
        }
        if (user.getPwd().equals(password)) {
            return StatusType.LOGIN_CORRECT;
        } else {
            return StatusType.PASSWORD_INCORRECT;
        }
    }

    public User findUserByEmail(String email) {
        ListEmployee employees = inventame.getEmployees();
        return(employees.find(email));
    }
}

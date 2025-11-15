package com.whitebox;

public class App 
{
    public static void main( String[] args )
    {
        User user = new User();

        System.out.println(user.conectarBD());
        boolean sucesso = user.verificarUsuario("Paulo", "$2a$10$.7UwBVoG8BX6.Wu7hidK6uZMoeBGQIMwH7yquHyM/6W8g3s6AqFsS");

        System.out.println(sucesso);
    }
}

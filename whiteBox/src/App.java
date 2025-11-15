import login.UserOriginal;

public class App {

    public static void main(String[] args) throws Exception {

        UserOriginal user = new UserOriginal();

        System.out.println(user.conectarBD());
        user.verificarUsuario("admin", "admin");

    }
}

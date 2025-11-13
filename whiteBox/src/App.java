import login.User;

public class App {

    public static void main(String[] args) throws Exception {

        User user = new User();

        user.verificarUsuario("admin", "admin");

    }
}

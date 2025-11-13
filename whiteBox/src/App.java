import login.User;

public class App {

    public static void main(String[] args) throws Exception {

        User user = new User();

        System.out.println(user.verificarUsuario("admin", "admin"));

    }
}

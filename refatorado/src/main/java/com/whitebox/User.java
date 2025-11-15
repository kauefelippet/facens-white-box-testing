package com.whitebox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por operações de usuário, como autenticação.
 */
public class User {

    /**
     * Conecta ao banco de dados usando JDBC.
     * 
     * @return uma {@link Connection} válida ou {@code null} se falhar.
     * @throws SQLException           se ocorrer erro ao obter a conexão.
     * @throws ClassNotFoundException se o driver JDBC não for encontrado.
     */
    public Connection conectarBD() {
        Connection conn = null;
        try {
            // Ajuste: nome do driver corrigido para MySQL Connector/J e url definida para container do projeto de StartUp
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/rodakidb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&user=user&password=1234";
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
        return conn;
    }

    /** Email do usuário autenticado */
    public String email = "";

    /** Resultado da verificação de usuário */
    public boolean result = false;

    /**
     * Verifica se existe usuário com login e senha informados.
     * 
     * @param login o nome de login do usuário.
     * @param senha a senha do usuário.
     * @return {@code true} se o usuário existir e credenciais estiverem corretas;
     *         {@code false} caso contrário ou em erro.
     */
    public boolean verificarUsuario(String login, String senha) {

        // Usa try-with-resources para garantir o fechamento dos recursos
        try (Connection conn = conectarBD()) {
            if (conn == null) {
                System.out.println("Falha ao obter conexão com o banco de dados.");
                return false;
            }

            // Alteração do nome da tabela para "users", que já existe no banco
            String sql = "SELECT email FROM users WHERE name = ? AND password_hash = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, login);
                pst.setString(2, senha);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        result = true;
                        email = rs.getString("email");
                        System.out.println("E-mail localizado: " + email);
                    } else {
                        result = false;
                        email = "";
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            result = false;
        }

        return result;
    }
}

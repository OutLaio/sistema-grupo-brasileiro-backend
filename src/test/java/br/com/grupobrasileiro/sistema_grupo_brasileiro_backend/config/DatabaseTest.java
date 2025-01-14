package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/testgbrasileiro";
        String user = "postgres";
        String password = "1234";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs1 = null;

        try {
            // Cria a conexão com o banco de dados
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida!");

            stmt = conn.createStatement();

            // Executa a consulta
            String sql = "SELECT r.* FROM \"Tb_Routes\" r " +
                         "JOIN \"Tb_BAgencyBoards\" b ON r.id_b_agency_board = b.id " +
                         "JOIN \"Tb_CompaniesCities\" cc ON r.id_company_city = cc.id " +
                         "JOIN \"Tb_Companies\" c ON cc.id_company = c.id " +
                         "JOIN \"Tb_Cities\" ci ON cc.id_city = ci.id " +
                         "WHERE c.name = 'Empresa A';";

            System.out.println("Executando consulta: " + sql);
            rs1 = stmt.executeQuery(sql);  // Executa a consulta uma única vez

            // Processa os resultados
            while (rs1.next()) {
                // Acesse as colunas pelo nome
                System.out.println("Route ID: " + rs1.getInt("id")); // Ajuste "id" para o nome correto da coluna
            }

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha os recursos
            try {
                if (rs1 != null) rs1.close();  // Fechando o ResultSet correto
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

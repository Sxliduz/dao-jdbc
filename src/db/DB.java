// METODOS PARA CONECTAR E DESCONECTAR DO BANCO DE DADOS

package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	// Metodo para conectar no banco de dados
	private static Connection conn = null;
	
	// Retorna o obj conn de cima. Metodo para conectar o banco de dados
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties(); // Codigo para conectar cm o banco
				String url = props.getProperty("dburl"); // Url do banco de dados
				conn = DriverManager.getConnection(url, props); // url do banco e as propriedades da conexao
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// Metodo para carregar e auxiliar o db.properties e guardar dentro de um obj.
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs); // Faz a leitura do fs e guarda no props
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	// Metodo auxiliar para fechar o obj do programa. Evitar de fazer muitos try catchs
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closerResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}

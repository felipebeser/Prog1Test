package swing;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Produto {
	private int id;
	private String titulo;
	private String categoria;
	private float preco;
	private int status;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean cadastrarProduto(String titulo, String categoria, float preco) {
		// Define a conexão
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "insert into product set titulo=?, categoria=?, preco=?, status=1;";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setString(1, titulo); // Substitui o primeiro parâmetro da consulta pela agência informada
			ps.setString(2, categoria); // Substitui o segundo parâmetro da consulta pela conta informada
			ps.setFloat(3, preco); // Substitui o terceiro parâmetro da consulta pelo titular informado
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar a conta: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean consultarProduto(String titulo, String categoria) {
		// Define a conexão
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select * from product where titulo=? and categoria=? and status=1";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setString(1, titulo); // Substitui o primeiro parâmetro da consulta pela agência informada
			ps.setString(2, categoria); // Substitui o segundo parâmetro da consulta pela conta informada
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se não está antes do primeiro registro
				return false; // Conta não cadastrada
			} else {
				// Efetua a leitura do registro da tabela
				while (rs.next()) {
					this.titulo = rs.getString("titulo");
					this.categoria = rs.getString("categoria");
					this.status = rs.getInt("status");
					this.preco = rs.getFloat("preco");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o produto: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean atualizarProduto(String titulo, String categoria, float preco) {
		if (!consultarProduto(titulo, categoria))
			return false;
		else {
			// Define a conexão
			Connection conexao = null;
			try {
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update product set titulo=?, categoria=? , preco=? where titulo=? and categoria=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, titulo);
				ps.setString(2, categoria);
				ps.setFloat(3, preco);
				ps.setString(4, titulo);
				ps.setString(5, categoria);
				
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar o produto: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	public boolean deletarProduto(String titulo, String categoria, float preco) {
		if (!consultarProduto(titulo, categoria))
			return false;
		else {
			// Define a conexão
			Connection conexao = null;
			try {
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update product set status=-1 where titulo=? and categoria=? and preco=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, titulo);
				ps.setString(2, categoria);
				ps.setFloat(3, preco);
				
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a exclusão!");
				else
					System.out.println("Produto excluído!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao deletar o produto: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
}


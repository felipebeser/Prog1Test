package swing;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
/**
 * Implementa a GUI via Swing, assim como o controle de botões e suas funções.
 * Utiliza objeto da classe Produto.
 * @see swing.Produto
 * @author felipebeser
 *
 */
public class TesteSwing {

	public static void main(String[] args) {
		// Define a janela
		JFrame janela = new JFrame("Cadastro de produtos"); // Janela Normal
		janela.setResizable(false); // A janela n�o poder� ter o tamanho ajustado
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(400, 300); // Define tamanho da janela
		// Define o layout da janela
		Container caixa = janela.getContentPane();
		caixa.setLayout(null);
		// Define os labels dos campos
		JLabel labelTitulo = new JLabel("Título: ");
		JLabel labelCategoria = new JLabel("Categoria: ");
		JLabel labelPreco = new JLabel("Preço: ");
		JLabel labelMensagem = new JLabel("");
		// Posiciona os labels na janela
		labelTitulo.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelCategoria.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelPreco.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		labelMensagem.setBounds(110, 160, 250, 20); // coluna, linha, largura, tamanho
		// Define os input box
		JTextField jTextTitulo = new JTextField();
		
		String[] categorias = new String[] {"Guitarra", "Baixo", "Bateria"};

		JComboBox<String> jBoxCategoria = new JComboBox<String>(categorias);
		//JTextField jTextCategoria = new JTextField();
		JTextField jTextPreco = new JTextField();
		// Define se os campos est�o habilitados ou n�o no in�cio
		jTextTitulo.setEnabled(true);
		//jTextCategoria.setEnabled(true);
		jBoxCategoria.setEnabled(true);
		jTextPreco.setEnabled(false);
		// Posiciona os input box
		jTextTitulo.setBounds(180, 40, 150, 20);
		//jTextCategoria.setBounds(180, 80, 150, 20);
		jBoxCategoria.setBounds(180, 80, 150, 20);
		jTextPreco.setBounds(180, 120, 70, 20);
		// Adiciona os r�tulos e os input box na janela
		janela.add(labelTitulo);
		janela.add(labelCategoria);
		janela.add(labelPreco);
		janela.add(labelMensagem);
		janela.add(jTextTitulo);
		//janela.add(jTextCategoria);
		janela.add(jBoxCategoria);
		janela.add(jTextPreco);
		// Define bot�es e a localiza��o deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(250, 120, 100, 20);
		janela.add(botaoConsultar);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(false);
		janela.add(botaoGravar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(250, 200, 100, 20);
		janela.add(botaoLimpar);
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(250, 230, 100, 20);
		botaoDeletar.setEnabled(false);
		janela.add(botaoDeletar);
		// Define objeto conta para pesquisar no banco de dados
		Produto produto = new Produto();
		
		// Define a��es dos bot�es
		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String titulo = jTextTitulo.getText();
					//String categoria = jTextCategoria.getText();
					String categoria = jBoxCategoria.getSelectedItem().toString();
					botaoGravar.setEnabled(true);
					Float preco;
					if (titulo.length()==0) {
						JOptionPane.showMessageDialog(janela, "Preencha o campo título");
						jTextTitulo.requestFocus();
					}
					else {
						if(!produto.consultarProduto(titulo, categoria)) {
							labelMensagem.setText("Produto não encontrado!");
							preco = 0f;
							}
						else {
							preco = produto.getPreco();
							labelMensagem.setText("Produto encontrado!");
						}
					jTextPreco.setText(String.valueOf(preco));
					jTextTitulo.setEnabled(false);
					//jTextCategoria.setEnabled(false);
					jBoxCategoria.setEnabled(false);
					botaoConsultar.setEnabled(false);
					botaoDeletar.setEnabled(true);
					jTextPreco.setEnabled(true);
					jTextPreco.requestFocus();
					}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela,
							"Preencha os campos título e categoria corretamente!");
				}
			}
		});
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = jTextTitulo.getText();
				//String categoria = jTextCategoria.getText();
				String categoria = jBoxCategoria.getSelectedItem().toString();
				float preco = Float.parseFloat(jTextPreco.getText().trim()); // Retira os espa�os em branco
				if (preco==0) {
					JOptionPane.showMessageDialog(janela, "Preencha um preço para o produto");
					jTextPreco.requestFocus();
				}
				else {
					if (!produto.consultarProduto(titulo, categoria)) {
						if (!produto.cadastrarProduto(titulo, categoria, preco))
							JOptionPane.showMessageDialog(janela, "Erro na inclusão do título!");
						else
							labelMensagem.setText("Produto cadastrado!");
					} else {
						if (!produto.atualizarProduto(titulo, categoria, preco))
							JOptionPane.showMessageDialog(janela, "Erro na atualização do titular!");
						else
							labelMensagem.setText("Produto alterado!");
					}

				}
			}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextTitulo.setText(""); // Limpar campo
				//jTextCategoria.setText(""); // Limpar campo
				jBoxCategoria.setSelectedIndex(0);
				jTextPreco.setText(""); // Limpar campo
				jTextTitulo.setEnabled(true);
				//jTextCategoria.setEnabled(true);
				jBoxCategoria.setEnabled(true);
				jTextPreco.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				botaoDeletar.setEnabled(false);
				labelMensagem.setText(null);
				jTextTitulo.requestFocus(); // Colocar o foco em um campo
			}
		});
		
		botaoDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titulo = jTextTitulo.getText();
				//String categoria = jTextCategoria.getText();
				String categoria = jBoxCategoria.getSelectedItem().toString();
				float preco = Float.parseFloat(jTextPreco.getText().trim()); // Retira os espa�os em branco
					if (!produto.consultarProduto(titulo, categoria)) {
						labelMensagem.setText("Erro, produto não encontrado!");
					} else {
						int input = JOptionPane.showConfirmDialog(janela, "Tem certeza que deseja excluir o produto?");
						if(input==0) {
							if (!produto.deletarProduto(titulo, categoria, preco))
								JOptionPane.showMessageDialog(janela, "Erro na exclusão do produto!");
							else {
								labelMensagem.setText("Produto excluído!");
								jTextPreco.setText("");
								jBoxCategoria.setSelectedIndex(0);
								jTextPreco.setText(""); // Limpar campo
								jTextTitulo.setEnabled(true);
								//jTextCategoria.setEnabled(true);
								jBoxCategoria.setEnabled(true);
								jTextPreco.setEnabled(false);
								botaoConsultar.setEnabled(true);
								botaoGravar.setEnabled(false);
								botaoDeletar.setEnabled(false);
								jTextTitulo.setText("");
								jTextTitulo.requestFocus();
							}
						}
						else
							JOptionPane.showMessageDialog(janela, "Operação cancelada.");
					}
			}
		});
		
		// Apresenta a janela
		janela.setVisible(true); // Exibe a janela
	}
}

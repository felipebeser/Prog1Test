package swing;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TesteSwing {
	public static void main(String[] args) {
		// Define a janela
		JFrame janela = new JFrame("Cadastro de conta"); // Janela Normal
		janela.setResizable(false); // A janela não poderá ter o tamanho ajustado
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(400, 300); // Define tamanho da janela
		// Define o layout da janela
		Container caixa = janela.getContentPane();
		caixa.setLayout(null);
		// Define os labels dos campos
		JLabel labelAgencia = new JLabel("Agência: ");
		JLabel labelNumero = new JLabel("Número da conta: ");
		JLabel labelTitular = new JLabel("Titular: ");
		// Posiciona os labels na janela
		labelAgencia.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelNumero.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelTitular.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		// Define os input box
		JTextField jTextAgencia = new JTextField();
		JTextField jTextNumero = new JTextField();
		JTextField jTextTitular = new JTextField();
		// Define se os campos estão habilitados ou não no início
		jTextAgencia.setEnabled(true);
		jTextNumero.setEnabled(true);
		jTextTitular.setEnabled(false);
		// Posiciona os input box
		jTextAgencia.setBounds(180, 40, 50, 20);
		jTextNumero.setBounds(180, 80, 50, 20);
		jTextTitular.setBounds(180, 120, 150, 20);
		// Adiciona os rótulos e os input box na janela
		janela.add(labelAgencia);
		janela.add(labelNumero);
		janela.add(labelTitular);
		janela.add(jTextAgencia);
		janela.add(jTextNumero);
		janela.add(jTextTitular);
		// Define botões e a localização deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 80, 100, 20);
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
		janela.add(botaoDeletar);
		// Define objeto conta para pesquisar no banco de dados
		ContaCorrenteNormal conta = new ContaCorrenteNormal();
		// Define ações dos botões
		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int agencia = Integer.parseInt(jTextAgencia.getText());
					int numero = Integer.parseInt(jTextNumero.getText());
					botaoGravar.setEnabled(true);
					String titular;
					if (!conta.consultarConta(agencia, numero))
						titular = "";
					else
						titular = conta.getTitular();
					jTextTitular.setText(titular);
					jTextAgencia.setEnabled(false);
					jTextNumero.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextTitular.setEnabled(true);
					jTextTitular.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela,
							"Preencha os campos agência e número da conta corretamente!!");
				}
			}
		});
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int agencia = Integer.parseInt(jTextAgencia.getText());
				int numero = Integer.parseInt(jTextNumero.getText());
				String titular = jTextTitular.getText().trim(); // Retira os espaços em branco
				if (titular.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo titular");
					jTextTitular.requestFocus();
				}
				else {
					if (!conta.consultarConta(agencia, numero)) {
						if (!conta.cadastrarConta(agencia, numero, titular))
							JOptionPane.showMessageDialog(janela, "Erro na inclusão do titular!");
						else
							JOptionPane.showMessageDialog(janela, "Inclusão realizada!");
					} else {
						if (!conta.atualizarConta(agencia, numero, titular))
							JOptionPane.showMessageDialog(janela, "Erro na atualização do titular!");
						else
							JOptionPane.showMessageDialog(janela, "Alteração realizada!");
					}

				}
			}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextAgencia.setText(""); // Limpar campo
				jTextNumero.setText(""); // Limpar campo
				jTextTitular.setText(""); // Limpar campo
				jTextAgencia.setEnabled(true);
				jTextNumero.setEnabled(true);
				jTextTitular.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				jTextAgencia.requestFocus(); // Colocar o foco em um campo
			}
		});
		// Apresenta a janela
		janela.setVisible(true); // Exibe a janela
	}
}

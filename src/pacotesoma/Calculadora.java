package pacotesoma;
import javax.swing.*;
import java.awt.*;

public class Calculadora {
    private final JTextField visor;
    private double numeroAnterior;
    private String operacaoPendente;
    private boolean limparVisor;

    public Calculadora() {
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        visor = new JTextField(10);
        visor.setFont(new Font("Arial", Font.PLAIN, 24));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(false);

        frame.add(visor, BorderLayout.NORTH);
        frame.add(criarPainelBotoes(), BorderLayout.CENTER);

        // Inicialize operacaoPendente como vazia
        operacaoPendente = "";

        frame.setVisible(true);
    }


    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 5, 5));

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String textoBotao : botoes) {
            JButton botao = new JButton(textoBotao);
            botao.setFont(new Font("Arial", Font.PLAIN, 18));
            botao.addActionListener(e -> botaoClicado(textoBotao));
            painelBotoes.add(botao);
        }

        return painelBotoes;
    }

    private void botaoClicado(String textoBotao) {
        if (textoBotao.matches("[0-9]")) {
            if (limparVisor) {
                visor.setText("");
                limparVisor = false;
            }
            visor.setText(visor.getText() + textoBotao);
        } else if (textoBotao.equals("C")) {
            visor.setText("");
        } else if (textoBotao.matches("[/+\\-*=]")) {
            calcular();
            operacaoPendente = textoBotao;
            numeroAnterior = Double.parseDouble(visor.getText());
            limparVisor = true;
        } else if (textoBotao.equals("=")) {
            calcular();
            operacaoPendente = "";
            limparVisor = true;
        }
    }

    private void calcular() {
        if (!operacaoPendente.isEmpty()) {
            double numeroAtual = Double.parseDouble(visor.getText());
            switch (operacaoPendente) {
                case "+" -> numeroAnterior += numeroAtual;
                case "-" -> numeroAnterior -= numeroAtual;
                case "*" -> numeroAnterior *= numeroAtual;
                case "/" -> {
                    if (numeroAtual != 0) {
                        numeroAnterior /= numeroAtual;
                    } else {
                        visor.setText("Erro");
                        return;
                    }
                }
            }
            visor.setText(String.valueOf(numeroAnterior));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculadora::new);
    }
}

package br.uninove.poo.eventos;

//Importações necessárias
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

//Como temos eventos, a classe herda de JFrame e implementa a ActionListener
public class Aplicativo extends JFrame implements ActionListener {
    
    //Atributos locais da classe:
    private JPanel pnlCampos, pnlTabela, pnlBotoes, pnlNome, pnlTelefone, pnlCidade;
    private TitledBorder tituloPnlCampos, tituloPnlTabela, tituloPnlBotoes;
    private JLabel lblNome, lblTelefone, lblCidade;
    private JTextField txtNome;
    private JFormattedTextField txtTelefone;
    private MaskFormatter mskTelefone;
    private JTable tblClientes;
    private DefaultTableModel tblClientesModel;
    JButton btnAdicionar, btnLimpar;
    private JComboBox cmbCidades;
    private DefaultComboBoxModel cmbCidadesModel;
    
    
    //Construtor que coloca tudo no lugar
    public Aplicativo() {
        definirJanela();     //Chama o método que seta as propriedades da janela
        pnlCampos = new JPanel(new GridLayout(3, 1));     //Um painel para os campos
        tituloPnlCampos = BorderFactory.createTitledBorder("Cadastro de Clientes");
        pnlCampos.setBorder(tituloPnlCampos);
        
        
        //Painel para os botôes:
        pnlBotoes = new JPanel(new FlowLayout());
        tituloPnlBotoes = BorderFactory.createTitledBorder("Botões");
        pnlBotoes.setBorder(tituloPnlBotoes);
        
        
        //painel para a tabela
        pnlTabela = new JPanel(new GridLayout(1, 1));
        tituloPnlTabela = BorderFactory.createTitledBorder("Tabela de Dados");
        pnlTabela.setBorder(tituloPnlTabela);
        
        
        //Seta os labels
        lblNome = new JLabel("Nome");
        lblTelefone = new JLabel("Telefone");
        lblCidade = new JLabel("Cidade");
        txtNome = new JTextField(50);
        
        
        //Cria um campo de texto com uma máscara para o telefone
        try {
            mskTelefone = new MaskFormatter("(##)####-####");
            mskTelefone.setPlaceholderCharacter('_');
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Máscara incorreta");
        }
        txtTelefone = new JFormattedTextField(mskTelefone);
        cmbCidadesModel = new DefaultComboBoxModel();
        cmbCidades = new JComboBox(cmbCidadesModel);
        
        
        //Cidades para o combo de seleção
        String cidade1 = "São Paulo";
        String cidade2 = "Rio de Janeiro";
        String cidade3 = "Campinas";
        
        
        //Acrescenta as cidades
        cmbCidadesModel.addElement(cidade1);
        cmbCidadesModel.addElement(cidade2);
        cmbCidadesModel.addElement(cidade3);
        pnlNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlNome.add(lblNome);
        pnlNome.add(txtNome);
        pnlCampos.add(pnlNome);
        
        pnlTelefone = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTelefone.add(lblTelefone);
        pnlTelefone.add(txtTelefone);
        pnlCampos.add(pnlTelefone);
        
        pnlCidade = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCidade.add(lblCidade);
        pnlCidade.add(cmbCidades);
        pnlCampos.add(pnlCidade);
        
        
        //Seta os botôes
        btnAdicionar = new JButton("Adicionar à tabela");
        btnAdicionar.addActionListener(this);      //Adiciona o Listener ao botão
        btnLimpar = new JButton("Limpar campos");
        btnLimpar.addActionListener(this);        //Adiciona o Listener ao botão
        pnlBotoes.add(btnAdicionar);
        pnlBotoes.add(btnLimpar);
        
        
        //Define a tabela
        String[] cols = {"Nome", "Telefone", "Cidade"};
        tblClientesModel = new DefaultTableModel(cols, 3);
        tblClientes = new JTable(tblClientesModel);
        tblClientesModel.setNumRows(0);
        
        JScrollPane scrRolagem = new JScrollPane(tblClientes,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnlTabela.add(scrRolagem);
        
        add(pnlCampos, BorderLayout.NORTH);
        add(pnlBotoes, BorderLayout.CENTER);
        add(pnlTabela, BorderLayout.SOUTH);                 
        
    }
    
    //Método para setar algumas propriedades da janela
    private void definirJanela() {
        setTitle("Aplicação de cadastro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 700);
        setLocation(200, 80);
    }
    
    //Método main
    public static void main(String[] args) {
        Aplicativo ap = new Aplicativo();
        ap.setVisible(true);  
    }
    
    //Sobrescrita obrigatória da interface ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /*
        A ideia é assim: é um lestener para todos os botões.
        O Listener deve identificar qual botão foi clicado e disparar
        uma ação específica para cada um
        */
        
        if (e.getSource() == btnAdicionar) {     //Para o botão adicionar
            String[] linha = {txtNome.getText(),
                txtTelefone.getText(),
                cmbCidadesModel.getSelectedItem().toString()};
            tblClientesModel.addRow(linha);
            JOptionPane.showMessageDialog(this, "Dados adicionados", "Feito", JOptionPane.PLAIN_MESSAGE);
            
            } else if (e.getSource() == btnLimpar) {     //Para o botão limpar
                int op = JOptionPane.showConfirmDialog(this, "Quer, realmente, limpar os campos?", "Limpar",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (op == 0) {
                    txtNome.setText(null);
                    txtTelefone.setText(null);
                    cmbCidadesModel.setSelectedItem("São Paulo");
                    txtNome.requestFocus();    //Coloca o cursor no txtNome
                }
        }
    }
    
}

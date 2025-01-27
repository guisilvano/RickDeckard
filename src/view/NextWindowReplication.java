package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import database.TableReplicationDirectionDAO;
import database.TableReplicationProcessDAO;
import database.ConectionsReplicationDAO;
import database.ConnectionFactory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class NextWindowReplication<E> extends JInternalFrame implements NextWindowController {

	private JLabel origemLbl;
	private JLabel destinoLbl;
	private JLabel pointerLbl;
	private JTextField txfProcesso;
	private JTextField txfTabela;
	private JTextField txfTabelas;
	private JTextField txfErros;
	private JComboBox<E> processCmb;
	private JComboBox<E> directionCmb;
	private JProgressBar pbbPrincipal;
	private JProgressBar pbbIndeterminada;
	private JButton btnReplicar;

	public static void main(String[] args) throws SQLException {
		NextWindowReplication np = new NextWindowReplication();
		np.setVisible(true);

	}

	public NextWindowReplication() throws SQLException {
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 327, 141);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);

		// Define o tamanho da janela.
		setSize(382, 345);

		// Define o titulo da janela.
		setTitle("Replica\u00E7\u00E3o");

		// Define que não poderá ser alterado as dimensões da tela.
		setResizable(true);

		// Define o método de fechamento da janela.
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBackground(Color.WHITE);

		setLocation(1, 1);
		getContentPane().setLayout(null);
		// Cria os componentes.

		btnReplicar = new JButton(new AbstractAction("REPLICAR") {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (btnReplicar.getText().equals("REPLICAR")) {

					NextReplicationExecute replication = null;
					try {
						replication = new NextReplicationExecute(NextWindowReplication.this);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					replication.ReplicacaoIniciar();

				}

			}

		});
		btnReplicar.setBounds(10, 264, 120, 31);
		btnReplicar.setBackground(SystemColor.menu);
		getContentPane().add(btnReplicar);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(140, 265, 216, 31);
		progressBar.setStringPainted(true);
		getContentPane().add(progressBar);

		Connection conn = ConnectionFactory.getConnection("masterReplicator", "admin", "admin");

		JLabel lblProcesso = new JLabel("Processo:");
		lblProcesso.setBounds(10, 11, 96, 17);
		lblProcesso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(lblProcesso);

		processCmb = new JComboBox();
		processCmb.setBounds(82, 11, 120, 20);
		processCmb.setModel(new DefaultComboBoxModel(loadProcessComboBox()));
		getContentPane().add(processCmb);

		JLabel lblDireo = new JLabel("Dire\u00E7\u00E3o:");
		lblDireo.setBounds(10, 39, 96, 17);
		lblDireo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(lblDireo);

		directionCmb = new JComboBox();
		directionCmb.setBounds(82, 39, 120, 20);
		directionCmb.setModel(new DefaultComboBoxModel(loadDirectionsComboBox()));
		getContentPane().add(directionCmb);

		origemLbl = new JLabel("");
		origemLbl.setBounds(212, 42, 64, 14);
		getContentPane().add(origemLbl);

		destinoLbl = new JLabel("");
		destinoLbl.setBounds(292, 42, 64, 14);
		getContentPane().add(destinoLbl);

		pointerLbl = new JLabel("->");
		pointerLbl.setBounds(273, 41, 24, 17);
		getContentPane().add(pointerLbl);

	}



	String[] loadDirectionsComboBox() throws SQLException {

		Connection conn = ConnectionFactory.getConnection("masterReplicator", "admin", "admin");

		TableReplicationDirectionDAO dao = new TableReplicationDirectionDAO(conn);
		String[] Directions = dao.selectDirectionNames(processCmb.getSelectedItem().toString());

		return Directions;

	}

	String[] loadProcessComboBox() throws SQLException {

		Connection conn = ConnectionFactory.getConnection("masterReplicator", "admin", "admin");

		TableReplicationProcessDAO dao = new TableReplicationProcessDAO(conn);
		String[] Process = dao.selectProcessNames();

		return Process;

	}

	public void UpdateFields() throws SQLException {

		Connection conn = ConnectionFactory.getConnection("masterReplicator", "admin", "admin");

		TableReplicationDirectionDAO directionDAO = new TableReplicationDirectionDAO(conn);

		origemLbl.setText(directionDAO.selectOrigem(directionCmb.getSelectedItem().toString()));
		destinoLbl.setText(directionDAO.selectDestino(directionCmb.getSelectedItem().toString()));

	}


	public String getDirection() {
		
		return directionCmb.getSelectedItem().toString();
		
	}
	
	public String getProcess() {
		
		return processCmb.getSelectedItem().toString();
		
	}
	
	@Override
	public void DirecaoExibir(String direcaoOrigem, String direcaoDestino) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ProcessoExibir(String processo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void TabelaExibir(String tabela) {
		// TODO Auto-generated method stub

	}

	@Override
	public void TabelasExibir(int quantidade) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ErrosExibir(int quantidade) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BarraProgressoIndeterminadaIniciar(boolean start) {
		// TODO Auto-generated method stub

	}

	@Override
	public void BarraProgressoValor(int valor) {
		// TODO Auto-generated method stub

	}
}

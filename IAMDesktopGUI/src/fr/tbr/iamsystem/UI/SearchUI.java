package fr.tbr.iamsystem.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.tbr.iamcore.Luncher.IamManager;
import fr.tbr.iamcore.datamodel.Identity;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class SearchUI extends JDialog implements	ListSelectionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldDP;
	private JTextField textFieldEM;
	private JTable table;
	private Object[] tableColumnNames = {"UID", "Display Name", "Email", "BirthDate"};
	private JTextField textField_DP;
	private JTextField textField_EM;
	private JTextField textField_DB;
	private JTextField textField_UID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SearchUI dialog = new SearchUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SearchUI() {
		setTitle("Identity Search");
		setBounds(100, 100, 767, 618);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("Display Name");
		
		textFieldDP = new JTextField();
		textFieldDP.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		
		textFieldEM = new JTextField();
		textFieldEM.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Identity identity = new Identity();
				identity.setDisplayName(textFieldDP.getText());
				identity.setEmail(textFieldEM.getText());
				
				List<Identity> searchResult = IamManager.getInstance().SearchUser(identity);
				for(Identity iden:searchResult)
				{
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					String date= df.format(iden.getBirthDate());
					Object[] row = { iden.getUid(), iden.getDisplayName(), iden.getEmail(), date };

				    DefaultTableModel model = (DefaultTableModel) table.getModel();

				    model.addRow(row);
				}
				
			}
		//TableAccessibleContext.
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Identity identity = new Identity();
				identity.setDisplayName(textField_DP.getText());
				identity.setEmail(textField_EM.getText());
				identity.setUid(textField_UID.getText());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date parsedDate = null;
				try {
					parsedDate = simpleDateFormat.parse(textField_DB.getText());
					identity.setBirthDate(parsedDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				identity.setBirthDate(parsedDate);
				IamManager.getInstance().Update(identity);
				
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Identity identity = new Identity();
				identity.setDisplayName(textField_DP.getText());
				identity.setEmail(textField_EM.getText());
				identity.setUid(textField_UID.getText());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date parsedDate = null;
				try {
					parsedDate = simpleDateFormat.parse(textField_DB.getText());
					identity.setBirthDate(parsedDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				identity.setBirthDate(parsedDate);
				IamManager.getInstance().Delete(identity);
				
			}
		});
		
		JLabel lblDisplayName = new JLabel("Display Name");
		
		textField_DP = new JTextField();
		textField_DP.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		
		textField_EM = new JTextField();
		textField_EM.setColumns(10);
		
		JLabel lblDatebirth = new JLabel("DateBirth");
		
		textField_DB = new JTextField();
		textField_DB.setColumns(10);
		
		JLabel lblUid = new JLabel("UID");
		
		textField_UID = new JTextField();
		textField_UID.setEditable(false);
		textField_UID.setColumns(10);
		
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(165)
							.addComponent(btnSubmit))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(72)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1))
							.addGap(42)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldEM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldDP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(172)
							.addComponent(btnModify)
							.addGap(179)
							.addComponent(btnDelete))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 688, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDisplayName)
								.addComponent(lblDatebirth))
							.addGap(29)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_DP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_DB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(101)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEmail)
								.addComponent(lblUid))
							.addGap(33)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_UID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_EM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textFieldDP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(textFieldEM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnSubmit)
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDisplayName)
						.addComponent(textField_DP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail)
						.addComponent(textField_EM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDatebirth)
						.addComponent(textField_DB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUid)
						.addComponent(textField_UID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnModify)
						.addComponent(btnDelete))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},tableColumnNames) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		
		
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener( this );
		table.setEnabled(true);
		//table.setRowSelectionAllowed(true);
		//table.setRowSelectionInterval(0, 1);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setColumnSelectionAllowed(columnSelectionAllowed);
		scrollPane.setViewportView(table);
		contentPanel.setLayout(gl_contentPanel);
	}
	// Handler for list selection changes
	 	public void valueChanged( ListSelectionEvent event )
	 	{
	 		// See if this is a valid table selection
			if( event.getSource() == table.getSelectionModel()
							&& event.getFirstIndex() >= 0 )
			{
				// Get the data model for this table
				TableModel model = (TableModel)table.getModel();

				// Determine the selected item
				String string = (String)model.getValueAt(
										table.getSelectedRow(),
										1 );

				// Display the selected item
				textField_DP.setText(string);
				string = (String)model.getValueAt(
						table.getSelectedRow(),
						2 );
				textField_EM.setText(string);
				string = (String)model.getValueAt(
						table.getSelectedRow(),
						0 );
				textField_UID.setText(string);
				string = (String)model.getValueAt(
						table.getSelectedRow(),
						3 );
				textField_DB.setText(string);


			}
	 	}
	
}

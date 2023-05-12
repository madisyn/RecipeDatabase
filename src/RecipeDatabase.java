import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;


public class RecipeDatabase {

	private JFrame frame;
	private JTextField txtdname;
	private JTextField txtmtype;
	private JTextField txtdiff;
	private JTable table;
	private JTextField txtDishID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeDatabase window = new RecipeDatabase();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecipeDatabase() {
		initialize();
		Connect();
	}

	Connection con;
	PreparedStatement pst;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/recipedatabase", "root", "");
			JOptionPane.showMessageDialog(null, "Connection yay!"); 
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 709, 432);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Recipe Directory");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(257, 11, 258, 58);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 80, 316, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel labelDishName = new JLabel("Dish Name");
		labelDishName.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelDishName.setBounds(30, 29, 100, 27);
		panel.add(labelDishName);
		
		JLabel labelMealType = new JLabel("Meal type");
		labelMealType.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelMealType.setBounds(30, 83, 100, 27);
		panel.add(labelMealType);
		
		JLabel labelDiff = new JLabel("Difficulty");
		labelDiff.setFont(new Font("Tahoma", Font.BOLD, 14));
		labelDiff.setBounds(30, 136, 100, 27);
		panel.add(labelDiff);
		
		txtdname = new JTextField();
		txtdname.setBounds(140, 34, 136, 20);
		panel.add(txtdname);
		txtdname.setColumns(10);
		
		txtmtype = new JTextField();
		txtmtype.setColumns(10);
		txtmtype.setBounds(140, 88, 136, 20);
		panel.add(txtmtype);
		
		txtdiff = new JTextField();
		txtdiff.setColumns(10);
		txtdiff.setBounds(140, 141, 136, 20);
		panel.add(txtdiff);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dname, mtype, dif;
				
				dname = txtdname.getText(); 
				mtype = txtmtype.getText();
				dif = txtdiff.getText();
				
				try {
					pst = con.prepareStatement("insert into recipes(name, ype, difficulty)values(?, ?, ?)" );
					pst.setString(1, dname);
					pst.setString(2, mtype);
					pst.setString(3,  dif);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					// table_load();
					txtdname.setText("");
					txtmtype.setText("");
					txtdiff.setText("");
					txtdname.requestFocus();
				}
				catch(SQLException el){
					el.printStackTrace();
				}

				
			}
		});
		btnSave.setBounds(20, 287, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(119, 287, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.setBounds(218, 287, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 80, 326, 230);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 321, 316, 61);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel labelDishID = new JLabel("Dish ID");
		labelDishID.setBounds(10, 21, 66, 17);
		labelDishID.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(labelDishID);
		
		txtDishID = new JTextField();
		txtDishID.setBounds(86, 21, 144, 20);
		txtDishID.setColumns(10);
		panel_1.add(txtDishID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(412, 340, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(525, 340, 89, 23);
		frame.getContentPane().add(btnDelete);
	}
}





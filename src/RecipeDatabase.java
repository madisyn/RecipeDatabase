import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


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
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rd;
	DefaultTableModel model;
	
	//private JTextField txtID;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/recipedatabase", "root", "");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

	public void table_load()
	    {
	     try
	     {
	    pst = con.prepareStatement("select * from recipes");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	}
	     catch (SQLException e)
	     {
	     e.printStackTrace();
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
				String dname, mtype;
				int dif;
				
				dname = txtdname.getText(); 
				mtype = txtmtype.getText();
				dif = Integer.parseInt(txtdiff.getText());
				
				try {
					pst = con.prepareStatement("insert into recipes(name, type, difficulty)values(?, ?, ?)" );
					pst.setString(1, dname);
					pst.setString(2, mtype);
					pst.setInt(3,  dif);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
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
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtdname.setText("");
				txtmtype.setText("");
				txtdiff.setText("");
				txtdname.requestFocus();
			}
		});
		btnClear.setBounds(119, 287, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
		txtDishID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = txtDishID.getText();
		 
		                pst = con.prepareStatement("select name,type,difficulty from recipes where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		           if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String type = rs.getString(2);
		                String difficulty = rs.getString(3);
		                
		                txtdname.setText(name);
		                txtmtype.setText(type);
		                txtdiff.setText(difficulty);
		                
		                
		            }  
		            else
		            {
		             txtdname.setText("");
		             txtmtype.setText("");
		             txtdiff.setText("");
		            }

		        }
			catch (SQLException ex) {
			          
			        }
		}

		});
		txtDishID.setBounds(86, 21, 144, 20);
		txtDishID.setColumns(10);
		panel_1.add(txtDishID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String dname, mtype;
				int dif, id;
				
				id = Integer.parseInt(txtDishID.getText());
				dname = txtdname.getText(); 
				mtype = txtmtype.getText();
				dif = Integer.parseInt(txtdiff.getText());
				
				try {
					pst = con.prepareStatement("update recipes set name  =? , type =?, difficulty =? where id = ?");
					pst.setString(1, dname);
					pst.setString(2, mtype);
					pst.setInt(3,  dif);
					pst.setInt(4,  id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Updated!");
					table_load();
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
		btnUpdate.setBounds(412, 340, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id;
				
				id = Integer.parseInt(txtDishID.getText());

				
				try {
					pst = con.prepareStatement("delete from recipes where id =?");
					pst.setInt(1,  id);
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					table_load();
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
		btnDelete.setBounds(525, 340, 89, 23);
		frame.getContentPane().add(btnDelete);
	}
}





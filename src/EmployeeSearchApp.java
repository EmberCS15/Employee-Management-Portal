import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.event.*;
import java.util.*;

public class EmployeeSearchApp extends JFrame {
	private JPanel contentPane;
	private JTextField lastNameTextField;
	private JTable table;
	private EmployeeDAO dao;
	
	public static String userName;
	JLabel userLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchApp frame = new EmployeeSearchApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeSearchApp(String userName){
		this();
		this.userName=userName;
		userLabel.setText("Logged in as "+userName+"    ");
		//System.out.println(userName+" "+this.userName);
	}
	public EmployeeSearchApp() {
		try{
			dao=new EmployeeDAO();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,"Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
		}
		setTitle("Employee Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("Search App");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		userLabel = new JLabel("Logged in As "+userName);
		panel.add(userLabel);

		JLabel lblEnter = new JLabel("Enter Last Name");
		panel.add(lblEnter);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(30);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String lastName=lastNameTextField.getText();
					List<Employee> employees=null;
					if(lastName!=null&&lastName.trim().length()>0)
						employees=dao.searchEmployee(lastName);
					else employees=dao.getAllEmployees();
					/*for(Employee emp:employees){
						System.out.println(emp);
					}*/
					EmployeeTableModel tableModel=new EmployeeTableModel(employees);
					table.setModel(tableModel);
				}catch(Exception exc){
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Error:"+exc, "Error", JOptionPane.ERROR_MESSAGE);;
				}
			}
		});
		panel.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Add Employee");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEmployeeDialog dialog=new AddEmployeeDialog(EmployeeSearchApp.this,dao);
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnNewButton);
		
		JButton btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(row<0){
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Select an Employee","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Employee tempEmp=(Employee)table.getValueAt(row,EmployeeTableModel.OBJECT_COL);
				AddEmployeeDialog dialog=new AddEmployeeDialog(EmployeeSearchApp.this,dao,tempEmp,true);
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("Delete Employee");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(row<0){
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "Select an Employee","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Employee tempEmp=(Employee)table.getValueAt(row,EmployeeTableModel.OBJECT_COL);
				dao.deleteEmployee(tempEmp.getID());
				refreshEmployeesView();
			}
		});
		panel_1.add(btnDeleteEmployee);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		panel_1.add(btnExit);
	}

	public void refreshEmployeesView() {
		// TODO Auto-generated method stub
		try{
			List<Employee> ls=dao.getAllEmployees();
			EmployeeTableModel tableM=new EmployeeTableModel(ls);
			table.setModel(tableM);
		}catch(Exception exc){
			JOptionPane.showMessageDialog(this,"Error"+exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}

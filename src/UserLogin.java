import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.Statement;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;

public class UserLogin extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameTextField;
	private JPasswordField passwordField;
	private JTextField userNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
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
	public UserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
	
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StrongPasswordEncryptor encryptor=new StrongPasswordEncryptor();
				String encryptedPassword="";
				String inputPassword=new String(passwordField.getPassword());
				PreparedStatement pstmt=null;
				Connection myCon=null;
				try{
					myCon=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","rambo123");
					pstmt=myCon.prepareStatement("select * from users where user_mail=?");
					pstmt.setString(1, userNameTextField.getText());
					ResultSet rs=pstmt.executeQuery();
					while(rs.next()){
						encryptedPassword=rs.getString("password");
					}
					if(encryptor.checkPassword(inputPassword, encryptedPassword)){
						EmployeeSearchApp employeeSearchApp=new EmployeeSearchApp(firstNameTextField.getText());
						setVisible(false);
						dispose();
						employeeSearchApp.setVisible(true);
					}
					else JOptionPane.showMessageDialog(UserLogin.this, "Check UserName or Password","Error",JOptionPane.ERROR_MESSAGE);;
				}catch(Exception exc){
					exc.printStackTrace();
				}finally{  
					try{
						if(myCon!=null){
							myCon.close();
						}
						if(pstmt!=null){
							pstmt.close();
						}
					}catch(Exception exc){
						System.out.println("Login Portal Connection Error");
						exc.printStackTrace();
					}
				}
			}
		});
		panel.add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterPortal rp=new RegisterPortal();
				rp.setVisible(true);
			}
		});
		panel.add(btnRegister);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblUserName = new JLabel("FIRST NAME");
		panel_1.add(lblUserName, "8, 6, right, default");
		
		firstNameTextField = new JTextField();
		panel_1.add(firstNameTextField, "10, 6, right, center");
		firstNameTextField.setColumns(35);
		
		JLabel lblUsername = new JLabel("USERNAME");
		panel_1.add(lblUsername, "8, 10, right, default");
		
		userNameTextField = new JTextField();
		panel_1.add(userNameTextField, "10, 10, fill, default");
		userNameTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		panel_1.add(lblPassword, "8, 14, right, default");
		
		passwordField = new JPasswordField();
		passwordField.setColumns(35);
		panel_1.add(passwordField, "10, 14, left, default");
	}
}

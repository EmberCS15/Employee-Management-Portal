import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JOptionPane;

public class RegisterPortal extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameText;
	private JTextField lastNameText;
	private JTextField emailText;
	private JTextField passwordText;
	private JTextField confirmText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPortal frame = new RegisterPortal();
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
	public RegisterPortal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblFirstName = new JLabel("First Name");
		panel.add(lblFirstName, "6, 6, right, default");
		
		firstNameText = new JTextField();
		panel.add(firstNameText, "8, 6, fill, default");
		firstNameText.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		panel.add(lblLastName, "6, 8, right, default");
		
		lastNameText = new JTextField();
		panel.add(lastNameText, "8, 8, fill, default");
		lastNameText.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		panel.add(lblEmail, "6, 10, right, default");
		
		emailText = new JTextField();
		panel.add(emailText, "8, 10, fill, default");
		emailText.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "6, 12, right, default");
		
		passwordText = new JTextField();
		panel.add(passwordText, "8, 12, fill, default");
		passwordText.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		panel.add(lblConfirmPassword, "6, 14, right, default");
		
		confirmText = new JTextField();
		panel.add(confirmText, "8, 14, fill, default");
		confirmText.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(passwordText.getText().equals(confirmText.getText())){
					StrongPasswordEncryptor passwordEncryptor=new StrongPasswordEncryptor();
					String encryptedPassword=passwordEncryptor.encryptPassword(passwordText.getText());
					String usermail="";
					usermail=firstNameText.getText().toLowerCase()+".cs15";
					Connection myCon=null;
					PreparedStatement pstmt=null;
					try{
						myCon=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","rambo123");
						pstmt=myCon.prepareStatement("insert into users(last_name,first_name,email,password,user_mail)values(?,?,?,?,?)");
						pstmt.setString(1,lastNameText.getText());
						pstmt.setString(2, firstNameText.getText());
						pstmt.setString(3,emailText.getText());
						pstmt.setString(4, encryptedPassword);
						pstmt.setString(5, usermail);
						pstmt.executeUpdate();
						setVisible(false);
						dispose();
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
							exc.printStackTrace();
							System.out.println("Error while Closing Connection");
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(RegisterPortal.this, "Password Fields Dont Match", "Information", JOptionPane.INFORMATION_MESSAGE);;
				}
			}
		});
		panel.add(btnRegister, "8, 18");
	}

}

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
public class AddEmployeeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private EmployeeDAO employeeDAO;
	private EmployeeSearchApp employeeSearchApp;
	private boolean updateMode=false;
	private Employee prevEmp;
	/**
	 * Launch the application.
	 */
	public AddEmployeeDialog(EmployeeSearchApp theEmployeeSearchApp,EmployeeDAO theEmployeeDAO,Employee theEmployee,boolean update){
		this();
		employeeDAO=theEmployeeDAO;
		employeeSearchApp=theEmployeeSearchApp;
		prevEmp=theEmployee;
		if(update){
			updateMode=update;
			setTitle("Update Employee");
			populateGUI(prevEmp);
		}
	}
	public AddEmployeeDialog(EmployeeSearchApp theEmployeeSearchApp,EmployeeDAO theEmployeeDAO){
		this();
		employeeDAO = theEmployeeDAO;
		employeeSearchApp = theEmployeeSearchApp;
	}
	/**
	 * Create the dialog.
	 */
	public AddEmployeeDialog() {
		setTitle("Add Employee");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "4, 4, right, default");
		}
		{
			textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(textField, "6, 4, fill, default");
			textField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "4, 8, right, default");
		}
		{
			textField_1 = new JTextField();
			textField_1.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(textField_1, "6, 8, fill, default");
			textField_1.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Email");
			contentPanel.add(lblNewLabel, "4, 12, right, center");
		}
		{
			textField_2 = new JTextField();
			textField_2.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(textField_2, "6, 12, fill, default");
			textField_2.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Salary");
			contentPanel.add(lblNewLabel_1, "4, 16, right, default");
		}
		{
			textField_3 = new JTextField();
			textField_3.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(textField_3, "6, 16, fill, default");
			textField_3.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("SAVE");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveEmployee();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void populateGUI(Employee prevEmp){
		try{
			//EmployeeTableModel model=new EmployeeTableModel(prevEmp);
			this.textField.setText(prevEmp.getFirstName());
			this.textField_1.setText(prevEmp.getLastName());
			this.textField_2.setText(prevEmp.getEmail());
			this.textField_3.setText(prevEmp.getSalary().toString());
		}catch(Exception exc){
			JOptionPane.showMessageDialog(employeeSearchApp, "Error"+exc,"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	private BigDecimal convertStringToBigDecimal(String salary){
		BigDecimal result=null;
		try{
			double sal=Double.parseDouble(salary);
			result=BigDecimal.valueOf(sal);
		}catch(Exception e){
			result=BigDecimal.valueOf(0.0);
		}
		return result;
	}
	protected void saveEmployee(){
		String firstName = textField.getText();
		String lastName = textField_1.getText();
		//System.out.println(lastName);
		String emailName = textField_2.getText();
		String salaryName= textField_3.getText();
		BigDecimal sl=convertStringToBigDecimal(salaryName);
		Employee theEmployee=null;
		Employee tempEmp=null;
		if(updateMode){
			tempEmp=prevEmp;
			tempEmp.setLastName(lastName);
			tempEmp.setFirstName(firstName);
			tempEmp.setEmail(emailName);
			tempEmp.setSalary(sl);
		}
		else{
		theEmployee = new Employee(0,lastName,firstName,emailName,sl);
		}
		try{
			if(updateMode){
				employeeDAO.updateEmployee(tempEmp);
			}else{
				employeeDAO.addEmployee(theEmployee);
			}
			//employeeDAO.addEmployee(theEmployee);
			setVisible(false);
			dispose();
			employeeSearchApp.refreshEmployeesView();
			JOptionPane.showMessageDialog(employeeSearchApp,"Employee Addded Successfully","Employee Added",JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception exc){
			JOptionPane.showMessageDialog(employeeSearchApp,"Error Saving File"+exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}

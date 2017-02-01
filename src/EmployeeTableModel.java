import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
public class EmployeeTableModel extends AbstractTableModel{
	public static final int OBJECT_COL=-1;
	private static final int ID_COL=0; 
	private static  final int LAST_NAME_COL=1;
	private static final int FIRST_NAME_COL=2;
	private static final int SALARY_COL=4;
	private static final int EMAIL_COL=3;
	private String columnNames[]={"ID","Last Name","First Name","Email","Salary"};
	private List<Employee> employ;
	public EmployeeTableModel(List<Employee> theEmployee){
		employ=theEmployee;
	}
	@Override
	public int getColumnCount(){
		return columnNames.length; 	
	}
	
	@Override
	public int getRowCount(){
		return employ.size();
	}
	
	@Override
	public String getColumnName(int row){
		return columnNames[row];
	}
	@Override
	public Class<?> getColumnClass(int col){
		return getValueAt(0,col).getClass();
	}
	public Object getValueAt(int row,int col){
		Employee temp=employ.get(row);
		switch(col){
			case ID_COL:return temp.getID();
			case LAST_NAME_COL:return temp.getLastName();
			case FIRST_NAME_COL:return temp.getFirstName();
			case EMAIL_COL:return temp.getEmail();
			case SALARY_COL:return temp.getSalary();
			case OBJECT_COL:return temp;
			default:return temp.getFirstName();	
		}
	}
}
 		
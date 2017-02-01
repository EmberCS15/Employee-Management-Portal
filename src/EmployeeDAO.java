import java.sql.*;
import java.io.*;
import java.util.Properties;
import java.util.*;
public class EmployeeDAO {
	private Connection myCon=null;
	public EmployeeDAO() throws Exception{
		Properties prop=new Properties();
		prop.load(new FileInputStream("E://Sem4//deatils.properties"));
		String user=prop.getProperty("user");
		String dbName=prop.getProperty("dbName");
		String password=prop.getProperty("password");
		System.out.println(dbName+" "+user+" "+password);
		try{
		myCon=DriverManager.getConnection(dbName, user, password);
		System.out.println("Connection Established");
		}catch(Exception exc){}
		System.out.println("The Connection has not been Established");
	}
	public List<Employee> getAllEmployees() throws Exception{
		List<Employee> emp=new ArrayList<Employee>();
		Statement stmt=null;
		ResultSet myRs=null;
		try{
			
			stmt=myCon.createStatement();
			myRs=stmt.executeQuery("select * from employees");
			//System.out.println("Executed The get Queries");
			while(myRs.next()){
				Employee tempEmp=convertRowToEmployee(myRs);
				emp.add(tempEmp);
			}
			return emp;
		}catch(Exception exc){
			exc.printStackTrace();
		}finally{
			if(stmt!=null){
				stmt.close();
			}
			if(myRs!=null){
				myRs.close();
			}
		}
		return emp;
	}
	public List<Employee> searchEmployee(String lastName) throws Exception{
		PreparedStatement ps=null;
		ResultSet myRs=null;
		List<Employee> list=new ArrayList<>();
		try{
			lastName+="%";
			ps=myCon.prepareStatement("select * from employees where last_name LIKE ?;");
			ps.setString(1,lastName);
			myRs=ps.executeQuery();
			//System.out.println("The Query has Been Executed");
			while(myRs.next()){
				Employee tempEmp=convertRowToEmployee(myRs);
				//System.out.println(tempEmp);
				list.add(tempEmp);
			}
			return list;
		}catch(Exception exc){
			exc.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(myRs!=null){
				myRs.close();
			}
		}
		return list;
	}
	private Employee convertRowToEmployee(ResultSet rs) throws Exception{
		Employee emCon=new Employee(rs.getInt("id"),rs.getString("first_name"),rs.getString("last_name"),rs.getString("email"),rs.getBigDecimal("salary"));
		return emCon;
	}
	public void addEmployee(Employee theEmployee) throws Exception{
		PreparedStatement pstmt=null;
		try{
			int userID=1,emp_id=1;
			pstmt=myCon.prepareStatement("select * from users where first_name=?");
			pstmt.setString(1, EmployeeSearchApp.userName);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				userID=rs.getInt("id");
			}
			//System.out.println(userID);
			pstmt=myCon.prepareStatement("insert into employees (last_name,first_name,email,salary)values(?,?,?,?)");
			//pstmt.setInt(1, theEmployee.getID());
			pstmt.setString(1,theEmployee.getLastName());
			pstmt.setString(2,theEmployee.getFirstName());
			pstmt.setString(3,theEmployee.getEmail());
			pstmt.setBigDecimal(4, theEmployee.getSalary());
			pstmt.executeUpdate();
			pstmt=myCon.prepareStatement("select * from employees where last_name=?");
			pstmt.setString(1, theEmployee.getLastName());
			rs=pstmt.executeQuery();
			while(rs.next()){
				emp_id=rs.getInt("id");
			}
			//
			//This is for the Audit Table
			//
			//
			pstmt=myCon.prepareStatement("insert into audit_history (user_id,employee_id,action,action_date_time,user_name,employee_name)values(?,?,?,?,?,?)");
			pstmt.setInt(1, userID);
			pstmt.setInt(2, emp_id);
			pstmt.setString(3, "Employee Updated");
			pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			pstmt.setString(5, EmployeeSearchApp.userName);
			pstmt.setString(6, theEmployee.getFirstName());
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
	}
	public void updateEmployee(Employee theEmployee) throws Exception{
		PreparedStatement pstmt=null;
		try{
			int user_ID=4;
			pstmt=myCon.prepareStatement("select * from users where first_name=?");
			pstmt.setString(1, EmployeeSearchApp.userName);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				user_ID=rs.getInt("id");
				System.out.println("User id is::"+user_ID);
			}
			pstmt=myCon.prepareStatement("update employees set last_name=?,first_name=?,email=?,salary=? where id=?;");
			//pstmt.setInt(1, theEmployee.getID());
			pstmt.setString(1,theEmployee.getLastName());
			pstmt.setString(2,theEmployee.getFirstName());
			pstmt.setString(3,theEmployee.getEmail());
			pstmt.setBigDecimal(4, theEmployee.getSalary());
			pstmt.setInt(5, theEmployee.getID());
			pstmt.executeUpdate();
			//
			//Updating the Audit Table
			//
			pstmt=myCon.prepareStatement("insert into audit_history (user_id,employee_id,action,action_date_time,user_name,employee_name)values(?,?,?,?,?,?)");
			pstmt.setInt(1, user_ID);
			pstmt.setInt(2, theEmployee.getID());
			pstmt.setString(3, "Employee Updated");
			pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			pstmt.setString(5, EmployeeSearchApp.userName);
			pstmt.setString(6, theEmployee.getFirstName());
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null)
				pstmt.close();
		}
	}	
	public void deleteEmployee(int id){
		PreparedStatement pstmt=null;
		try{
			pstmt=myCon.prepareStatement("delete from employees where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}catch(Exception exc){
			exc.printStackTrace();
		}finally{
			if(pstmt!=null){
				try{
				pstmt.close();
				}catch(Exception e){}
			}
		}
	}
	/*public static void main(String args[]) throws Exception{
		EmployeeDAO query=new EmployeeDAO();
		System.out.println(query.getAllEmployees());
		System.out.println(query.searchEmployee("Thom"));
	}*/
}

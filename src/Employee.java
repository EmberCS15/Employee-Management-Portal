import java.math.BigDecimal;
public class Employee {
	private int id;
	private String first_name;
	private String last_name;
	private String email;
	private BigDecimal salary;
	public Employee(int id,String first_name,String last_name,String email,BigDecimal salary){
		super();
		this.id=id;
		this.first_name=first_name;
		this.last_name=last_name;
		this.email=email;
		this.salary=salary;
	}
	public String toString(){
		return id+" "+first_name+" "+last_name+" "+email+" "+salary;
	}
	public String getLastName(){
		return last_name;
	}
	public String getFirstName(){
		return first_name;
	}
	public String getEmail(){
		return email;
	}
	public BigDecimal getSalary(){
		return salary;
	}
	public Integer getID(){
		Integer id_col=new Integer(id);
		return id_col;	 
	}
	public void setFirstName(String firstName){
		this.first_name=firstName;
	}
	public void setLastName(String lastName){
		this.last_name=lastName;
	}
	public void setEmail(String email){
		this.email=email;
	}	
	public void setSalary(BigDecimal salary){
		this.salary=salary;
	}
}

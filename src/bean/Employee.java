package bean;
//员工信息：“ID”，“姓名”，“年龄”，“电话”，“职位”，“入职日期”，“薪水”，“部门”。
public class Employee {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String position;
    private String entryDate;
    private double salary;
    private String department;
    public Employee() {
    }
    public Employee(int id, String name, int age, String phone, String position, String entryDate, double salary, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.position = position;
        this.entryDate = entryDate;
        this.salary = salary;
        this.department = department;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", phone=" + phone + ", position=" + position
                + ", entryDate=" + entryDate + ", salary=" + salary + ", department=" + department + "]";
    }
}

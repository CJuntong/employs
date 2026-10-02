package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EmployManager extends JFrame {
    // 表格相关
    private JTable table;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;

    private static final List<Employee> empList = new ArrayList<>();

    static {
        for (int i = 1; i <= 20; i++) {
            empList.add(new Employee(i, "员工" + i, "部门" + (i % 5 + 1), 5000 + i * 200));
        }
    }

    // 组件
    private JTextField searchField;
    private JButton searchBtn;
    private JButton addBtn;

    // 当前登录用户名
    private String currentUser;

    // 员工实体类
    static class Employee {
        private int id;
        private String name;
        private String dept;
        private double salary;

        public Employee(int id, String name, String dept, double salary) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDept() { return dept; }
        public void setDept(String dept) { this.dept = dept; }
        public double getSalary() { return salary; }
        public void setSalary(double salary) { this.salary = salary; }
    }

    public EmployManager() {
        initData();
        initUI();
    }

    public EmployManager(String username) {
        this.currentUser = username;
        initData();
        initUI();
    }

    private void initData() {
    }

    // 初始化界面
    private void initUI() {
        setTitle("员工信息管理系统");
        setSize(800, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ========== 顶部欢迎面板 ==========
        Color HEADER_COLOR = new Color(64, 128, 255);
        Color HEADER_HOVER = new Color(100, 160, 255);
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, HEADER_COLOR, getWidth(), getHeight(), HEADER_HOVER);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 8, 20));

        JLabel welcomeLabel = new JLabel("🎉 欢迎 " + currentUser + " 进入员工信息管理系统", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("在这里您可以管理所有员工的信息", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(220, 230, 255));
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // ========== 工具栏面板：搜索框、搜索按钮、添加按钮 ==========
        JPanel toolPanel = new JPanel();
        toolPanel.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        searchField = new JTextField(15);
        searchBtn = new JButton("搜索");
        addBtn = new JButton("添加");

        toolPanel.add(searchField);
        toolPanel.add(searchBtn);
        toolPanel.add(addBtn);

        // ========== 顶部合并面板 ==========
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(headerPanel);
        topContainer.add(toolPanel);

        // ========== 表格 ==========
        String[] columnNames = {"编号", "姓名", "部门", "薪资"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        refreshTable(empList);
        JScrollPane scrollPane = new JScrollPane(table);

        // ========== 右键菜单：修改、删除 ==========
        popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("修改");
        JMenuItem delItem = new JMenuItem("删除");
        popupMenu.add(editItem);
        popupMenu.add(delItem);

        // 表格右键监听
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        // ========== 按钮事件 ==========
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            List<Employee> filterList = new ArrayList<>();
            for (Employee emp : empList) {
                if (emp.getName().contains(keyword) || emp.getDept().contains(keyword)) {
                    filterList.add(emp);
                }
            }
            refreshTable(filterList);
        });

        addBtn.addActionListener(e -> showAddDialog());

        editItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;
            int empId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Employee target = findEmpById(empId);
            if(target != null) showEditDialog(target);
        });

        delItem.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;
            int empId = (Integer) tableModel.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(null, "确定删除编号为 " + empId + " 的员工？", "提示", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                empList.removeIf(emp -> emp.getId() == empId);
                searchField.setText("");
                refreshTable(empList);
            }
        });

        // 布局
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topContainer, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    // 刷新表格数据
    private void refreshTable(List<Employee> list) {
        tableModel.setRowCount(0);
        for (Employee emp : list) {
            Object[] rowData = {emp.getId(), emp.getName(), emp.getDept(), emp.getSalary()};
            tableModel.addRow(rowData);
        }
    }

    // 根据ID查找员工
    private Employee findEmpById(int id) {
        for (Employee emp : empList) {
            if (emp.getId() == id) return emp;
        }
        return null;
    }

    // 新增弹窗
    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField salaryField = new JTextField();
        Object[] msg = {
                "姓名：", nameField,
                "部门：", deptField,
                "薪资：", salaryField
        };
        int res = JOptionPane.showConfirmDialog(null, msg, "新增员工", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            String salaryText = salaryField.getText().trim();

            if (name.isEmpty() || dept.isEmpty() || salaryText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请填写完整信息", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double salary = Double.parseDouble(salaryText);
                int newId = empList.stream().mapToInt(Employee::getId).max().orElse(0) + 1;
                Employee newEmp = new Employee(newId, name, dept, salary);
                empList.add(newEmp);
                refreshTable(empList);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "薪资请输入数字", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // 修改弹窗
    private void showEditDialog(Employee emp) {
        JLabel idLabel = new JLabel(String.valueOf(emp.getId()));
        idLabel.setForeground(new Color(127, 140, 155));
        JTextField nameField = new JTextField(emp.getName());
        JTextField deptField = new JTextField(emp.getDept());
        JTextField salaryField = new JTextField(String.valueOf(emp.getSalary()));
        Object[] msg = {
                "编号：", idLabel,
                "姓名：", nameField,
                "部门：", deptField,
                "薪资：", salaryField
        };
        int res = JOptionPane.showConfirmDialog(null, msg, "修改员工 - 编号 " + emp.getId(), JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            emp.setName(nameField.getText());
            emp.setDept(deptField.getText());
            emp.setSalary(Double.parseDouble(salaryField.getText()));
            refreshTable(empList);
        }
    }

    public static void main(String[] args) {
        // Swing程序建议在事件调度线程启动
        SwingUtilities.invokeLater(() -> {
            new EmployManager().setVisible(true);
        });
    }
}

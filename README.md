# Employs — 人事管理系统

基于 **Java Swing** 的桌面端人事（员工）管理系统，包含用户登录注册与员工信息的增删改查。

## 功能

- **登录 / 注册**：支持密码明文切换显示、记住密码、注册校验
- **员工管理**：表格展示员工列表（ID、姓名、部门、薪资），支持搜索、添加、编辑
- **界面样式**：Swing 自绘扁平化风格（自定义按钮、圆角输入框、渐变头部）

## 技术栈

- Java（Swing / AWT）
- 纯 JDK 实现，无任何第三方依赖

## 运行

### 命令行

```bash
javac -encoding UTF-8 -d out src/APP.java src/bean/*.java src/ui/*.java
java -cp out APP
```

### IntelliJ IDEA

直接打开项目，运行 `src/APP.java` 中的 `main()` 方法即可。

## 项目结构

```
src/
├── APP.java                # 程序入口
├── bean/
│   ├── Employee.java       # 员工实体类
│   └── User.java           # 用户实体类
└── ui/
    ├── LoginUi.java        # 登录 / 注册界面
    └── EmployManager.java  # 员工管理主界面
```

> 说明：当前版本数据保存在内存中，重启后不会持久化。

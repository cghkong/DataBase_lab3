package App;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static java.lang.Thread.sleep;

public class Main extends Application{

    // JDBC 驱动名及数据库 URL
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/ComChat";

    // 数据库的用户名与密码，需要根据自己的设置
    public static final String USER = "root";
    public static final String PASS = "******";  // 数据库登录密码

    public static Connection connection;
    public static Statement statement;
    public static Stage stageCore;
    public static Stage stageLogin;
    public static String MyUserid;

    @Override
    public void start(Stage stage) throws Exception {
        stageLogin = stage;
        LoginApp loginApp = new LoginApp();
        loginApp.start(stageLogin);
    }

    public static void main(String[] args) {

        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("正在连接数据库...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            if (!connection.isClosed()) {
                System.out.println("连接成功!");

                statement = connection.createStatement();

                launch(args);
            }
            statement.close();
            connection.close();
        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        }// 处理 Class.forName 错误
        finally {
            // 关闭资源
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Exit!");

    }

}


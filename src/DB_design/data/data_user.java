package DB_design.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class data {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/comchat";

        // 数据库的用户名与密码，需要根据自己的设置
        final String USER = "root";
        final String PASS = "cgh21#sql";

        Connection connection;

        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);

        // 打开链接
        System.out.println("正在连接数据库...");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        Random random = new Random();
        String pw = "123456";
        String[] sexset = {"F","M"};
        int cnt=0;
        for(long i=20220201;i<=20221100;i++)
        {
            StringBuilder sb=new StringBuilder();
            StringBuilder labl=new StringBuilder();
            String id = String.valueOf(i);
            for(int j=0;j<9;j++)
            {
                long result=Math.round(Math.random()*25+65);
                sb.append((char)result);
            }

            for(int j=0;j<25;j++)
            {
                long result=Math.round(Math.random()*25+65);
                labl.append((char)result);
            }

            String name = sb.toString();
            String sql1 = "insert into user_register values (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,pw);
            preparedStatement.setString(3,name);
            preparedStatement.execute();

            int age=random.nextInt(50)+12;
            String sex= sexset[cnt%2];
            cnt++;
            String label = labl.toString();
            int money = 1000;
            String sql2 = "insert into client_info values (?,?,?,?,?,?);";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1,id);
            preparedStatement2.setString(2,name);
            preparedStatement2.setInt(3,age);
            preparedStatement2.setString(4,sex);
            preparedStatement2.setString(5,label);
            preparedStatement2.setInt(6,money);
            preparedStatement2.execute();

            String sql3 = "insert into friendinfo values (?,?,?,?,?);";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            preparedStatement3.setString(1,id);
            preparedStatement3.setString(2,name);
            preparedStatement3.setString(3,name);
            preparedStatement3.setInt(4,age);
            preparedStatement3.setString(5,sex);

            preparedStatement3.execute();


        }
    }
}

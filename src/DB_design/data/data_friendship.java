package DB_design.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class data_friendship {
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
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
            for(long i=20220201;i<=20221000;i++)
            {
                int count = random.nextInt(10);
                String id = String.valueOf(i);
                for(int k=0;k<count;k++) {
                    long id_f;
                    id_f = i + (long) random.nextInt(90);
                    String id_friend = String.valueOf(id_f);
                    String message = "hello,how it is  going";

                    String sql0 = "insert into records values (?,?,?);";
                    PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
                    preparedStatement0.setString(1, id);
                    preparedStatement0.setString(2, id_friend);
                    preparedStatement0.setString(3, message);
                    preparedStatement0.execute();


                    String sql1 = "insert into friendship values (?,?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, id_friend);
                    preparedStatement.execute();

                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql1);
                    preparedStatement2.setString(1, id_friend);
                    preparedStatement2.setString(2, id);
                    preparedStatement2.execute();

                }

            }
        }

}

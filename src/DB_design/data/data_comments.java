package DB_design.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class data_comments {
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
                for(long i=20220201;i<=20221100;i++)
                {
                    int count = random.nextInt(20);
                    for(int k=0;k<count;k++) {
                        StringBuilder cont = new StringBuilder();
                        StringBuilder tops = new StringBuilder();
                        String id = String.valueOf(i);
                        for (int j = 0; j < 100; j++) {
                            long result = Math.round(Math.random() * 25 + 65);

                            cont.append((char) result);
                            if(j==30)
                            {
                                cont.append("\n");
                            }
                        }
                        String contents = cont.toString();
                        for (int j = 0; j < 25; j++) {
                            long result = Math.round(Math.random() * 25 + 65);
                            tops.append((char) result);
                        }
                        String topics = tops.toString();

                        int num = random.nextInt(100);

                        String sql1 = "insert into usercomments values (?,?,?,?);";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, topics);
                        preparedStatement.setString(3, contents);
                        preparedStatement.setInt(4, num);
                        preparedStatement.execute();

                    }

                }
            }

}

package App;

import java.awt.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static App.Main.*;



public class LoginUI{
    public Button Blogin = new Button();
    public Label Luserid = new Label();
    public Label Lpassword = new Label();
    public TextField Tuserid = new TextField();
    public Label Logo = new Label();
    public TextField ErrorMsg = new TextField();
    public Button Bregister;
    public Button BnewUser;
    public TextField Tnewid;
    public TextField Tnewname;
    public TextField Tnewpw;
    public Pane Registerplane;
    public TextField Tnewsex;
    public TextField Tnewage;
    public PasswordField Tpassword;


    @FXML
    private void click_Login(){
        String userid= Tuserid.getText();
        String userpw = Tpassword.getText();

        try {
            if (userid.equals("") || userid == null) {
                String ans = "用户id为空";
                ErrorMsg.setText(ans);
                return;
            }
            if (userpw.equals("") || userpw == null) {
                String ans = "密码为空";
                ErrorMsg.setText(ans);
                return;
            }
            String pw = "";
            String sql = "select user_pw from User_Register where user_id=" + userid + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                pw = resultSet.getString("user_pw");
            }
            if (pw.equals(userpw)) {
                MyUserid = userid;
                String ans = "登陆成功";
                ErrorMsg.setText(ans);
                stageCore = new Stage();
                CoreApp coreApp = new CoreApp();
                coreApp.start(stageCore);
                coreApp.init();
                stageLogin.close();

            } else {
                String ans = "密码错误或用户id错误";
                ErrorMsg.setText(ans);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void Register_show()
    {
        Registerplane.setVisible(true);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                null,null);
        Background background = new Background(background_fill);
        Registerplane.setBackground(background);
    }

    @FXML
    public void register_exit()
    {
        Registerplane.setVisible(false);
    }

    @FXML
    public void new_user(){
        String newid = Tnewid.getText();
        String newname = Tnewname.getText();
        String newpw = Tnewpw.getText();
        String newsex = Tnewsex.getText();
        String newage = Tnewage.getText();

        if(newid==null || newid.length()!=8)
        {
            Tnewid.setText("请重新输入id");
            return;
        }
        if(newname==null || newname.length()>15)
        {
            Tnewname.setText("请重新输入用户名");
            return;
        }
        if(newpw==null || newpw.length()>9)
        {
            Tnewpw.setText("请重新输入密码");
            return;
        }

        String sqlQ = "select * from user_register where user_id=?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQ);
            preparedStatement.setString(1,newid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Tnewid.setText("该id已注册");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "insert into user_register values (?,?,?);";
        String sqlcinfo = "insert into client_info values (?,?,?,?,null,default);";
        String sqlfinfo = "insert into friendinfo values (?,?,null,?,?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newid);
            preparedStatement.setString(2, newpw);
            preparedStatement.setString(3, newname);
            preparedStatement.execute();

            PreparedStatement preparedStatementcinfo = connection.prepareStatement(sqlcinfo);
            preparedStatementcinfo. setString(1,newid);
            preparedStatementcinfo. setString(2,newname);
            preparedStatementcinfo. setString(3,newage);
            preparedStatementcinfo. setString(4,newsex);
            preparedStatementcinfo.execute();

            PreparedStatement preparedStatementfinfo = connection.prepareStatement(sqlfinfo);
            preparedStatementfinfo.setString(1,newid);
            preparedStatementfinfo.setString(2,newname);
            preparedStatementfinfo.setString(3,newsex);
            preparedStatementfinfo.setString(4,newage);
            preparedStatementfinfo.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Registerplane.setVisible(false);
    }

}

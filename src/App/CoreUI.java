package App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;
import javax.swing.text.html.HTML;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static App.Main.*;

public class CoreUI {

    public Button BFriend;
    public Button BComments;
    public Button BInformation;
    public ImageView Imageself;
    public TextField Tname;
    public TextField Tage;
    public TextField Tsex;
    public TextField Tmoney;
    public Button Bsetting;
    public ListView<Button> FriendList = new ListView<>();
    public VBox CenterBox;
    public TextArea Trecords;
    public TextField Tfid;
    public Button Bsend;
    public TextArea TMessage;
    public TextField UserID;
    public Button changeEdit;
    public SplitPane Commentplane;
    public BorderPane Mainplane;
    public Button BHome;
    public ListView<TextArea> ListComments = new ListView<>();
    public TextArea Tincomment;
    public TextField Ttopics;
    public Button Brelease;
    public VBox BFriendBox;
    public VBox BmeBox;
    public Menu MLogout;
    public ScrollPane Recordsplane;
    public TableView<Records_element> TableRecords = new TableView<>();
    public Button BRecords;
    public Pane AddPlane;
    public TextField Taddfid;
    public Menu MFriend;
    public MenuItem Manuadd;
    public TextArea TIntroduction;
    public TableColumn<Records_element,String> user_id_column;
    public TableColumn<Records_element,String> user_id_f_column;
    public TableColumn<Records_element,String> user_message_column;

    @FXML
    public void initapp()
    {
        String init_sql = "select * from client_info where user_id="+MyUserid+";";
        try {
            ResultSet resultSet = statement.executeQuery(init_sql);
            if(resultSet.next())
            {
                Image image = new Image("file:bg.jpg");
                Imageself.setImage(image);
                Tage.setText(resultSet.getString("user_age"));
                Tname.setText(resultSet.getString("user_name"));
                Tsex.setText(resultSet.getString("user_sex"));
                UserID.setText(MyUserid);
                Tmoney.setText(resultSet.getString("user_money"));
                TIntroduction.setText(resultSet.getString("user_label"));
            }
            else {
                System.out.println("无数据！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void sendMessage()
    {
        String msg = TMessage.getText();
        if(msg==null || msg.length()==0)
        {
            return;
        }
        String fid = Tfid.getText();
        if(fid==null || fid.length()==0)
        {
            return;
        }
        String uid=null;
        String sql = "select user_id_f from friendship where user_id=? and user_id_f=?;";
        try{
            PreparedStatement preparedStatementcheck = connection.prepareStatement(sql);
            preparedStatementcheck.setString(1,UserID.getText());
            preparedStatementcheck.setString(2,fid);
            ResultSet resultSetcheck = preparedStatementcheck.executeQuery();

            if(fid.equals(UserID.getText()))
            {
                Trecords.setText("发送id错误");
                return;
            }

            if (resultSetcheck.next()) {
                uid = UserID.getText();

                String msg_send = Trecords.getText() + "from " + uid + " To " + fid + ": " + msg + "\n";

                //update records;
                System.out.println(msg_send);
                String insert_sql = "insert into records values ( ?,?,?);";

                PreparedStatement preparedStatement = connection.prepareStatement(insert_sql);
                preparedStatement.setString(1, uid);
                preparedStatement.setString(2, fid);
                preparedStatement.setString(3, msg);

                preparedStatement.execute();

                Trecords.setText(msg_send);

            }
            else{
                Trecords.setText(Trecords.getText()+"该用户和TA还不是朋友\n");

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Chatplane()
    {
        Commentplane.setVisible(false);
        Recordsplane.setVisible(false);
    }

    @FXML
    public void express_comments()
    {
        Recordsplane.setVisible(false);
        Commentplane.setVisible(true);
        ListComments.setMaxWidth(Mainplane.getPrefWidth()-BFriendBox.getPrefWidth()-BmeBox.getPrefWidth());
        try {
            String sql_context = "select * from usercomments where user_id=? ;";
            PreparedStatement preparedStatement_show = connection.prepareStatement(sql_context);
            preparedStatement_show.setString(1,UserID.getText());

            ResultSet resultSet = preparedStatement_show.executeQuery();
            ObservableList<TextArea> items = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                TextArea textArea = new TextArea();
                textArea.setPrefWidth(200);
                textArea.setPrefHeight(80);
                textArea.setText(resultSet.getString("user_id")+"  "+resultSet.getString("user_topics")
                        +"\n"+resultSet.getString("user_contents"));
                textArea.setEditable(false);
                items.add(textArea);
            }
            ListComments.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeEditable()
    {
        Tsex.setEditable(true);
        Tmoney.setEditable(true);
        Tage.setEditable(true);
        TIntroduction.setEditable(true);
    }

    @FXML
    public void setInformation()
    {

        if(Tsex.getText()==null || Tsex.getText()==null
            || Tmoney.getText()==null || TIntroduction.getText()==null)
        {
            System.out.println("填写信息不完整!");
        }
        else{
            Tsex.setText(Tsex.getText());
            Tage.setText(Tage.getText());
            Tmoney.setText(Tmoney.getText());
            TIntroduction.setText(TIntroduction.getText());
        }
        Tsex.setEditable(false);
        Tmoney.setEditable(false);
        Tage.setEditable(false);
        TIntroduction.setEditable(false);

        String sql1 = "update client_info set user_age=?,user_sex=?,user_money=?,user_label=? where user_id=?;";
        String sql2 = "update friendinfo set sex=?,age=? where user_id=?;";
        try{
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, Integer.parseInt(Tage.getText()));
            preparedStatement1.setString(2, Tsex.getText());
            preparedStatement1.setInt(3, Integer.parseInt(Tmoney.getText()));
            preparedStatement1.setString(4, TIntroduction.getText());
            preparedStatement1.setString(5, UserID.getText());

            preparedStatement1.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, Tsex.getText());
            preparedStatement2.setInt(2, Integer.parseInt(Tage.getText()));
            preparedStatement2.setString(3, UserID.getText());
            preparedStatement2.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void release_comment()
    {
        String msg_comments = Tincomment.getText();
        String msg_topics =Ttopics.getText();
        int num1 = 1;
        String uid = UserID.getText();

        try{
            String sql = "insert into usercomments values (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,uid);
            preparedStatement.setString(2,msg_topics);
            preparedStatement.setString(3,msg_comments);
            preparedStatement.setInt(4,num1);

            preparedStatement.execute();

            TextArea textArea = new TextArea();
            textArea.setPrefHeight(120.0);
            textArea.setText(""); textArea.setText(uid+"  "+msg_topics
                    +"\n"+msg_comments);
            textArea.setEditable(false);
            ObservableList<TextArea> items = ListComments.getItems();
            items.add(0,textArea);
            ListComments.setItems(items);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void Friend_show()
    {
        ObservableList<Button> items = FXCollections.observableArrayList();
        String sql_friends = "select * from friendinfo where user_id in (select user_id_f from friendship where user_id=?);";
        FriendList.setPrefWidth(BFriendBox.getPrefWidth());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql_friends);
            preparedStatement.setString(1,UserID.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String fname = resultSet.getString("user_name");
                String fage = resultSet.getString("age");
                String fsex = resultSet.getString("sex");
                String fid = resultSet.getString("user_id");

                Button button = new Button();
                button.setPrefWidth(150);
                button.setPrefHeight(22);
                button.setText(resultSet.getString("user_name"));

                button.setOnMouseClicked(event->{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Friend Information");
                    alert.setHeaderText(fname);
                    alert.setContentText("fid:" + fid + "\n" + "age:" + fage + "  " + "sex:" + fsex);
                    alert.showAndWait();
                    Tfid.setText(fid);
                });

                button.setOnMouseDragged(mouseEvent -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("delete friend");
                        alert.setHeaderText(fname);
                        alert.setContentText("are you sure to delete the friend");
                        alert.showAndWait();
                        alert.setOnCloseRequest(ActionEvent -> {
                        });
                        String deleteSql = "delete from friendship where user_id=? and user_id_f=?;";
                        String deleteSql2 = "delete from friendship where user_id=? and user_id_f=?;";

                        try {
                            PreparedStatement preparedStatementdelete = connection.prepareStatement(deleteSql);
                            preparedStatementdelete.setString(1, UserID.getText());
                            preparedStatementdelete.setString(2, fid);
                            preparedStatementdelete.execute();

                            PreparedStatement preparedStatementdelete2 = connection.prepareStatement(deleteSql2);
                            preparedStatementdelete2.setString(2, UserID.getText());
                            preparedStatementdelete2.setString(1, fid);
                            preparedStatementdelete2.execute();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                });

                items.add(button);
                System.out.println(fid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FriendList.setItems(items);
        FriendList.setVisible(true);
    }


    @FXML
    public void Logout()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(UserID.getText());
        alert.setContentText("are you sure to Logout?");
        alert.showAndWait();

        String deleteRegisterSQl = "delete from user_register where user_id=?;";
        String deleteclientSQl = "delete from client_info where user_id=?;";
        String deletefriendinfoSQl = "delete from friendinfo where user_id=?";
        String deletefriendshipSQl = "delete from friendship where user_id=? or user_id_f=?;";
        String deletecommentsSQl = "delete from usercomments where user_id=?;";
        String deleterecordsSQl = "delete from records where user_id=? or user_id_f=?;";
        String deleteTransactionSQL = "delete from transaction_event where user_id=? or user_id_f=?;";

        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(deleteRegisterSQl);
            preparedStatement1.setString(1,UserID.getText());
            preparedStatement1.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement(deleteclientSQl);
            preparedStatement2.setString(1,UserID.getText());
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = connection.prepareStatement(deletefriendinfoSQl);
            preparedStatement3.setString(1,UserID.getText());
            preparedStatement3.execute();

            PreparedStatement preparedStatement4 = connection.prepareStatement(deletefriendshipSQl);
            preparedStatement4.setString(1,UserID.getText());
            preparedStatement4.setString(2,UserID.getText());
            preparedStatement4.execute();

            PreparedStatement preparedStatement5 = connection.prepareStatement(deletecommentsSQl);
            preparedStatement5.setString(1,UserID.getText());
            preparedStatement5.execute();

            PreparedStatement preparedStatement6 = connection.prepareStatement(deleterecordsSQl);
            preparedStatement6.setString(1,UserID.getText());
            preparedStatement6.setString(2,UserID.getText());
            preparedStatement6.execute();

            PreparedStatement preparedStatement7 = connection.prepareStatement(deleteTransactionSQL);
            preparedStatement7.setString(1,UserID.getText());
            preparedStatement7.setString(2,UserID.getText());
            preparedStatement7.execute();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Logout");
        alert2.setHeaderText(UserID.getText());
        alert2.setContentText("Logout successfully?");
        alert2.showAndWait();

        System.exit(0);
    }

    @FXML
    public void show_records()
    {
        Commentplane.setVisible(false);
        Recordsplane.setVisible(true);

        user_id_column.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        user_id_f_column.setCellValueFactory(new PropertyValueFactory<>("user_id_f"));
        user_message_column.setCellValueFactory(new PropertyValueFactory<>("message"));

        ObservableList<Records_element> items = FXCollections.observableArrayList();

        String sql = "select * from records where user_id=? or user_id_f=?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,UserID.getText());
            preparedStatement.setString(2,UserID.getText());

            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next())
            {
                items.add(new Records_element(resultSet.getString("user_id"),
                        resultSet.getString("user_id_f"),resultSet.getString("message")));
                System.out.println(resultSet.getString("message"));
            }
            TableRecords.setItems(items);
            TableRecords.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void show_addplane()
    {
        AddPlane.setVisible(true);
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                null,null);
        Background background = new Background(background_fill);
        AddPlane.setBackground(background);
    }

    @FXML
    public void register_exit()
    {
        AddPlane.setVisible(false);
    }

    @FXML
    public void addfriend()
    {
        String fid = Taddfid.getText();
        String Qsql = "select * from user_register where user_id=?;";

        if(fid==null || fid.length()!=8)
        {
            Trecords.setText(Trecords.getText()+"\n"+"该用户id格式错误，添加失败\n");
            return;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Qsql);
            preparedStatement.setString(1,fid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                Trecords.setText(Trecords.getText()+"\n"+"该用户id未注册，添加失败\n");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String addsql = "insert into friendship values (?,?),(?,?);";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(addsql);
            preparedStatement.setString(1,UserID.getText());
            preparedStatement.setString(2,fid);
            preparedStatement.setString(3,fid);
            preparedStatement.setString(4,UserID.getText());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Trecords.setText(Trecords.getText()+"\n"+"添加成功!\n");
        AddPlane.setVisible(false);
    }

}

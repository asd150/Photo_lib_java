package controller;


import Model.AlbumUsers;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


@FXML private TextField username;
@FXML private Button addButton;
@FXML private Button adminQuit;
@FXML private Button adminLogout;
@FXML private ListView<User> userList;


private AlbumUsers albumUsers;
private User user;
private ArrayList<User> userArrayList;
private ObservableList<User> obList;






   public void add() throws IOException, ClassNotFoundException {

      String input = username.getText();

       if(!input.isEmpty()){

           if(!albumUsers.UsernameTaken(input)) {
               User newUser = new User(input);

               albumUsers.addUsers(newUser);

               userArrayList = albumUsers.getUsers();
               obList = FXCollections.observableList(userArrayList);
               userList.setItems(obList);
               username.setText("");
               AlbumUsers.writeUsers(albumUsers);
               username.setText("");
           }
           else
           {
               Alert sameuser = new Alert(Alert.AlertType.ERROR);
               sameuser.setTitle("Same username");
               sameuser.setContentText("Username Already Exists");
               sameuser.showAndWait();
               username.setText("");
           }

       }
       else
       {
           Alert noinput = new Alert(Alert.AlertType.ERROR);
           noinput.setContentText("Please Enter Username");
           noinput.setTitle("Empty Field");
           noinput.showAndWait();
           username.setText("");

       }

   }
   public void quit(){

      Stage stage= (Stage) adminQuit.getScene().getWindow();
       stage.close();
   }
   public void remove() throws IOException, ClassNotFoundException {


       int index = userList.getSelectionModel().getSelectedIndex();
       String rem = userArrayList.get(index).getUsernname();
       if(!rem.equals("admin")) {

           User u = userArrayList.get(index);
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Confirm?");
           alert.setContentText("Are you sure? You want to delete " + rem );
           Optional<ButtonType> result = alert.showAndWait();
           if(result.get() == ButtonType.OK) {

               albumUsers.removeUsers(u);
               obList = FXCollections.observableList(userArrayList);
               userList.setItems(obList);
               AlbumUsers.writeUsers(albumUsers);
           }
       }
       else
       {
           Alert adminDel = new Alert(Alert.AlertType.ERROR);
           adminDel.setTitle("Delete Admin");
           adminDel.setContentText("Admin cannot be removes");
           adminDel.showAndWait();
       }
   }
   public void logout() throws IOException {
       FXMLLoader logout = new FXMLLoader(getClass().getResource("/View/photoLogin.fxml"));
       Parent root = (Parent) logout.load();
       Scene scene = new Scene(root);
       Stage stage = new Stage();
       stage.setTitle("Login");
       stage.setResizable(false);
       stage.setScene(scene);
       stage.show();
       Stage currStage =(Stage) adminLogout.getScene().getWindow();
       currStage.close();

   }

   private void populateList() throws IOException, ClassNotFoundException {
       albumUsers= AlbumUsers.readUsers();
       for(User u : albumUsers.getUsers()) {
           System.out.println("users " +u.getUsernname());
       }
        System.out.println("populateList "+albumUsers );
        userArrayList = albumUsers.getUsers();
        obList = FXCollections.observableList(userArrayList);
        userList.setItems(obList);
        //userList.getSelectionModel().select(0);

   }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

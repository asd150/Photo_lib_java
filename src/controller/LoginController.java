package controller;

import Model.AlbumUsers;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import  javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernamelogin;
    @FXML private Button loginButton;
    /**
     *
     */
    @FXML private Button loginquit;

    private String UserName;
    private Stage stage;
    private AlbumUsers albumUsers;






   @FXML
   private void login() throws IOException, ClassNotFoundException {
        //when login button is pressed


       /**
        * if(user ecxists)
        *   then open user window
        *   if(user entered is "admin")
        *       then open admin window
        *     if(the user entered doesnot existes in the albumuser list
        *       add the user
        *       */

        //get the text from login space
       //User newuser = new User("s");
       //System.out.println(newuser.getUsernname());
       //albumUsers.addUsers(newuser);
      // AlbumUsers.writeUsers(albumUsers);
       albumUsers = AlbumUsers.readUsers();
       UserName = usernamelogin.getText().trim();
       usernamelogin.setText("");

       //jugarddd
        for(User u : albumUsers.getUsers()) {
            System.out.println("input list " + albumUsers.getUsers());
        }
       if(UserName.isEmpty()){
           //show alert message
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Username Invalid");
           alert.setHeaderText("Enter Username");
           alert.setContentText("Please Enter Username!");
           alert.showAndWait();
       }
       else
           {

           if(albumUsers.UserExists(UserName)) {
               //if userexists?
               if (UserName.equals("admin")) {
                   // got to admin window
                    FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/View/photoAdmin.fxml"));
                    Parent root = (Parent) adminLoader.load();
                    Scene scene = new Scene(root);
                    Stage adminStage = new Stage();
                    adminStage.setScene(scene);
                    adminStage.setTitle("Admin view");
                    adminStage.setResizable(false);
                    adminStage.show();
                    stage = (Stage) usernamelogin.getScene().getWindow();
                    stage.close();


               } else {
                   //go to user window
                   //regular user and it exists

                   FXMLLoader userloader = new FXMLLoader(getClass().getResource("/View/photoUser.fxml"));

                   Parent root = (Parent)userloader.load();
                   Scene scene = new Scene(root);
                   Stage userStage = new Stage();
                   //System.out.println("userexist = "+UserName);
                   UserController ctrl = userloader.getController();
                   ctrl.getuser(UserName);

                   userStage.setResizable(false);
                   userStage.setTitle("User View");
                   userStage.setScene(scene);
                   userStage.show();
                   stage = (Stage) usernamelogin.getScene().getWindow();
                   stage.close();

               }
           }
           else{

                //user doesnot exist
               //create acc

               System.out.println("User not exists");
               System.out.println("Answer = "+albumUsers.UsernameTaken(UserName));
               if(albumUsers.UsernameTaken(UserName)){
                   Alert sameInput = new Alert(Alert.AlertType.ERROR);
                   sameInput.setTitle("Enter Different UserName");
                   sameInput.showAndWait();

               }
               else
               {
                   User newUser = new User(UserName);
                   albumUsers.addUsers(newUser);
                   AlbumUsers.writeUsers(albumUsers);
                   FXMLLoader newuserloader = new FXMLLoader(getClass().getResource("/View/photoUser.fxml"));
                   Parent root = (Parent) newuserloader.load();
                   Scene scene = new Scene(root);
                   Stage newStage = new Stage();
                   UserController ctrl = newuserloader.getController();
                   ctrl.getuser(UserName);

                   newStage.setScene(scene);
                   newStage.setTitle("User View");
                   newStage.show();
                   stage = (Stage) usernamelogin.getScene().getWindow();
                   stage.close();
               }

           }
       }

   }





    @FXML
    private void quit() throws Exception{

        stage = (Stage) loginquit.getScene().getWindow();
        stage.close();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}



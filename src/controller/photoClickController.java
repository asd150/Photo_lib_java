package controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * ${2 Player Chess }
 *
 * @author Arthkumar Desai
 * @author Roman Stashchyshyn
 */
public class photoClickController {
    //private var
    private Album currentAlbum;
    private User currentUser;
    private Photos currentPic;
    private String currName;


    private AlbumUsers albumusers;
    private ArrayList<User> userList;
    private ArrayList<Album> albums;
    private ObservableList<Photos> obsList;
    private ArrayList<Tag> tags;
    private boolean search = false;



    @FXML private Button quitButton;
    @FXML private ImageView imageViewer;
    @FXML private Text captionLabel;
    @FXML private Text dateLabel;
    @FXML private TextField captionArea;
    @FXML private TextField albumArea;
    @FXML private Button captionButton;
    @FXML private Button copyButton;
    @FXML private Button moveButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;





    public void set_user_album(Album album,User user, boolean search) throws IOException, ClassNotFoundException {

        currentAlbum = album;
        System.out.println("Current album 1 "+ currentAlbum);
        currentUser = user;
        currName = user.getUsernname();

        System.out.println("User albums " + currentUser.getAlbums());

        albumusers = AlbumUsers.readUsers();
        System.out.println("albumUsers  = " +albumusers.getUsers());

      for(int i =0 ;i<albumusers.getUsers().size();i++){
         if( albumusers.getUsers().get(i).getUsernname().equals(currentUser.getUsernname())){
              System.out.println("Index is i  " + i) ;
              System.out.println("username is  " + currentUser.getUsernname());
              albumusers.getUsers().remove(i);

          }
      }

      for(int i = 0 ; i < currentUser.getAlbums().size();i++){
          if(currentUser.getAlbums().get(i).getAlbumName().equals(currentAlbum.getAlbumName())){
              System.out.println("Matched album is " + currentUser.getAlbums().get(i));
              currentUser.removeAlbum(currentAlbum);

          }
      }


      System.out.println("CurrentUser albums "+ currentUser.getAlbums());
      System.out.println("current Album "+ currentAlbum.getListofphotos().size());
      System.out.println("Current User" + currentUser);



        System.out.println("albumUsers 2 = " +albumusers.getUsers());




    }

    public void set_stage(Stage stage, ImageView imageView, File imageFile) throws IOException, ClassNotFoundException {
        System.out.println("Image File "+imageFile);
        imageViewer.setImage(imageView.getImage());
//        int PicIndex;
//       for(Photos p : currentAlbum.getListofphotos()){
//           if(p.getPhotoFile().equals(imageFile)){
//               System.out.println("matched file name " + p.getPhotoFile());
//               currentPic = p;
//               PicIndex =  currentAlbum.getListofphotos().indexOf(p);
//               currentAlbum.removePhoto(p);
//
//           }
//       }
        System.out.println("SIZE  = "+currentAlbum.getListofphotos().size());
       for(int i =0; i < currentAlbum.getListofphotos().size(); i++){
           if(imageFile.equals(currentAlbum.getListofphotos().get(i).getPhotoFile())){
               currentPic = currentAlbum.getListofphotos().get(i);
               currentAlbum.removePhoto(currentPic);

           }
       }
//     System.out.println("SIZE  = "+currentAlbum.getListofphotos().size());
////       System.out.println("CURRENT PIC "+ currentPic.getPhotoFile());

        if(currentPic.getCaption()==null){
           captionLabel.setText("No Caption");
        }else{
       captionLabel.setText(currentPic.getCaption());}
       dateLabel.setText(currentPic.getDate());







    }

    public void caption() throws IOException, ClassNotFoundException {
        System.out.println("captionarea "+captionArea.getText());
        if(captionArea.getText().isEmpty()){
            Alert empty = new Alert(Alert.AlertType.ERROR);
            empty.setContentText("Enter Caption");
            empty.setTitle("Empty Field");
            empty.showAndWait();
            captionArea.setText("");
        }
        else{
            Alert notEmpty = new Alert(Alert.AlertType.CONFIRMATION);
            notEmpty.setContentText("Are you Sure? Action to Add caption " + captionArea.getText());
            Optional<ButtonType> result = notEmpty.showAndWait();
            if(result.get()==ButtonType.OK){
                currentPic.setCaption(captionArea.getText());
                captionLabel.setText(captionArea.getText());
                captionArea.setText("");


            }
        }


    }
    public void move() throws IOException, ClassNotFoundException {
//        System.out.println("currentPic Caption " + currentPic.getCaption());
//        System.out.println("AFTER DEL USERS "+albumusers.getUsers());

        String givenAlbum = null;
        boolean contains = false;
        if(albumArea.getText().isEmpty() || albumArea.getText().equalsIgnoreCase("admin")){
            //Alert for empty
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("Please enter correct info");
            error.show();
            albumArea.setText("");

        }else
        {

            //check if that album exists
            givenAlbum =  albumArea.getText();

           System.out.println(currentUser.getAlbums());
           for(int i = 0 ;i < currentUser.getAlbums().size();i++){
               if(givenAlbum.equals(currentUser.getAlbums().get(i).getAlbumName())){
                   System.out.println("Matched " + givenAlbum);
                   contains = true;

               }

           }
        }

           if(contains){
               System.out.println("3");
               //album is present(CHECKED)
               //del from current album (DONE)
               //add to another album (DONE)


             //confirmation
             Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
             Optional<ButtonType> result = confirm.showAndWait();
             if(result.get() == ButtonType.OK){

                 for(int i = 0; i < currentUser.getAlbums().size();i++){
                     if(givenAlbum.equals(currentUser.getAlbums().get(i).getAlbumName())){
                         currentUser.getAlbums().get(i).addPhoto(currentPic);

                     }
                 }
               // System.out.println("move() " + currentUser.getAlbums());

                 currentUser.addAlbum(currentAlbum);
                 albumusers.addUsers(currentUser);
                 AlbumUsers.writeUsers(albumusers);
                 currentPic= null;
                 albumArea.setText("");

                 imageViewer.setImage(null);
                 captionButton.setDisable(true);
                 addButton.setDisable(true);
                 deleteButton.setDisable(true);
                 copyButton.setDisable(true);
                 moveButton.setDisable(true);
                 captionLabel.setText("");
                 dateLabel.setText("");
             }
             else{

             }


           }
           else
           {

               //ALERT
               Alert notExist = new Alert(Alert.AlertType.ERROR);
               notExist.setContentText("Invalid Album Name");
               notExist.show();
               albumArea.setText("");
               givenAlbum=null;
               contains=false;
           }


            //if yes move currentPic to given album
            //back to albumview






    }
    public void copy(){

    }
    public void addTag(){

    }
    public void deletetag(){

    }
public void logOut() throws IOException {

        FXMLLoader logout = new FXMLLoader(getClass().getResource("/View/photoLogin.fxml"));
    Parent root = (Parent) logout.load();
    Scene scene = new Scene(root);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Login Page");
    stage.show();
    Stage closeStage = (Stage) quitButton.getScene().getWindow();
    closeStage.close();


}
public void back() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
        Parent root = (Parent) loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Album view");
        AlbumViewController ctrl = loader.getController();
        ctrl.set_user_album(currentAlbum,currentUser,currName);

        Stage qStage = (Stage) quitButton.getScene().getWindow();
        qStage.close();






}
    public void quit(){
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }


}

package controller;

import Model.Album;
import Model.Photos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class AlbumViewController {

   //FXML
   @FXML private Button quitButton;
   @FXML private Button addButton;
   @FXML private Button logoutButton;
   @FXML private Button deleteButton;
   @FXML private Button backButton;
   @FXML private Button slideShowButton;

    //used when back button is pressed
private String fromUser;
//album to open from the user
private Album albumtoshow;

//global variables
    private Stage stage;
   @FXML private ImageView imageView;

    public void getAlbum(Album album,String fromUser){
    album.getListofphotos();


      //Imageview
        imageView = new ImageView();
        

    }


    public void quit(){
        stage = (Stage) quitButton.getScene().getWindow();
        stage.close();

    }
    public void logout(){
        //go to login page
    }
    public void back(){
        //go to user page
    }
    public void add(){
    //add photos to existing list
    }
    public void delete(){
    //delete photos from existing list
    }
    public void slideshow() throws IOException {
        //go to slideshow.fxml
        FXMLLoader slideshowLoader = new FXMLLoader(getClass().getResource("/View/photoSlideShow.fxml"));
        Parent root = (Parent) slideshowLoader.load();
        Scene scene = new Scene(root);
        Stage slideStage = new Stage();
        slideStage.setScene(scene);
        slideStage.setTitle("Slide Show");
        slideStage.setResizable(false);
        slideStage.show();
        stage = (Stage) slideShowButton.getScene().getWindow();
        stage.close();


    }

}

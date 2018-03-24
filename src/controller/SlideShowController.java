package controller;

import Model.Album;

import Model.Photos;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ${2 Player Chess }
 *
 * @author Arthkumar Desai
 * @author Roman Stashchyshyn
 */
public class SlideShowController {

    private Album albumrec;
    private User user;
    private String Username;

    private  Photos currentpic;
    private boolean firstSetup = true;
    @FXML private ImageView view;
    @FXML private AnchorPane anchorPane;
    @FXML private Button quitButton;

    public void getAlbum(Album album, User user1, String username) throws FileNotFoundException {
        this.albumrec = album;
        this.user = user1;
        this.Username = username;
        System.out.println("printing"+this.albumrec + ""+  this.user + ""+   this.Username);
        Image image = new Image(new FileInputStream(album.getListofphotos().get(0).getPhotoFile()));
        currentpic = album.getListofphotos().get(0);
        view.setImage(image);
        firstSetup=false;
        }

    public void next() throws FileNotFoundException {



      Photos p =  albumrec.nextPhoto(currentpic);
     currentpic=p;
      Image image = new Image(new FileInputStream(p.getPhotoFile()));
      view.setImage(image);
      System.out.println("image "+image);

    }
    public void previous() throws FileNotFoundException {

        Photos p = albumrec.prevPhoto(currentpic);
        currentpic = p;
        Image image = new Image(new FileInputStream(p.getPhotoFile()));
        view.setImage(image);

    }
    public void quit(){
       Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        }
    public void logOut() throws IOException {
        FXMLLoader logoutloader = new FXMLLoader(getClass().getResource("/View/photoLogin.fxml"));
        Parent root = (Parent) logoutloader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.show();


    }
    public void back() throws IOException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Album View");
        stage.setResizable(false);
        stage.setScene(scene);
        AlbumViewController ctrl = loader.getController();
        ctrl.set_user_album(albumrec,user,Username);
        stage.show();
        Stage closes = (Stage) quitButton.getScene().getWindow();
        closes.close();



    }

}

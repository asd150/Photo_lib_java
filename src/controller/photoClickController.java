package controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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



    public void set_user_album(Album album,User user, boolean search){

        currentAlbum = album;
        System.out.println("Current album 1 "+ currentAlbum);
        currentUser = user;
        System.out.println("User albums " + currentUser.getAlbums());



    }

    public void set_stage(Stage stage, ImageView imageView, File imageFile) throws IOException, ClassNotFoundException {
        System.out.println("Image File "+imageFile);
        imageViewer.setImage(imageView.getImage());

       for(Photos p : currentAlbum.getListofphotos()){
           if(p.getPhotoFile().equals(imageFile)){
               System.out.println("matched file name " + p.getPhotoFile());
               currentPic = p;

           }
       }

        if(currentPic.getCaption()==null){
           captionLabel.setText("No Caption");
        }else{
       captionLabel.setText(currentPic.getCaption());}
       dateLabel.setText(currentPic.getDate());


        //get user -> albumuser
        //get albumList ->user.getAlbums();
        //get album -> list
        //get photo -> album

        albumusers = AlbumUsers.readUsers();
        System.out.println("All Users " + albumusers.getUsers());

        for(User u : albumusers.getUsers()){
            if(u.getUsernname().equals(currentUser.getUsernname())){
                System.out.println("Matched Username" + u.getUsernname());
            }
        }

        System.out.println("Current User Albums " + currentUser.getAlbums());
        for(Album a : currentUser.getAlbums()){
            if(a.getAlbumName().equals(currentAlbum.getAlbumName())){
                System.out.println("Matched albumName " + a.getAlbumName());
            }
        }



    }

    public void caption(){
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
                captionArea.setText("");
            }
        }


    }
    public void move(){
        System.out.println("currentPic Caption " + currentPic.getCaption());

    }
    public void copy(){

    }
    public void addTag(){

    }
    public void deletetag(){

    }

public void logOut(){

}
public void back(){


}
    public void quit(){
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }


}

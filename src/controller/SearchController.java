package controller;

import Model.Album;
import Model.AlbumUsers;
import Model.Photos;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * $ Photo Library
 *
 * @author Arthkumar Desai
 * @author Roman Stashchyshyn
 */
public class SearchController {
    private String username;
    private Album album;
    private User user;
    private Stage stage;
    private AlbumUsers albumUsers;
    private ArrayList<User> userList;
    private ArrayList<Album> albums;
    private Album currentAlbum;
    private User CurrentUser;
    @FXML private Button createButton;



    @FXML
   private TextField newAlbumName;

    @FXML
    private VBox vBox;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TilePane photoCollection;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label earliestDate, latestDate, albumSize;

    private boolean createdAlbum = false;

    /**
     *
     * @param photosList
     * @param recUser
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void getData(List<Photos> photosList,User recUser ) throws IOException, ClassNotFoundException {

        System.out.println(photosList);
        System.out.println(recUser.getAlbums());

            albumUsers = AlbumUsers.readUsers();
            System.out.println("read " + albumUsers.getUsers());
            for(int i =0;i<albumUsers.getUsers().size();i++){
                if(albumUsers.getUsers().get(i).getUsernname().equals(recUser.getUsernname())){
                    CurrentUser=albumUsers.getUsers().get(i);
                    albumUsers.removeUsers(CurrentUser);
                }
            }
        System.out.println("delete " + albumUsers.getUsers());
        Album a = new Album();

      for(int i =0 ; i < photosList.size();i++){
          a.addPhoto(photosList.get(i));
      }

      currentAlbum = a;
//      albums = user.getAlbums();

        for(Photos photo : currentAlbum.getListofphotos()) {
            ImageView imageView;
            imageView = create_image_view(photo.getPhotoFile());
            Label labeledImage = create_captioned_image(imageView, photo.getCaption(),photo.getPhotoFile());
            photoCollection.getChildren().addAll(labeledImage);
        }
        if(currentAlbum.getListofphotos().size() > 0) {
            currentAlbum.setListofphotos(currentAlbum.getListofphotos());
            albumSize.setText(currentAlbum.getListofphotos().size()+" photos");
            earliestDate.setText(currentAlbum.getEarly().getDate());
            latestDate.setText(currentAlbum.getOld().getDate());
        }else {
            albumSize.setText("This album is empty");
            earliestDate.setText("This album is empty");
            latestDate.setText("This album is empty");
        }



        scrollPane.setContent(photoCollection);




    }

    /**
     *
     * @param imageView
     * @param caption
     * @param imageFile
     * @return
     */
    private Label create_captioned_image(ImageView imageView, String caption, File imageFile) {
        Label ret = new Label(caption);
        ret.setPadding(new Insets(5, 5, 5, 5));
        ret.setGraphic(
                imageView
        );
        ret.setWrapText(true);
        ret.setContentDisplay(ContentDisplay.TOP);
        ret.setGraphicTextGap(20);

        return ret;
    }

    /**
     *
     * @param imageFile
     * @return
     */
    private ImageView create_image_view(final File imageFile) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 100, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(100);
        }catch(IOException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void createAlbum() throws IOException, ClassNotFoundException {
        System.out.println(CurrentUser.getAlbums());
        boolean notContain =true;
        albums = CurrentUser.getAlbums();
        for(int i =0; i < albums.size();i++){
            if(albums.get(i).getAlbumName().equals(newAlbumName.getText())){
                System.out.println("Same name");
                notContain= false;
            }
        }
        if(notContain){
            currentAlbum.setAlbumName(newAlbumName.getText());
            System.out.println("sdgds"+CurrentUser.getAlbums());
            CurrentUser.addAlbum(currentAlbum);
            albumUsers.addUsers(CurrentUser);
            AlbumUsers.writeUsers(albumUsers);
            createButton.setDisable(true);
            newAlbumName.setDisable(true);



        }

    }

    /**
     *
     * @throws IOException
     */
    public void back() throws IOException {

        System.out.println(CurrentUser.getUsernname());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoUser.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        UserController ctrl = loader.getController();
        ctrl.getuser(CurrentUser.getUsernname(),stage);
        stage.show();
        stage.setTitle("User View");
        Stage stg = (Stage) createButton.getScene().getWindow();
        stg.close();



    }

    /**
     *
     */
    public void quit(){
        Stage stg = (Stage) createButton.getScene().getWindow();
        stg.close();
    }
}

package controller;
import Model.Album;
import Model.AlbumUsers;
import Model.User;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import Model.Photos;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Optional;








    public class AlbumViewController {
        private String username;
        private Album album1;
        private User user;

        private AlbumUsers users;
        private ArrayList<User> userList;
        private ArrayList<Album> albums;

        @FXML
        private VBox vBox;
        @FXML private Button backButton;
        @FXML private Button quitButton;
        @FXML private Button logoutButtton;



        //private BorderPane borderPane;

        @FXML
        private TilePane photoCollection;

        @FXML
        private ScrollPane scrollPane;

        @FXML
        private Label earliestDate, latestDate, albumSize;

    public void set_user_album(Album album, User user1, String username) throws IOException, ClassNotFoundException {
        this.album1 = album;
        this.user = user1;
        this.username = username;
        System.out.println(this.album1 + ""+  this.user + ""+   this.username);


       //setup scrollpane
        scrollPane.setStyle("-fx-background-color: DAE6F3;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(false);
        photoCollection.setPadding(new Insets(15, 15, 15, 15));
        photoCollection.setHgap(15);
        photoCollection.setVgap(15);
        photoCollection.setTileAlignment(Pos.TOP_LEFT);
        photoCollection.setPrefColumns(1);

        users = AlbumUsers.readUsers();
        System.out.println("1 "+users.getUsers());
        System.out.println("4 " + user.getAlbums());
            userList = users.getUsers();

        for(int i = 0;i< userList.size();i++){
            if(userList.get(i).getUsernname().equals(username)){
                user = userList.get(i);
                users.getUsers().remove(i);
                System.out.println("2 "+users.getUsers());
            }
        }

        albums = user.getAlbums();
        for(int i = 0;i<albums.size();i++){
            if(albums.get(i).getAlbumName().trim().equals(album.getAlbumName())){
                album1 = albums.get(i);
                user.getAlbums().remove(i);
            }
        }

        System.out.println("5 " + user.getAlbums());


        for(Photos p : album.getListofphotos()){
            ImageView imageView;
            imageView = createImageview(p.getPhotoFile());
            Label labelImage = createImage(imageView,p.getPhotoFile(),p.getCaption());
            photoCollection.getChildren().addAll(labelImage);
        }

        if(album.getListofphotos().size()>0){
            albumSize.setText(album.getListofphotos().size() + "Photos");
            earliestDate.setText(album.getEarly().getDate());
            latestDate.setText(album.getOld().getDate());

        }else {
            albumSize.setText("0");
            earliestDate.setText("");
            latestDate.setText("");

        }

        scrollPane.setContent(photoCollection);

    }
    public Label createImage(ImageView imageView,File file,String caption) throws IOException{
        Label labelView  = new Label(caption);
        labelView.setPadding(new Insets(0));
        labelView.setGraphic(imageView);
        labelView.setWrapText(true);
        labelView.setContentDisplay(ContentDisplay.TOP);
        labelView.setGraphicTextGap(15);

        DropShadow dropShadow = new DropShadow(20,Color.AQUA);

        labelView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageView imageView;

               if(event.getButton().equals(MouseButton.PRIMARY)){
                   if(event.getClickCount()==2){
                            System.out.println("Double clicked");
                       FXMLLoader clickloader = new FXMLLoader(getClass().getResource("/View/photoClick.fxml"));
                       Parent root = null;
                       try {
                           root = (Parent) clickloader.load();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       Scene scene = new Scene(root);
                       Stage clickStage = new Stage();
                       clickStage.setScene(scene);
                       photoClickController ctrl = clickloader.getController();
                       ctrl.getUser(user,album1);
                       clickStage.setTitle("Photo View");
                       clickStage.show();
                       Stage stage = (Stage) quitButton.getScene().getWindow();
                       stage.close();


                   }
                   else if(event.getClickCount()==1){

                       int index = -1;
                       for(int i = 0;i<album1.getListofphotos().size();i++){
                           if(file.getAbsolutePath().equals(album1.getListofphotos().get(i).getPhotoFile().getAbsolutePath())){
                               index = i;

                           }
                       }

                       Label temp = (Label) photoCollection.getChildren().get(index);
                       if(temp.getEffect()==null){
                           temp.setEffect(dropShadow);
                       }
                       else {
                           temp.setEffect(null);
                       }
                        photoCollection.getChildren().set(index,temp);
                   }
               }

            }
        });



        return labelView;
    }

    public ImageView createImageview(File file) throws FileNotFoundException {

        ImageView imageView = null;
        Image image = new Image(new FileInputStream(file),100,0,true,true);
        imageView = new ImageView(image);
        imageView.setFitWidth(100);
        return imageView ;
    }

    public void back() throws IOException, ClassNotFoundException {
        System.out.println("3 "+user.getAlbums());

        user.getAlbums().add(album1);
        users.getUsers().add(user);
        AlbumUsers.writeUsers(users);
        System.out.println("6 " + user.getAlbums());
        FXMLLoader backLoader = new FXMLLoader(getClass().getResource("/View/photoUser.fxml"));
        Parent root = (Parent)backLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("User View");
        stage.setResizable(false);
        stage.setScene(scene);
        UserController ctrl = backLoader.getController();
        ctrl.getuser(username);

        stage.show();

      Stage closes = (Stage)  backButton.getScene().getWindow();
        closes.close();


    }
    public void add_photo() throws IOException {

        FileChooser fileChooser =  new FileChooser();
        fileChooser.setTitle("Choose Picture");

          fileChooser.getExtensionFilters().addAll(
                  new FileChooser.ExtensionFilter("JPG File","*.jpg"),
                  new FileChooser.ExtensionFilter("PNG File","*png")
          );


          File f = fileChooser.showOpenDialog( backButton.getScene().getWindow());

        if(f==null){
            return;
        }
        else {
            for(Photos p : album1.getListofphotos()){
                if(p.getPhotoFile().equals(f)){
                    return;
                }
            }
        }
        album1.addPhoto((new Photos(f)));
        ImageView imageView;
        imageView = createImageview(f);
        Label newLabel = createImage(imageView,f,"No caption");
        if(album1.getListofphotos().size()>0){
            albumSize.setText(album1.getListofphotos().size()+"Photos");
            earliestDate.setText(album1.getEarly().getDate());
            latestDate.setText(album1.getOld().getDate());
        }
        else
        {
            albumSize.setText("0");
            earliestDate.setText("");
            latestDate.setText("");
        }


        photoCollection.getChildren().addAll(newLabel);

    }
    public void delete_photo() {
        int counter = 0;

        System.out.println("9 " + photoCollection.getChildren().size());
        if (photoCollection.getChildren().size() > 0) {
                for (int i = 0; i < photoCollection.getChildren().size(); i++) {
                    Label temp = (Label) photoCollection.getChildren().get(i);
                    if (temp.getEffect() != null) {
                        counter++;
                    } else {
                        System.out.println("effect null");
                    }
            }
        }else {
                System.out.println("size is 0");
            }
            System.out.println("counter is " + counter);
        if(counter!=0){
            Alert delAlert = new Alert(AlertType.CONFIRMATION);
            delAlert.setTitle("Delete?");
            Optional<ButtonType> result = delAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                while (counter >= 0) {
                    for (int i = 0; i < photoCollection.getChildren().size(); i++) {
                        Label temp = (Label) photoCollection.getChildren().get(i);
                        if (temp.getEffect() != null) {
                            album1.getListofphotos().remove(i);
                            photoCollection.getChildren().remove(i);
                        }
                    }
                    counter--;
                }
            }
            else {
                for (int i = 0; i < photoCollection.getChildren().size(); i++) {
                    Label temp = (Label) photoCollection.getChildren().get(i);
                    temp.setEffect(null);
                }

            }
        }
    }

    public void quit() throws IOException, ClassNotFoundException {
        user.getAlbums().add(album1);
        users.getUsers().add(user);
        AlbumUsers.writeUsers(users);
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        }
    public void log_out() throws IOException, ClassNotFoundException {
        user.getAlbums().add(album1);
        users.getUsers().add(user);
        AlbumUsers.writeUsers(users);
        FXMLLoader logout = new FXMLLoader(getClass().getResource("/View/photoLogin.fxml"));
        Parent root = (Parent) logout.load();
        Scene scene = new Scene(root);
        Stage logoutStage = new Stage();
        logoutStage.setScene(scene);
        logoutStage.setResizable(false);
        logoutStage.setTitle("Login View");
        logoutStage.show();

        Stage stage = (Stage) logoutButtton.getScene().getWindow();
        stage.close();
    }
    public void slideshow() throws IOException, ClassNotFoundException {
        user.getAlbums().add(album1);
        users.getUsers().add(user);
        AlbumUsers.writeUsers(users);

        if(album1.getListofphotos().size()>0) {

            //send the album

            FXMLLoader slideLoader = new FXMLLoader(getClass().getResource("/View/photoSlideShow.fxml"));
            Parent root = (Parent) slideLoader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.setTitle("SlideShow View");
            stg.setResizable(false);
            SlideShowController ctrl = slideLoader.getController();
            ctrl.getAlbum(album1);
            stg.setScene(scene);
            stg.show();
            Stage closes = (Stage) logoutButtton.getScene().getWindow();
            closes.close();
        }
        else
        {
            Alert nopics = new Alert(AlertType.ERROR);
            nopics.setTitle("Empty Album");
            nopics.setContentText("Empty Album");
            nopics.showAndWait();

        }
    }





}












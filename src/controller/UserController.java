package controller;

import Model.Album;
import Model.AlbumUsers;
import Model.Tag;
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
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;




public class UserController{

    //global variables
    private Stage stage;

    //assign variables to button
   @FXML private Button quitButton;
   @FXML private Button logoutButton;
   @FXML private Button createButton;
    @FXML private Button removeButton;
   @FXML private Button renameButton;
   @FXML private Button openAlbumButton;
  @FXML  private Button addTagButton;
   @FXML private Button deletetagButton;
   @FXML private ListView<Album> albumListview;

   @FXML private TextField albumName;
   @FXML private TextField tagName;
    @FXML private TextField tagValue;

    @FXML private ListView<Tag> searchTagList;
    private ObservableList<Tag> tagList;
    private  ArrayList<Tag> tags;

   private ObservableList<Album> albumobs;
   ;
   private ArrayList<Album> albumList;
   private List<String> albumNameList;
    private String userin;
    private AlbumUsers albumusers;
    private User currentUser;
    private Stage stg;
    private Album send;
//    public void initialize(URL location, ResourceBundle resources) {
//
//       System.out.println("1");
//        try {
//            albumusers = AlbumUsers.readUsers();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Username = initializer " + userin);
//
//        for(User u : albumusers.getUsers()){
//            if(u.getUsernname().equals(userin));
//            currentUser = u;
//          albumList = u.getAlbums();
//
//
//        }
//       // System.out.println("list = "+albumList);
//        albumobs = FXCollections.observableList(albumList);
//        albumListview.setItems(albumobs);
//
//    }


    /********************************************************************************                                                                             *
        * implement the code in rename() which prevent the user to enter duplicate names *
        * also implement the code in create() which prevent duplicate names
     *    Also implement Tags button *
     * ******************************************************************************/



public void getuser(String username){


        this.userin = username;



    System.out.println("1");
        try {
            albumusers = AlbumUsers.readUsers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Username = initializer " + userin);

        for(User u : albumusers.getUsers()){
            //System.out.println("users = " + u.getUsernname());

            if(u.getUsernname().equals(userin)){
                //System.out.println("Matched user is " + u.getUsernname());
                currentUser = u;
                albumList = u.getAlbums();
            }


        }
        System.out.println("Album list is " + albumList);
        //System.out.println("CurrentUser is  " + currentUser.getUsernname());
       // System.out.println("list = "+albumList);
        albumobs = FXCollections.observableList(albumList);
        albumListview.setItems(albumobs);
    tags = new ArrayList<Tag>();
    tagList = FXCollections.observableArrayList(tags);


    searchTagList.setItems(tagList);

    searchTagList.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
        public ListCell<Tag> call(ListView<Tag> param) {
            final ListCell<Tag> cell = new ListCell<Tag>() {
                @Override
                public void updateItem(Tag tag, boolean empty) {

                    super.updateItem(tag, empty);
                    if (tag == null) {
                        setText(null);
                    }else {
                        setText(tag.getName() + ": " + tag.getValue());
                    }
                }
            };
            return cell;
        }
    });


//Determines if input file is empty, if not, selects first item
    if(!albumobs.isEmpty()) {
        albumListview.getSelectionModel().select(0);
        displayItem();
    }
    // set listener for the items
    albumListview
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> displayItem());

    tags = new ArrayList<Tag>();
    tagList = FXCollections.observableArrayList(tags);


    searchTagList.setItems(tagList);

    searchTagList.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>() {
        public ListCell<Tag> call(ListView<Tag> param) {
            final ListCell<Tag> cell = new ListCell<Tag>() {
                @Override
                public void updateItem(Tag tag, boolean empty) {

                    super.updateItem(tag, empty);
                    if (tag == null) {
                        setText(null);
                    }else {
                        setText(tag.getName() + ": " + tag.getName());
                    }
                }
            };
            return cell;
        }
    });



    //Determines if input file is empty, if not, selects first item
    if(!tagList.isEmpty()) {
        searchTagList.getSelectionModel().select(0);
        displayItem();
    }
    // set listener for the items
    searchTagList
            .getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> displayItem());







}
   public void quit(){
        //terminate the program

     Stage stg=  (Stage)quitButton.getScene().getWindow();
       stg.close();


   }
   public void logout() throws IOException{
        //go to loginpage
       FXMLLoader logout = new FXMLLoader(getClass().getResource("/View/photoLogin.fxml"));
       Parent root = (Parent) logout.load();
       Scene scene = new Scene(root);
       Stage stage = new Stage();
       stage.setTitle("Login");
       stage.setResizable(false);
       stage.setScene(scene);
       stage.show();
       stg = (Stage) logoutButton.getScene().getWindow();
       stg.close();



   }



   //********************NEED TO IMPLEMENT************
   public void search(){
        //search photos using tags or dates
   }

   public void create() throws IOException, ClassNotFoundException {
        //create album
       //add to albumuser
       //get albumname
           if(!albumName.getText().isEmpty()){
           String input = albumName.getText();

           Album newAlbum = new Album(input);
           currentUser.addAlbum(newAlbum);
           //albumList.add(newAlbum);

           albumobs = FXCollections.observableList(currentUser.getAlbums());
           albumListview.setItems(albumobs);
           AlbumUsers.writeUsers(albumusers);
           albumName.setText("");

           System.out.println("Current user in create() = " + currentUser.getAlbums());
       }
       else {
               //Alert: field is empty
           }







//      albumList.add(newalbum);
//      albumobs = FXCollections.observableList(albumList);
//      albumListview.setItems(albumobs);
   }
   public void remove() throws IOException, ClassNotFoundException {
        //remove the album
       Alert removealert = new Alert(Alert.AlertType.CONFIRMATION);
       removealert.setTitle("Delete Album");
       removealert.setContentText("Are you sure?");
       Optional<ButtonType> result = removealert.showAndWait();
       if(result.get() == ButtonType.OK) {
           int index = albumListview.getSelectionModel().getSelectedIndex();

           currentUser.removeAlbum(albumList.get(index));
           albumobs = FXCollections.observableList(currentUser.getAlbums());
           albumListview.setItems(albumobs);
           AlbumUsers.writeUsers(albumusers);
       }

   }
   public void rename() throws IOException, ClassNotFoundException {
    int index = albumListview.getSelectionModel().getSelectedIndex();
    String oldname =  albumList.get(index).getAlbumName();
    String newName = albumName.getText();

       if(oldname.equals(newName)){
           Alert same = new Alert(Alert.AlertType.ERROR);
           same.setContentText("Same Album Name");
           same.setTitle("Error");
           same.showAndWait();
           albumName.setText("");

       }else if (!oldname.equals(newName) && !newName.isEmpty()){
           Alert newnamealert = new Alert(Alert.AlertType.CONFIRMATION);
           Optional<ButtonType> result = newnamealert.showAndWait();
           if(result.get() == ButtonType.OK) {
               albumList.get(index).setAlbumName(newName);
               albumobs = FXCollections.observableList(currentUser.getAlbums());
               albumListview.setItems(albumobs);
               AlbumUsers.writeUsers(albumusers);
               albumName.setText("");
           }
       }
       else{}
   }
   public void openAlbum() throws IOException, ClassNotFoundException {
        //got to another view called photoOpenAlbum.fxml

        int index = albumListview.getSelectionModel().getSelectedIndex();
        send = albumList.get(index);
       FXMLLoader openloader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
       Parent root = (Parent) openloader.load();
       Scene scene = new Scene(root);
       Stage openStage = new Stage();
       openStage.setScene(scene);
       AlbumViewController ctrl = openloader.getController();

       ctrl.set_user_album(send,currentUser,userin);
       openStage.setTitle("Album view");
       openStage.setResizable(false);
        stage = (Stage) openAlbumButton.getScene().getWindow();
        stage.close();
       openStage.show();

   }

   public void addTags(){
       String value = tagValue.getText();
       String name = tagName.getText();
       Tag addedTag = new Tag(name, value);
       for(int i = 0; i < tagList.size(); i++) {
           if(tagList.get(i).getName().equals(name) && tagList.get(i).getValue().equals(value)) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Dialog");
               alert.setHeaderText("Improper Input");
               alert.setContentText("This tag already exists in the tag library, input new Tag");
               alert.showAndWait();
               tagName.setText("");
               tagValue.setText("");
               return;
           }
       }


       if(value.equals("") || name.equals("")) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Dialog");
           alert.setHeaderText("Improper Input");
           alert.setContentText("Cannot add blank tag!");
           alert.showAndWait();
           tagName.setText("");
           tagValue.setText("");
           return;
       }else{
           tagList.add(addedTag);
           searchTagList.getSelectionModel().select(tagList.size());
           tagName.setText("");
           tagValue.setText("");
       }


   }
    private void displayItem() {

        if(albumListview.getSelectionModel().getSelectedItem()!=null) {
            Album selected = albumListview.getSelectionModel().getSelectedItem();

        }else {
            return;
        }
    }


}

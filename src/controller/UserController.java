package controller;

import Model.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


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
    @FXML private DatePicker earlyDate;
    @FXML private DatePicker latestDate;

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
    private Album album;


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

private Stage curStage;

    public void getuser(String username,Stage stage){

        this.curStage = stage;
        this.userin = username;
       // System.out.println("usercontroller username " + username);



        System.out.println("1");
        try {
            albumusers = AlbumUsers.readUsers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //System.out.println("Username = initializer " + userin);

        for(User u : albumusers.getUsers()){
            //System.out.println("users = " + u.getUsernname());

            if(u.getUsernname().equals(userin)){
                //System.out.println("Matched user is " + u.getUsernname());
                currentUser = u;
                albumList = u.getAlbums();
            }


        }
       // System.out.println("Album list is " + albumList);
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
    public void search() throws ParseException, IOException, ClassNotFoundException {
        //search photos using tags or dates
        List<Photos> search = new ArrayList<>();
        Album albumResult = new Album();

        //get the input from date
        //get input from tag

        if((earlyDate.getValue()!=null && latestDate.getValue()!=null) && (tagName.getText()==null && tagValue.getText()==null) ) {
            System.out.println("dates are not null");

            for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {
                    LocalDate d = currentUser.getAlbums().get(i).getListofphotos().get(j).getDate_time().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if((earlyDate.getValue().isBefore(d) && latestDate.getValue().isAfter(d)) || (earlyDate.getValue().equals(d) && latestDate.getValue().equals(d))){
                       // System.out.println(currentUser.getAlbums().get(i).getListofphotos().get(j).getPhotoFile());

                        search.add(currentUser.getAlbums().get(i).getListofphotos().get(j));

                            albumResult.addPhoto(currentUser.getAlbums().get(i).getListofphotos().get(j));


                    }

                }
            }
           System.out.println(search.size());
            System.out.println(albumResult.getListofphotos() );
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SearchResult.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stagea = new Stage();
            stagea.setTitle("Search Results");
            stagea.setScene(scene);
            stagea.setResizable(false);
            stagea.show();
            SearchController ctrl = loader.getController();
            ctrl.getData(search,currentUser);
            Stage stg = (Stage) logoutButton.getScene().getWindow();
            stg.close();

        }
        else if((earlyDate.getValue()==null && latestDate.getValue()==null) && (tagName.getText()!=null && tagValue.getText()!=null)){
            System.out.println("search using tags");
            int index = searchTagList.getSelectionModel().getSelectedIndex();
           Tag tg  = tagList.get(index);

            System.out.println(tg.getName() + "" +  tg.getValue());




            for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {



                        if(currentUser.getAlbums().get(i).getListofphotos().get(j).tagexist(tg)){
                            if(search.size()==0) {
                                search.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                            }
                            else
                            {

                                if(search.contains(currentUser.getAlbums().get(i).getListofphotos().get(j))){

                                }
                                else
                                {
                                    search.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                }
                            }
                        }

                }
            }
          //  System.out.println(search);//DONE1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SearchResult.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stagea = new Stage();
            stagea.setTitle("Search Results");
            stagea.setScene(scene);
            stagea.setResizable(false);
            stagea.show();
            SearchController ctrl = loader.getController();
            ctrl.getData(search,currentUser);
            Stage stg = (Stage) logoutButton.getScene().getWindow();
            stg.close();
        }
        else
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error!");
            error.setContentText("Search using date Range OR Tags");
            error.showAndWait();
            tagValue.setText("");
            tagName.setText("");
        }
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

        if(albumListview.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Improper Selection");
            alert.setContentText("There are no Albums in the list to be opened!");

            alert.showAndWait();
            return;
        }
        int openIndex = albumListview.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Open Confirmation Dialog");
        alert.setContentText("Click OK if you are sure you want to open the selected Album");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
           // try {
                System.out.println("openAlbum()" + albumusers.getUsers());

              //  albumusers.getUsers().add(currentUser);
               // AlbumUsers.writeUsers(albumusers);
            //}catch(IOException | ClassNotFoundException e) {
           //     e.printStackTrace();
          //  }

            try {
              Album  Curralbum = currentUser.getAlbums().get(openIndex);

                FXMLLoader photoLoader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
                Parent root = (Parent)photoLoader.load();
                Scene photoScene = new Scene(root);
                Stage photoStage = new Stage();
                photoStage.setScene(photoScene);
                photoStage.setResizable(true);
                photoStage.setTitle(Curralbum.getAlbumName()+"'s"+" Photo Collection");
                AlbumViewController cntrl = photoLoader.getController();
                cntrl.set_user_album(Curralbum, currentUser, userin);

                stage = (Stage)albumListview.getScene().getWindow();
                stage.close();
                photoStage.show();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
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

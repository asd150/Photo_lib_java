package controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    @FXML private ListView<Tag> searchTagList;
    @FXML private TextField tagArea;
    @FXML private TextField tagValArea;
    private ObservableList<Tag> tagList;
    private  ArrayList<Tag> tags;
    private ArrayList<User> userArrayList;

    private Album moveTo;
    private Album copyTo;
    private Stage thisstage;







    public void set_user_album(Album album,User user, boolean search) throws IOException, ClassNotFoundException {
          System.out.println("++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("set-user-album");
//        System.out.println("Received album " + album.getAlbumName());
//        System.out.println(" recived User" + user.getUsernname());
//        System.out.println("user has albums  " + user.getAlbums() );


        albumusers = AlbumUsers.readUsers();
        userArrayList = albumusers.getUsers();
        System.out.println("reading all users" + albumusers.getUsers());

        currentUser =user;
        currentAlbum = album;
        currName = currentUser.getUsernname();
    }
    public void set_stage(Stage stage, ImageView imageView, File imageFile) throws IOException, ClassNotFoundException {
    thisstage = stage;
        imageViewer.setImage(imageView.getImage());
        imageViewer.setPreserveRatio(true);
         System.out.println("set_stage" );

        for (int i = 0; i < currentAlbum.getListofphotos().size(); i++) {
            if (imageFile.equals(currentAlbum.getListofphotos().get(i).getPhotoFile())) {
                currentPic = currentAlbum.getListofphotos().get(i);
            }
        }

        System.out.println("rec current pic " + currentPic.getCaption());
//System.out.println("initital users " + userArrayList);
//System.out.println("initial albumusers" + albumusers.getUsers());

        for (int i = 0; i < userArrayList.size(); i++) {
            if (userArrayList.get(i).getUsernname().equals(currentUser.getUsernname())) {
               // userArrayList.remove(i);
                albumusers.getUsers().remove(i);
                //System.out.println("Current user removed"+albumusers.getUsers().get(i).getUsernname());
            }
            //System.out.println(albumusers.getUsers().get(i).getUsernname());
        }
       // System.out.println("after del initital users " + userArrayList);
        System.out.println("initial albumusers" + albumusers.getUsers());

        for (int i = 0; i < currentUser.getAlbums().size(); i++) {
            if (currentUser.getAlbums().get(i).getAlbumName().equals(currentAlbum.getAlbumName())) {
                currentUser.removeAlbum(currentAlbum);
            }
        }

        for (int i = 0; i < currentAlbum.getListofphotos().size(); i++) {
            if (currentAlbum.getListofphotos().get(i).getPhotoFile().equals(currentPic.getPhotoFile())) {
                currentAlbum.removePhoto(currentPic);
            }
        }
        if (currentPic.getCaption() != null) {
            captionLabel.setText(currentPic.getCaption());

        }
        dateLabel.setText(currentPic.getDate());


        tags = (ArrayList<Tag>) currentPic.getTags();
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
                        } else {
                            setText(tag.getName() + ": " + tag.getValue());
                        }
                    }
                };
                return cell;
            }
        });


        //Determines if input file is empty, if not, selects first item
        if (!tagList.isEmpty()) {
            searchTagList.getSelectionModel().select(0);
            if (searchTagList.getSelectionModel().getSelectedItem() != null) {
                Tag selected = searchTagList.getSelectionModel().getSelectedItem();
            }
            // set listener for the items
            searchTagList
                    .getSelectionModel()
                    .selectedItemProperty()
                    .addListener((obs, oldVal, newVal) -> displayItem());


        }
    }
    private void displayItem() {

        if(searchTagList.getSelectionModel().getSelectedItem()!=null) {
            Tag selected = searchTagList.getSelectionModel().getSelectedItem();

        }else {
            return;
        }
    }
    public void caption(){
        System.out.println("in back" + albumusers.getUsers());
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
    public void addTag(){
        String name = tagArea.getText().trim();
        String value = tagValArea.getText().trim();
        Tag addedTag = new Tag(name, value);

        if(name.equals("") || value.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Improper Input");
            alert.setContentText("Cannot add blank tag!");
            alert.showAndWait();
            return;
        }else if(currentPic.tagexist(addedTag)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Improper Input");
            alert.setContentText("This tag already exists in the tag library, input new Tag");
            alert.showAndWait();
            return;
        }

        Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);
        alertConf.setTitle("Confirmation Dialog");
        alertConf.setHeaderText("Add Confirmation Dialog");
        alertConf.setContentText("Click OK if you are sure you want to add the tag?");

        Optional<ButtonType> result = alertConf.showAndWait();

        if(result.get() == ButtonType.OK) {

            currentPic.addTag(addedTag);
            tagList.add(addedTag);
            searchTagList.getSelectionModel().select(tagList.size());
            tagValArea.setText("");
            tagArea.setText("");
        }
    }
    public void deletetag(){
        if(searchTagList.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Improper Selection");
            alert.setContentText("There are no Tags in the list to be deleted!");

            alert.showAndWait();
            return;
        }

        int deleteIndex = searchTagList.getSelectionModel().getSelectedIndex();
        int listSize = tagList.size();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Confirmation Dialog");
        alert.setContentText("Click OK if you are sure you want to delete the selected tag");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {

            Tag delTag = currentPic.getTags().get(deleteIndex);
            currentPic.removeTag(delTag);

            tagList.remove(deleteIndex);
            if(tagList.isEmpty()) {

            }else if((deleteIndex+1) == listSize) {
                searchTagList.getSelectionModel().select(deleteIndex--);
            }else {
                searchTagList.getSelectionModel().select(deleteIndex++);
            }
        }


        displayItem();

    }

    public void move() throws IOException, ClassNotFoundException {
//            System.out.println("current pic caption" + currentPic.getCaption());
//            System.out.println("Current pic tags" + currentPic.getTags().size());
//            System.out.println("Albumusers" +albumusers.getUsers());
//
//            System.out.println(currentUser);
//            System.out.println(currentUser.getAlbums());
//
//            System.out.println(currentAlbum.getListofphotos().size());
//            System.out.println(currentAlbum);
//            System.out.println(currentPic.getPhotoFile());

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
                       moveTo = currentUser.getAlbums().get(i);
                    }
                }
                // System.out.println("move() " + currentUser.getAlbums());
                currentUser.removeAlbum(moveTo);
                moveTo.addPhoto(currentPic);
                currentUser.addAlbum(moveTo);
                currentUser.addAlbum(currentAlbum);

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
                backmove();
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







    }

    public void copy(){



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
                 System.out.println("move() " + currentUser.getAlbums());
//                currentUser.removeAlbum(copyTo);
//                copyTo.addPhoto(currentPic);
//                currentUser.addAlbum(copyTo);



                albumArea.setText("");


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

    }
    public void back() throws IOException, ClassNotFoundException {

        currentAlbum.addPhoto(currentPic);
        currentUser.addAlbum(currentAlbum);
        albumusers.addUsers(currentUser);



        System.out.println(currentAlbum.getListofphotos().size());

        AlbumUsers.writeUsers(albumusers);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stg = new Stage();
        AlbumViewController ctrl = loader.getController();
        ctrl.set_user_album(currentAlbum,currentUser,currName);
        ctrl.setStage(thisstage);
        stg.show();
        stg.setScene(scene);
        stg.setTitle("Album View");
        Stage close = (Stage) quitButton.getScene().getWindow();
        close.close();

    }
public void backmove() throws IOException, ClassNotFoundException {
    System.out.println("in back" + albumusers.getUsers());
    albumusers.addUsers(currentUser);
    AlbumUsers.writeUsers(albumusers);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoOpenAlbum.fxml"));
    Parent root = (Parent) loader.load();
    Scene scene = new Scene(root);
    Stage stg = new Stage();
    AlbumViewController ctrl = loader.getController();
    ctrl.set_user_album(currentAlbum,currentUser,currName);
    ctrl.setStage(thisstage);
    stg.show();
    stg.setScene(scene);
    stg.setTitle("Album View");
    Stage close = (Stage) quitButton.getScene().getWindow();
    close.close();
//
//
//
//    System.out.println("in back 2" + albumusers.getUsers());









}
    public void quit(){
        currentAlbum.addPhoto(currentPic);
        currentUser.addAlbum(currentAlbum);
        albumusers.addUsers(currentUser);
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
    public void logOut() throws IOException {
        currentAlbum.addPhoto(currentPic);
        currentUser.addAlbum(currentAlbum);
        albumusers.addUsers(currentUser);
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


}

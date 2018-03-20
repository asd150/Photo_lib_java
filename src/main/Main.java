package main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception, EOFException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/photoLogin.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 600));
        AlbumUsers list = new AlbumUsers();
        list = AlbumUsers.readUsers();
        String userdir = System.getProperty("user.dir");
        String path = userdir+"/stock/";

        //if list is empty add admin
        //else continue
        if(list.getUsers().size()==0){
            User adminuser = new User("admin");
            list.addUsers(adminuser);
            AlbumUsers.writeUsers(list);
            primaryStage.show();
        }
        else{
//            User a = new User("a");
//            Album album = new Album();
//            File pics = new File("C:\\Users\\desai\\Desktop\\RU\\spring18\\softMeth\\photos43\\pic1.jpg");
//            Photos mypic = new Photos(pics);
//            album.addPhoto(mypic);
//            album.setAlbumName("My first album");
//            Tag myTag = new Tag("tag1","tagval");
//            mypic.addTag(myTag);
//
//
//            a.addAlbum(album);
//            list.addUsers(a);
//            AlbumUsers.writeUsers(list);
            primaryStage.show();

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

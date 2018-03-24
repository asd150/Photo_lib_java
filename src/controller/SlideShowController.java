package controller;

import Model.Album;

import Model.Photos;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * ${2 Player Chess }
 *
 * @author Arthkumar Desai
 * @author Roman Stashchyshyn
 */
public class SlideShowController {

    private Album albumrec;

    private  Photos currentpic;
    private boolean firstSetup = true;
    @FXML private ImageView view;
    @FXML private AnchorPane anchorPane;

    public void getAlbum(Album album) throws FileNotFoundException {
        this.albumrec = album;

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

    }
    public void logOut(){

    }
    public void back(){

    }

}

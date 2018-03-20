package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable

{

    private String usernname ;
    //user has list of album
    private ArrayList<Album> albums;
    //user is following other user
    private ArrayList<User> following;

    public User() {
        albums = new ArrayList<>();
        following =  new ArrayList<>();
    }

    public User(String username){
        albums = new ArrayList<>();
        following = new ArrayList<>();
        this.usernname=username;
}

    public ArrayList<User> getFollowing() {
        return following;
    }

    public String getUsernname() {
        return usernname;
    }

    public void setUsernname(String usernname) {
        this.usernname = usernname;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album alb){
        this.albums.add(alb);
    }


    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setFollowing(ArrayList<User> following) {

        this.following = following;
    }

    public void removeAlbum(Album album){
        albums.remove(album);
    }

    public Album getAlbumName(String name){
        for(Album ab : albums){
            if(name.equals(ab.getAlbumName())){
                return ab;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getUsernname();
    }
}

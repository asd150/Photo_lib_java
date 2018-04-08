package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Album implements Serializable {


    private String albumName;
    private List<Photos> listofphotos = null;
    private int sizes =0;
    private double dates;
    private Photos old;
    private Photos early;



    public Album(){
        listofphotos = new ArrayList<Photos>();
    }
    public  Album(String albumName){
        this.albumName = albumName;
        listofphotos = new ArrayList<Photos>();
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Photos> getListofphotos() {
        return listofphotos;
    }

    public void setListofphotos(List<Photos> photos){
        listofphotos = photos;
        sizes = photos.size();
       sorter();

        this.set_early(this.listofphotos.get(0));
        this.set_old(this.listofphotos.get(this.sizes-1));

    }
    public void addPhoto(Photos photos){
        this.listofphotos.add(photos);
        sizes++;
        sorter();
        this.set_early(this.listofphotos.get(0));

        this.set_old(this.listofphotos.get(this.sizes-1));
    }

    public void removePhoto(Photos photos){
        this.listofphotos.remove(photos);
        sizes--;
        sorter();

    }
    public boolean photoExists(Photos photo){
        return listofphotos.contains(photo);
    }

    public void set_early(Photos early){
        if(early!=null){
            this.early = early;
        }
    }
    public void set_old(Photos old){
        if(old!=null){
            this.old = old;
        }
    }

    public Photos getEarly(){
        return early;
    }

    public Photos getOld() {
        return old;
    }


    public Photos prevPhoto(Photos p){
        if(p==null){
            return null;

        }
        if(sizes<2){
            return p;
        }

        for(Photos photos : listofphotos){
            if(p.getPhotoFile().equals(photos.getPhotoFile())){
                if(listofphotos.indexOf(photos)!=0){
                    return listofphotos.get(listofphotos.indexOf(photos) -1);
                }
            }
        }
        return p;
    }

    public Photos nextPhoto(Photos p){
        if(p==null){
            return null;
        }
        if(sizes<2){
            return p;
        }

        for(Photos photos : listofphotos){
            if(p.getPhotoFile().equals(photos.getPhotoFile())){
                if(listofphotos.indexOf(photos)!= listofphotos.size()-1){
                    return listofphotos.get(listofphotos.indexOf(photos)+1);
                }
            }
        }
        return p;

    }



    public void sorter(){
        Collections.sort(listofphotos,(o1, o2) -> o1.getDate().compareTo(o2.getDate()));


    }


    public String getDates(){
        if(this.sizes>0){
            return this.getOld() + " - " + this.getEarly();
        }
        return "Empty Album";
    }

    @Override
    public String toString() {
        return getAlbumName();
    }

    public void setDates(double dates){
        this.dates=dates;
    }

}

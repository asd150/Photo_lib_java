package Model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Photos implements Serializable {



    private List<Tag> tags;
    private Calendar date_time;
    private File photoFile;
    private List<Album> parentAlb;
    private String caption;

    public Photos(File photoFile){
        parentAlb = new ArrayList<Album>();
        tags = new ArrayList<>();
        this.photoFile = photoFile;
        date_time = Calendar.getInstance();
        date_time.set(Calendar.MILLISECOND,0);
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public void removeTag(Tag tag){
        tags.remove(tag);
    }
    public void addTag(Tag tag){
        tags.add(tag);
    }
    public boolean tagexist(Tag tag){
        for(Tag tg : tags){
            if(tg.equals(tag)){
                return true;
            }
        }
        return false;
    }

    public Calendar getDate_time() {
        return date_time;
    }

    public void setDate_time(Calendar date_time) {
        this.date_time = date_time;
    }

    public List<Album> getParentAlb() {
        return parentAlb;
    }

    public void setParentAlb(List<Album> parentAlb) {
        this.parentAlb = parentAlb;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addParentAlb(Album alb){
        parentAlb.add(alb);
    }

    public void removeParentAlb(Album alb){
        parentAlb.remove(alb);

    }

    public boolean existingAlb(Album alb){
        if(parentAlb.contains(alb)){
            return true;
        }
        else {
            return false;
        }
    }

    public String getDate(){
        String[] arrTime = date_time.getTime().toString().split("\\s+");
        return arrTime[0] + "," + arrTime[1] + "," + arrTime[2];
    }

}

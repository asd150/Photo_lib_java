package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {

    private String name;
    private String value;

    private ArrayList<Tag> taglist;

    public Tag(String tagname,String tagValue){
        this.name = tagname;
        this.value = tagValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public boolean equals(Tag tag){
        if(tag==null){
            return false;
        }
        return tag.getName().trim().equals(this.name) && tag.getValue().trim().equals(this.value);
    }
}

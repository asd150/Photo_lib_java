package Model;


import java.io.*;
import java.util.ArrayList;

public class AlbumUsers implements Serializable {

    private ArrayList<User> users;

    private static final long serialVersionUID = 1L;


    static String userDir = System.getProperty("user.dir");
    public static String userListFile = userDir+"/users.dat";

    static File file = new File(userListFile);


    public AlbumUsers(){
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    public void addUsers(User user){
        System.out.println("Album User ="+ user);
        users.add(user);
    }

    public void removeUsers(User user){

        users.remove(user);
    }



    public User getUserByUname(String givenUname){
        for (User u : users){
            if(givenUname.trim().equals(u.getUsernname())){
                return u;
            }
        }
        return null;
    }

    public boolean UserExists(String givenUname) {
        System.out.println("GivenUName = " + givenUname);
        for (User u : users) {
            if (givenUname.trim().equals(u.getUsernname())) {
                return true;
            }

        }
            return false;
    }

    public boolean UsernameTaken(String givenUsername){
        for(User u : users){
            if(givenUsername.trim().equals(u.getUsernname())){
                return true;
            }
        }
        return false;
    }



    public static AlbumUsers readUsers() throws IOException,ClassNotFoundException{
        AlbumUsers usersavailable = null;
       file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
       ObjectInputStream ois = new ObjectInputStream(fis);
       usersavailable =(AlbumUsers) ois.readObject();
       ois.close();
        return usersavailable;
    }

    public static void writeUsers(AlbumUsers albumUsers) throws IOException,ClassNotFoundException{
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(albumUsers);
        oos.close();
    }

    @Override
    public String toString() {
        String userLisr = " ";
        if(users == null){
            return "No Users Exists";

        }
        else {
            for(User user : users){
                return userLisr + user.getUsernname() + "\n";
            }
        }
        return userLisr;
    }


}

package ret.novelly.novelly;

import java.util.UUID;

/**
 * Created by ret70 on 10/13/2015.
 */
public class Pastes {

    private String title;
    private String paste;
    private String pasteID;
    private String storyID;
    private String userID;


    Pastes()
    {

    }
    Pastes(String title, String paste)
    {
        this.title= title;
        this.paste=paste;

    }
    void setUserID(String userID){

        this.userID= userID;
    }

    String getUserID()
    {
        return userID;
    }

    void setID(String pasteID, String storyID){
        this.pasteID=pasteID;
        this.storyID = storyID;

    }
    void setTitle(String t)
    {
        title=t;
    }

    void setUserPaste(String s)
    {
        paste=s;
    }

    String getID()
    {
        return pasteID;
    }
    String getTitle()
    {
        return title;
    }
    String getUserPaste()
    {
        return paste;
    }

}

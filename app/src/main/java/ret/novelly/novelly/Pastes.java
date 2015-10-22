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


    Pastes() {

    }

    Pastes(String pasteID, String storyID,String userID,String paste,String title) {
        this.pasteID=pasteID;
        this.storyID=storyID;
        this.userID=userID;
        this.title = title;
        this.paste = paste;

    }

    void setUserID(String userID) {

        this.userID = userID;
    }

    String getUserID() {
        return userID;
    }

    void setID(String pasteid) {
        this.pasteID = pasteid;
    }

    void setTitle(String t) {
        title = t;
    }

    void setStoryID(String s)
    {
        storyID=s;
    }
    void setUserPaste(String s) {
        paste = s;
    }

    String getStoryID() {
        return storyID;
    }

    String getID() {
        return pasteID;
    }

    String getTitle() {
        return title;
    }

    String getUserPaste() {
        return paste;
    }

}

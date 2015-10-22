package ret.novelly.novelly;


import java.util.UUID;

public class Vote {

    private String title;
    private String paste;
    private String pasteID;
    private String storyID;
    private String userID;
    private String voteID;


    Vote() {

    }

    Vote(String pasteID, String storyID, String userID) {
        this.title = title;
        this.paste = paste;
        this.userID = userID;
        voteID= UUID.randomUUID().toString();

    }

    void setUserID(String userID) {

        this.userID = userID;
    }

    String getUserID() {
        return userID;
    }

    void setID(String id) {
        this.voteID = voteID;
    }

    void setTitle(String t) {
        title = t;
    }

    void setStoryID(String s) {
        storyID = s;
    }

    void setUserPaste(String s) {
        paste = s;
    }

    String getStoryID() {
        return storyID;
    }

    String getID() {
        return voteID;
    }

    String getTitle() {
        return title;
    }

    String getPasteID() {
        return pasteID;
    }

    String getUserPaste() {
        return paste;
    }

}

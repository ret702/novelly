package ret.novelly.novelly;


import java.util.UUID;

public class Vote {


    private String pasteID;
    private String storyID;
    private String userID;
    private String voteID;


    Vote() {

    }

    Vote(String pasteID, String storyID, String userID) {

        this.pasteID = pasteID;
        this.userID = userID;
        this.storyID=storyID;
       this.voteID= UUID.randomUUID().toString();

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



    void setStoryID(String s) {
        storyID = s;
    }



    String getStoryID() {
        return storyID;
    }

    String getID() {
        return voteID;
    }


    String getPasteID() {
        return pasteID;
    }


}

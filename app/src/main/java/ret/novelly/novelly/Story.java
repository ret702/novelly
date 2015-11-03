package ret.novelly.novelly;


import java.util.UUID;

/**
 * Created by ret70 on 8/28/2015.
 */
public class Story {

    private String title;
    private String userStory;
    private String storyID;
    private String userID;

    Story() {

    }

    Story( String userID,String story,String title) {

        this.storyID=UUID.randomUUID().toString();
        this.userID=userID;
        this.title = title;
        this.userStory = story;
    }


    void setID(String id) {
        this.storyID = id;
    }

    void setTitle(String t) {
        title = t;
    }

    void setUserID(String id) {
        this.userID = id;
    }

    void setUserStory(String s) {
        userStory = s;
    }


    String getUserID() {
        return userID;
    }

    String getID() {
        return storyID;
    }

    String getTitle() {
        return title;
    }

    String getUserStory() {
        return userStory;
    }
}

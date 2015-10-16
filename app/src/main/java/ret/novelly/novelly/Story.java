package ret.novelly.novelly;


import java.util.UUID;

/**
 * Created by ret70 on 8/28/2015.
 */
public class Story {

   private String title;
    private String userStory;
    private String id;

    Story()
    {

    }
    Story(String title, String userStory)
    {
        this.title= title;
        this.userStory=userStory;

    }

    void setID(String id){
        this.id=id;

    }
    void setTitle(String t)
    {
        title=t;
    }

    void setUserStory(String s)
    {
        userStory=s;
    }

    String getID()
    {
        return id;
    }
    String getTitle()
    {
        return title;
    }
    String getUserStory()
    {
        return userStory;
    }
}

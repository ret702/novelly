package ret.novelly.novelly;


import java.util.UUID;

/**
 * Created by ret70 on 8/28/2015.
 */
public class Story {

   private String title;
    private String userStory;
    private UUID id;

    Story()
    {

    }
    Story(String title, String userStory)
    {
        this.title= title;
        this.userStory=userStory;

    }

    void setID(UUID id){
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

    UUID getID()
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

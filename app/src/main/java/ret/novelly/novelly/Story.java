package ret.novelly.novelly;



/**
 * Created by ret70 on 8/28/2015.
 */
public class Story {

    String title;
    String userStory;
    int id;

    Story()
    {

    }
    Story(String title, String userStory)
    {
        this.title= title;
        this.userStory=userStory;

    }

    void setID(int id){
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

    int getID()
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

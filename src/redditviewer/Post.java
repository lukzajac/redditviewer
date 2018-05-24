package redditviewer;

public class Post {
    private String title;
    private String selftext;
    private String link;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTitle() {
        return title;
    }
    public String getSelftext() {
        return selftext;
    }
    public String getLink() {
        return link;
    }

    public Post(String title) {
        this.title = title;
    }

}

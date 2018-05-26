package redditviewer;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Post {
    private String title;
    private int score;
    private boolean over18;
    private String subreddit;
    private boolean self;
    private String selftext;
    private String selftextHtml;
    private String thumbnailLink;
    private String link;
    private LocalDateTime creationTime;

    public String getTitle() {
        return title;
    }
    public int getScore() {
        return score;
    }
    public boolean isOver18() {
        return over18;
    }
    public String getSubreddit() {
        return subreddit;
    }
    public boolean isSelf() {
        return self;
    }
    public String getSelftext() {
        return selftext;
    }
    public String getSelftextHtml() {
        return selftextHtml;
    }
    public String getThumbnailLink() {
        return thumbnailLink;
    }
    public String getLink() {
        return link;
    }
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Post(JsonNode postRoot) {

        title = postRoot.get("title").asText();
        score = postRoot.get("score").asInt();
        over18 = postRoot.get("over_18").asBoolean();
        subreddit = postRoot.get("subreddit").asText();
        self = postRoot.get("is_self").asBoolean();
        selftext = postRoot.get("selftext").asText();
        //TODO: replace deprecated function
        selftextHtml = StringEscapeUtils.unescapeHtml4(postRoot.get("selftext_html").asText());
        thumbnailLink = postRoot.get("thumbnail").asText();
        link = postRoot.get("url").asText();

        int creationTimeFromEpoch = postRoot.get("created_utc").asInt();
        creationTime = LocalDateTime.ofEpochSecond(creationTimeFromEpoch, 0, ZoneOffset.UTC);
    }

}

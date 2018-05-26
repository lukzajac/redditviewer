package redditviewer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class Loader {
    private String subreddit;
    private String userAgent;
    private ObjectMapper mapper;
    private String sortMode;

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Loader() {
        mapper = new ObjectMapper();
        userAgent = "Default User Agent";
    }

    public ObservableList<Post> getPosts() throws IOException {
        HttpsURLConnection connection = null;
        try {
            connection = createConnection();
            JsonNode treeRoot = mapper.readTree(createConnection().getInputStream());
            return parseTree(treeRoot);
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    private HttpsURLConnection createConnection() throws IOException {
        URL url = createURL();
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.addRequestProperty("User-Agent", userAgent);
        connection.setRequestMethod("GET");
        return connection;
    }

    private URL createURL() throws MalformedURLException {
        String urlTemplate = "https://reddit.com/r/%s/%s.json";
        return new URL(String.format(urlTemplate, subreddit, sortMode));
    }

    private ObservableList<Post> parseTree(JsonNode root) {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        JsonNode data = root.get("data").get("children");
        Iterator<JsonNode> it = data.elements();
        while (it.hasNext()) {
            JsonNode postData = it.next().get("data");
            posts.add(new Post(postData));
        }
        return posts;
    }
}

package redditviewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UiController implements Initializable {
    private Loader loader;
    private ObservableList<Post> posts;
    private ObservableList<String> modeList;

    @FXML private TextField subredditField;
    @FXML private ComboBox<String> sortModeSelection;
    @FXML private ListView<Post> postTitles;
    @FXML private Text postTitle;
    @FXML private WebView postContents;

    private WebEngine postContentsEngine;

    public UiController() {
        loader = new Loader();
        postTitles = new ListView<Post>();
        postContents = new WebView();
        sortModeSelection = new ComboBox<String>();
        modeList = FXCollections.observableArrayList(
                "top", "best", "hot", "new", "rising", "controversial", "gilded"
        );

    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        postTitles.setCellFactory(listView -> new PostListCell());
        postTitles.getSelectionModel().selectedItemProperty().addListener((observableValue, post, t1) -> {
            if(t1 != null)
                displayPost(t1);
        });

        postContentsEngine = postContents.getEngine();

        sortModeSelection.setItems(modeList);
        sortModeSelection.getSelectionModel().selectFirst();
        selectSortMode();

    }

    @FXML private void loadSubreddit() {
        if(!subredditField.getText().isEmpty())
        {
            try {
                loader.setSubreddit(subredditField.getText());
                posts = loader.getPosts();
                displaySubreddit();
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void displaySubreddit() {
        postTitles.setItems(posts);
    }

    private void displayPost(Post post) {
        if(post.isSelf())
            postContentsEngine.loadContent(post.getSelftextHtml());
        else
            postContentsEngine.load(post.getLink());

        postTitle.setText(post.getTitle());
    }

    @FXML private void selectSortMode() {
        loader.setSortMode(sortModeSelection.getValue());
    }
}

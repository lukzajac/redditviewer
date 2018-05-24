package redditviewer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UiController implements Initializable {
    private Loader loader;
    private ObservableList<Post> posts;

    @FXML private TextField subredditField;
    @FXML private ListView<Post> postTitles;
    @FXML private Text postTitle;
    @FXML private TextArea postContents;

    public UiController() {
        loader = new Loader();
        postTitles = new ListView<Post>();
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        //TODO: tidy it up
        postTitles.setCellFactory(listView -> new PostListCell());
        postTitles.getSelectionModel().selectedItemProperty().addListener((observableValue, post, t1) -> {
            if(t1 != null) {
                postContents.setText(t1.getSelftext());
                postTitle.setText(t1.getTitle());
            }
        });
    }

    @FXML private void loadSubreddit(ActionEvent event) {
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
}

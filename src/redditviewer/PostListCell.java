package redditviewer;

import javafx.scene.control.ListCell;

public class PostListCell extends ListCell<Post> {
    @Override protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(String.format("%d | %s", post.getScore(), post.getTitle()));
            setGraphic(null);
        }
    }
}

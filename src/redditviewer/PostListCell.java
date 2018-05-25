package redditviewer;

import javafx.scene.control.ListCell;

public class PostListCell extends ListCell<Post> {
    @Override protected void updateItem(Post item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getTitle());
            setGraphic(null);
        }
    }
}

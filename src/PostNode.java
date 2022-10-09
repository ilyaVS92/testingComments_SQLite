import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostNode {
    private int _id;
    private int date;
    private String postText;
    private String postAuthor;
    private ArrayList<Comment> comments;
    private ArrayList<tobeDone> toDoItems;
//    private ArrayList<> updateHistoryForPost;


    public PostNode(int _id, String postText, int date, String postAuthor, ArrayList<Comment> comments) {
        this.date = date;
        this._id = _id;
        this.postText = postText;
        this.postAuthor = postAuthor;
        this.comments = comments;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getDate() {
        return date;
    }
    public String getPostAuthor(){
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor){
        this.postAuthor = postAuthor;
    }
    public void setDate(int date) {
        this.date = date;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}

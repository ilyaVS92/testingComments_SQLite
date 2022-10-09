import java.util.Date;

public class Comment {
//    private int comment_id;
    private String comment_text;
    private String comment_date;
    private String comment_author;
//    private ArrayList<> updateHistoryForPost;

//    private int comment_parent;
    //                SELECT "+comment_date+", "+usr_alias+", "+comment_text+"

    public Comment(String comment_text, String comment_date, String comment_author) {
//        this.comment_id = comment_id;
        this.comment_text = comment_text;
        this.comment_date = comment_date;
        this.comment_author = comment_author;
//        this.comment_parent = comment_parent;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

//    public int getComment_parent() {
//        return comment_parent;
//    }
//
//    public void setComment_parent(int comment_parent) {
//        this.comment_parent = comment_parent;
//    }
}

public class tobeDone {
    private String dateDue;
    private String dateCreated;
    private int _id;
    private int post_id;
    private String text;

    public tobeDone(String dateDue, String dateCreated, int _id, int post_id, String text) {
        this.dateDue = dateDue;
        this.dateCreated = dateCreated;
        this._id = _id;
        this.post_id = post_id;
        this.text = text;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

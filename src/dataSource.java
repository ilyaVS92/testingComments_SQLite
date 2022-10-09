import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//TODO:
// 0. add crud functions (14-09-2022);
// 1. search post by keyword;
// 2. select individual posts(projects)
// 3. delete/modify comments in post and write changes to db
// 4. add calendar integration for visual checks that something was done

public enum dataSource {
    INSTANCE;

    enum SortOrder{
        PostDate_ASC("ORDER BY post.date ASC"),
        PostDate_DSC("ORDER BY post.date ASC"),
        CommentDate_ASC("ORDER BY comments.date ASC"),
        CommentDate_DSC("ORDER BY comments.date DSC"),
        NONE("");

        String str;

        SortOrder(String s) {
//            this.s = s;
        }
    }
    enum SearchType{
        //could probably just set the search type to avoid extra input from the user
    }

    private Connection connTest;

    public static final String DB_NAME = "testComments.db";
    public static final String CONNECTION_STR = "jdbc:sqlite:C:\\Users\\Taurus\\Applications\\sqLite\\test_databases\\" + DB_NAME;

    ///COMMON STRINGS:
    public static final String ID = "_id";
    public static final String USR_ID = "usr_id";
    public static final String DATE = "date";
    public static final String POST_ID = "post_id";
    public static final String COMMENT_TEXT = "comment";
    public static final String LESSON = "lesson";
    public static final String PROBLEM_ID = "problem_id";
    public static final String POST_TEXT = "post_text";
    public static final String ITEM_TEXT = "item_text";
    public static final String DATE_CREATED = "date_created";
    public static final String DATE_DUE = "date_due";
    public static final String FIRSTNAME = "first_name";
    public static final String LASTNAME = "last_name";
    public static final String USERNAME = "usr_name";

    ///TABLE STRINGS:
    public static final String commentsTable = "comments";
    public static final String postTable = "posts";
    public static final String usersTable = "users";
    public static final String toBeDoneTable = "toBeDone";
    public static final String problemsTable = "problems";
    public static final String lessonsTable = "lessons";

    ///COLUMN STRINGS WITH "." REFERENCES
    public static final String dotUserID = "users._id";
    public static final String dotUserFirstName = "users.first_name";
    public static final String dotUserLastName = "users.last_name";
    public static final String dotUserAlias = "users.usr_name";

    public static final String dotCommentID = "comments._id";
    public static final String dotCommentUsrID = "comments.usr_id";
    public static final String dotCommentText = "comments.comment";
    public static final String dotCommentDate = "comments.date";
    public static final String dotCommentPostID = "comments.post_id";

    public static final String dotPostID = "posts._id";
    public static final String dotPostText = "posts.post_text";
    public static final String dotPostAuthor = "posts.usr_id";
    public static final String dotPostDate = "posts.date";

    public static final String toBeDone_id = "toBeDone._id";
    public static final String toBeDone_text = "toBeDone.item_text";
    public static final String toBeDone_dateMk = "toBeDone.date_created";
    public static final String toBeDone_postID = "toBeDone.post_id";
    public static final String toBeDone_dateDue = "toBeDone.date_due";

    public static final String problems_id = "problems._id";
    public static final String problems_text = "problems.problem";
    public static final String problems_authorID = "problems.usr_id";
    public static final String problems_postID = "problems.post_id";
    public static final String problems_date = "problems.date";

    public static final String lessons_id = "lessons._id";
    public static final String lessons_text = "lessons.lesson";
    public static final String lessons_authorID = "lessons.usr_id";
    public static final String lessons_date = "lessons.date";
    public static final String lessons_problemID = "lessons.problem_id";


    ///GENERAL USE STRINGS
    public static final String failure = "FAILURE: ";
    public static final String success = "SUCCESS: ";

    ///INSERT STRINGS
    public static final String insertPost = "INSERT INTO "+postTable+" ("+POST_TEXT+", "+USR_ID+", "+DATE+") VALUES (?, ?, ?)";
    PreparedStatement insertPost_prep;
    public static final String insertComment = "INSERT INTO "+commentsTable+" ("+ USR_ID +","+ COMMENT_TEXT +","+ DATE +","+ POST_ID +") VALUES (?,?,?,?)";
    PreparedStatement insertComment_prep;
    ///SEARCH STRINGS
    //SELECT comments.comment, comments.date, users.usr_name FROM comments INNER JOIN users ON comments.usr_id=users._id WHERE comments.post_id=1;
    public static final String showCommentsForPost = "SELECT "+ dotCommentDate +", "+ dotUserAlias +", "+ dotCommentText +" FROM "+commentsTable+" INNER JOIN "+usersTable+" ON "+ dotCommentUsrID +"="+ dotUserID +" WHERE "+ dotCommentPostID +"=?";
    PreparedStatement showCommentsForPost_prep;

    //SELECT * FROM posts WHERE post_text LIKE "%post%"
    public static final String queryPostsByText = "SELECT "+ ID +" FROM "+postTable+" WHERE "+POST_TEXT+" LIKE ?";
//    public static final String searchPosts = "SELECT _id FROM posts WHERE post_text LIKE "+"'%'"+" ? "+"'%'";//"+"\""+"%?%"+"\"";
    PreparedStatement queryPostsByText_prep;

//    public static final String showPosts = "SELECT "+post_id+", "+post_text+", "+post_date+", "+post_authorID+" FROM "+postTable;
//    public static final String queryPostByID = "SELECT "+ID+", "+POST_TEXT+", "+DATE+" FROM "+postTable+" WHERE "+ID+"=?";
//    public static final String queryPostByID = "SELECT _id, post_text, date FROM posts WHERE ID = ?";//works
    public static final String queryPostByID = "SELECT "+ID+", "+POST_TEXT+", "+DATE+" FROM posts WHERE "+ID+" = ?";//works


    //    public static final String showPostsForID = "SELECT "+post_text+", "+post_date+","+post_authorID+" FROM "+postTable+" WHERE _id= ?";
    PreparedStatement queryPostByID_prep;

    public static final String countPosts = "SELECT COUNT() FROM posts";

//    public static final String RECORD_UPDATE = "INSERT INTO UpdateHistory (post_reference INTEGER, comment_reference INTEGER DEFAULT 0, usr_id INTEGER,date TEXT)";
    //post-reference = which post is getting updated; comment-ref = which comment is getting updated; user_id = who is doing the update; date = when the update was done

    public static final String UPDATE_POST_TEXT = "UPDATE "+postTable+" SET "+ POST_TEXT +"= ? WHERE "+ID+"=?";
    PreparedStatement updatePostText_prep;
    public static final String UPDATE_COMMENT_TEXT = "UPDATE "+commentsTable+" SET "+ COMMENT_TEXT +"= ? WHERE "+ID+"= ?";
    PreparedStatement updateCommentText_prep;

//    public static final String UPDATE_COMMENT_USER = "UPDATE "+commentsTable+" SET "+usr_id+"= ? WHERE _id= ?";//is this even a good idea?? maybe we have an author and responsible party


    enum searchType {
        GLOBAL(1), PREFIX(2), SUFFIX(3), EXACT(4);
        int type;
        searchType(int type){
            this.type = type;
        }
    }

    ////////////////////////////////////////////CONNECTION////////////////////////////////////////////
    public boolean open() {
        try {
            connTest = DriverManager.getConnection(CONNECTION_STR);

            insertPost_prep = connTest.prepareStatement(insertPost);
            insertComment_prep = connTest.prepareStatement(insertComment);

            showCommentsForPost_prep = connTest.prepareStatement(showCommentsForPost);

            queryPostsByText_prep = connTest.prepareStatement(queryPostsByText);


            updatePostText_prep = connTest.prepareStatement(UPDATE_POST_TEXT); //throws error
            updateCommentText_prep = connTest.prepareStatement(UPDATE_COMMENT_TEXT); //throws error
//
            queryPostByID_prep = connTest.prepareStatement(queryPostByID); //throws error
//


            System.out.println(success + "CONNECTION - OPENED");
            return true;
        } catch (SQLException sqlE) {
            System.out.println(failure + "unable to open prepared statements in open(): " + sqlE.getMessage());
            return false;
        }
    }

    public boolean close() {
        if (updateCommentText_prep !=null){
            try {
                updateCommentText_prep.close();
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());;
            }
        }
        if (updatePostText_prep !=null){
            try {
                updatePostText_prep.close();
            } catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());;
            }
        }
        if (insertPost_prep != null){
            try {
                insertPost_prep.close();
            } catch (SQLException sqlE){
                System.out.println(sqlE.getMessage());
            }
        }

        if (queryPostsByText_prep != null){
            try{
                queryPostsByText_prep.close();
            } catch (SQLException sqlE) {
                System.out.println("unable to close searchPost_prep");
            }
        }
        if (showCommentsForPost_prep !=null){
            try{
                showCommentsForPost_prep.close();
            } catch (SQLException sqlE){
                System.out.println("unable to close prep_statement_01");
            }
        }
        if (queryPostByID_prep !=null){
            try{
                queryPostByID_prep.close();
            } catch (SQLException sqlE){
                System.out.println("unable to close prep_statement_02");
            }
        }
        if (connTest != null) {
            try {
                connTest.close();
                System.out.println(success+"CONNECTION - CLOSED");
                return true;

            } catch (SQLException sqlE) {
                System.out.println(failure+"CONNECTION - unable to close: " + sqlE.getMessage());
                return false;
            }
        } else {
            System.out.println(failure+"CONNECTION ALREADY CLOSED");
            return true;
        }
    }
    ////////////////////////////////////////////INSTANCE////////////////////////////////////////////
    public static dataSource getInstance(){
        return INSTANCE;
    }

    ////////////////////////////////////////////CRUD FUNCTIONS////////////////////////////////////////////
    //CRUD - POSTS//

    /////////////////CREATE
    public int addPost(String postText, String author, String date) throws SQLException {
//        ArrayList<Integer> matchingPosts = findPostID_FromDB(postText,matchType.EXACT);
//        if (matchingPosts==null) { //checking if post already exits
        connTest.setAutoCommit(false);
        try {
            insertPost_prep.setString(1, postText);
            insertPost_prep.setString(2, author);
            insertPost_prep.setString(3, getTimeStamp(date));

            int affectedRows = insertPost_prep.executeUpdate();
            if (affectedRows == 1) {
                connTest.commit();
                ResultSet generatedKeys = insertPost_prep.getGeneratedKeys();
                System.out.println("returning _id for newly generated Post");
                return generatedKeys.getInt(1);
            } else {
                connTest.rollback();
                throw new SQLException("Something went wrong, .rollback() called");
            }
        } catch (SQLException sqlE) {
            System.out.println(sqlE.getMessage());
        } finally {
            connTest.setAutoCommit(true);
        }
        return -1;
    }

    public boolean editSinglePost(int postNum, String updatedText, int usrID){
        //get post object
//        PostNode localNode = getInstance().getPostObj(postNum);
//        if (localNode!=null) {
//            System.out.println(localNode.getPostText());
            try {
                connTest.setAutoCommit(false);
                updatePostText_prep.setString(1,updatedText);
                updatePostText_prep.setInt(2,postNum);

                int affectedRows = updatePostText_prep.executeUpdate();
                if (affectedRows==1){
                    connTest.commit();
                    System.out.println(success+"updated post text!");
                } else {
                    connTest.rollback();
                    System.out.println("called rollback on "+getClass().getSimpleName());
                }
                return true;
            } catch (SQLException sqlE){
                System.out.println(sqlE.getMessage());
            } finally{
                try{
                    connTest.setAutoCommit(true);
                } catch (SQLException autoComm){
                    System.out.println(autoComm.getMessage());
                }
            }
            System.out.println("post update failed!");
            return false;
        }
        //edit required field
        //    public static final String UPDATE_POST_TEXT = "UPDATE "+postTable+" SET "+post_text+"= ? WHERE "+ID+"=?";
        //write post obj to database?? UPDATE posts SET post_text="find a good place to drink coffee near the library" WHERE _id=5;
//        return false;
//    }
    public boolean deletePost(int postNum){
        //
        return false;
    }

    public ArrayList<Integer> findPostID_FromDB(String searchStr, searchType st){ //returns a list of matches FROM the DB
        ArrayList<Integer> postSearchResults = new ArrayList<>();
        try {
            switch (st){
                case EXACT -> queryPostsByText_prep.setString(1,searchStr);
                case GLOBAL -> queryPostsByText_prep.setString(1,"%"+searchStr+"%");
                case PREFIX -> queryPostsByText_prep.setString(1,"%"+searchStr);
                case SUFFIX -> queryPostsByText_prep.setString(1,searchStr+"%");
            }
            ResultSet results = queryPostsByText_prep.executeQuery();

            while (results.next()){
                postSearchResults.add(results.getInt(1));
            }
            return postSearchResults;
        } catch (SQLException sqlE){
            System.out.println(failure+"unable to get POST_id for "+searchStr+" search term");
        }
        return null;
    }
    //CRUD - COMMENTS//
    public void addComment(int usrID, String comment, int postNodeNum, String timeStamp, boolean commitChanges) throws SQLException { //create comment
        // include auto-generated date and time with local timezone offset.
        //"+usrID+","+comment+","+date+","+postID+") VALUES (?,?,?,?)
        connTest.setAutoCommit(false);
        try {
            insertComment_prep.setInt(1, usrID);
            insertComment_prep.setString(2, comment);
            insertComment_prep.setString(3,getTimeStamp(timeStamp));
            insertComment_prep.setInt(4, postNodeNum);

            //check how many rows were changed and execute update
            int affectedRows = insertComment_prep.executeUpdate();
            if (affectedRows == 1){
                try {
                    if (!commitChanges){
                        connTest.rollback();
                        System.out.println(success+" this was a test - rolling back changes");
                    } else {
                        System.out.println(success+" comment written to DB");
                        connTest.commit();
                    }
                } catch (SQLException sqlE) {
                    try {
                        connTest.rollback();
                        System.out.println(failure + sqlE.getMessage());
                    } catch (SQLException sqlE1){
                        System.out.println("unable to call rollback: "+sqlE1);
                    }
                }
            }

        } catch (SQLException sqlE) {
            System.out.println("unable to set prepared statement for comment");
        } finally {
            try {
                connTest.setAutoCommit(true);
            } catch (SQLException sqlException){
                System.out.println(sqlException.getMessage());
            }
        }
    }
    public PostNode getPostObj(int postNumber){ //retrieve specific POST
        try{
            queryPostByID_prep.setInt(1, postNumber);
            ResultSet results = queryPostByID_prep.executeQuery();
            //SELECT "+post_id+", "+post_text+", "+post_date+, +post_author
            return new PostNode(
                    results.getInt(1),
                    results.getString(2),
                    results.getInt(3),
                    results.getString(4),
                    getCommentsForPost(postNumber,SortOrder.CommentDate_ASC));
        } catch (SQLException sqlE){
            System.out.println(failure+"unable to get POST; "+sqlE.getMessage());
        }
        return null;
    }
    public ArrayList<Comment> getCommentsForPost(int postNodeNum, SortOrder so){//read comment
        try{
            showCommentsForPost_prep.setString(1, String.valueOf(postNodeNum));
            ResultSet results = showCommentsForPost_prep.executeQuery();
            ArrayList<Comment> comments = new ArrayList<>();
            System.out.println("Retrieving comments; created arraylist for comments");
            System.out.println(showCommentsForPost_prep.toString());
            while (results.next()){
//                SELECT "+comment_date+", "+usr_alias+", "+comment_text+"
//                int comment_id, String comment_text, Date comment_date, String comment_author, int comment_parent
//                String comment_text, String comment_date, String comment_author
                comments.add(new Comment(
//                        results.getInt(comment_id),
//                        results.getString(comment_text),
//                        results.getString(comment_date),
//                        results.getString(comment_authorID)
                        results.getString(3),
                        results.getString(1),
                        results.getString(2)
//                        results.getInt(comment_parentID)
                        )
                );
                System.out.println("Added comment to arraylist");
            }
            return comments;
        } catch (SQLException sqlE){
            System.out.println(failure+"IN getCommentsForPost: "+sqlE.getMessage());
        }
        System.out.println("No results found in COMMENTS");
        return null;
    }
    public void editSingleComment(){//modify comment

    }
    public void deleteComment(){//delete comment

    }

    ////////////////////////////////////////////PROCESS RECEIVED INFO////////////////////////////////////////////
    public void processDate(){
        //input is date in unix format, and we want to display this in normal ways
    }
    public void encodeDate(){
        //accept a date entered and return something that will be OK for the db
    }
    public String getTimeStamp(String timeStamp){ //returns timestamp in string format based on system time
        if (!timeStamp.equals("")){
            return timeStamp;
        } else {
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            String text = date.format(formatter);

            LocalDateTime localTime = LocalDateTime.now();
            DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("HH:mm");
            String timeText = localTime.format(timeForm);

            OffsetTime offset = OffsetTime.now();
            DateTimeFormatter offsetForm = DateTimeFormatter.ofPattern("x");
            String offsetL = offset.format(offsetForm);

            LocalTime parsedTime = LocalTime.parse(timeText, timeForm);
            LocalDate parsedDate = LocalDate.parse(text, formatter);

            return parsedDate + " " + offsetL + " " + parsedTime;
        }
    }
}

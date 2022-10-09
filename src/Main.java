import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //Primary GOAL: test out how to display a tree view of comments that are children of posts;
    //=>import list to hashmap, probably based on the parent post id
    //Secondary GOAL: CRUD operations
    public static void main(String[] args) throws SQLException {
        dataSource.getInstance().open();

        System.out.println(dataSource.getInstance().editSinglePost(5,getStringFromUser(),13));

//        printPostsFromSearch(findPostID_FromDB());
//        dataSource.getInstance().addPost("get a good java (meaning coffee) grinder","isa","");

//        findPostID_FromDB();
//        dataSource.getInstance().getCommentsForPost()

        dataSource.getInstance().close();

    }
    public static void printPostsFromSearch(ArrayList<Integer> results){
        for (Integer postID : results) {
            PostNode post = dataSource.getInstance().getPostObj(postID);

            System.out.println("POST ID: " + post.get_id() + "| POST TXT: " + post.getPostText() + "| postDate: " + post.getDate());
            int i = 1;
            for (Comment c : post.getComments()) {
                System.out.println("---> COMMENT " + i + ": " + c.getComment_text() + "| dated:" + c.getComment_date() + "| author: " + c.getComment_author());
                i++;
            }
        }
    }
    public static ArrayList<Integer> findPostID(){
        /////////////////START [>] DISPLAY SEARCH RESULTS - searching for all POSTS that contain a string searchTerm
        //I'm looking for the index of the postnode or postnodes that contain the search term
        String searchTerm = getStringFromUser();
        ArrayList<Integer> searchResults = dataSource.getInstance().findPostID_FromDB(searchTerm, dataSource.searchType.GLOBAL);
        if (searchResults!=null) {
            System.out.println("--"+searchResults);
            for (Integer i : searchResults) {
                System.out.println(i);
//                PostNode post = dataSource.getInstance().getPostObj(searchResults.get(i));
//                assert post != null;
//                System.out.println("POST ID: "+post.get_id()+"| POST TXT: "+post.getPostText()+"| postDate: "+ post.getDate());
            }
            System.out.println("for loop finished");
        } else {
            System.out.println("SearchResults are null");
        }
        /////////////////END [X] DISPLAY SEARCH RESULTS - searching for all POSTS that contain a string searchTerm
        return searchResults;
    }

    public static void addComment() throws SQLException {
        String commnt = getStringFromUser();
        int postNo = getIntFromUser();
        dataSource.getInstance().addComment(13,commnt,postNo,"",true); //could probably use the builder pattern here
    }

    public static String getStringFromUser(){
        System.out.println("Enter input to pass to DB:");

        Scanner scanner = new Scanner(System.in);
        String queryFromConsole = "";

        if(scanner.hasNextLine()) {
            queryFromConsole = scanner.nextLine();
        }
        scanner.close();
        return queryFromConsole;
    }
    public static int getIntFromUser(){
        System.out.println("Enter input to pass to DB:");

        Scanner scanner = new Scanner(System.in);
        int queryFromConsole = 0;

        if(scanner.hasNextInt()) {
            queryFromConsole = scanner.nextInt();
        }
        scanner.close();
        return queryFromConsole;
    }
}

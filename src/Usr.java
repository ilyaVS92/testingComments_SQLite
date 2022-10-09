public class Usr {
    //we can add privelages later.
    //only being used to get the name of the person making the modification
    //not being implemented in test runs.
    private int userID;
    private String userFirstName;
    private String userLastName;
    private String userHandle;
    private int grubPriv;

    public Usr (int userID, String userFirstName, String userLastName, String userHandle, int grubPriv){
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userHandle = userHandle;
        this.grubPriv = grubPriv;
    }
}

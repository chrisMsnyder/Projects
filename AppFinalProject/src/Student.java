/**
 * The student object, stores relevant info about students
 *
 * @author Chris Snyder
 */

public class Student
{
    private String password;
    private int bannerId;

    /**
     * Constructor
     *
     * @param password Students password
     * @param bannerId Students id
     */
    public Student(String password, int bannerId)
    {
        this.password = password;
        this.bannerId = bannerId;
    }

    /**
     * gets the password
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password
     *
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the banner id
     *
     * @returns the banner id
     */
    public int getBannerId() {
        return bannerId;
    }

    /**
     * sets the banner id
     *
     * @param bannerId sets the new banner id
     */
    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }
}

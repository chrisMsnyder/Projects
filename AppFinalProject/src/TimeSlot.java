import java.util.ArrayList;

/**
 * The timeslot object, stores relevants info about the time slots
 *
 * @author Chris Snyder
 */
public class TimeSlot
{
	private int hour;
	private int minute;
	private String stop;
	private int offset;
	private ArrayList<Student> students;
	private int studentLimit = 70;

	/**
	 * Constructor
	 *
	 * @param iHour the timeslots hour
	 * @param iMinute the timeslots minute
	 * @param stop the name of the stop
	 */
	public TimeSlot(int iHour, int iMinute, String stop)
	{
		this.setHour(iHour);
		this.setMinute(iMinute);
		this.setStop(stop);
		this.students = new ArrayList<Student>();
	}

	/**
	 *  Returns the timeslot in string form
	 *
	 * @return string time
	 */
	public String toString()
	{
		if (this.getMinute() < 10)
			return this.getHour() + ":0" + this.getMinute();
		else
			return this.getHour() + ":" + this.getMinute();
			
	}


	/**
	 * gets the hour
	 * @return hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * sets the hour
	 * @param hour hour
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * get the minute
	 * @return int minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * sets the minutes
	 * @param minute int minute
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * get the stop
	 * @return String stop
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * sets the Stop
	 * @param stop String stop
	 */
	public void setStop(String stop)
	{
		this.stop = stop;
	}

	/**
	 * get the student array list
	 * @return the student array list
	 */
	public ArrayList<Student> getStudents()
	{
		return students;
	}

}

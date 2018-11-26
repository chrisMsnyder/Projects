import java.util.ArrayList;

/**
 * Route object, stores relevent info about the routes as well
 * as allowing students to be added to it, and print
 *
 * @author Chris Snyder
 */
public class Route
{
	private ArrayList<TimeSlot> timeSlots;
	private ArrayList<BusStop> busStops;
	private String name;
	private TimeSlot start;
	private TimeSlot end;
	private int studentLimit;

	/**
	 * Constructor
	 *
	 * @param szName name of the route
	 * @param start start time
	 * @param end end time
	 */
	public Route(String szName, TimeSlot start, TimeSlot end)
	{
		this.setTimeSlots(new ArrayList<TimeSlot>());
		this.setBusStops( new ArrayList<BusStop>());
		//this.setStudents( new ArrayList<>());
		this.setName(szName);
		this.setStart(start);
		this.setEnd(end);
		this.studentLimit = 70;
	}


	/**
	 * adds a new bus stop
	 * @param name name of the stop
	 * @param offset time offest for the next stop
	 */
	public void addBusStop(String name, int offset)
	{
		BusStop stop = new BusStop(name, offset);
		this.getBusStops().add(stop);
	}


	/**
	 * Checks if the route currently has seats left at a specified time
	 * @param iTime the specified time
	 * @return true if there are seats left
	 */
	public Boolean hasSeats(int iTime)
	{
		if (this.getTimeSlots().get(iTime).getStudents().size() < this.studentLimit)
			return true;
		else
			return false;

	}


	/**
	 * fills the roue with timeslots
	 *
	 */
	public void fillTimeSlots()
	{

		int iH = 0;
		int iM = 0;
		int sH = this.getStart().getHour();
		int sM = this.getStart().getMinute();
		int endH = this.getEnd().getHour() ;
		int endM = this.getEnd().getMinute();
		int stopCount = 0;
		Boolean bEnd = false;



		for(;iH < 24; iM++)
		{
			TimeSlot time;
			String stopName = getBusStops().get(stopCount).getName();
			int offsetAmount = getBusStops().get(stopCount).getOffset();

			if (iM >= 60)
			{
				iM = 0;
				iH++;
			}
			if (iH == sH && iM == sM && !bEnd)
			{
				time = new TimeSlot(sH, sM, stopName);
				stopCount++;
				if (stopCount == this.getBusStops().size())
					stopCount = 0;
				sM += offsetAmount;
				if (sM >= 60)
				{
					sM -= 60;
					sH++;
				}
			}
			else
				time = new TimeSlot(iH, iM, "NULL");
			this.getTimeSlots().add(time);

			if (iH == endH && iM == endM)
				bEnd = true;
		}

	}


	/**
	 * prints all the routes timeslots as well as info about who is on it
	 */
	public void printTimes()
	{
		System.out.println("\n");
		for (int i = 0; i < this.getBusStops().size(); i++)
		{
			System.out.printf("%24s", this.getBusStops().get(i).getName());
		}
		System.out.println();
		int stopCount = 0;
		for (int j = 0; j < this.getTimeSlots().size(); j++)
		{
			if (!this.getTimeSlots().get(j).getStop().equals("NULL")) {
				System.out.printf("%20s %d", this.getTimeSlots().get(j).toString(), 70 - this.getTimeSlots().get(j).getStudents().size());
				stopCount++;
				if (stopCount >= this.getBusStops().size())
				{
					stopCount = 0;
					System.out.println();
				}
			}
		}
	}

	/**
	 * gets the timeslots
	 * @return the timeslots
	 */
	public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	/**
	 * sets the timeslots
	 * @param timeSlots sets the timeslot arraylist
	 */
	public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

	/**
	 * get the Start timeslot
	 * @return TimeSlot start
	 */
	public TimeSlot getStart() {
		return start;
	}

	/**
	 * sets the Start time
	 * @param start tImeSLot start
	 */
	public void setStart(TimeSlot start) {
		this.start = start;
	}

	/**
	 * gets the End time
	 * @return the end time
	 */
	public TimeSlot getEnd() {
		return end;
	}

	/**
	 * sets the End time
	 * @param end the End time
	 */
	public void setEnd(TimeSlot end) {
		this.end = end;
	}

	/**
	 * gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the bus stop array ist
	 * @return the bus stop array list
	 */
	public ArrayList<BusStop> getBusStops() {
		return busStops;
	}

	/**
	 * sets the bus stops
	 * @param busStops the bus stops array list
	 */
	public void setBusStops(ArrayList<BusStop> busStops) {
		this.busStops = busStops;
	}


}

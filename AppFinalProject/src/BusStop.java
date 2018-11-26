/**
 *
 * This object is for the bus stops, it basically just stores the relevent
 * info for a bus stop
 *
 * @author Chris Snyder
 */
public class BusStop
{
	private String name;
	private int offset;

	/**
	 * Constructor
	 *
	 * @param name name of the stop
	 * @param offset time offset for the next stop
	 */
	public BusStop(String name, int offset)
	{
		this.setName(name);
		this.setOffset(offset);
	}

	/**
	 * gets the offset
	 *
	 * @return offset time
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * sets the offset
	 *
	 * @param offset The offset time
	 *
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * gets the name
	 *
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name
	 *
	 * @param name the name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
}

import java.util.Random;
import java.util.Scanner;

/**
 * Individual Routes to be run simultaneously
 *
 * @author Chris Snyder
 */
public class BeginRoute implements Runnable
{
    private Route route;
    private ClockGetterSetter currentTime;
    private ReceiverGetter getter;


    /**
     * Constructor
     *
     * @param route
     * @param currentTime
     * @param getter
     */
    public BeginRoute(Route route, ClockGetterSetter currentTime, ReceiverGetter getter)
    {
        this.route = route;
        this.currentTime = currentTime;
        this.getter = getter;
    }


    @Override
    public void run()
    {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        String time = "";
        String input;
        int[] array = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2};


        //addRandomStudents(route, 1000, currentTime);

        while(true)
        {

            int num = rand.nextInt(20);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                //e.printStackTrace();
            }
            addRandomStudents(route, array[num], currentTime);

            if(currentTime.getTime().equals("23:30"))
                break;

            if (getter.getRoute().equals(route.getName()))
            {
                System.out.println("**********************************************************");
                System.out.print("\nUser added: ");
                addRealStudent(route, currentTime, getter);
                System.out.println("\n**********************************************************");
                getter.setRoute("");
            }
        }


    }

    /**
     *
     * Part of the simulation, this funtion will generate a number of random students
     * with random schedule times and locations
     *
     * @param route the route this thread is working with
     * @param addNum the number of students to add
     * @param currentTime the clock timer
     */
    public static void addRandomStudents(Route route, int addNum, ClockGetterSetter currentTime)
    {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        String input = "";
        TimeSlot[] times;

        int id = 0;
        int hour = 0;
        int min = 0;
        int stopIndex = 0;
        int timeIndex = -1;


        for (int i = 0; i < addNum; i++)
        {

            int offset = 0;
            id = rd.nextInt(99999999);
            while(hour * 60 + min < currentTime.getiHour()*60+currentTime.getiMin()) {
                hour = rd.nextInt(22);
                min = rd.nextInt(59);
            }

            stopIndex = rd.nextInt(route.getBusStops().size());
            //System.out.println( "    " + hour + ":" + min);
            Student student = new Student("Test", id);
            times= findTimes(route, stopIndex, hour, min);

            if (times[1].toString().equals("0:00"))
                timeIndex = times[0].getHour() * 60 + times[0].getMinute();
            else
                timeIndex = times[1].getHour() * 60 + times[1].getMinute();

            route.getTimeSlots().get(timeIndex).getStudents().add(student);
            System.out.printf("%s Student: %-9s added to %s at %-15s %s %d. \n", currentTime.getTime(), id, route.getBusStops().get(stopIndex).getName()
                    , times[1].toString()
                    , "Seats Left:"
                    ,  70 - route.getTimeSlots().get(times[1].getHour() * 60 + times[1].getMinute()).getStudents().size());
            for (int j = stopIndex + 1; j < route.getBusStops().size(); j++)
            {
                offset += route.getBusStops().get(j -1).getOffset();
                route.getTimeSlots().get(timeIndex + offset).getStudents().add(student);
                //System.out.println("   " + route.getTimeSlots().get(timeIndex + offset).toString());
            }
            //route.printTimes();
            hour = 0;
            min = 0;
        }
    }


    /**
     * This function is for aading the user to be into the bus
     *
     * @param route the route this thread is using
     * @param currentTime the clock timer
     * @param getter the getter object for obtaining the user entered data
     */
    public static void addRealStudent(Route route, ClockGetterSetter currentTime, ReceiverGetter getter)
    {

        TimeSlot[] times;


        int id = getter.getId();
        int hour = getter.getiHour();
        int min = getter.getiMin();
        int stopIndex = getter.getStopIndex();
        int timeIndex;
        int offset = 0;

            Student student = new Student("Test", id);
            times= findTimes(route, stopIndex, hour, min);

            if (times[1].toString().equals("0:00"))
                timeIndex = times[0].getHour() * 60 + times[0].getMinute();
            else
                timeIndex = times[1].getHour() * 60 + times[1].getMinute();

            route.getTimeSlots().get(timeIndex).getStudents().add(student);
            System.out.printf("%s Student: %-9s added to %s at %-15s %s %d. \n", currentTime.getTime(), id, route.getBusStops().get(stopIndex).getName()
                    , times[1].toString()
                    , "Seats Left:"
                    ,  70 - route.getTimeSlots().get(times[1].getHour() * 60 + times[1].getMinute()).getStudents().size());
            for (int j = stopIndex + 1; j < route.getBusStops().size(); j++)
            {
                offset += route.getBusStops().get(j -1).getOffset();
                route.getTimeSlots().get(timeIndex + offset).getStudents().add(student);
            }

    }


    /**
     * Searches the route for available times near closest to the time entered by the user
     *
     * @param route the route being used by this thread
     * @param stopIndex the index of the bus stop
     * @param iStartHour the arrival hour
     * @param iStartMin the arrival minute
     * @return Array of 3 TimeSlots
     */
    public static TimeSlot[] findTimes(Route route, int stopIndex, int iStartHour, int iStartMin)
    {
        int iHour = iStartHour;
        int iMin = iStartMin;
        int timesFound = 0;
        TimeSlot[] times = new TimeSlot[3];
        TimeSlot closeTime = new TimeSlot(0, 0, "NULL");
        TimeSlot laterTime = new TimeSlot(0, 0, "NULL");
        TimeSlot earlyTime = new TimeSlot(0, 0, "NULL");

        int iTime = iHour * 60 + iMin;
        String szStop = route.getBusStops().get(stopIndex).getName();

        while(timesFound < 2 && iTime < 24 * 60)
        {
            iTime = iHour * 60 + iMin;

            if (route.hasSeats(iTime) && route.getTimeSlots().get(iTime).getStop().equals(szStop)) {
                if (timesFound == 0) {
                    closeTime = route.getTimeSlots().get(iTime);
                    timesFound = 1;
                }
                else
                {
                    laterTime = route.getTimeSlots().get(iTime);
                    timesFound = 2;
                }
            }
            iMin++;
            if (iMin >= 60)
            {
                iMin -= 60;
                iHour++;
            }
        }
        iHour = iStartHour;
        iMin = iStartMin;

        while (timesFound < 3 && iTime > -1)
        {
            iTime = iHour * 60 + iMin;
            if (route.hasSeats(iTime) && route.getTimeSlots().get(iTime).getStop().equals(szStop))
            {
                earlyTime = route.getTimeSlots().get(iTime);
                timesFound = 3;
            }
            iMin--;
            if (iMin <= -1)
            {
                iMin += 60;
                iHour--;
            }
        }


        times[0] = earlyTime;
        times[1] = closeTime;
        times[2] = laterTime;

        return times;
    }
}

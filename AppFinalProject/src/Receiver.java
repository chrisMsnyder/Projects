import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Receiver implements Runnable
{
    private String route;
    private int iHour;
    private int iMin;
    private int stopIndex;
    private int id;
    private ReceiverGetter getter;
    private ArrayList<Route> routes;

    public Receiver(ArrayList<Route> routes, ReceiverGetter getter)
    {
        this.route = "";
        this.iHour = -1;
        this.iMin = -1;
        this.stopIndex = -1;
        this.id = -1;
        this.getter = getter;
        this.routes = routes;
    }

    public void run()
    {
        ServerSocket listener;
        Socket sock = null;
        String splitStr[];
        TimeSlot times[];
        String answer = null;

        try {
            listener = new ServerSocket(9090);
            sock = listener.accept();
        }
        catch (IOException e) { }



        while (true)
        {
            while (answer == null) {
                BufferedReader input = null;
                OutputStream ostream = null;
                try {
                    input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    ostream  = sock.getOutputStream();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                try{
                    answer = input.readLine();}
                catch (IOException e){}


                //System.out.println(answer);

                splitStr = answer.split(",");
                this.route = splitStr[1];
                this.stopIndex = Integer.parseInt(splitStr[2]);
                this.iHour = Integer.parseInt(splitStr[3]);
                this.iMin = Integer.parseInt(splitStr[4]);
                this.id = Integer.parseInt(splitStr[0]);
                for (int i = 0; i < routes.size(); i++)
                {
                    if (routes.get(i).getName().equals(this.route))
                    {
                        times = findTimes(routes.get(i), this.stopIndex, this.iHour, this.iMin);
                        break;
                    }
                }
                PrintWriter out = new PrintWriter(ostream, true);
                out.println();
                getter.setRoute(this.route);
                getter.setStopIndex(this.stopIndex);
                getter.setTime(this.iHour, this.iMin);
                getter.setId(this.id);


            }
            answer = null;
        }
    }


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

        //find next two available times
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

        //find early time
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

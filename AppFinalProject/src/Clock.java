public class Clock implements Runnable {
    private Route route;
    ClockGetterSetter currentTime;


    public Clock(Route route, ClockGetterSetter currentTime)
    {
        this.route = route;
        this.currentTime = currentTime;

    }
    @Override
    public void run()
    {
        int iHour = currentTime.getiHour();
        int iMin = currentTime.getiMin();
        int index = 60 * iHour + iMin;


        try
        {
            while (true)
		    {

                iHour = route.getTimeSlots().get(index).getHour();
                iMin = route.getTimeSlots().get(index).getMinute();
                currentTime.setTime(iHour, iMin);
			    try {
			    	Thread.sleep(60000);
			    } catch (InterruptedException e) {

			    }

			    index++;
		    }
        }
        catch(Exception e)
        {
            System.out.println("Exception");
        }
    }
}

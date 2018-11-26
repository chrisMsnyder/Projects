import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

/**
 * The primary simulation for testing the commuter app.
 * Generates ROutes, stops, and students
 *
 * @author Chris Snyder
 */
public class AppFinalProject 
{

	/**
	 * main, the heart and soul of every Java program
	 *
	 * @param args array of Strings
	 */
	public static void main(String args[])
	{
		ArrayList<Route> routes = new ArrayList<>();
		TimeSlot start = new TimeSlot(6, 32, "Start");
		TimeSlot end = new TimeSlot(22, 34, "End");
		ArrayList<Thread> threads = new ArrayList<>();
		Route route;
		Calendar now = Calendar.getInstance();


		
		route = new Route("Route 43", start, end);
		route.addBusStop("Campus Oval", 3);
		route.addBusStop("Barshop Lot 2", 1);
		route.addBusStop("Barshop Lot 1", 2);
		route.addBusStop("Brackenridge Lot 4", 2);
		route.addBusStop("Brackenridge Lot 5", 1);
		route.addBusStop("Brackenridge Lot 3", 9);
		routes.add(route);
		route.fillTimeSlots();
		ClockGetterSetter currentTime = new ClockGetterSetter(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
		//ClockGetterSetter currentTime = new ClockGetterSetter(7, 35);
		ReceiverGetter getter = new ReceiverGetter();
		Thread clock = new Thread(new Clock(route, currentTime));

		clock.start();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 40, "Start");
		end = new TimeSlot(22, 30, "End");
		route = new Route("Route 13", start, end);
		route.addBusStop("Arts Building", 5);
		route.addBusStop("East Campus Lot 1", 2);
		route.addBusStop("East Campus Lot 2", 8);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 40, "Start");
		end = new TimeSlot(22, 29, "End");
		route = new Route("Route 12", start, end);
		route.addBusStop("Arts Building", 4);
		route.addBusStop("Hill Country Place", 8);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 40, "Start");
		end = new TimeSlot(22, 33, "End");
		route = new Route("Route 14", start, end);
		route.addBusStop("Arts Building", 5);
		route.addBusStop("The Parq", 1);
		route.addBusStop("Broadstone", 2);
		route.addBusStop("The Luxx", 7);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 45, "Start");
		end = new TimeSlot(22, 32, "End");
		route = new Route("Route 20", start, end);
		route.addBusStop("Ford Lot", 4);
		route.addBusStop("The Outpost", 3);
		route.addBusStop("Avalon Place", 8);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 40, "Start");
		end = new TimeSlot(22, 31, "End");
		route = new Route("Route 22", start, end);
		route.addBusStop("Ford Lot", 4);
		route.addBusStop("Villas at Babcock", 2);
		route.addBusStop("The Reserve", 9);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 40, "Start");
		end = new TimeSlot(22, 29, "End");
		route = new Route("Route 40", start, end);
		route.addBusStop("Campus Oval", 4);
		route.addBusStop("Tetro Village", 11);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		start = new TimeSlot(6, 47, "Start");
		end = new TimeSlot(22, 34, "End");
		route = new Route("Route 42", start, end);
		route.addBusStop("Campus Oval", 7);
		route.addBusStop("High View Place", 2);
		route.addBusStop("Maverick Creek", 6);
		routes.add(route);
		route.fillTimeSlots();
		threads.add(new Thread(new BeginRoute(route, currentTime, getter)));

		Thread receiver = new Thread(new Receiver(routes, getter));
		receiver.start();

		for (int k = 0; k < threads.size(); k++)
			threads.get(k).start();

		//let the main class wait on the other t
		while(!currentTime.getTime().equals("23:30"));

		clock.interrupt();
		receiver.interrupt();

		for (int k = 0; k < threads.size(); k++)
			threads.get(k).interrupt();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(int k = 0; k < routes.size(); k++)
			routes.get(k).printTimes();

		System.out.println("\n" + currentTime.getTime());
		System.exit(0);


	}

}

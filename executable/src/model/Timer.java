/*
 * Kovács Ákos
 * 2019.03.11
 */

package pandaplaza.model;

import java.util.*;

public class Timer implements Runnable{

	/**
	 * List of objects which has to be ticked.
	 */
	private List<ITickable> tickables = new ArrayList<>();
	
	/**
	 * Used in test cases to identify the Timer object.
	 */
	private String name;
	
	/**
	 * Use a built-in timer.
	 */
	java.util.Timer t = new java.util.Timer(false);
	
	/**
	 * New thread for the timer.
	 */
	Thread timerThread = new Thread(this);
	
	/**
	 * true, if the timer is stopped, false if not.
	 */
	private boolean stopped = false;
	
	/**
	 * The time between two ticks.
	 */
	private int intervaltime;
	
	/**
	 * Time from timer's start, measured in milliseconds.
	 */
	private int timeFromStart_ms = 0;
	public Timer() {}
	
	public String getName() {return name;}
	public void setName(String n) {name = n;}
	
	public void addTickable(ITickable it) {
		if (!tickables.contains(it)) {
			tickables.add(it);
		}
	}

	public void removeTickable(ITickable it) {
		tickables.remove(it);
	}
	
	/**
	 * Starts the timer with the default 500 ms interval time.
	 */
	public void Start() {
		Start(500);
	}
	
	/**
	 * Starts the timer with the given interval time.
	 * @param Interval_ms - Interval time in milliseconds.
	 */
	public void Start(int Interval_ms) {
		if (stopped) {
			t = new java.util.Timer(false);
			stopped = false;
			timeFromStart_ms = 0;
		}
		intervaltime = Interval_ms;
		run();
	}
	
	/** 
	 * Stops the timer.
	 */
	public void Stop()  {
		t.cancel();
		t.purge();
		stopped = true;
	}
	
	/**
	 * The thread calls this function.
	 */
	@Override
	public void run() {
		st();
	}

	/**
	 * Start the built-in timer.
	 */
	private void st() {
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				timeFromStart_ms += intervaltime;
				
				List<ITickable> iterateOverTickables = new ArrayList<>();
				iterateOverTickables.addAll(tickables);
				
				for (ITickable it : iterateOverTickables) {
					it.tick();
				}
			} }, 0, intervaltime);
	}
	
	/**
	 * Returns the elapsed time from the start in seconds.
	 * @return Elapsed time from start in seconds.
	 */
	public int getTimeFromStartSec() {
		return timeFromStart_ms/1000;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String newLine = System.lineSeparator();
		sb.append("Timer:");
		sb.append(newLine); sb.append(name);
		sb.append(newLine); sb.append("t:");
		for(ITickable it : tickables) { sb.append(" "); sb.append(it.getName());}
		sb.append(newLine); sb.append(newLine);
		return sb.toString();
	}
}
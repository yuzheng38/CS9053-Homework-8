package edu.nyu.cs9053.homework8;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class LambdaScheduler{
	private final ArrayList<LambdaJob> scheduler;

	public LambdaScheduler(){
		scheduler = new ArrayList<>();
	}

	public ArrayList<LambdaJob> getScheduler(){
		return scheduler;
	}

	public void addJob(LambdaJob job){
		scheduler.add(job);
	}

	// Greedy algorithm
	public ArrayList<LambdaJob> scheduleJobs(){
		Collections.sort(scheduler);
		ArrayList<LambdaJob> result = new ArrayList<>();
		result.add(scheduler.get(0));
		int prev = 0;
		for(int curr = 1; curr < scheduler.size(); curr++){
			if((scheduler.get(curr).getStart()).compareTo(scheduler.get(prev).getFinish()) >= 0){
				result.add(scheduler.get(curr));
				prev = curr;
			}
		}
		return result;
	}
}
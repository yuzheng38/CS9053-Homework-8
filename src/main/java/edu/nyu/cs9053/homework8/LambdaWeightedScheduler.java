package edu.nyu.cs9053.homework8;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

public class LambdaWeightedScheduler{
	private final ArrayList<LambdaJob> weightedScheduler;

	public LambdaWeightedScheduler(){
		weightedScheduler = new ArrayList<>();
	}

	public ArrayList<LambdaJob> getWeightedScheduler(){
		return weightedScheduler;
	}

	public void addJob(LambdaJob job){
		weightedScheduler.add(job);
	}

	// Dynamic programming using hashmap<profit, index> to store result. 
	public ArrayList<LambdaJob> scheduleWeightedJobs(){
		Collections.sort(weightedScheduler);

		double[] table = new double[weightedScheduler.size()];
		table[0] = weightedScheduler.get(0).getProfit();

		HashMap<Double, Integer> map = new HashMap<>();
		map.put(weightedScheduler.get(0).getProfit(), 0); 

		double maxProfit = 0d; 
		for(int i = 1; i < weightedScheduler.size(); i++){
			double currProfit = weightedScheduler.get(i).getProfit();
			int latestNonConflictJob = findLatestNonConflict(weightedScheduler, i);
			if(latestNonConflictJob != -1){
				currProfit += table[latestNonConflictJob];
			}
			maxProfit = Math.max(currProfit, table[i - 1]);
			table[i] = maxProfit;

			if(!map.containsKey(maxProfit)){
				map.put(maxProfit, i);
			}
		}

		// get result subset of jobs from hashmap
		ArrayList<LambdaJob> result = getOptimalJobs(weightedScheduler, map, maxProfit);
		Collections.sort(result);	// Optional sort
		return result;
	}

	private ArrayList<LambdaJob> getOptimalJobs(ArrayList<LambdaJob> scheduler, HashMap<Double, Integer> map, Double maxProfit){
		ArrayList<LambdaJob> result = new ArrayList<>();
		while(map.get(maxProfit) != null){
			LambdaJob job = scheduler.get(map.get(maxProfit));
			result.add(job);
			maxProfit -= job.getProfit();
		}
		return result;
	}

	private int findLatestNonConflict(ArrayList<LambdaJob> jobList, int index){
		for(int j = index - 1; j >= 0; j--){
			if(jobList.get(j).getFinish().compareTo(jobList.get(index).getStart()) <= 0){
				return j;
			}
		}
		return -1;
	}

}
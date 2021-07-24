package com.sourav.forkjoin;

import static com.sourav.util.CommonUtil.delay;
import static com.sourav.util.CommonUtil.stopWatch;
import static com.sourav.util.LoggerUtil.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.sourav.util.DataSet;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>>{
	
	private static final long serialVersionUID = -8215264574130550737L;
	private List<String> inputList;
	
	public ForkJoinUsingRecursion(List<String> inputList) {
		this.inputList = inputList;
	}

	public static void main(String args[]) {
		stopWatch.start();
	    List<String> resultList = new ArrayList<>();
	    List<String> names = DataSet.namesList();
	    System.out.println(names);
	    
	    ForkJoinUsingRecursion joinUsingRecursion = new ForkJoinUsingRecursion(names);
	    ForkJoinPool pool = new ForkJoinPool();
	    resultList = pool.invoke(joinUsingRecursion);
	    
	    stopWatch.stop();
	    log("Final Result : "+ resultList);
	    log("Total Time Taken : "+ stopWatch.getTime());
	}
	
	private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
	
	@Override
	protected List<String> compute() {
		
		if(inputList.size() == 1) {
			List<String> resultList = new ArrayList<>();
			inputList.forEach(name -> {
				resultList.add(addNameLengthTransform(name));
			}
			);
			return resultList;
		}
		int midPoint = inputList.size()/2;
		ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
		inputList = inputList.subList(midPoint, inputList.size());
		List<String> rightResult = compute();
		List<String> leftResult = leftInputList.join();
		leftResult.addAll(rightResult);
		return leftResult;
	}
}
	

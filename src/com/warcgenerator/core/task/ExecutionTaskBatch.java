package com.warcgenerator.core.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ExecutionTaskBatch {
	private boolean terminate;
	public List<Task> tasks;
	public Stack<Task> tasksExecutedList;
	
	public ExecutionTaskBatch() {
		this.tasks = new ArrayList<Task>();
		this.terminate = false;
		this.tasksExecutedList = new Stack<Task>();
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void execution() {
		Iterator<Task> tasksIterator = tasks.iterator();
		
		while (tasksIterator.hasNext() && !terminate) {
			Task currentTask = tasksIterator.next();
			currentTask.execute();
		}
	}

	public void terminate() {
		this.terminate = true;
	}
	
	public boolean isTerminate() {
		return terminate;
	}
}
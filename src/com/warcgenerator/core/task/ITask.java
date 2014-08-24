package com.warcgenerator.core.task;

public interface ITask {
	void execute();
	void rollback();
}

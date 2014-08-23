package com.warcgenerator.gui.task;

import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingWorker;

import com.warcgenerator.core.common.GenerateCorpusState;
import com.warcgenerator.core.common.GenerateCorpusStates;
import com.warcgenerator.core.logic.IAppLogic;
import com.warcgenerator.gui.view.generate.GeneratingCorpusDialog;

public class GenerateCorpusTask extends SwingWorker<Void, Integer> implements Observer {
	private IAppLogic logic;
	private GeneratingCorpusDialog gcd;
	private GenerateCorpusState gcState;
	
	public GenerateCorpusTask(IAppLogic logic, GeneratingCorpusDialog gcd) {
		this.logic = logic;
		this.gcd = gcd;
		this.gcState = new GenerateCorpusState();
		gcState.addObserver(this);
	}

	/*
	 * Main task. Executed in background thread.
	 */
	@Override
	public Void doInBackground() {
		setProgress(0);
		
		logic.generateCorpus(gcState);
		// Initialize progress property.

		/*
		 * while (progress < 100) { // Sleep for up to one second. try {
		 * //Thread.sleep(random.nextInt(1000)); } catch
		 * (InterruptedException ignore) { } // Make random progress.
		 * //progress += random.nextInt(10); setProgress(Math.min(progress,
		 * 100)); }
		 */
		return null;
	}

	/*
	 * Executed in event dispatching thread
	 */
	@Override
	public void done() {
		if (this.isCancelled()) {
			System.out.println("La tarea ha sido cancelada");
			logic.stopGenerateCorpus();
		} else {
			System.out.println("Tarea completada");
		}
	}

	@Override
	public void update(Observable obs, Object arg1) {
		System.out.println("se ha escuchado algo!!");
		// TODO Auto-generated method stub
		if (obs == gcState) {
			//gcd.getStateLbl().setText(arg0);
			int inc = Math.round(100 / (GenerateCorpusStates.values().length + 1));
			System.out.println("inc es " + inc);
			int progress = 0;
			switch (gcState.getState()) {
				case GETTING_URLS_FROM_DS:
					gcd.getStateLbl().setText("Obteniendo urls de los datasources");
					System.out.println("Obteniendo urls<--");
					progress = getProgress() + inc;
					break;
				case READING_SPAM:
					gcd.getStateLbl().setText("Leyendo urls de spam");
					System.out.println("Reading spam!!");
					progress = getProgress() + inc;
					break;
				case CRAWLING_URLS:
					gcd.getStateLbl().setText("Leyendo url: " + gcState.
							getCurrentUrlCrawled());
						System.out.println("Reading urls");
						progress = getProgress() + 
							Math.round(inc / gcState.getWebsToVisitTotal());
					break;
				case READING_HAM:
					gcd.getStateLbl().setText("Leyendo urls de ham");
					System.out.println("Reading ham");
					progress = getProgress() + inc;
					break;
				case ENDING:
					gcd.getStateLbl().setText("Finalizando la ejecucion");
					System.out.println(" -- Finalizando --");
					progress = 100;
					break;
				case CANCELlING_PROCESS:
					gcd.getStateLbl().setText("Cancelando el proceso");
					System.out.println(" -- Cancelando el proceso --");
					progress = 100;
					break;
			}
			
			System.out.println("progress!!!!! ->>> " + progress);
			setProgress(progress);
			//setProgress(gcState.getWebsVisited());
			
			publish();
			System.out.println("Numero webs visitadas: " + gcState.getWebsVisited());
		}
	}
}

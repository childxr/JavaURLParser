package app;

import java.util.ArrayList;
import java.io.File;

import pojo.WorkerThread;
import tools.WebTools;

public class JavaURLParser {
		
	public static void main(String[] args) {

		//Input validation
		if (args.length != 3) {
			System.out.println("[ERROR] 3 parameters are needed!");
			System.out.println("[INFOR] java JavaURLParser [threadnum] [url-txt] [output-dir]");
			return;
		}
		if (Integer.valueOf(args[0]) <= 0) {
			System.out.println("[ERROR] at least one thread is needed!");
			System.out.println("[INFOR] java JavaURLParser [threadnum] [url-txt] [output-dir]");
			return;
		}
		File file = new File(args[1]);
		if (!file.exists()) {
			System.out.println("[ERROR] url file does not exist!");
			System.out.println("[INFOR] java JavaURLParser [threadnum] [url-txt] [output-dir]");
			return;
		}
		
		int nThread = Integer.valueOf(args[0]);
		String urlListFile = args[1];
		String path = args[2];
		System.out.println("[INFOR] Max # of thread ="+ nThread +"\n[INFOR] URL list @ "+urlListFile+"\n[INFOR] Output Dir @ "+path);

		//Allocate work to workers
		WebTools.loadURLs(urlListFile);
		ArrayList<WorkerThread> workers = new ArrayList<WorkerThread> ();
		for (int i = 0; i < nThread; i++) workers.add(new WorkerThread(i, path));
		for (WorkerThread worker: workers)worker.getThread().start();
		
		//Wait until all workers finish their and output
		try {
			for (WorkerThread worker: workers) worker.getThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		System.out.println("[INFOR] All URLs have been processed\n[INFOR] Please check out @ "+path);
	}
}

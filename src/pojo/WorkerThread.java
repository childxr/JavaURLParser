package pojo;

import tools.WebTools;

public class WorkerThread implements Runnable {
	private Thread thread;
	private int workerId;
	private static String workPath = null;
	private static int nThread;
	
	public WorkerThread(int workerId, String path) {
		this.workerId = workerId;
		thread = new Thread(this);
		if(workPath == null)workPath = path;
		nThread++;
	}
	
	public void run() {
		int numPage = WebTools.getURLsNumber();
		for (int i = workerId; i < numPage; i += nThread) {
			System.out.println("[PROCS] Worker:"+workerId+" downloading "+i);
			WebTools.downLoadWebPage(i, workPath);
			System.out.println("[PROCS] Worker:"+workerId+" parsing "+i);
			WebTools.parseWebPage(i,workPath);
			System.out.println("[PROCS] Worker:"+workerId+" clearing page "+i);
			WebTools.deleteWebPage(i, workPath);
		}
	}
	
	public int getWorkerId() {
		return this.workerId;
	}
	
	public Thread getThread() {
		return this.thread;
	}
	
	public static String getWorkPath() {
		return workPath;
	}
	
	public static int getNumThread() {
		return nThread;
	}
	

}

// package rmi;


public class ProgressBar {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static void progress() throws InterruptedException{
		System.out.print("[                              ]\r");
		for(int i = 0;i < 30;i ++) {
			System.out.print(ANSI_CYAN);
			System.out.print("[");
			for(int j = i;j > 0;j --) {
				System.out.print("=");
			}
			if(i != 29) {
				System.out.print(">");
			}
			for(int j = 30 - i;j > 0;j --) {
				System.out.print(" ");
			}
			if(i != 29) {
				System.out.print("]\r");
			}
			else {
				System.out.print("] Done!");
				Thread.sleep(80);
				System.out.print("\r");
			}
			Thread.sleep(40);
		}
		System.out.println();
	}
}
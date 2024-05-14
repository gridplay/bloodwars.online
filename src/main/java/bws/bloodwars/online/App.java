package bws.bloodwars.online;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", version = "App 0.0.1-SNAPSHOT", mixinStandardHelpOptions = true)
public class App implements Runnable {
	public BWS bws;

    @Option(names = { "-p", "--port" }, description = "port to listen on") 
    int port = 666;

    @Override
    public void run() {
    	try {
			bws = new BWS(port);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new App()).execute(args));
    }

}

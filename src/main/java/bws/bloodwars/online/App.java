package bws.bloodwars.online;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bws.bloodwars.online.Server.BWS;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", version = "App 0.0.1-SNAPSHOT", mixinStandardHelpOptions = true)
public class App implements Runnable {
	private static final Logger logger = LogManager.getLogger(App.class);
	public BWS bws;

    @Option(names = { "-p", "--port" }, description = "port to listen on")
    int port = 666;

    @Override
    public void run() {
		logger.info("BloodWars Online server. Starting up");
    	bws = new BWS(this.port);
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new App()).execute(args));
    }

}

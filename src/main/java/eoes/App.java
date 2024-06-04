package eoes;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eoes.DB.DB;
import eoes.Server.EOES;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.EntityManagerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", version = "App 0.0.1-SNAPSHOT", mixinStandardHelpOptions = true)
public class App implements Runnable {
	private static final Logger logger = LogManager.getLogger(App.class);
	public EOES eoes;

	@SuppressWarnings("exports")
	public EntityManagerFactory connection;
	
	public static int ClientCount = 0;
	public Realms realm = new Realms();

    @Option(names = { "-p", "--port" }, description = "port to listen on")
    int port = 666;

    @Override
    public void run() {
        App app = new App();
        String portstr = app.LoadConfig("server.port");
        app.port = Integer.parseInt(portstr);
        app.connection = DB.getConnection();
        if (app.connection != null) {
            logger.info("Successfully connected to the database.");
        } else {
        	logger.info("Failed to connect to the database.");
        }
		logger.info("Eclipse of Eternity server. Starting up");
    	try {
			eoes = new EOES(this.port);
			realm.SaveRealm(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private static void copyFileFromJar(String sourcePathInJar, String destinationPathOutsideJar) {
        try {
            // Create a Path object for the destination path
            Path destinationPath = Paths.get(destinationPathOutsideJar);
            
            // Get the input stream for the file inside the JAR
            try (var inputStream = App.class.getResourceAsStream(sourcePathInJar)) {
                if (inputStream != null) {
                    // Copy the contents of the file from the input stream to the destination path
                    Files.copy(inputStream, destinationPath);
                } else {
                    System.err.println("Failed to copy "+destinationPathOutsideJar+". Input stream is null.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error copying: " + e.getMessage());
        }
    }
	public String LoadConfig(String conf) {
		String ret = "";
		Configurations configs = new Configurations();
        try {
            INIConfiguration config = configs.ini("config.ini");
            ret = config.getString(conf);

        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        }
        return ret;
	}
    public static void main(String[] args) {
    	String sourceLogConfig = "/resources/log4j2.xml";
    	String sourceLogFile = "/resources/eoes.log";
    	String sourceConfig = "/resources/config.ini";
        
        String destinationLogConfig = "log4j2.xml";
        String destinationLogFile = "eoes.log";
        String destinationConfig = "config.ini";

        // Check if the log4j.xml file already exists
        File configconfigFile = new File(destinationLogConfig);
        if (!configconfigFile.exists()) {
        	copyFileFromJar(sourceLogConfig, destinationLogConfig);
        }
        // Check if the bws.log file already exists
        File logconfigFile = new File(destinationLogFile);
        if (!logconfigFile.exists()) {
        	copyFileFromJar(sourceLogFile, destinationLogFile);
        }
        // Check if the config.ini file already exists
        File configFile = new File(destinationConfig);
        if (!configFile.exists()) {
        	copyFileFromJar(sourceConfig, destinationConfig);
        }
        System.exit(new CommandLine(new App()).execute(args));
    }

}

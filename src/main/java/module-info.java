module bloodwars.online {
    exports bws.bloodwars.online;
    exports bws.bloodwars.online.Server;

    requires com.google.gson;
    requires info.picocli;
    requires org.apache.logging.log4j;

    opens bws.bloodwars.online to info.picocli;
}

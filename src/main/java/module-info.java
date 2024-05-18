module bloodwars.online {
    exports bws.bloodwars.online;
    exports bws.bloodwars.online.Server;

    requires info.picocli;
    requires org.apache.logging.log4j;
    requires io.netty.all;
	requires com.fasterxml.jackson.databind;

    opens bws.bloodwars.online to info.picocli;
}

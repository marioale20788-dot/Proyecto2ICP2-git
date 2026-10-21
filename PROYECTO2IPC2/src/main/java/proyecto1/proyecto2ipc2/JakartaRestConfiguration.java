package proyecto1.proyecto2ipc2;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Configures Jakarta RESTful Web Services for the application.
 *
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends ResourceConfig {

    public JakartaRestConfiguration() {

        packages("proyecto1.proyecto2ipc2");
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
    }

}

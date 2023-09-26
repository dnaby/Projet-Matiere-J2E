package sn.ept.git47.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
@OpenAPIDefinition(
        info =
            @Info(
                    title = "Bicycle Sales",
                    description = "<span>This API provides endpoints for `managing bicycle sales`.</span> " +
                            "<span>It exposes `CRUD operations` for dashboards built with <u>Angular</u> " +
                            "and <u>Android Studio</u>.</span>\n\n <span>The data is sourced from a `database that " +
                            "tracks bicycle sales and production`.</span>",
                    contact = @Contact(
                            name = "Mouhamadou Naby DIA",
                            email = "dnaby@ept.sn",
                            url = "https://www.linkedin.com/in/mouhamadou-naby-dia-81840a231/"
                    ),
                    version = "1.0",
                    license = @License(name = "OpenSource")
            ),
        servers = { @Server(url = "http://localhost:8080/projet_matiere-1.0-SNAPSHOT/") }
)
public class API extends Application {}

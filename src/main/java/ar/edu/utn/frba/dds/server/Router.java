package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.OfertasProductoController;
import ar.edu.utn.frba.dds.serviceLocator.ServiceLocator;
import ar.edu.utn.frba.dds.services.FileUploadService;
import io.javalin.Javalin;
import io.javalin.http.UploadedFile;
import java.io.IOException;

/**
 * EL router es el encargado de las RUTAS.
 */

public class Router {
    /**
     * Inicio de App.
     */
    public static void init(Javalin app) {

        //LOGIN
        app.get("/login", ctx -> ctx.render("/auth/login/inicio-sesion.hbs"));

        //REGISTRO
        app.get("/registro", ctx -> ctx.render("/auth/registro/registro.hbs"));
        app.get("/registro/persona-humana", ctx -> ctx.render("/auth/registro/registro-humano.hbs"));
        app.get("/registro/persona-juridica", ctx -> ctx.render("/auth/registro/registro-juridico.hbs"));

        //ALTA
        app.get("/admin/tecnicos/nuevo", ctx -> ctx.render("/app/admin/alta-tecnico.hbs"));
        app.get("/admin/formularios/nuevo", ctx -> ctx.render("/app/admin/alta-formulario.hbs"));
        app.get("/admin/modelos-heladeras/nuevo", ctx -> ctx.render("/app/admin/alta-modelo-heladera.hbs"));

        //COLABORACIONES
        app.get("/colaborar", ctx -> ctx.render("/app/colaboraciones/colaborar.hbs"));
        app.get("/colaborar/donar-dinero", ctx -> ctx.render("/app/colaboraciones/donacion-dinero.hbs"));
        app.get("/colaborar/registrar-persona-vulnerable", ctx -> ctx.render("/app/colaboraciones/alta-persona-vulnerable.hbs"));
        app.get("/colaborar/donar-vianda", ctx -> ctx.render("/app/colaboraciones/donacion-vianda.hbs"));
        app.get("/colaborar/distribuir-viandas", ctx -> ctx.render("/app/colaboraciones/distribucion-vianda.hbs"));
        app.get("/colaborar/colocar-heladera", ctx -> ctx.render("/app/colaboraciones/cargo-heladera.hbs"));
        app.get("/colaborar/ofrecer-producto", ServiceLocator.get(OfertasProductoController.class)::create);

        //HELADERAS
        app.get("/heladeras", ctx -> ctx.render("/app/heladeras/heladeras.hbs"));
        app.get("/heladeras/suscribirme", ctx -> ctx.render("/app/heladeras/suscripcion.hbs"));
        app.get("/heladeras/reportar-falla-tecnica", ctx -> ctx.render("/app/heladeras/reportar-falla.hbs"));
        app.get("/heladeras/alertas", ctx -> ctx.render("/app/heladeras/listado-alertas.hbs"));

        //REPORTES
        app.get("/reportes", ctx -> ctx.render("/app/reportes/reportes.hbs"));

        //CARGA MASIVA
        app.get("/carga-masiva-colaboraciones", ctx -> ctx.render("/app/carga-masiva/carga-masiva.hbs"));

        //PRODUCTOS
        app.get("/productos", ServiceLocator.get(OfertasProductoController.class)::index);
        app.post("/productos", ServiceLocator.get(OfertasProductoController.class)::save);

        app.get("/", ctx -> {
            ctx.redirect("/quienes-somos");
        });

        app.get("/quienes-somos", ctx -> ctx.render("/app/quienes-somos.hbs"));

        app.post("/upload", ctx -> {
            UploadedFile uploadedFile = ctx.uploadedFile("file");
            try {
                FileUploadService fileUploadService = new FileUploadService();
                String result = fileUploadService.uploadFile(uploadedFile, "src/main/resources/templates/app/carga-masiva/");
                ctx.result(result);
            } catch (IOException e) {
                e.printStackTrace();
                ctx.result("Error al subir el archivo: " + e.getMessage());
            }
        });
    }
}
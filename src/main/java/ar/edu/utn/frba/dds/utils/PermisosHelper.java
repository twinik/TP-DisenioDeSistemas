package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.domain.colaboradores.autenticacion.Permiso;
import ar.edu.utn.frba.dds.models.repositories.IPermisosRepository;
import ar.edu.utn.frba.dds.serviceLocator.ServiceLocator;
import io.javalin.http.Context;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PermisosHelper {

  private static PermisosHelper instancia;
  private final Set<Permiso> permisosDisponibles = new HashSet<>();
  private IPermisosRepository permisosRepository;

  private PermisosHelper() {
    this.permisosRepository = ServiceLocator.get(IPermisosRepository.class);
    permisosDisponibles.addAll(this.permisosRepository.buscarTodos());
  }

  public static PermisosHelper getInstance() {
    if (instancia == null) {
      instancia = new PermisosHelper();
    }
    return instancia;
  }

  public Set<Permiso> buscarPermisosPara(Context ctx) {
    if (ctx.method().name().equals("GET")) {
      return switch (ctx.matchedPath()) {
        case "/admin/tecnicos/nuevo" -> this.buscarPorNombres("alta-tecnico");
        case "/admin/formularios/nuevo" -> this.buscarPorNombres("alta-formulario");
        case "/admin/modelos-heladeras/nuevo" -> this.buscarPorNombres("alta-modelo-heladera");
        case "/colaborar/ofrecer-producto" -> this.buscarPorNombres("ofrecer-productos");
        case "/colaborar/donar-dinero" -> this.buscarPorNombres("donar-dinero");
        case "/colaborar/colocar-heladera" -> this.buscarPorNombres("colocar-heladeras");
        case "/productos" -> this.buscarPorNombres("canjear-productos");
        default -> new HashSet<>();
      };
    } else if (ctx.method().name().equals("POST")) {
      return switch (ctx.matchedPath()) {
        case "/admin/tecnicos/nuevo" -> this.buscarPorNombres("alta-tecnico");
        case "/admin/formularios/nuevo" -> this.buscarPorNombres("alta-formulario");
        case "/admin/modelos-heladeras/nuevo" -> this.buscarPorNombres("alta-modelo-heladera");
        case "/colaborar/ofrecer-producto" -> this.buscarPorNombres("ofrecer-productos");
        case "/colaborar/donar-dinero" -> this.buscarPorNombres("donar-dinero");
        case "/colaborar/colocar-heladera" -> this.buscarPorNombres("colocar-heladeras");
        case "/productos" -> this.buscarPorNombres("ofrecer-productos");
        default -> new HashSet<>();
      };
    }
    return new HashSet<>();

  }

  public void agregarPermiso(Permiso p) {
    this.permisosRepository.guardar(p);
    this.permisosDisponibles.add(p);
  }

  public Set<Permiso> buscarPorNombres(String... nombres) {
    return this.permisosDisponibles.stream().filter(p ->
        Arrays.stream(nombres).anyMatch(nombre -> p.getDesc_interna().equalsIgnoreCase(nombre))
    ).collect(Collectors.toSet());
  }


}
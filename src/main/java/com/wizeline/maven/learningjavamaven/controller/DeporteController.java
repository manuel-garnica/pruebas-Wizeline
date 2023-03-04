package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.CatalogoDeportes;
import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.model.ErrorDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseGenericoDTO;
import com.wizeline.maven.learningjavamaven.observer.Observable;
import com.wizeline.maven.learningjavamaven.observer.observers.CatalogoDeportesObserver;
import com.wizeline.maven.learningjavamaven.service.DeporteService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deportes")
@Tag(name = "Deporte", description = "API para la gestión del catalogo deportes")
public class DeporteController {

    private final Bucket bucket;

    public DeporteController() {
        Refill refill = Refill.intervally(5, Duration.ofSeconds(10));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    @Autowired
    private DeporteService deporteService;
    @GetMapping("/Singleton")
    public ResponseEntity<List<DeporteDTO>> obtenerDeportesSingleton() {
        CatalogoDeportes catalogoDeportes =CatalogoDeportes.getInstance(deporteService);
       return ResponseEntity.ok(catalogoDeportes.getDeporteDTO());
    }
  /*  @Operation(summary = "Obtener todos los deportes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de deportes obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron deportes")
    })*/
  @Operation(summary = "Obtener todos los deportes", tags = {"Deporte"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Lista de deportes obtenida correctamente",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = DeporteDTO.class))),
          @ApiResponse(responseCode = "404", description = "No se encontraron deportes")})
    @GetMapping
    public ResponseEntity<List<DeporteDTO>> obtenerDeportes() {
      if (!bucket.tryConsume(1)) {
          return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
      }
        List<DeporteDTO> deportes = deporteService.obtenerTodosDeportes();
        return ResponseEntity.ok(deportes);
    }

    @Operation(summary = "Obtener un deporte por ID", tags = {"Deporte"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deporte encontrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeporteDTO.class, example = "{\n" +
                                    "    \"id\": \"63f3d63d9a5f3261d0c27289\",\n" +
                                    "    \"nombre\": \"Fútbol\",\n" +
                                    "    \"descripcion\": \"Deporte de 11 jugadores :D\",\n" +
                                    "    \"fechaRegistro\": \"2022-02-20T19:30:00.000+00:00\"\n" +
                                    "}"))),
            @ApiResponse(responseCode = "404", description = "Deporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeporteDTO> obtenerDeportePorId(@Parameter(description = "ID del deporte a eliminar", required = true) @PathVariable String id) {
        Optional<DeporteDTO> optionalDeporte = deporteService.obtenerDeportePorId(id);
        if (optionalDeporte.isPresent()) {
            DeporteDTO deporte = optionalDeporte.get();
            return ResponseEntity.ok(deporte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Crear deporte", tags = {"Deporte"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El deporte se ha creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeporteDTO.class, example = "{\n" +
                                    "    \"id\": \"63f3d63d9a5f3261d0c27289\",\n" +
                                    "    \"nombre\": \"Fútbol\",\n" +
                                    "    \"descripcion\": \"Deporte de 11 jugadores :D\",\n" +
                                    "    \"fechaRegistro\": \"2022-02-20T19:30:00.000+00:00\"\n" +
                                    "}"))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<DeporteDTO> crearDeporte(@RequestBody DeporteDTO deporte) {
        DeporteDTO nuevoDeporte = deporteService.crearDeporte(deporte);
        //Observable para actualizar el catagolo de deportes el insertar una nuevo
        Observable observable = new Observable();
        observable.attach(new CatalogoDeportesObserver(deporteService));
        observable.setFueEditado(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDeporte);
    }
    @Operation(summary = "Actualziacion del deporte por ID", tags = {"Deporte"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deporte  correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGenericoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Deporte no encontrado")
            ,
            @ApiResponse(responseCode = "500", description = "Error en el servicio")
    })
    @PutMapping("/{id}")
    public ResponseEntity actualizarDeporte(@PathVariable String id, @RequestBody DeporteDTO deporte) {
        ResponseGenericoDTO responseGenericoDTO= new ResponseGenericoDTO();
        Optional<DeporteDTO>  deporteDTO= Optional.of(new DeporteDTO());
      try {
          deporteDTO= Optional.ofNullable(deporteService.actualizarDeporte(id, deporte));
          responseGenericoDTO.setCode(HttpStatus.OK.toString());
          responseGenericoDTO.setStatus("OK");
          responseGenericoDTO.setData(deporteDTO);
          return ResponseEntity.status(HttpStatus.CREATED).body(responseGenericoDTO);
      }
      catch (RuntimeException runtimeException){
          responseGenericoDTO.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
          responseGenericoDTO.setStatus(HttpStatus.NOT_FOUND.toString());
          responseGenericoDTO.setData(deporteDTO);
          responseGenericoDTO.setErrors(new ErrorDTO("404","Deporte no encontrado"));
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseGenericoDTO);
      }
      catch (Exception e){
          responseGenericoDTO.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
          responseGenericoDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
          responseGenericoDTO.setData(deporteDTO);
          responseGenericoDTO.setErrors(new ErrorDTO(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),"Error revisar"));
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGenericoDTO);
      }

    }
    @Operation(summary = "Eliminar un deporte por ID", tags = {"Deporte"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Deporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarDeporte(@PathVariable String id) {
        deporteService.eliminarDeporte(id);
        return ResponseEntity.noContent().build();
    }
}


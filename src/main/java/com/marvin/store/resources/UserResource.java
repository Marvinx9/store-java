package com.marvin.store.resources;

import com.marvin.store.entities.User;
import com.marvin.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    @ApiResponse(responseCode = "200", description = "lista de usuários retornado com sucesso", content = {
            @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = User.class)))})
    public ResponseEntity<List<User>> findAll(){
        List<User> list = userService.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Recebe o id do usuário e busca no banco de dados")
    @ApiResponse(responseCode = "200", description = "usuario retornado com sucesso", content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class))
    })
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @Operation(summary = "Cria um usuário no banco de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro ao salvar esse usuário", content = {
                    @Content(schema = @Schema(implementation = Void.class))
            })
    })
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = userService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
        obj = userService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

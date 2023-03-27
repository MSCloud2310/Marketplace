package com.javeriana.userManagment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
import com.javeriana.userManagment.services.SocialNetworkService;
import com.javeriana.userManagment.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SocialNetworkService socialNetService;

    @GetMapping("/client/{id}") 
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        Client client = userService.getClientUser(id);
        
        if(client == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrado el usuario con el id " + id);
        
        return ResponseEntity.ok(client);
    }

    @GetMapping("/provider/{id}") 
    public ResponseEntity<?> getProvider(@PathVariable("id") Long id){
        Provider provider = userService.getProviderUser(id);

        if(provider == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrado el usuario con el id " + id);

        return ResponseEntity.ok(provider);
    }

    @GetMapping("/provider/{id}/socialNetworks")
    public ResponseEntity<?> getSocialNetworks(@PathVariable("id") Long id){
        List<SocialNetwork> socialNets = userService.getSocialNetworksByProviderId(id);

        if(socialNets == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrado el usuario con el id " + id);

        return ResponseEntity.ok(socialNets);
    }

    @GetMapping("/provider/{id}/socialNetworks/{idS}")
    public ResponseEntity<?> getSocialNetwork(@PathVariable("idS") Long id){
        SocialNetwork socialNetwork = socialNetService.getSocialNetwork(id);

        if(socialNetwork == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrada la red social");
    
        return ResponseEntity.ok(socialNetwork);
    }

    @PostMapping("/client")
    public ResponseEntity<?> postUser(@RequestBody Client client){
        Client clientTemp = userService.createClientUser(client);
        
        if(clientTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(clientTemp);
    }

    @PostMapping("/provider")
    public ResponseEntity<?> postProvider(@RequestBody Provider provider){
        Provider providerTemp = userService.createProviderUser(provider);
        
        if(providerTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(providerTemp);
    }

    @PostMapping("/provider/{id}/socialNetworks")
    public ResponseEntity<?> postSocialNetwork(@RequestBody SocialNetwork socialNetwork, @PathVariable("id") Long id){
        SocialNetwork socialNetworkTemp = socialNetService.createSocialNetwork(socialNetwork, id);

        if(socialNetworkTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la red social");
    
        return ResponseEntity.status(HttpStatus.CREATED).body(socialNetworkTemp);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<?> putUser(@RequestBody Client client, @PathVariable("id") Long id){
        Client clientTemp = userService.updateClientUser(client);
        
        if(clientTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el usuario");
        
        return ResponseEntity.status(HttpStatus.OK).body(clientTemp);
    }

    @PutMapping("/provider/{id}")
    public ResponseEntity<?> putProvider(@RequestBody Provider provider, @PathVariable("id") Long id){
        Provider providerTemp = userService.updateProviderUser(provider);
        
        if(providerTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el usuario");
        
        return ResponseEntity.status(HttpStatus.OK).body(providerTemp);
    }

    //Validar usuario y enviarle el provedor para relacionarlo
    @PutMapping("/provider/{id}/socialNetworks/{idS}")
    public ResponseEntity<?> putSocialNetwork(@RequestBody SocialNetwork socialNetwork, @PathVariable("id") Long id){
        SocialNetwork socialNetworkTemp = socialNetService.updateSocialNetwork(socialNetwork, id);

        if(socialNetworkTemp == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la red social");
    
        return ResponseEntity.status(HttpStatus.OK).body(socialNetworkTemp);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id){
        Boolean response = userService.deleteClient(id);
        
        if(!response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrado el usuario con el id " + id);
        
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito");
    }

    @DeleteMapping("/provider/{id}")
    public ResponseEntity<?> deleteProvider(@PathVariable("id") Long id){
        Boolean response = userService.deleteProvider(id);
        
        if(!response)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fue encontrado el usuario con el id " + id);
        
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito");
    }

    @DeleteMapping("/provider/{id}/socialNetworks/{idS}")
    public ResponseEntity<?> deleteSocialNetwork(@PathVariable("idS") Long id){
        Boolean socialNetworkTemp = socialNetService.deleteSocialNetwork(id);

        if(!socialNetworkTemp)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la red social");
    
        return ResponseEntity.status(HttpStatus.OK).body("Red social eliminada con exito");
    }
}

package com.javeriana.userManagment.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.javeriana.userManagment.model.AuthResponse;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
import com.javeriana.userManagment.model.User;
import com.javeriana.userManagment.repository.ClientRepository;
import com.javeriana.userManagment.repository.ProviderRepository;
import com.javeriana.userManagment.repository.SocialNetworkRepository;
import com.javeriana.userManagment.security.TokenUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  ProviderRepository providerRepository;

  @Autowired
  SocialNetworkRepository socialNetworkRepository;

  @Autowired
  @Lazy
  AuthenticationManager authenticationManager;

  //REGISTER USER - CLIENT
  public AuthResponse createClientUser(Client client) {
    Optional<Client> clientAux = clientRepository.findByEmail(client.getEmail());
    if (clientAux.isPresent()) {
        return null;
    }

    client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword())); 
    Long clientTemp = clientRepository.save(client).getId();
    Optional<Client> clientCreated = clientRepository.findById(clientTemp);

    if (clientCreated.isPresent()) {
        var jwtToken = TokenUtils.createToken(clientCreated.get().getEmail(), clientCreated.get().getRole().toString(), clientCreated.get().getId());
        return AuthResponse.builder().token(jwtToken).build();
    }
    
    return null;
  }


  //REGISTER USER - PROVIDER
  public AuthResponse createProviderUser(Provider provider) {
    Optional<Provider> providerAux = providerRepository.findByEmail(provider.getEmail());
    if (providerAux.isPresent()) {
        return null;
    }

    provider.setPassword(new BCryptPasswordEncoder().encode(provider.getPassword())); 
    Long providerTemp = providerRepository.save(provider).getId();
    Optional<Provider> providerCreated = providerRepository.findById(providerTemp);

    if (providerCreated.isPresent()) {
        var jwtToken = TokenUtils.createToken(providerCreated.get().getEmail(), providerCreated.get().getRole().toString(), providerCreated.get().getId());
        return AuthResponse.builder().token(jwtToken).build();
    }

    return null;
  }

  //VALIDATE TOKEN
  public AuthResponse validateToken(String token){
    String email = TokenUtils.validateToken(token);

    if(this.getClientByEmail(email).isPresent()){
        User userOptional = this.getClientByEmail(email).get();
        return new AuthResponse(TokenUtils.createToken(userOptional.getEmail(), userOptional.getRole().toString(), userOptional.getId()));
    }

    else if(this.getProviderByEmail(email).isPresent()){
        User userOptional = this.getProviderByEmail(email).get();
        return new AuthResponse(TokenUtils.createToken(userOptional.getEmail(), userOptional.getRole().toString(), userOptional.getId()));
    }
    
    else {
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
  }

  //GET USER BY ID
  public Client getClientUser(Long id) {
      Optional<Client> client = clientRepository.findById(id);

      if (client.isPresent()) {
          return client.get();
      }

      return null;
  }

  public Provider getProviderUser(Long id) {
      Optional<Provider> provider = providerRepository.findById(id);

      if (provider.isPresent()) {
          return provider.get();
      }

      return null;
  }

  //GET SOCIAL NETWORK BY ID
  public List<SocialNetwork> getSocialNetworksByProviderId(Long id) {
      Optional<Provider> provider = providerRepository.findById(id);

      if (provider.isPresent()) {
          return provider.get().getSocialNetworks();
      }

      return null;
  }

  //UPDATE USER
  public Client updateClientUser(Client client) {
      Optional<Client> clientTemp = clientRepository.findById(client.getId());

      if (clientTemp.isPresent()) {
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
        Long clientUpdatedId = clientRepository.save(client).getId();
        Optional<Client> clientUpdated = clientRepository.findById(clientUpdatedId);

        if (clientUpdated.isPresent()) {
            return clientUpdated.get();
        }
          
      }

      return null;
  }

  public Provider updateProviderUser(Provider provider) {
     Optional<Provider> providerTemp = providerRepository.findById(provider.getId());

      if (providerTemp.isPresent()) {
  
        provider.setPassword(new BCryptPasswordEncoder().encode(provider.getPassword()));
        Long providerUpdatedId = providerRepository.save(provider).getId();
        Optional<Provider> providerUpdated = providerRepository.findById(providerUpdatedId);

        if (providerUpdated.isPresent()) {
            return providerUpdated.get();
        }
          
      }
  
      return null;
  }

  //GET USER BY EMAIL
  public Optional<Client> getClientByEmail(String username) {
      return clientRepository.findByEmail(username);
  }

  public Optional<Provider> getProviderByEmail(String username) {
      return providerRepository.findByEmail(username);
  }

  //DETELE USER
  public boolean deleteClient(Long id) {
      Optional<Client> client = clientRepository.findById(id);

      if (client.isPresent()) {
          clientRepository.deleteById(id);
          return true;
      }

      return false;
  }

  public boolean deleteProvider(Long id) {
      Optional<Provider> provider = providerRepository.findById(id);

      if (provider.isPresent()) {
          providerRepository.deleteById(id);
          return true;
      }

      return false;
  }
}

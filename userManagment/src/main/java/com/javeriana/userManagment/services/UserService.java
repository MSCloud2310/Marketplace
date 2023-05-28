package com.javeriana.userManagment.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.javeriana.userManagment.model.AuthResponse;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
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

  private static final String ALGORITHM = "AES";
  private static final String KEY = "keyEncriptation";
  
/*
  //LOGIN USER - CLIENT
  public AuthResponse authUserClient(AuthRequest auth) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            auth.getUsername(),
            auth.getPassword()
        )
    );

    Optional<Client> client = clientRepository.findByEmail(auth.getUsername());
    if (client.isPresent()) {
        var jwtToken = jwtService.generateToken(client.get());
        return AuthResponse.builder().token(jwtToken).build();
    }

    return null;
  }


  //LOGIN USER - PROVIDER
  public AuthResponse authUserProvider(AuthRequest auth) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            auth.getUsername(),
            auth.getPassword()
        )
    );

    Optional<Provider> provider = providerRepository.findByEmail(auth.getUsername());
    if (provider.isPresent()) {
        var jwtToken = jwtService.generateToken(provider.get());
        return AuthResponse.builder().token(jwtToken).build();
    }

    return null;
  }
 */

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
        var jwtToken = TokenUtils.createToken(clientCreated.get().getEmail());
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

    String password = provider.getPassword();
    String encryptPassword = encrypt(password);

    if (encryptPassword != null) {
        provider.setPassword(encryptPassword);
        Long providerTemp = providerRepository.save(provider).getId();
        Optional<Provider> providerCreated = providerRepository.findById(providerTemp);

        if (providerCreated.isPresent()) {
            var jwtToken = TokenUtils.createToken(providerCreated.get().getEmail());
            return AuthResponse.builder().token(jwtToken).build();
        }
    }

    return null;
  }


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

  public List<SocialNetwork> getSocialNetworksByProviderId(Long id) {
      Optional<Provider> provider = providerRepository.findById(id);

      if (provider.isPresent()) {
          return provider.get().getSocialNetworks();
      }

      return null;
  }

  public Client updateClientUser(Client client) {
      Optional<Client> clientTemp = clientRepository.findById(client.getId());

      if (clientTemp.isPresent()) {
          String password = client.getPassword();
          String encryptPassword = encrypt(password);

          if (encryptPassword != null) {
              client.setPassword(encryptPassword);
              Long clientUpdatedId = clientRepository.save(client).getId();
              Optional<Client> clientUpdated = clientRepository.findById(clientUpdatedId);

              if (clientUpdated.isPresent()) {
                  return clientUpdated.get();
              }
          }
      }

      return null;
  }

  public Provider updateProviderUser(Provider provider) {
      Optional<Provider> providerTemp = providerRepository.findById(provider.getId());

      if (providerTemp.isPresent()) {
          String password = provider.getPassword();
          String encryptPassword = encrypt(password);

          if (encryptPassword != null) {
              provider.setPassword(encryptPassword);
              Long providerUpdatedId = providerRepository.save(provider).getId();
              Optional<Provider> providerUpdated = providerRepository.findById(providerUpdatedId);

              if (providerUpdated.isPresent()) {
                  return providerUpdated.get();
              }
          }
      }

      return null;
  }

  public Optional<Client> getClientByEmail(String username) {
      return clientRepository.findByEmail(username);
  }

  public Optional<Provider> getProviderByEmail(String username) {
      return providerRepository.findByEmail(username);
  }

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

  public static SecretKey generateKey() throws Exception {
      KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
      SecureRandom secureRandom = new SecureRandom();
      keyGenerator.init(128, secureRandom);
      return keyGenerator.generateKey();
  }

  public static SecretKeySpec getSecretKeySpec() throws Exception {
      byte[] key = KEY.getBytes(StandardCharsets.UTF_8);
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      return new SecretKeySpec(key, ALGORITHM);
  }

  public SecretKeySpec crearClave() {
      try {
          byte[] cadena = KEY.getBytes("UTF-8");
          MessageDigest md = MessageDigest.getInstance("SHA-1");
          cadena = md.digest(cadena);
          cadena = Arrays.copyOf(cadena, 16);
          SecretKeySpec sc = new SecretKeySpec(cadena, ALGORITHM);
          return sc;
      } catch (Exception e) {
          return null;
      }
  }

  public String encrypt(String password) {
      try {
          SecretKeySpec sc = crearClave();
          Cipher cipher = Cipher.getInstance(ALGORITHM);
          cipher.init(Cipher.ENCRYPT_MODE, sc);
          byte[] cadena = password.getBytes("UTF-8");
          byte[] candenaEncriptada = cipher.doFinal(cadena);
          return Base64.getEncoder().encodeToString(candenaEncriptada);
      } catch (Exception e) {
          return null;
      }
  }

}

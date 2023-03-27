package com.javeriana.userManagment.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.javeriana.userManagment.model.Auth;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
import com.javeriana.userManagment.repository.ClientRepository;
import com.javeriana.userManagment.repository.ProviderRepository;
import com.javeriana.userManagment.repository.SocialNetworkRepository;

@Service
public class UserService {

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  ProviderRepository providerRepository;

  @Autowired
  SocialNetworkRepository socialNetworkRepository;

  private static final String ALGORITHM = "AES";
  private static final String KEY = "llaveEncriptacion";

  public Client authUserClient(Auth auth) {
    Optional<Client> client = clientRepository.findByUsername(auth.getUsername());

    if (client.isPresent()) {
      if (client.get().getPassword().equals(encriptar(auth.getPassword())))
        return client.get();
    }

    return null;
  }

  public Provider authUserProvider(Auth auth) {
    Optional<Provider> provider = providerRepository.findByUsername(auth.getUsername());

    if (provider.isPresent()) {
      if (provider.get().getPassword().equals(encriptar(auth.getPassword())))
        return provider.get();
    }

    return null;
  }

  public Client getClientUser(Long id) {
    Optional<Client> client = clientRepository.findById(id);

    if (client.isPresent())
      return client.get();

    return null;
  }

  public Provider getProviderUser(Long id) {
    Optional<Provider> provider = providerRepository.findById(id);

    if (provider.isPresent())
      return provider.get();

    return null;
  }

  public List<SocialNetwork> getSocialNetworksByProviderId(Long id) {
    Optional<Provider> provider = providerRepository.findById(id);

    if (provider.isPresent())
      return provider.get().getSocialNetworks();

    return null;
  }

  public Client createClientUser(Client client) {

    Optional<Client> clientAux = clientRepository.findByUsername(client.getUsername());
    if (clientAux.isPresent())
      return null;

    String password = client.getPassword();
    String passwordEncriptada = encriptar(password);

    if (passwordEncriptada != null) {
      client.setPassword(passwordEncriptada);

      Long clientTemp = clientRepository.save(client).getId();
      Optional<Client> clientCreated = clientRepository.findById(clientTemp);

      if (clientCreated.isPresent())
        return clientCreated.get();
    }

    return null;
  }

  public Provider createProviderUser(Provider provider) {

    Optional<Provider> providerAux = providerRepository.findByUsername(provider.getUsername());
    if (providerAux.isPresent())
      return null;

    String password = provider.getPassword();
    String passwordEncriptada = encriptar(password);

    if (passwordEncriptada != null) {
      provider.setPassword(passwordEncriptada);

      Long providerTemp = providerRepository.save(provider).getId();
      Optional<Provider> providerCreated = providerRepository.findById(providerTemp);

      if (providerCreated.isPresent())
        return providerCreated.get();
    }

    return null;
  }

  public Client updateClientUser(Client client) {
    Optional<Client> clientTemp = clientRepository.findById(client.getId());

    if (clientTemp.isPresent()) {
      String password = client.getPassword();
      String passwordEncriptada = encriptar(password);

      if (passwordEncriptada != null) {
        client.setPassword(passwordEncriptada);
        Long clientUpdatedId = clientRepository.save(client).getId();
        Optional<Client> clientUpdated = clientRepository.findById(clientUpdatedId);

        if (clientUpdated.isPresent())
          return clientUpdated.get();
      }
    }

    return null;
  }

  public Provider updateProviderUser(Provider provider) {
    Optional<Provider> providerTemp = providerRepository.findById(provider.getId());

    if (providerTemp.isPresent()) {
      String password = provider.getPassword();
      String passwordEncriptada = encriptar(password);

      if (passwordEncriptada != null) {
        provider.setPassword(passwordEncriptada);
        Long providerUpdatedId = providerRepository.save(provider).getId();
        Optional<Provider> providerUpdated = providerRepository.findById(providerUpdatedId);

        if (providerUpdated.isPresent())
          return providerUpdated.get();
      }
    }

    return null;
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

  public String encriptar(String password) {
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

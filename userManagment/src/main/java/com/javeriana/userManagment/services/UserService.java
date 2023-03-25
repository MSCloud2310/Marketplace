package com.javeriana.userManagment.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Client getClientUser(Long id){
        Optional<Client> client = clientRepository.findById(id);

        if(client.isPresent())
            return client.get();
        
        return null;
    }

    public Provider getProviderUser(Long id){
        Optional<Provider> provider = providerRepository.findById(id);

        if(provider.isPresent())
            return provider.get();
        
        return null;
    }

    public List<SocialNetwork> getSocialNetworksByProviderId(Long id){
        Optional<Provider> provider = providerRepository.findById(id);

        if(provider.isPresent())
            return provider.get().getSocialNetworks();

        return null;
    }

    public Client createClientUser(Client client){
        Long clientTemp = clientRepository.save(client).getId();
        Optional<Client> clientCreated = clientRepository.findById(clientTemp);

        if(clientCreated.isPresent())
            return clientCreated.get();
        
        return null;
    }

    public Provider createProviderUser(Provider provider){
        Long providerTemp = providerRepository.save(provider).getId();
        Optional<Provider> providerCreated = providerRepository.findById(providerTemp);

        if(providerCreated.isPresent())
            return providerCreated.get();
        
        return null;
    }

    public Client updateClientUser(Client client){
        Optional<Client> clientTemp = clientRepository.findById(client.getId());

        if(clientTemp.isPresent()){
            Long clientUpdatedId = clientRepository.save(client).getId();
            Optional<Client> clientUpdated = clientRepository.findById(clientUpdatedId);
    
            if(clientUpdated.isPresent())
                return clientUpdated.get();
        }
         
        return null;
    }

    public Provider updateProviderUser(Provider provider){
        Optional<Provider> providerTemp = providerRepository.findById(provider.getId());

        if(providerTemp.isPresent()){
            Long providerUpdatedId = providerRepository.save(provider).getId();
            Optional<Provider> providerUpdated = providerRepository.findById(providerUpdatedId);
    
            if(providerUpdated.isPresent())
                return providerUpdated.get();
        }
         
        return null;
    }

    public boolean deleteClient(Long id){
        Optional<Client> client = clientRepository.findById(id);

        if(client.isPresent()){
            clientRepository.deleteById(id);
            return true;
        }
        
        return false;  
    }

    public boolean deleteProvider(Long id){
        Optional<Provider> provider = providerRepository.findById(id);

        if(provider.isPresent()){
            providerRepository.deleteById(id);
            return true;
        }
            
        return false;
    }

}

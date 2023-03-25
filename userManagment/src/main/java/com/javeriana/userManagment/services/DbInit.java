package com.javeriana.userManagment.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.javeriana.userManagment.model.Client;
import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
import com.javeriana.userManagment.repository.ClientRepository;
import com.javeriana.userManagment.repository.ProviderRepository;
import com.javeriana.userManagment.repository.SocialNetworkRepository;

@Component
public class DbInit implements CommandLineRunner{

    private static final int NUM_CLIENTS = 10;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    SocialNetworkRepository socialNetworkRepository;

    @Override
    public void run(String... args) throws Exception {

        List<SocialNetwork> socialNetworksList = new ArrayList<>();
        socialNetworksList.add(new SocialNetwork("Facebook", "HerreraPTE", "www.Facebook"));
        socialNetworksList.add(new SocialNetwork("Instagram", "HerreraPTE", "www.Instagram"));
        socialNetworksList.add(new SocialNetwork("Twitter", "HerreraPTE", "www.Twitter"));

        for(int i=0; i<NUM_CLIENTS; i++){
            Client client = new Client("HERRERA30080", "123456", "MANUEL", "ORTIZ", 15, "HERRERA ES GEI", "www.html", "123456789");
            clientRepository.save(client);
        }

        
        Provider provider = new Provider("HERRERA30080", "123456", "MANUEL", "ORTIZ", 15, "HERRERA ES GEI", "www.html", "310547894", "www.Empresa");
        
        providerRepository.save(provider);

        List<SocialNetwork> socialNetworksListB = new ArrayList<>();

        for(SocialNetwork s: socialNetworksList){
            SocialNetwork socialNetwork = new SocialNetwork(s.getName(), s.getUserName(), s.getUrl(), provider);
            socialNetworkRepository.save(socialNetwork);
            socialNetworksListB.add(socialNetwork);
        }

        provider.setSocialNetworks(socialNetworksListB);
        providerRepository.save(provider);
    }
    
}

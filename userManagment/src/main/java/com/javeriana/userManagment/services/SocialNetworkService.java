package com.javeriana.userManagment.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javeriana.userManagment.model.Provider;
import com.javeriana.userManagment.model.SocialNetwork;
import com.javeriana.userManagment.repository.ProviderRepository;
import com.javeriana.userManagment.repository.SocialNetworkRepository;

@Service
public class SocialNetworkService {

    @Autowired 
    SocialNetworkRepository socialNetworkRepository;

    @Autowired
    ProviderRepository providerRepository;

    public SocialNetwork getSocialNetwork(Long id){
        Optional<SocialNetwork> socialNet = socialNetworkRepository.findById(id);

        if(socialNet.isPresent()){
            return socialNet.get();
        }

        return null;
    }

    public SocialNetwork createSocialNetwork(SocialNetwork socialNetwork, Long providerId){
        Optional<Provider> provider = providerRepository.findById(providerId);

        if(provider.isPresent()){
            socialNetwork.setProvider(provider.get());
            Long socialNetworkTemp = socialNetworkRepository.save(socialNetwork).getId();
            Optional<SocialNetwork> socialNet = socialNetworkRepository.findById(socialNetworkTemp);

            if(socialNet.isPresent()){
                List<SocialNetwork> socialNetList = provider.get().getSocialNetworks();
                socialNetList.add(socialNet.get());
                provider.get().setSocialNetworks(socialNetList);
                providerRepository.save(provider.get());
                return socialNet.get();
            }
        }
    
        return null;   
    }

    public SocialNetwork updateSocialNetwork(SocialNetwork socialNetwork, Long providerId){
        Optional<Provider> provider = providerRepository.findById(providerId);

        if(provider.isPresent()){
            Long socialNetworkTemp = socialNetworkRepository.save(socialNetwork).getId();
            Optional<SocialNetwork> socialNet = socialNetworkRepository.findById(socialNetworkTemp);

            if(socialNet.isPresent())
                return socialNet.get();
        }
    
        return null;  
    }

    public boolean deleteSocialNetwork(Long id){
        Optional<SocialNetwork> socialNet = socialNetworkRepository.findById(id);

        if(socialNet.isPresent()){
            socialNetworkRepository.deleteById(id);
            return true;
        }

        return false; 
    }
    
}

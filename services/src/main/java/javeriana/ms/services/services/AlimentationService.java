package javeriana.ms.services.services;

import javeriana.ms.services.model.Alimentation;
import javeriana.ms.services.repository.AlimentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentationService {

    @Autowired
    private AlimentationRepository alimentationRepository;


    public List<Alimentation> getAllAlimentation() {
        return alimentationRepository.findAll();
    }

    public Alimentation getAlimentationById(Long id) {
        return alimentationRepository.findById(id).orElse(null);
    }

    public void addAlimentation(Alimentation alimentation) {
        alimentationRepository.save(alimentation);
    }

    public void updateAlimentation(Long id, Alimentation alimentation) {
        alimentationRepository.save(alimentation);

    }

    public void deleteAlimentation(Long id) {
        alimentationRepository.deleteById(id);
    }

}
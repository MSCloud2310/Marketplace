package javeriana.ms.services.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javeriana.ms.services.model.Lodging;
import javeriana.ms.services.repository.LodgingRepository;

@Service
public class LodgingService {

    @Autowired
    private LodgingRepository lodgingRepository;

    public List<Lodging> getAllLodgings() {
        return lodgingRepository.findAll();
    }

    public Optional<Lodging> getLodgingById(Long id) {
        return lodgingRepository.findById(id);
    }

    public Lodging createLodging(Lodging lodging) {
        return lodgingRepository.save(lodging);
    }

    public Lodging updateLodging(Long id, Lodging lodging) {
        Optional<Lodging> optionalLodging = lodgingRepository.findById(id);
        if (optionalLodging.isPresent()) {
            Lodging lodgingToUpdate = optionalLodging.get();
            lodgingToUpdate.setRooms(lodging.getRooms());
            lodgingToUpdate.setChef(lodging.isChef());
            lodgingToUpdate.setParking(lodging.isParking());
            lodgingToUpdate.setLaundry(lodging.isLaundry());
            lodgingToUpdate.setPet_friendly(lodging.isPet_friendly());
            return lodgingRepository.save(lodging);
        } else {
            return null;
        }
    }

    public void deleteLodging(Long id) {
        lodgingRepository.deleteById(id);
    }

}


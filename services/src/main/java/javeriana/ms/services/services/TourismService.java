package javeriana.ms.services.services;

import javeriana.ms.services.model.Tourism;
import javeriana.ms.services.repository.TourismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourismService {

    @Autowired
    private TourismRepository tourismRepository;

    public List<Tourism> getAllTourism() {
        return tourismRepository.findAll();
    }

    public Tourism getTourismById(Long id) {
        return tourismRepository.findById(id).orElse(null);
    }

    public Tourism createTourism(Tourism tourism) {
        return tourismRepository.save(tourism);
    }

    public Tourism updateTourism(Long id, Tourism tourism) {
        Tourism existingTourism = tourismRepository.findById(id).orElse(null);
        if (existingTourism == null) {
            return null;
        }
        existingTourism.setType(tourism.getType());
        existingTourism.setDuration(tourism.getDuration());
        existingTourism.setAdditional_info(tourism.getAdditional_info());
        return tourismRepository.save(existingTourism);
    }

    public void deleteTourism(Long id) {
        tourismRepository.deleteById(id);
    }
}

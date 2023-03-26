package proyecto.reserve.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.repository.ReserveRepositoryRest;

@Service
public class ReserveService {
    @Autowired
    private ReserveRepositoryRest reserveRepositoryRest;
    public void addReserve(ReserveRest reserve) {
        reserveRepositoryRest.save(reserve);
    }
    public ReserveRest deleteReserve(Integer id) {
        reserveRepositoryRest.deleteById(id);
        return null;
    }
    public ReserveRest getReserveById(Integer id) {
        return reserveRepositoryRest.findById(id).orElse(null);
    }

    public void updateReserve(Integer id, ReserveRest reserve) {
        reserveRepositoryRest.save(reserve);

    }

}

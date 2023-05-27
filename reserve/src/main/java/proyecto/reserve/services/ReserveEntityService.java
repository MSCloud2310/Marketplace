package proyecto.reserve.services;

import proyecto.reserve.entities.ReserveRest;

import java.util.List;

public interface ReserveEntityService {
    public ReserveRest getEntityById(Integer id);
    public List<ReserveRest> getAllEntities();
   /* public ReserveRest addEntity(ReserveRest entity);
    public boolean updateEntity(ReserveRest entity);
    public boolean deleteEntityById(long id);*/
}

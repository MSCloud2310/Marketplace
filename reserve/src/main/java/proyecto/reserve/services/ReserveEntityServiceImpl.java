package proyecto.reserve.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.repository.ReserveRepositoryRest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReserveEntityServiceImpl implements ReserveEntityService {
    private ReserveRepositoryRest repository;
    public ReserveEntityServiceImpl() {
    }
    @Autowired
    public ReserveEntityServiceImpl(ReserveRepositoryRest repository) {
        this.repository = repository;
    }
    @Override
    public ReserveRest getEntityById(Integer id) {
        return this.repository.findById(id).get();
    }

    @Override
    public List< ReserveRest > getAllEntities() {
        List< ReserveRest > list = new ArrayList< >();
        repository.findAll().forEach(e -> list.add(e));
        return list;
    }
}

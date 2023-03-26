package proyecto.reserve.WebService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import proyecto.reserve.entities.GetReserveRequest;
import proyecto.reserve.entities.GetReserveResponse;
import proyecto.reserve.entities.Reserve;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.repository.ReserveRepository;
import proyecto.reserve.services.ReserveEntityService;

import java.util.ArrayList;
import java.util.List;

public class ReserveEndPoint {
    public static final String NAMESPACE_URI = "http://www.example.com/demo/entities";

    private ReserveEntityService service;


/*
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllReserveRequest")
    @ResponsePayload
    public GetReserveResponse getAllMovies(@RequestPayload GetReserveRequest request) {
        GetReserveResponse response = new GetReserveResponse();
        List<ReserveRest> reserveTypeList = new ArrayList<ReserveRest>();
        List<ReserveRest> reserveEntityList = service.getAllEntities();
        for (ReserveRest entity : reserveEntityList) {
            ReserveRest reserveType = new ReserveRest();
            BeanUtils.copyProperties(entity, reserveType);
            reserveTypeList.add(reserveType);
        }
        response.setReserve();
        return response;
    }*/
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReserveRequest")
    @ResponsePayload
    public GetReserveResponse getReserveById(@RequestPayload GetReserveRequest request) {
        System.out.println(" llego ");
        GetReserveResponse response = new GetReserveResponse();//generated
        ReserveRest reserveEntity = service.getEntityById(request.getId());
        Reserve reserveType = new Reserve();
        BeanUtils.copyProperties(reserveEntity, reserveType);
        //import org.springframework.beans.BeanUtils;
        response.setReserve(reserveType);//generated
        return response;
    }



}

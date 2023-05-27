package proyecto.reserve.WebService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import proyecto.reserve.entities.GetReserveRequest;
import proyecto.reserve.entities.GetReserveResponse;
import proyecto.reserve.entities.Reserve;
import proyecto.reserve.entities.ReserveRest;
import proyecto.reserve.services.ReserveEntityService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


@Endpoint
public class ReserveEndPoint {
    public static final String NAMESPACE_URI = "http://www.example.com/demo/entities";
    @Autowired
    private ReserveEntityService service;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReserveRequest")
    @ResponsePayload
    public GetReserveResponse getReserveById(@RequestPayload GetReserveRequest request) throws DatatypeConfigurationException {
        System.out.println(" llego ");
        GetReserveResponse response = new GetReserveResponse();//generated
        ReserveRest reserveEntity = service.getEntityById(request.getId());
        System.out.println("sss"+reserveEntity.getId());
        System.out.println("sss"+reserveEntity.getIdUser());
        System.out.println("sss"+reserveEntity.getIdService());
        System.out.println("sss"+reserveEntity.getFinishDate());
        Reserve reserveType = new Reserve();
        reserveType.setId(reserveEntity.getId());
        reserveType.setUserID(reserveEntity.getIdUser());
        reserveType.setIdService(reserveEntity.getIdService());
        response.setReserve(reserveType);//generated
        return response;
    }



}

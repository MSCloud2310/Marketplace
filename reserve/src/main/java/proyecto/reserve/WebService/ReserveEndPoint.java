package proyecto.reserve.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import proyecto.reserve.entities.GetReserveRequest;
import proyecto.reserve.entities.GetReserveResponse;
import proyecto.reserve.repository.ReserveRepository;

public class ReserveEndPoint {
    private static final String NAMESPACE_URI = "http://www.example.com/demo/entities";

    private ReserveRepository reserveRepository;

    @Autowired
    public ReserveEndPoint(ReserveRepository countryRepository) {
        this.reserveRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReserveRequest")
    @ResponsePayload
    public GetReserveResponse getReserve(@RequestPayload GetReserveRequest request) {
        GetReserveResponse response = new GetReserveResponse();
        response.setReserve(reserveRepository.findReserveById(request.getId()));

        return response;
    }
}

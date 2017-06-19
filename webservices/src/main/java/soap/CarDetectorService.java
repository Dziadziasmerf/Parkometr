package soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface CarDetectorService {


    @WebMethod
    String setSpaceTaken(Integer id);

    @WebMethod
    void setSpaceFree(Integer id);
}

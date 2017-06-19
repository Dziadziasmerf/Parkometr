package soap.soap;

import soap.CarDetectorService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/webservices/soap?wsdl");
        QName qname = new QName("http://soap/", "CardDetectorService");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Zajęcie(za)/zwolnienie miejsca(zw)? ");
            String option = br.readLine();

            System.out.print("Wpisz id miejsca: ");
            int spaceId = Integer.valueOf(br.readLine());

            Service service = Service.create(url, qname);
            CarDetectorService carDetectorService = service.getPort(CarDetectorService.class);

            if (option.equals("za"))
                carDetectorService.setSpaceTaken(spaceId);
            else if (option.equals("zw"))
                carDetectorService.setSpaceFree(spaceId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package rest.meter;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import persistence.model.Ticket;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class TicketServiceClient {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Wpisz id miejsca: ");
            int spaceId = Integer.valueOf(br.readLine());

            System.out.print("Wpisz czas ważności biletu (w minutach): ");
            int duration = Integer.valueOf(br.readLine());

            Ticket ticket = new Ticket();

            ticket.setBuyDate(new Date());
            ticket.setDurationMin(duration);
            ticket.setValid(true);

            ResteasyClient client = new ResteasyClientBuilder().build();

            ResteasyWebTarget target = client
                    .target("http://localhost:8080/webservices/rest/spaces/" + spaceId + "/ticket");

            Response response = target.request().post(
                    Entity.entity(ticket, "application/json"));

            if (response.getStatus() != 200 && response.getStatus() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Server response : \n");
            System.out.println(response.readEntity(String.class));

            response.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}

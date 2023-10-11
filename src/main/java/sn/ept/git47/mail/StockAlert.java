package sn.ept.git47.mail;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class StockAlert {
    @PersistenceContext(name = "projet_matiere")
    private EntityManager entityManager;
    @Inject
    private EmailSender emailSender;

    @Schedule(hour = "*", minute = "*/30", persistent = false)
    public void sendStockAlerts() {
        try {
            TypedQuery<Object[]> query = entityManager.createNamedQuery("Stock.getStockStatus", Object[].class);
            List<Object[]> stockStatusResults = query.getResultList();

            StringBuilder tableBuilder = new StringBuilder();
            tableBuilder.append("<p>Stock Status:</p><br><br>");
            tableBuilder.append("<table><tr><th>Product Name</th><th>Quantity</th></tr>");

            for (Object[] result : stockStatusResults) {
                String productName = (String) result[0];
                int stockQuantity = (int) result[1];

                tableBuilder.append("<tr><td>").append(productName).append("</td><td>").append(stockQuantity).append("</td></tr>");
            }

            tableBuilder.append("</table>");

            String emailBody = "<html><body>" + tableBuilder.toString() + "</body></html>";

            emailSender.sendEmail("nabylmoustaphadia@gmail.com", "Stock Alert", emailBody);
        } catch (MessagingException e) {
            System.out.println("Error on sending stock alert mail: " + e.getMessage());
        }
    }
}

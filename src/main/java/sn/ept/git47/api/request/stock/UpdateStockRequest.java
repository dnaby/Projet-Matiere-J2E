package sn.ept.git47.api.request.stock;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createStockRequest")
public class UpdateStockRequest {
    private int quantite;;

    public UpdateStockRequest() {
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

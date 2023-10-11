package sn.ept.git47.api.request.stock;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createStockRequest")
public class NewStockRequest {
    private String productName;
    private String magasinName;
    private int quantite;

    public NewStockRequest() {}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMagasinName() {
        return magasinName;
    }

    public void setMagasinName(String magasinName) {
        this.magasinName = magasinName;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

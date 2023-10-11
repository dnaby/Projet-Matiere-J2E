package sn.ept.git47.api.response.stock;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Marque;
import sn.ept.git47.entity.Stock;

@XmlRootElement(name = "createStockResponse")
public class StockResponse {
    private String msg;
    private Stock stock;

    public StockResponse() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}

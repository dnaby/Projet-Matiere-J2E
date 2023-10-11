package sn.ept.git47.api.request.stock;

import jakarta.xml.bind.annotation.XmlRootElement;
import sn.ept.git47.entity.Stock;

import java.util.List;

@XmlRootElement(name = "syncStockRequest")
public class SyncStock {
    private List<Stock> stocks;

    public SyncStock() {
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}

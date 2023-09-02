package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import sn.ept.git47.entity.Stock;

@Stateless
public class StockFacade extends AbstractFacade<Stock> {
    public StockFacade() {
        super(Stock.class);
    }
}

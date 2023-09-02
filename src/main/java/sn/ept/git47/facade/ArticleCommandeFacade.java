package sn.ept.git47.facade;

import jakarta.ejb.Stateless;
import sn.ept.git47.entity.ArticleCommande;
import sn.ept.git47.entity.ArticleCommandeId;

@Stateless
public class ArticleCommandeFacade extends AbstractFacade<ArticleCommande> {
    public ArticleCommandeFacade() {
        super(ArticleCommande.class);
    }

    public ArticleCommande findById(ArticleCommandeId articleCommandeId) {
        return getEntityManager().find(ArticleCommande.class, articleCommandeId);
    }
}

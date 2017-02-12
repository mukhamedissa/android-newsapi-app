package mukhamedissa.kz.newsapiapp.util.db.orm;

/**
 * Created by Mukhamed Issa on 2/12/17.
 */

public class ORMFactory {

    private ORMFactory() {
        throw new IllegalStateException(ORMFactory.class.getCanonicalName() + " cannot be instantiated");
    }

    private static SourceORM sSourceORM = null;
    private static ArticleORM sArticleORM = null;

    public static SourceORM getSourceORM() {
        if(sSourceORM == null){
            sSourceORM = new SourceORM();
        }

        return sSourceORM;
    }

    public static ArticleORM getArticleORM() {
        if(sArticleORM == null) {
            sArticleORM = new ArticleORM();
        }

        return sArticleORM;
    }

}

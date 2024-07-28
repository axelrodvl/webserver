package co.axelrod.webserver.blog.article.model;

import java.util.List;

public class ArticleDescription {
    private final String title;
    private final List<String> tags;
    private final String url;

    public ArticleDescription(String title, List<String> tags, String url) {
        this.title = title;
        this.tags = tags;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getUrl() {
        return url;
    }
}

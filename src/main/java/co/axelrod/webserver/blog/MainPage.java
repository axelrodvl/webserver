package co.axelrod.webserver.blog;

import co.axelrod.webserver.blog.article.ArticleService;

import java.util.stream.Collectors;

public class MainPage {


    public static String getArticles(String path) {
        String articles = ArticleService.parseArticles(path)
                .stream()
                .map(articleDescription -> {
                    return "<li>"
                            + "<a href=\"" + "article/" + articleDescription.getUrl() + "\">" + articleDescription.getTitle() + "</a>"
                            + "</li>";
                })
                .collect(Collectors.joining());

        return Template.getTemplate(path).replace("PLACEHOLDER", "<h1>Articles</h1><ul>" + articles + "</ul>");
    }
}

package co.axelrod.webserver.blog;

import co.axelrod.webserver.blog.article.ArticleService;
import co.axelrod.webserver.blog.article.model.ArticleDescription;
import co.axelrod.webserver.blog.project.ProjectService;
import co.axelrod.webserver.blog.project.model.ProjectDescription;

import java.util.stream.Collectors;

public class MainPage {
    public static String getArticles(String path) {
        String articles = ArticleService.parseArticles(path)
                .stream()
                .map(MainPage::convertArticleToHtml)
                .collect(Collectors.joining());

        String projects = getProjects(path);

        return Template.getTemplate(path).replace("PLACEHOLDER",
                "<h3 style=\"text-align: center; font-weight: normal;\">Articles</h3>"
                        + articles
                        + "<h3 style=\"text-align: center; font-weight: normal;\">Projects</h3>"
                        + projects);
    }

    private static String convertArticleToHtml(ArticleDescription articleDescription) {
        return "<a href=\"" + "article/" + articleDescription.getUrl() + "\" style=\"text-decoration: none;\">"
                + "<div class=\"list-card\">"
                + "<p style=\"color: #000; margin-top: 0;\">" + articleDescription.getTitle() + "</p>"
                + "<p style=\"margin-top: 0; margin-bottom: 0;\">" + articleDescription.getTags().stream()
                .map(tag -> "<span class=\"tag\">" + tag.strip() + "</span>")
                .collect(Collectors.joining(" ")) + "</p>"
                + "</div>"
                + "</a>";
    }

    public static String getProjects(String path) {
        return ProjectService.parseProjects(path)
                .stream()
                .map(MainPage::convertProjectToHtml)
                .collect(Collectors.joining());
    }

    private static String convertProjectToHtml(ProjectDescription projectDescription) {
        return "<a href=\"" + projectDescription.getLink() + "\" style=\"text-decoration: none;\">"
                + "<div class=\"list-card\">"
                + "<p style=\"color: #000;\">" + projectDescription.getDescription() + "</p>"
                + "<p>" + projectDescription.getTags().stream()
                .map(tag -> "<span class=\"tag\">" + tag.strip() + "</span>")
                .collect(Collectors.joining(" ")) + "</p>"
                + "</div>"
                + "</a>";
    }
}

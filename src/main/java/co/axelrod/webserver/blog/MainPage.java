package co.axelrod.webserver.blog;

import co.axelrod.webserver.blog.article.ArticleService;
import co.axelrod.webserver.blog.project.ProjectService;

import java.util.stream.Collectors;

public class MainPage {


    public static String getArticles(String path) {
        String articles = ArticleService.parseArticles(path)
                .stream()
                .map(articleDescription -> {
                    return "<a href=\"" + "article/" + articleDescription.getUrl() + "\" style=\"text-decoration: none;\">"
                            + "<div class=\"list-card\">"
                            + "<p style=\"color: #000;\">" + articleDescription.getTitle() + "</p>"
                            + "<p>" + articleDescription.getTags().stream()
                            .map(tag -> "<span class=\"tag\">" + tag.strip() + "</span>")
                            .collect(Collectors.joining(" ")) + "</p>"
                            + "</div>"
                            + "</a>";

                })
                .collect(Collectors.joining());

        String projects = getProjects(path);

        return Template.getTemplate(path).replace("PLACEHOLDER",
                "<h3 style=\"text-align: center; font-weight: normal;\">Articles</h3>"
                        + articles
                        + "<h3 style=\"text-align: center; font-weight: normal;\">Projects</h3>"
                        + projects);
    }

    public static String getProjects(String path) {
        return ProjectService.parseProjects(path)
                .stream()
                .map(projectDescription -> {
                    return "<a href=\"" + projectDescription.getLink() + "\" style=\"text-decoration: none;\">"
                            + "<div class=\"list-card\">"
                            + "<p style=\"color: #000;\">" + projectDescription.getDescription() + "</p>"
                            + "<p>" + projectDescription.getTags().stream()
                                .map(tag -> "<span class=\"tag\">" + tag.strip() + "</span>")
                                .collect(Collectors.joining(" ")) + "</p>"
                            + "</div>"
                            + "</a>";
                })
                .collect(Collectors.joining());
    }
}

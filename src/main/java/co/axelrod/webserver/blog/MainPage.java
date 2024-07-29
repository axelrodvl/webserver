package co.axelrod.webserver.blog;

import co.axelrod.webserver.blog.article.ArticleService;
import co.axelrod.webserver.blog.project.ProjectService;

import java.util.stream.Collectors;

public class MainPage {


    public static String getArticles(String path) {
        String articles = ArticleService.parseArticles(path)
                .stream()
                .map(articleDescription -> {
                    return "<div style=\"text-align: center; padding: 1em; margin-top: 1em; margin-bottom: 1em; background-color: #fff; border-radius: .3rem; border-style: solid; border-width: 1px; border-color: #eee\">"
                            + "<h3>"
                            + "<a href=\"" + "article/" + articleDescription.getUrl() + "\">" + articleDescription.getTitle() + "</a>"
                            + "</h3>"
                            + "<p>" + articleDescription.getTags().stream().collect(Collectors.joining(",")) + "</p>"
                            + "</div>";
                })
                .collect(Collectors.joining());

        String projects = getProjects(path);

        return Template.getTemplate(path).replace("PLACEHOLDER",
                "<h2 style=\"text-align: center;\">Articles</h2>"
                        + articles
                        + "<h2 style=\"text-align: center;\">Projects</h2>"
                        + projects);
    }

    public static String getProjects(String path) {
        return ProjectService.parseProjects(path)
                .stream()
                .map(projectDescription -> {
                    return "<div style=\"text-align: center; padding: 1em; margin-top: 1em; margin-bottom: 1em; background-color: #fff; border-radius: .3rem; border-style: solid; border-width: 1px; border-color: #eee\">"
                            + "<h3>"
                                + "<a href=\"" + projectDescription.getLink() + "\">" + projectDescription.getName()  + "</a>"
                            + "</h3>"
                            + "<p>" + projectDescription.getDescription() + "</p>"
                            + "<p>Technologies: " + projectDescription.getTags().stream().collect(Collectors.joining(",")) + "</p>"
                            + "</div>";
                })
                .collect(Collectors.joining());
    }
}

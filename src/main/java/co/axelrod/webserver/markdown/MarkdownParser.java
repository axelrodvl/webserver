package co.axelrod.webserver.markdown;

import co.axelrod.webserver.blog.Template;

import java.util.Arrays;
import java.util.Stack;

public class MarkdownParser {
    public static byte[] convertToHtml(String path, byte[] markdown) {
        String raw = new String(markdown);

        Stack<String> stack = new Stack<>();

        StringBuilder response = new StringBuilder();

        int headerLinesLeft = 5;
        for (String line : raw.split("\n")) {
            if (headerLinesLeft != 0) {
                if (headerLinesLeft == 5) {
                    response
                            .append("<h2>")
                            .append(line.replace("title: ", ""))
                            .append("</h2>");
                }
                if (headerLinesLeft == 4) {
                    response
                            .append("<h3>Tags</h3>")
                            .append("<ul>");

                    Arrays.stream(line.replace("tags: ", "").split(",")).forEach(tag -> {
                        response.append("<li>").append(tag).append("</li>");
                    });

                    response.append("</ul>");
                }
                headerLinesLeft--;
                continue;
            }

            convertString(line, response, stack);
        }

        return Template.getTemplate(path).replace("PLACEHOLDER", response).getBytes();
    }

    static void convertString(String line, StringBuilder response, Stack<String> stack) {
        if (line == null || line.isEmpty() || line.isBlank()) {
            return;
        }

        String firstToken = line.split(" ")[0];
        if (firstToken == null || firstToken.isBlank() || firstToken.isEmpty()) {
            firstToken = line.strip().split(" ")[0];
            line = line.strip();
        }

        if (!stack.isEmpty()) {
            if ("pre".equals(stack.peek())) {
                if ("```".equals(firstToken)) {
                    response.append("</pre>");
                    stack.pop();
                    return;
                }
                response.append(line).append("\n");
            }
            return;
        }

        String tag = "p";

        if ("#".equals(firstToken)) {
            tag = "h1";
            line = line.substring(2);
        }
        if ("##".equals(firstToken)) {
            tag = "h2";
            line = line.substring(3);
        }
        if ("###".equals(firstToken)) {
            tag = "h3";
            line = line.substring(4);
        }
        if ("####".equals(firstToken)) {
            tag = "h4";
            line = line.substring(5);
        }
        if ("#####".equals(firstToken)) {
            tag = "h5";
            line = line.substring(6);
        }

        if ("-".equals(firstToken)) {
            tag = "li";
            line = line.substring(2);
        }

        if ("```".equals(firstToken) || "```".equals(line)) {
            response.append("<pre>");
            stack.push("pre");
            return;
        }

        line = processLink(line);
        line = processBold(line);
        line = processCode(line);

        response
                .append("<").append(tag).append(">")
                .append(line)
                .append("</").append(tag).append(">");
    }

    private static String processCode(String line) {
        if (line.contains("`") && !line.contains("```")) {
            line = line.replaceFirst("`", "<code>");
            line = line.replaceFirst("`", "</code>");
            return processCode(line);
        }
        return line;
    }

    private static String processBold(String line) {
        if (line.contains("**")) {
            line = line.replaceFirst("\\*\\*", "<b>");
            line = line.replaceFirst("\\*\\*", "</b>");
            return processBold(line);
        }
        return line;
    }

    private static String processLink(String line) {
        if (line.contains("[") && line.contains("]") && line.contains("(") && line.contains(")")) {
            String linkName = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            String linkHref = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
            return line.substring(0, line.indexOf("["))
                    + "<a href=\"" + linkHref + "\">"
                    + linkName
                    + "</a>"
                    + line.substring(line.indexOf(")") + 1);
        }
        return line;
    }
}

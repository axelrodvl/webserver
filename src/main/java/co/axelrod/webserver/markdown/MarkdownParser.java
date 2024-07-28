package co.axelrod.webserver.markdown;

import java.util.Stack;

public class MarkdownParser {
    private static final String START_OF_HTML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\"/>\n" +
            "        <link rel=\"icon\" href=\"favicon.ico\"/>\n" +
            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
            "        <meta name=\"theme-color\" content=\"#000000\"/>\n" +
            "        <meta name=\"description\" content=\"Vadim Axelrod\"/>\n" +
            "        <title>Vadim Axelrod</title>\n" +
            "<style>\n" +
            "            .container, .container-fluid, .container-lg, .container-md, .container-sm, .container-xl {\n" +
            "                width: 100%;\n" +
            "                padding-right: 15px;\n" +
            "                padding-left: 15px;\n" +
            "                margin-right: auto;\n" +
            "                margin-left:auto\n" +
            "            }\n" +
            "\n" +
            "            @media (min-width: 576px) {\n" +
            "                .container, .container-sm {\n" +
            "                    max-width:540px\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            @media (min-width: 768px) {\n" +
            "                .container, .container-md, .container-sm {\n" +
            "                    max-width:720px\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            @media (min-width: 992px) {\n" +
            "                .container, .container-lg, .container-md, .container-sm {\n" +
            "                    max-width:960px\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            @media (min-width: 1200px) {\n" +
            "                .container, .container-lg, .container-md, .container-sm, .container-xl {\n" +
            "                    max-width:1140px\n" +
            "                }\n" +
            "            }\n" +
            "        </style>" +
            "    </head>\n" +
            "    <body><div class=\"container\">";

    private static final String END_OF_HTML = "</div></body>\n" +
            "</html>";

    public static byte[] convertToHtml(byte[] markdown) {
        String raw = new String(markdown);

        Stack<String> stack = new Stack<>();

        StringBuilder response = new StringBuilder();
        response.append(START_OF_HTML);

        for (String line : raw.split("\n")) {
            convertString(line, response, stack);
        }

        response.append(END_OF_HTML);
        return response.toString().getBytes();
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

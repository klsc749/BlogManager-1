package com.example.blogmanager.util;
import java.util.Collections;
import java.util.Set;
public class MarkDownUtil {

    public String markdownToPlainText(String markdownContent) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);
        Renderer renderer = Renderer.builder().nodeRendererFactory(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(Set<NodeRenderingHandler<?>> nodeRenderingHandlers) {
                return new TextContentNodeRenderer();
            }
        }).build();
        String plainText = renderer.render(document);

        // Remove extra spaces, newlines, and other unwanted characters
        plainText = plainText.replaceAll("\\s{2,}", " ").trim();

        return plainText;
    }
}

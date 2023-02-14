package com.ssafy.common.util;

import com.lowagie.text.Image;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageReplacedElementFactory implements ReplacedElementFactory {

    private final ReplacedElementFactory replacedElementFactory;

    private final String path;

    public ImageReplacedElementFactory(ReplacedElementFactory replacedElementFactory, String path) {
        this.replacedElementFactory = replacedElementFactory;
        this.path = path;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox, UserAgentCallback callback, int cssWidth, int cssHeight) {
        Element element = blockBox.getElement();
        String nodeName = element.getNodeName();

        if (nodeName.equals("img")) {
            ITextFSImage fsImage;
            try {
                String imageFileName = element.getAttribute("src").split("/image/")[1];
                fsImage = new ITextFSImage(
                        Image.getInstance(
                                Files.readAllBytes(
                                        Paths.get(path + "/static/image/" + imageFileName)
                                )
                        )
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (cssWidth != -1 || cssHeight != -1) {
                fsImage.scale(cssWidth, cssHeight);
            }

            return new ITextImageElement(fsImage);
        }

        return replacedElementFactory.createReplacedElement(layoutContext, blockBox, callback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        replacedElementFactory.reset();
    }

    @Override
    public void remove(Element e) {
        replacedElementFactory.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        replacedElementFactory.setFormSubmissionListener(listener);
    }
}

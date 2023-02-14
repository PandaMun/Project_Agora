package com.ssafy.common.util;

import com.lowagie.text.pdf.BaseFont;
import org.springframework.core.io.ClassPathResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;

public class PdfGenerator {

    public static String generateFromHtml(String path, String fileName, String html) {
        String saveFilePath = String.format("%s/%s.pdf", path + "/static/temp", fileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.getSharedContext().setReplacedElementFactory(new ImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), path));
            renderer.setDocumentFromString(html);
            renderer.getFontResolver().addFont(
                    new ClassPathResource("/static/font/NanumBarunGothic.ttf").getURL().toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            renderer.layout();
            renderer.createPDF(fileOutputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return saveFilePath;
    }
}

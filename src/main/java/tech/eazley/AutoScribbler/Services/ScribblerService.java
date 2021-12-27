package tech.eazley.AutoScribbler.Services;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ScribblerService {
    public byte[] replace(String templateString, String replaceString, InputStream inputStream) throws IOException,
            InvalidFormatException,
            org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        try {

            /**
             * if uploaded doc then use HWPF else if uploaded Docx file use
             * XWPFDocument
             */
            /**XWPFDocument doc = new XWPFDocument(
                    OPCPackage.open("./doc/doc.docx"));**/

            XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputStream));

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains(templateString)) {
                            text = text.replace(templateString, replaceString);//your content
                            r.setText(text, 0);
                        }
                    }
                }
            }

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains(templateString)) {
                                    text = text.replace(templateString, replaceString);
                                    r.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.write(outputStream);
            inputStream.close();
            doc.close();
            outputStream.close();

            return outputStream.toByteArray();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

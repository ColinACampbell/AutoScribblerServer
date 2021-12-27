package tech.eazley.AutoScribbler.Controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.eazley.AutoScribbler.Services.ScribblerService;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/scribbler")
public class ScribblerController {

    @Autowired
    ScribblerService scribblerService;

    @GetMapping("/")
    public String test()
    {
        return "Hello World";
    }

    @PostMapping("/")
    public byte[] Scribble(@RequestBody HashMap<String,Object> submittedFiles, HttpServletResponse response) throws IOException, InvalidFormatException {
        ArrayList<String> files = (ArrayList<String>) submittedFiles.get("files");
        ArrayList<byte[]> data = new ArrayList<>();

        for (String base64String: files)
        {
            String base64Value = base64String.split(",")[1];
            byte[] bytes = Base64.getDecoder().decode(base64Value);
            data.add(bytes);
        }

        try {
            byte[] newData = scribblerService.replace("<name>","Colin Campbell",new ByteArrayInputStream(data.get(0)));
            Map<String,byte[]> mapReporte = new HashMap<>();

            mapReporte.put("File",newData);

            // get your file as InputStream
            //InputStream is = new ByteArrayInputStream(newData);



            //zipOutputStream.close();
            //zipOutputStream.finish();
            //zipOutputStream.flush();

            // copy it to response's OutputStream

            response.setHeader("Content-Disposition", "attachment; filename=demo.zip");
            response.setContentType("application/zip");
            //IOUtils.copy(new ByteArrayInputStream(zipData),response.getOutputStream());
            //org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());

            //response.flushBuffer();

            return listBytesToZip(mapReporte);
        } catch (IOException ex) {
            //log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            ex.printStackTrace();
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    protected byte[] listBytesToZip(Map<String, byte[]> mapReporte) throws IOException {
        String extension = ".docx";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        for (Map.Entry<String, byte[]> reporte : mapReporte.entrySet()) {
            ZipEntry entry = new ZipEntry(reporte.getKey() + extension);
            entry.setSize(reporte.getValue().length);
            zos.putNextEntry(entry);
            zos.write(reporte.getValue());
        }
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }
}

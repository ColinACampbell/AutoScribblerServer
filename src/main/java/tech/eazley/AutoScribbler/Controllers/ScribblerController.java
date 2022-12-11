package tech.eazley.AutoScribbler.Controllers;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.eazley.AutoScribbler.Models.HttpModels.ScribbleRequestBody;
import tech.eazley.AutoScribbler.Services.ScribblerService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
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
    public byte[] Scribble(@RequestBody ScribbleRequestBody requestBody, HttpServletResponse response) throws IOException, InvalidFormatException {
        ArrayList<String> files = requestBody.getFiles();
        ArrayList<ScribbleRequestBody.TemplateDefinitionRequestBody> templateDefinitions = requestBody.getTemplateDefinitions();

        ArrayList<byte[]> data = new ArrayList<>();

        for (String base64String: files)
        {
            String base64Value = base64String.split(",")[1];
            byte[] bytes = Base64.getDecoder().decode(base64Value);
            data.add(bytes);
        }

        try {

            for (ScribbleRequestBody.TemplateDefinitionRequestBody templateDefinition: templateDefinitions)
            {
                for (int i = 0; i < data.size(); i ++)
                {
                    byte[] newData = scribblerService.replace(templateDefinition.getItemName(),templateDefinition.getValue(),new ByteArrayInputStream(data.get(i)));
                    //data.remove(i);
                    data.set(i,newData);
                }
            }

            //byte[] newData = scribblerService.replace("<name>","Colin Campbell",new ByteArrayInputStream(data.get(0)));
            Map<String,byte[]> mapReporte = new HashMap<>();

            int pos = 0;
            for (byte[] file : data)
            {
                mapReporte.put(requestBody.getFileNames().get(pos),file);
                pos++;
            }

            Date date = new Date();
            String fileName = "AutoScribbler-"+ date.getTime()+".zip";
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            response.setContentType("application/zip");

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

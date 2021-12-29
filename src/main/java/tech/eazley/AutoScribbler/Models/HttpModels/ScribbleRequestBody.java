package tech.eazley.AutoScribbler.Models.HttpModels;

import java.util.ArrayList;



public class ScribbleRequestBody {

    public static class TemplateDefinitionRequestBody
    {
        String dataType;
        String value;
        String itemName;

        public TemplateDefinitionRequestBody()
        {

        }

        public TemplateDefinitionRequestBody(String dataType, String value, String itemName)
        {
            this.dataType = dataType;
            this.value = value;
            this.itemName = itemName;
        }

        public String getDataType() {
            return dataType;
        }

        public String getItemName() {
            return itemName;
        }

        public String getValue() {
            return value;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

     private ArrayList<String> files;
     private ArrayList<TemplateDefinitionRequestBody> templateDefinitions;
     private ArrayList<String> fileNames;

    public ScribbleRequestBody()
    {

    }

    public ScribbleRequestBody(ArrayList<String> files, ArrayList<TemplateDefinitionRequestBody> templateDefinitions, ArrayList<String> fileNames)
    {
        this.files = files;
        this.templateDefinitions = templateDefinitions;
        this.fileNames = fileNames;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public ArrayList<TemplateDefinitionRequestBody> getTemplateDefinitions() {
        return templateDefinitions;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }

    public void setTemplateDefinitions(ArrayList<TemplateDefinitionRequestBody> templateDefinitions) {
        this.templateDefinitions = templateDefinitions;
    }

    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }
}

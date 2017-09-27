/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static org.ats.gejagular.GenarateService.genService;
import org.ats.gejagular.entity.Badge;


/**
 * *
 * @author Luck Cherubin
 * @Company ATS-Group *
 */
public final class JavaEntityToNgForm {
//private static final Logger logger = LoggerFactory.getLogger(HotmanApplication.class);
    //mapping java types with Type Script types 
    private Map<String, String> javaToTs = fillJavaToTsMap();
    private Map<String, String> javaToHtmlInput = fillJavaToHtmlInputMap();
    private Map<String, String> attributeToTsMap = new HashMap<>();
    private Map<String, String> attributeToHtmlMap = new HashMap<>();

    public JavaEntityToNgForm() {
    }

    private Map<String, String> fillJavaToHtmlInputMap() {
        Map<String, String> javaToHtml = new HashMap<>();
        javaToHtml.put("java.lang.Long", "number");
        javaToHtml.put("java.lang.String", "text");
        javaToHtml.put("java.time.ZonedDateTime", "datetime-local");
        javaToHtml.put("java.time.LocalDate", "date");
        javaToHtml.put("java.lang.Integer", "number");
        javaToHtml.put("java.lang.Boolean", "checkbox");
        javaToHtml.put("boolean", "checkbox");
        return javaToHtml;
    }
/**
 * 
 * @return Java correspontance Html 
 */
    private Map<String, String> fillJavaToTsMap() {
        Map<String, String> javaToTs1 = new HashMap<>();
        javaToTs1.put("java.lang.Long", "number");
        javaToTs1.put("java.lang.String", "string");
        javaToTs1.put("java.time.ZonedDateTime", "any");
        javaToTs1.put("java.time.LocalDate", "any");
        javaToTs1.put("java.lang.Integer", "number");
        javaToTs1.put("java.lang.Boolean", "boolean");
        return javaToTs1;
    }

    public Map<String, Map<String, String>> getAttributeMap(Class clazz) {
        Map<String, Map<String, String>> globalMap = new HashMap<>();
        String tsType = "";
        String inputType = "";
        //   System.err.println("\n\n\n");
        // get fields name via getter mathod in entity class
        for (Method method1 : clazz.getMethods()) {
            if ((method1.getName().startsWith("get"))
                    && (!method1.getName().equals("getClass"))
                    || (method1.getName().startsWith("is"))) {
                String typeName = method1.getGenericReturnType().getTypeName();
                String attributeName = method1.getName();
                if (attributeName.startsWith("get")) {
                    attributeName = attributeName.substring(3);
                } else {
                    attributeName = attributeName.substring(2);
                }
                attributeName = attributeName.replaceFirst(attributeName.substring(0, 1), attributeName.substring(0, 1).toLowerCase());
                if (javaToTs.get(typeName) != null) {
                    tsType = javaToTs.get(typeName);
                    
                } else {
                    int debut = typeName.lastIndexOf(".") + 1;
                    int fin = typeName.length();
                    if (typeName.endsWith(">")) {
                        tsType = typeName.substring(debut, fin - 1) + "[]";
                    } else {
                        tsType = typeName.substring(debut, fin);
                    }

                }
                if (javaToHtmlInput.get(typeName) != null) {
                    inputType = javaToHtmlInput.get(typeName);
                } else {
                    inputType = "select";
                }
                attributeToTsMap.put(attributeName, tsType);
                attributeToHtmlMap.put(attributeName, inputType);
            }
        }
        globalMap.put("tsAttribute", attributeToTsMap);
        globalMap.put("htmlAttribute", attributeToHtmlMap);
        return globalMap;
    }

    public String generateNgModelText(Class clazz) {
        String text = "export class " + clazz.getSimpleName() + " {\n"
                + "    constructor(\n";
        for (Map.Entry<String, String> entry : getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += ("        public " + key + "?: " + value + ",\n");
        }
        return text + ")\n{\n}";
    }

    /**
     *
     * @param clazzs
     * @return * Map<String, String> generateNgModelText(Set<Class> clazzs) {
     * Map<String, String> modelsTextMap = new HashMap<>(); for (Class clazz :
     * clazzs) { // System.err.println("\n\n\n"); // get fields name via getter
     * mathod in entity class
     *
     * String text = "export class " + clazz.getSimpleName() + " {\n" + "
     * constructor(\n"; for (Map.Entry<String, String> entry :
     * getAttributeMap(clazz).entrySet()) { String key = entry.getKey(); String
     * value = entry.getValue(); // System.out.println(key + " : " + value);
     *
     * text += (" public " + key + "?: " + value + ",\n"); } text = text +
     * ")\n{\n}"; modelsTextMap.put(clazz.getSimpleName(), text);
     *
     * }
     * return modelsTextMap; }
     */
    public void generateModelFile(Class clazz) {
        String contenu = generateNgModelText(clazz);
     //baseEntityDir(clazz);
     String dest= UtiGen.destEnttity(clazz);
        //   System.out.println(contenu + "\n\n\n");
        Path fichier = Paths.get(dest+"/"+clazz.getSimpleName().toLowerCase() + ".model.ts");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, charset)) {
            writer.write(contenu, 0, contenu.length());
            writer.close();
        } catch (IOException ioe) {
        }
        // contenu = "";
    }

    /**
     *
     * @param clazzs * void generateModelFile(Set<Class> clazzs) {
     * Map<String, String> contenu = generateNgModelText(clazzs);
     * //System.out.println(contenu); contenu.entrySet().forEach((entry) -> {
     * String key = entry.getKey(); String value = entry.getValue(); Path
     * fichier = Paths.get(key.toLowerCase() + ".model.ts"); Charset charset =
     * Charset.forName("UTF-8"); try (BufferedWriter writer =
     * Files.newBufferedWriter(fichier, charset)) { writer.write(value, 0,
     * value.length()); } catch (IOException ioe) { } });
     *
     * }
     */
    public String generateFormText(Class clazz) {
        String header;
        String fieldName = null;
        String inputType = "";
        String entity = clazz.getSimpleName().toLowerCase();
        String formControlText = "";
        for (Map.Entry<String, String> entry : getAttributeMap(clazz).get("htmlAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            fieldName = key;
            inputType = value;
            formControlText
                    += "<div class=\"form-group\">\n"
                    + "            <label class=\"form-control-label\" for=\"field_" + fieldName + "\">" + fieldName.toUpperCase() + "</label>\n"
                    + "            <input type=\"" + inputType + "\" class=\"form-control\" name=\"" + fieldName + "\" id=\"field_" + fieldName + "\"\n"
                    + "                [(ngModel)]=\"" + entity + "." + fieldName + "\" required />\n"
                    + "</div>\n";

        }

        // System.err.println(formControlText);
        return formControlText;
    }

    public  void generateHtmlFile(Class clazz) {
        String contenu = generateFormText(clazz);
        //   System.out.println(contenu + "\n\n\n");
     String dest= UtiGen.destEnttity(clazz);
        Path fichier = Paths.get(dest+"/"+clazz.getSimpleName().toLowerCase() + ".component.html");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, charset)) {
            writer.write(contenu, 0, contenu.length());
            writer.close();
        } catch (IOException ioe) {
        }
        // contenu = "";
    }
    
    public void outAllFile(Object clazz){
        generateAll((Class) clazz);
    }

    public void generateAll(Class clazz) {
        generateModelFile(clazz);
        generateHtmlFile(clazz);
        genService(clazz);
    }
    public static void main(String[] args) {
        JavaEntityToNgForm a=new JavaEntityToNgForm();
        
        System.out.println("out work");
        Class cs=Badge.class;
        System.out.println("show class"+cs.getTypeName());
      //  org.ats.gejagular.
        StringJoiner joiner= new StringJoiner(".");
        joiner.add("org").add("ats").add("gejagular");
        File fic=new File("./src/org/ats/gejagular/entity");
        File[] lisf=fic.listFiles();
        for (File lisf1 : lisf) {
            System.out.println("liste fille"+ lisf1.getName());
            System.out.println("liste fille"+ lisf1.getName().replace("java", "class"));
            String t=lisf1.getName().replace(".java", "");
            Class c;
            
            System.out.println("Class "+t);
            try {
                c = Class.forName("org.ats.gejagular.entity."+t);
                System.out.println("Class convert "+c);
                   a.outAllFile(c);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JavaEntityToNgForm.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
        System.out.println("Repertoire");
        Path path =Paths.get("./", "src/org/ats/gejagular/entity");
        
        try(Stream<Path> strem=Files.list(path)) {
            strem.filter(paths -> path.toFile().isDirectory()).forEach(System.out::println);
            
        } catch (Exception e) {
        }
         System.out.println("Sous repertoire");
        Path path1 =Paths.get("./", "src/org/ats/gejagular/entity");
        
        try(Stream<Path> strem=Files.walk(path1)) {
            strem.filter(paths -> path.toFile().isDirectory()).forEach(System.out::println);
            
        } catch (Exception e) {
        }
        
        
       
    }
    
    
    
}

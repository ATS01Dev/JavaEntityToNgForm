/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular;

import org.ats.gejagular.core.UtiGen;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.ats.gejagular.base.GenModule.genModule;
import static org.ats.gejagular.base.GenRoute.genRoute;
import static org.ats.gejagular.base.GenarateService.genHomeService;
import static org.ats.gejagular.base.GenarateService.genService;
import static org.ats.gejagular.base.GenerateComponent.*;
import static org.ats.gejagular.base.genModel.construtModel;
import static org.ats.gejagular.children.Add.*;
import static org.ats.gejagular.children.View.*;
import static org.ats.gejagular.test.Protrator.construtProtorTest;

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
    private static final Logger log = Logger.getLogger(JavaEntityToNgForm.class.getName());

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

    /*private String generateNgModelText(Class clazz) {
        String text = "export class " + clazz.getSimpleName() + " {\n"
                + "    constructor(\n";
        for (Map.Entry<String, String> entry : getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += ("        public " + key + "?: " + value + ",\n");
        }
        return text + ")\n{\n}";
    }*/

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
    private void generateModelFile(Class clazz) {
        String contenu = construtModel(clazz);
        //baseEntityDir(clazz);
        String dest = UtiGen.destEnttity(clazz);
        //   System.out.println(contenu + "\n\n\n");
        Path fichier = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".model.ts");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, charset)) {
            writer.write(contenu, 0, contenu.length());
            writer.close();
        } catch (IOException ioe) {
        }
        // contenu = "";
    }

    /**
     * Génerer les composants basic de l'entité parend
     *
     * @Autor koyaja
     * @param clazz
     */
    protected void generateComponentMain(Class clazz) {
        String contenuMain = genMainComponent(clazz);
        String contenuHtml = genHtmlComponent(clazz);
        String contenuScss = genScssComponent(clazz);
        String contenuService = genHomeService(clazz);
        String contenuModule = genModule(clazz);
        String contenuRoute = genRoute(clazz);

        //baseEntityDir(clazz);
        String dest = UtiGen.destEnttity(clazz);

        //   System.out.println(contenu + "\n\n\n");
        Path fichierMain = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".component.ts");
        Path fichierHtml = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".component.html");
        Path fichierScss = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".component.scss");
        Path fichierService = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".service.ts");
        Path fichierModule = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".module.ts");
        Path fichierRoue = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".routing.ts");

        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichierMain, charset)) {
            writer.write(contenuMain, 0, contenuMain.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierHtml, charset)) {
            writer.write(contenuHtml, 0, contenuHtml.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierScss, charset)) {
            writer.write(contenuScss, 0, contenuScss.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierService, charset)) {
            writer.write(contenuService, 0, contenuService.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierModule, charset)) {
            writer.write(contenuModule, 0, contenuModule.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierRoue, charset)) {
            writer.write(contenuRoute, 0, contenuRoute.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }

    }

    protected void genChldrienAdd(Class clazz) {
        String contenuAddTs = genaddHomTs(clazz);
        String contenuHtml = constutddHtml(clazz);
        String contenuScss = constuteddScss();

        String destViw = UtiGen.destChildrenAdd(clazz);
        Path fichierAddTs = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-add.ts");
        Path fichierHtml = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-add.html");
        Path fichierScss = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-add.scss");
        // view gen
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichierAddTs, charset)) {
            writer.write(contenuAddTs, 0, contenuAddTs.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierHtml, charset)) {
            writer.write(contenuHtml, 0, contenuHtml.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierScss, charset)) {
            writer.write(contenuScss, 0, contenuScss.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
    }

    protected void genChldrienView(Class clazz) {
        String contenuViewTs = constutViewTs(clazz);
        String contenuViewHtml = constutViewHtml(clazz);
        String contenuViewScss = constutViewScss();

        String destViw = UtiGen.destChildrenView(clazz);
        Path fichierAddTs = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-view.ts");
        Path fichierHtml = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-view.html");
        Path fichierScss = Paths.get(destViw + "/" + clazz.getSimpleName().toLowerCase() + ".component-view.scss");
        // view gen
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichierAddTs, charset)) {
            writer.write(contenuViewTs, 0, contenuViewTs.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierHtml, charset)) {
            writer.write(contenuViewHtml, 0, contenuViewHtml.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
        try (BufferedWriter writer = Files.newBufferedWriter(fichierScss, charset)) {
            writer.write(contenuViewScss, 0, contenuViewScss.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
    }

    protected void genProtorTest(Class clazz) {
        String contenuProTest = construtProtorTest(clazz);

        String destTest = UtiGen.destEnttity(clazz);
        Path fichierProTest = Paths.get(destTest + "/" + clazz.getSimpleName().toLowerCase() + ".component.spec.ts");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichierProTest, charset)) {
            writer.write(contenuProTest, 0, contenuProTest.length());
            writer.close();
        } catch (IOException ioe) {
            log.log(Level.INFO, "Erreur de generation {0} ", ioe);
        }
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
    private String generateFormText(Class clazz) {
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

    private void generateHtmlFile(Class clazz) {
        String contenu = generateFormText(clazz);
        //   System.out.println(contenu + "\n\n\n");
        String dest = UtiGen.destEnttity(clazz);
        Path fichier = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".component.html");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, charset)) {
            writer.write(contenu, 0, contenu.length());
            writer.close();
        } catch (IOException ioe) {
        }
        // contenu = "";
    }

    public void outAllFile(Object clazz) {
        // generateAll((Class) clazz);
        generateComponentMain((Class) clazz);
        genChldrienView((Class) clazz);
        genChldrienAdd((Class) clazz);
        genProtorTest((Class) clazz);
        generateModelFile((Class) clazz);
    }

    private void generateAll(Class clazz) {
        generateModelFile(clazz);
        generateHtmlFile(clazz);
        genService(clazz);
    }

    /**
     * le package dans lequel se trouve toute les class d'entitées exemle
     * com.exemple.entity ou org.ats.gejagular.entities
     *
     * @param packageToScan
     */
    public void genWithScanEntitiesDirFromPagakage(String packageToScan) {

        String preparFile = packageToScan.replace(".", "/");
        StringBuilder dirFile = new StringBuilder();
        dirFile.append("./src/").append(preparFile);
        System.out.println(" scan " + dirFile.toString());
        File fic = new File(dirFile.toString());
        File[] lisf = fic.listFiles();
        for (File lisf1 : lisf) {
            System.out.println("liste fille" + lisf1.getName());
            System.out.println("liste fille" + lisf1.getName().replace("java", "class"));
            String t = lisf1.getName().replace(".java", "");
            Class c;

            System.out.println("Class " + t);
            try {
                c = Class.forName(packageToScan + "." + t);
                System.out.println("Class convert " + c);
                outAllFile(c);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JavaEntityToNgForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
//    public void genWithScanEntitiesDirFromLocation(String location){
//        
//    }

    public static void main(String[] args) {
        JavaEntityToNgForm a = new JavaEntityToNgForm();

        a.genWithScanEntitiesDirFromPagakage("org.ats.gejagular.entity");
        //genWithScanEntitiesDir("org.ats.gejagular.entity");
        System.out.println("out work");
        /*    Class cs=Badge.class;
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
         */
 /*  System.out.println("Repertoire");
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
        }*/

    }

    /**
     * gen add view
     */
}

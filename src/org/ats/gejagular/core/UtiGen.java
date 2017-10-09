/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ats.gejagular.JavaEntityToNgForm;

/**
 *
 * @author KOYAJA21
 */
public class UtiGen {

    private static final Logger log = Logger.getLogger(UtiGen.class.getName());
    
    
   public static String baseDir()  {
    File dirEntitys =new File("entity");
        
        if (!dirEntitys.exists()) {
            dirEntitys.mkdir();
            System.out.println("dossier "+dirEntitys.getAbsolutePath());
        }
    return "entity";
    }
    
    public static String baseEntityDir(Class clazz){
        
           File dirEntity =new File(clazz.getSimpleName().toLowerCase());
        if (!dirEntity.exists()) {
            dirEntity.mkdir();
            log.log(Level.INFO,"org.lcherubin.javaentitytongform.JavaEntityToNgForm.generateHtmlFile() : Créer le dossier de l'entité model ");
            
//        }else{
//            System.out.println("voulez vous regénerez y ou n");
//           String yorno = "no";
//            Scanner c=
//                    new Scanner(System.in);
//           
//            
//           if(c.next()== "y"){
//                           log.log(Level.WARNING," Suspression {} ",dirEntity);
//
//               dirEntity.delete();
//               dirEntity.mkdirs();
//           }
        }
        log.log(Level.INFO,"Créer le dossier de l'entité model retourné {}",dirEntity.getAbsolutePath());
        return dirEntity.getAbsolutePath();
    }
    
    public static String destEnttity(Class clazz){
        System.out.println("Traitement");
        Path path2 =Paths.get("./", "app/pages/"+clazz.getSimpleName());
        try {
            if(path2.toFile().exists()){
                System.out.println("existe");
            }else
            {
               Path path3 = Files.createDirectories(path2);  
            }
           
        } catch (IOException ex) {
            Logger.getLogger(JavaEntityToNgForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "./app/pages/"+clazz.getSimpleName();
    }
    public static String destChildrenView(Class clazz){
        System.out.println("Traitement");
        String dest ="./app/pages/"+clazz.getSimpleName()+"/view";
        Path path2 =Paths.get("./", "app/pages/"+clazz.getSimpleName()+"/view/");
        try {
            if(path2.toFile().exists()){
                System.out.println("existe");
            }else
            {
               Path path3 = Files.createDirectories(path2);  
            }
           
        } catch (IOException ex) {
            Logger.getLogger(JavaEntityToNgForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dest;
    }
   public static String destChildrenAdd(Class clazz){
        System.out.println("Traitement");
        String dest ="./app/pages/"+clazz.getSimpleName()+"/add";
        Path path2 =Paths.get("./", "app/pages/"+clazz.getSimpleName()+"/add/");
        try {
            if(path2.toFile().exists()){
                System.out.println("existe");
            }else
            {
               Path path3 = Files.createDirectories(path2);  
            }
           
        } catch (IOException ex) {
            Logger.getLogger(JavaEntityToNgForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dest;
    }
    
    
}

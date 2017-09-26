/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOYAJA21
 */
public class UtiGen {

    private static final Logger log = Logger.getLogger(UtiGen.class.getName());
    
    static void baseEntityDir(Class clazz){
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
    }
    
}

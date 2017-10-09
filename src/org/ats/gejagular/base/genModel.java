/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular.base;

import java.util.Map;
import org.ats.gejagular.JavaEntityToNgForm;

/**
 *
 * @author hp
 */
public class genModel {
    
    public static String construtModel(Class clazz) {
        
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder modelBuilder = new StringBuilder();
        modelBuilder.append("export class ").append(className).append(" {\n").
                append(genAtribModel(clazz)).append("}");
        
        return modelBuilder.toString();
    }
    
    private static String genAtribModel(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += ("\t"+key + "?: " + value + ";\n");
        }
        return text;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular.children;

import java.util.Map;
import org.ats.gejagular.JavaEntityToNgForm;

/**
 *
 * @author hp
 */
public class Add {
    
    public static String genaddHomTs(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder addClass = new StringBuilder();
        
        addClass.append("import { Component, OnInit } from '@angular/core';\n"
                + "import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';\n"
                + "import {HotelsService} from \"../" + classNameMin + ".service\";\n"
                + "import {Router} from \"@angular/router\";\n"
                + "@Component({\n"
                + "    // tslint:disable-next-line:component-selector\n"
                + "    selector: 'add-" + classNameMin + "',\n"
                + "    templateUrl: './" + classNameMin + ".component-add.html',\n"
                + "  styleUrls: ['./" + classNameMin + ".component-add.scss']\n"
                + "})\n"
                + "export class " + className + "AddComponent implements OnInit {\n"
                + "    // model: hotels;\n"
                + "    public form:FormGroup;\n"
                + genAbstractControl(clazz)
                + "  public submitted:boolean = false;\n"
                + "\n"
                + "    constructor(fb:FormBuilder,\n"
                + "                private " + classNameMin + "Service: " + className + "Service,\n"
                + "                private router: Router\n"
                + "    ) {\n"
                + "      this.form = fb.group({\n"
                + genControl(clazz)
                + "      });\n"
                + "\n"
                + genAtribControl(clazz)
                + "    }\n"
                + "\n"
                + "    ngOnInit() {\n"
                + "    }\n"
                + "  public onSubmit(values:Object):void {\n"
                + "     // let me=this;\n"
                + "    this.submitted = true;\n"
                + "    if (this.form.valid) {\n"
                + "\n"
                + "      console.log(values);\n"
                + "\n"
                + "      this." + classNameMin + "Service.save(values).subscribe((resp) =>{\n"
                + "        console.log(resp)\n"
                + "        this.router.navigate(['/pages/" + classNameMin + "/lists']);\n"
                + "        // me.router.navigate(['/pages/" + classNameMin + "/lists']);\n"
                + "      }, error2 => {\n"
                + "        console.log(error2)\n"
                + "      })\n"
                + "    }\n"
                + "\n"
                + "\n"
                + "  }\n"
                + "\n"
                + "}");
        return addClass.toString();
    }
    
    public static String constutddHtml(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder addClass = new StringBuilder();
        
        addClass.append("<div class=\"container\">\n"
                + "    <h1>Ajouter un "+className +"</h1>\n"
                + "  <form [formGroup]=\"form\" (ngSubmit)=\"onSubmit(form.value)\" class=\"form-horizontal\">\n"
                + genHmlFormInput(clazz)
                + "    <div class=\"form-group row\">\n"
                + "      <div class=\"offset-sm-2 col-sm-10\">\n"
                + "        <button [disabled]=\"!form.valid\" type=\"submit\" class=\"btn btn-default btn-auth\" > Enregistrer </button>\n"
                + "        <!--<a routerLink=\"/login\" class=\"forgot-pass\" >{{'login.forgot_password'}}</a>-->\n"
                + "      </div>\n"
                + "    </div>\n"
                + "\n"
                + "    </form>\n"
                + "  \n"
                + "</div>");
        
        return addClass.toString();
    }

    public static String constuteddScss() {
//        String className = clazz.getSimpleName();
//        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder addClass = new StringBuilder();
        addClass.append("@import \"../../../theme/sass/conf/conf\";\n"
                + "\n"
                + "\n"
                + "@media (min-width: 992px){\n"
                + "  .container {\n"
                + "    width: 715px !important;\n"
                + "    max-width: 100%;\n"
                + "  }\n"
                + "}\n"
                + "\n"
                + "@media (min-width: 768px){\n"
                + "  .container {\n"
                + "    width: 616px !important;\n"
                + "    max-width: 100%;\n"
                + "  }\n"
                + "}\n"
                + "");
   
        return addClass.toString();
    }

    //*********************************************************Section controleur +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * c
     *
     * @param clazz
     * @return
     */
    private static String genAbstractControl(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += "  public " + key + ": AbstractControl;\n";
        }
        return text;
    }
    
    private static String genControl(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += " '" + key + "': ['', Validators.compose([Validators.required, Validators.minLength(4)])],\n";
        }
        return text;
    }
    
    private static String genAtribControl(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += "this." + key + " = this.form.controls['" + key + "'];\n";
        }
        return text;
    }

    private static String genHmlFormInput(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += "<div class=\"form-group\">\n"
                    + "        <label for=\"code\">" + key + "</label>\n"
                    + "        <input [formControl]=\"" + key + "\" type=\"text\" class=\"form-control\" id=\"" + key + "\" placeholder=\"" + key + "\"  required>\n"
                    + "      </div>\n";
        }
        return text;
    }
    
}

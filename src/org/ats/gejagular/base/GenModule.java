/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular.base;

/**
 *
 * @author hp
 */
public class GenModule {

    public static String genModule(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder moduleClass = new StringBuilder();
        moduleClass.append("import { NgModule }      from '@angular/core';\n"
                + "import { CommonModule }  from '@angular/common';\n"
                + "import {FormsModule, ReactiveFormsModule} from '@angular/forms';\n"
                + "import { NgaModule } from '../../theme/nga.module';\n"
                + "import {routing} from \"./" + classNameMin + ".routing\";\n"
                + "import {DataTableModule} from \"angular2-datatable\";\n"
                + "import {" + className + "Service} from \"./hotels.service\";\n"
                + "import {" + className + "ViewComponent} from \"./view/" + classNameMin + ".view.component\";\n"
                + "import {Ng2SmartTableModule} from \"ng2-smart-table\";\n"
                + "import {" + className + "AddComponent} from \"./add/" + classNameMin + ".component-add\";\n"
                + "import {" + className + "Component} from \"./" + classNameMin + ".component\";\n"
                + "// import { Editors } from './editors.component';\n"
                + "// import { Ckeditor } from './components/ckeditor/ckeditor.component';\n"
                + "\n"
                + "\n"
                + "@NgModule({\n"
                + "  imports: [\n"
                + "    CommonModule,\n"
                + "    FormsModule,\n"
                + "    NgaModule,\n"
                + "    DataTableModule,\n"
                + "    Ng2SmartTableModule,\n"
                + "    ReactiveFormsModule,\n"
                + "    FormsModule,\n"
                + "    NgaModule,\n"
                + "\n"
                + "    // CKEditorModule,\n"
                + "    routing\n"
                + "  ],\n"
                + "  declarations: [\n"
                + "    // Editors,\n"
                + "    // Ckeditor\n"
                + "\n"
                + "    " + className + "ViewComponent,\n"
                + "   " + className + "AddComponent,\n"
                + "    " + className + "Component\n"
                + "  ],\n"
                + "  providers: [\n"
                + "    " + className + "Service\n"
                + "  ]\n"
                + "})\n"
                + "export class " + className + "Module {\n"
                + "}");

        return moduleClass.toString();
    }

}

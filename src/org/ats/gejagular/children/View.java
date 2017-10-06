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
public class View {

    public static String constutViewHtml(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder viewClass = new StringBuilder();

        viewClass.append("<div class=\"widgets\">\n"
                + "\n"
                + "  <div class=\"row\">\n"
                + "    <ba-card cardTitle=\"Listes des " + className + " \" baCardClass=\"with-scroll\" class=\"smart-table-container\">\n"
                + "      <ng2-smart-table\n"
                + "        [settings]=\"settings\"\n"
                + "        [source]=\"source\"\n"
                + "        (deleteConfirm)=\"onDeleteConfirm($event)\"\n"
                + "        (editConfirm)=\"onSaveConfirm($event)\"\n"
                + "        (createConfirm)=\"onCreateConfirm($event)\" ></ng2-smart-table>\n"
                + "    </ba-card>\n"
                + "  </div>\n"
                + "\n"
                + "</div>");

        return viewClass.toString();
    }

    public static String constutViewTs(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder viewClass = new StringBuilder();

        viewClass.append("import { Component } from '@angular/core';\n"
                + "import { LocalDataSource } from 'ng2-smart-table';\n"
                + "import {" + className + "Service} from \"../" + classNameMin + ".service\";\n"
                + "import {Router} from \"@angular/router\";\n"
                + "\n"
                + "@Component({\n"
                + "  selector: '" + classNameMin + ".view.html',\n"
                + "  templateUrl: './" + classNameMin + ".view.html',\n"
                + "  styleUrls: ['./" + classNameMin + ".view.scss']\n"
                + "})\n"
                + "export class " + className + "ViewComponent {\n"
                + "\n"
                + "  query: string = '';\n"
                + "\n"
                + "  settings = {\n"
                + "    add: {\n"
                + "      addButtonContent: '<i class=\"ion-ios-plus-outline\"></i>',\n"
                + "      createButtonContent: '<i class=\"ion-checkmark\"></i>',\n"
                + "      cancelButtonContent: '<i class=\"ion-close\"></i>',\n"
                + "      confirmCreate: true\n"
                + "    },\n"
                + "    edit: {\n"
                + "      editButtonContent: '<i class=\"ion-edit\"></i>',\n"
                + "      saveButtonContent: '<i class=\"ion-checkmark\"></i>',\n"
                + "      cancelButtonContent: '<i class=\"ion-close\"></i>',\n"
                + "      confirmSave: true,\n"
                + "\n"
                + "\n"
                + "    },\n"
                + "    delete: {\n"
                + "      deleteButtonContent: '<i class=\"ion-trash-a\"></i>',\n"
                + "      confirmDelete: true\n"
                + "    },\n"
                + "    columns: {\n"
                + genCelleControl(clazz)
                + "    }\n"
                + "  };\n"
                + "\n"
                + "  source: LocalDataSource = new LocalDataSource();\n"
                + "\n"
                + "  constructor(protected " + classNameMin + "Service: " + className + "Service,\n"
                + "              private router: Router) {\n"
                + "    console.log('come now')\n"
                + "    this." + classNameMin + "Service.getAll" + className + "().subscribe((data) => {\n"
                + "      console.log(data)\n"
                + "      this.source.load(data);\n"
                + "      this.source.setPaging(data.size,10);\n"
                + "    });\n"
                + "  }\n"
                + "\n"
                + "  onDeleteConfirm(event): void {\n"
                + "\n"
                + "    console.log('wep '+event.data)\n"
                + "    if (window.confirm('Are you sure you want to delete?')) {\n"
                + "      console.log(event.data)\n"
                + "      console.log('id '+event.data.id)\n"
                + "\n"
                + "      event.confirm.resolve();\n"
                + "      this." + classNameMin + "Service.delete(event.data.id).subscribe((data) => {\n"
                + "        console.log(data)\n"
                + "        this.router.navigate(['/pages/" + classNameMin + "/lists'])\n"
                + "        //this.source.load(data);\n"
                + "      });\n"
                + "    } else {\n"
                + "      event.confirm.reject();\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "  onSaveConfirm(event): void  {\n"
                + "    if (window.confirm('Voulez vous vraiment mettre à jour ?')) {\n"
                + "      // event.newData['name'] += ' + added in code';\n"
                + "      console.log(event.data)\n"
                + "      console.log('id '+event.data.id);\n"
                + "      event.confirm.resolve(event.newData);\n"
                + "      console.log(event.newData)\n"
                + "      this." + classNameMin + "Service.update(event.newData).subscribe((data) => {\n"
                + "        console.log(data)\n"
                + "        this.router.navigate(['/pages/" + classNameMin + "/lists'])\n"
                + "        //this.source.load(data);\n"
                + "      });\n"
                + "\n"
                + "    } else {\n"
                + "      event.confirm.reject();\n"
                + "    }\n"
                + "  }\n"
                + "\n"
                + "  onCreateConfirm(event): void  {\n"
                + "    if (window.confirm('Voulez vous vraiment ajouter un nouveau hotels?')) {\n"
                + "      // event.newData['name'] += ' + added in code';\n"
                + "      console.log(event.newData)\n"
                + "      // console.log('id '+event.data.id)\n"
                + "      event.confirm.resolve(event.newData);\n"
                + "\n"
                + "      this." + classNameMin + "Service.save(event.newData).subscribe((data) => {\n"
                + "        console.log(data)\n"
                + "        this.router.navigate(['/pages/" + classNameMin + "/lists'])\n"
                + "        //this.source.load(data);\n"
                + "      });\n"
                + "    } else {\n"
                + "      event.confirm.reject();\n"
                + "    }\n"
                + "  }\n"
                + "}");

        return viewClass.toString();
    }

    //******************************* controleur ++++++++++++++++++++++++++
    private static String genCelleControl(Class clazz) {
        String text = "";
        for (Map.Entry<String, String> entry : new JavaEntityToNgForm().getAttributeMap(clazz).get("tsAttribute").entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //  System.out.println(key + " : " + value);

            text += "" + key + ": {\n"
                    + "        title: '" + key + "',\n"
                    + "        type: '" + value + "',\n"
                    + "      },\n";
//            text += "this." + key + " = this.form.controls['" + key + "'];\n";
        }
        return text;
    }

    public static String constutViewScss() {
        //String className = clazz.getSimpleName();
        //String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder viewClass = new StringBuilder();

        viewClass.append("@import \"../../../theme/sass/conf/conf\";\n"
                + ":host /deep/ .widgets {\n"
                + "  .smart-table-container {\n"
                + "    width: 100%;\n"
                + "  }\n"
                + "}\n"
                + "\n"
                + ":host /deep/ {\n"
                + "  ng2-smart-table {\n"
                + "    th, td {\n"
                + "      border: 1px solid $border-light !important;\n"
                + "      line-height: 35px;\n"
                + "      vertical-align: middle;\n"
                + "    }\n"
                + "\n"
                + "    table tr td {\n"
                + "      padding: 0 8px;\n"
                + "    }\n"
                + "\n"
                + "    color: $default-text;\n"
                + "\n"
                + "    input {\n"
                + "      line-height: 1.5 !important;\n"
                + "    }\n"
                + "\n"
                + "    ng2-smart-table-cell {\n"
                + "      color: $default-text;\n"
                + "      line-height: 35px;\n"
                + "    }\n"
                + "\n"
                + "    tbody {\n"
                + "      tr {\n"
                + "        color: $default-text;\n"
                + "      }\n"
                + "      tr:hover {\n"
                + "        background: rgba(0, 0, 0, 0.03);\n"
                + "      }\n"
                + "    }\n"
                + "\n"
                + "    a.ng2-smart-sort-link {\n"
                + "      font-size: 14px !important;\n"
                + "      color: $default-text;\n"
                + "      font-weight: $font-bolder;\n"
                + "      &.sort {\n"
                + "        font-weight: $font-bolder !important;\n"
                + "\n"
                + "        &::after {\n"
                + "          border-bottom-color: $default-text !important;\n"
                + "        }\n"
                + "      }\n"
                + "    }\n"
                + "\n"
                + "    .ng2-smart-actions {\n"
                + "      width: 70px;\n"
                + "      text-align: center;\n"
                + "      .actions {\n"
                + "        float: none;\n"
                + "        text-align: center;\n"
                + "      }\n"
                + "    }\n"
                + "\n"
                + "    .ng2-smart-actions-title-add {\n"
                + "      text-align: center;\n"
                + "    }\n"
                + "\n"
                + "    a.ng2-smart-action, .ng2-smart-title {\n"
                + "      font-size: 14px !important;\n"
                + "      color: $default-text;\n"
                + "      padding: 0 5px;\n"
                + "      display: inline-block;\n"
                + "\n"
                + "      &.ng2-smart-action-add-add {\n"
                + "        font-size: 25px !important;\n"
                + "      }\n"
                + "    }\n"
                + "\n"
                + "    nav.ng2-smart-pagination-nav {\n"
                + "      display: flex;\n"
                + "      justify-content: center;\n"
                + "    }\n"
                + "  }\n"
                + "}\n"
                + "\n"
                + "");

        return viewClass.toString();
    }
}

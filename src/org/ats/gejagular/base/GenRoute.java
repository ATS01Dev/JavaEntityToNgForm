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
public class GenRoute {

   public static String genRoute(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder routeClass = new StringBuilder();
        routeClass.append("import {RouterModule, Routes} from \"@angular/router\";\n"
                + "import {"+className+"ViewComponent} from \"./view/"+classNameMin+".view.component\";\n"
                + "import {"+className+"AddComponent} from \"./add/"+classNameMin+".component-add\";\n"
                + "import {"+className+"Component} from \"./"+classNameMin+".component\";\n"
                + "\n"
                + "const routes: Routes = [\n"
                + "  {\n"
                + "    path: '',\n"
                + "    component: "+className+"Component,\n"
                + "    children: [\n"
                + "      { path: 'lists', component: "+className+"ViewComponent },\n"
                + "      { path: 'add', component: "+className+"AddComponent },\n"
                + "      // { path: 'googlemaps', component: GoogleMaps },\n"
                + "      // { path: 'leafletmaps', component: LeafletMaps },\n"
                + "      // { path: 'linemaps', component: LineMaps }\n"
                + "    ]\n"
                + "\n"
                + "  }\n"
                + "];\n"
                + "\n"
                + "export const routing = RouterModule.forChild(routes);");
        return routeClass.toString();

    }
}

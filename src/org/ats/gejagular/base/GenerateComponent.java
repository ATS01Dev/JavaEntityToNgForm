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
public class GenerateComponent {

   public static String coreComponent(Class clazz) {
        String className = clazz.getSimpleName();
        StringBuilder componentClass = new StringBuilder();
        componentClass.append("import { Component, OnInit, OnDestroy } from '@angular/core';\nimport { ActivatedRoute, Router } from '@angular/router';\nimport { Subscription } from 'rxjs/Rx';\n import { ")
                .append(className).append(" } from './")
                .append(className.toLowerCase())
                .append(".model';\nimport { ")
                .append(className)
                .append("Service} from './client.service'");
        componentClass.append("@Component({\n    selector: 'app-")
                .append(className.toLowerCase()).append("',\n    templateUrl: './")
                .append(className.toLowerCase())
                .append(".component.html'\n})\n");
        componentClass.append("export class ")
                .append(className).append("Component implements OnInit, OnDestroy {\n\n    ")
                .append(className.toLowerCase()).append(": ")
                .append(className).append("[];\n    currentAccount: any;\n    eventSubscriber: Subscription;\n    itemsPerPage: number;\n    links: any;\n    page: any;\n    predicate: any;\n    queryCount: any;\n    reverse: any;\n    totalItems: number;\n    currentSearch: string;\nconstructor(\n  private ")
                .append(className.toLowerCase())
                .append("Service: ")
                .append(className)
                .append("Service)\n");

        return componentClass.toString();
    }

  public static String genMainComponent(Class clazz) {

        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder componentBuilder = new StringBuilder();
        componentBuilder.append("import { Component, OnInit } from '@angular/core';\n\n\n@Component({\n  // tslint:disable-next-line:component-selector\n  selector: 'app-").
                append(classNameMin).append("',\n  templateUrl: './").
                append(classNameMin).append(".component.html',\n  styleUrls: ['./").append(classNameMin).append(".component.scss']\n})\nexport class ").append(className).append("Component implements OnInit {\n\n\n\n  constructor() { }\n\n  ngOnInit() {\n\n\n\n  }\n\n\n\n}");

        return componentBuilder.toString();
    }

   public static String genHtmlComponent(Class clazz) {

        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder componentBuilder = new StringBuilder();
        componentBuilder.append("<router-outlet></router-outlet>");
        return componentBuilder.toString();
    }
   public static String genScssComponent(Class clazz) {

        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder componentBuilder = new StringBuilder();
        componentBuilder.append("");
        return componentBuilder.toString();
    }
}

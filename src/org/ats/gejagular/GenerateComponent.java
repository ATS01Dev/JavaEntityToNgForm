/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular;

/**
 *
 * @author hp
 */
public class GenerateComponent {
    
    static String coreComponent(Class clazz) {
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
}

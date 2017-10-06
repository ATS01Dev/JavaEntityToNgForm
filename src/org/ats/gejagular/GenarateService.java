/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOYAJA21
 */
public class GenarateService {

    private static final Logger log = Logger.getLogger(GenarateService.class.getName());

    public GenarateService() {
    }

    static String coreService(Class clazz) {
        String className = clazz.getSimpleName();
        StringBuilder serviceClass = new StringBuilder();
        log.info("import angular librairie");
        serviceClass.append("import { Injectable } from '@angular/core';\nimport { Observable } from 'rxjs/Rx';\nimport { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';\nimport { ").append(className).append(" } from './").append(className.toLowerCase()).append(".model';\n@Injectable()\n");
        log.log(Level.INFO, "Creat class '{''}'{0}", className);
        serviceClass.append("export class ").append(className).append("Service  { \nprivate resourceUrl = 'api/").append(className).append("';\n constructor(private http: Http) { }\n \n");
        log.log(Level.INFO, "Create methode create {0}", className);
        serviceClass.append("create(").append(className.toLowerCase()).append(":").append(className).append("):Observable<").append(className).append(">{\n").append("let copy: ").append(className).append(" = Object.assign({},").append(className.toLowerCase()).append(");\n        return this.http.post(this.resourceUrl, copy).map((res: Response) => {\n return res.json();\n});\n    }\n\n");
        log.log(Level.INFO, "Create methode update {0}", className);
        serviceClass.append("update(").append(className.toLowerCase()).append(":").append(className).append("): Observable<").append(className).append("> {\n        let copy: ").append(className).append(" = Object.assign({}, ").append(className.toLowerCase()).append(");\n        return this.http.put(this.resourceUrl, copy).map((res: Response) => {\n            return res.json();\n        });\n    }\n \n");
        log.log(Level.INFO, "find one methode  {0}", className);
        serviceClass.append("find(id: number): Observable<").append(className).append("> {\n        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {\n            return res.json();\n        });\n    }\n\n");
        log.log(Level.INFO, "find all methode  {0}", className);

        serviceClass.append("query(req?: any): Observable<Response> {\n"
                + "        let options = this.createRequestOption(req);\n"
                + "        return this.http.get(this.resourceUrl, options)\n"
                + "        ;\n"
                + "    }\n\n");
        log.log(Level.INFO, "find delete methode  {0}", className);
        serviceClass.append("delete(id: number): Observable<Response> {\n"
                + "        return this.http.delete(`${this.resourceUrl}/${id}`);\n"
                + "    }\n\n");
        log.log(Level.INFO, "requet option  methode  {0}", className);

        serviceClass.append("private createRequestOption(req?: any): BaseRequestOptions {\n"
                + "        let options: BaseRequestOptions = new BaseRequestOptions();\n"
                + "        if (req) {\n"
                + "            let params: URLSearchParams = new URLSearchParams();\n"
                + "            params.set('page', req.page);\n"
                + "            params.set('size', req.size);\n"
                + "            if (req.sort) {\n"
                + "                params.paramsMap.set('sort', req.sort);\n"
                + "            }\n"
                + "            params.set('query', req.query);\n"
                + "\n"
                + "            options.search = params;\n"
                + "        }\n"
                + "        return options;\n"
                + "    }\n \n }\n\n");

        return serviceClass.toString();
    }

    static void genService(Class clazz) {

        String contenu = coreService(clazz);
        //   System.out.println(contenu + "\n\n\n");
        // baseEntityDir(clazz);
        String dest = UtiGen.destEnttity(clazz);
        Path fichier = Paths.get(dest + "/" + clazz.getSimpleName().toLowerCase() + ".service.ts");
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(fichier, charset)) {
            writer.write(contenu, 0, contenu.length());
            writer.close();
        } catch (IOException ioe) {
        }
    }

    static String genHomeService(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder serviceClass = new StringBuilder();
        serviceClass.append("import {Injectable} from \"@angular/core\";\n"
                + "import { Http, Headers, Response, Request, RequestOptions, URLSearchParams,RequestMethod } from '@angular/http';\n"
                + "\n"
                + "import {ApiRequestService} from \"../shared/requestHttp/api-request.service\";\n"
                + "import {Observable} from \"rxjs/Observable\";\n"
                + "import {AuthenticationService} from \"../shared/authentication/authentication.service\";\n"
                + "\n"
                + "@Injectable()\n"
                + "export class "+className+"Service {\n"
                + "\n"
                + "  pathUrl=\"api/"+classNameMin+"\";\n"
                + "  constructor(private apiRequest: ApiRequestService,\n"
                + "              private http: Http,\n"
                + "              private userInfoService: AuthenticationService\n"
                + "              // private translate:TranslateService\n"
                + "  ) {\n"
                + "  }\n"
                + "\n"
                + "  getAll"+className+"(page?: number, size?: number): Observable<any> {\n"
                + "    //Create Request URL params\n"
                + "    let me = this;\n"
                + "    let params: URLSearchParams = new URLSearchParams();\n"
                + "    params.set('page', typeof page === \"number\" ? page.toString() : \"0\");\n"
                + "    params.set('size', typeof page === \"number\" ? size.toString() : \"1000\");\n"
                + "    return this.apiRequest.get(this.pathUrl, params);\n"
                + "  }\n"
                + "\n"
                + "  save(hotels: any){\n"
                + "    return this.apiRequest.post(this.pathUrl,hotels);\n"
                + "  }\n"
                + "  delete(id:any){\n"
                + "    return this.apiRequest.delete(`${this.pathUrl}/${id}`)\n"
                + "  }\n"
                + "  update(hotels: any){\n"
                + "    return this.apiRequest.put(this.pathUrl, hotels);\n"
                + "  }\n"
                + "  \n"
                + "}");

        return serviceClass.toString();
    }

}

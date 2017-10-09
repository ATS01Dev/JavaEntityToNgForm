/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ats.gejagular.test;

/**
 *
 * @author hp
 */
public class Protrator {

    public static String construtProtorTest(Class clazz) {
        String className = clazz.getSimpleName();
        String classNameMin = clazz.getSimpleName().toLowerCase();
        StringBuilder protorTestClass = new StringBuilder();

        protorTestClass.append("import { async, ComponentFixture, TestBed } from '@angular/core/testing';\n"
                + "\n"
                + "import { " + className + "Component } from './" + classNameMin + ".component';\n"
                + "\n"
                + "describe('" + className + "Component', () => {\n"
                + "  let component: " + className + "Component;\n"
                + "  let fixture: ComponentFixture<" + className + "Component>;\n"
                + "\n"
                + "  beforeEach(async(() => {\n"
                + "    TestBed.configureTestingModule({\n"
                + "      declarations: [ " + className + "Component ]\n"
                + "    })\n"
                + "    .compileComponents();\n"
                + "  }));\n"
                + "\n"
                + "  beforeEach(() => {\n"
                + "    fixture = TestBed.createComponent(" + className + "Component);\n"
                + "    component = fixture.componentInstance;\n"
                + "    fixture.detectChanges();\n"
                + "  });\n"
                + "\n"
                + "  it('should be created', () => {\n"
                + "    expect(component).toBeTruthy();\n"
                + "  });\n"
                + "});");

        return protorTestClass.toString();
    }

}

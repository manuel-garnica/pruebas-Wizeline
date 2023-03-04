package com.wizeline.maven.learningjavamaven.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadosdeMexico {

   private static String[] estadosArray = {"Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas",
            "Chihuahua", "Ciudad de México", "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato",
            "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca",
            "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas",
            "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"};

    public  static   String[]  obtenerArrayEstado(){
        return  estadosArray;
    }
    public  static  Map<Integer,String >  obtenerMapaEstado(){
        Map<Integer, String> estadosMap = new HashMap<>();
        for (int i = 0; i < estadosArray.length; i++) {
            estadosMap.put(i + 1, estadosArray[i]);
        }
        return estadosMap;
    }

    public  static  List<String>  obtenerListaEstado(){
        List<String> estadosLista = new ArrayList<>();
        for (int i = 0; i < estadosArray.length; i++) {
            estadosLista.add(estadosArray[i]);
        }
        return  estadosLista;
    }

}

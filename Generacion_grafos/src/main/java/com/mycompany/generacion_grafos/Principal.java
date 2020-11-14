/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.generacion_grafos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author darky
 */
public class Principal {

   

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
         
       Grafo g;
       g = new Grafo();
       g.grafosER(500,1000);
       g.grafosGilbert(500,0.1);
       g.grafosGeografico(500,0.5);
       g.grafosBarabasi(500,4);
       
    }
    
 

}


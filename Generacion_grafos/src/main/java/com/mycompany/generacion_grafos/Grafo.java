/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.generacion_grafos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author darky
 */
public class Grafo {
    
    public void grafosER(int n,int m) {
        //Metodo de Erdos y Renyi: n nodos con m aristas, se unen aleaotoriamente
        Random rand = new Random();
        int matriz1[][] = new int[m][2];
        int contador = 0;
        while ((contador<m) && (contador<n*(n-1)/2)){
            int n1=rand.nextInt(n)+1;
            int n2=rand.nextInt(n)+1;
            int r = repetidos(matriz1,n1,n2);//esta función sirve para saber si ya existe esa arista
                                             //r=0 cuando no existe esa arista y 1 en caso contrario

            if ((n1 != n2)&&(r==0)) { //que no se conecte un nodo consigo mismo y no exista ya esa arusta
                matriz1[contador][0]=n1;
                matriz1[contador][1]=n2;
                contador += 1;
            }
        }
        //Mostramos la matriz
          /* for (int i=0;i<m;i++){
            for (int j=0;j<2;j++){
                System.out.print(matriz1[i][j] + " ");
            }
            System.out.println();
        }*/
        //Guardamos la matriz en .csv y en .dot
        //guardarMatriz("GrafoErdosRenyi.csv", matriz1);
        guardarMatrizDot("GrafoErdosRenyi.dot", matriz1);
    }

   
     public void grafosGilbert(int n, double p) {
        //Método de gilbert, n nodos con probabilidad p de unirse. 
        //Se genera un número aleatorio entre 0 y 1, si es menor a p no se forma la arista
        //Primero inicializamos una matriz dinámica
        ArrayList<Integer>[] matriz1 = new ArrayList[2]; 
        matriz1[0] = new ArrayList<>(); 
        matriz1[1] = new ArrayList<>();
        double proba;
        for (int i=0;i<n;i++){
         for (int j=0;j<n;j++){
             proba = Math.random();
             int bo = repetidosdin(matriz1,i+1,j+1);
             if ((proba <= p)&&(i != j)&&(bo==0)) {
                 matriz1[0].add(i+1);
                 matriz1[1].add(j+1);
             }
         }  
       }
        //Pasamos la matriz al formato como se guardará
        int matriz[][] = new int[matriz1[0].size()][2];
        for (int j = 0; j < matriz1[0].size(); j++) {
                matriz[j][0] = matriz1[0].get(j);
                matriz[j][1] = matriz1[1].get(j);
              } 
         System.out.println(matriz1[0].size());//mostrar cuántas aristas se crearon
        //Guardamos la matriz con este método
        //guardarMatriz("GrafoGilbert.csv", matriz);
        guardarMatrizDot("GrafoGilbert.dot", matriz);
    }
    
     
     public void grafosGeografico(int n,double r) {
        //n vértices en un rectángulo unitario con coordenadas normales
        //si están a una distancia igual o menor a r, se unen
        double distancia;
        double coordenadas[][] = new double[n][2];
        ArrayList<Integer>[] matriz1 = new ArrayList[2]; 
        matriz1[0] = new ArrayList<>(); 
        matriz1[1] = new ArrayList<>();
        Random random = new Random();
        int contador=0;
        for (int j=0;j<n;j++){//generamos las coordenadas de los puntos
          Double x = random.nextGaussian();
          Double y = random.nextGaussian();
          coordenadas[j][0]=x;
          coordenadas[j][1]=y;
        }
        for (int i=0;i<n;i++){
          for (int j=0;j<n;j++){
              double x1 = coordenadas[i][0];
              double y1 = coordenadas[i][1];
              double x2 = coordenadas[j][0];
              double y2 = coordenadas[j][1];
              distancia = Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2,2));
              int bo = repetidosdin(matriz1,i+1,j+1);
              if ((distancia<=r)&&(i != j)&&(bo==0)) {
                 matriz1[0].add(i+1);
                 matriz1[1].add(j+1);
              }
         }  
       }
           //Pasamos la matriz al formato como se guardará
        int matriz[][] = new int[matriz1[0].size()][2];
        for (int j = 0; j < matriz1[0].size(); j++) {
                matriz[j][0] = matriz1[0].get(j);
                matriz[j][1] = matriz1[1].get(j);
              } 
        System.out.println(matriz1[0].size());
       // guardarMatriz("GrafoGeografico.csv", matriz);
        guardarMatrizDot("GrafoGeografico.dot", matriz);
    }
     

    public void grafosBarabasi(int n, int d) {
        double proba;
        ArrayList<Integer>[] matriz1 = new ArrayList[2]; 
        matriz1[0] = new ArrayList<>(); 
        matriz1[1] = new ArrayList<>();

        double grados[][] = new double[n][2];
        //llenamos de ceros la matriz de grados
        for (int i=0;i<n;i++){
            grados[i][0]=i+1;
            grados[i][1]=0;
        }
        int contador = 0;
        //Ahora vamos a generar los n nodos
        for (int i=0;i<n;i++){
            for (int j=0;j<i;j++){ //este ciclo se repetirá i veces
                 double p = 1 - grados[j][1]/d; //calculamos la probabilidad de unirse al nodo
                 proba = Math.random(); //generamos un número aleatorio para comparar con p
                
                 if(p>=proba){
                     matriz1[0].add(i+1);
                     matriz1[1].add(j+1);
                     grados[i][1] = grados[i][1]+1;
                     grados[j][1] = grados[j][1]+1;
                 }
            }
        }
        /*En este punto, la matriz se ve así:
        [n1 n2 n3 n4]
        [n5 n6 n7 n8]
        Por lo que hay que pasarlo a un formato de matriz nx2
        */         
        //Pasamos la matriz al formato como se guardará
        int matriz[][] = new int[matriz1[0].size()][2];
        for (int j = 0; j < matriz1[0].size(); j++) {
                matriz[j][0] = matriz1[0].get(j);
                matriz[j][1] = matriz1[1].get(j);
                //System.out.print(al[0].get(j) + " ");
                //System.out.print(al[1].get(j) + "\n");
            } 
        System.out.println(matriz1[0].size());
        //guardarMatriz("GrafoBarbasi.csv", matriz);
        guardarMatrizDot("GrafoBarbasi.dot", matriz);
    }
     
     //función para guardar la matriz en csv
     public static void guardarMatriz(String nombre, int matriz[][]) {
       File f;
       FileWriter w;
       BufferedWriter bw;
       PrintWriter wr;
       try {
           f = new File(nombre);
           w = new FileWriter(f);
           bw = new BufferedWriter(w);
           wr = new PrintWriter(bw);
           
           wr.write("Id,Source,Target\n");
           
           for (int i=0;i<matriz.length;i++){
            for(int j=0;j<3;j++){
                if(j==0){
                 wr.append(Integer.toString(i+1) + ",");
                }
                if(j==1){
                 wr.append(Integer.toString(matriz[i][j-1]) + ",");
                }
                if(j==2){
                 wr.append(Integer.toString(matriz[i][j-1]));
                }
            }
            wr.append("\n");  
        }
           //
           wr.close();
           bw.close();
           
       } catch (IOException e){
           JOptionPane.showMessageDialog(null, "Error");
       }

    }
     
    //función para guardar la matriz en .dot
    public static void guardarMatrizDot(String nombre, int matriz[][]) {
       File f;
       FileWriter w;
       BufferedWriter bw;
       PrintWriter wr;
       try {
           f = new File(nombre);
           w = new FileWriter(f);
           bw = new BufferedWriter(w);
           wr = new PrintWriter(bw);
           wr.write("graph abstract {\n");
           
           for (int i=0;i<matriz.length;i++){
                 wr.append("nodo_" + Integer.toString(matriz[i][0]) + "->" + "nodo_" + Integer.toString(matriz[i][1])+";\n");
              
        }
           wr.append("}"); 
           wr.close();
           bw.close();
           
       } catch (IOException e){
           JOptionPane.showMessageDialog(null, "Error");
       }

    }

   //función para buscar los números repetidos en una matriz estática
    public static int repetidos(int matriz[][],int n1,int n2) { 
      int n=0;
       for (int i=0;i<matriz.length;i++){
            if ((matriz[i][0]==n1) && (matriz[i][1]==n2)){
                n=1;
            }
            if ((matriz[i][0]==n2) && (matriz[i][1]==n1)){
                n=1;
            }
      }
      return n;
   }
    //función para buscar los números repetidos en una matriz dinámica
        public static int repetidosdin(ArrayList<Integer>[] matriz,int n1,int n2){
        int n = 0;
        for (int j = 0; j < matriz[0].size(); j++) {
                int num1 = matriz[0].get(j);
                int num2 = matriz[1].get(j);
                if(((num1==n1)&&(num2==n2)) || ((num1==n2)&&(num2==n1))){
                    n = 1;
                }
            } 
        return n;
    }

}




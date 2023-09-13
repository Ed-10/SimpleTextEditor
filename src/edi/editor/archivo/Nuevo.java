package edi.editor.archivo;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Nuevo extends JPanel{
    private static JPanel ventana;
    private static JTabbedPane tPane;
    private static ArrayList<JTextPane> listAreaDeTexto;
    private static ArrayList <File> listFile;
    private static ArrayList<JScrollPane> listScroll;
    private static int contadorPanel=0; //Variable para contar cantidad de paneles creados
    private static boolean existePanel = false;//Indica si existe un panel creado
    public static void crearPanel1(){
        ventana=new JPanel();
        listFile.add(new File(""));
        listAreaDeTexto.add(new JTextPane());
        listScroll.add(new JScrollPane(listAreaDeTexto.get(contadorPanel)));
        ventana.add(listScroll.get(contadorPanel));
        tPane.addTab("NewText", ventana);
        tPane.setSelectedIndex(contadorPanel);
        contadorPanel++;
        existePanel=true;
    }
}

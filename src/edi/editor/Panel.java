package edi.editor;

import edi.editor.archivo.Nuevo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    private JTabbedPane tPane;
    private JPanel ventana;
    private ArrayList <JTextPane> listAreaDeTexto;
    private ArrayList <File> listFile;
    private ArrayList<JScrollPane> listScroll;
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
    private JMenuItem elementoItem;
    //crearPanel
    private int contadorPanel=0; //Variable para contar cantidad de paneles creados
    private boolean existePanel = false;//Indica si existe un panel creado
    //crearPanel
    public Panel(){
        //Area del Menu
        JPanel panelMenu=new JPanel();
        menu= new JMenuBar();

        archivo=new JMenu("Archivo");
        editar=new JMenu("Editar");
        seleccion=new JMenu("Seleccionar");
        ver=new JMenu("Ver");
        apariencia=new JMenu("Apariencia");

        menu.add(archivo);
        menu.add(editar);
        menu.add(seleccion);
        menu.add(ver);
        //menu.add(apariencia);

        //Elemenos del menu archivo
        creaItem("Nuevo Archivo","archivo","nuevo");
        creaItem("Abrir Archivo", "archivo","abrir");
        creaItem("Guardar","archivo","guardar");
        creaItem("Guardar como","archivo", "guardarComo");
        //Elemenos del menu editar
        creaItem("Deshacer","editar","");
        creaItem("Rehacer","editar","");
        editar.addSeparator();
        creaItem("Cortar","editar","");
        creaItem("Copiar","editar","");
        creaItem("Pegar","editar","");
        //Elemenos del menu seleccion
        creaItem("Seleccionar Todo","seleccionar","");
        //Elemenos del menu ver
        creaItem("Numeracion","ver","");
        ver.add(apariencia);
        creaItem("Normal","apariencia","");
        creaItem("Dark","apariencia","");

        panelMenu.add(menu);

        //Area de texto
        tPane=new JTabbedPane();
        listFile= new ArrayList<File>();
        listAreaDeTexto=new ArrayList<JTextPane>();
        listScroll=new ArrayList<JScrollPane>();
        //crearPanel();
        add(panelMenu);
        add(tPane);
    }
    public void crearPanel(){
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
    public void creaItem(String opcion, String menu, String accion){
        elementoItem=new JMenuItem(opcion);
        if (menu.equals("archivo")){
            archivo.add(elementoItem);
            if (accion.equals("nuevo")){
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        crearPanel();
                    }
                });
            } else if (accion.equals("abrir")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        crearPanel();
                        JFileChooser selectorArchivo=new JFileChooser();
                        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        int resultado=selectorArchivo.showOpenDialog(listAreaDeTexto.get(tPane.getSelectedIndex()));
                        if (resultado == JFileChooser.APPROVE_OPTION){ //Seleccionando opcion Abrir
                            try {
                                boolean existePatch = false;
                                for (int i=0; i<tPane.getTabCount(); i++){
                                    File file=selectorArchivo.getSelectedFile();
                                    if (listFile.get(i).getPath().equals(file.getPath())) existePatch=true;
                                }
                                if (!existePatch){
                                    File archivo=selectorArchivo.getSelectedFile();
                                    listFile.set(tPane.getSelectedIndex(),archivo);
                                    FileReader entradaArchivo= new FileReader(listFile.get(tPane.getSelectedIndex()).getPath());
                                    BufferedReader miBuffer=new BufferedReader(entradaArchivo);
                                    String linea="";
                                    String titulo=listFile.get(tPane.getSelectedIndex()).getName();
                                    tPane.setTitleAt(tPane.getSelectedIndex(),titulo);//Titulo en pestaña del panel
                                    while (linea!=null){
                                        linea=miBuffer.readLine();//Lectura de lineas del archivo cargado
                                        //if (linea!=null) listAreaDeTexto.get(tPane.getSelectedIndex()).setText(linea);
                                        if (linea!=null) Utilidades.append(linea+"\n",listAreaDeTexto.get(tPane.getSelectedIndex()));
                                    }
                                }else {
                                    for(int i=0; i<tPane.getTabCount();i++){
                                        File file=selectorArchivo.getSelectedFile();
                                        if (listFile.get(i).getPath().equals(file.getPath())){
                                            tPane.setSelectedIndex(i);
                                            //Limitando la generacion de nuevas ventanas
                                            listScroll.remove(tPane.getTabCount()-1);
                                            listAreaDeTexto.remove(tPane.getTabCount()-1);
                                            listFile.remove(tPane.getTabCount()-1);
                                            tPane.remove(tPane.getTabCount()-1);
                                            contadorPanel--;
                                            break;
                                        }
                                    }
                                }
                            }catch (IOException e1){
                                e1.printStackTrace();
                            }
                        }else {
                            int opcionCancelar=tPane.getSelectedIndex();
                            if (opcionCancelar!= -1){
                                listScroll.remove(tPane.getTabCount()-1);
                                listAreaDeTexto.remove(tPane.getTabCount()-1);
                                listFile.remove(tPane.getTabCount()-1);
                                tPane.remove(tPane.getTabCount()-1);
                                contadorPanel--;
                            }
                        }
                    }
                });
            }
        } else if (menu.equals("editar")) {
            editar.add(elementoItem);
        } else if (menu.equals("seleccionar")) {
            seleccion.add(elementoItem);
        } else if (menu.equals("ver")) {
            ver.add(elementoItem);
        }else if (menu.equals("apariencia")){
            apariencia.add(elementoItem);
        }
    }
}
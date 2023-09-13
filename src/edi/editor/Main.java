package edi.editor;

import javax.swing.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Marco marco=new Marco();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setVisible(true);
    }
}
class Marco extends JFrame {
    public Marco(){
        setBounds(300,300,500,400);
        setTitle("Simple Editor :)");
        add(new Panel());
    }
}




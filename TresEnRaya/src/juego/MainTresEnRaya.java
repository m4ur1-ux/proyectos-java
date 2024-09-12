package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;



public class MainTresEnRaya implements ActionListener{
	JFrame pantalla;
	JPanel panel;
	JLabel juego ;
	JProgressBar progreso = null;
	JButton boton;
	JButton boton1;

	public MainTresEnRaya() {
		// TODO Auto-generated constructor stub

		disenio();
		pantalla.setVisible(true);
		fill();	
	}

	
	


private void disenio() {
	// TODO Auto-generated method stub
	pantalla = new JFrame();
	pantalla.setSize(300,250);
	pantalla.setLocationRelativeTo(null);
	pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	panel = new JPanel();
	panel.setLayout(null);
	panel.setBackground(Color.LIGHT_GRAY);
	panel.setSize(300,200);
	pantalla.add(panel);
	
	juego = new JLabel("JUEGO TRES EN RAYA");
	juego.setFont(new Font("Console",Font.PLAIN,20));
	juego.setForeground(Color.white);
	juego.setBounds(25,0,250,30);
	panel.add(juego);
	
	progreso = new JProgressBar();
	progreso.setStringPainted(true);
	progreso.setBounds(20,60,250,20);
	panel.add(progreso);
	
	boton = new JButton("Jugar");
	boton.setBounds(100,100,100,30);
	boton.setFocusable(false);
	boton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	boton.setVisible(false);
	boton.addActionListener(this);
	panel.add(boton);
	
	
	boton1 = new JButton("Autor");
	boton1.setBounds(100,150,100,30);
	boton1.setFocusable(false);
	boton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	boton1.setVisible(false);
	boton1.addActionListener(this);
	panel.add(boton1);
	
	
	
	
	
	
	
}

private void fill() {
	// TODO Auto-generated method stub
	int counter=0;
	while(counter<=100) {
		progreso.setValue(counter);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		counter += 10;
		
	}
	boton.setVisible(true);
	boton1.setVisible(true);

	
}

public static void main(String[] args) {
	// TODO Auto-generated method stub
	new MainTresEnRaya();
	
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource()==boton) {
	       String[] opciones = {"Bot", "ChatGPT"};
	        int seleccion = JOptionPane.showOptionDialog(
	            null, 
	            "Elige La Dificultad", 
	            "Dificultad: ", 
	            JOptionPane.DEFAULT_OPTION, 
	            JOptionPane.QUESTION_MESSAGE, 
	            null, 
	            opciones, 
	            opciones[1]
	        );
	

	        if (seleccion != JOptionPane.CLOSED_OPTION) {
	            String dificultad = opciones[seleccion];
	            Programa juego = new Programa(dificultad);
	        }
	}
	       
	if(e.getSource()==boton1) {
		JOptionPane.showMessageDialog(boton, "Creado por Mauricio Peña Domínguez (Gran Mauricio)");
       
	}
}

}

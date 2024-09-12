package software;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

public class MainAhorcado implements ActionListener{


	JFrame pantalla;
	JPanel panel;
	JLabel juego ;
	JProgressBar progreso = null;
	JButton boton = null;
	public MainAhorcado() {
		
		disenio();
		pantalla.setVisible(true);
		fill();	
		
	}
	
	private void disenio() {
		// TODO Auto-generated method stub
		pantalla = new JFrame();
		pantalla.setSize(300,200);
		pantalla.setLocationRelativeTo(null);
		pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setSize(300,200);
		pantalla.add(panel);
		
		juego = new JLabel("JUEGO DEL AHORCADO");
		juego.setFont(new Font("Console",Font.PLAIN,20));
		juego.setForeground(Color.red);
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
		
		
		
		
		
	}

	private void fill() {
		// TODO Auto-generated method stub
		int counter=0;
		while(counter<=100) {
			progreso.setValue(counter);
			try {
				Thread.sleep(450);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter += 10;
			
		}
		boton.setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainAhorcado();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==boton) {
			pantalla.dispose();
			Ahorcado juego = new Ahorcado();
	        juego.iniciarPartida();
		}
	}

}

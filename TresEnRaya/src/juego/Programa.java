package juego;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class Programa implements ActionListener {

    private JFrame pantalla;
    private JPanel panel;
    private JButton[][] tablero;
    private final int numfilas = 3;
    private final int numcol = 3;
    private boolean jugadorTurno;
    private boolean juegoTerminado;
    private String dificultad;

    public Programa(String dificultad) {
        this.dificultad = dificultad;
        disenio();
        jugadorTurno = turnoinicial();
        pantalla.setVisible(true);
        if (!jugadorTurno) {
            movimientoMaquina();
        }
    }

    private boolean turnoinicial() {
        if((new Random().nextInt(10)) % 2 == 0) {
            JOptionPane.showMessageDialog(panel, "Comienzas tu");
            return true;
        } else {
            JOptionPane.showMessageDialog(panel, "Comienza " + dificultad);
            return false;
        }
    }

    private void movimientoMaquina() {
        if (juegoTerminado) return;
        
        if (dificultad.equals("Bot")) {
            movimientoAleatorio();
        } else if (dificultad.equals("ChatGPT")) {
            movimientoIA();
        }
    }

    private void movimientoAleatorio() {
        boolean movido = false;
        while (!movido) {
            int i = new Random().nextInt(numfilas);
            int j = new Random().nextInt(numcol);
            if (tablero[i][j].getText().equals(" ")) {
                tablero[i][j].setText("0");
                tablero[i][j].setBackground(Color.red);
                tablero[i][j].setEnabled(false);
                movido = true;
                if (verificarGanador("0")) {
                    mostrarMensaje("Ha ganado el bot, eres un poco malo");
                    juegoTerminado = true;
                } else if (esEmpate()) {
                    mostrarMensaje("Es un empate");
                    juegoTerminado = true;
                } else {
                    jugadorTurno = true;
                }
            }
        }
    }

    private void movimientoIA() {
   
    	        if (juegoTerminado) return;

    	        int[] mejorMovimiento = encontrarMejorMovimiento();
    	        int i = mejorMovimiento[0];
    	        int j = mejorMovimiento[1];
    	        
    	        tablero[i][j].setText("0");
    	        tablero[i][j].setBackground(Color.red);
    	        tablero[i][j].setEnabled(false);
    	        if (verificarGanador("0")) {
    	            mostrarMensaje("Ha ganado ChatGPT, GANAN LAS MÁQUINAS");
    	            juegoTerminado = true;
    	        } else if (esEmpate()) {
    	            mostrarMensaje("Es un empate!");
    	            juegoTerminado = true;
    	        } else {
    	            jugadorTurno = true;
    	        }
    	    }
    

    private boolean esEmpate() {
        for (int i = 0; i < numfilas; i++) {
            for (int j = 0; j < numcol; j++) {
                if (tablero[i][j].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disenio() {
        pantalla = new JFrame();
        pantalla.setTitle("JUEGO 3 EN RAYA");
        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantalla.setSize(500, 500);
        pantalla.setLocationRelativeTo(null);
        
        panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setLayout(new GridLayout(3, 3, 5, 5));
        pantalla.add(panel);
    
        tablero = new JButton[numfilas][numcol];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = new JButton(" ");
                tablero[i][j].addActionListener(this);
                panel.add(tablero[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) return;
        
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (e.getSource() == tablero[i][j] && tablero[i][j].getText().equals(" ")) {
                    if (jugadorTurno) {
                        tablero[i][j].setText("X");
                        tablero[i][j].setBackground(Color.blue);
                        tablero[i][j].setEnabled(false);
                        if (verificarGanador("X")) {
                            mostrarMensaje("HAS GANADO");
                            juegoTerminado = true;
                            return;
                        } else if (esEmpate()) {
                            mostrarMensaje("Es un empate");
                            juegoTerminado = true;
                        } else {
                            jugadorTurno = false;
                            if (!dificultad.equals("Otro Jugador")) {
                                movimientoMaquina();
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panel, mensaje);
        int response = JOptionPane.showConfirmDialog(panel, "¿Quieres jugar de nuevo?", "Juego Terminado", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            System.exit(0);
        }
    }

    private void reiniciarJuego() {
        pantalla.dispose();
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

    private boolean verificarGanador(String simbolo) {
        for (int i = 0; i < numfilas; i++) {
            if (tablero[i][0].getText().equals(simbolo) && tablero[i][1].getText().equals(simbolo) && tablero[i][2].getText().equals(simbolo)) {
                return true;
            }
        }
        for (int j = 0; j < numcol; j++) {
            if (tablero[0][j].getText().equals(simbolo) && tablero[1][j].getText().equals(simbolo) && tablero[2][j].getText().equals(simbolo)) {
                return true;
            }
        }
        if (tablero[0][0].getText().equals(simbolo) && tablero[1][1].getText().equals(simbolo) && tablero[2][2].getText().equals(simbolo)) {
            return true;
        }
        if (tablero[0][2].getText().equals(simbolo) && tablero[1][1].getText().equals(simbolo) && tablero[2][0].getText().equals(simbolo)) {
            return true;
        }
        return false;
    }

    private int evaluarTablero() {
        if (verificarGanador("0")) {
            return 10;
        }
        if (verificarGanador("X")) {
            return -10;
        }
        return 0;
    }

    private int minimax(boolean esMax) {
        int puntaje = evaluarTablero();

        if (puntaje == 10) return puntaje;
        if (puntaje == -10) return puntaje;
        if (esEmpate()) return 0;

        if (esMax) {
            int mejor = Integer.MIN_VALUE;
            for (int i = 0; i < numfilas; i++) {
                for (int j = 0; j < numcol; j++) {
                    if (tablero[i][j].getText().equals(" ")) {
                        tablero[i][j].setText("0");
                        mejor = Math.max(mejor, minimax(false));
                        tablero[i][j].setText(" ");
                    }
                }
            }
            return mejor;
        } else {
            int mejor = Integer.MAX_VALUE;
            for (int i = 0; i < numfilas; i++) {
                for (int j = 0; j < numcol; j++) {
                    if (tablero[i][j].getText().equals(" ")) {
                        tablero[i][j].setText("X");
                        mejor = Math.min(mejor, minimax(true));
                        tablero[i][j].setText(" ");
                    }
                }
            }
            return mejor;
        }
    }

    private int[] encontrarMejorMovimiento() {
        int mejorVal = Integer.MIN_VALUE;
        int[] mejorMovimiento = {-1, -1};

        for (int i = 0; i < numfilas; i++) {
            for (int j = 0; j < numcol; j++) {
                if (tablero[i][j].getText().equals(" ")) {
                    tablero[i][j].setText("0");
                    int movimientoVal = minimax(false);
                    tablero[i][j].setText(" ");
                    if (movimientoVal > mejorVal) {
                        mejorMovimiento[0] = i;
                        mejorMovimiento[1] = j;
                        mejorVal = movimientoVal;
                    }
                }
            }
        }
        return mejorMovimiento;
    }

    public static void main(String[] args) {
        // No se necesita especificar dificultad aquí
        new Programa("Bot"); // Valor por defecto, se puede cambiar si es necesario
    }
}

package software;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Clase que implementa el juego del Ahorcado con ayuda del ChatGPT
 * @author Mau 
 */
public class Ahorcado {

    private static final String[] PALABRAS_SECRETAS = { "camara", "teclado", "monitor", "mesa", "pizarra", "profesor",
            "proyector", "abrigo", "teléfono", "terminal", "pantalla", "raton", "ordenador", "silla", "encerado",
            "alumno", "aula", "mochila", "invitado", "editor" };

    private JFrame pantalla;
    private JPanel panelppal;
    private JPanel panelDibujo;
    private JLabel introducir;
    private JLabel word;
    private JLabel wrong;
    private JLabel intentos;
    private JTextField campoletra;
    private JButton elegirletra;
    private String palabraSecreta;
    private char[] vPalabra;
    private boolean[] vIndices;
    private StringBuilder fallos;
    private final int maxIntentos = 6; 
    private int fallosContador;
    
   /**
    * Constructor de la clase ahorcado,configura la parte gráfica del juego.
    * Se usa un JPanel para la clase Graphics, donde se dibujará el ahorcado.
    * 
    */
    public Ahorcado() {
        pantalla = new JFrame();
        pantalla.setSize(800, 500);
        pantalla.setLocationRelativeTo(null);
        pantalla.setLayout(null);
        pantalla.setBackground(Color.LIGHT_GRAY);
        pantalla.setTitle("Juego del Ahorcado");
        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelppal = new JPanel();
        panelppal.setSize(800, 500);
        panelppal.setLayout(null);
        panelppal.setBackground(Color.gray);
        pantalla.add(panelppal);

        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                int x = 100;
                int y = 100;
                int width = 50;
                int height = 50;

               
                switch(fallosContador) {
                case 1:
                	 g.fillRect(x-10, y + 200, 150, 10); // Base 
                	 break;
                case 2:
                	g.fillRect(x-10, y + 200, 150, 10); // Base
                	g.fillRect(x + 80, y, 10, 200); // Poste 
                	break;
                case 3:
                	 g.fillRect(x-10, y + 200, 150, 10); // Base
                	 g.fillRect(x + 80, y, 10, 200); // Poste 
                	 g.fillRect(x, y, 100, 10); // Viga horizontal
                	 break;
                case 4:
                	g.fillRect(x-10, y + 200, 150, 10); // Base
                	g.fillRect(x + 80, y, 10, 200); // Poste 
                	g.fillRect(x, y, 100, 10); // Viga horizontal
                	g.fillRect(x -10, y, 10, 30); // Soga
                	break;
                case 5:
                	g.fillRect(x-10, y + 200, 150, 10); // Base
                	g.fillRect(x + 80, y, 10, 200); // Poste 
                	g.fillRect(x, y, 100, 10); // Viga horizontal
                	g.fillRect(x -10, y, 10, 30); // Soga
                	 g.fillOval(x-20, y+30, 40, 40); // Cabeza
                	 break;
                case 6:
                	g.fillRect(x-10, y + 200, 150, 10); // Base
                	g.fillRect(x + 80, y, 10, 200); // Poste 
                	g.fillRect(x, y, 100, 10); // Viga horizontal
                	g.fillRect(x -10, y, 10, 30); // Soga
                    g.fillOval(x-20, y+30, 40, 40); // Cabeza
                	g.fillRect(x -10, y + 70, 10, 80); // Cuerpo
                	g.setColor(Color.RED); 
                	g.setFont(new Font("Arial", Font.BOLD, 40)); 
                	g.drawString("looser", x + 20, y-10); 
                	break;
                default:
                	break;
                }

              }
                
        };
        panelDibujo.setBounds(400, 0, 500, 500);
        panelDibujo.setBackground(Color.gray);
        panelppal.add(panelDibujo);

        introducir = new JLabel("Introduce letra:");
        introducir.setBounds(100, 100, 100, 30);
        panelppal.add(introducir);

        campoletra = new JTextField();
        campoletra.setBounds(200, 105, 30, 20);
        panelppal.add(campoletra);

        elegirletra = new JButton("Aceptar");
        elegirletra.setBounds(100, 250, 100, 30);
        panelppal.add(elegirletra);

        word = new JLabel();
        word.setBounds(100, 150, 300, 30);
        panelppal.add(word);

        wrong = new JLabel();
        wrong.setBounds(100, 200, 300, 30);
        panelppal.add(wrong);

        intentos = new JLabel("Intentos restantes: " + (maxIntentos - fallosContador));
        intentos.setBounds(40, 50, 200, 50);
        panelppal.add(intentos);

        pantalla.setVisible(true);

        vIndices = new boolean[PALABRAS_SECRETAS.length];
        fallos = new StringBuilder();
        fallosContador = 0;
    }
/**
 * Método para iniciar una partida, dentro del ActionListener del boton se
 * realiza la validación de la letra.
 */
    public void iniciarPartida() {
        elegirletra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoIngresado = campoletra.getText().toLowerCase();
                if (!textoIngresado.isEmpty() && textoIngresado.length() == 1 && Character.isLetter(textoIngresado.charAt(0))) {
                    char letraSeleccionada = textoIngresado.charAt(0);
                    campoletra.setText("");

                    boolean letraCorrecta = false;
                    for (int i = 0; i < palabraSecreta.length(); i++) {
                        if (palabraSecreta.charAt(i) == letraSeleccionada && vPalabra[i] != letraSeleccionada) {
                            vPalabra[i] = letraSeleccionada;
                            letraCorrecta = true;
                        }
                    }

                    if (!letraCorrecta) {
                        fallos.append(letraSeleccionada).append(" ");
                        fallosContador++;
                        intentos.setText("Intentos restantes: " + (maxIntentos - fallosContador));
                    }

                    mostrarEstadoPartida();

                    if (palabraCompletada()) {
                        JOptionPane.showMessageDialog(pantalla, "¡Felicidades! Has ganado.");
                        JOptionPane.showMessageDialog(pantalla, "Adios");
                        pantalla.dispose();
                        } else if (fallosContador >= maxIntentos) {
                        JOptionPane.showMessageDialog(pantalla, "Has perdido. La palabra era: " + palabraSecreta);
                        
                    }
                   
                    panelDibujo.repaint(); 
                	} else {
                    JOptionPane.showMessageDialog(pantalla, "Debes ingresar una única letra válida.");
                	}
            }
        });

        nuevaPalabra();
        vPalabra = new char[palabraSecreta.length()];
        fallos.setLength(0);
        fallosContador = 0;
        intentos.setText("Intentos restantes: " + (maxIntentos - fallosContador));
        mostrarEstadoPartida();
       
    }
/**
 * Usamos la clase StringBuilder para mostrar en pantalla como va nuestra partida.
 */
    private void mostrarEstadoPartida() {
        StringBuilder palabraOculta = new StringBuilder();

        for (char c : vPalabra) {
            palabraOculta.append(c == '\0' ? "_" : c).append(' ');
        }
        word.setText("Palabra: " + palabraOculta.toString());

        wrong.setText("Fallos: " + fallos.toString());
    }
/**
 * Método donde usamos la clase random para elegir una palabra
 */
    private void nuevaPalabra() {
        Random random = new Random();
        int index;
        do {
            index = random.nextInt(PALABRAS_SECRETAS.length);
        } while (vIndices[index]);
        vIndices[index] = true;
        palabraSecreta = PALABRAS_SECRETAS[index];
    }
/**
 * Método de verificacion para ver si hemos conseguido completar la palabra
 * 
 */
    private boolean palabraCompletada() {
        for (char c : vPalabra) {
            if (c == '\0') {
                return false;
            }
        }
        return true;
    }

    
}

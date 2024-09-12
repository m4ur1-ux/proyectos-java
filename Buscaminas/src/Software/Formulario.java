package Software;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


/*A partir de donde cliques tienen que generarse las minas, es decir el primer sitio
 *  donde cliques no puede tener minas alrededor por lo menos en 1 boton de distancia
 *  
 */
public class Formulario {
	JFrame pantalla ;
	JPanel panel;
	int numFilas = 10;
	int numMinas = 10;
	int numCols = 10;
	JButton[][] botonesTablero;
	Border bevelborder;
	Tablero tablerobuscaminas;
	JMenuBar menubar = new JMenuBar();
	JMenuItem menuTamano = new JMenuItem();
	JMenuItem menuNuevoJuego= new JMenuItem();
	JMenuItem menuNumMinas= new JMenuItem();
	JMenuItem jmenuitem1= new JMenuItem();
	JMenu jmenu1= new JMenu();
	GroupLayout layout;
	
	
	
	public Formulario() {
		  	disenio();
	        juegoNuevo();
	        pantalla.setJMenuBar(menubar);
	        pantalla.setVisible(true);
	       
	    }
	    
	    void descargarControles(){
	        if (botonesTablero!=null){
	            for (int i = 0; i < botonesTablero.length; i++) {
	                for (int j = 0; j < botonesTablero[i].length; j++) {
	                    if (botonesTablero[i][j]!=null){
	                        panel.remove(botonesTablero[i][j]);
	                    }
	                }
	            }
	        }
	    }
	    private void juegoNuevo(){
	        descargarControles();
	        cargarcontroles();
	        crearTablero();
	        panel.repaint();
	    }

	private void disenio() {
		// TODO Auto-generated method stub
		pantalla = new JFrame();
		pantalla.setSize(800,800);
		pantalla.setLocationRelativeTo(null);
		pantalla.setBackground(Color.gray);
		pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
			
		panel = new JPanel();
		panel.setSize(800, 800);
		panel.setLayout(layout);
		panel.setBackground(Color.gray);
		pantalla.add(panel);
		
		layout = new GroupLayout(panel);
		layout.setHorizontalGroup( layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 377, Short.MAX_VALUE)
				);
		
		
		jmenuitem1.setText("JMenuItem");
		
		
		jmenu1.setText("Juego");
		
		menuNuevoJuego.setText("Nuevo");
		menuNuevoJuego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				menuNuevoJuegoActionPerformed(e);
			}
		});
		jmenu1.add(menuNuevoJuego);
		
		
		menuTamano.setText("Tamano");
		menuTamano.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				menuTamanoActionPerformed(e);
			}
		});
		jmenu1.add(menuTamano);
		
		
		menuNumMinas.setText("Numero de Minas");
		menuNumMinas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				menuNumMinasActionPerformed(e);
			}
		});
		jmenu1.add(menuNumMinas);
		
		
		menubar.setPreferredSize(new Dimension(800,30));
		menubar.setBackground(Color.LIGHT_GRAY);
		
		menubar.add(jmenu1);
	}
	
	private void menuTamanoActionPerformed(ActionEvent e) {
		int num=Integer.parseInt(JOptionPane.showInputDialog("Digite tamaño de la matriz, n*n"));
        this.numFilas=num;
        this.numCols=num;
        juegoNuevo();

	}
	
	private void menuNuevoJuegoActionPerformed(ActionEvent e) {
		juegoNuevo();
	}
	private void menuNumMinasActionPerformed(ActionEvent e) {
		int num=Integer.parseInt(JOptionPane.showInputDialog("Digite número de Minas"));
        this.numMinas=num;
        juegoNuevo();

	}
	private void crearTablero() {
		tablerobuscaminas = new Tablero(numFilas,numCols,numMinas);
		tablerobuscaminas.setEventoLoose(new Consumer<List<Casilla>>() {
			
			@Override
			public void accept(List<Casilla> t) {
				// TODO Auto-generated method stub
				for(Casilla casillaconminas: t) {
					botonesTablero[casillaconminas.getPosFila()][casillaconminas.getPosColumna()].setText("*");
				}
			}
		});
		tablerobuscaminas.setEventoWin(new Consumer<List<Casilla>>() {
			
			@Override
			public void accept(List<Casilla> t) {
				// TODO Auto-generated method stub
				for(Casilla casillaconminas: t) {
					botonesTablero[casillaconminas.getPosFila()][casillaconminas.getPosColumna()].setText(":)");
				}
			}
		});
		tablerobuscaminas.setEventoCasillaAbierta(new Consumer<Casilla>() {

			@Override
			public void accept(Casilla t) {
				// TODO Auto-generated method stub
				botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
				botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor()==0?"":t.getNumMinasAlrededor()+"");

			}
			
		});
		tablerobuscaminas.imprimirTablero();
	}
	private void cargarcontroles() {
		
		int posX = 25;
		int posY = 25;
		int ancho = 30;
		int alto = 30;
		botonesTablero = new JButton[numFilas][numCols];
		for(int i=0; i<botonesTablero.length;i++) {
			for(int j=0;j<botonesTablero[i].length;j++) {
				botonesTablero[i][j] = new JButton();
				botonesTablero[i][j].setName(i+","+j);
				bevelborder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
				botonesTablero[i][j].setBorder(bevelborder);
				if(i==0 && j==0) {
					botonesTablero[i][j].setBounds(posX, posY, ancho, alto);
				}else if(i==0 && j!=0) {
					botonesTablero[i][j].setBounds(botonesTablero[i][j-1].getX()+botonesTablero[i][j-1].getWidth(), posY, ancho, alto);
				}else {
					botonesTablero[i][j].setBounds(botonesTablero[i-1][j].getX(),botonesTablero[i-1][j].getY()+botonesTablero[i-1][j].getHeight(), ancho, alto);
				}
				
				botonesTablero[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						btnClick(e);
						
					}
				});
				panel.add(botonesTablero[i][j]);
				
			}
		}
	}
	
	private void btnClick(ActionEvent e) {
		// TODO Auto-generated method stub
	
		JButton btn = (JButton) e.getSource();
		String [] coordenada = btn.getName().split(",");
		int posFila = Integer.parseInt(coordenada[0]);
		int posCol = Integer.parseInt(coordenada[1]);
	
		tablerobuscaminas.seleccionarCasilla(posFila, posCol);
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Formulario();
	}

}

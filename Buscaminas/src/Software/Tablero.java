	package Software;
	
	import java.util.LinkedList;
	import java.util.List;
	import java.util.function.Consumer;
	
	/**
	 * 
	 * @author Mau
	 */
	public class Tablero {
		Casilla[][] casillas;
		
		int numFilas;
		int numColumnas;
		int numMinas;
		private Consumer<List<Casilla>> eventoLoose;
		private Consumer<List<Casilla>> eventoWin;
		Consumer<Casilla> eventoCasillaAbierta;
		 int numCasillasAbiertas;
		 boolean juegoTerminado;
		
		public Tablero( int numFilas, int numColumnas, int numMinas) {
		
			this.numFilas = numFilas;
			this.numColumnas = numColumnas;
			this.numMinas = numMinas;
			inicializarCasillas();
			
		}
		
		
		public void inicializarCasillas() {
			casillas = new Casilla[this.numFilas][this.numColumnas];
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					casillas[i][j] = new Casilla(i,j);
				}
			}
			generarMinas();
		}
	
	
		private void generarMinas() {
			int minasGeneradas = 0;
			while(minasGeneradas != numMinas) {
				int posFila = (int)(Math.random()*casillas.length);
				int posCol = (int)(Math.random()*casillas[0].length);
				if(!casillas[posFila][posCol].isMina()) {
					casillas[posFila][posCol].setMina(true);
					minasGeneradas++;
				}
			}
			actualizarMinasAlrededor();
		}
		
		public void imprimirTablero() {
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					System.out.print(casillas[i][j].isMina()?"*":"0");
				}
				System.out.println("");
			}
			
		}
		private void imprimirPistas() {
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					System.out.print(casillas[i][j].getNumMinasAlrededor());
				}
				System.out.println("");
			}
			
		}
		
		private void actualizarMinasAlrededor() {
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					if(casillas[i][j].isMina()) {
						List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(i, j);
						casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlredeor());
				}
				}
			}
		}
		
		private List<Casilla> obtenerCasillasAlrededor(int posFila, int posCol){
			List<Casilla> listaCasillas = new LinkedList<>();
			for(int i=0; i<8; i++) {
				int tempPosFila = posFila;
				int tempPosCol = posCol;
				switch(i) {
					case 0:
						//Arriba
						tempPosFila --;
						break;
					case 1:
						//Arriba Derecha
						tempPosFila --;
						tempPosCol ++;
						break;
					case 2:
						//Derecha
						tempPosCol ++;
						break;
					case 3:
						//Derecha Abajo
						tempPosCol ++;
						tempPosFila ++;
						break;
					case 4:
						//Abajo
						tempPosFila ++;
						break;
					case 5:
						//Abajo izquierda
						tempPosFila ++;
						tempPosCol --;
						break;
					case 6:
						//Izquierda
						tempPosCol --;
						break;
					case 7:
						//Izquierda Arriba
						tempPosFila --;
						tempPosCol --;
						break;
					
				}
			if(tempPosFila>=0 && tempPosFila<this.casillas.length 
					&& tempPosCol>=0 && tempPosCol<this.casillas[0].length) {
				listaCasillas.add(this.casillas[tempPosFila][tempPosCol]);
			}
			}
			return listaCasillas;
		}
		
		List<Casilla> obtenerCasillaconMina(){
			List<Casilla> casillaconminas = new LinkedList<>();
			for(int i=0;i<casillas.length;i++) {
				for(int j=0;j<casillas[i].length;j++) {
					if(casillas[i][j].isMina()) {
						casillaconminas.add(casillas[i][j]);
					}
				}
			}
			return casillaconminas;
		}
		
		public void seleccionarCasilla (int posFila,int posCol) {
			eventoCasillaAbierta.accept(this.casillas[posFila][posCol]);
			if(this.casillas[posFila][posCol].isMina()) {
				eventoLoose.accept(obtenerCasillaconMina());
			}else if (this.casillas[posFila][posCol].getNumMinasAlrededor()==0) {
				marcarCasillaAbierta(posFila, posCol);
				List<Casilla> casillasalrededor = obtenerCasillasAlrededor(posFila,posCol);
				
				for(Casilla casilla : casillasalrededor) {
					if(!casilla.isAbierta()) {
						seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
					}
				}
			}else {
				marcarCasillaAbierta(posFila, posCol);
			}
			if(partidaGanada()) {
				eventoWin.accept(obtenerCasillaconMina());
			}
		}
			
			
	
	
		public void setEventoLoose(Consumer<List<Casilla>> eventoLoose) {
			this.eventoLoose = eventoLoose;
		}
		
		public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
			this.eventoCasillaAbierta = eventoCasillaAbierta;
		}
		
		void marcarCasillaAbierta(int posFila, int posCol) {
			if(!this.casillas[posFila][posCol].isAbierta()) {
				numCasillasAbiertas++;
				this.casillas[posFila][posCol].setAbierta(true);
			}
		}
		
		boolean partidaGanada() {
			return numCasillasAbiertas>=(numFilas*numColumnas)-numMinas;
		}
		
		public void setEventoWin(Consumer<List<Casilla>> eventoWin) {
			this.eventoWin = eventoWin;
		}
	
	
		public static void main(String[] args) {
			Tablero tablero = new Tablero(5,5,5);
			tablero.imprimirTablero();
			System.out.println("---");
			tablero.imprimirPistas();
		}
	}

package sudoku;

import java.util.Random;

public class Principal {
	final static int TESTIGO = 0;
	

	public static void main(String[] args) {
		int[][] sudoku = generarSudoku();
		mostrarSudoku(sudoku);

	}

	private static int[][] generarSudoku() {
		int[][] sudoku = new int[9][9];

		do {
			sudoku = devolverSudokuLimpio();
			// Si devuelve un null rellenar sudoku, empezamos de nuevo a generarlo
			sudoku = rellenadorDeSudoku(sudoku);
		} while (sudoku == null);

		return sudoku;
	}

	private static int[][] rellenadorDeSudoku(int[][] sudoku) {

		Random r = new Random();
		int[] plantillaComprobadora = new int[10];
		for (int i = 1; i < plantillaComprobadora.length; i++) {
			plantillaComprobadora[i] = i;
		}

		for (int i = 0; i < sudoku.length; i++) {

			for (int j = 0; j < sudoku[0].length; j++) {

				do {
					int contador = 0;
					// Buscamos si hemos usado ya todos los números del 1-9
					for (int j2 = 1; j2 < plantillaComprobadora.length; j2++) {
						if (plantillaComprobadora[j2] == TESTIGO) {
							contador++;
						}
					}
					// Significa que es un sudoku irresoluble, puesto que hemos usado todos los
					// números del 1-9
					if (contador == 9) {
						return null;
					}
					// Generamos valor aleatorio
					int valorAleat = r.nextInt(9) + 1;
					// Introducimos el valor aleatorio y lo borramos de la plantilla introduciendo
					// el testigo
					for (int k = 1; k < plantillaComprobadora.length; k++) {
						if (plantillaComprobadora[k] == valorAleat) {
							sudoku[i][j] = valorAleat;
							plantillaComprobadora[k] = TESTIGO;
						}
					}

				} while (numCorrecto(i, j, sudoku[i][j], sudoku));
				// Llenamos de nuevo la plantilla del 1-9
				for (int j2 = 1; j2 < plantillaComprobadora.length; j2++) {
					plantillaComprobadora[j2] = j2;
				}
			}

		}
		return sudoku;
	}

	// Devuelve un sudoku lleno de 0's
	private static int[][] devolverSudokuLimpio() {
		int[][] sudoku = new int[9][9];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[0].length; j++) {
				sudoku[i][j] = 0;
			}
		}
		return sudoku;
	}

	private static boolean numCorrecto(int fila, int columna, int numero, int[][] sudoku) {
		return mirarColumna(columna, fila, sudoku[fila][columna], sudoku)
				|| mirarFila(fila, columna, sudoku[fila][columna], sudoku)
				|| mirarCaja(fila, columna, sudoku[fila][columna], sudoku);
	}
        /**
         * Mira si en esa fila puede entrar un número
         * @param fila
         * @param columna
         * @param numero
         * @param sudoku
         * @return 
         */
	private static boolean mirarFila(int fila, int columna, int numero, int[][] sudoku) {

		for (int j = 0; j < sudoku.length; j++) {

			if (columna != j && sudoku[fila][j] == numero) {
				return true;
			}

		}

		return false;
	}
        /**
         * Mira si en esa columna puede servir un numero
         * @param columna
         * @param fila
         * @param numero
         * @param sudoku
         * @return 
         */
	private static boolean mirarColumna(int columna, int fila, int numero, int[][] sudoku) {

		for (int i = 0; i < sudoku.length; i++) {

			if (fila != i && sudoku[i][columna] == numero) {
				return true;
			}

		}

		return false;
	}
        /**
         * Comprueba si en una caja es posible incluir un número
         * @param fila
         * @param columna
         * @param numero
         * @param sudoku
         * @return 
         */
	private static boolean mirarCaja(int fila, int columna, int numero, int[][] sudoku) {
		int f = fila - fila % 3;
		int c = columna - columna % 3;
		int finFila=fila - fila % 3+3;
		int finColumna=columna - columna % 3+3;
		for (int i = f; i < finFila; i++) {
			for (int j = c; j < finColumna; j++) {
				if (fila != i && columna != j && sudoku[i][j] == numero) {
					return true;//Significa que ya nos hemos encontrado el número y por tanto no se puede introducir en esa posición
				}
			}
		}
		return false;
	}
	
        /**
         * Método para mostrar el sudoku por consola
         * @param m 
         */
	private static void mostrarSudoku(int[][] m) {
		if (m == null || m.length != 9 || m[0].length != 9) {
			throw new RuntimeException("Un matriz de sudoku debe tener 9 filas y 9 columnas");
		}
		System.out.println("┌──────────┬───────────┬─────────┐");

		for (int i = 0; i < m.length; i++) {
			System.out.print("│");
			for (int j = 0; j < m[0].length; j++) {
				System.out.print(" " + m[i][j] + " ");
				if (j == 2 || j == 5)
					System.out.print("│");
			}
			System.out.print("│");
			System.out.println();
			if (i == 2 || i == 5)
				System.out.println("├──────────┼───────────┼──────────┤");
		}
		System.out.println("└───────────┴───────────┴──────────┘");
	}
}

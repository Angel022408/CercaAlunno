import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lettore {
	public static final int POS_COGNOME = 0;
	public static final int POS_NOME = 1;
	public static final int POS_CITTA = 2;
	public static final int POS_CELL = 3;
	
	String pathFile;
	
	// Genera un oggetto di tipo FileOutputStream in modalità "scrittura" 
	// (no-accodamento)
	public FileOutputStream getFOSWrite() throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(new File(pathFile));
		return fos;
	}
	// Genera un oggetto di tipo FileOutputStream in modalità "accodamento" 
	public FileOutputStream getFOSAppend() throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(new File(pathFile),true);
		return fos;
	}
	// Genera un oggetto di tipo BufferedReader per leggerne tutte le righe.
	public BufferedReader getReader() throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(new File(pathFile)));		
		return br;
	}
	// Aggiorna il campo "pathFile" leggendo il path da prompt (in fase di avvio programma)
	public void LeggiPath() {
		// Creo lettore di parametri da console
		Scanner scanner = new Scanner(System.in);
		// Stampo messaggio a video
		System.out.println("Inserisci il path del file:");
		// Leggo path del file CSV
		pathFile = scanner.nextLine();
	}
	// Popola l'elenco di alunni leggendolo dal file (popola il campo "elenco")
	public void CaricaFile() {
		// Preparo oggetto Generics di tipo ArrayList per oggetti di tipo Alunno
		Alunno.elenco = new ArrayList<Alunno>();
		
		
		/*********** LETTURA DEL FILE *****************************************************/ 
		File fileCSV = new File(pathFile);		
		try {
			// Creo un BufferedReader a partire dal path del CSV 
			BufferedReader reader = new BufferedReader(new FileReader(fileCSV));
			// Dichiaro una String per ospitare la riga del CSV corrente. 
			String line = "";
			// Fin tanto che ci sono righe da leggere... leggo la riga corrente in "line"
			while((line = reader.readLine())!=null) {
				// Divido la stringa usando il separatore "," usando il metodo nativo "split" dell'oggetto String
				String[] infoAlunno = line.split(",");
				// Vado a popolare la lista di alunni man mano che leggo le righe
				Alunno.elenco.add(
					new Alunno(
						infoAlunno[POS_NOME],
						infoAlunno[POS_COGNOME],
						infoAlunno[POS_CITTA],
						infoAlunno[POS_CELL]
					)
				);				
			}
			//System.out.println("Fine della lettura del file");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		/*********** fine LETTURA DEL FILE **************************************************/
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe creata per la gestione del menu
 * @author Samuele Dell'aquila
 *
 */
public class Menu{
	
	String[] opzioni;
	int codiceMenu;
	
	public Menu() {
		codiceMenu = -1;
		// Opzioni possibili
		opzioni = new String[] {
			"ESCI",
			"VISUALIZZA",
			"CERCA",
			"FILTRA",
			"INSERISCI",
			"ELIMINA",
		};
	}
	// Path del file 
	// C:\Users\filan\eclipse-workspace\CercaAlunno\src\elenco.csv	
	/**
	 * Metodo per leggere il codice dell'opzione
	 * selezionata dall'utente
	 */
	public void LeggiCodiceOpzione() {
		Scanner scanner = new Scanner(System.in);		
		codiceMenu = scanner.nextInt();
	}
	/*
	 * Metodo per stampare continuamente il menu, fin tanto che non viene
	 * selezionata l'opzione di uscita.
	 */
	public void StampaMenu() {
		do {
			// Stampa delle possibili voci leggendo da array di String
			for(int i=0;i<opzioni.length;i++) {
				System.out.println(i+". "+opzioni[i]);			
			}
			System.out.println("Opzione: " );
			// Creo un oggetto di tipo Scanner per poter leggere
			// da prompt
			Scanner scanner = new Scanner(System.in);
			// Codice numerico dell'opzione selezionata
			codiceMenu = scanner.nextInt();
			// Metodo che seleziona le operazioni da eseguire dato un codice 
			// di opzione
			EseguiAzione();
			// Ripeto all'infinito fin tanto che il "codiceMenu" è maggiore di zero
			// Questo perchè il codice di uscita è proprio pari a 0 (zero)
		} while( codiceMenu > 0);
	}
	
	public void EseguiAzione() {
		// Serie di "case" (uno per tipo opzione)
		switch(codiceMenu) {
			case 0:
				System.out.println("Arrivederci");
				break;
			case 1:
				Scanner _scanner = new Scanner(System.in);
				System.out.println("Vuoi leggere tutti i record ? [1=Sì, 0=No]");
				int prosegui = _scanner.nextInt();
				// Se l'utente decide di procedere, stampiamo l'intero elenco 
				// di nomi nel file .CSV
				if( prosegui == 1 ) {
					// Semplicemente leggo l'elenco dall'ArrayList caricato 
					// in fase di avvio del programma
					for(Alunno _alunnoTrovato : Alunno.elenco) {
						System.out.println(
						"COGNOME: "+_alunnoTrovato.cognome + "\n"+
								"NOME: "+_alunnoTrovato.nome + "\n"+
								"CITTA: "+_alunnoTrovato.citta + "\n"+
								"CELL: "+_alunnoTrovato.cell + "\n"+
								""
						);
					}
				}
				break;
			case 2:
				// CERCA
				System.out.println("Inserisci parola di ricerca:");
				// Leggo path del file CSV
				_scanner = new Scanner(System.in);
				String parolaRicerca = _scanner.nextLine();
				// Elenco degli alunni trovati
				ArrayList<Alunno> alunniTrovati = Alunno.CercaAlunno(parolaRicerca);
				System.out.println("Alunni trovati: "+alunniTrovati.size());
				for(Alunno _alunnoTrovato : alunniTrovati) {
					System.out.println(
						"COGNOME: "+_alunnoTrovato.cognome + "\n"+
						"NOME: "+_alunnoTrovato.nome + "\n"+
						"CITTA: "+_alunnoTrovato.citta + "\n"+
						"CELL: "+_alunnoTrovato.cell + "\n"+
						""
					);
				}
				break;	
			case 3:
				// FILTRA
				System.out.println("Inserisci parola di filtro:");
				// Leggo path del file CSV
				_scanner = new Scanner(System.in);
				String parolaFiltro = _scanner.nextLine();
				// Gli alunni risultanti sono calcolati dal metodo statico
				// "FiltraAlunno" della classe "Alunno"
				ArrayList<Alunno> alunniFiltrati = Alunno.FiltraAlunno(parolaFiltro);
				System.out.println("Alunni trovati: "+alunniFiltrati .size());
				for(Alunno _alunnoTrovato : alunniFiltrati ) {
					System.out.println(
						"COGNOME: "+_alunnoTrovato.cognome + "\n"+
						"NOME: "+_alunnoTrovato.nome + "\n"+
						"CITTA: "+_alunnoTrovato.citta + "\n"+
						"CELL: "+_alunnoTrovato.cell + "\n"+
						""
					);
				}
				break;	
			case 4: 
				// INSERISCI
				// Creo un nuovo oggetto di tipo "Alunno"
				// che inizialmente non ha campi popolati
				Alunno alunnoNuovo = new Alunno();
				// Inizio a leggere uno per volta i campi dell'istanza "alunnoNuovo"
				// in modo da popolare ogni informazione letta da prompt.
				_scanner = new Scanner(System.in);
				System.out.println("Inserisci i dati alunno:");
				System.out.println("Nome:");        
				alunnoNuovo.nome = _scanner.nextLine();
				System.out.println("Cognome:");        
				alunnoNuovo.cognome = _scanner.nextLine();
				System.out.println("Città:");        
				alunnoNuovo.citta = _scanner.nextLine();
				System.out.println("Cellulare:");        
				alunnoNuovo.cell = _scanner.nextLine();
				try {
					// Invoco il metodo "salvaSuFile" per salvare su file CSV
					// Creando un FileOutputStream aperto in modalità "Append"
					// Cioè una volta aperto, non vado a cancellare il contenuto 
					// precedente del file, ma lo vado accodare.
					alunnoNuovo.salvaSuFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {			
					e.printStackTrace();
				}
				break;
			case 5: 
				System.out.println("Inserisci criterio di cancellazione:");
				_scanner = new Scanner(System.in);
				try {
					// Dapprima creo una stringa vuota (buffer) su cui vado ad accodare 
					// ogni riga letta dal file. 
					// Se almeno un campo della riga corrente contiene un criterio di 
					// eliminazione, allora la stringa corrente non verrà accodata.
					// Alla fine della lettura di tutto il file, apro un oggetto di tipo
					// FileOutputStream,questa volta non in modalità "append", in modo da 
					// cancellare ogni traccia precedente del file e lo utilizzo per
					// scriverci dentro l'attuale contenuto del buffer.
					Alunno.elimina(_scanner.nextLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("CODICE ERRATO !");					
		}
	}
}
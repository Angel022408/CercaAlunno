import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Alunno{
	public String nome;
	public String cognome;
	public String citta; 
	public String cell;
	
	public static ArrayList<Alunno> elenco;
	
	public Alunno(String _nome, String _cognome, String _citta, String _cell) {
		nome = _nome; 
		cognome = _cognome; 
		citta = _citta; 
		cell = _cell;
	}
	
	public Alunno() {
		
	}
	
	// Cerca un alunno verificando tutti i campi (nome,cognome,citta,cellulare) 
	// attraverso un match esatto delle stringhe.
	public static ArrayList<Alunno> CercaAlunno(String parolaRicerca) {
		ArrayList<Alunno> trovati = new ArrayList<Alunno>();
		Alunno alunno = null;
		for( Alunno _alunno : elenco ) {
			if( alunnoTrovato(_alunno,parolaRicerca) ) { 
				trovati.add(_alunno);
			}
		}
		return trovati;
	}
	
	// Cerca un alunno verificando tutti i campi (nome,cognome,citta,cellulare) 
	// attraverso un match intermedio, ovvero cerca anche porzioni di stringhe.
	public static ArrayList<Alunno> FiltraAlunno(String parolaRicerca) {
		ArrayList<Alunno> trovati = new ArrayList<Alunno>();
		Alunno alunno = null;
		for( Alunno _alunno : elenco ) {
			if( alunnoFiltrato(_alunno,parolaRicerca) ) { 
				trovati.add(_alunno);
			}
		}
		return trovati;
	}
	
	// Determina se l'alunno passato come parametro contiene, in modalità "ricerca esatta", 
	// la parola all'interno di almeno un campo
	public static boolean alunnoTrovato(Alunno alunno, String parolaRicerca) {
		return (
			alunno.nome.equalsIgnoreCase(parolaRicerca) ||
			alunno.cognome.equalsIgnoreCase(parolaRicerca) || 
			alunno.citta.equalsIgnoreCase(parolaRicerca) || 
			alunno.cell.equalsIgnoreCase(parolaRicerca) 
 		);
	}
	// Determina se l'alunno passato come parametro contiene, in modalità "ricerca intermedia", 
	// la parola all'interno di almeno un campo	
	public static boolean alunnoFiltrato(Alunno alunno, String parolaFiltro) {
		return (
			// PAROLA: Dell'aquila
			// FILTRO: aquila -----> indexOf --> 5
			// FILTRO: test -----> indexOf --> -1
			// FILTRO: Del -----> indexOf --> 0	
			// FILTRO: AqUiLa -----> 
			alunno.nome.toLowerCase().indexOf(parolaFiltro.toLowerCase()) > -1 ||
			alunno.cognome.toLowerCase().indexOf(parolaFiltro.toLowerCase()) > -1 ||
			alunno.citta.toLowerCase().indexOf(parolaFiltro.toLowerCase()) > -1 ||
			alunno.cell.toLowerCase().indexOf(parolaFiltro.toLowerCase()) > -1 
			
 		);
	}
	
	// Apre il file CSV in modalità "accodamento" attraverso l'oggetto
	// FileOutputStream per aggiungere nuovi alunni.
	public void salvaSuFile() throws UnsupportedEncodingException, IOException{	
		FileOutputStream fos = Main.lettore.getFOSAppend();
		fos.write(new String(cognome+",").getBytes("UTF-8"));
		fos.write(new String(nome+",").getBytes("UTF-8"));
		fos.write(new String(citta+",").getBytes("UTF-8"));
		fos.write(new String(cell+"\n").getBytes("UTF-8"));
		fos.flush();
		fos.close();
		Main.lettore.CaricaFile();
	}
	// Elimina l'alunno individuato per (nome,cognome,citta,cellulare) dal file .CSV
	public static void elimina(String criterioDiEliminazione) throws IOException {
		String contenutoNuovoFile = ""; 
		BufferedReader br = Main.lettore.getReader();
		while(true) {
			String line = br.readLine(); 
			if( line == null ) {
				break;
			}
			// Accavone,Fabio,Andria,255262423454235
			String[] dettagli = line.split(",");
			boolean accoda = true;
			for(String _dettaglio : dettagli) {
				if( _dettaglio.equalsIgnoreCase(criterioDiEliminazione) ) {
					accoda=false;
					break;
				}
			}
			if( accoda )
				contenutoNuovoFile += line+"\n";
		}
		FileOutputStream fos = Main.lettore.getFOSWrite();
		fos.write(contenutoNuovoFile.getBytes("UTF-8"));
		fos.flush();
		fos.close();		
	}
}
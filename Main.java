import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static Lettore lettore;
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		lettore = new Lettore();
		lettore.LeggiPath();
		lettore.CaricaFile();
		menu.StampaMenu();
	}
}






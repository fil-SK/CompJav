package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        
	        Program prog = (Program)(s.value); 
	        
	        
	        //Tab.init();			// Inicijalizacija tabele simbola
	        ExtendedSymTab.init();	// Inicijalizacija tabele simbola - moja
	        
	        
			// Ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			
			
			// Ispis prepoznatih programskih konstrukcija
			SemanticPass v = new SemanticPass();
			prog.traverseBottomUp(v); 
	      
			
			// Ovaj deo ispisace se nakon sto se isparsira ceo program
			// Zato ovde mozes da imas neki ispis koji se desava nakon sto si ceo program obradio
			
			log.info("Ukupan br. print naredbi, bez dodatnog argumenta: " + v.brojacPrintBezArg);
			log.info("Deklarisano promenljivih: " + v.brojacPromenljivih);
		
			
			log.info("===================================");
			
			// Tab.dump();			// Ispis sadrzaja tabele simbola
			ExtendedSymTab.dump();	// Ispis sadrzaja tabele simbola - moja
			
			
			// Nacin realizacije DumpSymbolTableVisitor je takav da ne podrzava bool tip, i zato se on ne ispisuje uopste
			
			
			
			// Ako parser nije detektovao gresku i ako nije doslo do greske prilikom semanticke analize (kod v)
			if(!p.errorDetected && v.passed()) {
				
				log.info("Parsiranje USPESNO zavrseno!");
				
				// Ako je parsiranje uspesno odradjeno vrsimo generisanje koda
				
				File objFile = new File("test/program.obj");			// Fajl koji ce predstavljati izlazni obj fajl
				
				if(objFile.exists()) {
					objFile.delete();
				}
				
				CodeGenerator codeGen = new CodeGenerator();
				prog.traverseBottomUp(codeGen);
				
				
				// Za vreme obilazenja stabla pamtimo informacije koje koristimo kasnije
				Code.dataSize = v.nVars;						// Iz parsera dohvatamo broj promenljivih u programu
				Code.mainPc = codeGen.getMainPc();				// mainPC obezbedjuje generator koda
				Code.write(new FileOutputStream(objFile));		// Pravimo output stream u objektni fajl
				
			}
			else {
				log.error("Parsiranje NIJE uspesno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}

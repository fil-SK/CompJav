package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import org.apache.log4j.*;

/* Ova klasa sluzi da implementira obilazak AST-a, nakon sto ga parser konstruise.
 * Pisemo visit metode samo za klase onih smena kojima je potrebna neka obrada.
 * 
 * Ova klasa formirana je tokom faze 2 - ali se ovaj, i ostatak pravog sadrzaja nalaze u klasi RuleVisitor
 * 
 *  */

public class RuleVisitor extends VisitorAdaptor{

	Logger log = Logger.getLogger(getClass());
	
	int brojacPrintBezArg = 0;		// Brojac koliko puta je pozvana print funkcija, bez argumenta
	int brojacPromenljivih = 0;		// Brojac deklarisanih promenljivih
	
	
	
	
	// ---------------------- Metode za deklaraciju promenljivih START ----------------------
	
	public void visit(RegularVariable RegularVariable) {
		brojacPromenljivih++;
	}
	
	// ---------------------- Metode za deklaraciju promenljivih END ------------------------
	
	
	
	// -------------------- Metode za funkciju PRINT START ------------------------
	
	public void visit(PrintNoArgStatement PrintNoArgStatement) {
		brojacPrintBezArg++;
		log.info("Prepoznata print naredba");
	}
	
	// -------------------- Metode za funkciju PRINT END ------------------------
	
	
	// -------------------- Metode za oporavak od greske START --------------------
	
	public void visit(SyntaxError syntaxError) {
		
	}
	
	// -------------------- Metode za oporavak od greske END --------------------
}
 
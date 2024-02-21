package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

/** Ovo je pomocna klasa koja sluzi da se prebroji broj formalnih parametara i lokalnih promenljivih funkcije. */

public class CounterVisitor extends VisitorAdaptor{

	protected int counter;
	
	public int getCounter() {
		return counter;
	}
	
	
	// Pomocne klase ce naslediti CounterVisitor, tako da ce svaka imati svoj counter, za ono sto ta klasa broji
	
	
	/** Pomocna klasa za brojanje formalnih parametara */
	public static class FormalParamCounter extends CounterVisitor{
		
		// Ovde bi trebalo da imam visit metode koje vrste brojanje formalnih parametara funkcije
		// Ali posto ja radim nivo A, koji obuhvata samo main, koji nema parametre, onda mi ovo efektivno ostaje prazno
		
		
	}
	
	
	
	/** Pomocna klasa za brojanje lokalnih promenljivih */
	public static class LocalVarCounter extends CounterVisitor{
		
		
		public void visit(RegularVariable singleVar) {
			counter++;
		}
		
		public void visit(MultipleRegularVarDecl multipleVars) {
			counter++;
		}
		
		public void visit(ArrayVariable singleArrayVar) {
			counter++;
		}
		
		public void visit(MultipleArrayVarDecl multipleArrayVars) {
			counter++;
		}
	}
}

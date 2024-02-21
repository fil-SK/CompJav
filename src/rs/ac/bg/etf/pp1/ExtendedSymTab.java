package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ExtendedSymTab extends Tab{

	// Doslovno sam samo ubacio bool polje, currentLevel sam napravio novo privatno polje, zato sto nisam mogao da nasledim private polje iz Tab klase
	// I onda kopirao init metodu i u njoj ubacio Obj cvor za tip Bool
	
	
	public static final Struct boolType = new Struct(Struct.Bool);		// Uvodjenje tipa Bool
	
	private static int currentLevel; // nivo ugnezdavanja tekuceg opsega ; Ovo moram da imam jer ne moze da se nasledi
	
	
	
	
	/**
	 * Inicijalizacija universe opsega, tj. njegovo popunjavanje Obj cvorovima,
	 * kao sto je izlozeno na vezbama i predavanjima. Razlika je sto se Obj
	 * cvorovu umecu u hes tabelu.
	 * Ovo je ista init metoda kao u Tab klasi, samo sto je dodato polje Bool
	 */
	public static void init() {
		
		Scope universe = currentScope = new Scope(null);
		
		universe.addToLocals(new Obj(Obj.Type, "int", intType));
		universe.addToLocals(new Obj(Obj.Type, "char", charType));
		universe.addToLocals(new Obj(Obj.Type, "bool", boolType));			// Dodat Bool cvor
		
		universe.addToLocals(new Obj(Obj.Con, "eol", charType, 10, 0));
		universe.addToLocals(new Obj(Obj.Con, "null", nullType, 0, 0));
		
		universe.addToLocals(chrObj = new Obj(Obj.Meth, "chr", charType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 0, 1));
			chrObj.setLocals(currentScope.getLocals());
			closeScope();
		}
		
		universe.addToLocals(ordObj = new Obj(Obj.Meth, "ord", intType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "ch", charType, 0, 1));
			ordObj.setLocals(currentScope.getLocals());
			closeScope();
		} 
		
		
		universe.addToLocals(lenObj = new Obj(Obj.Meth, "len", intType, 0, 1));
		{
			openScope();
			currentScope.addToLocals(new Obj(Obj.Var, "arr", new Struct(Struct.Array, noType), 0, 1));
			lenObj.setLocals(currentScope.getLocals());
			closeScope();
		}
		
		currentLevel = -1;
		
	}
}

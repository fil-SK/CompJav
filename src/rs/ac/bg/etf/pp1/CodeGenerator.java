package rs.ac.bg.etf.pp1;


import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.CounterVisitor.FormalParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.LocalVarCounter;
import rs.ac.bg.etf.pp1.ast.*;



/** Klasa koja u visit metodama vrsi generisanje bajtkoda. */

public class CodeGenerator extends VisitorAdaptor{

	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}

	
	
	// --------------------- Pomocne promenljive za obradu produkcija START -------
	
	Obj trenutniDesignatorNiz = null;									// Cuvam Obj cvor designatora koji predstavlja niz, ovo mi je potrebno kako bih u MultipleDesignatorExpansionArray visit metodi mogao da uradim load designatora
	 
	
	
	// --------------------- Pomocne promenljive za obradu produkcija END ---------
	
	
	
	
	
	// -------------------- Metode za obradu metoda START -------------------------
	
	
	/** Visit metoda kada se obradi naziv void metode. */
	public void visit(MethodVoidName voidMethodName) {
		
		
		// Ukoliko se radi o metodi main, tada je potrebno postaviti mainPC u CodeGenerator
		if(voidMethodName.getMethodName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		
		
		// Funkcija krece da se izvrsava od adrese ukazane registrom PC
		voidMethodName.obj.setAdr(Code.pc);
		

		
		// Potrebno je izvrsiti potrebne instrukcije koje se izvrsavaju PRE naredbi u telu te metode
		// Instrukcija enter pravi aktivacioni zapis na steku i sve parametre sa ExprStack-a kopira u aktivacioni zapis pozvane fukcije
		
		// Moramo staviti: 1) br. formalnih parametara, 2) br. formal param. + br. lokalnih promenljivih - to cemo dobiti preko pomocne klase CounterVisitor
		
		
		// Prvo vrsimo prebrojavanje formalnih parametara i lokalnih promenljivih funkcije
		SyntaxNode methodNode = voidMethodName.getParent();
		
		LocalVarCounter brojacLokalProm = new LocalVarCounter();
		methodNode.traverseTopDown(brojacLokalProm);
		
		FormalParamCounter brojacFormParametara = new FormalParamCounter();
		methodNode.traverseTopDown(brojacFormParametara);
		// Posto radim nivo A, onda je to samo funkcija main bez parametara, tako da je broj form. param. 0, i tu koritim default inicijalizovanu vrednost za counter, pa ne moram da pozivam ovaj traversal
		
		
		// Enter instrukcija
		Code.put(Code.enter);
		Code.put(brojacFormParametara.getCounter());
		Code.put(brojacFormParametara.getCounter() + brojacLokalProm.getCounter());
		
		
		// Nakon ovoga treba da se obradi telo funkcije, sto se obradjuje kroz StatementsList deo
		// To ce se pokriti kroz odgovarajuce visit metode za te konkretne naredbe koriscene u telu funkcije
		
		
		// Nakon tela funkcije, potrebno je izgenerisati funkcije koje UNISTAVAJU aktivacioni zapis funkcije na steku
		// I koje vrse povratak iz pozvane funkcije.
		// Posto se to radi nakon sto se funkcija obradi, to radim kroz MethodDecl smenu, jer se ona obradjuje nakon sto se cela funkcija isparsira
		// Samim tim, taj deo se poziva na kraju, kao sto je i funkcionalnost za exit i return instrukcije
	}
	
	
	/** MethodDecl za metodu tipa void, bez parametara. */
	public void visit(VoidMethodNoParam voidNoParam) {
		
		// Pozivamo dve metode koje unistavaju aktivacioni zapis na steku i koje rade povratak iz pozvane funkcije
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	// -------------------- Metode za obradu metoda END ---------------------------
	
	
	
	// -------------------- Metode za Factor START --------------------------------
	
	
	/** Factor koji predstavlja prepoznavanje konstante. */
	public void visit(FactorNumConst numConst) {
		
		// Stavljamo konstantu u tabelu simbola
		// Konstante NE ZAUZIMAJU PROSTOR U MEMORIJI, vec se za njih formiraju Obj cvorovi, u cijem Adr polju se nalazi vrednost konstante
		
		Obj numConstNode = ExtendedSymTab.insert(Obj.Con, "$", numConst.struct);
		numConstNode.setLevel(0);													// globalni scope konstante
		numConstNode.setAdr(numConst.getN1());				// Vrednost konstante dobijamo od leksera ; N1 je automatski generisano ime za NUM_CONST terminal
		
		
		// Konstante se pojavljuju u izrazima (Expr) i potrebno ih je staviti na ExprStack
		Code.load(numConstNode);
		
	}
	
	
	public void visit(FactorCharConst charConst) {
		
		// Stavljamo konstantu u tabelu simbola
		// Konstante NE ZAUZIMAJU PROSTOR U MEMORIJI, vec se za njih formiraju Obj cvorovi, u cijem Adr polju se nalazi vrednost konstante
		
		Obj charConstNode = ExtendedSymTab.insert(Obj.Con, "$", charConst.struct);
		charConstNode.setLevel(0);
		charConstNode.setAdr(charConst.getC1());
		
		// Konstante se pojavljuju u izrazima (Expr) i potrebno ih je staviti na ExprStack
		Code.load(charConstNode);
	}
	
	
	public void visit(FactorBoolConst boolConst) {
		
		// Stavljamo konstantu u tabelu simbola
		// Konstante NE ZAUZIMAJU PROSTOR U MEMORIJI, vec se za njih formiraju Obj cvorovi, u cijem Adr polju se nalazi vrednost konstante
		
		Obj boolConstNode = ExtendedSymTab.insert(Obj.Con, "$", boolConst.struct);
		boolConstNode.setLevel(0);
		
		if(boolConst.getB1().equals(true)) {
			boolConstNode.setAdr(1);
		}
		else {
			boolConstNode.setAdr(0);
		}
		
		// Konstante se pojavljuju u izrazima (Expr) i potrebno ih je staviti na ExprStack
		Code.load(boolConstNode);
	}
	
	
	// -------------------- Metode za Factor END ----------------------------------
	
	
	// -------------------- Metode za Designator START ----------------------------
	
	
	/** Ukoliko se koristi niz, ova visit metoda obradjuje Expr koji predstavlja njegovu dimenziju. */
	public void visit(MultipleDesignatorExpansionArray arrayDesignator) {
		
		
		// Postavi adresu niza na ExprStack
		//Code.load(trenutniDesignatorNiz);
		
		/*
		 * U ovom trenutku, ExprStack izgleda ovako:
		 * |const|
		 * | adr | <-- sp
		 * A meni treba da to bude zamenjeno
		 * */
		
		//Code.put(Code.dup_x1);
		//Code.put(Code.pop);
		
		/* dup_x1 ce da napravi sledece
		 * | adr |
		 * |const|
		 * | adr | <-- sp
		 * I onda kada se uradi pop, ostaje mi samo adr i ispod njega const, gde SP pokazuje na const
		 * */
		
		trenutniDesignatorNiz = null;				// Ponisti promenljivu
	}
	
	
	/** Ova metoda je korisna kako bi se, kada se npr. radi sa nizom, uvek obezbedilo da se prvo stavi adresa niza na ExprStack pa tek onda indeks. */
	public void visit(RegularDesignatorName regularDesignatorName) {
		/*
		 * Inicijalno sam imao samo ovo, ali mislim da mi ovako nije ispravno
		 * 
		SyntaxNode regularDesignatorNameParent = regularDesignatorName.getParent();
		SyntaxNode regularDesignatorParent = regularDesignatorNameParent.getParent();
		
		// Ako se Designator obradjuje zbog ReadStatement-a onda ne treba da se radi ovaj load
		if(regularDesignatorParent instanceof ReadStatement) {
			
		}
		else {
			Code.load(regularDesignatorName.obj);
		}
		*/
		
		
		SyntaxNode regularDesignatorNameParent = regularDesignatorName.getParent();
		SyntaxNode regularDesignatorParent = regularDesignatorNameParent.getParent();
		
		// Ako se Designator obradjuje zbog ReadStatement-a onda ne treba da se radi ovaj load
		if(regularDesignatorParent instanceof ReadStatement) {
			
		}
		
		else {
			
			// Designator se obradjuje zbog neceg drugog
			
			// Ukoliko je designator niz, onda zelim da mu se postavi adr na ExprStack, bez obzira da li je u pitanju AssignmentStatement ili ne, prosto cim radim sa nizom uvek hocu da imam njegovu adresu prvo na steku
			if(regularDesignatorName.obj.getType().getKind() == Struct.Array) {
				Code.load(regularDesignatorName.obj);
			}
			
			// Inace, ako nije niz, onda je obicna promenljiva. Obicnu promenljivu ne zelim da ucitavam na stek ukoliko se koristi u smeni AssignmentStatement, zato sto se tada radi upis u promenljivu, pa mi tada ne treba ovaj load, vec da se samo uradi store iz AssignmentStatement smene
			else if(regularDesignatorParent instanceof AssignmentStatement) {
				
			}
			
			// Medjutim, ako jeste obicna promenljiva, ali nije iz smene AssignmentStatement, to znaci da je to neka rvalue primena promenljive, i tada zelim da se ucita njena vrednost
			else {
				Code.load(regularDesignatorName.obj);
			}
			
		}
		
	}
	
	
	/** Generisanje koda za obicnu promenljivu. Ovde se samo vrednost te promenljive stavlja na ExprStack. */
	public void visit(RegularDesignator designator) {
		
		// Kad se obradjuje nizovska promenljiva, prvo je neophodno da se velicina niza N smesti na stek
		// Velicina niza je int tipa, i to ce biti ili neka obicna promenljiva ili konstanta, tako da ce se ona kroz njene odgovarajuce produkcije smestiti na stek
		
		
		// Ne zelis da se generise load designatora ako se izvrsava AssignmentStatement smena, zato sto je tada potrebno da se neka vrednost smesti U TAJ designator
		// Dakle, tada ti treba STORE u designator, a ne load, tako da u tom slucaju ne zelis da se izvrsi to
		
		
		
		trenutniDesignatorNiz = designator.obj;		// Sacuvaj Obj cvor
		
		SyntaxNode designatorParent = designator.getParent();
		
		if(!(designatorParent instanceof AssignmentStatement)) {
			
			// Ovde sam planirao da mi pokriva samo slucaj kada imam Designator koji je oblika niz[index]
			// I onda posto na steku imam adr i index hocu da mi se uradi aload kako bih dobio niz[index]
			// I to je onda u situaciji kada mi se niz[index] NE NALAZI levo od znaka jednako =
			
			// To znaci da je taj Designator zapravo neki Expr, i onda hocu da proverim njegove parente sve dok ne dodjem do Expr
			
			
			
			// U situaciji kada radis niz[index]++ ili niz[index]--, tada moras da na ExprStack imas adr,index,adr,index, gde se jedan par adr,index iskoristi za dohvatanje elementa koji se inkrementira, a drugi par za astore te vrednosti
			if((designatorParent instanceof IncrementStatement && trenutniDesignatorNiz.getKind() == Obj.Elem)
					|| (designatorParent instanceof DecrementStatement && trenutniDesignatorNiz.getKind() == Obj.Elem)) {
				// Da bi mi se na ExprStack-u napravilo adresa i index koje kasnije iskoristim za astore
				Code.put(Code.dup2);
			}
			else if(trenutniDesignatorNiz.getKind() == Obj.Elem){
				
				// Ako je u pitanju niz[index], onda se on u ovoj else grani nalazi jedino kao FactorVariable, a u tom slucaju je potrebno da se uradi aload
				Code.load(designator.obj);
				return;							// U ovom slucaju hoces da ti se prekine ova visit metoda, jer si za taj nizovski element obradio sta je trebalo
			}
			
			
			// load treba da se uradi samo ako nije ReadStatement
			if(designatorParent instanceof ReadStatement) {
				
			}
			else {
				
				// Mislim da bi trebalo da imam ovakve provere i za onaj drugi print
				
				// Za print kod obicne promenljive necu da izbacujem dva puta load na ExprStack
				while(designatorParent != null && !(designatorParent instanceof PrintNoArgStatement)) {
					designatorParent = designatorParent.getParent();
				}
				
				if(designatorParent instanceof PrintNoArgStatement) {
					
				}
				
				else {
					// Code.load(designator.obj);
				}
				
			}
			
		}
	
	}
	
	
	/** Ova metoda je korisna kako bi se, kada se npr. radi sa nizom, uvek obezbedilo da se prvo stavi adresa niza na ExprStack pa tek onda indeks. */
	public void visit(ScopedDesignatorName scopedDesignatorName) {
		
		// Metoda uradjena analogno RegularDesignatorName metodi
		
		/*
		 * Inicijalno je bilo ovako ali mislim da ovo nije dobro
		 * 
		SyntaxNode scopedDesignatorNameParent = scopedDesignatorName.getParent();
		SyntaxNode scopedDesignatorParent = scopedDesignatorNameParent.getParent();
		
		// Ako se Designator obradjuje zbog ReadStatement-a onda ne treba da se radi ovaj load
		if(scopedDesignatorParent instanceof ReadStatement) {
			
		}
		else {
			Code.load(scopedDesignatorName.obj);
		}
		
		*/
		
		
		SyntaxNode scopedDesignatorNameParent = scopedDesignatorName.getParent();
		SyntaxNode scopedDesignatorParent = scopedDesignatorNameParent.getParent();
		
		// Ako se Designator obradjuje zbog ReadStatement-a onda ne treba da se radi ovaj load
		if(scopedDesignatorParent instanceof ReadStatement) {
			
		}
		
		else {
			
			// Designator se obradjuje zbog neceg drugog
			
			// Ukoliko je designator niz, onda zelim da mu se postavi adr na ExprStack, bez obzira da li je u pitanju AssignmentStatement ili ne, prosto cim radim sa nizom uvek hocu da imam njegovu adresu prvo na steku
			if(scopedDesignatorName.obj.getType().getKind() == Struct.Array) {
				Code.load(scopedDesignatorName.obj);
			}
			
			// Inace, ako nije niz, onda je obicna promenljiva. Obicnu promenljivu ne zelim da ucitavam na stek ukoliko se koristi u smeni AssignmentStatement, zato sto se tada radi upis u promenljivu, pa mi tada ne treba ovaj load, vec da se samo uradi store iz AssignmentStatement smene
			else if(scopedDesignatorParent instanceof AssignmentStatement) {
				
			}
			
			// Medjutim, ako jeste obicna promenljiva, ali nije iz smene AssignmentStatement, to znaci da je to neka rvalue primena promenljive, i tada zelim da se ucita njena vrednost
			else {
				Code.load(scopedDesignatorName.obj);
			}
			
		}
	}
	
	
	/** Generisanje koda za obicnu ili nizovsku promenljivu. */
	public void visit(ScopedDesignator scopedDesignator) {
		
		// Radjeno analogno kao u RegularDesignator ; Ovde su izbaceni detaljniji komentari, za vise info. pogledaj tamo
		
		
		// Ne zelis da se generise load designatora ako se izvrsava AssignmentStatement smena, zato sto je tada potrebno da se neka vrednost smesti U TAJ designator
		// Dakle, tada ti treba STORE u designator, a ne load, tako da u tom slucaju ne zelis da se izvrsi to
		
		trenutniDesignatorNiz = scopedDesignator.obj;		// Sacuvaj Obj cvor
		
		SyntaxNode scopedDesignatorParent = scopedDesignator.getParent();
		
		if(!(scopedDesignatorParent instanceof AssignmentStatement)) {
			
			// Ovde sam planirao da mi pokriva samo slucaj kada imam Designator koji je oblika niz[index]
			// I onda posto na steku imam adr i index hocu da mi se uradi aload kako bih dobio niz[index]
			// I to je onda u situaciji kada mi se niz[index] NE NALAZI levo od znaka jednako =
			
			// To znaci da je taj Designator zapravo neki Expr, i onda hocu da proverim njegove parente sve dok ne dodjem do Expr
			
			
			
			// U situaciji kada radis niz[index]++ ili niz[index]--, tada moras da na ExprStack imas adr,index,adr,index, gde se jedan par adr,index iskoristi za dohvatanje elementa koji se inkrementira, a drugi par za astore te vrednosti
			if((scopedDesignatorParent instanceof IncrementStatement && trenutniDesignatorNiz.getKind() == Obj.Elem)
					|| (scopedDesignatorParent instanceof DecrementStatement && trenutniDesignatorNiz.getKind() == Obj.Elem)) {
				// Da bi mi se na ExprStack-u napravilo adresa i index koje kasnije iskoristim za astore
				Code.put(Code.dup2);
			}
			
			else if(trenutniDesignatorNiz.getKind() == Obj.Elem){
				
				// Ako je u pitanju niz[index], onda se on u ovoj else grani nalazi jedino kao FactorVariable, a u tom slucaju je potrebno da se uradi aload
				Code.load(scopedDesignator.obj);
				return;							// U ovom slucaju hoces da ti se prekine ova visit metoda, jer si za taj nizovski element obradio sta je trebalo
			}
			
			
			// load treba da se uradi samo ako nije ReadStatement
			if(scopedDesignatorParent instanceof ReadStatement) {
				
			}
			else {
				
				// Mislim da bi trebalo da imam ovakve provere i za onaj drugi print
				
				// Za print kod obicne promenljive necu da izbacujem dva puta load na ExprStack
				while(scopedDesignatorParent != null && !(scopedDesignatorParent instanceof PrintNoArgStatement)) {
					scopedDesignatorParent = scopedDesignatorParent.getParent();
				}
				
				if(scopedDesignatorParent instanceof PrintNoArgStatement) {
					
				}
				else {
					// Code.load(designator.obj);
				}
				
			}
			
		}

	}
	
	
	
	/** Visit metoda koja se posecuje kada se formira niz, tj. kada se obradjuje new type[size]; */
	public void visit(FactorObjectArray factorArray) {
		
		// Expr je dimenzija niza koji se pravi, i on ce vec biti postavljen na ExprStack
		
		// Prvo da "pocistim" stek, jer mi je RegularDesignatorName postavio adresu niza, a ovde mi to ne odgovara
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.pop);
		
		
		// E sad dalje ide potreban kod za ovu visit metodu
		Code.put(Code.newarray);
		
		// U zavisnosti od toga da li se pravi niz bajtova (za tip char) ili niz reci (za ostalo), koristi se drugaciji arg
		if(factorArray.getType().struct.getKind() == Struct.Char) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
		
		// Nakon ovog izvrsavanja, na steku ce se naci adresa koja predstavlja prostor koji je dodeljen nizu
		// Posto se niz pravi kao niz = new int[size], za to se koristi AssignmentStatement, tako da se tamo radi STORE koja smesta tu vrednost na adresno polje niza
	}
	
	
	/** Generisanje koda za naredbu dodele vrednosti. */
	public void visit(AssignmentStatement assignmentStatement) {
	
		// Designator je tipa Obj - to je memorijska lokacija kojoj je moguce dodeliti neku vrednost
		
		// Smatramo da je Expr vec obradjen unutar gramatike i da se na ExprStack nalazi vrednost koji treba dodeliti.
		// Potrebno je sacuvati tu vrednost u promenljivu
		
		
		// Dohvatamo vrednost sa ExprStack i cuvamo je u Destination
		
		
		
		Code.store(assignmentStatement.getDesignator().obj);			// Ovo ce generisati odgovarajucu instrukciju, na osnovu tipa Obj cvora
		
		
	}
	
	
	// -------------------- Metode za Designator END ------------------------------
	
	
	// -------------------- Metode za Expr START ----------------------------------
	
	
	public void visit(MultipleAddop multipleAddop) {
		
		// Ovde samo guras odgovarajucu operaciju na stek, zato sto se MultipleAddop smena sastoji od neterminala Addop i Term
		// Prvo se obidje Addop, pa onda Term, pa tek onda taj konkretni MultipleAddop
		// Samim tim, prvo ce se na steku naci vrednost, pa tek onda operacija, sto je i trazeni redosled
		
		if(multipleAddop.getAddop() instanceof AddopAddition) {
			// Operacija sabiranja
			Code.put(Code.add);
		}
		else {
			// Operacija oduzimanja
			Code.put(Code.sub);
		}
	}
	
	public void visit(MultipleMulop multipleMulop) {
		
		// Analogno kao i MultipleAddop
		
		if(multipleMulop.getMulop() instanceof MulopMultiplication) {
			Code.put(Code.mul);
		}
		else if(multipleMulop.getMulop() instanceof MulopDivision) {
			Code.put(Code.div);
		}
		else {
			Code.put(Code.rem);		// Remainder, sto je moduo
		}
	}
	
	
	public void visit(NegatedTerm negatedTerm) {
		// Negiraj term koji je vec na steku
		Code.put(Code.neg);
	}
	
	
	public void visit(NegativePrefixTerm negativeTerm) {
		// Nista ne treba, obradjeno je kroz NegatedTerm i kroz Term smene
	}
	
	
	// -------------------- Metode za Expr END ----------------------------------
	
	
	// -------------------- Metode za obradu naredbi (Statements) START -----------
	
	
	/** Generisanje koda za return Expr; */
	public void visit(ReturnStatement returnStatement) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	/** Generisanje koda za return; */
	public void visit(NoReturnStatement noReturnStatement) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public void visit(IncrementStatement incStatement) {
		
		// Ocekujemo da je Designator vec na ExprStack
		Obj designatorNode = incStatement.getDesignator().obj;
		
		
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorNode);
		
	}
	
	
	public void visit(DecrementStatement decStatement) {
		
		// Analogno kao za inkrement; Ocekujemo da je Designator vec na ExprStack
		
		Obj designatorNode = decStatement.getDesignator().obj;
		
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorNode);
	}
	
	
	// -------------------- Metode za obradu naredbi (Statements) END -------------
	
	
	
	// -------------------- Metode za print funkciju START ------------------------
	
	
	/** Generisanje koda za print funkciju koja se poziva bez argumenta sirine. */
	public void visit(PrintNoArgStatement printNoArg) {
		
		// Ocekujemo da ce vrednost koja se ispisuje BITI na steku
		
		
		// U print naredbi ispisuje se Expr, i to je vrednost koja je vec izracunata u okviru smene za Expr
		// Podrazumeva se da je vrednost za Expr vec stavljena na stek, u delu gramatike gde se Expr obradjuje
		
		Struct exprTipIzraza = printNoArg.getExpr().struct;
		
		
		
		
		if(exprTipIzraza == ExtendedSymTab.intType) {
			Code.loadConst(6);									// Ucitamo konstantu koja predstavlja sirinu - random npr. 6
			Code.put(Code.print);								// Smestamo u bafer bajtkoda
		}
		else if(exprTipIzraza == ExtendedSymTab.charType) {
			Code.loadConst(1);									// Za CHAR stavimo da je sirina 1
			Code.put(Code.bprint);								// Za tip CHAR se koristi bprint
		}
		else if(exprTipIzraza == ExtendedSymTab.boolType) {
			Code.loadConst(6);									// Ucitamo konstantu koja predstavlja sirinu - random npr. 6
			Code.put(Code.print);								// Smestamo u bafer bajtkoda
		}
		
	}
	
	
	/** Generisanje koda za print funkciju koja se poziva SA ARGUMENTOM sirine ispisa. */
	public void visit(PrintWithArgStatement printWithArg) {
		
		// Ocekujemo da ce vrednost koja se ispisuje BITI na steku
		// a onda dodatno treba push-ovati sirinu na kojoj se podatak ispisuje
		
		
		// Generisemo instrukciju const, koja predstavlja sirinu na kojoj se ispisuje podatak
		
		Struct exprTipIzraza = printWithArg.getExpr().struct;
		
		if(exprTipIzraza == ExtendedSymTab.intType) {
			Code.loadConst(printWithArg.getWidth());			// Ucitamo konstantu koja predstavlja sirinu
			Code.put(Code.print);								// Smestamo u bafer bajtkoda
		}
		else if(exprTipIzraza == ExtendedSymTab.charType) {
			Code.loadConst(printWithArg.getWidth());
			Code.put(Code.bprint);								// Za tip CHAR se koristi bprint
		}
		else if(exprTipIzraza == ExtendedSymTab.boolType) {
			Code.loadConst(printWithArg.getWidth());			// Ucitamo konstantu koja predstavlja sirinu
			Code.put(Code.print);	
		}
	}
	
	
	// -------------------- Metode za print funkciju START ------------------------
	
	
	
	// -------------------- Read funkcija START -----------------------------------
	
	
		public void visit(ReadStatement readStatement) {
			
			Struct designatorType = readStatement.getDesignator().obj.getType();
			
			if(designatorType == ExtendedSymTab.intType || designatorType == ExtendedSymTab.boolType) {
				Code.put(Code.read);
			}
			else {
				Code.put(Code.bread);		// Za tip char, posto je on 1 bajt
			}
					
			Code.store(readStatement.getDesignator().obj);		// Nakon toga, store unesene vrednosti u promenljivu
		}
		
	
	// -------------------- Read funkcija END -------------------------------------

		
}

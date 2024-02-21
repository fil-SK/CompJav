package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;					// Da bi pisao visit metode cvorova
import rs.etf.pp1.symboltable.*;				// Da bi mogao da koristis tabelu simbola
import rs.etf.pp1.symboltable.concepts.*;		// Da bi mogao da koristis klase Obj, Struct i Scope



/** Ova klasa sluzi da implementira obilazak AST-a, nakon sto ga parser konstruise.
 * Pisemo visit metode samo za klase onih smena kojima je potrebna neka obrada.
 *
 */

public class SemanticPass extends VisitorAdaptor{

	public static final int GLOBALNA = 1, LOKALNA = 2;		// Iako je globalna sa level=0 i lokalna sa level=1, ja sam ovo pomerio za +1 zato sto hocu da mi vrednost 0 bude situacija kada se ne obradjuju promenljive
	
	
	Logger log = Logger.getLogger(getClass());
	
	boolean errorDetected = false;				// Za proveru da li je doslo do gresaka tokom parsiranja
	int nVars;									// Potrebno za CodeGen

	int brojacPrintBezArg = 0;					// Brojac koliko puta je pozvana print funkcija, bez argumenta
	int brojacPromenljivih = 0;					// Brojac deklarisanih promenljivih
	
	Obj trenutnoObradjivanaMetoda = null;		// Obj cvor metode cija se definicija trenutno obradjuje
	boolean postojiReturnNaredba = false;		// Da li postoji return naredba u funkciji; 
	
	int dosegObradjivanePromenljive = 0;		// Ovde pratim kada se obradjuje VarDecl da li je promenljiva globalna ili lokalna, kako bih znao da li da postavim polje Level na 0 (globalna) ili 1 (lokalna)
	Struct tipPromenljivihIKonstanti = null;	// Kada postoji vise promenljivih ili konstanti odvojenih zarezima, onda ovako pratim kog su oni tipa
												// Posto se svakako tip obradjuje u svojoj visit metodi, a u trenutku obrade jedne naredbe obradjujem ili konstante ili promenljive, onda mogu da gledam ovako.
	
	
	Struct mulopFaktorTip = null;				// Ovde cu da cuvam tip za faktor koji se javlja u Factor Mulop ... Mulop Faktor smeni; Svi oni moraju biti tipa int tako da cu u svim tim smenama da postavljam ovde tip i da svaki put proverim da li je int, a ako nije da onda bacim gresku
	Struct addopTermTip = null;					// Slicno kao za mulopFaktorTip, koristim ovo da bih pratio tipove za term-ove koji se koriste za addop operacije
	
	boolean designatorJeNiz = false;			// Posto designator moze biti obican, a moze npr. biti i niz, onda preko ovoga pratim da li se obradjuje niz ili obican designator
	boolean obradaNewNiz = false;				// Pratim da li obradjujem naredbu niz = new tip[expr], kako bih znao da onda posmatram tip za ELEMENT tog niza u AssignmentStatement-u
	Obj novidesignatorJeNiz = null;				// Ukoliko je designator koji se obradjuje niz, onda ovde cuvamo njegov objektni cvor. To je bitno zato sto u smeni koja za designator obradjuje [Expr] deo, hocemo da u njoj stvorimo Obj cvor tipa Elem, i onda mi treba da za taj novi obj cvor postavim isto ime kao i ime tog designatora koji je niz		
	LinkedList<Obj> listaDesignatoraNiza = new LinkedList<Obj>();		// Posto niz moze da ima vece ugnezdavanje, npr. niz[nizX[nizY[const]]], u ovom slucaju su niz,nizX i nizY designatori, i onda mi treba da cuvam njihove Obj cvorove, kako bih za niz postavio elem od nizX a za nizX od nizY
	
	
	String tekuciNamespace = "";				// Pratim koji je trenutno aktivni namespace koji se obradjuje
	String punoScopedDesignatorIme = "";		// Kada se koristi ident::ident, onda ovde pamtim taj PUN NAZIV
	
	
	// ---------------------- Pomocne metode za prijavu gresaka START ----------------------
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
	public boolean passed() {
		return !errorDetected;
	}
	
	// ---------------------- Pomocne metode za prijavu gresaka END ----------------------
	
	
	
	
	
	// ---------------------- Metode za PROGRAM START ----------------------
	
	
	/** Visit metoda koja se poziva kada se isparsira IDENT koji predstavlja ime programa */
	public void visit(ProgramName programName) {
		
		// Formiramo Obj cvor tipa Prog i ubacujemo ga u trenutno otvoreni opseg (Universe opseg) 
		// Obj cvor tipa prog NEMA tip, prosledjujemo predefinisani tip noType
		
		programName.obj = ExtendedSymTab.insert(Obj.Prog, programName.getProgramName(), ExtendedSymTab.noType);
		ExtendedSymTab.openScope();
		
		// Ovaj scope bice zatvoren kada se zavrsi parsiranje celog programa, tj. kada se pozove visit metoda za Program
		
		
		// Na pocetku programa, prvo se deklarisu globalne promenljive, ako ih ima
		// Tek kada se dodje do MethodDeclList neterminala dolazi se do deklaracija lokalnih promenljivih metode
		dosegObradjivanePromenljive = GLOBALNA;		// Unutar prostora imena su sve promenljive globalne
	 }
	 
	 
	/** Visit metoda koja se poziva kada se isparsira CEO PROGRAM */
	 public void visit(Program program) {
		 
		 // Potrebno je ulancati sve lokalne simbole u cvor Prog i potom zatvoriti opseg
		 
		 nVars = ExtendedSymTab.currentScope.getnVars();
		 
		 ExtendedSymTab.chainLocalSymbols(program.getProgramName().obj);		// Lokalne simbole ulancavamo u Obj cvor PROG
		 ExtendedSymTab.closeScope();
	 }
	
	 
	// ---------------------- Metode za PROGRAM END ----------------------
	
	
	// ---------------------- Metode za Namespace START ------------------
	 
	
	/** Visit metoda koja obradjuje parsiranje imena namespace-a. */ 
	public void visit(NamespaceName namespaceName){
		
		tekuciNamespace = namespaceName.getNamespaceName();
	}
	
	
	public void visit(Namespace namespaceProduction) {
		
		// Samo resetujem da se trenutno ne obradjuje nijedan namespace
		tekuciNamespace = "";
	}
	
	 
	// ---------------------- Metode za Namespace END -------------------- 
	 
	 
	 
	// ---------------------- Metode za Type START ----------------------
	 
	public void visit(RegularType type) {
		 
		// Da li taj Obj cvor vec postoji u tabeli simbola. Ako postoji vraca se taj Obj cvor, ako ne postoji vraca se noObj
		
		Obj typeNode = ExtendedSymTab.find(type.getTypeName());		// Pokusaj da dohvatis taj Obj cvor iz tabele simbola
		 
		if(typeNode == ExtendedSymTab.noObj) {
			// Ovaj Obj cvor ne postoji u tabeli simbola - greska
			report_error("Tip " + type.getTypeName() + " nije pronadjen u tabeli simbola!", null);
			type.struct = ExtendedSymTab.noType;		// Postavimo za neterminal da je njegov tip noType, kako bismo to koristili u proverama gde se taj Type neterminal koristi
		 }
		 else {
			 // Obj cvor POSTOJI u tabeli simbola - ali da li je to ZAPRAVO tip?
			 
			 // U ovom trenutku znamo da ovo sto parsiramo POSTOJI u tabeli simbola, ali moramo da proverimo da li je to nesto sto je ispravno
			 // To je zato sto smo mi ovde preko find metode trazili na osnovu imena, to ime efektivno moze da bude ime neke promenljive, a mi ovde "racunamo" na to da je to tip
			 // Ako je taj string ZAISTA nesto sto predstavlja tip, onda u polje kind tog obj cvora mora imati vrednost Obj.Type
			 // Ako nema, onda je to neka vrsta objektnog cvora, ali sigurno nije tip, a posto mi ovde OCEKUJEMO da to bude tip, to je onda greska
			 
			 
			 if(typeNode.getKind() == Obj.Type) {
				 // Jeste tip, tako da za polje struct postavimo njegov tip
				 type.struct = typeNode.getType();
				 
				 tipPromenljivihIKonstanti = typeNode.getType();		// Cuvamo tip promenljivih
			 }
			 else {
				 // Vratio je obj cvor koji nije Type - dakle, jeste obj cvor ali to nije tip, i to je greska
				 type.struct = ExtendedSymTab.noType;
				 report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
			 }
		 }
	 }
	
	
	public void visit(ScopedType scopedType) {
		
		// TODO
		
	}
	
	
	 
	// ---------------------- Metode za Type END ----------------------
	 
	 
	// ---------------------- Metode za deklaraciju promenljivih START ----------------------
	
	
	/** Visit metoda koja se poziva kada se prepozna obicna promenljiva. Ovo ne obuhvata promenljive koje se navode nakon zareza.
	 * Primer promenljive koja ce biti prepoznata: int a; U slucaju int a,b; promenljiva b nece biti prepoznata u ovoj smeni.
	 * To treba pokriti u odgovarajucim smenama iz MultipleVarDeclList produkcije.  */
	public void visit(RegularVariable regularVariable) {
		
		brojacPromenljivih++;
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imePromenljive = tekuciNamespace + "::" + regularVariable.getVariableName();
			
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(imePromenljive) != ExtendedSymTab.noObj) {
				report_error("Promenljiva " + imePromenljive + " je vec deklarisana!", null);
			}
			else {
							
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
				Obj varNode = ExtendedSymTab.insert(Obj.Var, imePromenljive, regularVariable.getType().struct);
				report_info("Deklarisana promenljiva " + imePromenljive, regularVariable);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					varNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					varNode.setLevel(1);
				}
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(regularVariable.getVariableName()) != ExtendedSymTab.noObj) {
				report_error("Promenljiva " + regularVariable.getVariableName() + " je vec deklarisana!", null);
			}
			else {
				
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
				Obj varNode = ExtendedSymTab.insert(Obj.Var, regularVariable.getVariableName(), regularVariable.getType().struct);
				report_info("Deklarisana promenljiva " + regularVariable.getVariableName(), regularVariable);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					varNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					varNode.setLevel(1);
				}
			}
		}
		
		
		
		
	}
	
	
	/** Visit metoda koja se poziva za prepoznavanje obicne promenljive, ali za one promenljive koje se navode nakon zareza.
	 * Npr. int x,y,z - ova visit metoda obradjuje promeljive y i z. */
	public void visit(MultipleRegularVarDecl moreVariables) {
		
		brojacPromenljivih++;
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imePromenljive = tekuciNamespace + "::" + moreVariables.getAdditionalVariableName();
			
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(imePromenljive) != ExtendedSymTab.noObj) {
							
				// Ako vrati nesto sto nije noObj, znaci da vec postoji u tabeli simbola
				report_error("Promenljiva " + imePromenljive + " je vec deklarisana!", null);
			}
			else {
							
				// Promenljiva ne postoji u tabeli simbola, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
				Obj varNode = ExtendedSymTab.insert(Obj.Var, imePromenljive, tipPromenljivihIKonstanti);
				report_info("Deklarisana promenljiva " + imePromenljive + "(navedena nakon zareza)", moreVariables);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					varNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					varNode.setLevel(1);
				}
			}
			
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(moreVariables.getAdditionalVariableName()) != ExtendedSymTab.noObj) {
				
				// Ako vrati nesto sto nije noObj, znaci da vec postoji u tabeli simbola
				report_error("Promenljiva " + moreVariables.getAdditionalVariableName() + " je vec deklarisana!", null);
			}
			else {
				
				// Promenljiva ne postoji u tabeli simbola, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
				Obj varNode = ExtendedSymTab.insert(Obj.Var, moreVariables.getAdditionalVariableName(), tipPromenljivihIKonstanti);
				report_info("Deklarisana promenljiva " + moreVariables.getAdditionalVariableName() + "(navedena nakon zareza)", moreVariables);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					varNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					varNode.setLevel(1);
				}
			}
		}

	}
	
	
	/** Ova visit metoda poziva se nakon sto se obrade sve deklaracije promenljivih odvojene zarezima. 
	 * Npr. int a,b,c; Ova metoda pozvace se nakon deklaracije promenljive c. */
	public void visit(MultipleVarDecl multipleVariables){
		
		// Posto se ova metoda obradjuje tek kada se obrade sve promenljive odvojene zarezima, onda nju koristimo da resetujemo promenljivu koja cuva tip tih promenljivih odvojenih zarezima
		
		System.out.println("Obradjene sve promenljive do kraja ove naredbe, do znaka tacka-zarez.");
		
		tipPromenljivihIKonstanti = null;
	}
	
	
	/** Visit metoda za deklaraciju nizovskih promenljivih. Npr. int niz[]; */
	public void visit(ArrayVariable arrayVar) {
		
		brojacPromenljivih++;
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imePromenljive = tekuciNamespace + "::" + arrayVar.getArrayName();
			
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(imePromenljive) != ExtendedSymTab.noObj) {
				report_error("Nizovska promenljiva " + imePromenljive + " je vec deklarisana!", null);
			}
			else {
							
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
							
				// Prvo napravimo Struct cvor tipa niza
				Struct arrayTypeNode = new Struct(Struct.Array, arrayVar.getType().struct);
							
				// Pa onda TAJ TIP koristimo za dodavanje u tabelu simbola
				Obj arrayVarNode = ExtendedSymTab.insert(Obj.Var, imePromenljive, arrayTypeNode);
				report_info("Deklarisana nizovska promenljiva " + imePromenljive, arrayVar);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					arrayVarNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					arrayVarNode.setLevel(1);
				}
			}
			
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(arrayVar.getArrayName()) != ExtendedSymTab.noObj) {
				report_error("Nizovska promenljiva " + arrayVar.getArrayName() + " je vec deklarisana!", null);
			}
			else {
				
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
				
				// Prvo napravimo Struct cvor tipa niza
				Struct arrayTypeNode = new Struct(Struct.Array, arrayVar.getType().struct);
				
				// Pa onda TAJ TIP koristimo za dodavanje u tabelu simbola
				Obj arrayVarNode = ExtendedSymTab.insert(Obj.Var, arrayVar.getArrayName(), arrayTypeNode);
				report_info("Deklarisana nizovska promenljiva " + arrayVar.getArrayName(), arrayVar);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					arrayVarNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					arrayVarNode.setLevel(1);
				}
			}
		}

	}
	
	
	/** Ova visit metoda poziva se kada se obradjuju ostale nizovske promenljive, nakon zareza. */
	public void visit(MultipleArrayVarDecl moreArrayVariables) {
		
		brojacPromenljivih++;
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imePromenljive = tekuciNamespace + "::" + moreArrayVariables.getAdditionalArrayVarName();
			
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(imePromenljive) != ExtendedSymTab.noObj) {
				report_error("Nizovska promenljiva " + imePromenljive + " je vec deklarisana!", null);
			}
			else {
								
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
									
				// Prvo napravimo Struct cvor tipa niza
				Struct arrayTypeNode = new Struct(Struct.Array, tipPromenljivihIKonstanti);
									
				// Pa onda TAJ TIP koristimo za dodavanje u tabelu simbola
				Obj arrayVarNode = ExtendedSymTab.insert(Obj.Var, imePromenljive, arrayTypeNode);
				report_info("Deklarisana nizovska promenljiva " + imePromenljive + "(nakon zareza)", moreArrayVariables);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					arrayVarNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					arrayVarNode.setLevel(1);
				}
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			// Provera da li je promenljiva sa tim imenom vec deklarisana (ako jeste, onda vec postoji u tabeli simbola, i to je greska)
			if(ExtendedSymTab.find(moreArrayVariables.getAdditionalArrayVarName()) != ExtendedSymTab.noObj) {
				report_error("Nizovska promenljiva " + moreArrayVariables.getAdditionalArrayVarName() + " je vec deklarisana!", null);
			}
			else {
					
				// Promenljiva ne postoji, pa sada dodajemo Obj cvor tipa Var u tabelu simbola
						
				// Prvo napravimo Struct cvor tipa niza
				Struct arrayTypeNode = new Struct(Struct.Array, tipPromenljivihIKonstanti);
						
				// Pa onda TAJ TIP koristimo za dodavanje u tabelu simbola
				Obj arrayVarNode = ExtendedSymTab.insert(Obj.Var, moreArrayVariables.getAdditionalArrayVarName(), arrayTypeNode);
				report_info("Deklarisana nizovska promenljiva " + moreArrayVariables.getAdditionalArrayVarName() + "(nakon zareza)", moreArrayVariables);
				
				
				// Postavljanje dosega promenljive
				if(dosegObradjivanePromenljive == GLOBALNA) {
					arrayVarNode.setLevel(0);
				}
				else if(dosegObradjivanePromenljive == LOKALNA) {
					arrayVarNode.setLevel(1);
				}
			}
		}

	}
	
	
	// ---------------------- Metode za deklaraciju promenljivih END ------------------------
	
	
	// ---------------------- Metode za deklaraciju konstanti START -------------------------
	
	/** Ova visit metoda poziva se kada se obradjuje prva numericka konstanta u naredbi. */
	public void visit(NumConstDecl numConst) {
		
		// Radi se po istom principu kao deklaracija prve promenljive
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + numConst.getNumConstName();
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
			
			else {

				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola

				Struct numConstType = numConst.getType().struct;
				
				if(numConstType == ExtendedSymTab.intType) {
					
					Obj numConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, numConstType);
					report_info("Deklarisana konstanta " + imeKonstante, numConst);
								
					numConstNode.setAdr(numConst.getNumValue());		// Postavi vrednost konstante u tabelu simbola
								
				}
				else {
					report_error("Greska na liniji " + numConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(numConst.getNumConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + numConst.getNumConstName() + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				Struct numConstType = numConst.getType().struct;
							
				if(numConstType == ExtendedSymTab.intType) {
					Obj numConstNode = ExtendedSymTab.insert(Obj.Con, numConst.getNumConstName(), numConstType);
					report_info("Deklarisana konstanta " + numConst.getNumConstName(), numConst);
								
					numConstNode.setAdr(numConst.getNumValue());		// Postavi vrednost konstante u tabelu simbola
								
				}
				else {
					report_error("Greska na liniji " + numConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
				
				
			}
			
		}
		
	}
	
	
	
	/** Visit metoda koju koristim kada navodim vise numerickih konstanti odvojenih zarezima, kako bih postavio tip int ukoliko su brojevi. */
	public void visit(NumConstValue numConstValue) {
		
		if(numConstValue.getNumConstValue() instanceof Integer) {
			numConstValue.struct = ExtendedSymTab.intType;
			return;
		}
		
		numConstValue.struct = ExtendedSymTab.noType;
	}
	
	
	/** Ova visit metoda poziva se kada se obradjuju preostale numericke konstante u naredbi.
	 * Npr. za const int a = 10, b = 15, c = 20; Ova visit metoda obradjuje konstante b i c. */
	public void visit(MultipleNumConst moreNumConsts) {
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + moreNumConsts.getNumConstName();
			
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
			
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				// Ovde sam to radio na drugaciji nacin nego kod "prve" konstante u naredbi
				if(moreNumConsts.getNumConstValue().struct == ExtendedSymTab.intType) {
					Obj anotherNumConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, tipPromenljivihIKonstanti);
					report_info("Deklarisana konstanta " + imeKonstante + "(navedena nakon zareza)", moreNumConsts);
					
					anotherNumConstNode.setAdr(moreNumConsts.getNumConstValue().getNumConstValue());
				}
				else {
					report_error("Greska na liniji " + moreNumConsts.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
		
			}
			
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(moreNumConsts.getNumConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + moreNumConsts.getNumConstName() + " je vec deklarisana!", null);
			}
			
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				// Ovde sam to radio na drugaciji nacin nego kod "prve" konstante u naredbi
				if(moreNumConsts.getNumConstValue().struct == ExtendedSymTab.intType) {
					Obj anotherNumConstNode = ExtendedSymTab.insert(Obj.Con, moreNumConsts.getNumConstName(), tipPromenljivihIKonstanti);
					report_info("Deklarisana konstanta " + moreNumConsts.getNumConstName() + "(navedena nakon zareza)", moreNumConsts);
					
					anotherNumConstNode.setAdr(moreNumConsts.getNumConstValue().getNumConstValue());
				}
				else {
					report_error("Greska na liniji " + moreNumConsts.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
		
			}
		}
		
	}
	
	
	// Za dalje konstante (char i bool tipa) se radi na identican nacin kao ove dve metode iznad
	
	/** Ova visit metoda poziva se kada se obradjuje prva char konstanta u naredbi. */
	public void visit(CharConstDecl charConst) {
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + charConst.getCharConstName();
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
			
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				Struct charConstType = charConst.getType().struct;
				
				if(charConstType == ExtendedSymTab.charType) {
					Obj charConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, charConstType);
					report_info("Deklarisana konstanta " + imeKonstante, charConst);
					
					charConstNode.setAdr(charConst.getCharValue());
				}
				else {
					report_error("Greska na liniji " + charConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
				
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(charConst.getCharConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + charConst.getCharConstName() + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				Struct charConstType = charConst.getType().struct;
				
				if(charConstType == ExtendedSymTab.charType) {
					Obj charConstNode = ExtendedSymTab.insert(Obj.Con, charConst.getCharConstName(), charConstType);
					report_info("Deklarisana konstanta " + charConst.getCharConstName(), charConst);
					
					charConstNode.setAdr(charConst.getCharValue());
				}
				else {
					report_error("Greska na liniji " + charConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
				
			}
		}
		
	}
	
	
	/** Ova visit metoda poziva se kada se obradjuju preostale numericke konstante u naredbi.
	 * Npr. za const char a = 'a', b = 'b', c = 'c'; Ova visit metoda obradjuje konstante b i c. */
	public void visit(MultipleCharConst moreCharConsts) {
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + moreCharConsts.getCharConstName();
			
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				Obj anotherCharConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, tipPromenljivihIKonstanti);
				report_info("Deklarisana konstanta " + imeKonstante + "(navedena nakon zareza)", moreCharConsts);
				
				anotherCharConstNode.setAdr(moreCharConsts.getC2());
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(moreCharConsts.getCharConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + moreCharConsts.getCharConstName() + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				Obj charConstNode = ExtendedSymTab.insert(Obj.Con, moreCharConsts.getCharConstName(), tipPromenljivihIKonstanti);
				report_info("Deklarisana konstanta " + moreCharConsts.getCharConstName() + "(navedena nakon zareza)", moreCharConsts);
				
				charConstNode.setAdr(moreCharConsts.getC2());
			}
			
		}

	}
	
	
	/** Ova visit metoda poziva se kada se obradjuje prva bool konstanta u naredbi. */
	public void visit(BoolConstDecl boolConst) {
		
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + boolConst.getBoolConstName();
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
						
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
							
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				Struct boolConstType = boolConst.getType().struct;
							
				if(boolConstType == ExtendedSymTab.boolType) {
								
					Obj boolConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, boolConstType);
					report_info("Deklarisana konstanta " + imeKonstante, boolConst);
								
					if(boolConst.getBoolValue().equals(true)) {
						boolConstNode.setAdr(1);
					}
					else {
						boolConstNode.setAdr(0);
					}
								
				}
				else {
					report_error("Greska na liniji " + boolConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
							
			}
			
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			
			// Provera da li je ta konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(boolConst.getBoolConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + boolConst.getBoolConstName() + " je vec deklarisana!", null);
			}
			
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				
				// Provera da li je Type ekvivalentan postojecem numerickom tipu iz tabele simbola
				Struct boolConstType = boolConst.getType().struct;
				
				if(boolConstType == ExtendedSymTab.boolType) {
					
					Obj boolConstNode = ExtendedSymTab.insert(Obj.Con, boolConst.getBoolConstName(), boolConstType);
					report_info("Deklarisana konstanta " + boolConst.getBoolConstName(), boolConst);
					
					if(boolConst.getBoolValue().equals(true)) {
						boolConstNode.setAdr(1);
					}
					else {
						boolConstNode.setAdr(0);
					}
					
				}
				else {
					report_error("Greska na liniji " + boolConst.getLine() + ": Tip konstante se ne slaze sa dodeljenom vrednoscu!", null);
				}
				
			}
		}

	}
	
	
	/** Ova visit metoda poziva se kada se obradjuju preostale numericke konstante u naredbi.
	 * Npr. za const bool a = true, b = true, c = false; Ova visit metoda obradjuje konstante b i c. */
	public void visit(MultipleBoolConst moreBoolConsts) {
		
		// Ukoliko POSTOJI namespace
		if(!tekuciNamespace.equals("")) {
			
			String imeKonstante = tekuciNamespace + "::" + moreBoolConsts.getBoolConstName();
			
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(imeKonstante) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + imeKonstante + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				Obj anotherBoolConstNode = ExtendedSymTab.insert(Obj.Con, imeKonstante, tipPromenljivihIKonstanti);
				report_info("Deklarisana konstanta " + imeKonstante + "(navedena nakon zareza)", moreBoolConsts);
				
				if(moreBoolConsts.getB2().equals(true)) {
					anotherBoolConstNode.setAdr(1);
				}
				else {
					anotherBoolConstNode.setAdr(0);
				}
			}
		}
		
		// Ukoliko NE POSTOJI namespace
		else {
			
			// Provera da li je konstanta vec deklarisana (ako jeste, onda vec postoji u tabeli simbola)
			if(ExtendedSymTab.find(moreBoolConsts.getBoolConstName()) != ExtendedSymTab.noObj) {
				report_error("Konstanta " + moreBoolConsts.getBoolConstName() + " je vec deklarisana!", null);
			}
			else {
				// Konstanta nije deklarisana - dodajemo je prvi put u tabelu simbola
				Obj boolConstNode = ExtendedSymTab.insert(Obj.Con, moreBoolConsts.getBoolConstName(), tipPromenljivihIKonstanti);
				report_info("Deklarisana konstanta " + moreBoolConsts.getBoolConstName() + "(navedena nakon zareza)", moreBoolConsts);
				
				if(moreBoolConsts.getB2().equals(true)) {
					boolConstNode.setAdr(1);
				}
				else {
					boolConstNode.setAdr(0);
				}
			}
		}
		
	}
	
	
	// ---------------------- Metode za deklaraciju konstanti END ---------------------------
	
	
	// ---------------------- Deklaracija metode START ----------------------
	
	// Kada otvoris scope za metodu, ti u njega dodajes deklarisana imena. Kada zavrsis deklaraciju metode (MethodDecl smenu), tada je potrebno
	// da se sva nastala imena ulancaju u locals polje Obj cvora metode.
	// Zato moras da imas mogucnost da u MethodDecl smeni nekako dodjes do Obj cvora metode, kako bi mogao da ulancas imena.
	
	
	/** Visit metoda za obradu TIP + NAZIV metode (sa povratnom vrednoscu). */
	public void visit(MethodTypeName methodTypeName) {
		
		// Dodas metodu u tabelu simbola i sacuvas njen Obj cvor
		trenutnoObradjivanaMetoda = ExtendedSymTab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getType().struct);
		
		
		// Postavis obj polje na Obj cvor te metode, da bi to kasnije mogao da izvuces ; I otvoris opseg (opseg otvaras pre nego sto krenes argumente da obradis, a to radis u nekoj drugoj visit metodi)
		methodTypeName.obj = trenutnoObradjivanaMetoda;
		ExtendedSymTab.openScope();
		
		report_info("Obradjuje se metoda " + methodTypeName.getMethodName(), methodTypeName);
		
		
		dosegObradjivanePromenljive = LOKALNA;	// Posto se obradjuje metoda, promenljive unutar nje su lokalne
	}
	
	
	/** Visit metoda za obradu TIP + NAZIV metode tipa void */
	public void visit(MethodVoidName methodVoidName) {
		
		// Efektivno se radi ista stvar kao za metodu koja ima povratnu vrednost

		// Dodas metodu u tabelu simbola i sacuvas njen Obj cvor
		// Tip povratne vrednosti je void, pa se koristi None
		Struct voidType = new Struct(Struct.None);
		trenutnoObradjivanaMetoda = ExtendedSymTab.insert(Obj.Meth, methodVoidName.getMethodName(), voidType);
		
		// Postavis obj polje na Obj cvor te metode, da bi to kasnije mogao da izvuces ; I otvoris opseg (opseg otvaras pre nego sto krenes argumente da obradis, a to radis u nekoj drugoj visit metodi)
		methodVoidName.obj = trenutnoObradjivanaMetoda;
		ExtendedSymTab.openScope();
		
		report_info("Obradjuje se void metoda " + methodVoidName.getMethodName(), methodVoidName);
		
		
		dosegObradjivanePromenljive = LOKALNA;	// Posto se obradjuje metoda, promenljive unutar nje su lokalne
	}
	
	
	/** Visit metoda za obradu metode bez parametara (sa povratnom vrednoscu). Ova visit metoda predstavlja smenu koja obradjuje
	 * celu deklaraciju metode izgleda npr.   retType fun() {...}, gde je retType neki povratni tip. */
	public void visit(RetTypeMethodNoParam retTypeMethodNoParam) {
		
		// Proveri, ukoliko povratni tip nije void, da li postoji return naredba
		if(!postojiReturnNaredba && trenutnoObradjivanaMetoda.getType() != ExtendedSymTab.noType) {
			report_error("Semanticka greska na liniji " + retTypeMethodNoParam.getLine() + ": metoda " + trenutnoObradjivanaMetoda.getName() + " nema return naredbu!", null);
		}
		
		
		// Ulancavamo sva imena iz trenutnog opsega u locals polje za Obj cvor koji je prosledjen kao argument
		// Tj. iz trenutnog opsega ulancavamo sva imena u locals polje Obj cvora te metode, zatim zatvaramo opseg i potom, posto se vise nijedna metoda ne obradjuje, postavljamo na null
		
		ExtendedSymTab.chainLocalSymbols(trenutnoObradjivanaMetoda);
		ExtendedSymTab.closeScope();
		
		trenutnoObradjivanaMetoda = null;
		postojiReturnNaredba = false;
		
		dosegObradjivanePromenljive = 0;	// Resetujem pomocnu promenljivu jer sam zavrsio sa deklaracijom metoda
	}
	
	
	/** Visit metoda za metodu npr.  void method() {...} */
	public void visit(VoidMethodNoParam voidMethNoParam) {
		
		// Efektivno, mislim da treba da se radi isto kao i za obicnu metodu bez parametara
		
		ExtendedSymTab.chainLocalSymbols(trenutnoObradjivanaMetoda);
		ExtendedSymTab.closeScope();
		
		trenutnoObradjivanaMetoda = null;
		dosegObradjivanePromenljive = 0;	// Resetujem pomocnu promenljivu jer sam zavrsio sa deklaracijom metoda
	}
	
	
	// ---------------------- Deklaracija metode END ----------------------
	
	
	// ---------------------- Designator START ----------------------------
	
	
	public void visit(RegularDesignatorName designatorName) {
		
		// Ovo je odvojeno u zasebnu smenu, kako bi se odmah pri parsiranju naziva Designatora izvrsila ova metoda, gde bismo proverili da li je ovo
		// obicna ili nizovska promenljiva, i onda tu informaciju cuvamo
		
		// Da bi se neko IME koristilo, mora da postoji u tabeli simbola. Pokusamo da ga pronadjemo tamo. Ili ce nam vratiti njegov Obj cvor, ili ce vratiti noObj ako on ne postoji.
		Obj designatorNode = ExtendedSymTab.find(designatorName.getDesignatorName());
		
		// Ako je noObj onda ne postoji i to je greska koju moramo prijaviti.
		if(designatorNode == ExtendedSymTab.noObj) {
			report_error("GRESKA na liniji " + designatorName.getLine() + "! Ime "  + designatorName.getDesignatorName() + " nije deklarisano!", null);
			designatorName.obj = ExtendedSymTab.noObj;
		}
		
		// Inace postoji u tabeli simbola; za DesignatorName neterminal cuvamo ovaj Obj cvor, kako bismo kasnije u metodi za Designator mogli da vidimo sta dodeljujemo
		designatorName.obj = designatorNode;
		
		//novidesignatorJeNiz = designatorNode;		// Ovo cuvam i ovde da bih u onoj [Expr] smeni mogao da dohvatim naziv ovog designatora

		
		
		// Hoces da se u listu designatora doda samo ako je taj designator zapravo NIZ
		// Jer, ti hoces da ti elementi tog niza (ciji su obj cvorovi tipa Obj.Elem) budu ONOG TIPA kao i sam niz, a ne tipa podatka koji se koristi za indeksiranje tog niza
		// A ukoliko se za indeksiranje koristi neki Designator, onda je taj designator tipa int, a niz moze biti i char ili bool, i onda bi element niza uzeo tip tog int designatora a ne samog ocekivanog tipa tog niza
		// I onda hoces da ti se u listu designatora (koja je zapravo lista onih designatora koji JESU NIZ) doda ISKLJUCIVO ako je taj designator tipa niza, dakle ako je njegov Tip takav da je Kind==Array
				
		if(designatorNode.getType().getKind() == Struct.Array) {
			listaDesignatoraNiza.add(designatorNode);
		}
	}
	
	
	/** Visit metoda za obican designator. Ovaj designator predstavlja obicno pojavljivanje imena promenljive ili neke metode u nekoj naredbi. */
	public void visit(RegularDesignator designator) {
		
		// DesignatorName u sebi ima Obj cvor koji predstavlja cvor ubacen u tabelu simbola
		// Taj Obj cvor moze biti prostog tipa, za obicnu promenljivu, ili Array tipa za nizovsku promenljivu
		// Medjutim, ako je nizovskog tipa, onda to moramo da promenimo u Obj.Elem, pa se to ovde ispituje
		
		
		int startingDesignatorKind = designator.getRegularDesignatorName().obj.getType().getKind();		// Da li je Array, ili neki prosti tip (Int, Char, Bool)
		
		if(designator.getMultipleDesignatorExpansion() instanceof NoMultipleDesignatorExpansion) {
			// U tom slucaju se designator pojavljuje bez [Expr], tako da u tom slucaju nemamo formiran Obj.Elem tip cvora
			// I tada treba da koristimo onaj Obj cvor koji postoji sacuvan u tabeli simbola
			
			designator.obj = designator.getRegularDesignatorName().obj;
		}
		else {
			// Znaci da je tipa MultipleDesignatorExpansionArray pa tada koristimo njegov obj cvor koji je tipa Obj.Elem
			designator.obj = designator.getMultipleDesignatorExpansion().obj;
		}
		
		
		// Dodatna provera za MJ specifikaciju
		// Ukoliko je niz, onda Designator mora biti tipa niza
		if(designatorJeNiz) {
			
			if(startingDesignatorKind != Struct.Array) {
				report_error("Greska: Designator mora biti niz!", null);
			}
			else {
				designatorJeNiz = false;		// Ugasim ovo jer sam zavrsio sa obradom
			}
		}
	}
	
	
	public void visit(MultipleDesignatorExpansionArray exprForArrayDesignator) {
		
		Struct exprStructNode = exprForArrayDesignator.getExpr().struct;
		
		// Expr mora biti tipa int
		if(exprStructNode != ExtendedSymTab.intType) {
			report_error("Greska: Izraz u zagradama [] mora biti tipa int!", null);
		}
		else {
			// Expr jeste int, to je u redu, samo jos postavim ovaj flag da bih znao da se radi o nizu
			designatorJeNiz = true;
			
			
			// Ovo je cvor koji predstavlja element tog niza, a njega uzimam kao poslednji designator koji je dodat u listu
			if(!listaDesignatoraNiza.isEmpty()) {
				
				novidesignatorJeNiz = listaDesignatoraNiza.removeLast();
				
				
				// Za podatak u []: Ukoliko je FactorNumConst onda se uzima getType().getElemType; Inace uzima se getType()
				Obj arrayElementNode;
				if(novidesignatorJeNiz.getType().getElemType() == null) {
					arrayElementNode = new Obj(Obj.Elem, novidesignatorJeNiz.getName(), novidesignatorJeNiz.getType());
				}
				else {
					arrayElementNode = new Obj(Obj.Elem, novidesignatorJeNiz.getName(), novidesignatorJeNiz.getType().getElemType());
				}
				
				
				
				exprForArrayDesignator.obj = arrayElementNode;
				
				
				novidesignatorJeNiz = null;		// Posto sam obradio ovaj designator, ovde ga ponistim
			}
			
		}
		
	}
	
	
	/** Visit metoda koja samo parsira puno ime designatora i cuva ga u neterminalu. */
	public void visit(ScopedDesignatorName fullDesignatorName) {
		
		String namespaceName = fullDesignatorName.getNamespaceName();
		String designatorName = fullDesignatorName.getDesignatorName();
		
		punoScopedDesignatorIme = namespaceName + "::" + designatorName;
		
		
		// Da bi se neko IME koristilo, mora da postoji u tabeli simbola. Pokusamo da ga pronadjemo tamo. Ili ce nam vratiti njegov Obj cvor, ili ce vratiti noObj ako on ne postoji.
		Obj designatorNode = ExtendedSymTab.find(punoScopedDesignatorIme);
		
		// Ako je noObj onda ne postoji i to je greska koju moramo prijaviti.
		if(designatorNode == ExtendedSymTab.noObj) {
			report_error("GRESKA na liniji " + fullDesignatorName.getLine() + "! Ime "  + punoScopedDesignatorIme + " nije deklarisano!", null);
		}
		
		// Inace postoji u tabeli simbola; za Designator neterminal cuvamo ovaj Obj cvor, kako bismo kasnije u potrebnim proverama mogli da ga iskoristimo.
		fullDesignatorName.obj = designatorNode;
		
		//novidesignatorJeNiz = designatorNode;		// Ovo cuvam i ovde da bih u onoj [Expr] smeni mogao da dohvatim naziv ovog designatora
		
		
		// Hoces da se u listu designatora doda samo ako je taj designator zapravo NIZ
		// Jer, ti hoces da ti elementi tog niza (ciji su obj cvorovi tipa Obj.Elem) budu ONOG TIPA kao i sam niz, a ne tipa podatka koji se koristi za indeksiranje tog niza
		// A ukoliko se za indeksiranje koristi neki Designator, onda je taj designator tipa int, a niz moze biti i char ili bool, i onda bi element niza uzeo tip tog int designatora a ne samog ocekivanog tipa tog niza
		// I onda hoces da ti se u listu designatora (koja je zapravo lista onih designatora koji JESU NIZ) doda ISKLJUCIVO ako je taj designator tipa niza, dakle ako je njegov Tip takav da je Kind==Array
		
		if(designatorNode.getType().getKind() == Struct.Array) {
			listaDesignatoraNiza.add(designatorNode);
		}
			
	}
	
	
	/** Visit metoda za designator iz namespace-a. Ovaj designator predstavlja obicno pojavljivanje imena promenljive ili neke metode u nekoj naredbi. */
	public void visit(ScopedDesignator scopedDesignator) {
		
		// ScopedDesignatorName u sebi ima Obj cvor koji predstavlja cvor ubacen u tabelu simbola
		// Taj Obj cvor moze biti prostog tipa, za obicnu promenljivu, ili Array tipa za nizovsku promenljivu
		// Medjutim, ako je nizovskog tipa, onda to moramo da promenimo u Obj.Elem, pa se to ovde ispituje
				
		int startingDesignatorKind = scopedDesignator.getScopedDesignatorName().obj.getType().getKind();		// Da li je Array, ili neki prosti tip (Int, Char, Bool)		
		
		if(scopedDesignator.getMultipleDesignatorExpansion() instanceof NoMultipleDesignatorExpansion) {
			// U tom slucaju se designator pojavljuje bez [Expr], tako da u tom slucaju nemamo formiran Obj.Elem tip cvora
			// I tada treba da koristimo onaj Obj cvor koji postoji sacuvan u tabeli simbola
			
			scopedDesignator.obj = scopedDesignator.getScopedDesignatorName().obj;
		}
		else {
			// Znaci da je tipa MultipleDesignatorExpansionArray pa tada koristimo njegov obj cvor koji je tipa Obj.Elem
			scopedDesignator.obj = scopedDesignator.getMultipleDesignatorExpansion().obj;
		}
				
				
		// Dodatna provera za MJ specifikaciju
		// Ukoliko je niz, onda Designator mora biti tipa niza
		if(designatorJeNiz) {
						
			if(startingDesignatorKind != Struct.Array) {
				report_error("Greska: Designator mora biti niz!", null);
				}
			else {
				designatorJeNiz = false;		// Ugasim ovo jer sam zavrsio sa obradom
			}
		}
		
	}
	
	
	// ---------------------- Designator END ------------------------------
	
	
	// ---------------------- Factor START --------------------------------
	
	
	/** Visit metoda koja se poziva kada se u kodu (u nekoj naredbi) pojavi obican broj. */
	public void visit(FactorNumConst numberInCode) {
		numberInCode.struct = ExtendedSymTab.intType;			// Broj je tipa int
	}
	
	
	/** Visit metoda koja se poziva kada se u kodu (u nekoj naredbi) pojavi karakter. */
	public void visit(FactorCharConst characterInCode) {
		characterInCode.struct = ExtendedSymTab.charType;		// Karakter je tipa char
	}
	
	
	/** Visit metoda koja se poziva kada se u kodu (u nekoj naredbi) pojavi true ili false vrednost. */
	public void visit(FactorBoolConst booleanInCode) {
		booleanInCode.struct = ExtendedSymTab.boolType;
	}
	
	
	public void visit(FactorVariable regularVariable) {
		regularVariable.struct = regularVariable.getDesignator().obj.getType();
	}
	
	
	/** Visit metoda za faktor koji se ponasa kao poziv metode, gde ta metoda ne prima nikakve argumente. Npr. ako postoji funkcija ciji je potpis: int func(int a);
	 * tada bi se ova visit metoda pozvala za npr. naredbu iz maina oblika  func(10); */
	public void visit(FactorFunctionNoParam funcCall) {
	
		// Dohvatimo Obj cvor tog Designator neterminala, jer on zapravo predstavlja naziv te funkcije, pa dohvatamo efektivno Obj cvor za funkciju
		Obj functionNode = funcCall.getDesignator().obj;
		
		
		// Da li je on tipu funkcija?
		// Designator moze biti ili promenljiva ili funkcija, pa u slucaju da je ovo promenljiva onda moramo da prijavimo gresku, jer se ne moze pozivati kao funkcija nesto sto nije funkcija
		if(functionNode.getKind() == Obj.Meth) {
			report_info("Pronadjen poziv funkcije " + functionNode.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = functionNode.getType();		// ovo getType dohvata tip neterminala type, dok getKind dohvata Kind objektnog cvora
		}
		else {
			// Inace, ovo ime nije funkcija nego obicna promenljiva - greska 
			report_error("GRESKA na liniji " + funcCall.getLine() + "! " + functionNode.getName() + " nije funkcija!", null);
			funcCall.struct = ExtendedSymTab.noType;
		}
		
	}
	
	
	public void visit(FactorParenthesisGrouping exprInParenthesis) {
		exprInParenthesis.struct = exprInParenthesis.getExpr().struct;
	}
	
	
	// ----------------------------- Faktori koji se stvaraju sa NEW -----------------------------
	
	
	public void visit(FactorObjectArray newArray) {
		
		// Provera prema MJ specifikaciji
		// Expr mora biti int
		if(newArray.getExpr().struct != ExtendedSymTab.intType) {
			report_error("Greska na liniji " + newArray.getLine() + ": expr mora biti tipa int!", null);
		}
		else {
			// Ovaj niz dodat je u tabelu simbola prilikom deklaracije, ovde je samo potrebno da se postavi njegov tip na neterminalu
			
			Struct arrayNode = new Struct(Struct.Array, newArray.getType().struct);
			newArray.struct = arrayNode;
			
			obradaNewNiz = true;
		}
		
	}
	
	
	public void visit(FactorObjectNoParam newObject) {
		
		// Nisam siguran sta ovde treba da radim
		
		// Uradi proveru

		// Je l ovo dovoljno
		newObject.struct = newObject.getType().struct;
		
	}
	
	
	// ---------------------- Factor END ----------------------------------
	
	
	// ---------------------- Term START ----------------------------------
	
	// Term se poziva na faktor i na visestruko "nadovezivanje" faktora preko mulop operacije
	// Zato prvo obradjujem MultipleMulop
	
	public void visit(MultipleMulop multipleMulop) {
		
		Struct factorType = multipleMulop.getFactor().struct;
		
		
		// Faktor pri operaciji mulop sme da bude samo int tip - ako nije int to je greska!
		if(!factorType.compatibleWith(ExtendedSymTab.intType)) {
			mulopFaktorTip = ExtendedSymTab.noType;
			report_error("Greska na liniji " + multipleMulop.getLine() + ": Faktor nije tipa INT i ne moze se koristiti pri mulop operaciji!", null);
		}
		else {
			mulopFaktorTip = ExtendedSymTab.intType;
		}
	}
	
	
	public void visit(Term term) {
		
		// Term je neterminal koji koristimo "vise" (upper) u stablu, tako da nam treba da mu ovde podesimo tip
		// Term se zastoji od Faktora, tako da tip terma zapravo odgovara tipu faktora
		
		// Ako je ovo null to znaci da nisam imao MultipleMulop smene, pa se koristi samo Factor iz Term produkcije
		// U tom slucaju samo gledam njegov tip
		if(mulopFaktorTip == null) {
			term.struct = term.getFactor().struct;
		}
		
		// Inace, ako postoji vise faktora, onda moram da proverim da li su svi oni tipa int
		// Jesu tipa int, znaci da samo postavis taj int tip na term neterminal
		else if(term.getFactor().struct.compatibleWith(ExtendedSymTab.intType) && mulopFaktorTip.compatibleWith(ExtendedSymTab.intType)) {
			term.struct = term.getFactor().struct;
		}
		
		// Nisu tipa INT, samo postavi noType na term neterminal
		else {
			term.struct = ExtendedSymTab.noType;
		}
		
		
		// Nakon sto si ovo obradio, zavrsavanjem Term produkcije, obradio si tu jednu liniju
		// samim tim ti ova pomocna promenljiva mulopFaktorTip vise nije potrebna pa je resetujes na null
		mulopFaktorTip = null;
	}
	
	// ---------------------- Term END ------------------------------------
	
	// ---------------------- Izrazi (Expr) START -------------------------
	
	
	public void visit(MultipleAddop multipleAddop) {
		
		Struct termType = multipleAddop.getTerm().struct;
		
		// Taj term u rekurziji mora biti tipa int, jer ne mozes da sabiras nesto sto nije int, ako nije onda je greska
		if(!termType.compatibleWith(ExtendedSymTab.intType)) {
			addopTermTip = ExtendedSymTab.noType;
			report_error("Greska: Term nije tipa INT i ne moze se koristiti u addop operaciji!", null);
		}
		else {
			addopTermTip = ExtendedSymTab.intType;
		}
	}
	
	
	public void visit(PositivePrefixTerm positiveTerm) {
		
		Struct positiveTermTip = positiveTerm.getTerm().struct;
		
		
		// Ako je ovo null onda postoji samo jedan term pa se uzima njegov tip
		if(addopTermTip == null) {
			positiveTerm.struct = positiveTerm.getTerm().struct;
		}
		
		// Inace, ima vise term-ova pa moras da proveris da su svi oni tipa int
		// Ako jesu, postavi tip neterminala na int
		
		else if(positiveTermTip.compatibleWith(ExtendedSymTab.intType) && addopTermTip.compatibleWith(ExtendedSymTab.intType)
				&& positiveTermTip.compatibleWith(addopTermTip)){		// Ovaj red inicijalno nisam imao, dodao sam zbog MJ specifikacije
			positiveTerm.struct = positiveTerm.getTerm().struct;
		}
		
		// Inace, ako nisu, postavi tip neterminala na noType
		else {
			positiveTerm.struct = ExtendedSymTab.noType;
			report_error("Greska: Term nije tipa INT i ne moze se koristiti u addop operaciji!", null);
		}
		
		
		// Svakako si zavrsio tu obradu tako da resetuj ovu pomocnu promenljivu na null
		addopTermTip = null;
	}
	
	
	/** Negirani term, koji se koristi posle u NegativePrefixTerm produkciji. Izdvojen je ovako zbog faze generisanja koda, radi lakse obrade. */
	public void visit(NegatedTerm negatedTerm) {
		// Samo prosledim njegov Struct cvor nagore
		negatedTerm.struct = negatedTerm.getTerm().struct;
	}
	
	
	public void visit(NegativePrefixTerm negativeTerm) {
		
		// Radi se identicno kao za PositivePrefixTerm
		
		// Ako je ovo null onda postoji samo jedan term pa se uzima njegov tip
		if(addopTermTip == null) {
			negativeTerm.struct = negativeTerm.getNegatedTerm().struct;
		}
				
		// Inace, ima vise term-ova pa moras da proveris da su svi oni tipa int
		// Ako jesu, postavi tip neterminala na int
		else if(negativeTerm.getNegatedTerm().struct.compatibleWith(ExtendedSymTab.intType) && addopTermTip.compatibleWith(ExtendedSymTab.intType)) {
			negativeTerm.struct = negativeTerm.getNegatedTerm().struct;
		}
				
		// Inace, ako nisu, postavi tip neterminala na noType
		else {
			negativeTerm.struct = ExtendedSymTab.noType;
			report_error("Greska: Term nije tipa INT i ne moze se koristiti u addop operaciji!", null);
		}
			
				
		// Svakako si zavrsio tu obradu tako da resetuj ovu pomocnu promenljivu na null
		addopTermTip = null;
	}
	
	
	
	// ---------------------- Izrazi (Expr) END ---------------------------
	
	
	
	
	
	// ---------------------- Visit metode za naredbe (statement) START -----------------------------
	
	/** Visit metoda za return naredbu, gde se vraca neki izraz. */
	public void visit(ReturnStatement returnExpr) {
		
		postojiReturnNaredba = true;			// Posto se obradjuje return expr naredba, onda return postoji
		
		Struct tipMetode = trenutnoObradjivanaMetoda.getType();
		
		// Proveravamo da li se tip izraza koji se vraca u return naredbi poklapa sa ocekivanim tipom povratne vrednosti funkcije
		if(!tipMetode.compatibleWith(returnExpr.getExpr().struct)) {
			report_error("Greska na liniji " + returnExpr.getLine() + ": Tip izraza iz return naredbe se ne poklapa sa ocekivanim tipom povratne vrednosti funkcije!", null);
		}
	}
	
	
	/** Visit metoda za return naredbu, gde se ne vraca izraz. */
	public void visit(NoReturnStatement noReturnExpr) {
		
		postojiReturnNaredba = true;
		
		Struct tipMetode = trenutnoObradjivanaMetoda.getType();
		
		if(tipMetode.getKind() != Struct.None) {
			report_error("Greska na liniji " + noReturnExpr.getLine() + ": return; moze da se koristi samo za metode povratnog tipa void!", null);
		}
		
	}
	
	
	
	/** Visit metoda za naredbu dodele vrednosti. */
	public void visit(AssignmentStatement assignStatement) {
		
		// Designator je destination tj. mesto u memoriji na koje se upisuje nesto
		// Expr je source tj. to predstavlja sracunatu vrednost koja treba da se upise
		
		
		// Dodatna provera iz MJ specifikacije
		int vrstaDesignatora = assignStatement.getDesignator().obj.getKind();
				
		if(vrstaDesignatora != Obj.Var && vrstaDesignatora != Obj.Elem && vrstaDesignatora != Obj.Fld) {
			report_error("Greska! Designator nije promenljiva, element niza ni polje unutar objekta!", null);
		}
		
		else {
			
			Struct designatorType = assignStatement.getDesignator().obj.getType();
			Struct exprType = assignStatement.getExpr().struct;
			
			
			if(assignStatement.getDesignator().obj.getKind() == Obj.Elem ||
					assignStatement.getDesignator().obj.getType().getKind() == Struct.Array) {
				
				// Proverava se tip designatora koji je niz
				
				if(obradaNewNiz) {
					
					Struct designatorElemType = designatorType.getElemType();
					Struct arraySizeExprType = exprType.getElemType();
					
					obradaNewNiz = false;
					
					if(!arraySizeExprType.assignableTo(designatorElemType)) {
						report_error("Greska na liniji " + assignStatement.getLine() + ": nekompatibilni tipovi u dodeli vrednosti!", null);
					}
				}
				
				else {
					
					Struct designatorElemTypeForObjElem = designatorType;		// Zato sto ovo obradjuje slucaj kada je Obj.Elem, a tamo je vec postavljen tip na tip elementa niza
					
					if(!exprType.assignableTo(designatorElemTypeForObjElem)) {
						report_error("Greska na liniji " + assignStatement.getLine() + ": nekompatibilni tipovi u dodeli vrednosti!", null);
					}
				}
				
				
				
			}
			
			else {
				if(!exprType.assignableTo(designatorType)) {
					report_error("Greska na liniji " + assignStatement.getLine() + ": nekompatibilni tipovi u dodeli vrednosti!", null);
				}
			}
			
		}
		
		
	}
	
	
	/** Visit metoda koja se poziva za operaciju inkrmentiranja. */
	public void visit(IncrementStatement incrementStatement) {
		
		Obj designatorNode = incrementStatement.getDesignator().obj;
		
		// Provera prema MJ specifikaciji
		
		// Designator mora biti tipa int
		if(designatorNode.getType() != ExtendedSymTab.intType) {
			report_error("Greska na liniji " + incrementStatement.getLine() + "! Designator mora biti tipa int!", null);
		}
		
		
		// Designator mora biti promenljiva, element niza ili polje objekta
		if(designatorNode.getKind() != Obj.Var && designatorNode.getKind() != Obj.Elem && designatorNode.getKind() != Obj.Fld) {
			report_error("Greska na liniji " + incrementStatement.getLine() + "! Designator mora biti promenljiva, element niza ili polje objekta!", null);
		}
	}
	
	
	public void visit(DecrementStatement decrementStatement) {
		
		// Radi se identicna provera kao za incrementStatement
		
		Obj designatorNode = decrementStatement.getDesignator().obj;
		
		// Provera prema MJ specifikaciji
		
		// Designator mora biti tipa int
		if(designatorNode.getType() != ExtendedSymTab.intType) {
			report_error("Greska na liniji " + decrementStatement.getLine() + "! Designator mora biti tipa int!", null);
		}
		
		
		// Designator mora biti promenljiva, element niza ili polje objekta
		if(designatorNode.getKind() != Obj.Var && designatorNode.getKind() != Obj.Elem && designatorNode.getKind() != Obj.Fld) {
			report_error("Greska na liniji " + decrementStatement.getLine() + "! Designator mora biti promenljiva, element niza ili polje objekta!", null);
		}
	}
	
	
	// ---------------------- Visit metode za naredbe (statement) END -------------------------------
	
	
	
	
	
	
	
	// -------------------- Metode za print funkciju START ------------------------
	
	
	public void visit(PrintNoArgStatement printNoArgStatement) {
		brojacPrintBezArg++;
		log.info("Prepoznata print naredba");
		
		// Provera prema MJ specifikaciji
		
		// Expr mora biti tipa int, char ili bool
		Struct exprStructNode = printNoArgStatement.getExpr().struct;
		
		if(exprStructNode.getKind() == Struct.Array) {
			Struct arrayElementType = exprStructNode.getElemType();
			
			if(arrayElementType != ExtendedSymTab.intType && arrayElementType != ExtendedSymTab.charType && arrayElementType != ExtendedSymTab.boolType) {
				report_error("Greska na liniji " + printNoArgStatement.getLine() + ": Expr mora biti tipa int, char ili bool", null);
			}
		}
		else {
			// Expr nije tipa niza, pa se koristi obican pristup
			if(exprStructNode != ExtendedSymTab.intType && exprStructNode != ExtendedSymTab.charType && exprStructNode != ExtendedSymTab.boolType) {
				report_error("Greska na liniji " + printNoArgStatement.getLine() + ": Expr mora biti tipa int, char ili bool", null);
			}
		}
		
		
	}
	
	
	public void visit(PrintWithArgStatement printWithArg) {
		
		// Analogno kao i za print bez argumenta
		
		// Provera prema MJ specifikaciji
		
		// Expr mora biti tipa int, char ili bool
		Struct exprStructNode = printWithArg.getExpr().struct;
		if(exprStructNode != ExtendedSymTab.intType && exprStructNode != ExtendedSymTab.charType && exprStructNode != ExtendedSymTab.boolType) {
			report_error("Greska na liniji " + printWithArg.getLine() + ": Expr mora biti tipa int, char ili bool", printWithArg);
		}
		
	}
	
	
	// -------------------- Metode za print funkciju END ------------------------
	
	
	
	// -------------------- Metode za read funkciju START -----------------------
	
	
	public void visit(ReadStatement readStatement) {
		
		int designatorNodeKind = readStatement.getDesignator().obj.getKind();
		Struct designatorNodeType = readStatement.getDesignator().obj.getType();
		
		// Provera zahteva po MJ specifikaciji
		
		// Designator mora biti tipa int, char ili bool
		if(designatorNodeType != ExtendedSymTab.intType && designatorNodeType != ExtendedSymTab.charType
				&& designatorNodeType !=ExtendedSymTab.boolType) {
			report_error("Greska na liniji " + readStatement.getLine() + ": Designator mora biti tipa int, char ili bool!", null);
		}
		
		
		// Designator mora biti promenljiva, element niza ili polje objekta
		if(designatorNodeKind != Obj.Var && designatorNodeKind != Obj.Elem && designatorNodeKind != Obj.Fld) {
			report_error("Greska na liniji " + readStatement.getLine() + ": Designator mora biti promenljiva, element niza ili polje objekta!", null);
		}
	}
	
	
	// -------------------- Metode za read funkciju END -------------------------
	
	
	
	
	// -------------------- Metode za oporavak od greske START --------------------
	
	public void visit(SyntaxError syntaxError) {
		
	}
	
	// -------------------- Metode za oporavak od greske END --------------------
	
}

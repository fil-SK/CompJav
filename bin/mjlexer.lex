/* -------------------------------- Import section START -------------------------------- */

package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;


/* -------------------------------- Import section END -------------------------------- */


%%


/* -------------------------------- Directives section START -------------------------------- */


%{
	
	// ukljucivanje informacije o poziciji tokena
	// Funkcija pravi token na osnovu njegovog tipa
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	// Funkcija pravi token na osnovu njegovog tipa i njegove vrednosti
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%} 


%cup
%line
%column


%xstate COMMENT


%eofval{
	return new_symbol(sym.EOF);
%eofval}


/* -------------------------------- Directives section END -------------------------------- */


%%


/* -------------------------------- Regular expressions START -------------------------------- */


" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }


// Kada se odgovarajuci regex prepozna, akcija je stvaranje odgovarajuceg tokena
// Prvi arg. je promenljiva klase sym, a vrednost koja se dobija je vrednost procitane lekseme, koja se dobija pozivom metode yytext
"program"				{ return new_symbol(sym.PROGRAM, yytext()); }
"break"					{ return new_symbol(sym.BREAK, yytext()); }
"class"					{ return new_symbol(sym.CLASS, yytext()); }
"else"					{ return new_symbol(sym.ELSE, yytext()); }
"const"					{ return new_symbol(sym.CONST, yytext()); }
"if"					{ return new_symbol(sym.IF, yytext()); }
"new"					{ return new_symbol(sym.NEW, yytext()); }
"print"					{ return new_symbol(sym.PRINT, yytext()); }
"read"					{ return new_symbol(sym.READ, yytext()); }
"return"				{ return new_symbol(sym.RETURN, yytext()); }
"void"					{ return new_symbol(sym.VOID, yytext()); }
"extends"				{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"				{ return new_symbol(sym.CONTINUE, yytext()); }
"this"					{ return new_symbol(sym.THIS, yytext()); }
"for"					{ return new_symbol(sym.FOR, yytext()); }
"static"				{ return new_symbol(sym.STATIC, yytext()); }
"namespace"				{ return new_symbol(sym.NAMESPACE, yytext()); }

"+"						{ return new_symbol(sym.PLUS, yytext()); }					
"-"						{ return new_symbol(sym.MINUS, yytext()); }
"*"						{ return new_symbol(sym.MULTIPLY, yytext()); }
"/"						{ return new_symbol(sym.DIVIDE, yytext()); }

"%"						{ return new_symbol(sym.MODULUS, yytext()); }

"=="					{ return new_symbol(sym.IS_EQUAL, yytext()); }
"!="					{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">"						{ return new_symbol(sym.GREATER_THAN, yytext()); }
">="					{ return new_symbol(sym.GREATER_OR_EQUAL, yytext()); }
"<"						{ return new_symbol(sym.LOWER_THAN, yytext()); }
"<="					{ return new_symbol(sym.LOWER_OR_EQUAL, yytext()); }

"&&"					{ return new_symbol(sym.BITWISE_AND, yytext()); }
"||"					{ return new_symbol(sym.BITWISE_OR, yytext()); }

"="						{ return new_symbol(sym.ASSIGN_OP, yytext()); }

"++"					{ return new_symbol(sym.INCREMENT, yytext()); }
"--"					{ return new_symbol(sym.DECREMENT, yytext()); }

";"						{ return new_symbol(sym.SEMI_COLON, yytext()); }
":"						{ return new_symbol(sym.COLON, yytext()); }
"::"					{ return new_symbol(sym.SCOPE_OP, yytext()); }
","						{ return new_symbol(sym.COMMA, yytext()); }
"."						{ return new_symbol(sym.DOT, yytext()); }

"("						{ return new_symbol(sym.LEFT_PARENTHESIS, yytext()); }
")"						{ return new_symbol(sym.RIGHT_PARENTHESIS, yytext()); }
"["						{ return new_symbol(sym.LEFT_SQUARE_BRACKET, yytext()); }
"]"						{ return new_symbol(sym.RIGHT_SQUARE_BRACKET, yytext()); }
"{"						{ return new_symbol(sym.LEFT_CURLY_BRACKET, yytext()); }
"}"						{ return new_symbol(sym.RIGHT_CURLY_BRACKET, yytext()); }

"=>"					{ return new_symbol(sym.ARROW, yytext()); }



"//"					{ yybegin(COMMENT); }
<COMMENT> .				{ yybegin(COMMENT); }
<COMMENT> "\r\n"		{ yybegin(YYINITIAL); }



// Tacka handle-uje sve karaktere koji dosad nisu obradjeni
// Ident pocinje malim ili velikim slovom, nakon cega sledi proizvoljan broj malih ili velikih slova, ili donjih crta
[0-9]+						{ return new_symbol(sym.NUM_CONST, Integer.valueOf(yytext()) ); }
'.'							{ return new_symbol(sym.CHAR_CONST, yytext().charAt(1) ); }
"true"						{ return new_symbol(sym.BOOL_CONST, true); }
"false"						{ return new_symbol(sym.BOOL_CONST, false); }
([a-z]|[A-Z])[a-zA-Z0-9_]*	{ return new_symbol(sym.IDENT, yytext()); }



.						{ System.err.println("Leksicka greska (" + yytext() + ") na liniji " + (yyline + 1) + "." );}


/* -------------------------------- Regular expressions END -------------------------------- */
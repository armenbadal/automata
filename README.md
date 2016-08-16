# automata

Վերջավոր ավտոմատի լեզու և ինտերպրետատոր։


````
Program   = {(Automata|Recognize)}.
Automata  = 'automata' Ident '{' Alphabet States Initial Finals Commands '}'.
Alphabet  = 'alphabet' '=' '{' Symbol {',' Symbol} '}'.
States    = 'states' '=' '{' Ident {',' Ident} '}'.
Initial   = 'initial' '=' Ident.
Finals    = 'finals' '=' '{' Ident {',' Ident} '}'.
Commands  = 'commands' '=' '{' Command {',' Command} '}'. 
Command   = Ident ',' Symbol '->' Ident.
Recognize = 'recognize' String 'with' Ident. 
````
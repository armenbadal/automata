# automata

(Ծրագրավորման) լեզվի իրականացման ընթացքը։

1. Լեզվի քերականության սահմանում։
2. Տերմինալային և ոչ տերմինալային սիմվոլների առանձնացում։
3. Բառային (լեքսիկական) վերլուծիչի իրականացում տերմինալային սիմվոլների համար։
4. Շարահյուսական (սինտակտիկ) վերլուծիչի իրականացում։

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


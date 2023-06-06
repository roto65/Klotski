package solver;

public class KlotskiSolver {


}


/*
 * Idee per il risolutore / miglior mossa:
 * applico una BFS per cercare con quale combinazioni di mosse riesco ad arrivare alla fine e suggerisco la prima
 * salvo tutti i miei risultati (tutte le x mosse) su una collection di mongodb e nel caso l'utente chieda nuovamente
 * l'aiuto cerco prima se quella mossa è già stata calcolata
 * per velocizzare la ricerca su db al posto di fare una query complessa con una lista di oggetti converto lo stato
 * della board tramite bitset e poi long in un numero univoco (?) con la seguente convenzione:
 * - 00: bit per la cella vuota
 * - 01: bit per il blocco piccolo 1x1
 * - 10: bit per il blocco orizzontale
 * - 11: bit per il blocco verticale
 * gli ultimi 4 dei 36 bit rappresentano la posizione dell'angolo tl blocco grande usando la seguente convenzione:
 * vengono escluse le celle dell'ultima riga e dell'ultima colonna perché il blocco grande tl non può occuparle,
 * le rimanenti celle (3x4) vengono contate come se fossero un unico array tl -> br,
 * in questo modo si riescono a utilizzare solamente i 4 bit per indicizzare un blocco e non sono costretto ad
 * aggiungere un bit in più per tutti gli atri tipi, che altrimenti diventerebbero 5.
 *
 * Una volta completata la BFS, per cui sarà necessario utilizzare una Queue, sotto forma di LinkedList, è necessario
 * ricostruire l'albero della ricorsione tramite una Map, sotto forma di HashMap, in modo tale da legare a ogni stato
 * visitato lo stato precedente, quello da cui muovendo un solo blocco si arriva allo stato attuale.
 *
 * È anche necessario tenere traccia degli stati che già sono stati visitati per evitare di generare loop
 *
 * Durante la BFS potrebbe risultare scomodo l'utilizzo di una lista come ADT per mantenere le informazioni relative a
 * tutti i blocchi presenti sulla board in ogni dato momento, oltre che essere oneroso dal punto di vista della memoria
 * se devono essere generati molti stati diversi in pochissimo tempo.
 * Bisogna trovare un secondo metodo per rappresentare la posizione di tutti i blocchi più semplice ma dove si deve
 * comunque poter muovere i blocchi in modo abbastanza agile e veloce per non andare a gravare troppo sulla cpu.
 * Un'idea potrebbe essere rappresentare tutti i blocchi tramite una stringa (intesa come array di char) in cui ogni
 * carattere rappresenta una cella del campo
 */

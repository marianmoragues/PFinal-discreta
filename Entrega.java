package pfinaldiscreta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;


/*
 * Aquesta entrega consisteix en implementar tots els mètodes anomenats "exerciciX". Ara mateix la
 * seva implementació consisteix en llançar `UnsupportedOperationException`, ho heu de canviar així
 * com els aneu fent.
 *
 * Criteris d'avaluació:
 *
 * - Si el codi no compila tendreu un 0.
 *
 * - Les úniques modificacions que podeu fer al codi són:
 *    + Afegir un mètode (dins el tema que el necessiteu)
 *    + Afegir proves a un mètode "tests()"
 *    + Òbviament, implementar els mètodes que heu d'implementar ("exerciciX")
 *   Si feu una modificació que no sigui d'aquesta llista, tendreu un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implementats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Per exemple:
 *    + IMPORTANT: Aquesta entrega està codificada amb UTF-8 i finals de línia LF.
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut. Igualment per while si no és necessari.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau els noms i cognoms de tots els membres del grup a l'array `Entrega.NOMS` que
 * està definit a la línia 53.
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat. Si no podeu visualitzar bé algun enunciat, assegurau-vos de que el vostre editor
 * de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {

    static final String[] NOMS = {"Maria Antònia Moragues Seguí",
        "Irene Carbonell Aguilera",
        "Milena Godoy Char"};

    /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
     */
    static class Tema1 {

        /*
     * Determinau si l'expressió és una tautologia o no:
     *
     * (((vars[0] ops[0] vars[1]) ops[1] vars[2]) ops[2] vars[3]) ...
     *
     * Aquí, vars.length == ops.length+1, i cap dels dos arrays és buid. Podeu suposar que els
     * identificadors de les variables van de 0 a N-1, i tenim N variables diferents (mai més de 20
     * variables).
     *
     * Cada ops[i] pot ser: CONJ, DISJ, IMPL, NAND.
     *
     * Retornau:
     *   1 si és una tautologia
     *   0 si és una contradicció
     *   -1 en qualsevol altre cas.
     *
     * Vegeu els tests per exemples.
         */
        static final char CONJ = '∧';
        static final char DISJ = '∨';
        static final char IMPL = '→';
        static final char NAND = '.';

        static int exercici1(char[] ops, int[] vars) {
            try {
                // Convertim les primeres dues variables a booleans
                boolean var1 = es1(vars[0]);
                boolean var2 = es1(vars[1]);

                // Apliquem el primer operador entre var1 i var2
                var1 = operacions(var1, var2, ops[0]);

                // Iterem per la resta de variables i operadors
                for (int i = 2; i < vars.length; i++) {
                    var2 = es1(vars[i]);                  // Convertim la nova variable a boolean
                    var1 = operacions(var1, var2, ops[i - 1]);  // Apliquem l'operador corresponent
                }

                // Retornem el resultat final com a 1 (true) o 0 (false)
                if (var1) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (IllegalArgumentException e) {
                return -1;
            }
        }

        static boolean operacions(boolean a, boolean b, char op) {
            // Per saber quina operació ha de realitzar
            switch (op) {
                case CONJ:  // conjunció
                    return a && b;
                case DISJ:  // disjunció
                    return a || b;
                case IMPL:  // implicació
                    return !a || b;
                case NAND:  // nand
                    return !(a && b);
                default:
                    throw new IllegalArgumentException("Operador desconegut: " + op);
            }
        }

        static boolean es1(int valor) {
            return valor == 1;  // retorna true si el valor és 1
        }

        /*
     * Aquest mètode té de paràmetre l'univers (representat com un array) i els predicats
     * adients `p` i `q`. Per avaluar aquest predicat, si `x` és un element de l'univers, podeu
     * fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és cert).
     *
     * Amb l'univers i els predicats `p` i `q` donats, returnau true si la següent proposició és
     * certa.
     *
     * (∀x : P(x)) <-> (∃!x : Q(x))
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
            boolean totsCompleixenP = true; // suposam que tots compleixen P
            int comptadorQ = 0;             // comptam quants compleixen Q

            for (int x : universe) {
                if (!p.test(x)) {
                    totsCompleixenP = false;    // si qualcú no compleix P, canviam a false
                }
                if (q.test(x)) {
                    comptadorQ++;       // comptam quants compleixen Q
                }
            }

            // (∀x : P(x)) <-> (∃!x : Q(x)) s'ha de complir exactament una vegada
            boolean existeixUnicQ = comptadorQ == 1;

            // retornam si les dues condicions són equivalents
            return totsCompleixenP == existeixUnicQ;
        }

        static void tests() {
            // Exercici 1
            // Taules de veritat

            // Tautologia: ((p0 → p2) ∨ p1) ∨ p0
            test(1, 1, 1, () -> exercici1(new char[]{IMPL, DISJ, DISJ}, new int[]{0, 2, 1, 0}) == 1);

            // Contradicció: (p0 . p0) ∧ p0
            test(1, 1, 2, () -> exercici1(new char[]{NAND, CONJ}, new int[]{0, 0, 0}) == 0);

            // Exercici 2
            // Equivalència
            test(1, 2, 1, () -> {
                return exercici2(new int[]{1, 2, 3}, (x) -> x == 0, (x) -> x == 0);
            });

            test(1, 2, 2, () -> {
                return exercici2(new int[]{1, 2, 3}, (x) -> x >= 1, (x) -> x % 2 == 0);
            });
        }
    }

    /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][]. Podeu donar per suposat que tots els
   * arrays que representin conjunts i us venguin per paràmetre estan ordenats de menor a major.
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. L'array estarà ordenat lexicogràficament. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o bé amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a
   * i el codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per
   * aplicar f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu
   * f.apply(x).
     */
    static class Tema2 {

        static int valors[][];

        /*
     * Trobau el nombre de particions diferents del conjunt `a`, que podeu suposar que no és buid.
     *
     * Pista: Cercau informació sobre els nombres de Stirling.
         */
        static int exercici1(int[] a) {
            int n = a.length;

            // Matriu per guardar els nombres de Stirling
            int[][] S = new int[n + 1][n + 1];

            // Cas base
            S[0][0] = 1;

            // Fórmula de recurrència: S(n, k) = k * S(n-1, k) + S(n-1, k-1)
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    S[i][j] = j * S[i - 1][j] + S[i - 1][j - 1];
                }
            }

            // El nombre de particions del conjunt és la suma dels nombres de Stirling per a k = 1 fins n
            int totalParticions = 0;
            for (int k = 1; k <= n; k++) {
                totalParticions += S[n][k];
            }

            return totalParticions;
        }

        /*
     * Trobau el cardinal de la relació d'ordre parcial sobre `a` més petita que conté `rel` (si
     * existeix). En altres paraules, el cardinal de la seva clausura reflexiva, transitiva i
     * antisimètrica.
     *
     * Si no existeix, retornau -1.
         */
        static int exercici2(int[] a, int[][] rel) {
            int n = a.length;
            int[] pos = new int[a[n - 1] + 1];

            // Assignam la posició real per a cada valor de a
            for (int i = 0; i < n; i++) {
                pos[a[i]] = i;
            }

            // Cream matriu de relació
            boolean[][] relacio = new boolean[n][n];
            for (int[] par : rel) {
                relacio[pos[par[0]]][pos[par[1]]] = true;
            }

            // Clausura reflexiva
            for (int i = 0; i < n; i++) {
                relacio[i][i] = true;
            }

            // Clausura transitiva
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        relacio[i][j] |= (relacio[i][k] && relacio[k][j]);
                    }
                }
            }

            // Clausura antisimètrica
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && relacio[i][j] && relacio[j][i]) {
                        return -1;
                    }
                }
            }

            // Comptam el total de relacions
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (relacio[i][j]) {
                        count++;
                    }
                }
            }

            return count;
        }


        /*
     * Donada una relació d'ordre parcial `rel` definida sobre `a` i un subconjunt `x` de `a`,
     * retornau:
     * - L'ínfim de `x` si existeix i `op` és false
     * - El suprem de `x` si existeix i `op` és true
     * - null en qualsevol altre cas
         */
        static Integer exercici3(int[] a, int[][] rel, int[] x, boolean op) {
            int n = a.length;
            int max = a[n - 1];
            int[] posicio = new int[max + 1];

            for (int i = 0; i < n; i++) {
                posicio[a[i]] = i;
            }

            // Cream matriu de relació
            boolean[][] matriu = new boolean[n][n];
            for (int[] parella : rel) {
                int i = posicio[parella[0]];
                int j = posicio[parella[1]];
                matriu[i][j] = true;
            }

            // Clausura transitiva
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (matriu[i][k] && matriu[k][j]) {
                            matriu[i][j] = true;
                        }
                    }
                }
            }

            // Cerquem ínfim o suprem
            Integer millor = null;

            for (int i = 0; i < n; i++) {
                int valor = a[i];
                boolean compleix = true;

                for (int elem : x) {
                    int j = posicio[elem];

                    if (op) {
                        // Suprem: ha de ser major o igual que tots els de x
                        if (!matriu[j][i]) {
                            compleix = false;
                            break;
                        }
                    } else {
                        // Ínfim: ha de ser menor o igual que tots els de x
                        if (!matriu[i][j]) {
                            compleix = false;
                            break;
                        }
                    }
                }

                if (compleix) {
                    if (millor == null || (op && valor < millor) || (!op && valor > millor)) {
                        millor = valor;
                    }
                }
            }

            return millor;
        }

        /*
     * Donada una funció `f` de `a` a `b`, retornau:
     *  - El graf de la seva inversa (si existeix)
     *  - Sinó, el graf d'una inversa seva per l'esquerra (si existeix)
     *  - Sinó, el graf d'una inversa seva per la dreta (si existeix)
     *  - Sinó, null.
         */
        static int[][] exercici4(int[] a, int[] b, Function<Integer, Integer> f) {
            int[] valorsF = new int[a.length];
            int[] preimatges = new int[a.length];
            int mida = 0;

            // Crear parells (f(x), x) i verificar si hi ha col·lisions
            for (int i = 0; i < a.length; i++) {
                int x = a[i];
                int fx = f.apply(x);
                boolean trobat = false;

                for (int j = 0; j < mida; j++) {
                    if (valorsF[j] == fx) {
                        if (preimatges[j] != x) {
                            // Si ja existeix amb diferent x -> no és injectiva
                            return null;
                        }
                        trobat = true;
                        break;
                    }
                }

                if (!trobat) {
                    valorsF[mida] = fx;
                    preimatges[mida] = x;
                    mida++;
                }
            }

            // Construir el graf de la inversa estesa
            int[][] graf = new int[b.length][2];
            int valorArbitrari = a[0];

            for (int i = 0; i < b.length; i++) {
                int y = b[i];
                int x = valorArbitrari;

                for (int j = 0; j < mida; j++) {
                    if (valorsF[j] == y) {
                        x = preimatges[j];
                        break;
                    }
                }

                graf[i][0] = y;
                graf[i][1] = x;
            }

            return graf;
        }


        /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // Nombre de particions

            test(2, 1, 1, () -> exercici1(new int[]{1}) == 1);
            test(2, 1, 2, () -> exercici1(new int[]{1, 2, 3}) == 5);

            // Exercici 2
            // Clausura d'ordre parcial
            final int[] INT02 = {0, 1, 2};

            test(2, 2, 1, () -> exercici2(INT02, new int[][]{{0, 1}, {1, 2}}) == 6);
            test(2, 2, 2, () -> exercici2(INT02, new int[][]{{0, 1}, {1, 0}, {1, 2}}) == -1);

            // Exercici 3
            // Ínfims i suprems
            final int[] INT15 = {1, 2, 3, 4, 5};
            final int[][] DIV15 = generateRel(INT15, (n, m) -> m % n == 0);
            final Integer ONE = 1;

            test(2, 3, 1, () -> ONE.equals(exercici3(INT15, DIV15, new int[]{2, 3}, false)));
            test(2, 3, 2, () -> exercici3(INT15, DIV15, new int[]{2, 3}, true) == null);

            // Exercici 4
            // Inverses
            final int[] INT05 = {0, 1, 2, 3, 4, 5};

            test(2, 4, 1, () -> {
                var inv = exercici4(INT05, INT02, (x) -> x / 2);

                if (inv == null) {
                    return false;
                }

                inv = lexSorted(inv);

                if (inv.length != INT02.length) {
                    return false;
                }

                for (int i = 0; i < INT02.length; i++) {
                    if (inv[i][0] != i || inv[i][1] / 2 != i) {
                        return false;
                    }
                }

                return true;
            });

            test(2, 4, 2, () -> {
                var inv = exercici4(INT02, INT05, (x) -> x);

                if (inv == null) {
                    return false;
                }

                inv = lexSorted(inv);

                if (inv.length != INT05.length) {
                    return false;
                }

                for (int i = 0; i < INT02.length; i++) {
                    if (inv[i][0] != i || inv[i][1] != i) {
                        return false;
                    }
                }

                return true;
            });
        }

        /*
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
         */
        static int[][] lexSorted(int[][] arr) {
            if (arr == null) {
                return null;
            }

            var arr2 = Arrays.copyOf(arr, arr.length);
            Arrays.sort(arr2, Arrays::compare);
            return arr2;
        }

        /*
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
         */
        static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
            var rel = new ArrayList<int[]>();

            for (int a : as) {
                for (int b : bs) {
                    if (pred.test(a, b)) {
                        rel.add(new int[]{a, b});
                    }
                }
            }

            return rel.toArray(new int[][]{});
        }

        // Especialització de generateRel per as = bs
        static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
            return generateRel(as, as, pred);
        }
    }

    /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle no dirigit d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
     */
    static class Tema3 {

        /*
     * Determinau si el graf `g` (no dirigit) té cicles.
         */
        static boolean exercici1(int[][] graf) {
            int n = graf.length; //Nombre de nodes del graf 
            boolean[] marcat = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!marcat[i]) {
                    if (hiHaCicle(graf, marcat, i, -1)) { //Sitrobam un cicle retornam true
                        return true;
                    }
                }
            }
            return false;
        }

        // funció auxiliar per detectar cicles
        static boolean hiHaCicle(int[][] graf, boolean[] marcat, int actual, int anterior) {
            marcat[actual] = true; //Marcam el node actual com a visitat
            for (int veinat : graf[actual]) {
                if (!marcat[veinat]) {
                    if (hiHaCicle(graf, marcat, veinat, actual)) {
                        return true;
                    }
                } else if (veinat != anterior) {
                    return true;
                }
            }
            return false;
        }


        /*
     * Determinau si els dos grafs són isomorfs. Podeu suposar que cap dels dos té ordre major que
     * 10.
         */
        static boolean exercici2(int[][] g1, int[][] g2) {
            int n = g1.length;
            if (n != g2.length) { // Si els grafs no tenen els mateixos nodes no poden ser isomorfs
                return false;
            }

            int[] perm = new int[n]; //Array que representa una permutació de nodes
            for (int i = 0; i < n; i++) {
                perm[i] = i;
            }

            do { //Si amb aquesta permutació son isomorfs retornam true
                if (mateixGraf(g1, g2, perm)) {
                    return true;
                }
            } while (properaPermutacio(perm));

            return false;
        }

        static boolean mateixGraf(int[][] g1, int[][] g2, int[] perm) {
            int n = g1.length;
            for (int i = 0; i < n; i++) {
                boolean[] connexions = new boolean[n];
                for (int veinat : g1[i]) {
                    connexions[perm[veinat]] = true;
                }

                if (g2[perm[i]].length != g1[i].length) {
                    return false;
                }
                for (int v : g2[perm[i]]) { //Comprobam que tots els veïns del node perm[i] en g2 esta en connexions
                    if (!connexions[v]) {
                        return false;
                    }
                }
            }
            return true;
        }
//Genera la següent permutació lexicográfica de l'array perm
// Retorna false si ja no hi ha més permutacions

        static boolean properaPermutacio(int[] perm) {
            int i = perm.length - 2;
            while (i >= 0 && perm[i] > perm[i + 1]) {
                i--;
            }
            if (i < 0) {
                return false;
            }

            int j = perm.length - 1;
            while (perm[j] < perm[i]) {
                j--;
            }

            // Intercanviam perm[i] y perm[j]
            int aux = perm[i];
            perm[i] = perm[j];
            perm[j] = aux;

            for (int a = i + 1, b = perm.length - 1; a < b; a++, b--) {
                aux = perm[a];
                perm[a] = perm[b];
                perm[b] = aux;
            }
            return true;
        }


        /*
     * Determinau si el graf `g` (no dirigit) és un arbre. Si ho és, retornau el seu recorregut en
     * postordre desde el vèrtex `r`. Sinó, retornau null;
     *
     * En cas de ser un arbre, assumiu que l'ordre dels fills vé donat per l'array de veïns de cada
     * vèrtex.
         */
        static int[] exercici3(int[][] graf, int arrel) {
            int n = graf.length;
            boolean[] marcat = new boolean[n];
// Comprobam que no hi hagui cicles, si hi ha no és un arbre
            if (hiHaCicle(graf, marcat, arrel, -1)) {
                return null;
            }
            //Miram si es connex 
            for (boolean v : marcat) {
                if (!v) {
                    return null;
                }
            }

            int[] recorregut = new int[n];
            int[] posicio = new int[1];
            for (int i = 0; i < n; i++) {
                marcat[i] = false;
            }

            postordre(graf, arrel, marcat, recorregut, posicio);
            return recorregut;
        }
// Funció auxiliar: realitza un recorregut en postordre sobre el graf

        static void postordre(int[][] graf, int actual, boolean[] marcat, int[] recorregut, int[] pos) {
            marcat[actual] = true;
            for (int v : graf[actual]) {
                if (!marcat[v]) {
                    postordre(graf, v, marcat, recorregut, pos);
                }
            }
            recorregut[pos[0]++] = actual; //Afegim el node actual al recorregut
        }

        /*
     * Suposau que l'entrada és un mapa com el següent, donat com String per files (vegeu els tests)
     *
     *   _____________________________________
     *  |          #       #########      ####|
     *  |       O  # ###   #########  ##  ####|
     *  |    ####### ###   #########  ##      |
     *  |    ####  # ###   #########  ######  |
     *  |    ####    ###              ######  |
     *  |    ######################## ##      |
     *  |    ####                     ## D    |
     *  |_____________________________##______|
     *
     * Els límits del mapa els podeu considerar com els límits de l'array/String, no fa falta que
     * cerqueu els caràcters "_" i "|", i a més podeu suposar que el mapa és rectangular.
     *
     * Donau el nombre mínim de caselles que s'han de recorrer per anar de l'origen "O" fins al
     * destí "D" amb les següents regles:
     *  - No es pot sortir dels límits del mapa
     *  - No es pot passar per caselles "#"
     *  - No es pot anar en diagonal
     *
     * Si és impossible, retornau -1.
         */
        static int exercici4(char[][] mapa) {
            int files = mapa.length, columnes = mapa[0].length; //Dimensió 
            int origenX = -1, origenY = -1;
            //Cercam la posició d'origen
            for (int i = 0; i < files && origenX == -1; i++) {
                for (int j = 0; j < columnes; j++) {
                    if (mapa[i][j] == 'O') {
                        origenX = i;
                        origenY = j;
                        break;
                    }
                }
            }
            if (origenX == -1) {
                return -1;
            }

            int[][] cua = new int[files * columnes][2];
            boolean[][] visitat = new boolean[files][columnes];
            int[][] dist = new int[files][columnes];
            int ini = 0, fi = 0;

            int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
            cua[fi][0] = origenX;
            cua[fi][1] = origenY;
            fi++;
            visitat[origenX][origenY] = true;

            while (ini < fi) {
                int x = cua[ini][0], y = cua[ini][1];
                ini++;
                if (mapa[x][y] == 'D') { //Si arribam al final retornam la distancia
                    return dist[x][y];
                }

                for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d], ny = y + dy[d];
                    //Comprobam que estiguin dins els limits 
                    if (nx >= 0 && nx < files && ny >= 0 && ny < columnes && !visitat[nx][ny] && mapa[nx][ny] != '#') {
                        visitat[nx][ny] = true;
                        dist[nx][ny] = dist[x][y] + 1;
                        cua[fi][0] = nx;
                        cua[fi][1] = ny;
                        fi++;
                    }
                }
            }
            return -1;
        }


        /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {

            final int[][] D2 = {{}, {}};
            final int[][] C3 = {{1, 2}, {0, 2}, {0, 1}};

            final int[][] T1 = {{1, 2}, {0}, {0}};
            final int[][] T2 = {{1}, {0, 2}, {1}};

            // Exercici 1
            // G té cicles?
            test(3, 1, 1, () -> !exercici1(D2));
            test(3, 1, 2, () -> exercici1(C3));

            // Exercici 2
            // Isomorfisme de grafs
            test(3, 2, 1, () -> exercici2(T1, T2));
            test(3, 2, 1, () -> !exercici2(T1, C3));

            // Exercici 3
            // Postordre
            test(3, 3, 1, () -> exercici3(C3, 1) == null);
            test(3, 3, 2, () -> Arrays.equals(exercici3(T1, 0), new int[]{1, 2, 0}));

            // Exercici 4
            // Laberint
            test(3, 4, 1, () -> {
                return -1 == exercici4(new char[][]{
                    " #O".toCharArray(),
                    "D# ".toCharArray(),
                    " # ".toCharArray(),});
            });

            test(3, 4, 2, () -> {
                return 8 == exercici4(new char[][]{
                    "###D".toCharArray(),
                    "O # ".toCharArray(),
                    " ## ".toCharArray(),
                    "    ".toCharArray(),});
            });
        }
    }

    /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
     */
    static class Tema4 {

        /*
     * Primer, codificau el missatge en blocs de longitud 2 amb codificació ASCII. Després encriptau
     * el missatge utilitzant xifrat RSA amb la clau pública donada.
     *
     * Per obtenir els codis ASCII del String podeu utilitzar `msg.getBytes()`.
     *
     * Podeu suposar que:
     * - La longitud de `msg` és múltiple de 2
     * - El valor de tots els caràcters de `msg` està entre 32 i 127.
     * - La clau pública (n, e) és de la forma vista a les transparències.
     * - n és major que 2¹⁴, i n² és menor que Integer.MAX_VALUE
     *
     * Pista: https://en.wikipedia.org/wiki/Exponentiation_by_squaring
         */
        static int[] exercici1(String missatge, int n, int e) {
            byte[] lletres = missatge.getBytes(); //Convertim en un array de Bytes
            int[] blocs = new int[lletres.length / 2];

            // Agrupam cada dos caràcters en un sol enter (bloc)
            for (int i = 0; i < lletres.length; i += 2) {
                int primer = lletres[i];       // primer caràcter
                int segon = lletres[i + 1];    // segon caràcter
                blocs[i / 2] = primer * 256 + segon;
            }

            // Xifram cada bloc amb RSA: c = m^e mod n
            for (int i = 0; i < blocs.length; i++) {
                blocs[i] = potenciaMod(blocs[i], e, n);
            }

            return blocs;
        }

        // Mètode per calcular (base^exp) % modul
        static int potenciaMod(int base, int exp, int modul) {
            long resultat = 1;
            long b = base;

            for (int i = 0; i < exp; i++) {
                resultat = (resultat * b) % modul;
            }

            return (int) resultat;
        }


        /*
     * Primer, desencriptau el missatge utilitzant xifrat RSA amb la clau pública donada. Després
     * descodificau el missatge en blocs de longitud 2 amb codificació ASCII (igual que l'exercici
     * anterior, però al revés).
     *
     * Per construir un String a partir d'un array de bytes podeu fer servir el constructor
     * `new String(byte[])`. Si heu de factoritzar algun nombre, ho podeu fer per força bruta.
     *
     * També podeu suposar que:
     * - La longitud del missatge original és múltiple de 2
     * - El valor de tots els caràcters originals estava entre 32 i 127.
     * - La clau pública (n, e) és de la forma vista a les transparències.
     * - n és major que 2¹⁴, i n² és menor que Integer.MAX_VALUE
         */
        static String exercici2(int[] m, int n, int e) {
            // 1. Factoritzam n per trobar p i q
            int p = -1, q = -1;
            for (int i = 2; i < n; i++) {
                if (n % i == 0) {
                    p = i;
                    q = n / i;
                    break;
                }
            }

            int phi = (p - 1) * (q - 1);

            // 2. Trobar d tal que (e * d) % phi == 1
            int d = -1;
            for (int i = 1; i < phi; i++) {
                if ((e * i) % phi == 1) {
                    d = i;
                    break;
                }
            }

            // 3. Desxifram cada bloc m[i]^d mod n
            int[] blocs = new int[m.length];
            for (int i = 0; i < m.length; i++) {
                blocs[i] = potenciaMod(m[i], d, n);
            }

            // 4. Separar cada bloc en dos caràcters
            byte[] lletres = new byte[m.length * 2];
            for (int i = 0; i < blocs.length; i++) {
                int bloc = blocs[i];
                int primer = bloc / 256;
                int segon = bloc % 256;
                lletres[2 * i] = (byte) primer;
                lletres[2 * i + 1] = (byte) segon;
            }

            return new String(lletres);
        }

        static void tests() {
            // Exercici 1
            // Codificar i encriptar
            test(4, 1, 1, () -> {
                var n = 2 * 8209;
                var e = 5;

                var encr = exercici1("Patata", n, e);
                return Arrays.equals(encr, new int[]{4907, 4785, 4785});
            });

            // Exercici 2
            // Desencriptar i decodificar
            test(4, 2, 1, () -> {
                var n = 2 * 8209;
                var e = 5;

                var encr = new int[]{4907, 4785, 4785};
                var decr = exercici2(encr, n, e);
                return "Patata".equals(decr);
            });
        }
    }

    /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `test` per comprovar fàcilment que un valor sigui `true`.
     */
    public static void main(String[] args) {
        System.out.println("---- Tema 1 ----");
        Tema1.tests();
        System.out.println("---- Tema 2 ----");
        Tema2.tests();
        System.out.println("---- Tema 3 ----");
        Tema3.tests();
        System.out.println("---- Tema 4 ----");
        Tema4.tests();
    }

    // Informa sobre el resultat de p, juntament amb quin tema, exercici i test es correspon.
    static void test(int tema, int exercici, int test, BooleanSupplier p) {
        try {
            if (p.getAsBoolean()) {
                System.out.printf("Tema %d, exercici %d, test %d: OK\n", tema, exercici, test);
            } else {
                System.out.printf("Tema %d, exercici %d, test %d: Error\n", tema, exercici, test);
            }
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException && "pendent".equals(e.getMessage())) {
                System.out.printf("Tema %d, exercici %d, test %d: Pendent\n", tema, exercici, test);
            } else {
                System.out.printf("Tema %d, exercici %d, test %d: Excepció\n", tema, exercici, test);
                e.printStackTrace();
            }
        }
    }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :

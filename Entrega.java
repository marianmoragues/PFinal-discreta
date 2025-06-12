package pfinaldiscreta;

import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        static boolean operacions(boolean var1, boolean var2, char ops) {
            boolean operacio;

            switch (ops) {
                case 1:
                    operacio = var1 && var2;
                    break;
                case 2:
                    operacio = var1 || var2;
                    break;
                case 3:
                    operacio = !var1 || var2;
                    break;
                case 4:
                    operacio = !(var1 && var2);
                    break;
                default:
                    throw new AssertionError();
            }
            return operacio;
        }

        static boolean es1(int valor) {
            return valor == 1;
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
            boolean totsCompleixenP = true;
            int comptadorQ = 0;

            for (int x : universe) {
                if (!p.test(x)) {
                    totsCompleixenP = false;
                }
                if (q.test(x)) {
                    comptadorQ++;
                }
            }

            // (∀x : P(x)) <-> (∃!x : Q(x)) s'ha de complir exactament una vegada
            boolean existeixUnicQ = comptadorQ == 1;

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

            // Matriu per als nombres de Stirling de segon tipus
            int[][] S = new int[n + 1][n + 1];

            // Cas base: només hi ha una manera de dividir un conjunt buit en 0 subconjunts
            S[0][0] = 1;

            // Omplim la taula segons la relació de recurrència:
            // S(n, k) = k * S(n-1, k) + S(n-1, k-1)
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    S[i][j] = j * S[i - 1][j] + S[i - 1][j - 1];
                }
            }

            // El nombre de particions del conjunt és la suma dels nombres de Stirling de segon tipus per a k = 1 fins n
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
            int n = a.length; // Nombre d'elements del conjunt

            int max = a[n - 1]; // com que l'array a està ordenat, podem crear un array
            // per mapar cada valor del conjunt al seu índex
            int[] indexos = new int[max + 1]; // ens asseguram que la mida sigui suficient

            // omplim l'array indexos
            for (int i = 0; i < n; i++) {
                indexos[a[i]] = i;
            }

            // per representar la relació entre elements del conjunt
            boolean[][] matriu = new boolean[n][n];

            // afegim a la matriu les parelles que ja formen part de rel
            for (int[] parella : rel) {
                int i = indexos[parella[0]]; // index del primer element
                int j = indexos[parella[1]]; // index del segon element
                matriu[i][j] = true;         // hi ha relació i -> j
            }

            // Condició reflexiva: ∀a : a R a
            for (int i = 0; i < n; i++) {
                matriu[i][i] = true;
            }

            // Condició transitiva: ∀a, b, c : a R b ∧ b R c → a R c
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (matriu[i][k] && matriu[k][j]) {
                            matriu[i][j] = true;
                        }
                    }
                }
            }

            // Comprovació antisimetria: ∀a, b : a R b ∧ b R a → a = b
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && matriu[i][j] && matriu[j][i]) {
                        return -1; // relació no és antisimètrica, no pot ser d'ordre parcial
                    }
                }
            }

            // comptam quantes relacions conté la matriu finalment
            int total = 0;
            for (boolean[] fila : matriu) {
                for (boolean valor : fila) {
                    if (valor) {
                        total++;
                    }
                }
            }

            // retornam el cardinal de la relació
            return total;
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

            // Mapam cada element del conjunt a un índex
            int max = a[n - 1];
            int[] indexos = new int[max + 1];
            for (int i = 0; i < n; i++) {
                indexos[a[i]] = i;
            }

            // Cream la matriu de relació
            boolean[][] matriu = new boolean[n][n];
            for (int[] parella : rel) {
                int i = indexos[parella[0]];
                int j = indexos[parella[1]];
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

            // Suprem: mínim dels majos comuns
            // Ínfim: màxim dels menors comuns
            Integer resultat = null;

            for (int candidat : a) {
                int i = indexos[candidat];
                boolean vàlid = true;

                for (int elem : x) {
                    int j = indexos[elem];
                    if (op) {
                        // Suprem → ha de ser major o igual que tots els de x
                        if (!matriu[j][i]) {
                            vàlid = false;
                            break;
                        }
                    } else {
                        // Ínfim → ha de ser menor o igual que tots els de x
                        if (!matriu[i][j]) {
                            vàlid = false;
                            break;
                        }
                    }
                }

                if (vàlid) {
                    if (resultat == null) {
                        resultat = candidat;
                    } else {
                        if (op) {
                            // Suprem → cercam el menor
                            if (candidat < resultat) {
                                resultat = candidat;
                            }
                        } else {
                            // Ínfim → cercam el major
                            if (candidat > resultat) {
                                resultat = candidat;
                            }
                        }
                    }
                }
            }

            return resultat;
        }


        /*
     * Donada una funció `f` de `a` a `b`, retornau:
     *  - El graf de la seva inversa (si existeix)
     *  - Sinó, el graf d'una inversa seva per l'esquerra (si existeix)
     *  - Sinó, el graf d'una inversa seva per la dreta (si existeix)
     *  - Sinó, null.
         */
        static int[][] exercici4(int[] a, int[] b, Function<Integer, Integer> f) {
            int n = a.length;
            int[][] graf = new int[n][2];

            // Construïm el graf de la funció f
            for (int i = 0; i < n; i++) {
                graf[i][0] = a[i];
                graf[i][1] = f.apply(a[i]);
            }

            // Comprovam si és injectiva (no es repeteixen imatges)
            boolean injectiva = true;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (graf[i][1] == graf[j][1]) {
                        injectiva = false;
                        break;
                    }
                }
                if (!injectiva) {
                    break;
                }
            }

            // Comprovam si és exhaustiva (tots els de b apareixen com a imatge)
            boolean exhaustiva = true;
            for (int i = 0; i < b.length; i++) {
                boolean trobat = false;
                for (int j = 0; j < n; j++) {
                    if (graf[j][1] == b[i]) {
                        trobat = true;
                        break;
                    }
                }
                if (!trobat) {
                    exhaustiva = false;
                    break;
                }
            }

            // Si és bijectiva (injectiva i exhaustiva), retornam la inversa completa
            if (injectiva && exhaustiva) {
                int[][] inversa = new int[n][2];
                for (int i = 0; i < n; i++) {
                    inversa[i][0] = graf[i][1];
                    inversa[i][1] = graf[i][0];
                }
                return inversa;
            }

            // Si només és injectiva → inversa per l'esquerra
            if (injectiva) {
                int[][] esquerra = new int[n][2];
                for (int i = 0; i < n; i++) {
                    esquerra[i][0] = graf[i][1];
                    esquerra[i][1] = graf[i][0];
                }
                return esquerra;
            }

            // Si només és exhaustiva → inversa per la dreta
            if (exhaustiva) {
                int m = b.length;
                int[][] dreta = new int[m][2];
                for (int i = 0; i < m; i++) {
                    int y = b[i];
                    for (int j = 0; j < n; j++) {
                        if (graf[j][1] == y) {
                            dreta[i][0] = y;
                            dreta[i][1] = graf[j][0];
                            break;
                        }
                    }
                }
                return dreta;
            }

            // Si no compleix cap condició
            return null;
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
        static boolean exercici1(int[][] g) {
            int n = g.length;
            boolean[] visitat = new boolean[n];

            for (int i = 0; i < n; i++) {
                if (!visitat[i]) {
                    if (esCicle(g, visitat, i, -1)) {
                        return true;
                    }
                }
            }

            return false;
        }

        // retorna true si troba un cicle
        static boolean esCicle(int[][] g, boolean[] visitat, int actual, int pare) {
            visitat[actual] = true;

            for (int veinat : g[actual]) {
                if (!visitat[veinat]) {
                    if (esCicle(g, visitat, veinat, actual)) {
                        return true;
                    }
                } else if (veinat != pare) {
                    // Si el veïnat ja ha estat visitat i no és el pare → cicle
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
            if (n != g2.length) {
                return false;
            }

            int[] perm = new int[n];
            for (int i = 0; i < n; i++) {
                perm[i] = i;
            }

            do {
                if (comprovaIsomorfisme(g1, g2, perm)) {
                    return true;
                }
            } while (segPermutacio(perm));

            return false;
        }

        // Comprova si aplicar la permutació fa que g1 sigui igual a g2
        static boolean comprovaIsomorfisme(int[][] g1, int[][] g2, int[] perm) {
            int n = g1.length;

            for (int i = 0; i < n; i++) {
                boolean[] connexions = new boolean[n];
                for (int veinat : g1[i]) {
                    connexions[perm[veinat]] = true;
                }

                for (int j = 0; j < n; j++) {
                    if (g2[perm[i]].length != g1[i].length) {
                        return false;
                    }
                    boolean trobat = false;
                    for (int x : g2[perm[i]]) {
                        if (connexions[x]) {
                            trobat = true;
                        } else {
                            trobat = false;
                            break;
                        }
                    }
                    if (!trobat) {
                        return false;
                    }
                    break; // ja hem comprovat aquest vèrtex
                }
            }

            return true;
        }

        // Genera la següent permutació lexicogràfica 
        static boolean segPermutacio(int[] perm) {
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
        static int[] exercici3(int[][] g, int r) {
            int n = g.length;
            boolean[] visitat = new boolean[n];

            // Comprovam si hi ha algun cicle
            if (esCicle(g, visitat, r, -1)) {
                return null;
            }

            // Comprovam si és connex
            for (boolean v : visitat) {
                if (!v) {
                    return null;
                }
            }

            // Si és un arbre, feim el postordre
            int[] resultat = new int[n];
            int[] pos = new int[1]; // ús d'array per passar enter per referència
            Arrays.fill(visitat, false);
            postordre(g, r, visitat, resultat, pos);

            return resultat;
        }

        static void postordre(int[][] g, int u, boolean[] visitat, int[] resultat, int[] pos) {
            visitat[u] = true;

            for (int v : g[u]) {
                if (!visitat[v]) {
                    postordre(g, v, visitat, resultat, pos);
                }
            }

            resultat[pos[0]] = u;
            pos[0]++;
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
            int files = mapa.length;
            int columnes = mapa[0].length;

            // Cercam l'origen
            int origenX = -1, origenY = -1;
            for (int i = 0; i < files; i++) {
                for (int j = 0; j < columnes; j++) {
                    if (mapa[i][j] == 'O') {
                        origenX = i;
                        origenY = j;
                        break;
                    }
                }
                if (origenX != -1) {
                    break;
                }
            }

            // Si no s'ha trobat l'origen
            if (origenX == -1) {
                return -1;
            }

            // Cua bàsica amb arrays
            int max = files * columnes;
            int[][] cua = new int[max][2];
            int inici = 0;
            int fi = 0;

            // Arrays auxiliars
            boolean[][] visitat = new boolean[files][columnes];
            int[][] dist = new int[files][columnes];

            // Direccions (amunt, avall, esquerra, dreta)
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            // Afegim l'origen a la cua
            cua[fi][0] = origenX;
            cua[fi][1] = origenY;
            fi++;
            visitat[origenX][origenY] = true;
            dist[origenX][origenY] = 0;

            while (inici < fi) {
                int x = cua[inici][0];
                int y = cua[inici][1];
                inici++;

                if (mapa[x][y] == 'D') {
                    return dist[x][y];
                }

                // Exploram veïnats
                for (int d = 0; d < 4; d++) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if (nx >= 0 && nx < files && ny >= 0 && ny < columnes) {
                        if (!visitat[nx][ny] && mapa[nx][ny] != '#') {
                            visitat[nx][ny] = true;
                            dist[nx][ny] = dist[x][y] + 1;
                            cua[fi][0] = nx;
                            cua[fi][1] = ny;
                            fi++;
                        }
                    }
                }
            }

            return -1; // No s'ha trobat camí
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
        static int[] exercici1(String msg, int n, int e) {
            throw new UnsupportedOperationException("pendent");
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
            throw new UnsupportedOperationException("pendent");
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

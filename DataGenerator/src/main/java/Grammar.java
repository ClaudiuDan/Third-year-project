public class Grammar {
    public static final int SENTENCE_LENGTH = 3;
    /**
     sentence:  1: sentence 'and' sentence // deprecated
                2: noun verb noun
                3: noun verb adj
                4: ''
                5: noun verb constr

     constr:
                1: noun adjList
                2: noun nounList

     adjList:
                1: adj adjList
                2: ''

     nounList:
                1: noun nounList
                2: ''

     question:
                1: 'is' noun verb '?'
                2: 'how' 'is' noun '?'
     */

    /**
     CODE TRANSLATION
            2, 3: if entities . find ( word1 ) ! = None : newline newtab entities [ word1 ] [ word2 ] = word3 newline


     */
}

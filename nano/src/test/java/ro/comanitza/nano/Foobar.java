package ro.comanitza.nano;

/**
 *
 * Class that will be used for tests.
 *
 * @author scomanita
 */
public class Foobar {

    /**
     * Test method
     *
     * @return a test string
     */
    public String foo() {
        return "foobar";
    }

    /**
     * Test method
     *
     * @param i a test value
     * @return a test string
     */
    public String fooWithParam(final Integer i) {
        return "this is the number: " + i;
    }

    /**
     *
     * Test method
     *
     * @param first a test value
     * @param second  a test value
     * @param third a test value
     * @return a test string
     */
    public String fooWithMultipleParams(Integer first, Integer second, String third) {
        return "we have " + first + ", " + second + " and " + third;
    }
}

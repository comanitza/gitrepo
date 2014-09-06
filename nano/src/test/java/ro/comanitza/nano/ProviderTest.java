package ro.comanitza.nano;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * Test the {@link Provider} interface and it's
 * default implementation and offers example on
 * how the library should be used.
 *
 * @author scomanita
 */
public class ProviderTest {

    private static final String EXPECTED_STRING_FOR_PLAIN_METHOD = "foxtrot";
    private static final String EXPECTED_STRING_FROM_METHOD_WITH_SINGLE_PARAM = "so we got 200 in here";
    private static final String EXPECTED_STRING_FROM_METHOD_WITH_MULTIPLE_PARAMS = "we got ohio and a sum of 1300";

    private int counterToBeIncremented;

    /**
     * Test that a vanilla class is not enhanced and it's treated correctly
     * by the provider.
     */
    @Test
    public void testModelProviderWithVanillaClass() {
        ModelContainer modelContainer = creteSimpleModels();

        Provider provider = new ModelProvider(modelContainer);

        String providedInstance = provider.get(String.class);

        Assert.assertNotNull("Provided instance should not be null.", providedInstance);
    }

    /**
     * Test that a class is enhanced and one of it's method that has no params
     * is correctly intercepted.
     */
    @Test
    public void testModelProviderWithEnhancedClassMethodWithoutParams() {

        /**
         * 1. create the models to be used
         */
        ModelContainer modelContainer = creteSimpleModels();

        /**
         * 2. create a provider instance with the provided models
         */
        Provider provider = new ModelProvider(modelContainer);

        /**
         * 3. get an instance of the enhanced class
         */
        Foobar providedInstance = provider.get(Foobar.class);

        /**
         * 4. assert the enhanced class is not null
         */
        Assert.assertNotNull("Provided instance should not be null.", providedInstance);

        /**
         * 5. get the result from the enhanced class
         */
        String result = providedInstance.foo();

        /**
         * 6. assert the result is the expected one
         */
        Assert.assertEquals("Expected result was not found", EXPECTED_STRING_FOR_PLAIN_METHOD, result);
    }

    /**
     * Test the invocation of a intercepted method that has a single parameter
     */
    @Test
    public void testModelProviderWithEnhancedClassMethodWithSingleParam() {

        /**
         * 1. create the models to be used
         */
        ModelContainer modelContainer = creteSimpleModels();

        /**
         * 2. create a provider instance with the provided models
         */
        Provider provider = new ModelProvider(modelContainer);

        /**
         * 3. get an instance of the enhanced class
         */
        Foobar providedInstance = provider.get(Foobar.class);

        /**
         * 4. assert the enhanced class is not null
         */
        Assert.assertNotNull("Provided instance should not be null.", providedInstance);

        /**
         * 5. invoke the intercepted method
         */
        String result = providedInstance.fooWithParam(Integer.valueOf(100));

        /**
         * 6. check that we get the expected result
         */
        Assert.assertEquals("Expected result was not found", EXPECTED_STRING_FROM_METHOD_WITH_SINGLE_PARAM, result);
    }

    /**
     * Test the invocation of an intercepted method that has multiple params of different types.
     */
    @Test
    public void testModelProviderWithEnhancedClassMethodWithMultipleParams() {

        /**
         * 1. create the model container containing the models to be used
         */
        ModelContainer modelContainer = creteSimpleModels();

        /**
         * 2. create a provider instance with the provided models
         */
        Provider provider = new ModelProvider(modelContainer);

        /**
         * 3. get an instance of the enhanced class
         */
        Foobar providedInstance = provider.get(Foobar.class);

        /**
         * 4. assert the enhanced class is not null
         */
        Assert.assertNotNull("Provided instance should not be null.", providedInstance);

        /**
         * 5. invoke the intercepted method
         */
        String result = providedInstance.fooWithMultipleParams(100, 200, "yesen");

        /**
         * 6. check that we get the expected result
         */
        Assert.assertEquals("Expected result was not found", EXPECTED_STRING_FROM_METHOD_WITH_MULTIPLE_PARAMS, result);
    }

    /**
     * Test the creation of a {@link ro.comanitza.nano.ModelContainer} instance
     * using the {@link ro.comanitza.nano.ModelBuilder} builder.
     */
    @Test
    public void testMethodBuilder() {

        /**
         * 1. create the model container containing the models to be used
         */
        ModelContainer modelContainer = createModelContainerWithBuilder();

        /**
         * 2. create the provider
         */
        Provider provider = new ModelProvider(modelContainer);

        /**
         * 3. create a provided instance
         */
        Foobar providedInstance = provider.get(Foobar.class);

        /**
         * 4. check that the provided instance is non null
         */
        Assert.assertNotNull("Provided instance should not be null.", providedInstance);

        /**
         * 5. invoke the intercepted method
         */
        String result = providedInstance.foo();

        Assert.assertEquals("Expected result was not found", EXPECTED_STRING_FOR_PLAIN_METHOD, result);
    }

    /**
     * Test that check an intercepted method that has two {@link ro.comanitza.nano.ExecutionDescriptor}
     * mapped to it will invoke both {@link ro.comanitza.nano.ExecutionDescriptor}.
     *
     */
    @Test
    public void testBeforeJoinPoint() {

        /**
         * 1. create the model container containing the models to be used
         */
        ModelContainer modelContainer = createModelContainerWithBuilder();

        /**
         * 2. create the provider
         */
        Provider provider = new ModelProvider(modelContainer);

        /**
         * 3. create a provided instance
         */
        Foobar providedInstance = provider.get(Foobar.class);

        /**
         * 4. check that the provided instance is non null
         */
        Assert.assertNotNull("Provided instance should not be null.", providedInstance);

        int initialCounterValue = counterToBeIncremented;

        /**
         * 5. invoke the enhanced method
         */
        String result = providedInstance.fooWithParam(200);

        int finalCounterValue = counterToBeIncremented;

        /**
         * 6. check that we got the injected result
         */
        Assert.assertEquals("Expected result was not found", EXPECTED_STRING_FROM_METHOD_WITH_SINGLE_PARAM, result);

        /**
         * 7. check that the counter was incremented
         */
        Assert.assertTrue("Counter was not incremented!", finalCounterValue > initialCounterValue);
    }

    /**
     *
     * Method that creates a {@link ro.comanitza.nano.ModelContainer} instance
     * by using the {@link ro.comanitza.nano.ModelBuilder} builder class.
     *
     * @return a {@link ModelContainer}  instance
     */
    private ModelContainer createModelContainerWithBuilder() {
        Map<Method, ExecutionDescriptor> methodToDescriptor =  ModelBuilder.build()
                .addMethod(Foobar.class, "foo").addFunction((Function<String, String>)(String s) -> { return EXPECTED_STRING_FOR_PLAIN_METHOD;})
                .addJoinPoint(JoinPoint.INSTEAD).addParams(null).create();

        Map<Method, ExecutionDescriptor> methodToDescriptor2 =  ModelBuilder.build()
                .addMethod(Foobar.class, "fooWithParam", Integer.class).addFunction((Function<Integer, String>)(Integer i) -> { return EXPECTED_STRING_FROM_METHOD_WITH_SINGLE_PARAM;})
                .addJoinPoint(JoinPoint.INSTEAD).addParams(new Params(Integer.valueOf(100))).create();

        Map<Method, ExecutionDescriptor> methodToDescriptor3 =  ModelBuilder.build()
                .addMethod(Foobar.class, "fooWithParam", Integer.class).addFunction(
                        (Function<Integer, String>)(Integer i) -> { counterToBeIncremented++; return "counter was incremented";})
                .addJoinPoint(JoinPoint.BEFORE).addParams(new Params(Integer.valueOf(100))).create();

        ModelContainer modelContainer = new ModelContainer();
        modelContainer.addModel(methodToDescriptor);
        modelContainer.addModel(methodToDescriptor2);
        modelContainer.addModel(methodToDescriptor3);

        return modelContainer;
    }

    /**
     *
     * Method that creates a {@link ro.comanitza.nano.ModelContainer} instance
     * by directly creating the {@link Model} instances and adding them.
     *
     * @return a {@link ModelContainer}  instance
     */
    private ModelContainer creteSimpleModels() {
        Map<Class<?>, Model> models = new HashMap<Class<?>, Model>();

        Model foobarModel = new Model();

        Method methodWithoutParams;
        Method methodWithSingleParam;
        Method methodWithMultipleParams;

        try {
            methodWithoutParams = Foobar.class.getDeclaredMethod("foo");
            methodWithSingleParam = Foobar.class.getDeclaredMethod("fooWithParam", Integer.class);
            methodWithMultipleParams = Foobar.class.getDeclaredMethod("fooWithMultipleParams", Integer.class, Integer.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        foobarModel.addBundleToMethod(methodWithoutParams,
                new ExecutionDescriptorImpl<String, String>((String s) -> {return EXPECTED_STRING_FOR_PLAIN_METHOD;}, Optional.empty(), JoinPoint.INSTEAD)
            );

        foobarModel.addBundleToMethod(methodWithSingleParam,
                new ExecutionDescriptorImpl<>((Integer i) -> { return "so we got " + i + " in here";}, Optional.of(new Params(Integer.valueOf(200))), JoinPoint.INSTEAD)
            );

        Map<String, ParamTuple<?>> paramTupleMap = new HashMap<>();

        paramTupleMap.put("first", new ParamTuple<>(600, Integer.class));
        paramTupleMap.put("second", new ParamTuple<>(700, Integer.class));
        paramTupleMap.put("third", new ParamTuple<>("ohio", String.class));

        Function<Map<String, ParamTuple<?>>, String> func = (Map<String, ParamTuple<?>> m) -> {

                ParamTuple<?> firstParam = m.get("first");
                ParamTuple<?> secondParam = m.get("second");
                ParamTuple<?> thirdParam = m.get("third");

                Integer firstValue = Integer.class.cast(firstParam.getValueAsType());
                Integer secondValue = Integer.class.cast(secondParam.getValueAsType());
                String thirdValue = String.class.cast(thirdParam.getValueAsType());

                return "we got " + thirdValue + " and a sum of " + (firstValue + secondValue);
            };

        foobarModel.addBundleToMethod(methodWithMultipleParams, new ExecutionDescriptorImpl<>(func, Optional.of(new Params(paramTupleMap)), JoinPoint.INSTEAD));

        models.put(Foobar.class, foobarModel);

        return new ModelContainer(models);
    }
}

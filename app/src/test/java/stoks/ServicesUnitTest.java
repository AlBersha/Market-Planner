package stoks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import marketplanner.stocks.UrlBuilder;

import static org.junit.Assert.assertTrue;



public class UrlBuilderTest {
    private static final String BASE_URL = "http://fakeparams.net";
    private static final String parameter = "fake_param";
    private static UrlBuilder builder;
    private final String key = "fake_key";
    private final String DEFAULT_URL = "http://kqzg9.mocklab.io/market-planner.default/greatsMe";
    private final String DEFAULT_MESSAGE = "Hello, you just try to acceess the MarketPlanner";
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setUp() {
        builder = new UrlBuilder(BASE_URL);
    }

    @Test
    public void testAddParam() throws MalformedURLException, UnsupportedEncodingException {
        builder.addParam(key, parameter);
        assertTrue(builder.getUrl().equals(new URL(BASE_URL + "?" + key + "=" + parameter)));

    }

}

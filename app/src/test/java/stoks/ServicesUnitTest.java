package stoks;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import marketplanner.quotes.IQuoteService;
import marketplanner.quotes.QuoteFetcher;
import marketplanner.quotes.QuoteRequest;
import marketplanner.quotes.QuoteResult;
import marketplanner.quotes.TaskToken;
import marketplanner.stocks.UrlBuilder;
import marketplanner.stocks.Utilities;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ServicesUnitTest {
    private static final String BASE_URL = "http://fakeparams.net";
    private static final String parameter = "fake_param";
    private static UrlBuilder builder;
    private final String key = "fake_key";
    private final String DEFAULT_URL = "http://kqzg9.mocklab.io/market-planner.default/greatsMe";
    private final String DEFAULT_MESSAGE = "Hello, you just try to acceess the MarketPlanner";
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    private static IQuoteService quoteService = mock(IQuoteService.class);
    private static QuoteFetcher quoteFetcher;
    private static final String COMPANY_NAME = "COMPANY_NAME";
    private static final String SYMBOL = "COM";
    private static final  int PRICE = 2342223;

    @BeforeClass
    public static void setUp() throws Exception {
        builder = new UrlBuilder(BASE_URL);
        QuoteResult result = new QuoteResult();
        result.companyName = COMPANY_NAME;
        when(quoteService.getBatchSize()).thenReturn(1);
        when(quoteService.query(any())).thenReturn(Arrays.asList(result));
        quoteFetcher = new QuoteFetcher(quoteService);
    }

    @Test
    public void testAddParam() throws MalformedURLException, UnsupportedEncodingException {
        builder.addParam(key, parameter);
        assertTrue(builder.getUrl().equals(new URL(BASE_URL + "?" + key + "=" + parameter)));

    }

    @Test
    public void testFormatPrice(){
        String s = Utilities.formatPrice(new BigDecimal(PRICE));
        assertTrue(s.equals(String.valueOf(PRICE) + ",00"));
    }

    @Test
    public void testQuoteFetcher() {
        List<QuoteRequest> requests = Arrays.asList(new QuoteRequest("APL", "0-0-0", true));
        TaskToken token =  quoteFetcher.fetch(requests, (res, e, task_id) -> {});
        assertTrue(token.taskAdded() == 1);

    }

}

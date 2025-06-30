package info.vanahub.weather.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.RegistryType;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Provider
@ApplicationScoped
public class RateLimitFilter implements ContainerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry metricRegistry;

    private Counter totalRequests;
    private Counter blockedRequests;

    public RateLimitFilter() {

    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    private void registerMetrics() {
        if (totalRequests == null) {
            totalRequests = metricRegistry.counter("ratelimiter_total_requests");
        }
        if (blockedRequests == null) {
            blockedRequests = metricRegistry.counter("ratelimiter_blocked_requests");
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        registerMetrics();

        totalRequests.inc();

        String clientIP = requestContext.getHeaderString("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = requestContext.getUriInfo().getRequestUri().getHost();
        }

        Bucket bucket = buckets.computeIfAbsent(clientIP, k -> createNewBucket());

        if (!bucket.tryConsume(1)) {
            blockedRequests.inc();
            requestContext.abortWith(Response.status(429).entity("Too many requests - rate limit exceeded").build());
        }
    }
}

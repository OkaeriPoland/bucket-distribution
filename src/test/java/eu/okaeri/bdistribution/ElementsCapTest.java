package eu.okaeri.bdistribution;

import eu.okaeri.bdistribution.impl.ShuffleDistributor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElementsCapTest {

    @Test
    public void testCap_fours() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));
        buckets.add(new Bucket<>(Collections.singletonList("Player5")));
        buckets.add(new Bucket<>(Arrays.asList("Player6", "Player7")));
        buckets.add(new Bucket<>(Arrays.asList("Player8", "Player9")));
        buckets.add(new Bucket<>(Arrays.asList("Player10", "Player11", "Player12", "Player13", "Player14", "Player15", "Player16")));

        List<Bucket<String>> capped = distributor.capAtSizePerBucket(buckets);
        assertEquals(16, capped.stream().mapToInt(Bucket::size).sum());

        for (Bucket<String> bucket : capped) {
            assertTrue(bucket.size() <= 4);
        }
    }
}

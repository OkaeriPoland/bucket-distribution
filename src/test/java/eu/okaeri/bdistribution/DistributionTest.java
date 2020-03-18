package eu.okaeri.bdistribution;

import eu.okaeri.bdistribution.impl.ShuffleDistributor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class DistributionTest {

    @Test
    public void testDistribution_empty() {
        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();
        Distribution<String> distribution = distributor.distribute(buckets);
        assertTrue(distribution.isBalanced());
        assertEquals(0, distribution.getBuckets().size());
    }

    @Test
    public void testDistribution_fours_full_with_cap() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));
        buckets.add(new Bucket<>(Arrays.asList("Player5", "Player15", "Player16")));
        buckets.add(new Bucket<>(Arrays.asList("Player6", "Player7")));
        buckets.add(new Bucket<>(Arrays.asList("Player8", "Player9")));
        buckets.add(new Bucket<>(Arrays.asList("Player10", "Player11", "Player12", "Player13", "Player14")));

        List<Bucket<String>> capped = distributor.capAtSizePerBucket(buckets);
        Distribution<String> distribution = distributor.distribute(capped);
        System.out.println(distribution);

        assertTrue(distribution.isBalanced());
        assertEquals(4, distribution.getBuckets().size());
        IntStream.range(0, 3).forEach(i -> assertEquals(4, distribution.get(i).size()));
    }

    @Test
    public void testDistribution_fours_full() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));
        buckets.add(new Bucket<>(Collections.singletonList("Player5")));
        buckets.add(new Bucket<>(Arrays.asList("Player6", "Player7")));
        buckets.add(new Bucket<>(Arrays.asList("Player8", "Player9")));
        buckets.add(new Bucket<>(Arrays.asList("Player10", "Player11", "Player12")));
        buckets.add(new Bucket<>(Arrays.asList("Player13", "Player14", "Player15", "Player16")));

        Distribution<String> distribution = distributor.distribute(buckets);
        assertTrue(distribution.isBalanced());
        assertEquals(4, distribution.getBuckets().size());
        IntStream.range(0, 3).forEach(i -> assertEquals(4, distribution.get(i).size()));
    }

    @Test
    public void testDistribution_fours_3of4() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));
        buckets.add(new Bucket<>(Collections.singletonList("Player5")));
        buckets.add(new Bucket<>(Arrays.asList("Player6", "Player7")));
        buckets.add(new Bucket<>(Arrays.asList("Player8", "Player9")));
        buckets.add(new Bucket<>(Arrays.asList("Player10", "Player11", "Player12")));

        Distribution<String> distribution = distributor.distribute(buckets);
        assertTrue(distribution.isBalanced());
        assertEquals(3, distribution.getBuckets().size());
        IntStream.range(0, 2).forEach(i -> assertEquals(4, distribution.get(i).size()));
    }

    @Test
    public void testDistribution_fours_unbalanced() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 4);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));
        buckets.add(new Bucket<>(Collections.singletonList("Player5")));
        buckets.add(new Bucket<>(Arrays.asList("Player6", "Player7")));
        buckets.add(new Bucket<>(Arrays.asList("Player8", "Player9")));
        buckets.add(new Bucket<>(Arrays.asList("Player10", "Player11", "Player12")));
        buckets.add(new Bucket<>(Arrays.asList("Player13", "Player14", "Player15")));

        Distribution<String> distribution = distributor.distribute(buckets);
        assertFalse(distribution.isBalanced());
        assertEquals(4, distribution.getBuckets().size());
        IntStream.range(0, 2).forEach(i -> assertEquals(4, distribution.get(i).size()));
        assertEquals(3, distribution.get(3).size());
    }

    @Test
    public void testDistribution_single_full() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 1);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));
        buckets.add(new Bucket<>(Collections.singletonList("Player4")));

        Distribution<String> distribution = distributor.distribute(buckets);
        assertTrue(distribution.isBalanced());
        assertEquals(4, distribution.getBuckets().size());
        IntStream.range(0, 3).forEach(i -> assertEquals(1, distribution.get(i).size()));
    }

    @Test
    public void testDistribution_single_3of4() {

        BucketDistributor<String> distributor = new ShuffleDistributor<>(4, 1);
        List<Bucket<String>> buckets = new ArrayList<>();

        buckets.add(new Bucket<>(Collections.singletonList("Player1")));
        buckets.add(new Bucket<>(Collections.singletonList("Player2")));
        buckets.add(new Bucket<>(Collections.singletonList("Player3")));

        Distribution<String> distribution = distributor.distribute(buckets);
        assertTrue(distribution.isBalanced());
        assertEquals(3, distribution.getBuckets().size());
        IntStream.range(0, 2).forEach(i -> assertEquals(1, distribution.get(i).size()));
    }
}

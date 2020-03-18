Bucket Distribution
---
Distribute groups of elements into equal buckets.

### Example
```java
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
Distribution<String> distribution = distributor.distribute(capped);
//
// Example output
//
// Distribution[
//      buckets=[
//          Bucket[[Player4, Player3, Player8, Player9]],
//          Bucket[[Player6, Player7, Player2, Player1]],
//          Bucket[[Player5, Player15, Player16, Player14]],
//          Bucket[[Player10, Player11, Player12, Player13]]
//      ],
//      balanced=true]
```

### Available implementations:
- ShuffleDistributor: distribution by brute-forcing valid configurations (suitable for small groups of elements) [(detailed description)](https://github.com/OkaeriPoland/bucket-distribution/blob/master/src/main/java/eu/okaeri/bdistribution/impl/ShuffleDistributor.java#L11-L23)

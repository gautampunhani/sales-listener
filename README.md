#Doubts:

1. Pausing after 50 messages, does it have to re-trigger itself? Because #2 requirement says, all messages should be processed.
2. Requirement #5, Do we need to log adjustments applied or also the amount of adjustment for a product type as well?

# Stories created:

1. Record sales in the system.
2. Log Aggregated report of product and it's value.
3. Apply adjustments to sales
4. Pause org.jpchase.codingtest.listener after receiving 50 messages
5. Log report of Adjustment Operators applied

#Assumptions:

1. Aggregated report of products should be for all the sales received till now.
2. Adjustment Operators needs to be applied to all the recorded sales.
3. Aggregated report of Adjustment Operators will be for the current set of 50 messages.
4. All sales messages are in same currency denomination. Here they are `p`.
5. For Doubt #1,Assuming Application will only process 50 messages.
6. For Doubt #2, Assuming only the Adjustments applied need to be logged, not the price that got adjusted.

#Prerequisites

1. Java 8
2. Gradle 2.12

#Build and Test

1. For building `gradle clean build`
2. For running unit tests `gradle clean test`
3. For running simulator `gradle simulate` OR Run `SalesNotificationSimulation`



import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class UserBasedRecommendationSystem {

    public static void main(String[] args) {
        try {
            // 1. Create DataModel: Reads user-item preferences from a file.
            //    The file format should be: userID,itemID,preferenceValue
            DataModel model = new FileDataModel(new File("ratings.csv"));

            // 2. Choose a UserSimilarity algorithm:
            //    Measures similarity between users. PearsonCorrelationSimilarity is common.
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Define a UserNeighborhood:
            //    Identifies "similar" users based on the chosen similarity metric.
            //    ThresholdUserNeighborhood considers users above a certain similarity threshold.
            //    NearestNUserNeighborhood considers the 'N' most similar users.
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model); // Threshold of 0.1, experiment with this value

            // 4. Create the User-Based Recommender:
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 5. Generate Recommendations:
            long userId = 1; // User for whom to generate recommendations
            int numberOfRecommendations = 5; // How many recommendations to get

            System.out.println("Recommendations for User " + userId + ":");
            List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found for user " + userId);
            } else {
                for (RecommendedItem recommendation : recommendations) {
                    System.out.println("  Item: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
                }
            }

            // You can repeat for other users
            long userId2 = 2;
            System.out.println("\nRecommendations for User " + userId2 + ":");
            List<RecommendedItem> recommendations2 = recommender.recommend(userId2, numberOfRecommendations);
            if (recommendations2.isEmpty()) {
                System.out.println("No recommendations found for user " + userId2);
            } else {
                for (RecommendedItem recommendation : recommendations2) {
                    System.out.println("  Item: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

# ItemBasedREcommendationSystem
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity; // Or PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.util.List;

public class ItemBasedRecommendationSystem {

    public static void main(String[] args) {
        try {
            // 1. Create DataModel: Reads user-item preferences from a file.
            DataModel model = new FileDataModel(new File("ratings.csv"));

            // 2. Choose an ItemSimilarity algorithm:
            //    Measures similarity between items. UncenteredCosineSimilarity is often good for item-based.
            ItemSimilarity similarity = new UncenteredCosineSimilarity(model);
            // Alternative: ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Create the Item-Based Recommender:
            ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);

            // 4. Generate Recommendations:
            long userId = 1; // User for whom to generate recommendations
            int numberOfRecommendations = 5; // How many recommendations to get

            System.out.println("Recommendations for User " + userId + ":");
            List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found for user " + userId);
            } else {
                for (RecommendedItem recommendation : recommendations) {
                    System.out.println("  Item: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
                }
            }

            // You can repeat for other users
            long userId2 = 2;
            System.out.println("\nRecommendations for User " + userId2 + ":");
            List<RecommendedItem> recommendations2 = recommender.recommend(userId2, numberOfRecommendations);
            if (recommendations2.isEmpty()) {
                System.out.println("No recommendations found for user " + userId2);
            } else {
                for (RecommendedItem recommendation : recommendations2) {
                    System.out.println("  Item: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

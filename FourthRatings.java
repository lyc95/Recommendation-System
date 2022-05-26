/** 
 * @author (lyc) 
 * @version (25 May 2022)
 */
import java.util.*;
public class FourthRatings {
    
    public double getAverageByID(String id, int minimalRaters)
    {
        int numRaters = 0;
        double sumRatings = 0.0;
        for (Rater rater : RaterDatabase.getRaters())
        {
            double rating = rater.getRating(id);
            if ( rating != -1)
            {
                numRaters++;
                sumRatings += rating;
            }
        }
        
        if (numRaters != 0 && numRaters >= minimalRaters)
        {
            return sumRatings/numRaters;
        }
        else
        {
            return 0.0;
        }
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> res = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movieId : movies)
        {
            double avg = getAverageByID(movieId, minimalRaters);
            if (avg != 0.0)
            {
                res.add(new Rating(movieId, avg));
            }
        }
        return res;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> res = new ArrayList<>();
        for (Rating rating : getAverageRatings(minimalRaters))
        {
            if (filterCriteria.satisfies(rating.getItem()))
            {
                res.add(rating);
            }
        }
        return res;
    }
    
    private double dotProduct(Rater me, Rater r){
        ArrayList<String> itemsRatedByMe = me.getItemsRated();
        double dotProduct = 0;
        for (String id : itemsRatedByMe)
        {
            if (r.hasRating(id))
            {
                dotProduct += (me.getRating(id)-5)*(r.getRating(id)-5);
            }
        }
        return dotProduct;
    }
    
    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> res = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        if (me != null)
        {
            ArrayList<Rater> raters = RaterDatabase.getRaters();
            for (Rater other : raters)
            {
                if (!other.getID().equals(id))
                {
                    //if current rater is not myself
                    double dotProductValue = dotProduct(me, other);
                    if (dotProductValue > 0)
                    {
                        //only add similar positive dotProductValue
                        res.add(new Rating(other.getID(), dotProductValue));
                    }
                }
            }
        }
        return res;
    }
    
    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters){
        return getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> similarRaters = getSimilarities(raterID);
        
        ArrayList<Rating> topSimilarRaters = getTopSimilarRaters(similarRaters, numSimilarRaters);
        
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> moviesWithAvgWeight = new ArrayList<Rating>();
        for (String movieId : movies)
        {
            double weightedAvg = getWeightAvgRatingsBySimilarRaters(movieId, minimalRaters, topSimilarRaters);
            if (weightedAvg != -1.0)
            {
                moviesWithAvgWeight.add(new Rating(movieId, weightedAvg));
            }
        }
        //Sort the movies in descending order based on its weighted ratings by topSimilarRaters
        Collections.sort(moviesWithAvgWeight, Collections.reverseOrder());
        return moviesWithAvgWeight;
    }
    
    private ArrayList<Rating> getTopSimilarRaters(ArrayList<Rating> similarRaters, int numSimilarRaters)
    {
        Collections.sort(similarRaters, Collections.reverseOrder());
        ArrayList<Rating> topSimilarRaters = new ArrayList<Rating>();
        int size = Math.min(numSimilarRaters, similarRaters.size());
        for (int i = 0; i < size; i++)
        {
            topSimilarRaters.add(similarRaters.get(i));
        }
        return topSimilarRaters;
    }
    
    private double getWeightAvgRatingsBySimilarRaters(String id, int minimalRaters, ArrayList<Rating> topSimilarRaters){
        int numRaters = 0;
        double sumRatings = 0.0;
        for (Rating raterWithWeight : topSimilarRaters)
        {
            Rater rater = RaterDatabase.getRater(raterWithWeight.getItem());
            double rating = rater.getRating(id);
            if ( rating != -1)
            {
                numRaters++;
                // add product of rater weight and rating into sum
                sumRatings += rating * raterWithWeight.getValue();
            }
        }
        
        if (numRaters != 0 && numRaters >= minimalRaters)
        {
            return sumRatings/numRaters;
        }
        else
        {
            return -1.0;
        }
    }
}

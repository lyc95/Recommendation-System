import java.util.*;
/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }
    
    public ThirdRatings(String ratingFilename)
    {
        FirstRatings fr = new FirstRatings();
        this.myRaters = fr.loadRaters(ratingFilename);
    }
    

    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public double getAverageByID(String id, int minimalRaters)
    {
        int numRaters = 0;
        double sumRatings = 0.0;
        for (Rater rater : myRaters)
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
}

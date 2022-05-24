/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
import edu.duke.*;
import java.util.*;
public class MovieRunnerAverage {
    
    public void printAverageRatings()
    {
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        System.out.println("The Nos of movies: " + sr.getMovieSize());
        System.out.println("The nos of raters: " + sr.getRaterSize());
        ArrayList<Rating> avgRatings= sr.getAverageRatings(12);
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + sr.getTitle(rating.getItem()));
        }
    }
    
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        String title = "Vacation";
        String id = sr.getID(title);
        if (id.equals("NO SUCH TITLE"))
        {
            System.out.println("NO SUCH TITLE");
        }
        else
        {
            // minimalRaters = 1
            double avg = sr.getAverageByID(id, 1);
            if (avg == 0.0)
            {
                System.out.println("No Ratings yet");
            }
            else
            {
                System.out.println(title + " " + avg);
            }
        }
    }
}
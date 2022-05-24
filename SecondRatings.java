/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
import java.util.*;
public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String movieFilename, String ratingFilename)
    {
        FirstRatings fr = new FirstRatings();
        this.myMovies = fr.loadMovies(movieFilename);
        this.myRaters = fr.loadRaters(ratingFilename);
    }
    
    public int getMovieSize(){
        return myMovies.size();
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
        for (Movie movie : myMovies)
        {
            double avg = getAverageByID(movie.getID(), minimalRaters);
            if (avg != 0.0)
            {
                res.add(new Rating(movie.getID(), avg));
            }
        }
        return res;
    }
    
    public String getTitle(String id)
    {
        for (Movie movie : myMovies)
        {
            if (movie.getID().equals(id))
            {
                return movie.getTitle();
            }
        }
        return "ID NOT FOUND";
    }
    
    public String getID(String title)
    {
        for (Movie movie : myMovies)
        {
            if (movie.getTitle().equals(title))
            {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }
}

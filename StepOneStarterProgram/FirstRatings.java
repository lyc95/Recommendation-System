/**
 * @author (lyc) 
 * @version (23 May 2022)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import javafx.util.*;
public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename)
    {
        ArrayList<Movie> movies = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        for (CSVRecord record : fr.getCSVParser())
        {
            Movie currMovie = new Movie(
                record.get("id"),
                record.get("title"),
                record.get("year"),
                record.get("genre"),
                record.get("director"),
                record.get("country"),
                record.get("poster"),
                Integer.parseInt(record.get("minutes"))
            );
            movies.add(currMovie);
        }
        return movies;
    }
    
    public ArrayList<Rater> loadRaters(String filename)
    {
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        Map<String, Rater> map = new HashMap<>();
        for (CSVRecord record : fr.getCSVParser())
        {
            String raterId = record.get("rater_id");
            String movieId = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            if (!map.containsKey(raterId))
            {
                // create new Rater with rating
                Rater newRater = new Rater(raterId);
                newRater.addRating(movieId, rating);
                map.put(raterId, newRater);
                raters.add(newRater);
            }
            else
            {
                // add new rating to exising rater
                Rater extRater = map.get(raterId);
                extRater.addRating(movieId, rating);
            }
        }
        return raters;
    }
    
    public void testLoadMovies(){
        String filename = "data/ratedmoviesfull.csv";
        ArrayList<Movie> movies = loadMovies(filename);
        System.out.println("The Nos of movies read are " + movies.size());
        System.out.println("The Nos of movies include the Comedy genre: " + getComedyMovies(movies).size());
        System.out.println("The Nos of movies greater than 150 minutes in length: " + getMoviesLongerThan(150, movies).size());
        displayMaxMoviesByAnyDirector(movies);
    }
    
    public void testloadRaters(){
        String filename = "data/ratings.csv";
        ArrayList<Rater> raters = loadRaters(filename);
        System.out.println("The Nos of raters are " + raters.size());
        System.out.println("The Nos of rating for rater(id = 193) :" + getNosOfRatingsByRaterId("193", raters));
        displayRaterWithMaxRating(raters);
        System.out.println("The Nos of rater who rated movie 1798709 is " + getNosOfRatingsByMovieId("1798709", raters));
        System.out.println("The Nos of movies rated are " + getNumRatedMovies(raters));
    }
    
    //Below are helper functions for testing purpose
    private ArrayList<Movie> getMoviesLongerThan(int mins, ArrayList<Movie> movies){
        ArrayList<Movie> res = new ArrayList<>();
        for (Movie movie : movies){
            if (movie.getMinutes() > mins)
            {
                res.add(movie);
            }
        }
        return res;
    }
    
    private ArrayList<Movie> getComedyMovies(ArrayList<Movie> movies){
        ArrayList<Movie> res = new ArrayList<>();
        for (Movie movie : movies){
            if (movie.getGenres().toLowerCase().indexOf("comedy") != -1)
            {
                res.add(movie);
            }
        }
        return res;
    }
    
    private void displayMaxMoviesByAnyDirector(ArrayList<Movie> movies){
        //determine the maximum number of movies by any director, and who the directors are that directed that many movies
        Map<String, Integer> movieCounts = new HashMap<>();
        for (Movie movie : movies){
            String[] directorNames = movie.getDirector().split(",");
            for (String directorName : directorNames)
            {
                movieCounts.put(directorName, movieCounts.getOrDefault(directorName, 0) + 1);
            }
        }

        int maxCount= 0;
        for (String directorName : movieCounts.keySet())
        {
            maxCount = Math.max(maxCount, movieCounts.get(directorName).intValue());
        }
        
        ArrayList<String> directorList = new ArrayList<>();
        for (String directorName : movieCounts.keySet())
        {
            if (movieCounts.get(directorName).intValue() == maxCount){
                directorList.add(directorName);
            }
        }
        System.out.println("Maximum nos of movies by any director is " + maxCount);
        System.out.println("Directors who directed that maximum movies are " + directorList);
    }
    
    private int getNosOfRatingsByRaterId(String rater_id, ArrayList<Rater> raters)
    {
        for (Rater rater : raters)
        {
            if (rater.getID().equals(rater_id))
            {
                return rater.numRatings();
            }
        }
        return -1;
    }
    
    private void displayRaterWithMaxRating(ArrayList<Rater> raters)
    {
        int maxRatingCount = 0;
        for (Rater rater : raters)
        {
            maxRatingCount = Math.max(maxRatingCount, rater.numRatings());
        }
        System.out.println("The max Nos of Ratings are " + maxRatingCount);
        int nosOfRatersWithMaxNumRatings  = 0;
        Rater maxRater = null;
        for (Rater rater : raters)
        {
            if (rater.numRatings() == maxRatingCount)
            {
                nosOfRatersWithMaxNumRatings++;
                maxRater = rater;
                break;
            }
        }
        System.out.println("The Nos of raters with  maximum nos of ratings is " + nosOfRatersWithMaxNumRatings);
        System.out.println("The rater rated the most number of movies is " + maxRater.getID());
    }
    
    private int getNosOfRatingsByMovieId(String movie_id, ArrayList<Rater> raters)
    {
        int res = 0;
        for (Rater rater : raters)
        {
            if (rater.hasRating(movie_id))
            {
                res++;
            }
        }
        return res;
    }
    
    private int getNumRatedMovies(ArrayList<Rater> raters)
    {
        //create set to store rated movie Ids
        Set<String> set = new HashSet<>();
        for (Rater rater : raters)
        {
            ArrayList<String> movieRated = rater.getItemsRated();
            for (String id : movieRated)
            {
                if (!set.contains(id))
                {
                    set.add(id);
                }
            }
        }
        return set.size();
    }
}

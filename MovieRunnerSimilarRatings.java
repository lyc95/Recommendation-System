/**
 * @author (lyc) 
 * @version (25 May 2022)
 */
import java.util.*;
public class MovieRunnerSimilarRatings {
    public void printAverageRatings(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= fr.getAverageRatings(35);
        
        System.out.println("Movies found: " + avgRatings.size());
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1990));
        filters.addFilter(new GenreFilter("Drama"));
        
        ArrayList<Rating> avgRatings= fr.getAverageRatingsByFilter(8, filters);
        System.out.println("Movies found: " + avgRatings.size());
        
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printSimilarRatings(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> moviesWithWeight = fr.getSimilarRatings("71", 10, 3);
        
        for (Rating movie : moviesWithWeight)
        {
            System.out.println(MovieDatabase.getTitle(movie.getItem()) +": "+ movie.getValue());
        }
    }
    
    public void printSimilarRatingsByGenre(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> moviesWithWeight = fr.getSimilarRatingsByFilter("964", 20, 5, new GenreFilter("Mystery"));
        for (Rating movie : moviesWithWeight)
        {
            System.out.println(MovieDatabase.getTitle(movie.getItem()) +": "+ movie.getValue());
        }
    }
    
    public void printSimilarRatingsByDirector(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> moviesWithWeight = fr.getSimilarRatingsByFilter("120", 10, 2, 
            new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"));
        for (Rating movie : moviesWithWeight)
        {
            System.out.println(MovieDatabase.getTitle(movie.getItem()) +": "+ movie.getValue());
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new MinutesFilter(80, 160));
        filters.addFilter(new GenreFilter("Drama"));
        
        ArrayList<Rating> moviesWithWeight = fr.getSimilarRatingsByFilter("168", 10, 3, filters);
        for (Rating movie : moviesWithWeight)
        {
            System.out.println(MovieDatabase.getTitle(movie.getItem()) +": "+ movie.getValue());
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes(){
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        System.out.println("The nos of raters: " + RaterDatabase.size());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new MinutesFilter(70, 200));
        filters.addFilter(new YearAfterFilter(1975));
        
        ArrayList<Rating> moviesWithWeight = fr.getSimilarRatingsByFilter("314", 10, 5, filters);
        for (Rating movie : moviesWithWeight)
        {
            System.out.println(MovieDatabase.getTitle(movie.getItem()) +": "+ movie.getValue());
        }
    }
}

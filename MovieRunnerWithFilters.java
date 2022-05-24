import java.util.*;
/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
public class MovieRunnerWithFilters {
    
    public void printAverageRatings(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= tr.getAverageRatings(35);
        
        System.out.println("Movies found: " + avgRatings.size());
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfter(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(20, new YearAfterFilter(2000));
        
        System.out.println("Movies found: " + avgRatings.size());
        
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(20, new GenreFilter("Comedy"));
        
        System.out.println("Movies found: " + avgRatings.size());
        
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() +  " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByMinutes(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(5, new MinutesFilter(105, 135));
        
        System.out.println("Movies found: " + avgRatings.size());
        
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
        
    }
    
    public void printAverageRatingsByDirectors(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(4, new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"));
        
        System.out.println("Movies found: " + avgRatings.size());
        /*
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() +  " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getDirector(rating.getItem()));
        }*/
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1990));
        filters.addFilter(new GenreFilter("Drama"));
        
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(8, filters);
        System.out.println("Movies found: " + avgRatings.size());
        /*
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getGenres(rating.getItem()));
        }*/
    }
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("The nos of raters: " + tr.getRaterSize());
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("The nos of movies: " + MovieDatabase.size());
        AllFilters filters = new AllFilters();
        filters.addFilter(new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
        filters.addFilter(new MinutesFilter(90, 180));
        ArrayList<Rating> avgRatings= tr.getAverageRatingsByFilter(3, filters);
        System.out.println("Movies found: " + avgRatings.size());
        Collections.sort(avgRatings);
        for (Rating rating : avgRatings)
        {
            System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
}

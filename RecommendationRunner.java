/**
 * @author (lyc) 
 * @version (26 May 2022)
 */
import java.util.*;
import java.util.Random;
public class RecommendationRunner implements Recommender {
    public ArrayList<String> getItemsToRate (){
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<String> list = new ArrayList<>();
        Random rand = new Random();
        while (list.size() < 15)
        {
            int rdnIndex = rand.nextInt(movies.size());
            String movieID = movies.get(rdnIndex);
            if (!list.contains(movieID))
            {
                list.add(movieID);
            }
        }
        return list;
    }
    
    public void printRecommendationsFor (String webRaterID){
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> similarMovies = fr.getSimilarRatings(webRaterID, 20, 5);
        ArrayList<String> suggestedMovies = getSuggestedMoviesBy(similarMovies, webRaterID, 15);
        
        if (suggestedMovies == null || suggestedMovies.size() == 0)
        {
            System.out.println("<p>No recommendation available currently</p>");
        }
        else
        {
            System.out.println("<table><tr><th>Index</th><th>Title</th><th>Year</th><th>Genres</th></tr>");
            for (int i = 0; i < suggestedMovies.size(); i++)
            {
                String movieID = suggestedMovies.get(i);
                System.out.println("<tr><td>" + (i+1) + "</td><td>" + MovieDatabase.getTitle(movieID) + "</td><td>"  + 
                    MovieDatabase.getYear(movieID) + "</td><td>" + MovieDatabase.getGenres(movieID) +  "</td></tr>");
            }
            System.out.println("</table>");
        }
    }
    
    private ArrayList<String> getSuggestedMoviesBy(ArrayList<Rating> suggestedMovies, String webRaterID, int maxNumDisplay)
    {
        ArrayList<String> res = new ArrayList<>();
        Rater me = RaterDatabase.getRater(webRaterID);
        if (maxNumDisplay <= 0 || webRaterID == null || suggestedMovies == null || me == null)
        {
            return res;
        }
        int numDisplay = Math.min(suggestedMovies.size(), maxNumDisplay);
        // index of suggestedMovies
        int index = 0;
        while (res.size() < numDisplay && index < suggestedMovies.size())
        {
            //if the webRater did not rate the suggested movie yet then add into the recommendation list
            String currMovieID = suggestedMovies.get(index++).getItem();
            if (!me.hasRating(currMovieID))
            {
                res.add(currMovieID);
            }
        }
        return res;
    }
}

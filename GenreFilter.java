
/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
public class GenreFilter implements Filter{
    private String myGenre;
    
    public GenreFilter(String genre) {
    	myGenre = genre;
    }
    
    @Override
    public boolean satisfies(String id) {
    	return MovieDatabase.getGenres(id).indexOf(myGenre) != -1;
    }
}

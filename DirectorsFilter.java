/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
public class DirectorsFilter implements Filter{
    private String[] directors;
    
    public DirectorsFilter(String directors) {
        //String input may contain several directors separated by commas 
    	this.directors = directors.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        String directorList = MovieDatabase.getDirector(id);
        for (String director : this.directors)
        {
            //remove space at both start and end
            if (directorList.contains(director.trim()))
            {
                return true;
            }
        }
        return false;
    }
}

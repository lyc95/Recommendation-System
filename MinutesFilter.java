/**
 * @author (lyc) 
 * @version (24 May 2022)
 */
public class MinutesFilter implements Filter{
    private int myMin;
    private int myMax;
    
    public MinutesFilter(int minMins, int maxMins) {
    	myMin = minMins;
    	myMax = maxMins;
    }
    
    @Override
    public boolean satisfies(String id) {
        int mins = MovieDatabase.getMinutes(id);
    	return  mins >= myMin && mins <= myMax;
    }
}

import java.util.*;
import uk.ac.cam.cl.databases.moviedb.MovieDB;
import uk.ac.cam.cl.databases.moviedb.model.*;

public class Exercise3 {
    public static void main(String[] args) {
  // The set of person_id's that we want to count
  // If you want to add id (of type int) to the set
  // simply use pid_set.add(id) 
        Set<Integer> pid_set = new HashSet<Integer>();
        // open database 
        try (MovieDB database = MovieDB.open(args[0])) {
            // Find Kevin Bacon by ID
            Person person = database.getPersonById(3382035);
            // Loop through all the films he's acted in
                for(Role role : person.getActorIn()){
                    // Get Movie and then loop through co-actors
                    Movie movie = database.getMovieById(role.getMovieId());
                    for(CreditActor coactor : movie.getActors()){
                        // If they aren't Kevin Bacon himself then add to set
                        // Only contains unique items so no need to check if already added
                        if(coactor.getPersonId() != person.getId()){
                            pid_set.add(coactor.getPersonId());
                        }
                    }
                }
            System.out.println(pid_set.size());
        }
    }
}

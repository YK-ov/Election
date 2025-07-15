import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionTurn {
    private List<Candidate> candidates = new ArrayList<Candidate>();
    private List<Vote> votes = new ArrayList<>();

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public List<Vote> populate(String path) {
        BufferedReader reader;
        String line = "";
        int lineCounter = 0;
        try {
            reader = new BufferedReader(new FileReader(path));
            while((line = reader.readLine()) != null){
                lineCounter++;
                if (lineCounter > 1) {
                    Vote vote = Vote.fromCsvLine(line);
                    votes.add(vote);
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return votes;
    }



}
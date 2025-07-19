import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Election {
    private List<Candidate> candidates = new ArrayList<>();
    private ElectionTurn firstTurn = new ElectionTurn(candidates);
    private ElectionTurn secondTurn;
    private Candidate winner;


//Utwórz klasę Election, w której zdefiniujesz prywatne pole candidates - listę obiektów Candidate. Dodaj do niej metodę, która zwróci nową listę będącą kopią oryginalnej listy candidates, zachowując jednak te same referencje do obiektów.
    private List<Candidate> copyList() {
        List<Candidate> newCandidates = new ArrayList<>();

        newCandidates.addAll(candidates);
        return newCandidates;
    }

    public Candidate getWinner() {
        return winner;
    }

    public void populateCandidates(String path) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\n");

                for (String candidate : row) {
                    //System.out.printf("%-15s", candidate); -- for printing (reading from csv)
                    candidates.add(new Candidate(candidate));
                }
                //System.out.println(candidates); -- for printing (reading from csv)
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //* -- for testing
    public void printCandidates() {
        for (Candidate candidate : candidates) {
            System.out.println(candidate);
        }
    }
    ///*

    public ElectionTurn getFirstTurn() {
        return firstTurn;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }

    public void populate() throws Exception {
        String path = "src/1.csv";
        String secondPath = "src/2.csv";
        firstTurn.populate(path);
        try {
            winner = firstTurn.winner();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<Candidate> qualifedCandidates = new ArrayList<>();
        qualifedCandidates = firstTurn.runoffCandidates();
        secondTurn = new ElectionTurn(qualifedCandidates);

        List<Vote> votesForSecondTurn = firstTurn.getVotes();

        String line = "";
        BufferedReader reader = null;

        List<Candidate> newCandidates = new ArrayList<>();
        newCandidates.addAll(secondTurn.getCandidates());
        List<Vote> newVotesForCandidate = new ArrayList<>();
        int lineCounter = 0;


        try {
            reader = new BufferedReader(new FileReader(secondPath));
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                if (lineCounter > 1) {
                    List<String> location = new ArrayList<>();  // list and map initializing inside the while loop is necessary! otherwise wont work, wrong data will be saved
                    Map<Candidate, Integer> votesForCandidate = new HashMap<>();
                    String[] row = line.split(",");
                    for (int i = 0; i < row.length; i++) {
                        if (i < 3) {
                            location.add(String.valueOf(row[i]));
                        } else {
                            votesForCandidate.put(newCandidates.get(i - 3), Integer.valueOf(row[i]));
                        }
                    }
                    Vote votesFromLine = new Vote(votesForCandidate, location);
                    newVotesForCandidate.add(votesFromLine);
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        secondTurn.setVotes(newVotesForCandidate);


        //System.out.println(secondTurn.getVotes());
        System.out.println("Second tour:");
        //System.out.println(secondTurn.getVotes() + " from getter");
        winner = secondTurn.winner();
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }
}

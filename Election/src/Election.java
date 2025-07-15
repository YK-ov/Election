import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Election {
    private List<Candidate> candidates = new ArrayList<>();
    private ElectionTurn firstTurn = new ElectionTurn(candidates);
    private ElectionTurn secondTurn;


//Utwórz klasę Election, w której zdefiniujesz prywatne pole candidates - listę obiektów Candidate. Dodaj do niej metodę, która zwróci nową listę będącą kopią oryginalnej listy candidates, zachowując jednak te same referencje do obiektów.
    private List<Candidate> copyList() {
        List<Candidate> newCandidates = new ArrayList<>();

        newCandidates.addAll(candidates);
        return newCandidates;
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
                //System.out.println(); -- for printing (reading from csv)
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

    private void populate() {
        String path = "src/1.csv";
        firstTurn.populate(path);
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }
}

import java.util.*;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans
    private ArrayList<String> humanSelected = new ArrayList<>();
    private ArrayList<String> diffSelected = new ArrayList<>();

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> lowestMolecules = new ArrayList<>();

        Molecule lowestBond = null;
        int lowestBondStrength = Integer.MAX_VALUE;
        for (MolecularStructure humanStructure : humanStructures) {
            Molecule molecule = humanStructure.getMoleculeWithWeakestBondStrength();
            humanSelected.add(molecule.getId());
            lowestMolecules.add(molecule);
            if (molecule != null && molecule.getBondStrength() < lowestBondStrength) {
                lowestBond = molecule;
                lowestBondStrength = molecule.getBondStrength();
            }
        }

        for (MolecularStructure diffStructure : diffStructures) {
            Molecule molecule = diffStructure.getMoleculeWithWeakestBondStrength();
            diffSelected.add(molecule.getId());
            lowestMolecules.add(molecule);
            if (molecule != null && molecule.getBondStrength() < lowestBondStrength) {
                lowestBond = molecule;
                lowestBondStrength = molecule.getBondStrength();
            }
        }

        List<MolecularStructure> allStructers = new ArrayList<>();
        allStructers.addAll(humanStructures);
        allStructers.addAll(diffStructures);

        if (lowestBond != null) {
            for (Molecule molecule : lowestMolecules) {
                if (molecule.getId() != lowestBond.getId()) {
                    double strength = (lowestBondStrength + molecule.getBondStrength()) / 2.0;
                    serum.add(new Bond(lowestBond, molecule, strength));
                }
            }
        }

        return serum;
    }

    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + humanSelected);
        System.out.println("Vitales molecules selected for synthesis: " + diffSelected);
        System.out.println("Synthesizing the serum...");
        double totalStrength = 0;
        for (Bond bond : serum) {
            System.out.println("Forming a bond between " + bond.toString());
            totalStrength += bond.getWeight();
        }
        System.out.println("The total serum bond strength is " + String.format("%.2f", totalStrength));
    }
}

import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }


    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();

        // unordered map id to int
        HashMap<String, Integer> idToIndex = new HashMap<>();
        int index = 0;

        for(Molecule molecule : molecules) {
            boolean found = false;
            if(!idToIndex.containsKey(molecule.getId())) {
                for(String bond : molecule.getBonds()) {
                    if(idToIndex.containsKey(bond)) {
                        found = true;
                        idToIndex.put(molecule.getId(), idToIndex.get(bond));
                        break;
                    }
                }

                if(!found) {
                    idToIndex.put(molecule.getId(), index);
                    for(String bond : molecule.getBonds()) {
                        idToIndex.put(bond, index);
                    }
                    index++;
                }
            }else {
                for(String bond : molecule.getBonds()) {
                    idToIndex.put(bond, idToIndex.get(molecule.getId()));
                }
            }
        }

        for(int i = 0; i < index; i++) {
            structures.add(new MolecularStructure());
        }

        for(Molecule molecule : molecules) {
            structures.get(idToIndex.get(molecule.getId())).addMolecule(molecule);
        }

        return structures;
    }

    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " + (i + 1) + ": " + molecularStructures.get(i).toString());
        }
    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();
        
        for (MolecularStructure targetStructure : targeStructures) {
            boolean isAnomaly = true;
            for (MolecularStructure sourceStructure : sourceStructures) {
                if (sourceStructure.equals(targetStructure)) {
                    isAnomaly = false;
                    break;
                }
            }
            if (isAnomaly) {
                anomalyList.add(targetStructure);
            }
        }
        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals: ");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure.toString());
        }
    }
}

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        //<HumanMolecularData>
        // <Molecule>
        //     <ID>M145</ID>
        //     <BondStrength>21</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M46</MoleculeID>
        //         <MoleculeID>M88</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M46</ID>
        //     <BondStrength>34</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M145</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M88</ID>
        //     <BondStrength>62</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M145</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M518</ID>
        //     <BondStrength>53</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M615</MoleculeID>
        //         <MoleculeID>M49</MoleculeID>
        //         <MoleculeID>M819</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M615</ID>
        //     <BondStrength>32</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M518</MoleculeID>
        //         <MoleculeID>M49</MoleculeID>
        //         <MoleculeID>M819</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M49</ID>
        //     <BondStrength>23</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M615</MoleculeID>
        //         <MoleculeID>M518</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M819</ID>
        //     <BondStrength>98</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M615</MoleculeID>
        //         <MoleculeID>M518</MoleculeID>
        //     </Bonds>
        // </Molecule>
        // <Molecule>
        //     <ID>M564</ID>
        //     <BondStrength>44</BondStrength>
        //     <Bonds>
        //         <MoleculeID>M131</MoleculeID>
        //         <MoleculeID>M292</MoleculeID>
        //         <MoleculeID>M532</MoleculeID>
        //         <MoleculeID>M191</MoleculeID>
        //     </Bonds>
        // </Molecule>
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            List<Molecule> humanMolecules = new ArrayList<>();
            Node humanMolecularData = doc.getElementsByTagName("HumanMolecularData").item(0);
            NodeList humanMoleculesNodes = ((Element) humanMolecularData).getElementsByTagName("Molecule");
            for (int i = 0; i < humanMoleculesNodes.getLength(); i++) {
                Node molecule = humanMoleculesNodes.item(i);
                if (molecule.getNodeType() == Node.ELEMENT_NODE) {
                    Element moleculeElement = (Element) molecule;
                    String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
                    NodeList bonds = moleculeElement.getElementsByTagName("MoleculeID");
                    List<String> bondList = new ArrayList<>();
                    for (int j = 0; j < bonds.getLength(); j++) {
                        bondList.add(bonds.item(j).getTextContent());
                    }
                    Molecule newMolecule = new Molecule(id, bondStrength, bondList);
                    humanMolecules.add(newMolecule);
                }
            }

            List<Molecule> vitalesMolecules = new ArrayList<>();
            Node vitalesMolecularData = doc.getElementsByTagName("VitalesMolecularData").item(0);
            NodeList vitalesMoleculesNodes = ((Element) vitalesMolecularData).getElementsByTagName("Molecule");
            for (int i = 0; i < vitalesMoleculesNodes.getLength(); i++) {
                Node molecule = vitalesMoleculesNodes.item(i);
                if (molecule.getNodeType() == Node.ELEMENT_NODE) {
                    Element moleculeElement = (Element) molecule;
                    String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                    int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
                    NodeList bonds = moleculeElement.getElementsByTagName("MoleculeID");
                    List<String> bondList = new ArrayList<>();
                    for (int j = 0; j < bonds.getLength(); j++) {
                        bondList.add(bonds.item(j).getTextContent());
                    }
                    Molecule newMolecule = new Molecule(id, bondStrength, bondList);
                    vitalesMolecules.add(newMolecule);
                }
            }

            molecularDataHuman = new MolecularData(humanMolecules); // Create a new MolecularData object with the human molecules
            molecularDataVitales = new MolecularData(vitalesMolecules); // Create a new MolecularData object with the Vitales molecules
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
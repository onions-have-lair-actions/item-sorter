import java.io.*;
import java.util.*;

public class Sorter {
    public static void main(String[] args) {
        ArrayList<String> items = new ArrayList<>();
        ArrayList<String> itemMultis = new ArrayList<>();
        ArrayList<String> itemsOut = new ArrayList<>();

        try (FileReader f = new FileReader("items.txt");
             BufferedReader b = new BufferedReader(f)) {

            String line = b.readLine();
            String[] lineData = line.split(",");

            for (int i = 0; i < lineData.length; i++) {
                int amount = 0;
                if (!lineData[i].contains("Mystery")) {
                    int indexOfX = lineData[i].lastIndexOf(" x");
                    if (indexOfX > 5) {
                        amount = Integer.parseInt(lineData[i].substring(indexOfX + 2));
                        lineData[i] = lineData[i].substring(0, indexOfX + 1);
                    }
                }//end of if not Mystery Box item

                lineData[i] = lineData[i].trim();

                while (amount - 1 > 0) {
                    itemMultis.add(lineData[i]);
                    amount--;
                }
            }//end of for loop lineData

            items.addAll(Arrays.asList(lineData));
            items.addAll(itemMultis);

            Map<String, Integer> occurs = new HashMap<>();
            int index = 0;

            for (String item : items) {
                Integer j = occurs.get(item);
                occurs.put(item, (j == null) ? 1 : j + 1);
            }//end of for counting number of occurrences for each item

            int occursSize = occurs.size();

            for (int i = 0; i < occursSize; i++) {
                itemsOut.add("");
            }

            for (Map.Entry<String, Integer> val : occurs.entrySet()) {
                if (val.getValue() == 1) {
                    itemsOut.set(index, val.getKey());
                } else {
                    itemsOut.set(index, val.getKey() + " x" + val.getValue());
                }
                index++;
            }
            occurs.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(itemsOut);

        int indexBag = -1;
        int indexBox = -1;

        for (int i = 0; i < itemsOut.size(); i++) {
            if(itemsOut.get(i).startsWith("B") || itemsOut.get(i).startsWith(" B")) {
                indexBag = i;
            }
        }

        for (int i = 0; i < itemsOut.size(); i++) {
            if(itemsOut.get(i).startsWith("My")) {
                indexBox = i;
            }
        }


        if (indexBag != -1) {
            itemsOut.remove(indexBag);
            itemsOut.add(0, "Bag of Holding");
        }

        if (indexBox != -1) {
            String mB = itemsOut.remove(indexBox);
            itemsOut.add(itemsOut.size(), mB);
        }

        try (FileWriter f = new FileWriter("itemsO.txt");
             BufferedWriter b = new BufferedWriter(f)) {

            for (String item : itemsOut) {
                b.write(item + ", ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }//end of try-catch for writing
    }//end of main
}//end of class

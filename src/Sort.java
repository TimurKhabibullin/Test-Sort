import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Sort {
    static List<Integer> inListInt = new ArrayList<>();
    static List<String> inListStr = new ArrayList<>();
    static List<Integer> outListInt = new ArrayList<>();
    static List<String> outListStr = new ArrayList<>();
    static List<String> fileSource = new ArrayList<>();
    static List<String> separatedParameters = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        launch();
    }

    public static void launch() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String parameters = scanner.nextLine();
        getParametersAndFileSource(parameters);
        if (fileSource.size()>1){
        fileRead();
        if (separatedParameters.contains("-i")){
            outListInt = arrayToListInteger(mergeSortForInteger(listToIntArray(inListInt)));
        } else if (separatedParameters.contains("-s")){
            outListStr = arrayToListString(mergeSortForString(listToStringArray(inListStr)));
        }
        if (separatedParameters.contains("-d")){
            Collections.reverse(outListInt);
            Collections.reverse(outListStr);
        }
        fileWrite();
        }
    }

    public static void getParametersAndFileSource(String parameters){
        StringBuilder parameter = new StringBuilder();
        for (int i = 0; i < parameters.length(); i++){
            if (parameters.charAt(i) != ' '){
                parameter.append(parameters.charAt(i));
            }
            if (parameters.charAt(i) == ' ' || i == parameters.length() - 1){
                if (!parameter.toString().equals("-a") && !parameter.toString().equals("-d") &&
                        !parameter.toString().equals("-s") && !parameter.toString().equals("-i")){
                    fileSource.add(parameter.toString());
                } else {
                    separatedParameters.add(parameter.toString());
                }
                parameter.setLength(0);
            }
        }
    }

    public static void fileRead(){
        String fileName = "";
        int counterFileSource = 1;
        if (separatedParameters.contains("-i")){
            while (fileSource.size() > 1) {
                fileName = fileSource.get(counterFileSource);
                try (Scanner scan = new Scanner(new File(fileName))) {
                    int lineCounter = 1;
                    while (scan.hasNextLine()) {
                        try {
                            inListInt.add(Integer.parseInt(scan.nextLine()));
                        } catch (Exception e){
                            System.out.println("File - " + fileName + " contains invalid data, on line: " + lineCounter);
                        }
                        lineCounter++;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File" + fileName + " not found");
                }
                fileSource.remove(counterFileSource);
            }
        } else if (separatedParameters.contains("-s")){
            while (fileSource.size() > 1) {
                fileName = fileSource.get(counterFileSource);
                try (Scanner scan = new Scanner(new File(fileName))) {
                    int lineCounter = 1;
                    while (scan.hasNextLine()) {
                        String str = scan.nextLine();
                        String str2 = str.replaceAll("\\s+","");
                        if (str.equals(str2)){
                            inListStr.add(str);
                        } else {
                            System.out.println("File - " + fileName + " contains invalid data, on line: " + lineCounter);
                        }
                        lineCounter++;
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File" + fileName + " not found");
                }
                fileSource.remove(counterFileSource);
            }
        }
    }

    public static void fileWrite() throws IOException {
        if (separatedParameters.contains("-i")) {
            FileWriter nFile = new FileWriter(fileSource.get(0));
            for (int i = 0; i < outListInt.size(); i++) {
                nFile.write((int) outListInt.get(i) + " ");
            }
            nFile.close();
        }
        if (separatedParameters.contains("-s")){
            FileWriter nFile = new FileWriter(fileSource.get(0));
            for (int i = 0; i < outListStr.size(); i++) {
                nFile.write(outListStr.get(i) + " ");
            }
            nFile.close();
        }
    }

    public static int[] mergeSortForInteger(int[] sortArr) {
        int[] buffer1 = Arrays.copyOf(sortArr, sortArr.length);
        int[] buffer2 = new int[sortArr.length];
        int[] result = mergeSortInner(buffer1, buffer2, 0, sortArr.length);
        return result;
    }

    public static int[] mergeSortInner(int[] buffer1, int[] buffer2, int startIndex, int endIndex) {
        if (startIndex >= endIndex - 1) {
            return buffer1;
        }

        int middle = startIndex + (endIndex - startIndex) / 2;
        int[] sorted1 = mergeSortInner(buffer1, buffer2, startIndex, middle);
        int[] sorted2 = mergeSortInner(buffer1, buffer2, middle, endIndex);

        int index1 = startIndex;
        int index2 = middle;
        int destIndex = startIndex;
        int[] result = sorted1 == buffer1 ? buffer2 : buffer1;
        while (index1 < middle && index2 < endIndex) {
            result[destIndex++] = sorted1[index1] < sorted2[index2]
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            result[destIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            result[destIndex++] = sorted2[index2++];
        }
        return result;
    }

    public static String[] mergeSortInner(String[] buffer1, String[] buffer2, int startIndex, int endIndex) {
        if (startIndex >= endIndex - 1) {
            return buffer1;
        }

        int middle = startIndex + (endIndex - startIndex) / 2;
        String[] sorted1 = mergeSortInner(buffer1, buffer2, startIndex, middle);
        String[] sorted2 = mergeSortInner(buffer1, buffer2, middle, endIndex);

        int index1 = startIndex;
        int index2 = middle;
        int destIndex = startIndex;
        String[] result = sorted1 == buffer1 ? buffer2 : buffer1;
        while (index1 < middle && index2 < endIndex) {
            result[destIndex++] = 0 > (sorted1[index1].compareTo(sorted2[index2]))
                    ? sorted1[index1++] : sorted2[index2++];
        }
        while (index1 < middle) {
            result[destIndex++] = sorted1[index1++];
        }
        while (index2 < endIndex) {
            result[destIndex++] = sorted2[index2++];
        }
        return result;
    }

    public static String[] mergeSortForString(String[] sortArr){
        String[] buffer1 = Arrays.copyOf(sortArr, sortArr.length);
        String[] buffer2 = new String[sortArr.length];
        String[] result = mergeSortInner(buffer1, buffer2, 0, sortArr.length);
        return result;
    }

    public static int[] listToIntArray(List<Integer> list){
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++){
            result[i] = list.get(i);
        }
        return result;
    }

    public static String[] listToStringArray(List<String> list){
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            result[i] = list.get(i);
        }
        return result;
    }

    public static List<Integer> arrayToListInteger(int[] arr){
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++){
            result.add(arr[i]);
        }
        return result;
    }

    public static List<String> arrayToListString(String[] arr) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
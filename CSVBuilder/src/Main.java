public class Main {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments");
            return;
        }
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        try {
            CSVBuilder csvBuilder = new CSVBuilder(inputFilePath, outputFilePath);
            csvBuilder.createFile();
        } catch (Exception e) {
            System.err.println("Failed to create CSV file: " + e.getLocalizedMessage());
        }
    }
}

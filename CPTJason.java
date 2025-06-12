import arc.*; // Import ARC library for graphics and console interaction
import java.awt.*; // Import AWT for color and graphics
import java.awt.image.*; // Import image handling
import java.util.Random; // Import Random for randomization

public class CPTJason {
    public static void main(String[] args) {
        Console con = new Console(1280, 720); // Create console with resolution 1280x720
        
        // Course: ICS3U1c Computer Science CPT
        // Teacher: Mr. Alfred Ron Cadawas
        // Student: Jason Guan
        // CPT Name: Multiple Choice Game
        // Version: MondayleftmebrokenV12 
        // Included Standard Features:
        //      1. if/else
        //      2. while loops
        //      3. for loops
        //      4. methods
        //      5. file reading and file writing
        //      6. arrays
        //
        // Included Extra Features:
        //      1. imports and draws images
        //      2. Secret Screen (Press J) 
        //      3. (h)elp option
        //      4. console window is 1280x720 with the title
        //      5. cheat player's name increase the score by 2 times

        // Load background and screen images
        BufferedImage imgStartBKG = con.loadImage("Start.png");
        BufferedImage imgHomePageBKG = con.loadImage("Homepage.png");
        BufferedImage imgWinPageBKG = con.loadImage("Win.png");
        BufferedImage imgLosePageBKG = con.loadImage("Lose.png");
        BufferedImage imgChoice1BKG = con.loadImage("Choice1.png");
        BufferedImage imgChoice2BKG = con.loadImage("Choice2.png");
        BufferedImage imgChoice3BKG = con.loadImage("Choice3.png");
        BufferedImage imgUsernameBKG = con.loadImage("Username.png");
        BufferedImage imgHelpBKG = con.loadImage("Help.png");
        BufferedImage imgScoreBKG = con.loadImage("Score.png");
        BufferedImage imgQuizchoiceBKG = con.loadImage("Quizchoice.png");
        BufferedImage imgJokesBKG = con.loadImage("Jokes.png");

        // Declare game variables
        String strName = "";
        int intScore = 0;
        String strAnswer = "";
        String strCheat = "statitan"; // Cheat code username
        int intQuizchoice = 0;
        String strquizName = "";

        boolean running = true; // Controls main game loop
        Random random = new Random(); // Random number generator

        while (running) {
            con.setTextColor(Color.BLACK); // Set text color
            con.setDrawColor(Color.BLACK); // Set draw color
            con.clear(); // Clear screen
            con.drawImage(imgHomePageBKG, 0, 0); // Show home page background
            con.println("Try pressing J"); // Easter egg hint
            String strInput = con.readLine(); // Read user input

            if (strInput.equalsIgnoreCase("p")) {
                // Player starts the game
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgUsernameBKG, 0, 0); // Display username input background
                con.println("\n\n\n\n\n\n\n\n\n\n");
                con.print("                                          ");
                strName = con.readLine(); // Get player name

                while (strName.equals("")) {
                    con.println("Username cannot be blank. Try again:");
                    strName = con.readLine(); // Force user to enter name
                }

                intScore = 0;
                if (strName.equalsIgnoreCase(strCheat)) {
                    intScore = 2; // Enable cheat starting score
                    con.println("Cheat enabled! Starting score: " + intScore);
                }

                // Load quiz choices from file
                String[] strQuizchoices = new String[3];
                TextInputFile file = new TextInputFile("Quizchoice.txt");
                for (int i = 0; i < 3; i++) {
                    strQuizchoices[i] = file.readLine();
                }
                file.close();

                // Show quiz options
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgQuizchoiceBKG, 0, 0);
                con.println("Please choose a quiz to complete:");
                for (int i = 0; i < 3; i++) {
                    con.println((i + 1) + ") " + strQuizchoices[i]);
                }
                intQuizchoice = con.readInt(); // Get user quiz choice
                while (intQuizchoice < 1 || intQuizchoice > 3) {
                    con.println("Invalid choice. Please enter 1, 2, or 3:");
                    intQuizchoice = con.readInt();
                }
                strquizName = strQuizchoices[intQuizchoice - 1]; // Save selected quiz name

                // Select quiz file based on choice
                String selectedQuizFile = "";
                if (intQuizchoice == 1) {
                    selectedQuizFile = "Calc.txt";
                    con.drawImage(imgChoice1BKG, 0, 0);
                } else if (intQuizchoice == 2) {
                    selectedQuizFile = "Vectors.txt";
                    con.drawImage(imgChoice2BKG, 0, 0);
                } else if (intQuizchoice == 3) {
                    selectedQuizFile = "Both.txt";
                    con.drawImage(imgChoice3BKG, 0, 0);
                }

                // Count number of questions
                con.clear();
                TextInputFile quiz = new TextInputFile(selectedQuizFile);
                int intNumQuestions = 0;
                while (!quiz.eof()) {
                    quiz.readLine(); // Read lines to count
                    intNumQuestions++;
                }
                quiz.close();
                intNumQuestions /= 6; // Each question has 6 lines

                // Load questions into array
                String[][] strQuiz = new String[intNumQuestions][7];
                TextInputFile quiz2 = new TextInputFile(selectedQuizFile);
                for (int i = 0; i < intNumQuestions; i++) {
                    for (int j = 0; j < 6; j++) {
                        strQuiz[i][j] = quiz2.readLine(); // Read question data
                    }
                    strQuiz[i][6] = String.valueOf(random.nextInt(100) + 1); // Add random ID
                }
                quiz2.close();

                // Shuffle questions randomly
                for (int i = 0; i < intNumQuestions; i++) {
                    int randIndex = random.nextInt(intNumQuestions);
                    String[] temp = strQuiz[i];
                    strQuiz[i] = strQuiz[randIndex];
                    strQuiz[randIndex] = temp;
                }

                // Ask questions one by one
                for (int i = 0; i < intNumQuestions; i++) {
                    con.setDrawColor(Color.BLACK);
                    con.clear();
                    con.print("User: " + strName);
                    con.print("   Test: " + strquizName);
                    con.print("   Score: " + intScore + "/" + i);
                    int intBScore = intPercentageScore(intScore, i); // Call percentage method
                    con.println("  Percentage score: " + intBScore + "%");

                    // Display question and options
                    con.println("Q" + (i + 1) + ": " + strQuiz[i][0]);
                    con.println("A) " + strQuiz[i][1]);
                    con.println("B) " + strQuiz[i][2]);
                    con.println("C) " + strQuiz[i][3]);
                    con.println("D) " + strQuiz[i][4]);
                    con.println("(ID: " + strQuiz[i][6] + ")");
                    con.print("Enter your answer (A/B/C/D): ");
                    strAnswer = con.readLine().toUpperCase();

                    while (!strAnswer.equals("A") && !strAnswer.equals("B") &&
                           !strAnswer.equals("C") && !strAnswer.equals("D")) {
                        con.print("Invalid answer. Please enter A, B, C, or D: ");
                        strAnswer = con.readLine().toUpperCase();
                    }

                    if (strAnswer.equals(strQuiz[i][5].toUpperCase())) {
                        intScore++; // Add to score if correct
                    }
                }

                // Post-game menu
                boolean postGame = true;
                while (postGame) {
                    con.setDrawColor(Color.BLACK);
                    con.clear();
                    con.drawImage(imgScoreBKG, 0, 0);
                    con.print(strName + ", your final score is: " + intScore + "/" + intNumQuestions);
                    int intPercentage = intFinalScore(intScore, intNumQuestions); // Call final score method
                    con.println(" or " + intPercentage + "%");
                    con.println();
                    con.println("Press M to return to the main menu and save your score.");
                    con.println("Press E to exit without saving.");

                    String postGameInput = con.readLine(); // Get post-game input

                    if (postGameInput.equalsIgnoreCase("M")) {
                        TextOutputFile writer = new TextOutputFile("HighScore.txt", true);
                        writer.println(strName + "," + strquizName + "," + intPercentage); // Save score
                        writer.close();
                        con.println("Score saved! Returning to main menu...");
                        con.sleep(1500);
                        postGame = false;
                    } else if (postGameInput.equalsIgnoreCase("E")) {
                        con.println("Thanks for playing!");
                        con.sleep(1500);
                        running = false;
                        postGame = false;
                    } else {
                        con.println("Invalid input. Please try again.");
                        con.sleep(1000);
                    }
                }

            } else if (strInput.equalsIgnoreCase("v")) {
                // View high scores
                TextInputFile scoreReader = new TextInputFile("HighScore.txt");

                String[] names = new String[100];
                String[] quizzes = new String[100];
                int[] scores = new int[100];
                int count = 0;

                while (!scoreReader.eof()) {
                    String line = scoreReader.readLine();
                    if (line != null && !line.equals("")) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            names[count] = parts[0];
                            quizzes[count] = parts[1];
                            try {
                                scores[count] = Integer.parseInt(parts[2]); // Convert score to int
                            } catch (NumberFormatException e) {
                                scores[count] = 0;
                            }
                            count++;
                        }
                    }
                }
                scoreReader.close();

                // Sort high scores (bubble sort)
                for (int i = 0; i < count - 1; i++) {
                    for (int j = 0; j < count - i - 1; j++) {
                        if (scores[j] < scores[j + 1]) {
                            int tempScore = scores[j];
                            scores[j] = scores[j + 1];
                            scores[j + 1] = tempScore;

                            String tempName = names[j];
                            names[j] = names[j + 1];
                            names[j + 1] = tempName;

                            String tempQuiz = quizzes[j];
                            quizzes[j] = quizzes[j + 1];
                            quizzes[j + 1] = tempQuiz;
                        }
                    }
                }

                // Display top 10 scores
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.println("=== High Scores ===");
                for (int i = 0; i < count && i < 10; i++) {
                    con.println((i + 1) + ". " + names[i] + " | " + quizzes[i] + " | " + scores[i]);
                }
                con.println();
                con.println("Press any key to return to main menu.");
                con.readLine();

            } else if (strInput.equalsIgnoreCase("h")) {
                // Help screen
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgHelpBKG, 0, 0);
                con.println("\n\n\n\n\n\n\n");
                con.println("Help Section:");
                con.println("- Enter your username to begin.");
                con.println("- Choose a quiz topic.");
                con.println("- Answer each question (A/B/C/D).");
                con.println("- Use 'statitan' as a cheat code for bonus points!");
                con.println();
                con.println("Press any key to return to main menu.");
                con.readLine();

            } else if (strInput.equalsIgnoreCase("e")) {
                // Exit game
                con.setDrawColor(Color.BLACK);
                con.println("Thanks for playing!");
                running = false;

            } else if (strInput.equalsIgnoreCase("j")) {
                // Secret screen (Jokes)
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgJokesBKG, 0, 0);
                con.println("Press any key to return to main menu.");
                con.readLine();

            } else {
                // Invalid input
                con.setDrawColor(Color.BLACK);
                con.println("Invalid choice. Restart the program.");
                con.sleep(1500);
            }
        }

        con.closeConsole(); // Closes the console when done
    }

    public static int intPercentageScore(int intScore, int i) {
        // Calculates running score percentage
        int intBScore = 0;
        if (i != 0) {
            intBScore = (intScore * 100) / i;
        }
        return intBScore;
    }

    public static int intFinalScore(int intScore, int intNumQuestions) {
        // Calculates final score percentage
        int intPercentage = 0;
        if (intNumQuestions != 0) {
            intPercentage = (intScore * 100) / intNumQuestions;
        }
        return intPercentage;
    }
}

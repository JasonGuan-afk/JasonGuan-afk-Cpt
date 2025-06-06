import arc.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Random;

public class CPTJason {
    public static void main(String[] args) {
        Console con = new Console(1280, 720);

        // Load images
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


        String strName = "";
        int intScore = 0;
        String strAnswer = "";
        String strCheat = "statitan";
        int intQuizchoice = 0;
        String quizName = "";

        boolean running = true;

        while (running) {
			con.setTextColor(Color.BLACK);
            con.setDrawColor(Color.BLACK);
            con.clear();
            con.drawImage(imgHomePageBKG, 0, 0);
            con.println("Try pressing J");
            String strInput = con.readLine();

            if (strInput.equalsIgnoreCase("p")) {
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgUsernameBKG, 0, 0);
                con.println("\n\n\n\n\n\n\n\n\n\n");
                con.print("                                          ");
                strName = con.readLine();
                while (strName.equals("")) {
                    con.println("Username cannot be blank. Try again:");
                    strName = con.readLine();
                }

                intScore = 0;
                if (strName.equalsIgnoreCase(strCheat)) {
                    intScore = 2;
                    con.println("Cheat enabled! Starting score: " + intScore);
                }

                String[] strQuizchoices = new String[3];
                TextInputFile file = new TextInputFile("Quizchoice.txt");
                for (int i = 0; i < 3; i++) {
                    strQuizchoices[i] = file.readLine();
                }
                file.close();

                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgQuizchoiceBKG, 0, 0);
                con.println("Please choose a quiz to complete:");
                for (int i = 0; i < 3; i++) {
                    con.println((i + 1) + ") " + strQuizchoices[i]);
                }
                intQuizchoice = con.readInt();
                while (intQuizchoice < 1 || intQuizchoice > 3) {
                    con.println("Invalid choice. Please enter 1, 2, or 3:");
                    intQuizchoice = con.readInt();
                }
                quizName = strQuizchoices[intQuizchoice - 1];

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

                con.clear();
                TextInputFile quiz = new TextInputFile(selectedQuizFile);
                int intNumQuestions = 0;
                while (!quiz.eof()) {
                    quiz.readLine();
                    intNumQuestions++;
                }
                quiz.close();
                intNumQuestions /= 6;

                String[][] strQuiz = new String[intNumQuestions][6];
                TextInputFile quiz2 = new TextInputFile(selectedQuizFile);
                for (int i = 0; i < intNumQuestions; i++) {
                    for (int j = 0; j < 6; j++) {
                        strQuiz[i][j] = quiz2.readLine();
                    }
                }
                quiz2.close();

                // Shuffle questions
                Random random = new Random();
                for (int i = 0; i < intNumQuestions; i++) {
                    int randIndex = random.nextInt(intNumQuestions);
                    String[] temp = strQuiz[i];
                    strQuiz[i] = strQuiz[randIndex];
                    strQuiz[randIndex] = temp;
                }

                for (int i = 0; i < intNumQuestions; i++) {
                    con.setDrawColor(Color.BLACK);
                    con.clear();
                    con.println("Q" + (i + 1) + ": " + strQuiz[i][0]);
                    con.println("A) " + strQuiz[i][1]);
                    con.println("B) " + strQuiz[i][2]);
                    con.println("C) " + strQuiz[i][3]);
                    con.println("D) " + strQuiz[i][4]);
                    con.print("Enter your answer (A/B/C/D): ");
                    strAnswer = con.readLine().toUpperCase();

                    while (!strAnswer.equals("A") && !strAnswer.equals("B") && !strAnswer.equals("C") && !strAnswer.equals("D")) {
                        con.print("Invalid answer. Please enter A, B, C, or D: ");
                        strAnswer = con.readLine().toUpperCase();
                    }

                    if (strAnswer.equals(strQuiz[i][5].toUpperCase())) {
                        intScore++;
                    }
                }

                boolean postGame = true;
                while (postGame) {
                    con.setDrawColor(Color.BLACK);
                    con.clear();
                    con.drawImage(imgScoreBKG, 0, 0);
                    con.println(strName + ", your final score is: " + intScore + "/" + intNumQuestions);
                    con.println();
                    con.println("Press M to return to the main menu and save your score.");
                    con.println("Press E to exit without saving.");

                    String postGameInput = con.readLine();

                    if (postGameInput.equalsIgnoreCase("M")) {
                        // Save score
                        TextOutputFile writer = new TextOutputFile("HighScore.txt", true);
                        writer.println(strName + "," + quizName + "," + intScore);
                        writer.close();
                        con.println("Score saved! Returning to main menu...");
                        con.sleep(1500);
                        postGame = false; // return to main menu
                    } else if (postGameInput.equalsIgnoreCase("E")) {
                        con.println("Thanks for playing!");
                        con.sleep(1500);
                        running = false; // exit program
                        postGame = false;
                    } else {
                        con.println("Invalid input. Please try again.");
                        con.sleep(1000);
                    }
                }

            } else if (strInput.equalsIgnoreCase("v")) {
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
                                scores[count] = Integer.parseInt(parts[2]);
                            } catch (NumberFormatException e) {
                                scores[count] = 0;
                            }
                            count++;
                        }
                    }
                }
                scoreReader.close();

                // Bubble sort by score descending
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
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgHelpBKG, 0, 0);
                con.println("Help Section:");
                con.println("- Enter your username to begin.");
                con.println("- Choose a quiz topic.");
                con.println("- Answer each question (A/B/C/D).");
                con.println("- Use 'statitan' as a cheat code for bonus points!");
                con.println();
                con.println("Press any key to return to main menu.");
                con.readLine();

            } else if (strInput.equalsIgnoreCase("e")) {
                con.setDrawColor(Color.BLACK);
                con.println("Thanks for playing!");
                running = false;

            } else if (strInput.equalsIgnoreCase("j")) {
                con.setDrawColor(Color.BLACK);
                con.clear();
                con.drawImage(imgJokesBKG, 0, 0);
                con.println("Press any key to return to main menu.");
                con.readLine();

            } else {
                con.setDrawColor(Color.BLACK);
                con.println("Invalid choice. Restart the program.");
                con.sleep(1500);
            }
        }
    }
}

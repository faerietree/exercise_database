package aufgaben_db;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;

// TODO use JEuclid instead. (scilab jlatexmath was introduced only temporarily).
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import converter.Converter;


/**
 * Utility for processing LaTeX files.
 *
 * @author Schweiner, Artijom, J.R.I.B., worlddevelopment
 *
 */
public class LatexCutter {

/**
 * Cuts the given LaTeX sheetdraft with the help of a stored
 * DeclarationSet.
 *
 * @param sheetdraft
 * @return ArrayList of the split result (exercises) with header
 * and \begin | \end{document}, and in theory directly compilable.
 * @throws IOException
 */
	public static ArrayList<Exercise> cutExercises(Sheetdraft sheetdraft) throws IOException {

		System.out.println("LatexCutter was called.");

		String headermixture = ""; // to be determined
//		ArrayList<String> allLines = new ArrayList<String>();
		String[] allTexLines = sheetdraft.getRawContent();//getTexText();
		int indexOfFirstIdentifier = 0;
		int indexOfLastIdentifier = 0 ;
		int indexOfBeginDoc = 0;
//		File cutExercise;
		int indexOfFirstCut = 0;
		int indexOfLastCut = 0;
		boolean lastIdentifierFound = false;


		// ArrayList der zu schneidenen Aufgaben
		ArrayList<Exercise> outputTexExercises = new ArrayList<Exercise>();

		// Have no declarations been found in the sheet?.
		if (sheetdraft.getDeclarationSet().declarations.size() == 0) {
			return outputTexExercises;
		}

		// Using the content part heads create a list with the line
		// numbers at which to cut.
		ArrayList<Integer> linesToCut = new ArrayList<Integer>();

		HashMap<Integer, Declaration> lineDecReference
			= new HashMap<Integer, Declaration>();
		int ex_count_and_pos = 0;
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
				for (Declaration dec
						: sheetdraft.getDeclarationSet().declarations) {

					if (!dec.hasHead()) {
						continue;
					}
					//else

					if (dec.getHead() != null && singleLine != null) {
						System.out.println("Examining head: "
								+ dec.getHead().toString());

						if (singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[2])
								||
								singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[3])
								||
								singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[2])
								&& singleLine.contains(dec.getHead()[3])
								||
								singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[2])
								&& singleLine.contains(dec.getHead()[3])
								) {

							System.out.println("Found head: " + dec.getHead());
							Integer indexOfCut = i;
							while (!allTexLines[indexOfCut]
									.startsWith("\\begin")) {
								indexOfCut--;
							}
							System.out.println("Cut at line " + indexOfCut);
							linesToCut.add(indexOfCut);
							lineDecReference.put(indexOfCut, dec);
					}
				}
			}
		}

		// Read line with "\begin{document}"
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
			if (singleLine.startsWith("\\begin{docum")) {
				indexOfBeginDoc = i;
				System.out.println("begin{docum... found at line: "
						+ indexOfBeginDoc);
			}
		}
		// And store the header: (inclusive begin document)
		ArrayList<String> header = new ArrayList<String>();
		for (int i = 0; i < indexOfBeginDoc + 1; i++) {
			header.add(allTexLines[i]);
		}


		// Create an exercise from the content inbetween two heads.
		// Prepends and appends the header to every found exercise.
		// Store the found content parts.
		for (int i = 0; i < linesToCut.size() - 1; i++) {
			ArrayList<String> cutExercise = new ArrayList<String>();
			// cut from current cut index to the next cut index.
			for (int j = linesToCut.get(i); j < linesToCut.get(i + 1); j++ ) {
				cutExercise.add(allTexLines[j]);
			}
			// Prepend header to each TODO for performance add it first.
			for (int j = header.size() - 1; j > -1; j--) {
				cutExercise.add(0, header.get(j));
			}
			// Append end
			cutExercise.add(new String("\\end{document}"));

			// Convert to an array
			String[] exerciseText = new String[cutExercise.size()];
			for (int j = 0; j < exerciseText.length; j++) {
				exerciseText[j] = ersetzeUmlaute(cutExercise.get(j));
			}
			Declaration dec = lineDecReference.get(linesToCut.get(i));
			// Creating file for it on harddrive in writeToHarddisk.

			// Increment here because we start with 1 instead of 0
			ex_count_and_pos++;

			// write to filesystem
			String new_ex_filelink = sheetdraft
				.getFilelinkForExerciseFromPosWithoutEnding(
					ex_count_and_pos, ex_count_and_pos)
				+ sheetdraft.getFileEnding();
			ReadWrite.write(exerciseText, new_ex_filelink);

			// Create Exercise instance for eventual further handling.
//			Exercise loopExercise = new Exercise(
//					new_ex_filelink
//					, dec
//					, exercisePlainText
//					//, exerciseText
//					, headermixture
//			);
//			outputTexExercises.add(loopExercise);

		}

		System.out.println("*done* LatexCutter: cutExercises");
		return outputTexExercises;
	}



	static void createImagesForExercises(Sheetdraft sheetdraft) throws FileNotFoundException {
		createImagesForExercises(sheetdraft.getAllExercises().values());
	}

	static void createImagesForExercises(Collection<Exercise> exercises) throws FileNotFoundException {
		for (Exercise exercise : exercises) {
			Converter.tex2image(exercise.filelink);
		}
	}



	/**
	 * Replaces all Umlauts and \u00DF with their latex representation.
	 * e.g. &Ouml; -> \"O, etc.
	 * TODO Add support for more languages (currently FRENCH).
	 *
	 * @param line
	 * @return The line with umlauts in LaTeX representation.
	 */
	private static String ersetzeUmlaute(String line) {

		String ergebnis = line.replaceAll("\u00D6", "\\\"O");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00F6", "\\\"o");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00DC", "\\\"U");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00FC", "\\\"u");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00C4", "\\\"A");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00E4", "\\\"a");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00DF", "sz");
		return ergebnis;
	}



}
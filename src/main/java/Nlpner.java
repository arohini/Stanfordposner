import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.Triple;

public class Nlpner {

	public static String sent() throws IOException {

		List<String> sentenceList = new LinkedList<String>();
		DocumentPreprocessor dp = new DocumentPreprocessor("/home/serendio/Desktop/input/rawinput1.txt");
		for (List sent : dp) {
			sentenceList.addAll(sent);
		}
		String per2 = "";
		String stanf = sentenceList.toString();
		return stanf;
	}

	public static String tokens() throws FileNotFoundException {
		PTBTokenizer ptbt = new PTBTokenizer(new FileReader("/home/serendio/Desktop/input/rawinput1.txt"),new CoreLabelTokenFactory(), "");
		String op = "";
		for (CoreLabel label; ptbt.hasNext();) {
			label = (CoreLabel) ptbt.next();
			op = label.toString();
		}
		return op;
	}

	public static String pos() throws IOException {
		String a = sent();
		List<String> st = new LinkedList<String>(Arrays.asList(a));
		String[] b = new String[] { a };
		MaxentTagger tagger = new MaxentTagger("/home/serendio/stanford-postagger-2014-08-27/models/english-left3words-distsim.tagger");
		String tagged = "";
		for (String i : b) {
			tagged = tagger.tagString(i);
			//System.out.println(tagged + "\n");
		}
		return tagged;
	}

	public static String ner() throws IOException{
		String serializedClassifier1 = "/home/serendio/stanford-ner-2014-08-271/classifiers/english.muc.7class.distsim.crf.ser.gz";
		AbstractSequenceClassifier<CoreLabel> classifier1 = CRFClassifier.getClassifierNoExceptions(serializedClassifier1);
		String per2 = "";
		String po = pos();
		for (Triple<String, Integer, Integer> triple : classifier1.classifyToCharacterOffsets(po)) {
			String op1 = triple.first() + " " + " --> " + " " + po.toString().substring(triple.second, triple.third);
			String[] op2 = new String[] { op1 };
			per2 = op1;
			System.out.println(per2);
		}
		return per2;
	}
	public static void main(String[] args) throws IOException {
		Nlpner n = new Nlpner();
		n.sent();
		n.pos();
		n.ner();

	}
}

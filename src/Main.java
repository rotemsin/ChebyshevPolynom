import java.util.Scanner;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello and welcome to the Chebyshev polynoms calculator!");
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the size of the series you would like");
		int size = Integer.parseInt(input.next());
		Polynom t0 = new Polynom();
		t0 = t0.initializet0();
		Polynom t1 = new Polynom();
		t1 = t1.initializet1();
		Polynom u0 = new Polynom();
		u0 = u0.initializet0();
		Polynom u1 = new Polynom();
		u1 = u1.initializeu1();
		Vector<Polynom> polynomsVectorFirstKind = computeChebyshevSeries(t1, t0, size, true);
		Vector<Polynom> polynomsVectorSecondKind = computeChebyshevSeries(u1, u0, size, false);
		int selection = 0;
		while (selection != 6) {
			System.out.println("Please enter the computation you would like to have:");
			System.out.println("1 - Check equality of regular polynom to trigonometric polynom");
			System.out.println("2 - Check relation between Tn(x) d/dx and n*Un-1(x)");
			System.out.println("3 - Calculate the roots of a Chebyshev polynom of the 1st kind");
			System.out.println("4 - Calculate the roots of a Chebyshev polynom of the 2nd kind");
			System.out.println("5 - Calculate the extremas of a Chebyshev polynom of the 1st kind");
			System.out.println("6 - Exit");
			selection = Integer.parseInt(input.next());
			if (selection == 1) {
				System.out.println("Please select the index i of Ti");
				int index = Integer.parseInt(input.next());
				checkEquality(polynomsVectorFirstKind, index);
			}
			if (selection == 2) {
				System.out.println("Please select the index i of Ti");
				int index = Integer.parseInt(input.next());
				checkRelation(polynomsVectorFirstKind, polynomsVectorSecondKind, index);
			}
			if (selection == 3) {
				System.out.println("Please select the index i of Ti");
				int index = Integer.parseInt(input.next());
				polynomsVectorFirstKind.elementAt(index).calculateRootsFirstKind();
			}
			if (selection == 4) {
				System.out.println("Please select the index i of Ui");
				int index = Integer.parseInt(input.next());
				polynomsVectorSecondKind.elementAt(index).calculateRootsSecondKind();
			}
			if (selection == 5) {
				System.out.println("Please select the index i of Ti");
				int index = Integer.parseInt(input.next());
				polynomsVectorSecondKind.elementAt(index).calculateExtremasFirstKind();
			}
		}
		input.close();
	}

	private static Vector<Polynom> computeChebyshevSeries(Polynom t1, Polynom t0, int size, boolean isFirstKind) {
		if (isFirstKind) {
			System.out.println("The series of Chebyshev polynoms of the 1st kind of size " + size + " is");
			System.out.println("t0: 1");
			System.out.println("t1: x");
		}
		else {
			System.out.println("The series of Chebyshev polynoms of the 2nd kind of size " + size + " is");
			System.out.println("u0: 1");
			System.out.println("u1: 2x");
		}
		Vector<Polynom> result = new Vector<Polynom>();
		result.add(t0);
		result.add(t1);
		Polynom tn = t1;
		Polynom tnMinus1 = t0;
		for (int i = 2; i <= size; i++) {
			Polynom next = tn.computeNextPolynom(tnMinus1);
			result.add(next);
			if (isFirstKind)
				System.out.print("t" + i +": ");
			else
				System.out.print("u" + i +": ");
			next.printPolynom();
			tnMinus1 = tn;
			tn = next;
		}
		System.out.println("");
		return result;
	}

	private static void checkEquality(Vector<Polynom> polynomsVector, int n) {
		System.out.println("Calculating if the values are the same for values from -1 to 1 with jumps of 0.1");
		for (double j = -1; j <= 1; j += 0.1) {
			System.out.println("For: " + j);
			double value1 = polynomsVector.elementAt(n).computeValue(j);
			System.out.println("The regular value is " + value1);
			double value2 = Math.cos(n * Math.acos(j));
			System.out.println("The trigonometric value is " + value2);
		}
		System.out.println("");
	}

	private static void checkRelation(Vector<Polynom> firstKind, Vector<Polynom> secondKind, int n) {
		Polynom tnDeriviative = null;
		if (n == 0) {
			System.out.println("Impossible: There is no U-1");
			System.out.println("");
		}
		else if (n == 1) {
			System.out.print("T" + n + " d/dx is: ");
			System.out.println("1");
			System.out.print("n*U" + n + " is: ");
			System.out.println("1");
			System.out.println("");
		}
		else if (n == 2) {
			System.out.print("T" + n + " d/dx is: ");
			System.out.println("4x");
			System.out.print("n*U" + n + " is: ");
			System.out.println("4x");
			System.out.println("");
		}
		else {
			tnDeriviative = firstKind.elementAt(n).derivate();
			System.out.print("T" + n + " d/dx is: ");
			tnDeriviative.printPolynom();
			Polynom unMinus1ByX = secondKind.elementAt(n - 1).multiplyByCoefficient(n);
			System.out.print("n*U" + n + " is: ");
			unMinus1ByX.printPolynom();
			System.out.println("");
		}
	}

	public static void printPolynomTrigonometric(int n) {
		System.out.println("cos(" + n + "arcos x)");
	}

}

import java.util.*;

public class Tester {
    static int[] testIntArr = new int[]{1,2,3,4};
    static ArrayList<Integer> testArrList = new ArrayList<Integer>(Arrays.asList(1,2,3,4));

    public static void main(String[] args){
        testPiece();
        // testRook();
    }

    public static void testRook() {
        Piece rook = new Rook("w");
        System.out.println("TESTING ROOK:");
        System.out.println(rook);
        // System.out.println(rook.getAllowed());
    }

    private static void testPiece() {
        // Does not test getAllowed(), doublejump(), hasMoved(), setHasMoved()
        Piece testPiece = new Piece("king", "w");
        System.out.println("TESTING PEICE:");
        System.out.println("\tObject: " + testPiece);
        System.out.println("\tSide: " + testPiece.getSide());
        System.out.println("\tValue: " + testPiece.getValue());
        System.out.println("\tSide Mod: " + testPiece.getSideMod());
        System.out.println("\tImage: " + testPiece.getImage());
        System.out.println("\tArrayList: " + testArrList + " to array: " + Arrays.toString(testPiece.arrListToArr(testArrList)));
        System.out.println("\tCol of 0: " + testPiece.colOf(0) +  
            ", Col of 7: " + testPiece.colOf(7) + 
            ", Col of 56: " + testPiece.colOf(56) + 
            ", Col of 63: " + testPiece.colOf(63));
        System.out.println("\tRow of 0: " + testPiece.rowOf(0) +  
            ", Row of 7: " + testPiece.rowOf(7) + 
            ", Row of 56: " + testPiece.rowOf(56) + 
            ", Row of 63: " + testPiece.rowOf(63));
        System.out.println("\tPos of (0,0): " + testPiece.posToNum(0,0) +  
            ", Pos of (0,7): " + testPiece.posToNum(0,7) + 
            ", Pos of (7,0): " + testPiece.posToNum(7,0) + 
            ", Pos of (7,7): " + testPiece.posToNum(7,7));
        System.out.println("\tTest in range: 0 = " + testPiece.inRange(0) + 
            ", 4 = " + testPiece.inRange(4) + 
            ", 7 = " + testPiece.inRange(7) + 
            ", 8 = " + testPiece.inRange(8) + 
            ", -1 = " + testPiece.inRange(-1));
    }
}
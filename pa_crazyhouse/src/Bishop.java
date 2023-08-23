package src;

import java.util.ArrayList;

public class Bishop extends APiece {
    public Bishop(PlaySide side) {
        super(side);
        setType(Piece.BISHOP);
        super.value = 30;
    }

    public double[][] importanceW = {
            {-2,  -1, -1, -1,  -1,   -1,  -1, -2},
            {-1, 0.5,  1,  0, 0.5,    0,   0, -1},
            {-1,   0,  1,  1, 0.5,  0.5,   0, -1},
            {-1,   0,  1,  1,   1,    1,   0, -1},
            {-1,   0,  1,  1,   1,    1,   0, -1},
            {-1,   0,  1,  1, 0.5,  0.5,   0, -1},
            {-1, 0.5,  1,  0, 0.5,    0,   0, -1},
            {-2,  -1, -1, -1,  -1,   -1,  -1, -2}
    };

    public double[][] importanceB = {
            {-2, -1,  -1,  -1, -1, -1,  -1, -2},
            {-1,  0,   0, 0.5,  0,  1, 0.5, -1},
            {-1,  0, 0.5, 0.5,  1,  1,   0, -1},
            {-1,  0,   1,   1,  1,  1,   0, -1},
            {-1,  0,   1,   1,  1,  1,   0, -1},
            {-1,  0, 0.5, 0.5,  1,  1,   0, -1},
            {-1,  0,   0, 0.5,  0,  1, 0.5, -1},
            {-2, -1,  -1,  -1, -1, -1,  -1, -2}
    };



    @Override
    public double[][] getImportanceW() {
        return importanceW;
    }

    public double[][] getImportanceB() {
        return importanceB;
    }
    // nebun
    @Override
    public ArrayList<Move> calculatePossibleMoves(String source, APiece[][] table) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int[] indexes = translateMoveToIndex(source);
        int[] x = {-1, -1, 1, 1};
        int[] y = {-1, 1, -1, 1};

        for (int k = 0; k < 4; k++) {
            int i = indexes[0] + x[k];
            int j = indexes[1] + y[k];
            while (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
                if (table[i][j] == null) {
                    String destination = createDestination(i, j);
                    Move move = Move.moveTo(source, destination);
                    possibleMoves.add(move);
                } else if (table[i][j].getSide() != this.getSide()) {
                    String destination = createDestination(i, j);
                    Move move = Move.moveTo(source, destination);
                    APiece piece = table[i][j];
                    move.valueCapt = piece.value;
                    move.setCapture(true);
                    possibleMoves.add(move);
                    break;
                } else break;

                i += x[k];
                j += y[k];
            }
        }

        return possibleMoves;
    }
}